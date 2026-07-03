package com.plusls.MasaGadget.impl.feature.entityTrace;

import com.plusls.MasaGadget.game.Configs;
import com.plusls.MasaGadget.util.MiscUtil;
import com.plusls.MasaGadget.util.RenderUtil;
import com.plusls.MasaGadget.util.SyncUtil;
import fi.dy.masa.malilib.util.data.Color4f;
import lombok.Getter;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus;
import top.hendrixshen.magiclib.MagicLib;
import top.hendrixshen.magiclib.api.event.minecraft.render.RenderLevelListener;
import top.hendrixshen.magiclib.api.render.context.LevelRenderContext;
import top.hendrixshen.magiclib.impl.render.context.RenderGlobal;

public class EntityTraceRenderer implements RenderLevelListener {
    @Getter
    private static final EntityTraceRenderer instance = new EntityTraceRenderer();

    @ApiStatus.Internal
    public void init() {
        MagicLib.getInstance().getEventManager().register(RenderLevelListener.class, this);
    }

    @Override
    public void preRenderLevel(ClientLevel level, LevelRenderContext renderContext) {
        // NO-OP
    }

    @Override
    public void postRenderLevel(ClientLevel level, LevelRenderContext renderContext) {
        float partialTick = top.hendrixshen.magiclib.util.minecraft.render.RenderUtil.getPartialTick();

        for (Entity entity : level.entitiesForRendering()) {
            if (!(entity instanceof Villager villagerEntity) ||
                    (!Configs.renderVillageHomeTracer.getBooleanValue() && !Configs.renderVillageJobSiteTracer.getBooleanValue())) {
                continue;
            }

            Villager villager = MiscUtil.cast(SyncUtil.syncEntityDataFromIntegratedServer(villagerEntity));

            if (Configs.renderVillageHomeTracer.getBooleanValue()) {
                villager.getBrain().getMemory(MemoryModuleType.HOME).ifPresent(globalPos -> {
                    Vec3 eyeVec3 = entity.getEyePosition(partialTick);
                    Vec3 bedVec3 = new Vec3(globalPos.pos().getX() + 0.5, globalPos.pos().getY() + 0.5, globalPos.pos().getZ() + 0.5);
                    RenderGlobal.disableDepthTest();
                    RenderUtil.drawConnectLine(eyeVec3, bedVec3, 0.05,
                            new Color4f(1, 1, 1),
                            Color4f.fromColor(Configs.renderVillageHomeTracerColor.getColor(), 1.0F),
                            Configs.renderVillageHomeTracerColor.getColor());
                    RenderGlobal.enableDepthTest();
                });
            }

            if (Configs.renderVillageJobSiteTracer.getBooleanValue()) {
                villager.getBrain().getMemory(MemoryModuleType.JOB_SITE).ifPresent(globalPos -> {
                    Vec3 eyeVec3 = entity.getEyePosition(partialTick);
                    Vec3 jobVev3 = new Vec3(globalPos.pos().getX() + 0.5, globalPos.pos().getY() + 0.5, globalPos.pos().getZ() + 0.5);
                    RenderGlobal.disableDepthTest();
                    RenderUtil.drawConnectLine(eyeVec3, jobVev3, 0.05,
                            new Color4f(1, 1, 1),
                            Color4f.fromColor(Configs.renderVillageJobSiteTracerColor.getColor(), 1.0F),
                            Configs.renderVillageJobSiteTracerColor.getColor());
                    RenderGlobal.enableDepthTest();
                });
            }
        }
    }
}
