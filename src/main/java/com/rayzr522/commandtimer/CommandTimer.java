package com.rayzr522.commandtimer;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Rayzr
 */
public class CommandTimer extends JavaPlugin {
    private static CommandTimer instance;

    public static CommandTimer getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        reload();
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    /**
     * (Re)loads all configs from the disk
     */
    public void reload() {
        saveDefaultConfig();
        reloadConfig();
    }

}
