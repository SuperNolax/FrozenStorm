package net.frozenblock.frozenstorm.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.frozenstorm.entity.FrozenStormEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class FrozenStormModel<T extends FrozenStormEntity> extends SinglePartEntityModel<T> {

    private static final String RIBCAGE = "ribcage";
    private static final String CENTER_HEAD = "center_head";
    private static final String RIGHT_HEAD = "right_head";
    private static final String LEFT_HEAD = "left_head";
    private static final float RIBCAGE_PITCH_OFFSET = 0.065F;
    private static final float TAIL_PITCH_OFFSET = 0.265F;
    private final ModelPart root;
    private final ModelPart spike2;
    private final ModelPart spike1;
    private final ModelPart spike3;
    private final ModelPart body1;
    private final ModelPart body2;
    private final ModelPart body3;
    private final ModelPart head1;
    private final ModelPart head2;
    private final ModelPart head3;

    public FrozenStormModel(ModelPart root) {
        this.root = root;
        this.spike2 = root.getChild("spike2");
        this.spike1 = root.getChild("spike1");
        this.spike3 = root.getChild("spike3");
        this.body1 = root.getChild("body1");
        this.body2 = root.getChild("body2");
        this.body3 = root.getChild("body3");
        this.head1 = root.getChild("head1");
        this.head2 = root.getChild("head2");
        this.head3 = root.getChild("head3");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("shoulders", ModelPartBuilder.create().uv(0, 16).cuboid(-10.0F, 3.9F, -0.5F, 20.0F, 3.0F, 3.0F, new Dilation(0.5F)), ModelTransform.NONE);
        float f = 0.20420352F;
        modelPartData.addChild(
                "ribcage",
                ModelPartBuilder.create()
                        .uv(0, 22)
                        .cuboid(0.0F, 0.0F, 0.0F, 3.0F, 10.0F, 3.0F, new Dilation(0.5F))
                        .uv(24, 22)
                        .cuboid(-4.0F, 1.5F, 0.5F, 11.0F, 2.0F, 2.0F, new Dilation(0.5F))
                        .uv(24, 22)
                        .cuboid(-4.0F, 4.0F, 0.5F, 11.0F, 2.0F, 2.0F, new Dilation(0.5F))
                        .uv(24, 22)
                        .cuboid(-4.0F, 6.5F, 0.5F, 11.0F, 2.0F, 2.0F, new Dilation(0.5F)),
                ModelTransform.of(-2.0F, 6.9F, -0.5F, 0.20420352F, 0.0F, 0.0F)
        );
        modelPartData.addChild(
                "tail",
                ModelPartBuilder.create().uv(12, 22).cuboid(0.0F, 0.0F, 0.0F, 3.0F, 6.0F, 3.0F, new Dilation(0.5F)),
                ModelTransform.of(-2.0F, 6.9F + MathHelper.cos(0.20420352F) * 10.0F, -0.5F + MathHelper.sin(0.20420352F) * 10.0F, 0.83252203F, 0.0F, 0.0F)
        );
        modelPartData.addChild("center_head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.5F)), ModelTransform.NONE);
        ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(32, 0).cuboid(-4.0F, -4.0F, -4.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.5F));
        modelPartData.addChild("right_head", modelPartBuilder, ModelTransform.pivot(-8.0F, 4.0F, 0.0F));
        modelPartData.addChild("left_head", modelPartBuilder, ModelTransform.pivot(10.0F, 4.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    public void setAngles(T witherEntity, float f, float g, float h, float i, float j) {
        float k = MathHelper.cos(h * 0.1F);
        this.ribcage.pitch = (0.065F + 0.05F * k) * (float) Math.PI;
        this.tail.setPivot(-2.0F, 6.9F + MathHelper.cos(this.ribcage.pitch) * 10.0F, -0.5F + MathHelper.sin(this.ribcage.pitch) * 10.0F);
        this.tail.pitch = (0.265F + 0.1F * k) * (float) Math.PI;
        this.centerHead.yaw = i * (float) (Math.PI / 180.0);
        this.centerHead.pitch = j * (float) (Math.PI / 180.0);
    }

    public void animateModel(T witherEntity, float f, float g, float h) {
        rotateHead(witherEntity, this.rightHead, 0);
        rotateHead(witherEntity, this.leftHead, 1);
    }

    private static <T extends FrozenStormEntity> void rotateHead(T entity, ModelPart head, int sigma) {
        head.yaw = (entity.getHeadYaw(sigma) - entity.bodyYaw) * (float) (Math.PI / 180.0);
        head.pitch = entity.getHeadPitch(sigma) * (float) (Math.PI / 180.0);
    }
}
