package net.gbdhapa.fabric.client.renderer.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

public class PedestalRenderState extends BlockEntityRenderState {
    public final ItemStackRenderState feetState = new ItemStackRenderState();
    public final ItemStackRenderState legsState = new ItemStackRenderState();
    public final ItemStackRenderState torsoState = new ItemStackRenderState();
    public final ItemStackRenderState handsState = new ItemStackRenderState();
    
    public boolean hasFeet;
    public boolean hasLegs;
    public boolean hasTorso;
    public boolean hasHands;
    
    public int light;
    public int overlay;
}
