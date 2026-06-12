package net.dshbwlto.foodfight.item.api;

import com.simibubi.create.content.equipment.zapper.ShootableGadgetItemMethods;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import net.dshbwlto.foodfight.item.custom.HeavySniperItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class AbstractPotatoGunItem extends Item {
    public CustomRenderedItemModelRenderer renderedItemModel;

    public AbstractPotatoGunItem(Properties properties, CustomRenderedItemModelRenderer renderer, int rangeInBlocks) {
        super(properties);
        renderedItemModel = renderer;
    }


    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        // Raytrace - Find the target
        if (entity instanceof Player player && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == this) {
            Vec3 ray = entity.position()
                    .add(0, entity.getEyeHeight(), 0);
            Vec3 range = entity.getLookAngle()
                    .scale(1000);
            BlockHitResult raytrace =
                    level.clip(new ClipContext(ray, ray.add(range), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity));

            BlockPos pos = raytrace.getBlockPos();
            BlockState state = level.getBlockState(pos);

            level.addParticle(ParticleTypes.CLOUD, raytrace.getLocation().x, raytrace.getLocation().y, raytrace.getLocation().z, 0, 0, 0);
        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(SimpleCustomRenderer.create(this, renderedItemModel));
    }
}
