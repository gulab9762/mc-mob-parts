package net.gbdhapa.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.gbdhapa.Mobparts;

public class ModBlocks {
    public static final Block STATUE_PEDESTAL = register("statue_pedestal", 
        BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2.0f));

    private static Block register(String name, BlockBehaviour.Properties properties) {
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, Mobparts.id(name));
        return new Block(properties.setId(key));
    }
}
