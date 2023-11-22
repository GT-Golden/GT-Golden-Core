package io.github.gtgolden.gttest.block;

import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Living;
import net.minecraft.level.Level;
import net.minecraft.tileentity.TileEntityBase;
import net.minecraft.util.maths.MathHelper;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.state.property.EnumProperty;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;

public class Generator extends TemplateBlockWithEntity {
    public static final EnumProperty<Direction> FACING_PROPERTY = EnumProperty.of("facing", Direction.class);
    public static final BooleanProperty LIT_PROPERTY = BooleanProperty.of("lit");

    public Generator(Identifier identifier, Material material) {
        super(identifier, material);
        setTranslationKey(identifier.toString());
        setDefaultState(getStateManager().getDefaultState().with(FACING_PROPERTY, Direction.NORTH).with(LIT_PROPERTY, false));
    }

    @Override
    public void appendProperties(StateManager.Builder<BlockBase, BlockState> builder) {
        builder.add(FACING_PROPERTY);
        builder.add(LIT_PROPERTY);
    }

    @Override
    public void afterPlaced(Level level, int x, int y, int z, Living living) {
        super.afterPlaced(level, x, y, z, living);
        int direction = MathHelper.floor((double) (living.yaw * 4.0F / 360.0F) + 0.5D) & 3;
        level.setBlockState(x, y, z, getDefaultState().with(FACING_PROPERTY,
                switch (direction) {
                    case 0 -> Direction.EAST;
                    case 1 -> Direction.SOUTH;
                    case 2 -> Direction.WEST;
                    default -> Direction.NORTH;
                }));
    }

    @Override
    protected TileEntityBase createTileEntity() {
        return new GeneratorEntity();
    }
}
