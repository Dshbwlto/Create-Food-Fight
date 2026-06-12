package net.dshbwlto.foodfight.item.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.CreateClient;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.dshbwlto.foodfight.FoodFight;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class HeavyMinigunItemRenderer extends CustomRenderedItemModelRenderer {

    private static final PartialModel COG = PartialModel.of(FoodFight.asResource("item/heavy_minigun_gear"));
    private static final PartialModel BARREL = PartialModel.of(FoodFight.asResource("item/heavy_minigun_barrel"));
    private static final PartialModel LASER = PartialModel.of(FoodFight.asResource("item/lasaer"));
    private static final PartialModel TANK_L = PartialModel.of(FoodFight.asResource("item/heavy_minigun_tank_l"));
    private static final PartialModel TANK_R = PartialModel.of(FoodFight.asResource("item/heavy_minigun_tank_r"));

    @Override
    protected void render(ItemStack stack, CustomRenderedItemModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        renderer.render(model.getOriginalModel(), light);
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;

        float angle = AnimationTickHolder.getRenderTime() * -2.5f;
        HeavyMinigunItem item = (HeavyMinigunItem) stack.getItem();

        if (player != null) {
            boolean inMainHand = player.getMainHandItem() == stack;
            boolean inOffHand = player.getOffhandItem() == stack;

            if (inMainHand || inOffHand) {
                boolean leftHanded = player.getMainArm() == HumanoidArm.LEFT;
                float speed = CreateClient.POTATO_CANNON_RENDER_HANDLER.getAnimation(inMainHand ^ leftHanded,
                        AnimationTickHolder.getPartialTicks());
                angle += 360 * Mth.clamp(speed * 5, 0, 1);
            }
        }

        angle %= 360;

        ms.pushPose();
        ms.translate(0, 1/64f, 0);
        ms.mulPose(Axis.ZP.rotationDegrees(angle));
        renderer.render(COG.get(), light);
        ms.popPose();

        ms.pushPose();
        ms.translate(0, 1/64f, 0);
        ms.mulPose(Axis.ZP.rotationDegrees(-angle));
        renderer.render(BARREL.get(), light);
        ms.popPose();

        if (transformType == ItemDisplayContext.FIXED
                || transformType == ItemDisplayContext.GROUND
                || transformType == ItemDisplayContext.GUI
                || transformType == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND
                || transformType == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND) {
            renderer.render(TANK_R.get(), light);
        } else if (transformType == ItemDisplayContext.THIRD_PERSON_LEFT_HAND
                || transformType == ItemDisplayContext.FIRST_PERSON_LEFT_HAND) {
            renderer.render(TANK_L.get(), light);
        }
    }
}
