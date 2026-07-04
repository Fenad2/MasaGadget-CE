package com.plusls.MasaGadget.mixin.mod_tweak.tweakeroo.inventoryPreviewUseCache;

import com.plusls.MasaGadget.util.ModId;
import org.spongepowered.asm.mixin.Mixin;
import top.hendrixshen.magiclib.api.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.api.dependency.annotation.Dependency;
import top.hendrixshen.magiclib.api.preprocess.DummyClass;

@Dependencies(
        require = @Dependency(ModId.tweakeroo),
        conflict = @Dependency(value = ModId.minecraft, versionPredicates = ">=1.21-")
)
@Mixin(DummyClass.class)
public class MixinMixinRenderUtils {
}
