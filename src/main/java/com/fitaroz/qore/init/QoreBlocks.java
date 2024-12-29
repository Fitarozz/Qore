package com.fitaroz.qore.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

import com.fitaroz.qore.blocks.BauxiteOreBlock;
import com.fitaroz.qore.blocks.TestBlock;
import com.fitaroz.qore.blocks.solar_panel.SolarPanelBlock;
import com.fitaroz.qore.blocks.ModularBlock;

import com.fitaroz.qore.Qore;

public class QoreBlocks {
	public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(Qore.MODID);
	public static final DeferredBlock<Block> BAUXITE_ORE = REGISTRY.register("bauxite_ore", BauxiteOreBlock::new);
	public static final DeferredBlock<Block> TEST_BLOCK = REGISTRY.register("test_block", TestBlock::new);
	public static final DeferredBlock<Block> MODULAR_BLOCK = REGISTRY.register("modular_block", ModularBlock::new);
	public static final DeferredBlock<Block> SOLAR_PANEL_BLOCK = REGISTRY.register("solar_panel", SolarPanelBlock::new);

	// Start of user code block custom blocks
	// End of user code block custom blocks
}
