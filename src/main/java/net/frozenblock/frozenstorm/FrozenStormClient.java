package net.frozenblock.frozenstorm;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.frozenblock.frozenstorm.entity.render.FrozenStormModel;
import net.frozenblock.frozenstorm.entity.render.FrozenStormRenderer;
import net.frozenblock.frozenstorm.registry.RegisterEntities;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public class FrozenStormClient implements ClientModInitializer {

    public static final EntityModelLayer FROZEN_STORM_LAYER = new EntityModelLayer(FrozenStorm.id("frozen_storm"), "main");

    @Override
    public void onInitializeClient() {

        EntityRendererRegistry.register(RegisterEntities.FROZEN_STORM, FrozenStormRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(FROZEN_STORM_LAYER, FrozenStormModel::getTexturedModelData);
    }
}
