package net.gbdhapa.fabric.registry;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.gbdhapa.Mobparts;
import net.gbdhapa.block.ModBlocks;

public class ModRegistry {

    public static final Item ZOMBIE_HAND = registerItemField("zombie_hand");
    public static final Item ZOMBIE_FOOT = registerItemField("zombie_foot");
    public static final Item ZOMBIE_LEG = registerItemField("zombie_leg");
    public static final Item ZOMBIE_TORSO = registerItemField("zombie_torso");
    
    public static final Item SKELETON_HAND = registerItemField("skeleton_hand");
    public static final Item SKELETON_FOOT = registerItemField("skeleton_foot");
    public static final Item SKELETON_LEG = registerItemField("skeleton_leg");
    public static final Item SKELETON_TORSO = registerItemField("skeleton_torso");

    public static void register() {
        registerBlock("statue_pedestal", ModBlocks.STATUE_PEDESTAL);

        // Creative Tab Integration for 26.1 (Tiny Takeover)
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(output -> {
            output.accept(ZOMBIE_HAND);
            output.accept(ZOMBIE_FOOT);
            output.accept(ZOMBIE_LEG);
            output.accept(ZOMBIE_TORSO);
            output.accept(SKELETON_HAND);
            output.accept(SKELETON_FOOT);
            output.accept(SKELETON_LEG);
            output.accept(SKELETON_TORSO);
        });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(output -> {
            output.accept(ModBlocks.STATUE_PEDESTAL);
        });
    }

    private static Item registerItemField(String name) {
        Identifier id = Mobparts.id(name);
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, id);
        Item item = new Item(new Item.Properties().setId(key));
        return Registry.register(BuiltInRegistries.ITEM, id, item);
    }

    private static void registerBlock(String name, Block block) {
        Identifier id = Mobparts.id(name);
        Registry.register(BuiltInRegistries.BLOCK, id, block);
        
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, id);
        Registry.register(BuiltInRegistries.ITEM, id, new BlockItem(block, new Item.Properties().setId(key)));
    }
}
