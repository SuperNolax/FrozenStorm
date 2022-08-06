package net.frozenblock.frozenstorm.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.frozenblock.frozenstorm.FrozenStorm;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class RegisterItems {

    public static final Item FROZEN_NETHER_STAR = register("frozen_nether_star", new NetherStarItem(new FabricItemSettings().group(ItemGroup.MATERIALS).rarity(Rarity.RARE)));

    public static final Item FROZEN_STORM_SKULL = register(new WallStandingBlockItem(RegisterBlocks.FROZEN_STORM_SKULL, RegisterBlocks.FROZEN_STORM_WALL_SKULL, new FabricItemSettings().group(ItemGroup.DECORATIONS).rarity(Rarity.RARE)));

    public static void init() {
    }

    private static Item register(Block block, ItemGroup group, Block... blocks) {
        BlockItem blockItem = new BlockItem(block, new Item.Settings().group(group));

        for(Block block2 : blocks) {
            Item.BLOCK_ITEMS.put(block2, blockItem);
        }

        return register(blockItem);
    }

    private static Item register(BlockItem item) {
        return register(item.getBlock(), item);
    }

    protected static Item register(Block block, Item item) {
        return register(Registry.BLOCK.getId(block), item);
    }

    private static Item register(String id, Item item) {
        return register(FrozenStorm.id(id), item);
    }

    private static Item register(Identifier id, Item item) {
        if (item instanceof BlockItem) {
            ((BlockItem)item).appendBlocks(Item.BLOCK_ITEMS, item);
        }

        return Registry.register(Registry.ITEM, id, item);
    }
}
