package com.crazedrunner.modularminer.common.blockItems;

import com.crazedrunner.modularminer.common.ModularMiner;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;



public class BlockItemBase extends BlockItem{
    public BlockItemBase(Block blockIn, Properties builder) {
        super(blockIn, builder);
        builder.group(ModularMiner.TAB);
    }
}
