package net.dshbwlto.foodfight.item;

import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.createmod.catnip.lang.FontHelper;
import net.dshbwlto.foodfight.FoodFight;
import net.dshbwlto.foodfight.item.custom.HeavyMinigunItem;
import net.dshbwlto.foodfight.item.custom.HeavyMinigunItemRenderer;
import net.dshbwlto.foodfight.item.custom.HeavySniperItem;
import net.dshbwlto.foodfight.item.custom.HeavySniperItemRenderer;
import net.dshbwlto.foodfight.registry.custom.FoodFightRegistrate;
import net.minecraft.world.item.Item;

public class FoodFightItems {
    public static final FoodFightRegistrate REGISTRATE = FoodFight.REGISTRATE;
    static {
        REGISTRATE.setTooltipModifierFactory(item ->
                new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                        .andThen(TooltipModifier.mapNull(KineticStats.create(item))));
    }
    public static final ItemEntry<HeavySniperItem> HEAVY_SNIPER = REGISTRATE.item("heavy_sniper",
            properties -> new HeavySniperItem(properties.stacksTo(1), new HeavySniperItemRenderer(), 20)).register();
    public static final ItemEntry<HeavyMinigunItem> HEAVY_MINIGUN = REGISTRATE.item("heavy_minigun",
            properties -> new HeavyMinigunItem(properties.stacksTo(1), new HeavyMinigunItemRenderer(), 20)).register();

    public static void register() {}
}
