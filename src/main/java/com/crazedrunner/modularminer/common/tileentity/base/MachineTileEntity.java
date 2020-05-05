package com.crazedrunner.modularminer.common.tileentity.base;

import com.crazedrunner.modularminer.common.capability.MinerEnergy;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MachineTileEntity extends ModularDeviceEntity {
    private static final String TICK_TAG = "tick";
    private static final String ENERGY_TAG = "energy";
    private static final String ENERGY_PER_TICK_TAG = "energy_per_tick";
    private static final String TICKS_TO_COMPLETE_TAG = "ticks_to_complete";

    private int ticksToComplete = 0;
    private int energyPerTick = 0;

    private boolean initialized =  false;

    public MinerEnergy energy = new MinerEnergy(100000);
    protected final LazyOptional<MinerEnergy> energyCapabilityExternal = LazyOptional.of(() -> this.energy);
    protected final static Logger LOGGER = LogManager.getLogger();

    public MachineTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public void tick() {
        if (world != null && !world.isRemote()) {
            if (canOperate()) {
                if (tick >= ticksToComplete) {
                    resetTick();
                }
                energy.extractEnergy(energyPerTick, false);
            }
        }
        super.tick();
    }

    public boolean canOperate() {
        return energy != null &&
                energy.extractEnergy(energyPerTick, true) >= energyPerTick;
    }

    @Override
    public void read(@Nonnull final CompoundNBT nbt) {
        super.read(nbt);
        tick = nbt.getInt(TICK_TAG);
        energy.setEnergy(nbt.getInt(ENERGY_TAG));
        energyPerTick = nbt.getInt(ENERGY_PER_TICK_TAG);
        initialized = true;
    }

    @Override
    @Nonnull
    public CompoundNBT write(final CompoundNBT nbt) {
        super.write(nbt);
         nbt.putInt(TICK_TAG, tick);
        nbt.putInt(ENERGY_TAG, energy.getEnergyStored());
        nbt.putInt(ENERGY_PER_TICK_TAG, energyPerTick);
        return nbt;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable final Direction side) {
        if (cap == CapabilityEnergy.ENERGY) {
            return energyCapabilityExternal.cast();
        }
        return super.getCapability(cap, side);
    }

    public boolean isOperationFinished() {
        return tick >= ticksToComplete;
    }

    public int getTicksToComplete() {
        return ticksToComplete;
    }

    public void setTicksToComplete(int ticksToComplete) {
        LOGGER.debug("Time to complete set to: " + ticksToComplete);
        if (ticksToComplete >= 0) {
            this.ticksToComplete = ticksToComplete;
        }
    }

    public int getEnergyPerTick() {
        return energyPerTick;
    }

    public void setEnergyPerTick(int energyPerTick) {
        this.energyPerTick = Math.max(energyPerTick, 0);
    }
}