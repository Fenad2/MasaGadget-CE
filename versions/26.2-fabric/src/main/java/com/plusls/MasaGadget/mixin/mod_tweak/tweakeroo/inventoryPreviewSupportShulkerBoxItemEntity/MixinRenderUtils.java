package com.plusls.MasaGadget.mixin.mod_tweak.tweakeroo.inventoryPreviewSupportShulkerBoxItemEntity;

import com.plusls.MasaGadget.game.Configs;
import com.plusls.MasaGadget.util.ModId;
import fi.dy.masa.malilib.render.InventoryOverlay;
import fi.dy.masa.malilib.render.InventoryOverlayContext;
import fi.dy.masa.malilib.util.InventoryUtils;
import fi.dy.masa.malilib.util.data.tag.CompoundData;
import fi.dy.masa.tweakeroo.renderer.InventoryOverlayHandler;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.magiclib.api.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.api.dependency.annotation.Dependency;

@Dependencies(require = @Dependency(ModId.tweakeroo))
@Mixin(value = InventoryOverlayHandler.class, remap = false)
public abstract class MixinRenderUtils {
    @Inject(method = "getTargetInventoryFromEntity", at = @At("HEAD"), cancellable = true)
    private void masa_gadget$addShulkerBoxItemEntity(
            Entity entity,
            CompoundData data,
            CallbackInfoReturnable<InventoryOverlayContext> cir
    ) {
        if (!Configs.inventoryPreviewSupportShulkerBoxItemEntity.getBooleanValue() ||
                !(entity instanceof ItemEntity itemEntity)) {
            return;
        }

        ItemStack itemStack = itemEntity.getItem();

        if (!(itemStack.getItem() instanceof BlockItem blockItem) ||
                !(blockItem.getBlock() instanceof ShulkerBoxBlock)) {
            return;
        }

        Container inv = InventoryUtils.getAsInventory(InventoryUtils.getStoredItems(itemStack, 27));

        if (inv == null || inv.isEmpty()) {
            return;
        }

        InventoryOverlayHandler handler = (InventoryOverlayHandler) (Object) this;
        cir.setReturnValue(new InventoryOverlayContext(
                InventoryOverlay.getInventoryType(itemStack),
                inv,
                null,
                null,
                InventoryUtils.getStoredBlockEntityDataTag(itemStack),
                handler.getRefreshHandler()
        ));
    }
}
