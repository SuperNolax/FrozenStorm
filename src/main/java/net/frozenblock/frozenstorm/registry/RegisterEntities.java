package net.frozenblock.frozenstorm.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.frozenblock.frozenstorm.FrozenStorm;
import net.frozenblock.frozenstorm.entity.FrozenStormEntity;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

public class RegisterEntities {

    public static final EntityType<FrozenStormEntity> FROZEN_STORM = Registry.register(Registry.ENTITY_TYPE, FrozenStorm.id("frozen_storm"), FabricEntityTypeBuilder.createMob().spawnGroup(SpawnGroup.MONSTER).defaultAttributes(FrozenStormEntity::createWitherAttributes).entityFactory(FrozenStormEntity::new).fireImmune().specificSpawnBlocks(Blocks.WITHER_ROSE).dimensions(EntityDimensions.changing(0.9F, 3.5F)).build());


    public static void init() {

    }
}
