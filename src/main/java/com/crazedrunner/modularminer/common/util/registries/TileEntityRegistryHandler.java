package com.crazedrunner.modularminer.common.util.registries;

import com.crazedrunner.modularminer.common.ModularMiner;
import com.crazedrunner.modularminer.common.tileentity.CreativeBatteryEntity;
import com.crazedrunner.modularminer.common.tileentity.MinerControllerEntity;
import com.crazedrunner.modularminer.common.tileentity.MinerTileEntity;
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
                            .create(MinerTileEntity::new, BlockRegistryHandler.MINER.get())
                            .build(null);
                }
        );

    public static final RegistryObject<TileEntityType<?>> CREATIVE_BATTERY_ENTITY = TILE_ENTITY_TYPES
            .register("creative_battery",
                    () -> {
                        return TileEntityType.Builder
                                .create(CreativeBatteryEntity::new,
                                        BlockRegistryHandler.CREATIVE_BATTERY.get())
                                .build(null);
                    });

    public static final RegistryObject<TileEntityType<?>> MINER_CONTROLLER_ENTITY = TILE_ENTITY_TYPES
            .register("miner_controller_entity",
                    () -> {
                return TileEntityType.Builder.create(MinerControllerEntity::new,
                        BlockRegistryHandler.MINER_CONTROLLER.get()).build(null);
                    });
}
