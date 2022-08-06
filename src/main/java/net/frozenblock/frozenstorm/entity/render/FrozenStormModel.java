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
    private final ModelPart ribcage;
    private final ModelPart tail;
    private final ModelPart centerHead;
    private final ModelPart rightHead;
    private final ModelPart leftHead;

    public FrozenStormModel(ModelPart root) {
        this.root = root;
        this.ribcage = root.getChild("ribcage");
        this.tail = root.getChild(EntityModelPartNames.TAIL);
        this.centerHead = root.getChild("center_head");
        this.rightHead = root.getChild("right_head");
        this.leftHead = root.getChild("left_head");
        this.spike2 = root.getChild("spike2");
        this.spike1 = root.getChild("spike1");
        this.spike3 = root.getChild("spike3");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData spike2 = modelPartData.addChild("spike2", ModelPartBuilder.create().uv(12, 45).cuboid(-16.0F, -32.0F, -1.0F, 6.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, 24.0F, 0.0F, 0.0F, 0.0F, -0.1309F));

        ModelPartData spike1 = modelPartData.addChild("spike1", ModelPartBuilder.create().uv(18, 34).cuboid(-4.0F, -42.0F, -1.0F, 8.0F, 11.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, 24.0F, 0.0F, 0.0F, 0.0F, -0.1309F));

        ModelPartData spike3 = modelPartData.addChild("spike3", ModelPartBuilder.create().uv(0, 44).cuboid(6.0F, -27.0F, -2.0F, 6.0F, 12.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, 17.0F, 0.0F, 0.0F, 0.0F, 0.2182F));

        ModelPartData shoulders = modelPartData.addChild("shoulders", ModelPartBuilder.create().uv(0, 0).cuboid(-10.0F, 3.9F, -0.5F, 20.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 34).cuboid(-3.0F, -2.0F, -0.51F, 6.0F, 7.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData ribcage = modelPartData.addChild("ribcage", ModelPartBuilder.create().uv(34, 34).cuboid(0.0F, 0.0F, 0.0F, 3.0F, 10.0F, 3.0F, new Dilation(0.0F))
                .uv(32, 14).cuboid(-4.0F, 1.5F, 0.5F, 11.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(24, 10).cuboid(-4.0F, 4.0F, 0.5F, 11.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(24, 6).cuboid(-4.0F, 6.5F, 0.5F, 11.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 6.9F, -0.5F));

        ModelPartData tail = modelPartData.addChild(EntityModelPartNames.TAIL, ModelPartBuilder.create().uv(42, 18).cuboid(0.0F, 0.0F, 0.0F, 3.0F, 6.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 16.9F, -0.5F));

        ModelPartData center_head = modelPartData.addChild("center_head", ModelPartBuilder.create().uv(0, 6).cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData right_head = modelPartData.addChild("right_head", ModelPartBuilder.create().uv(24, 22).cuboid(-4.0F, -4.0F, -4.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-8.0F, 4.0F, 0.0F));

        ModelPartData left_head = modelPartData.addChild("left_head", ModelPartBuilder.create().uv(0, 22).cuboid(-4.0F, -4.0F, -4.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(10.0F, 4.0F, 0.0F));

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
