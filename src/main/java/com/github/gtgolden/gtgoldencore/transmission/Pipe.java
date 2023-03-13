package com.github.gtgolden.gtgoldencore.transmission;

import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.level.BlockView;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.Vec3i;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;
import net.modificationstation.stationapi.api.world.BlockStateView;

import java.util.Random;

public abstract class Pipe extends TemplateBlockBase implements HasCapabilities {
    public static final BooleanProperty NORTH = BooleanProperty.of("north");
    public static final BooleanProperty SOUTH = BooleanProperty.of("south");
    public static final BooleanProperty EAST = BooleanProperty.of("east");
    public static final BooleanProperty WEST = BooleanProperty.of("west");
    public static final BooleanProperty UP = BooleanProperty.of("up");
    public static final BooleanProperty DOWN = BooleanProperty.of("down");

    static final float MIN_SIZE = .25f;
    static final float MAX_SIZE = .75f;

    private final TileCapabilities capabilities;

    public Pipe(Identifier identifier, TileCapabilities capabilities) {
        super(identifier, Material.STONE);
        this.capabilities = capabilities;

        this.setHardness(0.8f);
        setTranslationKey(identifier.toString());
        setDefaultState(getStateManager().getDefaultState()
                .with(NORTH, false)
                .with(SOUTH, false)
                .with(EAST, false)
                .with(WEST, false)
                .with(UP, false)
                .with(DOWN, false));
    }

    @Override
    public TileCapabilities getCapabilities() {
        return capabilities;
    }

    @Override
    public void appendProperties(StateManager.Builder<BlockBase, BlockState> builder) {
        builder.add(NORTH, SOUTH, EAST, WEST, UP, DOWN);
    }

    @Override
    public void onBlockPlaced(Level level, int x, int y, int z) {
        super.onBlockPlaced(level, x, y, z);
        updatePipeModel(level, x, y, z);
        updateNearby(level, new Vec3i(x, y, z));
    }

    @Override
    public void onBlockRemoved(Level level, int x, int y, int z) {
        super.onBlockRemoved(level, x, y, z);
        updateNearby(level, new Vec3i(x, y, z));
    }

    @Override
    public void onAdjacentBlockUpdate(Level level, int x, int y, int z, int id) {
        super.onAdjacentBlockUpdate(level, x, y, z, id);
        updatePipeModel(level, x, y, z);
        updateNearby(level, new Vec3i(x, y, z));
//        level.method_216(x, y, z, id, 1);
    }

    protected void updateNearby(Level level, Vec3i pos) {}

    @Override
    public void onScheduledTick(Level level, int x, int y, int z, Random rand) {
        updatePipeModel(level, x, y, z);
    }

    public void updatePipeModel(Level level, int x, int y, int z) {
        level.setBlockState(x, y, z, getDefaultState()
                .with(NORTH, checkConnection(level, x - 1, y, z, 5))
                .with(SOUTH, checkConnection(level, x + 1, y, z, 4))
                .with(EAST, checkConnection(level, x, y, z - 1, 3))
                .with(WEST, checkConnection(level, x, y, z + 1, 2))
                .with(UP, checkConnection(level, x, y + 1, z, 0))
                .with(DOWN, checkConnection(level, x, y - 1, z, 1)));

        updateBoundingBox(level, x, y, z);
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public Box getCollisionShape(Level level, int x, int y, int z) {
        return Box.create(x, y, z, x + 1, y + 1, z + 1);
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public void updateBoundingBox(BlockView tileView, int x, int y, int z) {
        BlockState blockState = ((BlockStateView)tileView).getBlockState(x, y, z);

        float maxX = blockState.get(SOUTH) ? 1 : MAX_SIZE;
        float minX = blockState.get(NORTH) ? 0 : MIN_SIZE;
        float maxY = blockState.get(UP) ? 1 : MAX_SIZE;
        float minY = blockState.get(DOWN) ? 0 : MIN_SIZE;
        float maxZ = blockState.get(WEST) ? 1 : MAX_SIZE;
        float minZ = blockState.get(EAST) ? 0 : MIN_SIZE;

        this.setBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
    }

    protected abstract boolean checkConnection(BlockView tileView, int x, int y, int z, int side);
}
