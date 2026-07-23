package com.plusls.MasaGadget.mixin.accessor;

//#if MC < 260100
import com.mojang.serialization.Dynamic;
//#endif
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface AccessorLivingEntity {
    @Accessor("brain")
    void masa_gadget_mod$setBrain(Brain<?> brain);

    @Invoker("makeBrain")
    //#if MC >= 260100
    //$$ Brain<?> masa_gadget_mod$makeBrain(Brain.Packed packed);
    //#else
    Brain<?> masa_gadget_mod$makeBrain(Dynamic<?> dynamic);
    //#endif
}
