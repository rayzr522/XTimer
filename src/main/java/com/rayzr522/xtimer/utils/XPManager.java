package com.rayzr522.xtimer.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by Rayzr522 on 4/28/17.
 */
public class XPManager implements Listener {
    private Map<UUID, Integer> playerLevels = new HashMap<>();

    public XPManager(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void store(Player player) {
        playerLevels.put(player.getUniqueId(), player.getTotalExperience());
    }

    public void restore(Player player) {
        if (!playerLevels.containsKey(player.getUniqueId())) {
            return;
        }

        int xp = playerLevels.remove(player.getUniqueId());
        player.setTotalExperience(xp);
    }

    public void restoreAll() {
        playerLevels.keySet().stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .forEach(this::restore);
    }

    public void setExactLevel(Player player, int level) {
        player.setLevel(level);
        player.setExp(0);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        restore(e.getPlayer());
    }
}
