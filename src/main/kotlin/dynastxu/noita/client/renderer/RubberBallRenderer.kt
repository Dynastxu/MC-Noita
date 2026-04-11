package dynastxu.noita.client.renderer

import com.mojang.blaze3d.vertex.PoseStack
import dynastxu.noita.Noita
import dynastxu.noita.entity.RubberBallEntity
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation

class RubberBallRenderer(context: EntityRendererProvider.Context) : EntityRenderer<RubberBallEntity>(context) {
    override fun render(
        entity: RubberBallEntity,
        entityYaw: Float,
        partialTicks: Float,
        poseStack: PoseStack,
        buffer: MultiBufferSource,
        packedLight: Int
    ) {
        poseStack.pushPose()

        // 让球体始终面向玩家 (Billboarding)
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation())

        // 设置球体大小 (经验球大小，宽度0.5格)
        poseStack.scale(0.5f, 0.5f, 0.5f)

        // 使用原版的“item”渲染层来画一个带纹理的平面
        val vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE))
        val matrix = poseStack.last().pose()

        // 绘制一个2D平面 (单位正方形)
        vertexConsumer.addVertex(matrix, -1.0f, -1.0f, 0.0f).setColor(255, 255, 255, 255).setUv(0.0f, 1.0f)
            .setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(0.0f, 0.0f, 1.0f)
        vertexConsumer.addVertex(matrix, 1.0f, -1.0f, 0.0f).setColor(255, 255, 255, 255).setUv(1.0f, 1.0f)
            .setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(0.0f, 0.0f, 1.0f)
        vertexConsumer.addVertex(matrix, 1.0f, 1.0f, 0.0f).setColor(255, 255, 255, 255).setUv(1.0f, 0.0f)
            .setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(0.0f, 0.0f, 1.0f)
        vertexConsumer.addVertex(matrix, -1.0f, 1.0f, 0.0f).setColor(255, 255, 255, 255).setUv(0.0f, 0.0f)
            .setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(0.0f, 0.0f, 1.0f)

        poseStack.popPose()
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight)
    }

    override fun getTextureLocation(rubberBallEntity: RubberBallEntity): ResourceLocation {
        return TEXTURE
    }

    companion object {
        // 定义纹理路径: assets/yourmod/textures/entity/bouncy_ball.png
        private val TEXTURE: ResourceLocation =
            ResourceLocation.fromNamespaceAndPath(Noita.ID, "textures/entity/rubber_ball.png")
    }
}
