package com.fitaroz.qore.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class ModularBlock extends Block {
     public ModularBlock(ResourceLocation location) {
        this(BlockBehaviour.Properties.of().sound(SoundType.STONE)); // Exemple de propriétés, modifiez selon vos besoins
    }
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;

    // Propriétés pour les connexions diagonales
    public static final BooleanProperty NORTH_EAST = BooleanProperty.create("north_east");
    public static final BooleanProperty NORTH_WEST = BooleanProperty.create("north_west");
    public static final BooleanProperty SOUTH_EAST = BooleanProperty.create("south_east");
    public static final BooleanProperty SOUTH_WEST = BooleanProperty.create("south_west");

    public ModularBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
            .setValue(NORTH, false).setValue(SOUTH, false)
            .setValue(EAST, false).setValue(WEST, false)
            .setValue(UP, false).setValue(DOWN, false)
            .setValue(NORTH_EAST, false).setValue(NORTH_WEST, false)
            .setValue(SOUTH_EAST, false).setValue(SOUTH_WEST, false));
    }

    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, SOUTH, EAST, WEST, UP, DOWN, NORTH_EAST, NORTH_WEST, SOUTH_EAST, SOUTH_WEST);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        return this.defaultBlockState()
            .setValue(NORTH, this.shouldConnectTo(level, pos.relative(Direction.NORTH)))
            .setValue(SOUTH, this.shouldConnectTo(level, pos.relative(Direction.SOUTH)))
            .setValue(EAST, this.shouldConnectTo(level, pos.relative(Direction.EAST)))
            .setValue(WEST, this.shouldConnectTo(level, pos.relative(Direction.WEST)))
            .setValue(UP, this.shouldConnectTo(level, pos.relative(Direction.UP)))
            .setValue(DOWN, this.shouldConnectTo(level, pos.relative(Direction.DOWN)))
            .setValue(NORTH_EAST, this.shouldConnectTo(level, pos.relative(Direction.NORTH).relative(Direction.EAST)))
            .setValue(NORTH_WEST, this.shouldConnectTo(level, pos.relative(Direction.NORTH).relative(Direction.WEST)))
            .setValue(SOUTH_EAST, this.shouldConnectTo(level, pos.relative(Direction.SOUTH).relative(Direction.EAST)))
            .setValue(SOUTH_WEST, this.shouldConnectTo(level, pos.relative(Direction.SOUTH).relative(Direction.WEST)));
    }

    private boolean shouldConnectTo(BlockGetter level, BlockPos pos) {
        // Vérifie si le bloc adjacent est également un ModularBlock
        return level.getBlockState(pos).getBlock() instanceof ModularBlock;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        // Met à jour l'état de connexion pour les directions cardinales et diagonales
        return state.setValue(NORTH, this.shouldConnectTo(world, pos.relative(Direction.NORTH)))
                    .setValue(SOUTH, this.shouldConnectTo(world, pos.relative(Direction.SOUTH)))
                    .setValue(EAST, this.shouldConnectTo(world, pos.relative(Direction.EAST)))
                    .setValue(WEST, this.shouldConnectTo(world, pos.relative(Direction.WEST)))
                    .setValue(UP, this.shouldConnectTo(world, pos.relative(Direction.UP)))
                    .setValue(DOWN, this.shouldConnectTo(world, pos.relative(Direction.DOWN)))
                    .setValue(NORTH_EAST, this.shouldConnectTo(world, pos.relative(Direction.NORTH).relative(Direction.EAST)))
                    .setValue(NORTH_WEST, this.shouldConnectTo(world, pos.relative(Direction.NORTH).relative(Direction.WEST)))
                    .setValue(SOUTH_EAST, this.shouldConnectTo(world, pos.relative(Direction.SOUTH).relative(Direction.EAST)))
                    .setValue(SOUTH_WEST, this.shouldConnectTo(world, pos.relative(Direction.SOUTH).relative(Direction.WEST)));
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 15;
    }
}