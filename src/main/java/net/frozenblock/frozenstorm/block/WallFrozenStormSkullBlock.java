package net.frozenblock.frozenstorm.block;

import net.frozenblock.frozenstorm.FrozenStorm;
import net.frozenblock.frozenstorm.registry.RegisterBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SkullBlock;
import net.minecraft.block.WallSkullBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class WallFrozenStormSkullBlock extends WallSkullBlock {

    public WallFrozenStormSkullBlock(Settings settings) {
        super(FrozenStorm.FROZEN_SKULL_TYPE, settings);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        RegisterBlocks.FROZEN_STORM_SKULL.onPlaced(world, pos, state, placer, itemStack);
    }
}
