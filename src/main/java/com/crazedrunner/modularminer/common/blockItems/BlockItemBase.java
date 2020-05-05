package com.crazedrunner.modularminer.common.blockItems;

import com.crazedrunner.modularminer.common.ModularMiner;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;


public class BlockItemBase extends BlockItem{
    public BlockItemBase(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    public BlockItemBase(Block blockIn){
        this(blockIn, new Item.Properties().group(ModularMiner.TAB));
    }
}
