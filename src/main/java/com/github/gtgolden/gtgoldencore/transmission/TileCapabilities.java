package com.github.gtgolden.gtgoldencore.transmission;

import java.util.HashMap;

public class TileCapabilities {
    private final Connection[] connections;

    private TileCapabilities(HashMap<ConnectionType, Connection> connections) {
        this.connections = connections.values()
                .toArray(new Connection[ConnectionType.values().length]);
    }

    public boolean checkConnectionCapability(ConnectionType type) {
        return connections[type.ordinal()] != null;
    }

    public Connection getConnectionCapability(ConnectionType type) {
        return checkConnectionCapability(type) ? connections[type.ordinal()] : null;
    }

    public static class Builder {
        private HashMap<ConnectionType, Connection> connections = new HashMap<>();
        public TileCapabilities build() { return new TileCapabilities(connections); }
        public Builder addConnectionCapability(ConnectionType type, Connection connection) {
            connections.put(type, connection);
            return this;
        }
    }

}
