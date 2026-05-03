package net.gbdhapa.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class PedestalBlockEntity extends BlockEntity {
    private final Map<String, ItemStack> parts = new HashMap<>();

    public PedestalBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PEDESTAL_BLOCK_ENTITY, pos, state);
    }

    public Map<String, ItemStack> getParts() {
        return parts;
    }

    public void setPart(String type, ItemStack stack) {
        if (stack.isEmpty()) {
            parts.remove(type);
        } else {
            parts.put(type, stack.copy());
        }
        setChanged();
    }

    public ItemStack getPart(String type) {
        return parts.getOrDefault(type, ItemStack.EMPTY);
    }

    public boolean hasPart(String type) {
        return parts.containsKey(type) && !parts.get(type).isEmpty();
    }

    public ItemStack removeLastPart() {
        String[] order = {"hands", "torso", "legs", "feet"};
        for (String type : order) {
            if (hasPart(type)) {
                ItemStack removed = parts.remove(type);
                setChanged();
                return removed;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        parts.forEach((type, stack) -> {
            if (!stack.isEmpty()) {
                output.store("part_" + type, ItemStack.CODEC, stack);
            }
        });
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        String[] types = {"feet", "legs", "torso", "hands"};
        parts.clear();
        for (String type : types) {
            input.read("part_" + type, ItemStack.CODEC).ifPresent(stack -> parts.put(type, stack));
        }
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveCustomOnly(registries);
    }
}
