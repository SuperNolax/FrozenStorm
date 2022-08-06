package net.frozenblock.frozenstorm.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.frozenstorm.entity.FrozenStormEntity;
import net.minecraft.client.render.entity.feature.EnergySwirlOverlayFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class FrozenStormArmorFeatureRenderer extends EnergySwirlOverlayFeatureRenderer<FrozenStormEntity, FrozenStormModel<FrozenStormEntity>> {
    private static final Identifier SKIN = new Identifier("textures/entity/wither/wither_armor.png");
    private final FrozenStormModel<FrozenStormEntity> model;

    public FrozenStormArmorFeatureRenderer(FeatureRendererContext<FrozenStormEntity, FrozenStormModel<FrozenStormEntity>> context, EntityModelLoader loader) {
        super(context);
        this.model = new FrozenStormModel<>(loader.getModelPart(EntityModelLayers.WITHER_ARMOR));
    }

    @Override
    protected float getEnergySwirlX(float partialAge) {
        return MathHelper.cos(partialAge * 0.02F) * 3.0F;
    }

    @Override
    protected Identifier getEnergySwirlTexture() {
        return SKIN;
    }

    @Override
    protected EntityModel<FrozenStormEntity> getEnergySwirlModel() {
        return this.model;
    }
}
