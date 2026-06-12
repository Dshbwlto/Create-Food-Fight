package net.dshbwlto.foodfight.item.custom;

import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import net.dshbwlto.foodfight.item.api.AbstractPotatoGunItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class HeavyMinigunItem extends AbstractPotatoGunItem {

    public float range;

    public HeavyMinigunItem(Properties properties, CustomRenderedItemModelRenderer renderer, int rangeInBlocks) {
        super(properties, renderer, rangeInBlocks);
        range = rangeInBlocks;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        Player player = (Player) entity;

        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        return super.use(level, player, usedHand);
    }
}
