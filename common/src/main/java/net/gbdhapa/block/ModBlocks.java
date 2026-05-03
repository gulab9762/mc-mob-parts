package net.gbdhapa.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.gbdhapa.Mobparts;

import java.util.function.BiConsumer;

public class ModBlocks {
    public static final Block STATUE_PEDESTAL = registerCustom("statue_pedestal",
        new StatuePedestalBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
            .strength(2.0f)
            .noOcclusion()
            .setId(ResourceKey.create(Registries.BLOCK, Mobparts.id("statue_pedestal")))));

    // Decorative Mob Part Blocks
    public static final Block ZOMBIE_HAND = register("zombie_hand", mobPartProps());
    public static final Block ZOMBIE_FOOT = register("zombie_foot", mobPartProps());
    public static final Block ZOMBIE_LEG = register("zombie_leg", mobPartProps());
    public static final Block ZOMBIE_TORSO = register("zombie_torso", mobPartProps());

    public static final Block SKELETON_HAND = register("skeleton_hand", mobPartProps());
    public static final Block SKELETON_FOOT = register("skeleton_foot", mobPartProps());
    public static final Block SKELETON_LEG = register("skeleton_leg", mobPartProps());
    public static final Block SKELETON_TORSO = register("skeleton_torso", mobPartProps());

    private static BlockBehaviour.Properties mobPartProps() {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.END_ROD)
            .noOcclusion()
            .isSuffocating((state, level, pos) -> false)
            .isRedstoneConductor((state, level, pos) -> false);
    }

    private static Block register(String name, BlockBehaviour.Properties properties) {
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, Mobparts.id(name));
        if (name.contains("hand") || name.contains("foot") || name.contains("leg") || name.contains("torso")) {
            return new DecorativeMobPartBlock(properties.setId(key));
        }
        return new Block(properties.setId(key));
    }

    private static Block registerCustom(String name, Block block) {
        return block;
    }

    public static void registerAll(BiConsumer<String, Block> registerer) {
        registerer.accept("statue_pedestal", STATUE_PEDESTAL);
        registerer.accept("zombie_hand", ZOMBIE_HAND);
        registerer.accept("zombie_foot", ZOMBIE_FOOT);
        registerer.accept("zombie_leg", ZOMBIE_LEG);
        registerer.accept("zombie_torso", ZOMBIE_TORSO);
        registerer.accept("skeleton_hand", SKELETON_HAND);
        registerer.accept("skeleton_foot", SKELETON_FOOT);
        registerer.accept("skeleton_leg", SKELETON_LEG);
        registerer.accept("skeleton_torso", SKELETON_TORSO);
    }
}
