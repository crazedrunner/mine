package com.crazedrunner.modularminer.common.capability;

import net.minecraftforge.energy.EnergyStorage;


public class MinerEnergy extends EnergyStorage {
    /*
        Special Thanks to Bektor for his post:
            https://www.minecraftforge.net/forum/topic/60119-112-using-forge-energy-fe-help-still-needed/
        Special Thanks to Cadiboo for his Example Mod;
            https://github.com/Cadiboo/Example-Mod/
     */

    public MinerEnergy(final int capacity){
        super(capacity);
    }

    public MinerEnergy(final int capacity, final int maxTransfer){
        super(capacity, maxTransfer);
    }

    public MinerEnergy(final int capacity, final int maxReceive, final int maxExtract){
        super(capacity, maxReceive, maxExtract);
    }

    public MinerEnergy(final int capacity, final int maxReceive, final int maxExtract, final int energy){
        super(capacity, maxReceive, maxExtract, energy);
    }

    public int setEnergy(final int maxSet){
        return this.energy = Math.min(this.capacity, maxSet);
    }
}
