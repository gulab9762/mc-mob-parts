package net.gbdhapa.fabric.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.gbdhapa.block.PedestalBlockEntity;
import net.gbdhapa.fabric.client.renderer.state.PedestalRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraft.client.renderer.texture.OverlayTexture;

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
        // Extract lighting
        if (entity.getLevel() != null) {
            state.light = LevelRenderer.getLightCoords(entity.getLevel(), entity.getBlockPos().above());
        } else {
            state.light = 15728880;
        }
        state.overlay = OverlayTexture.NO_OVERLAY;

        // Extract parts
        updatePart(state.feetState, entity.getPart("feet"), state, "feet");
        state.hasFeet = !entity.getPart("feet").isEmpty();
        
        updatePart(state.legsState, entity.getPart("legs"), state, "legs");
        state.hasLegs = !entity.getPart("legs").isEmpty();
        
        updatePart(state.torsoState, entity.getPart("torso"), state, "torso");
        state.hasTorso = !entity.getPart("torso").isEmpty();
        
        updatePart(state.handsState, entity.getPart("hands"), state, "hands");
        state.hasHands = !entity.getPart("hands").isEmpty();
    }

    private void updatePart(ItemStackRenderState itemState, ItemStack stack, PedestalRenderState state, String type) {
        if (!stack.isEmpty()) {
            this.itemModelResolver.updateForTopItem(itemState, stack, ItemDisplayContext.FIXED, null, null, 0);
        }
    }

    @Override
    public void submit(PedestalRenderState state, PoseStack poses, SubmitNodeCollector collector, CameraRenderState camera) {
        // Feet
        if (state.hasFeet) {
            renderDual(state.feetState, state, poses, collector, 0.2, 0.2);
        }
        
        // Legs
        if (state.hasLegs) {
            renderDual(state.legsState, state, poses, collector, 0.5, 0.2);
        }
        
        // Torso
        if (state.hasTorso) {
            renderSingle(state.torsoState, state, poses, collector, 1.0);
        }
        
        // Hands
        if (state.hasHands) {
            renderDual(state.handsState, state, poses, collector, 1.0, 0.25);
        }
    }

    private void renderDual(ItemStackRenderState itemState, PedestalRenderState state, PoseStack poses, SubmitNodeCollector collector, double height, double offset) {
        // Left
        poses.pushPose();
        poses.translate(0.5 - offset, height, 0.5);
        poses.mulPose(Axis.YP.rotationDegrees(90));
        itemState.submit(poses, collector, state.light, state.overlay, 0);
        poses.popPose();

        // Right
        poses.pushPose();
        poses.translate(0.5 + offset, height, 0.5);
        poses.mulPose(Axis.YP.rotationDegrees(-90));
        itemState.submit(poses, collector, state.light, state.overlay, 0);
        poses.popPose();
    }

    private void renderSingle(ItemStackRenderState itemState, PedestalRenderState state, PoseStack poses, SubmitNodeCollector collector, double height) {
        poses.pushPose();
        poses.translate(0.5, height, 0.5);
        itemState.submit(poses, collector, state.light, state.overlay, 0);
        poses.popPose();
    }
}
