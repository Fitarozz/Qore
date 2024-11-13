package com.fitaroz.qore.items;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class RawBauxiteItem extends Item {
	public RawBauxiteItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}
}
