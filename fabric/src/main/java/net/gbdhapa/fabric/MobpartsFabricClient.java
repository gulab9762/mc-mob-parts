package net.gbdhapa.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.gbdhapa.client.MobpartsClient;

import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.gbdhapa.block.ModBlockEntities;
import net.gbdhapa.block.ModBlocks;
import net.gbdhapa.fabric.client.renderer.PedestalBlockEntityRenderer;

public class MobpartsFabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MobpartsClient.initClient();

        // Register BlockEntityRenderer
        BlockEntityRenderers.register(ModBlockEntities.PEDESTAL_BLOCK_ENTITY, PedestalBlockEntityRenderer::new);
	}
}
