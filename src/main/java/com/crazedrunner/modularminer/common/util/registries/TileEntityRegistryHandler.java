package com.crazedrunner.modularminer.common.util.registries;

import com.crazedrunner.modularminer.common.ModularMiner;
import com.crazedrunner.modularminer.common.tileentity.MinerTileEntity;
import com.crazedrunner.modularminer.common.util.registries.BlockRegistryHandler;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityRegistryHandler {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES =
            new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, ModularMiner.MOD_ID);

    public static void init(){
        TILE_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<TileEntityType<?>> MINER_TILE_ENTITY = TILE_ENTITY_TYPES
        .register("miner",
                () -> {
                    return TileEntityType.Builder
                            .create(() -> new MinerTileEntity(), BlockRegistryHandler.MINER.get())
                            .build(null);
                }
        );

}
