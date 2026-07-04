package com.plusls.MasaGadget.mixin.mod_tweak.tweakeroo.inventoryPreviewSupportShulkerBoxItemEntity;

import com.plusls.MasaGadget.game.Configs;
import com.plusls.MasaGadget.util.ModId;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import org.spongepowered.asm.mixin.Mixin;
import top.hendrixshen.magiclib.api.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.api.dependency.annotation.Dependency;

//#if MC >= 260100
//$$ import fi.dy.masa.malilib.render.InventoryOverlay;
//$$ import fi.dy.masa.malilib.render.InventoryOverlayContext;
//$$ import fi.dy.masa.malilib.util.InventoryUtils;
//$$ import fi.dy.masa.malilib.util.data.tag.CompoundData;
//$$ import fi.dy.masa.tweakeroo.renderer.InventoryOverlayHandler;
//$$ import net.minecraft.world.entity.Entity;
//$$ import org.spongepowered.asm.mixin.injection.At;
//$$ import org.spongepowered.asm.mixin.injection.Inject;
//$$ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//#else
import com.plusls.MasaGadget.impl.generic.HitResultHandler;
import fi.dy.masa.tweakeroo.renderer.RenderUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

//#if MC >= 12110
//$$ import com.mojang.logging.LogUtils;
//$$ import net.minecraft.client.Minecraft;
//$$ import net.minecraft.core.component.DataComponents;
//$$ import net.minecraft.nbt.CompoundTag;
//$$ import net.minecraft.util.ProblemReporter;
//$$ import net.minecraft.world.item.component.TypedEntityData;
//$$ import net.minecraft.world.level.storage.TagValueInput;
//$$ import net.minecraft.world.level.storage.ValueInput;
//$$ import org.slf4j.Logger;
//$$ import org.spongepowered.asm.mixin.Unique;
//#elseif MC >= 12106
//$$ import com.mojang.logging.LogUtils;
//$$ import net.minecraft.client.Minecraft;
//$$ import net.minecraft.core.component.DataComponents;
//$$ import net.minecraft.nbt.CompoundTag;
//$$ import net.minecraft.util.ProblemReporter;
//$$ import net.minecraft.world.item.component.CustomData;
//$$ import net.minecraft.world.level.storage.TagValueInput;
//$$ import net.minecraft.world.level.storage.ValueInput;
//$$ import org.slf4j.Logger;
//$$ import org.spongepowered.asm.mixin.Unique;
//#elseif MC > 12004
//$$ import net.minecraft.client.Minecraft;
//$$ import net.minecraft.core.component.DataComponents;
//$$ import net.minecraft.nbt.CompoundTag;
//$$ import net.minecraft.world.item.component.CustomData;
//#else
import net.minecraft.nbt.CompoundTag;
//#endif
//#endif

@Dependencies(require = @Dependency(ModId.tweakeroo))
//#if MC >= 260100
//$$ @Mixin(value = InventoryOverlayHandler.class, remap = false)
//#else
@Mixin(value = RenderUtils.class, remap = false)
//#endif
public abstract class MixinRenderUtils {
    //#if 260100 > MC && MC >= 12106
    //$$ @Unique
    //$$ private static final Logger masa_gadget$logger = LogUtils.getLogger();
    //#endif

    //#if MC >= 260100
    //$$ @Inject(method = "getTargetInventoryFromEntity", at = @At("HEAD"), cancellable = true)
    //$$ private void masa_gadget$addShulkerBoxItemEntity(
    //$$         Entity entity,
    //$$         CompoundData data,
    //$$         CallbackInfoReturnable<InventoryOverlayContext> cir
    //$$ ) {
    //$$     if (!Configs.inventoryPreviewSupportShulkerBoxItemEntity.getBooleanValue() ||
    //$$             !(entity instanceof ItemEntity itemEntity)) {
    //$$         return;
    //$$     }
    //$$
    //$$     ItemStack itemStack = itemEntity.getItem();
    //$$
    //$$     if (!(itemStack.getItem() instanceof BlockItem blockItem) ||
    //$$             !(blockItem.getBlock() instanceof ShulkerBoxBlock)) {
    //$$         return;
    //$$     }
    //$$
    //$$     Container inv = InventoryUtils.getAsInventory(InventoryUtils.getStoredItems(itemStack, 27));
    //$$
    //$$     if (inv == null || inv.isEmpty()) {
    //$$         return;
    //$$     }
    //$$
    //$$     InventoryOverlayHandler handler = (InventoryOverlayHandler) (Object) this;
    //$$     cir.setReturnValue(new InventoryOverlayContext(
    //$$             InventoryOverlay.getInventoryType(itemStack),
    //$$             inv,
    //$$             null,
    //$$             null,
    //$$             InventoryUtils.getStoredBlockEntityDataTag(itemStack),
    //$$             handler.getRefreshHandler()
    //$$     ));
    //$$ }
    //#else
    @ModifyVariable(
            method = "renderInventoryOverlay",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/util/GuiUtils;getScaledWindowWidth()I",
                    remap = false
            ),
            ordinal = 0
    )
    private static Container modifyInv(Container inv) {
        Container ret = inv;
        Entity traceEntity = HitResultHandler.getInstance().getHitEntity().orElse(null);

        if (Configs.inventoryPreviewSupportShulkerBoxItemEntity.getBooleanValue() &&
                ret == null &&
                traceEntity instanceof ItemEntity) {
            ItemStack itemStack = ((ItemEntity) traceEntity).getItem();
            Item item = itemStack.getItem();
            NonNullList<ItemStack> stacks = NonNullList.withSize(27, ItemStack.EMPTY);

            //#if MC >= 12110
            //$$ TypedEntityData<?> invData = itemStack.get(DataComponents.BLOCK_ENTITY_DATA);
            //$$
            //$$ if (invData == null) {
            //$$     return null;
            //$$ }
            //$$
            //$$ CompoundTag invNbt = invData.copyTagWithoutId();
            //$$ ValueInput input;
            //$$
            //$$ try (ProblemReporter.ScopedCollector collector = new ProblemReporter.ScopedCollector(MixinRenderUtils.masa_gadget$logger)) {
            //$$     input = TagValueInput.create(collector, Minecraft.getInstance().level.registryAccess(), invNbt);
            //$$ }
            //#elseif MC > 12004
            //$$ CustomData invData = itemStack.get(DataComponents.BLOCK_ENTITY_DATA);
            //$$
            //$$ if (invData == null) {
            //$$     return null;
            //$$ }
            //$$
            //$$ CompoundTag invNbt = invData.copyTag();
            //#else
            CompoundTag invNbt = itemStack.getTagElement("BlockEntityTag");
            //#endif

            if (item instanceof BlockItem && ((BlockItem) item).getBlock() instanceof ShulkerBoxBlock) {
                ret = new SimpleContainer(27);

                //#if MC >= 12110
                //$$ ContainerHelper.loadAllItems(input, stacks);
                //#elseif MC >= 12106
                //$$ try (ProblemReporter.ScopedCollector collector = new ProblemReporter.ScopedCollector(MixinRenderUtils.masa_gadget$logger)) {
                //$$     ValueInput input = TagValueInput.create(collector, Minecraft.getInstance().cameraEntity.registryAccess(), invNbt);
                //$$     ContainerHelper.loadAllItems(input, stacks);
                //$$ }
                //#else
                if (invNbt != null) {
                    ContainerHelper.loadAllItems(
                            invNbt,
                            stacks
                            //#if MC > 12004
                            //$$ , Minecraft.getInstance().level.registryAccess()
                            //#endif
                    );
                }
                //#endif

                for (int i = 0; i < 27; i++) {
                    ret.setItem(i, stacks.get(i));
                }
            }
        }

        return ret;
    }
    //#endif
}
