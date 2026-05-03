package net.gbdhapa.item;

import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.gbdhapa.Mobparts;

public class ModItems {
    public static final Item ZOMBIE_HAND = register("zombie_hand");
    public static final Item ZOMBIE_FOOT = register("zombie_foot");
    public static final Item ZOMBIE_LEG = register("zombie_leg");
    public static final Item ZOMBIE_TORSO = register("zombie_torso");
    
    public static final Item SKELETON_HAND = register("skeleton_hand");
    public static final Item SKELETON_FOOT = register("skeleton_foot");
    public static final Item SKELETON_LEG = register("skeleton_leg");
    public static final Item SKELETON_TORSO = register("skeleton_torso");

    private static Item register(String name) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Mobparts.id(name));
        return new Item(new Item.Properties().setId(key));
    }

    public static void init() {
        // Just to trigger class loading
    }
}
