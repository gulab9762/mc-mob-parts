package net.gbdhapa.fabric;

import net.fabricmc.api.ModInitializer;
import net.gbdhapa.Mobparts;

public class MobpartsFabric implements ModInitializer {
	@Override
	public void onInitialize() {
		Mobparts.init();
		net.gbdhapa.fabric.registry.ModRegistry.register();
		Mobparts.LOGGER.info("Mobparts registered on Fabric!");
	}
}
