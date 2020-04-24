package com.crazedrunner.modularminer.common;


import com.crazedrunner.modularminer.util.RegistryHandler;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("modularminer")
public final class ModularMiner{

    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "modularminer";

    public ModularMiner() {
        LOGGER.info("Fired ModularMiner class contructor.");

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        RegistryHandler.init();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {}

    private void doClientStuff(final FMLClientSetupEvent event) {}

    public static final ItemGroup TAB = new ItemGroup("modularMiner"){
        @Override
        public ItemStack createIcon(){
            return new ItemStack(RegistryHandler.BORE.get());
        }
    };
}