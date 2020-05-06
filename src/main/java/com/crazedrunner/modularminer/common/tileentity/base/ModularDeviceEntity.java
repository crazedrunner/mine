package com.crazedrunner.modularminer.common.tileentity.base;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModularDeviceEntity extends TileEntity implements ITickableTileEntity {
    protected final static Logger LOGGER = LogManager.getLogger();
    private final static Direction[] validDirections = {
            Direction.UP,
            Direction.DOWN,
            Direction.NORTH,
            Direction.EAST,
            Direction.SOUTH,
            Direction.WEST
    };
    protected int tick = 0;
    protected int ticksToComplete = 0;
    protected ModularDeviceEntity master;
    protected boolean isMaster = false;

    public ModularDeviceEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public void onLoad() {
        initializeMultiBlock();
    }

    @Override
    public void tick() {
        if (world != null && !world.isRemote) {
            tick++;
        }
    }

    public void resetTick() {
        tick = 0;

    }

    public boolean isMaster() {
        return this.isMaster;
    }

    public ModularDeviceEntity getMaster() {
        return master;
    }

    private void initializeMultiBlock() {
        if (world != null) {
            for (Direction direction : validDirections) {
                BlockPos checkMe = pos.offset(direction);
                TileEntity tileEntity = world.getTileEntity(checkMe);
                if (tileEntity instanceof ModularDeviceEntity) {
                    LOGGER.debug("Found another Miner block at: " + checkMe.toString());
                }
            }
        }
    }


}
