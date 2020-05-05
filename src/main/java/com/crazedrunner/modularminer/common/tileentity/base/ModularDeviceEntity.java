package com.crazedrunner.modularminer.common.tileentity.base;

import com.crazedrunner.modularminer.common.blocks.MinerController;
import com.crazedrunner.modularminer.common.tileentity.MinerControllerEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;

public class ModularDeviceEntity extends TileEntity implements ITickableTileEntity {
    protected int tick = 0;
    protected int ticksToComplete = 0;
    protected MinerControllerEntity controller;
    protected boolean isMaster = false;

    private Direction[] validDirections = {
            Direction.UP,
            Direction.DOWN,
            Direction.NORTH,
            Direction.EAST,
            Direction.SOUTH,
            Direction.WEST};

    public ModularDeviceEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public void tick() {
        if(world != null && !world.isRemote){
            tick++;
        }
    }

    public void resetTick(){
        tick = 0;

    }

    public MinerControllerEntity getController(){
        return controller;
    }

    private void initializeMultiBlock(){

    }


}
