package com.github.gtgolden.gtgoldencore;

import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.ModID;
import net.modificationstation.stationapi.api.util.Null;
import org.apache.logging.log4j.Logger;

public class GTGoldenCore {
    @Entrypoint.ModID
    public static final ModID MOD_ID = Null.get();
    @Entrypoint.Logger("GT-Golden-Core")
    public static final Logger LOGGER = Null.get();
}
