package io.github.how_bout_no.outvoted.client.render;

import io.github.how_bout_no.outvoted.client.model.MeerkatModel;
import io.github.how_bout_no.outvoted.entity.MeerkatEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MeerkatRenderer extends GeoMobRenderer<MeerkatEntity> {
    public MeerkatRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new MeerkatModel());
    }

    @Override
    public RenderLayer getRenderType(MeerkatEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityCutoutNoCull(this.getTexture(animatable));
    }

    @Override
    public void renderEarly(MeerkatEntity animatable, MatrixStack stackIn, float ticks,
                            VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                            int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn,
                red, green, blue, partialTicks);
        if (animatable.isBaby()) stackIn.scale(0.6F, 0.6F, 0.6F);
        else stackIn.scale(0.9F, 0.9F, 0.9F);
    }
}