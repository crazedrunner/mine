package com.crazedrunner.modularminer.common.items;

import com.crazedrunner.modularminer.common.ModularMiner;
import net.minecraft.item.Item;


public class ItemBase extends Item {

    public ItemBase() {
        super(new Item.Properties().group(ModularMiner.TAB));
    }
}
