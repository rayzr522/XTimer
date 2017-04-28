package com.rayzr522.xtimer;

import com.rayzr522.xtimer.command.CommandTimer;
import com.rayzr522.xtimer.command.CommandXTimer;
import com.rayzr522.xtimer.utils.CountdownManager;
import com.rayzr522.xtimer.utils.XPManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Rayzr
 */
public class XTimer extends JavaPlugin {
    private static XTimer instance;
    private XPManager xpManager;
    private CountdownManager countdownManager;

    public static XTimer getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        xpManager = new XPManager(this);
        countdownManager = new CountdownManager(this);

        reload();

        getCommand("xtimer").setExecutor(new CommandXTimer(this));
        getCommand("timer").setExecutor(new CommandTimer(this));
    }

    @Override
    public void onDisable() {
        xpManager.restoreAll();
        instance = null;
    }

    /**
     * (Re)loads all configs from the disk
     */
    public void reload() {
        saveDefaultConfig();
        reloadConfig();
    }

    public String getMessage(String key) {
        return ChatColor.translateAlternateColorCodes('&',
                getConfig().getString("message-prefix") + getConfig().getString("messages." + key)
        );
    }

    public boolean checkPermission(CommandSender sender, String permission, boolean sendMessage) {
        String fullPermission = String.format("CommandTimer.%s", permission);
        if (!sender.hasPermission(fullPermission)) {
            if (sendMessage) {
                sender.sendMessage(String.format(getMessage("no-permission"), fullPermission));
            }
            return false;
        }
        return true;
    }

    public XPManager getXPManager() {
        return xpManager;
    }

    public CountdownManager getCountdownManager() {
        return countdownManager;
    }
}
