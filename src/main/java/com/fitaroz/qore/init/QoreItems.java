package com.fitaroz.qore.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;

import com.fitaroz.qore.items.RawBauxiteItem;
import com.fitaroz.qore.items.AluminumIngotItem;

import com.fitaroz.qore.Qore;


public class QoreItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(Qore.MODID);
	
	public static final DeferredItem<Item> ALUMINUM_INGOT = REGISTRY.register("aluminum_ingot", AluminumIngotItem::new);
	public static final DeferredItem<Item> RAW_BAUXITE = REGISTRY.register("raw_bauxite", RawBauxiteItem::new);

	public static final DeferredItem<Item> BAUXITE_ORE = block(QoreBlocks.BAUXITE_ORE);
	public static final DeferredItem<Item> TEST_BLOCK = block(QoreBlocks.TEST_BLOCK);
	public static final DeferredItem<Item> MODULAR_BLOCK = block(QoreBlocks.MODULAR_BLOCK);
	
	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
	}

}
