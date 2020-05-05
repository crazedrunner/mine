package com.crazedrunner.modularminer.common.tileentity;

import com.crazedrunner.modularminer.common.tileentity.base.MachineTileEntity;
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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;

public class MinerTileEntity extends MachineTileEntity {
    private static final String BLOCK_TO_MINE_POS_TAG = "BLOCK_TO_MINE_POS";
    private static final String MINER_INDEX_TAG = "MINER_INDEX";
    private BlockPos blockToMinePos;
    // TODO: Set base speed as a config parameter
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
        if(world != null && !world.isRemote){
            if(!initialized){
                init();
            }
            if(canOperate()){
                LOGGER.debug("canOperate fired!");
                BlockState blockState = world.getBlockState(blockToMinePos);
                if(blockState.isSolid()){
                    LOGGER.debug("Current tick count: " + tick);
                    HashSet<ResourceLocation> blockTags = new HashSet<>(blockState.getBlock().getTags());
                    if(isOperationFinished()){
                        destroyBlock(blockState, true);
                        blockToMinePos = getNextBlockPosition();
                    }
                }
                else{
                    blockToMinePos = getNextBlockPosition();
                }
                super.tick();
            }
        }
    }

    private void init() {
        if(world != null) {
            initialized = true;
            currentChunk = world.getChunkAt(pos).getPos();
            blockToMinePos = getNextBlockPosition();
            setEnergyPerTick(10);
            energy.setEnergy(100000);
        }
    }

    private int getTicksToMine(BlockPos currentBlock){
        if(world != null && currentBlock != null){
            BlockState blockState = world.getBlockState(currentBlock);
            if(blockState.isSolid()){
                int hardness = (int) Math.floor(blockState.getBlockHardness(world, currentBlock));
                int timeToMine = this.baseSpeed * hardness;
                return Math.max(1, timeToMine);
            }
        }
        return 0;
    }


    private boolean destroyBlock(BlockState blockState, boolean dropBlock){
        if(world != null) {
            world.playEvent(2001, blockToMinePos, Block.getStateId(blockState));
            if (dropBlock) {
                TileEntity tileEntity = blockState.hasTileEntity() ? world.getTileEntity(blockToMinePos) : null;
                Block.spawnDrops(blockState, world, this.pos.add(0, 2, 0), tileEntity, null, ItemStack.EMPTY);
            }
            return world.removeBlock(blockToMinePos, false);
        }
        return false;
    }

    private BlockPos getNextBlockPosition() {
        assert world != null;

        int x = minerIndex % 16;
        int y = pos.getY() - 1 - (int) (minerIndex / (16 * 16));
        int z = (minerIndex / 16) % 16;

        BlockPos nextBlockPos = world.getChunkAt(pos).getPos().getBlock(x,y, z);
//        LOGGER.debug("New Block Position to mine: "
//                + Integer.toString(nextBlockPos.getX()) + ", "
//                + Integer.toString(nextBlockPos.getY()) + ", "
//                + Integer.toString(nextBlockPos.getZ()));

        minerIndex++;

        resetTick();
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
