package net.dshbwlto.foodfight.entity;

import com.simibubi.create.content.equipment.potatoCannon.PotatoProjectileEntity;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.damageTypes.CreateDamageSources;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class CustomPotatoProjectileEntity extends PotatoProjectileEntity {
    public CustomPotatoProjectileEntity(EntityType<? extends AbstractHurtingProjectile> type, Level level) {
        super(type, level);
    }

    @Override
    protected void onHitEntity(EntityHitResult ray) {
        super.onHitEntity(ray);

        if (getStuckEntity() != null)
            return;

        Vec3 hit = ray.getLocation();
        Entity target = ray.getEntity();
        float damage = type.damage() * additionalDamageMult + 100;
        float knockback = type.knockback() + additionalKnockback;
        Entity owner = this.getOwner();

        if (!target.isAlive())
            return;
        if (owner instanceof LivingEntity livingEntity)
            livingEntity.setLastHurtMob(target);

        if (target instanceof PotatoProjectileEntity ppe) {
            if (tickCount < 10 && target.tickCount < 10)
                return;
            if (ppe.getProjectileType() != getProjectileType()) {
                if (owner instanceof Player p)
                    AllAdvancements.POTATO_CANNON_COLLIDE.awardTo(p);
                if (ppe.getOwner() instanceof Player p)
                    AllAdvancements.POTATO_CANNON_COLLIDE.awardTo(p);
            }
        }

        pop(hit);

        if (target instanceof WitherBoss && ((WitherBoss) target).isPowered())
            return;
        if (type.preEntityHit(stack, ray))
            return;

        boolean targetIsEnderman = target.getType() == EntityType.ENDERMAN;
        int k = target.getRemainingFireTicks();
        if (this.isOnFire() && !targetIsEnderman)
            target.igniteForSeconds(5);

        boolean onServer = !level().isClientSide;
        DamageSource damageSource = causePotatoDamage();
        if (onServer && !target.hurt(damageSource, damage)) {
            target.setRemainingFireTicks(k);
            kill();
            return;
        }

        if (targetIsEnderman)
            return;

        if (!type.onEntityHit(stack, ray) && onServer) {
            if (random.nextDouble() <= recoveryChance) {
                recoverItem();
            } else {
                spawnAtLocation(type.dropStack());
            }
        }

        if (!(target instanceof LivingEntity livingentity)) {
            playHitSound(level(), position());
            kill();
            return;
        }

        if (type.reloadTicks() < 10)
            livingentity.invulnerableTime = type.reloadTicks() + 10;

        if (onServer && knockback > 0) {
            Vec3 appliedMotion = this.getDeltaMovement()
                    .multiply(1.0D, 0.0D, 1.0D)
                    .normalize();
            if (appliedMotion.lengthSqr() > 0.0D)
                livingentity.knockback(knockback * 0.6, -appliedMotion.x, -appliedMotion.z);
        }

        if (onServer && owner instanceof LivingEntity) {
            EnchantmentHelper.doPostAttackEffects((ServerLevel) level(), livingentity, damageSource);
        }

        if (livingentity != owner && livingentity instanceof Player && owner instanceof ServerPlayer
                && !this.isSilent()) {
            ((ServerPlayer) owner).connection
                    .send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
        }

        if (onServer && owner instanceof ServerPlayer serverplayerentity) {
            if (!target.isAlive() && target.getType()
                    .getCategory() == MobCategory.MONSTER || (target instanceof Player && target != owner))
                AllAdvancements.POTATO_CANNON.awardTo(serverplayerentity);
        }

        if (type.sticky() && target.isAlive()) {
            setStuckEntity(target);
        } else {
            kill();
        }

    }

    private void recoverItem() {
        if (!stack.isEmpty())
            spawnAtLocation(stack.copyWithCount(1));
    }

    private DamageSource causePotatoDamage() {
        return CreateDamageSources.potatoCannon(level(), this, getOwner());
    }

    private void pop(Vec3 hit) {
        if (!stack.isEmpty()) {
            for (int i = 0; i < 7; i++) {
                Vec3 m = VecHelper.offsetRandomly(Vec3.ZERO, this.random, .25f);
                level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, stack), hit.x, hit.y, hit.z, m.x, m.y,
                        m.z);
            }
        }
        if (!level().isClientSide)
            playHitSound(level(), position());
    }
}

