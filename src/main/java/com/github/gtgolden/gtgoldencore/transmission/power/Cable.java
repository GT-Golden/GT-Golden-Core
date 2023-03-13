package com.github.gtgolden.gtgoldencore.transmission.power;

import com.github.gtgolden.gtgoldencore.transmission.Connection;
import com.github.gtgolden.gtgoldencore.transmission.ConnectionType;
import com.github.gtgolden.gtgoldencore.transmission.Pipe;
import com.github.gtgolden.gtgoldencore.transmission.TileCapabilities;
import com.github.gtgolden.gtgoldencore.utils.WorldUtils;
import net.minecraft.level.BlockView;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Vec3i;
import net.modificationstation.stationapi.api.registry.Identifier;

import java.util.Objects;

public class Cable extends Pipe {
    public Cable(Identifier identifier) {
        super(identifier, new TileCapabilities.Builder()
                .addConnectionCapability(ConnectionType.power, new Connection() {}).build());
    }

    @Override
    protected void updateNearby(Level level, Vec3i pos) {
        PowerUtils.updateConnectedMachines(level, pos);
    }

    @Override
    protected boolean checkConnection(BlockView tileView, int x, int y, int z, int side) {
        TileCapabilities tileCapabilities = WorldUtils.getBlockCapabilities(tileView, x, y, z);

        return tileCapabilities != null &&
                Objects.requireNonNull(tileCapabilities.getConnectionCapability(ConnectionType.power)).canConnect(tileView, x, y, z, side);
    }
}
