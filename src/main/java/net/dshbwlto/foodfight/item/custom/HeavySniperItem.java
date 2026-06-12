package net.dshbwlto.foodfight.item.custom;

import com.simibubi.create.AllEntityTypes;
import com.simibubi.create.CreateClient;
import com.simibubi.create.api.equipment.potatoCannon.PotatoCannonProjectileType;
import com.simibubi.create.content.equipment.armor.BacktankUtil;
import com.simibubi.create.content.equipment.potatoCannon.PotatoCannonItem;
import com.simibubi.create.content.equipment.potatoCannon.PotatoCannonPacket;
import com.simibubi.create.content.equipment.potatoCannon.PotatoProjectileEntity;
import com.simibubi.create.content.equipment.zapper.ShootableGadgetItemMethods;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import com.simibubi.create.foundation.utility.GlobalRegistryAccess;
import com.simibubi.create.infrastructure.config.AllConfigs;
import net.createmod.catnip.math.VecHelper;
import net.dshbwlto.foodfight.entity.CustomPotatoProjectileEntity;
import net.dshbwlto.foodfight.entity.FoodFightEntityTypes;
import net.dshbwlto.foodfight.item.api.AbstractPotatoGunItem;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.event.EventHooks;

import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class HeavySniperItem extends AbstractPotatoGunItem {
    public HeavySniperItem(Properties properties, CustomRenderedItemModelRenderer renderer, int rangeInBlocks) {
        super(properties, renderer, rangeInBlocks);
    }
}
