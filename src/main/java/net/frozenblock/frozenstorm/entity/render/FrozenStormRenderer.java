package net.frozenblock.frozenstorm.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.frozenstorm.entity.FrozenStormEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class FrozenStormRenderer extends MobEntityRenderer<FrozenStormEntity, FrozenStormModel<FrozenStormEntity>> {
    private static final Identifier INVULNERABLE_TEXTURE = new Identifier("textures/entity/wither/wither_invulnerable.png");
    private static final Identifier TEXTURE = new Identifier("textures/entity/wither/wither.png");

    public FrozenStormRenderer(EntityRendererFactory.Context context) {
        super(context, new FrozenStormModel<>(context.getPart(EntityModelLayers.WITHER)), 1.0F);
        this.addFeature(new FrozenStormArmorFeatureRenderer(this, context.getModelLoader()));
    }

    protected int getBlockLight(FrozenStormEntity frozenStorm, BlockPos blockPos) {
        return 15;
    }

    public Identifier getTexture(FrozenStormEntity frozenStorm) {
        int i = frozenStorm.getInvulnerableTimer();
        return i > 0 && (i > 80 || i / 5 % 2 != 1) ? INVULNERABLE_TEXTURE : TEXTURE;
    }

    protected void scale(FrozenStormEntity frozenStorm, MatrixStack matrixStack, float f) {
        float g = 2.0F;
        int i = frozenStorm.getInvulnerableTimer();
        if (i > 0) {
            g -= ((float)i - f) / 220.0F * 0.5F;
        }

        matrixStack.scale(g, g, g);
    }
}
