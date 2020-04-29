package com.crazedrunner.modularminer.common.capability;

import com.crazedrunner.modularminer.common.util.enums.NBTKeys;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.IEnergyStorage;

public class MinerEnergy implements IEnergyStorage, INBTSerializable<CompoundNBT> {
    /*
        Special Thanks to Bektor for his post:
            https://www.minecraftforge.net/forum/topic/60119-112-using-forge-energy-fe-help-still-needed/
     */

    private int maxEnergyStored = 0;
    private int energyStored = 0;
    private int maxIncomingEnergy;

    public MinerEnergy(int energyStored, int maxEnergyStored, int maxIncomingEnergy){
        this.maxEnergyStored = maxEnergyStored;
        this.energyStored = energyStored;
        this.maxIncomingEnergy = maxIncomingEnergy;
    }
    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt(NBTKeys.MinerEnergy.MAX_ENERGY.name(), this.maxEnergyStored);
        nbt.putInt(NBTKeys.MinerEnergy.ENERGY_STORED.name(), this.energyStored);

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.maxEnergyStored = nbt.getInt(NBTKeys.MinerEnergy.MAX_ENERGY.name());
        this.energyStored = nbt.getInt(NBTKeys.MinerEnergy.ENERGY_STORED.name());
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return this.canReceive() ? this.addEnergy(Math.min(this.maxIncomingEnergy, maxReceive)) : 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return this.canExtract() ? this.removeEnergy(Math.min(this.maxIncomingEnergy, maxExtract)) : 0;
    }

    @Override
    public int getEnergyStored() {
        return this.energyStored;
    }

    @Override
    public int getMaxEnergyStored() {
        return this.maxEnergyStored;
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return true;
    }

    private int addEnergy(int energy){
        int availableCapacity = this.maxEnergyStored - energyStored;
        int e = Math.min(availableCapacity, energy);
        this.energyStored += e;
        return e;
    }

    private int removeEnergy(int energy){
        int e = Math.min(this.energyStored, energy);
        this.energyStored -= e;
        return e;
    }
}
