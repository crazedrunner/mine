package com.crazedrunner.modularminer.common.tileentity;

import com.crazedrunner.modularminer.common.tileentity.base.ModularDeviceEntity;
import com.crazedrunner.modularminer.common.util.registries.TileEntityRegistryHandler;
import net.minecraft.tileentity.TileEntityType;

public class MinerControllerEntity extends ModularDeviceEntity {
    public MinerControllerEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public MinerControllerEntity(){
        this(TileEntityRegistryHandler.MINER_CONTROLLER_ENTITY.get());
    }

    @Override
    public void tick() {
        super.tick();

    }
}
