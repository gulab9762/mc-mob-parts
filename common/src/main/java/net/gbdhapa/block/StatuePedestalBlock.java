package net.gbdhapa.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class StatuePedestalBlock extends BaseEntityBlock {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    public StatuePedestalBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(StatuePedestalBlock::new);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        if (pos.getY() < level.getMaxY() && level.getBlockState(pos.above()).canBeReplaced(context)) {
            return defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER);
        }
        return null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        level.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess tickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        DoubleBlockHalf half = state.getValue(HALF);
        if (direction.getAxis() == Direction.Axis.Y) {
            if (half == DoubleBlockHalf.LOWER && direction == Direction.UP) {
                if (!neighborState.is(this) || neighborState.getValue(HALF) != DoubleBlockHalf.UPPER) {
                    return Blocks.AIR.defaultBlockState();
                }
            } else if (half == DoubleBlockHalf.UPPER && direction == Direction.DOWN) {
                if (!neighborState.is(this) || neighborState.getValue(HALF) != DoubleBlockHalf.LOWER) {
                    return Blocks.AIR.defaultBlockState();
                }
            }
        }
        return state;
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide()) {
            DoubleBlockHalf half = state.getValue(HALF);
            BlockPos otherPos = half == DoubleBlockHalf.LOWER ? pos.above() : pos.below();
            BlockState otherState = level.getBlockState(otherPos);
            if (otherState.is(this) && otherState.getValue(HALF) != half) {
                level.setBlock(otherPos, Blocks.AIR.defaultBlockState(), 35);
                level.levelEvent(player, 2001, otherPos, Block.getId(otherState));
            }

            // Drop all displayed items if the lower block is destroyed
            BlockPos lowerPos = half == DoubleBlockHalf.LOWER ? pos : pos.below();
            if (level.getBlockEntity(lowerPos) instanceof PedestalBlockEntity pedestal) {
                pedestal.getParts().values().forEach(stack -> {
                    if (!stack.isEmpty()) {
                        Block.popResource(level, lowerPos, stack);
                    }
                });
                pedestal.getParts().clear();
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            return new PedestalBlockEntity(pos, state);
        }
        return null;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack heldStack, BlockState state, Level level, BlockPos pos,
                                          Player player, InteractionHand hand, BlockHitResult hit) {
        BlockPos lowerPos = state.getValue(HALF) == DoubleBlockHalf.LOWER ? pos : pos.below();

        if (!(level.getBlockEntity(lowerPos) instanceof PedestalBlockEntity pedestal)) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide()) {
            String partType = getPartType(heldStack);
            
            if (partType != null && !pedestal.hasPart(partType)) {
                // Place specific part
                pedestal.setPart(partType, heldStack.copyWithCount(1));
                if (!player.isCreative()) {
                    heldStack.shrink(1);
                }
                level.sendBlockUpdated(lowerPos, level.getBlockState(lowerPos), level.getBlockState(lowerPos), 3);
                return InteractionResult.SUCCESS;
            } else if (heldStack.isEmpty()) {
                // Remove last added part
                ItemStack removed = pedestal.removeLastPart();
                if (!removed.isEmpty()) {
                    if (!player.addItem(removed)) {
                        Block.popResource(level, pos, removed);
                    }
                    level.sendBlockUpdated(lowerPos, level.getBlockState(lowerPos), level.getBlockState(lowerPos), 3);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        
        // Return SUCCESS if we did something, otherwise PASS
        return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Nullable
    private String getPartType(ItemStack stack) {
        if (stack.is(ModBlocks.ZOMBIE_FOOT.asItem()) || stack.is(ModBlocks.SKELETON_FOOT.asItem())) return "feet";
        if (stack.is(ModBlocks.ZOMBIE_LEG.asItem()) || stack.is(ModBlocks.SKELETON_LEG.asItem())) return "legs";
        if (stack.is(ModBlocks.ZOMBIE_TORSO.asItem()) || stack.is(ModBlocks.SKELETON_TORSO.asItem())) return "torso";
        if (stack.is(ModBlocks.ZOMBIE_HAND.asItem()) || stack.is(ModBlocks.SKELETON_HAND.asItem())) return "hands";
        return null;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
