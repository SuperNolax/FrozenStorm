package net.frozenblock.frozenstorm.block;

import net.frozenblock.frozenstorm.FrozenStorm;
import net.frozenblock.frozenstorm.entity.FrozenStormEntity;
import net.frozenblock.frozenstorm.registry.RegisterBlocks;
import net.frozenblock.frozenstorm.registry.RegisterEntities;
import net.frozenblock.frozenstorm.registry.RegisterItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.function.MaterialPredicate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;

public class FrozenStormSkullBlock extends SkullBlock {
    @Nullable
    private static BlockPattern frozenStormBossPattern;
    @Nullable
    private static BlockPattern frozenStormDispenserPattern;

    public FrozenStormSkullBlock(AbstractBlock.Settings settings) {
        super(FrozenStorm.FROZEN_SKULL_TYPE, settings);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof SkullBlockEntity) {
            onPlaced(world, pos, (SkullBlockEntity)blockEntity);
        }

    }

    public static void onPlaced(World world, BlockPos pos, SkullBlockEntity blockEntity) {
        if (!world.isClient) {
            BlockState blockState = blockEntity.getCachedState();
            boolean bl = blockState.isOf(RegisterBlocks.FROZEN_STORM_SKULL) || blockState.isOf(RegisterBlocks.FROZEN_STORM_WALL_SKULL);
            if (bl && pos.getY() >= world.getBottomY() && world.getDifficulty() != Difficulty.PEACEFUL) {
                BlockPattern blockPattern = getFrozenStormBossPattern();
                BlockPattern.Result result = blockPattern.searchAround(world, pos);
                if (result != null) {
                    for(int i = 0; i < blockPattern.getWidth(); ++i) {
                        for(int j = 0; j < blockPattern.getHeight(); ++j) {
                            CachedBlockPosition cachedBlockPosition = result.translate(i, j, 0);
                            world.setBlockState(cachedBlockPosition.getBlockPos(), Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
                            world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, cachedBlockPosition.getBlockPos(), Block.getRawIdFromState(cachedBlockPosition.getBlockState()));
                        }
                    }

                    FrozenStormEntity frozenStorm = RegisterEntities.FROZEN_STORM.create(world);
                    BlockPos blockPos = result.translate(1, 2, 0).getBlockPos();
                    assert frozenStorm != null;
                    frozenStorm.refreshPositionAndAngles(
                            (double)blockPos.getX() + 0.5,
                            (double)blockPos.getY() + 0.55,
                            (double)blockPos.getZ() + 0.5,
                            result.getForwards().getAxis() == Direction.Axis.X ? 0.0F : 90.0F,
                            0.0F
                    );
                    frozenStorm.bodyYaw = result.getForwards().getAxis() == Direction.Axis.X ? 0.0F : 90.0F;
                    frozenStorm.onSummoned();

                    for(ServerPlayerEntity serverPlayerEntity : world.getNonSpectatingEntities(ServerPlayerEntity.class, frozenStorm.getBoundingBox().expand(50.0))) {
                        Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity, frozenStorm);
                    }

                    world.spawnEntity(frozenStorm);

                    for(int k = 0; k < blockPattern.getWidth(); ++k) {
                        for(int l = 0; l < blockPattern.getHeight(); ++l) {
                            world.updateNeighbors(result.translate(k, l, 0).getBlockPos(), Blocks.AIR);
                        }
                    }

                }
            }
        }
    }

    public static boolean canDispense(World world, BlockPos pos, ItemStack stack) {
        if (stack.isOf(RegisterItems.FROZEN_STORM_SKULL) && pos.getY() >= world.getBottomY() + 2 && world.getDifficulty() != Difficulty.PEACEFUL && !world.isClient) {
            return getFrozenStormDispenserPattern().searchAround(world, pos) != null;
        } else {
            return false;
        }
    }

    private static BlockPattern getFrozenStormBossPattern() {
        if (frozenStormBossPattern == null) {
            frozenStormBossPattern = BlockPatternBuilder.start()
                    .aisle("^^^", "###", "~#~")
                    .where('#', pos -> pos.getBlockState().isIn(BlockTags.WITHER_SUMMON_BASE_BLOCKS))
                    .where(
                            '^',
                            CachedBlockPosition.matchesBlockState(
                                    BlockStatePredicate.forBlock(RegisterBlocks.FROZEN_STORM_SKULL).or(BlockStatePredicate.forBlock(RegisterBlocks.FROZEN_STORM_WALL_SKULL))
                            )
                    )
                    .where('~', CachedBlockPosition.matchesBlockState(MaterialPredicate.create(Material.AIR)))
                    .build();
        }

        return frozenStormBossPattern;
    }

    private static BlockPattern getFrozenStormDispenserPattern() {
        if (frozenStormDispenserPattern == null) {
            frozenStormDispenserPattern = BlockPatternBuilder.start()
                    .aisle("   ", "###", "~#~")
                    .where('#', pos -> pos.getBlockState().isIn(BlockTags.WITHER_SUMMON_BASE_BLOCKS))
                    .where('~', CachedBlockPosition.matchesBlockState(MaterialPredicate.create(Material.AIR)))
                    .build();
        }

        return frozenStormDispenserPattern;
    }
}
