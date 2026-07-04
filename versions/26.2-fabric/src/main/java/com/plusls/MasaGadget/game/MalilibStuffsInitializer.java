package com.plusls.MasaGadget.game;

import com.plusls.MasaGadget.SharedConstants;
import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.InitializationHandler;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.hotkeys.IKeybindProvider;


public class MalilibStuffsInitializer {
    public static void init() {
        InitializationHandler.getInstance().registerInitializationHandler(() ->
                ConfigManager.getInstance().registerConfigHandler(SharedConstants.getModIdentifier(),
                        SharedConstants.getConfigHandler()));
        Configs.init();
        InputEventHandler.getKeybindManager().registerKeybindProvider(
                (IKeybindProvider) SharedConstants.getConfigManager());
    }

}
