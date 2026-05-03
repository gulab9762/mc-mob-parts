package net.gbdhapa.fabric.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.gbdhapa.block.PedestalBlockEntity;
import net.gbdhapa.fabric.client.renderer.state.PedestalRenderState;
import net.gbdhapa.item.ModItems;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.Minecraft;
import net.gbdhapa.block.ModBlocks;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraft.client.renderer.rendertype.RenderType;

public class PedestalBlockEntityRenderer implements BlockEntityRenderer<PedestalBlockEntity, PedestalRenderState> {
    private final ItemModelResolver itemModelResolver;

    public PedestalBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = Minecraft.getInstance().getItemModelResolver();
    }

    @Override
    public PedestalRenderState createRenderState() {
        return new PedestalRenderState();
    }

    @Override
    public void extractRenderState(PedestalBlockEntity entity, PedestalRenderState state, float partialTick, Vec3 pos, ModelFeatureRenderer.CrumblingOverlay crumbling) {
        ItemStack stack = entity.getDisplayedItem();
        state.itemStack = stack.copy();
        if (!stack.isEmpty()) {
            this.itemModelResolver.updateForTopItem(state.itemRenderState, stack, ItemDisplayContext.FIXED, entity.getLevel(), null, 0);
        }
    }

    @Override
    public void submit(PedestalRenderState state, PoseStack poses, SubmitNodeCollector collector, CameraRenderState camera) {
        if (state.itemStack.isEmpty()) return;

        double height = 1.0;

        if (state.itemStack.is(ModBlocks.ZOMBIE_HAND.asItem()) || state.itemStack.is(ModBlocks.SKELETON_HAND.asItem())) {
            // Left Hand
            poses.pushPose();
            poses.translate(0.25, height, 0.5);
            poses.mulPose(Axis.YP.rotationDegrees(90));
            state.itemRenderState.submit(poses, collector, 0, 0, 0); 
            poses.popPose();

            // Right Hand
            poses.pushPose();
            poses.translate(0.75, height, 0.5);
            poses.mulPose(Axis.YP.rotationDegrees(-90));
            state.itemRenderState.submit(poses, collector, 0, 0, 0);
            poses.popPose();
        } else {
            poses.pushPose();
            poses.translate(0.5, height, 0.5);
            state.itemRenderState.submit(poses, collector, 0, 0, 0);
            poses.popPose();
        }
    }
}
