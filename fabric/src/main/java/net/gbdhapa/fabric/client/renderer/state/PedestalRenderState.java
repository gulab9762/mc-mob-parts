package net.gbdhapa.fabric.client.renderer.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemStack;

public class PedestalRenderState extends BlockEntityRenderState {
    public ItemStack itemStack = ItemStack.EMPTY;
    public final ItemStackRenderState itemRenderState = new ItemStackRenderState();
}
