package com.plusls.MasaGadget.mixin.accessor;

import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.item.trading.MerchantOffers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractVillager.class)
public interface AccessorAbstractVillager {
    @Accessor("offers")
    void masa_gadget_mod$setOffers(MerchantOffers offers);

    //多人客户端直接读取 PCA 已同步的 offers 字段。
    //#if MC >= 12110
    //$$ @Accessor("offers")
    //$$ MerchantOffers masa_gadget_mod$getOffers();
    //#endif
}
