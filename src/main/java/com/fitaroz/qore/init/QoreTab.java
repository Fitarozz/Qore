package com.fitaroz.qore.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import com.fitaroz.qore.Qore;

public class QoreTab {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Qore.MODID);
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> QORE = REGISTRY.register("qore",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.qore.qore")).icon(() -> new ItemStack(QoreItems.ALUMINUM_INGOT.get())).displayItems((parameters, tabData) -> {
                tabData.accept(QoreItems.ALUMINUM_INGOT.get());
				tabData.accept(QoreBlocks.BAUXITE_ORE.get().asItem());
				tabData.accept(QoreItems.RAW_BAUXITE.get());
				tabData.accept(QoreBlocks.TEST_BLOCK.get().asItem());
				tabData.accept(QoreBlocks.MODULAR_BLOCK.get().asItem());
			})

					.build());
}
