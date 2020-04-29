package com.crazedrunner.modularminer.common.tileentity;

import com.crazedrunner.modularminer.common.capability.MinerEnergy;
import com.crazedrunner.modularminer.common.util.registries.TileEntityRegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MinerTileEntity extends TileEntity implements ITickableTileEntity {
    public int x = 0;
    public int y = 0;
    public int z = 0;
    public int tick = 0;
    public int speed = 40;

    private boolean activated = true;
    private boolean initialized = false;

    private MinerEnergy energy;
    private final LazyOptional<MinerEnergy> energyCapabilityExternal = LazyOptional.of(()->this.energy);
    private static final Logger LOGGER = LogManager.getLogger();

    public MinerTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        this.energy = new MinerEnergy(0, 1000, 100);
    }

    public MinerTileEntity() {
        this(TileEntityRegistryHandler.MINER_TILE_ENTITY.get());
    }

    @Override
    public void tick() {
        if(!initialized){
            init();
        }
        tick++;
        if (tick == this.speed && this.isRunning()){
            tick = 0;
            if (y > 5){
                execute();
            }
        }

    }

    private void init() {
        initialized = true;
        x = this.pos.getX();
        y = this.pos.getY() - 1;
        z = this.pos.getZ();
    }

    public void execute(){
        int index = 0;

        BlockPos blockRemovedPos = new BlockPos(this.x, this.y, this.z);

        assert this.world != null;
        BlockState blockState = this.world.getBlockState(blockRemovedPos);

        Block blockRemoved = blockState.getBlock();
        int harvestLevel = blockRemoved.getHarvestLevel(blockState);

        if (harvestLevel < 2) {
            LOGGER.info("Harvest Level: " + Integer.toString(harvestLevel));
            destroyBlock(blockRemovedPos, true, null);
        }
        this.y--;
    }

    private boolean destroyBlock(BlockPos pos, boolean dropBlock, @Nullable Entity entity){
        BlockState blockstate = world.getBlockState(pos);
        if (blockstate.isAir(world, pos)){
            return false;
        }
        IFluidState ifluidstate = world.getFluidState(pos);
        world.playEvent(2001, pos, Block.getStateId(blockstate));
        if (dropBlock){
            TileEntity tileEntity = blockstate.hasTileEntity() ? world.getTileEntity(pos) : null;
            Block.spawnDrops(blockstate, world, this.pos.add(0, 2, 0), tileEntity, entity, ItemStack.EMPTY);
        }
        return world.setBlockState(pos, ifluidstate.getBlockState(), 3);
    }

//    @Nonnull
//    @Override
//    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
//        if (this.energy == null){
//            return LazyOptional.empty();
//        }
//        return this.energyCapabilityExternal;
//    }

    @Override
    public CompoundNBT write(CompoundNBT compound){
        compound.put("init_values", this.toNBT());
        return super.write(compound);
    }

    @Override
    public void read(CompoundNBT compound){
        super.read(compound);
        CompoundNBT initValues = compound.getCompound("init_values");

        if (initValues != null){
            this.x = initValues.getInt("x");
            this.y = initValues.getInt("y");
            this.z = initValues.getInt("z");
            this.tick = 0;
            this.initialized = true;
        }
        else{
            init();
        }
    }
    private CompoundNBT toNBT(){
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("x", this.x);
        nbt.putInt("y", this.y);
        nbt.putInt("x", this.z);
        return nbt;
    }

    public boolean isRunning(){
        return this.activated;
    }
}
