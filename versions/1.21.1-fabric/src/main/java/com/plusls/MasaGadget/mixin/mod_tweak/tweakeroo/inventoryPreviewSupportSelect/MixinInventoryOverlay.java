package com.plusls.MasaGadget.mixin.mod_tweak.tweakeroo.inventoryPreviewSupportSelect;

import com.llamalad7.mixinextras.sugar.Local;
import com.plusls.MasaGadget.game.Configs;
import com.plusls.MasaGadget.impl.mod_tweak.tweakeroo.inventoryPreviewSupportSelect.InventoryOverlayRenderHandler;
import com.plusls.MasaGadget.util.ModId;
import fi.dy.masa.malilib.render.InventoryOverlay;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.api.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.api.dependency.annotation.Dependency;

@Dependencies(require = @Dependency(ModId.tweakeroo))
@Mixin(value = InventoryOverlay.class, remap = false)
public class MixinInventoryOverlay {
    @Inject(
            method = "renderStackAt(Lnet/minecraft/world/item/ItemStack;FFFLnet/minecraft/client/Minecraft;Lnet/minecraft/client/gui/GuiGraphics;DD)V",
            at = @At("RETURN"),
            remap = true,
            require = 0
    )
    private static void masa_gadget_mod$addStackToolTipLegacyOrder(
            CallbackInfo ci,
            @Local(argsOnly = true) ItemStack stack,
            @Local(ordinal = 0, argsOnly = true) float x,
            @Local(ordinal = 1, argsOnly = true) float y
    ) {
        masa_gadget_mod$updateState(x, y, stack);
    }

    @Inject(
            method = "renderStackAt(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/item/ItemStack;FFFLnet/minecraft/client/Minecraft;DD)V",
            at = @At("RETURN"),
            remap = true,
            require = 0
    )
    private static void masa_gadget_mod$addStackToolTipGuiFirst(
            CallbackInfo ci,
            @Local(argsOnly = true) GuiGraphics guiGraphics,
            @Local(argsOnly = true) ItemStack stack,
            @Local(ordinal = 0, argsOnly = true) float x,
            @Local(ordinal = 1, argsOnly = true) float y
    ) {
        masa_gadget_mod$updateState(x, y, stack);
    }

    private static void masa_gadget_mod$updateState(float x, float y, ItemStack stack) {
        if (Configs.inventoryPreviewSupportSelect.getBooleanValue()) {
            InventoryOverlayRenderHandler.getInstance().updateState((int) x, (int) y, stack);
        }
    }
}
