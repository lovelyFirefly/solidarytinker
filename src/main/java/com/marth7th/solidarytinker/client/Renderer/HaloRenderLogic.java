package com.marth7th.solidarytinker.client.Renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public final class HaloRenderLogic {
    public static void renderCompleteDynamicHaloHorizontal(PoseStack poseStack, Player player, float partialTick,ResourceLocation haloTexture) {
        poseStack.pushPose();
        poseStack.translate(0, player.getBbHeight() + 0.35f, 0);

        // 水平旋转逻辑
        float yaw = player.getViewYRot(partialTick);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(-yaw));

        MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        VertexConsumer vertexBuilder = buffer.getBuffer(RenderType.entityTranslucent(haloTexture));
        Matrix4f poseMatrix = poseStack.last().pose();
        Matrix3f normalMatrix = poseStack.last().normal();
        int light = 15728880;
        int overlay = OverlayTexture.NO_OVERLAY;

        float period = 3.0f;                // 呼吸周期（s）
        float minAlpha = 50.0f;             // 透明度下限
        float maxAlpha = 200.0f;            // 透明度上限
        float minBrightness = 0.5f;         // 亮度下限
        float maxBrightness = 1.0f;         // 亮度上限

        // 计算动态系数
        long currentTime = System.currentTimeMillis();
        float phase = (currentTime % (long)(period * 1000)) / (period * 1000.0f);
        float waveFactor = (float) Math.sin(phase * 2 * Math.PI);

        // 映射到实际范围
        int dynamicAlpha = (int) ((waveFactor + 1) / 2 * (maxAlpha - minAlpha) + minAlpha);
        int dynamicBrightness = (int) (
                ((waveFactor + 1) / 2 * (maxBrightness - minBrightness) + minBrightness) * 255
        );

        //顶点定义
        float size = 0.4f;
        buildVertex(vertexBuilder, poseMatrix, normalMatrix, -size, 0, -size, 0, 0, dynamicBrightness, dynamicAlpha, overlay, light);
        buildVertex(vertexBuilder, poseMatrix, normalMatrix, size, 0, -size, 1, 0, dynamicBrightness, dynamicAlpha, overlay, light);
        buildVertex(vertexBuilder, poseMatrix, normalMatrix, size, 0, size, 1, 1, dynamicBrightness, dynamicAlpha, overlay, light);
        buildVertex(vertexBuilder, poseMatrix, normalMatrix, -size, 0, size, 0, 1, dynamicBrightness, dynamicAlpha, overlay, light);

        poseStack.popPose();
        buffer.endBatch();
    }

    // 封装顶点构建逻辑
    private static void buildVertex(
            VertexConsumer builder, Matrix4f pose, Matrix3f normal,
            float x, float y, float z, float u, float v,
            int brightness, int alpha, int overlay, int light
    ) {
        builder.vertex(pose, x, y, z)
                .color(brightness, brightness, brightness, alpha) // 动态亮度和透明度
                .uv(u, v)
                .overlayCoords(overlay)
                .uv2(light)
                .normal(normal, 0, 1, 0)
                .endVertex();
    }
}
