package net.gbdhapa.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.gbdhapa.client.MobpartsClient;

public class MobpartsFabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MobpartsClient.initClient();
	}
}
