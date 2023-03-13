package com.github.gtgolden.gtgoldencore.transmission;

import com.github.gtgolden.gtgoldencore.transmission.power.TilePowerStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;

public class TileCapabilities {
    private final EnumMap<ConnectionType, Connection> connections;
    private final EnumMap<StorageType, Storage> storage;

    private boolean dirty = true;

    private TileCapabilities(@NotNull EnumMap<ConnectionType, Connection> connections, @NotNull EnumMap<StorageType, Storage> storage) {
        this.connections = connections;
        this.storage = storage;
    }

    public boolean hasConnectionCapability(@NotNull ConnectionType type) {
        return connections.get(type) != null;
    }

    public @Nullable Connection getConnectionCapability(@NotNull ConnectionType type) {
        return connections.get(type);
    }

    public boolean hasStorage(@NotNull StorageType type) {
        return storage.get(type) != null;
    }

    public @Nullable Storage getStorage(@NotNull StorageType type) {
        return storage.get(type);
    }

    public void markDirty() {
        dirty = true;
    }

    public static class Builder {
        private final EnumMap<ConnectionType, Connection> connectionsData = new EnumMap<>(ConnectionType.class);
        private final EnumMap<StorageType, Storage> storageData = new EnumMap<>(StorageType.class);
        public TileCapabilities build() { return new TileCapabilities(connectionsData, storageData); }

        public Builder addConnectionCapability(@NotNull ConnectionType type, @NotNull Connection connection) {
            connectionsData.put(type, connection);
            return this;
        }

        public Builder addStorage(@NotNull StorageType type, @NotNull Storage storage) {
            storageData.put(type, storage);
            return this;
        }

        public Builder setupPower(int maxPower, int defaultPower) {
            addConnectionCapability(ConnectionType.power, new Connection() {});
            addStorage(StorageType.power, new TilePowerStorage.Builder()
                    .setMaxPower(maxPower)
                    .setDefaultPower(defaultPower)
                    .build());
            return this;
        }

        public Builder setupPower(int maxPower) {
            return setupPower(maxPower, 0);
        }

        public Builder setupInventory(int inventorySize) {
            addConnectionCapability(ConnectionType.item, new Connection() {});
//            addStorage(TileStorageType.item, new Tile)
            return this;
        }
    }
}
