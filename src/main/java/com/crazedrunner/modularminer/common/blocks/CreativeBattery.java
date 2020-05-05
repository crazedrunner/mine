package com.crazedrunner.modularminer.common.blocks;

import com.crazedrunner.modularminer.common.util.registries.TileEntityRegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class CreativeBattery extends Block {
    public CreativeBattery(){
        super(Block.Properties.create(Material.IRON));
    }

    @Override
    public boolean hasTileEntity(BlockState blockState) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world){
        return TileEntityRegistryHandler.CREATIVE_BATTERY_ENTITY.get().create();
    }
}
