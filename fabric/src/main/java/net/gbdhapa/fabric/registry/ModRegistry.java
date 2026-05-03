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
import net.gbdhapa.item.ModItems;
import net.gbdhapa.block.ModBlocks;

public class ModRegistry {

    public static void register() {
        registerItem("zombie_hand", ModItems.ZOMBIE_HAND);
        registerItem("zombie_foot", ModItems.ZOMBIE_FOOT);
        registerItem("zombie_leg", ModItems.ZOMBIE_LEG);
        registerItem("zombie_torso", ModItems.ZOMBIE_TORSO);
        
        registerItem("skeleton_hand", ModItems.SKELETON_HAND);
        registerItem("skeleton_foot", ModItems.SKELETON_FOOT);
        registerItem("skeleton_leg", ModItems.SKELETON_LEG);
        registerItem("skeleton_torso", ModItems.SKELETON_TORSO);

        registerBlock("statue_pedestal", ModBlocks.STATUE_PEDESTAL);

        // Creative Tab Integration for 26.1 (Tiny Takeover)
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(output -> {
            output.accept(ModItems.ZOMBIE_HAND);
            output.accept(ModItems.ZOMBIE_FOOT);
            output.accept(ModItems.ZOMBIE_LEG);
            output.accept(ModItems.ZOMBIE_TORSO);
            output.accept(ModItems.SKELETON_HAND);
            output.accept(ModItems.SKELETON_FOOT);
            output.accept(ModItems.SKELETON_LEG);
            output.accept(ModItems.SKELETON_TORSO);
        });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(output -> {
            output.accept(ModBlocks.STATUE_PEDESTAL);
        });
    }

    private static void registerItem(String name, Item item) {
        Registry.register(BuiltInRegistries.ITEM, Mobparts.id(name), item);
    }

    private static void registerBlock(String name, Block block) {
        Identifier id = Mobparts.id(name);
        Registry.register(BuiltInRegistries.BLOCK, id, block);
        
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, id);
        Registry.register(BuiltInRegistries.ITEM, id, new BlockItem(block, new Item.Properties().setId(key)));
    }
}
