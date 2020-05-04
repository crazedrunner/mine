package com.crazedrunner.modularminer.common.tileentity;

import com.crazedrunner.modularminer.common.util.registries.TileEntityRegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.extensions.IForgeBlockState;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class MinerTileEntity extends MachineTileEntity{
    private static final String BLOCK_TO_MINE_POS_TAG = "BLOCK_TO_MINE_POS";
    private static final String MINER_INDEX_TAG = "MINER_INDEX";
    private BlockPos blockToMinePos;
    public int baseSpeed = 10;
    private int minerIndex = 0;
    private boolean initialized = false;

    private ChunkPos currentChunk;

    private static final Logger LOGGER = LogManager.getLogger();

    public MinerTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public MinerTileEntity() {
        this(TileEntityRegistryHandler.MINER_TILE_ENTITY.get());
    }

    @Override
    public void tick() {
        if(world == null || world.isRemote ){
            return;
        }

        if(!initialized){
            init();
        }

        super.tick();

        if (!canOperate()){
            return;
        }

        BlockState blockstate = world.getBlockState(blockToMinePos);

        if (!blockstate.isSolid()){
            LOGGER.debug("Block is not solid at: " + blockToMinePos.toString());
            blockToMinePos = getNextBlockPosition();
            return;
        }

        HashSet< ResourceLocation> tags = new HashSet<>(blockstate.getBlock().getTags());

        LOGGER.debug("Tags: " + tags.toString());
        if(isOperationFinished()){
            destroyBlock(blockToMinePos, true, null);
            blockToMinePos = getNextBlockPosition();
        }
    }

    private void init() {
        initialized = true;
        currentChunk = world.getChunkAt(pos).getPos();
        blockToMinePos = getNextBlockPosition();
        setEnergyPerTick(10);
        energy.setEnergy(100000);
    }

    private int getTicksToMine(BlockPos currentBlock){
        if (world == null || currentBlock == null){
            return 0;
        }

        BlockState state = world.getBlockState(currentBlock);

        if(!state.isSolid()){
            return 0;
        }

        int timeToMine = (int) Math.floor(
                this.baseSpeed * state.getBlockHardness(world, currentBlock)
        );
        return Math.max(1, timeToMine);
    }


    private boolean destroyBlock(BlockPos blockPos, boolean dropBlock, @Nullable Entity entity){
        BlockState blockstate = world.getBlockState(blockPos);
        if (blockstate.isAir(world, blockPos)){
            return false;
        }
        IFluidState ifluidstate = world.getFluidState(pos);
        world.playEvent(2001, blockPos, Block.getStateId(blockstate));
        if (dropBlock){
            TileEntity tileEntity = blockstate.hasTileEntity() ? world.getTileEntity(blockPos) : null;
            Block.spawnDrops(blockstate, world, this.pos.add(0, 2, 0), tileEntity, entity, ItemStack.EMPTY);
        }
        return world.removeBlock(blockPos, false);
        //return world.setBlockState(blockPos, ifluidstate.getBlockState(), 3);


    }

    private BlockPos getNextBlockPosition() {
        assert world != null;

        int x = minerIndex % 16;
        int y = pos.getY() - 1 - (int) (minerIndex / (16 * 16));
        int z = (minerIndex / 16) % 16;

        BlockPos nextBlockPos = world.getChunkAt(pos).getPos().getBlock(x,y, z);
        LOGGER.debug("New Block Position to mine: "
                + Integer.toString(nextBlockPos.getX()) + ", "
                + Integer.toString(nextBlockPos.getY()) + ", "
                + Integer.toString(nextBlockPos.getZ()));

        minerIndex++;

        resetTickCount();
        setTicksToComplete(getTicksToMine(nextBlockPos));

        return nextBlockPos;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return super.getCapability(cap, side);
    }

    @Override
    @Nonnull
    public CompoundNBT write(CompoundNBT nbt){
        super.write(nbt);
        nbt.put(BLOCK_TO_MINE_POS_TAG, NBTUtil.writeBlockPos(blockToMinePos));
        nbt.putInt(MINER_INDEX_TAG, minerIndex);
        return super.write(nbt);
    }

    @Override
    public void read(@Nonnull CompoundNBT nbt){
        super.read(nbt);
        minerIndex = nbt.getInt(MINER_INDEX_TAG);
        blockToMinePos = NBTUtil.readBlockPos(nbt.getCompound(BLOCK_TO_MINE_POS_TAG));
        this.initialized = true;
    }
}
