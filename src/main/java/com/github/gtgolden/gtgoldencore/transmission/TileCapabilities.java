package com.github.gtgolden.gtgoldencore.transmission;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;

public class TileCapabilities {
    private final EnumMap<ConnectionType, Connection> connections;

    private TileCapabilities(@NotNull EnumMap<ConnectionType, Connection> connections) {
        this.connections = connections;
    }

    public boolean hasConnectionCapability(@NotNull ConnectionType type) {
        return connections.get(type) != null;
    }

    public @Nullable Connection getConnectionCapability(@NotNull ConnectionType type) {
        return connections.get(type);
    }

    public static class Builder {
        private final EnumMap<ConnectionType, Connection> connections = new EnumMap<>(ConnectionType.class);
        public TileCapabilities build() { return new TileCapabilities(connections); }
        public Builder addConnectionCapability(@NotNull ConnectionType type, @NotNull Connection connection) {
            connections.put(type, connection);
            return this;
        }
    }

}
