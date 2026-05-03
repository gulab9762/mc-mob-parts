package net.gbdhapa.fabric.registry;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.gbdhapa.Mobparts;
import net.gbdhapa.item.ModItems;
import net.gbdhapa.block.ModBlocks;
import net.gbdhapa.block.ModBlockEntities;
import net.gbdhapa.block.PedestalBlockEntity;

public class ModRegistry {

    public static void register() {
        ModItems.registerAll(ModRegistry::registerItem);
        ModBlocks.registerAll(ModRegistry::registerBlock);

        // Register BlockEntityType for the pedestal
        ModBlockEntities.PEDESTAL_BLOCK_ENTITY = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            Mobparts.id("pedestal"),
            FabricBlockEntityTypeBuilder.create(PedestalBlockEntity::new, ModBlocks.STATUE_PEDESTAL).build()
        );

        // Creative Tab Integration for 26.1 (Tiny Takeover)
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(output -> {
            ModItems.registerAll((name, item) -> output.accept(item));
            ModBlocks.registerAll((name, block) -> {
                Identifier id = Mobparts.id(name);
                BuiltInRegistries.ITEM.get(id).ifPresent(ref -> output.accept(ref.value()));
            });
        });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(output -> {
            ModBlocks.registerAll((name, block) -> {
                // Find the block item from registries
                Identifier id = Mobparts.id(name);
                BuiltInRegistries.ITEM.get(id).ifPresent(ref -> output.accept(ref.value()));
            });
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
