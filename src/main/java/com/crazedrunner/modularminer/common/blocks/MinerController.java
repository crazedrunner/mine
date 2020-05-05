package com.crazedrunner.modularminer.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;


public class MinerController extends Block{

    public MinerController() {
        super(Block.Properties.create(Material.IRON).hardnessAndResistance(4.0f, 4.0f).sound(SoundType.ANVIL));
    }

}
