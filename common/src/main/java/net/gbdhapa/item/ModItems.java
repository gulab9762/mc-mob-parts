package net.gbdhapa.item;

import net.minecraft.world.item.Item;
import java.util.function.BiConsumer;

public class ModItems {
    // Mob parts are now registered as Blocks in ModBlocks

    public static void registerAll(BiConsumer<String, Item> registerer) {
        // Any items that are NOT blocks would go here
    }

    public static void init() {
    }
}
