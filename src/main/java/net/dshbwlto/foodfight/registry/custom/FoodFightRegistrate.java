package net.dshbwlto.foodfight.registry.custom;

import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.world.effect.MobEffect;

import java.util.function.Supplier;

public class FoodFightRegistrate extends CreateRegistrate {
    protected FoodFightRegistrate(String modid) {
        super(modid);
    }

    public static FoodFightRegistrate create(String modid) {
        return new FoodFightRegistrate(modid);
    }

    public <T extends MobEffect> MobEffectBuilder<T, FoodFightRegistrate> mobEffect(String name, Supplier<? extends T> factory) {
        return mobEffect(this, name, factory);
    }

    public <T extends MobEffect, P> MobEffectBuilder<T, P> mobEffect(P parent, Supplier<? extends T> factory) {
        return mobEffect(parent, currentName(), factory);
    }

    public <T extends MobEffect, P> MobEffectBuilder<T, P> mobEffect(P parent, String name, Supplier<? extends T> factory) {
        return entry(name, callback -> new MobEffectBuilder<>(this, parent, name, callback, factory));
    }
}
