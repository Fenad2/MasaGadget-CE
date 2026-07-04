package com.plusls.MasaGadget.util;

import com.plusls.MasaGadget.mixin.accessor.AccessorAbstractVillager;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.trading.MerchantOffers;

public class VillagerDataUtil {
    public static ResourceKey<VillagerProfession> getVillagerProfession(Villager villager) {
        return villager.getVillagerData().profession().unwrapKey().orElse(null);
    }

    public static MerchantOffers getOffers(AbstractVillager villager) {
        if (villager.level().isClientSide()) {
            return ((AccessorAbstractVillager) villager).masa_gadget_mod$getOffers();
        }

        return villager.getOffers();
    }
}
