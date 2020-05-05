package com.crazedrunner.modularminer.common.util.registries;

import com.crazedrunner.modularminer.common.ModularMiner;
import com.crazedrunner.modularminer.common.blockItems.BlockItemBase;
import com.crazedrunner.modularminer.common.blocks.CreativeBattery;
import com.crazedrunner.modularminer.common.blocks.Miner;
import com.crazedrunner.modularminer.common.blocks.MinerController;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegistryHandler {
    public static final DeferredRegister<Item> BLOCK_ITEMS =
            new DeferredRegister<>(ForgeRegistries.ITEMS, ModularMiner.MOD_ID);

    public static final DeferredRegister<Block> BLOCKS =
            new DeferredRegister<>(ForgeRegistries.BLOCKS, ModularMiner.MOD_ID);

    public static void init(){
        BLOCK_ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<Block> MINER_CONTROLLER =
            BLOCKS.register("miner_controller", MinerController::new);

    public static final RegistryObject<Block> MINER =
            BLOCKS.register("miner", Miner::new);

    public static final RegistryObject<Block> CREATIVE_BATTERY =
            BLOCKS.register("creative_battery", CreativeBattery::new);

    public static final RegistryObject<Item> MINER_CONTROLLER_ITEM =
            BLOCK_ITEMS.register("miner_controller",
                    () -> new BlockItemBase(MINER_CONTROLLER.get()));

    public static final RegistryObject<Item> MINER_ITEM =
            BLOCK_ITEMS.register("miner",
                    () -> new BlockItemBase(MINER.get()));

    public static final RegistryObject<Item> CREATIVE_BATTERY_ITEM =
            BLOCK_ITEMS.register("creative_battery",
                    ()-> new BlockItemBase(CREATIVE_BATTERY.get()));
}
