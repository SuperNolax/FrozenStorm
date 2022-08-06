package net.frozenblock.frozenstorm.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.frozenblock.frozenstorm.FrozenStorm;
import net.frozenblock.frozenstorm.block.FrozenStormSkullBlock;
import net.frozenblock.frozenstorm.block.WallFrozenStormSkullBlock;
import net.minecraft.block.*;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class RegisterBlocks {

    public static final FrozenStormSkullBlock FROZEN_STORM_SKULL = Registry.register(Registry.BLOCK, FrozenStorm.id("frozen_skull"), new FrozenStormSkullBlock(FabricBlockSettings.copy(Blocks.WITHER_SKELETON_SKULL)));
    public static final WallFrozenStormSkullBlock FROZEN_STORM_WALL_SKULL = Registry.register(Registry.BLOCK, FrozenStorm.id("frozen_skull_wall"), new WallFrozenStormSkullBlock(FabricBlockSettings.copy(Blocks.WITHER_SKELETON_WALL_SKULL)));

    public static void init() {
        DispenserBlock.registerBehavior(
                RegisterItems.FROZEN_STORM_SKULL,
                new FallibleItemDispenserBehavior() {
                    @Override
                    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                        World world = pointer.getWorld();
                        Direction direction = pointer.getBlockState().get(DispenserBlock.FACING);
                        BlockPos blockPos = pointer.getPos().offset(direction);
                        if (world.isAir(blockPos) && FrozenStormSkullBlock.canDispense(world, blockPos, stack)) {
                            world.setBlockState(
                                    blockPos,
                                    FROZEN_STORM_SKULL
                                            .getDefaultState()
                                            .with(SkullBlock.ROTATION, Integer.valueOf(direction.getAxis() == Direction.Axis.Y ? 0 : direction.getOpposite().getHorizontal() * 4)),
                                    Block.NOTIFY_ALL
                            );
                            world.emitGameEvent(null, GameEvent.BLOCK_PLACE, blockPos);
                            BlockEntity blockEntity = world.getBlockEntity(blockPos);
                            if (blockEntity instanceof SkullBlockEntity) {
                                FrozenStormSkullBlock.onPlaced(world, blockPos, (SkullBlockEntity)blockEntity);
                            }

                            stack.decrement(1);
                            this.setSuccess(true);
                        } else {
                            this.setSuccess(ArmorItem.dispenseArmor(pointer, stack));
                        }

                        return stack;
                    }
                }
        );
    }
}
