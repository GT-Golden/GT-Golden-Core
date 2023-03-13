package com.github.gtgolden.gtgoldencore.events;

import com.github.gtgolden.gtgoldencore.machines.ChargerTileEntity;
import com.github.gtgolden.gtgoldencore.machines.TestPowerTileEntity;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.tileentity.TileEntityRegisterEvent;

import static com.github.gtgolden.gtgoldencore.GTGoldenCore.MOD_ID;

public class TileEntityListener {
    @EventListener
    public void registerTileEntities(TileEntityRegisterEvent event) {
        event.register(TestPowerTileEntity.class, MOD_ID.id("testPowerEntity").toString());
        event.register(ChargerTileEntity.class, MOD_ID.id("chargerEntity").toString());
    }
}
