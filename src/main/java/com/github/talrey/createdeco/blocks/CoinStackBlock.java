package com.github.talrey.createdeco.blocks;

import com.github.talrey.createdeco.Registration;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class CoinStackBlock extends Block {
  private static final VoxelShape[] SHAPE = {
    Block.box(
  0d, 0d, 0d,
  16, 2d, 16d
    ),
    Block.box(
  0d, 0d, 0d,
  16, 4d, 16d
    ),
    Block.box(
  0d, 0d, 0d,
  16, 6d, 16d
    ),
    Block.box(
  0d, 0d, 0d,
  16, 8d, 16d
    ),
    Block.box(
  0d, 0d, 0d,
  16, 10d, 16d
    ),
    Block.box(
  0d, 0d, 0d,
  16, 12d, 16d
    ),
    Block.box(
  0d, 0d, 0d,
  16, 14d, 16d
    ),
    Block.box(
  0d, 0d, 0d,
  16, 16d, 16d
    )
  };

  public CoinStackBlock (Properties properties) {
    super(properties);
    this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.LAYERS, 1));
  }

  @Override
  public VoxelShape getShape (BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
    return SHAPE[state.getValue(BlockStateProperties.LAYERS)-1];
  }

  @Override
  public BlockState updateShape (BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
    if (facing == Direction.DOWN && !canSupportCenter(worldIn, facingPos, Direction.UP)) return Blocks.AIR.defaultBlockState();
    return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
  }

  @Nullable
  @Override
  public BlockState getStateForPlacement (BlockPlaceContext ctx) {
    return defaultBlockState();
  }

  @Override
  protected void createBlockStateDefinition (StateDefinition.Builder<Block, BlockState> builder) { builder.add(BlockStateProperties.LAYERS); }

  @Override
  public ItemStack getCloneItemStack (BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
    String material = state.getBlock().getDescriptionId().replace("_coinstack_block", "");
    material = material.substring(material.lastIndexOf('.')+1); // remove createdeco.block.
    material = material.substring(0,1).toUpperCase() + material.substring(1); // capitalize
    return Registration.COINSTACK_ITEM.containsKey(material) ? Registration.COINSTACK_ITEM.get(material).asStack() : new ItemStack(Items.AIR);
  }
}
