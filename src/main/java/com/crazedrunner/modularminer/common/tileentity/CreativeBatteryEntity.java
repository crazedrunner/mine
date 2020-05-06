package com.crazedrunner.modularminer.common.tileentity;

import com.crazedrunner.modularminer.common.tileentity.base.MachineTileEntity;
import com.crazedrunner.modularminer.common.util.registries.TileEntityRegistryHandler;
import com.mojang.datafixers.types.templates.CompoundList;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class CreativeBatteryEntity extends MachineTileEntity {
    private static final String MACHINE_TO_POWER_TAG = "machinesToPower";
    ArrayList<BlockPos> machinesToPowerList = new ArrayList<>();

    public CreativeBatteryEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public CreativeBatteryEntity(){
        this(TileEntityRegistryHandler.CREATIVE_BATTERY_ENTITY.get());
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        super.write(nbt);

        ArrayList<Long> machineLocations = new ArrayList<>();
        for(BlockPos blockpos : machinesToPowerList){
            machineLocations.add(blockpos.toLong());
        }
        nbt.putLongArray(MACHINE_TO_POWER_TAG, machineLocations);
        return nbt;
    }

    @Override
    public void read(@Nonnull CompoundNBT nbt) {
        super.read(nbt);
        long[] machines = nbt.getLongArray(MACHINE_TO_POWER_TAG);
        for (long machine : machines){
            machinesToPowerList.add(BlockPos.fromLong(machine));
        }
    }
}
