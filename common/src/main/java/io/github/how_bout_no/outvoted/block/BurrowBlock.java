package io.github.how_bout_no.outvoted.block;

import io.github.how_bout_no.outvoted.block.entity.BurrowBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.entity.vehicle.TntMinecartEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BurrowBlock extends BlockWithEntity {
    public static final DirectionProperty FACING;

    public BurrowBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    public BlockEntity createBlockEntity(BlockView world) {
        return new BurrowBlockEntity();
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient && player.isCreative() && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS)) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof BurrowBlockEntity) {
                BurrowBlockEntity BurrowBlockEntity = (BurrowBlockEntity) blockEntity;
                ItemStack itemStack = new ItemStack(this);
                boolean bl = !BurrowBlockEntity.hasNoMeerkats();
                if (!bl) {
                    return;
                }

                CompoundTag compoundTag2;
                if (bl) {
                    compoundTag2 = new CompoundTag();
                    compoundTag2.put("Bees", BurrowBlockEntity.getMeerkats());
                    itemStack.putSubTag("BlockEntityTag", compoundTag2);
                }

                compoundTag2 = new CompoundTag();
                itemStack.putSubTag("BlockStateTag", compoundTag2);
                ItemEntity itemEntity = new ItemEntity(world, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), itemStack);
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
            }
        }

        super.onBreak(world, pos, state, player);
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, net.minecraft.loot.context.LootContext.Builder builder) {
        Entity entity = (Entity) builder.getNullable(LootContextParameters.THIS_ENTITY);
        if (entity instanceof TntEntity || entity instanceof CreeperEntity || entity instanceof WitherSkullEntity || entity instanceof WitherEntity || entity instanceof TntMinecartEntity) {
            BlockEntity blockEntity = (BlockEntity) builder.getNullable(LootContextParameters.BLOCK_ENTITY);
            if (blockEntity instanceof BurrowBlockEntity) {
                BurrowBlockEntity BurrowBlockEntity = (BurrowBlockEntity) blockEntity;
//                BurrowBlockEntity.angerBees((PlayerEntity)null, state, BurrowBlockEntity.BeeState.EMERGENCY);
            }
        }

        return super.getDroppedStacks(state, builder);
    }

    private void refreshOpening(BlockPos pos, WorldAccess world) {
        Direction opening = (Direction) world.getBlockState(pos).get(FACING);
        if (opening != null)
            if (world.getBlockState(pos.offset(opening)).isAir())
                return;
        for (Direction direction1 : Direction.values()) {
            if (direction1 != Direction.DOWN) {
                if (world.getBlockState(pos.offset(direction1)).isAir()) {
                    opening = direction1;
                    this.rotate(world.getBlockState(pos), BlockRotation.NONE);
                    return;
                }
            }
        }
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!state.isOf(oldState.getBlock())) {
            refreshOpening(pos, world);
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        refreshOpening(pos, world);

        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        System.out.println("yo");
        return (BlockState) this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState) state.with(FACING, rotation.rotate((Direction) state.get(FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation((Direction) state.get(FACING)));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    static {
        FACING = FacingBlock.FACING;
    }
}
