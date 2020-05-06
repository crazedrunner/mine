package com.crazedrunner.modularminer.common.blocks;

import com.crazedrunner.modularminer.common.util.registries.TileEntityRegistryHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;


public class Miner extends Block {
    public Miner(Properties properties){
        super(properties);
    }

    public Miner(){
        this(Block.Properties.create(Material.IRON));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world){
        return TileEntityRegistryHandler.MINER_TILE_ENTITY.get().create();
    }

}
