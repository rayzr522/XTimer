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
        setTotalExp(player, xp);
    }

    public void restoreAll() {
        playerLevels.keySet().stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .forEach(this::restore);
    }

    public void setTotalExp(Player player, int xp) {
        player.setLevel(0);
        player.setExp(0);
        player.setTotalExperience(0);

        int level = 0;
        while (xp > requiredXP(level)) {
            xp -= requiredXP(level);
            level++;
        }

        player.setLevel(level);
        player.setExp(((float) xp) / requiredXP(level));
    }

    /**
     * Ported from Essentials. Why is Bukkit XP math so screwy?
     * @param player The player to get the XP of
     * @return The total XP of the player
     */
    public int getTotalExp(Player player) {
        int exp = Math.round(requiredXP(player.getLevel()) * player.getExp());
        int level = player.getLevel();

        while (level > 0) {
            level--;
            exp += requiredXP(level);
        }

        if (exp < 0) {
            exp = Integer.MAX_VALUE;
        }

        return exp;
    }

    private int requiredXP(int level) {
        if (level <= 15) {
            return (2 * level) + 7;
        }

        if (level >= 16 && level <= 30) {
            return (5 * level) - 38;
        }

        return (9 * level) - 158;
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
