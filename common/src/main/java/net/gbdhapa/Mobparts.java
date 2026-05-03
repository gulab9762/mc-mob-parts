package net.gbdhapa;

import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mobparts {
	public static final String MOD_ID = "mobparts";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static void init() {
		LOGGER.info("Initializing Mobparts common");
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
