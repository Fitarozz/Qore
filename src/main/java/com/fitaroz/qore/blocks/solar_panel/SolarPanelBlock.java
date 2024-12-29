package com.fitaroz.qore.blocks.solar_panel;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;


import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

import com.fitaroz.qore.blocks.solar_panel.SolarPanelBlockEntity;
import static com.fitaroz.qore.Utils.humanReadableNumberNoUnit;

@ParametersAreNonnullByDefault
public class SolarPanelBlock extends Block implements EntityBlock {

    private static final VoxelShape AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

    public SolarPanelBlock() {
        super(Properties.of().instabreak().pushReaction(PushReaction.DESTROY).sound(SoundType.METAL).explosionResistance(3600000.0F).lightLevel(
                (state) -> state.getValue(BlockStateProperties.POWERED) ? 15 : 0
        ).noOcclusion());
    }

    @NotNull
    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SolarPanelBlockEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide) return null;
        return (lvl, pos, st, be) -> {
            if (be instanceof SolarPanelBlockEntity solar) {
                solar.tickServer();
            }
        };
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(BlockStateProperties.POWERED, false)
                .setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.POWERED, BlockStateProperties.HORIZONTAL_FACING);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
        return AABB;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> componentList, TooltipFlag tooltipFlag) {
        HolderLookup.Provider lookupProvider = context.registries();
        if (lookupProvider != null) {
            componentList.add(Component.empty());
            componentList.add(Component.translatable("tooltip.solar_panels.capacity")
                .append(Component.literal(": 10000 FE"))
                .withStyle(ChatFormatting.BLUE));
            componentList.add(Component.translatable("tooltip.solar_panels.generation")
                .append(Component.literal(": 100 FE/t"))
                .withStyle(ChatFormatting.BLUE));
            componentList.add(Component.translatable("tooltip.solar_panels.transfer")
                .append(Component.literal(": 1000 FE/t"))
                .withStyle(ChatFormatting.BLUE));
        }
    }
}
