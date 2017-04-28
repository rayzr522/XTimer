package com.rayzr522.xtimer.utils;

import com.rayzr522.xtimer.XTimer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Rayzr522 on 4/28/17.
 */
public class CountdownManager implements Listener {
    private Map<UUID, Countdown> countdowns = new HashMap<>();

    private XTimer plugin;

    public CountdownManager(XTimer plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void startCountdown(Player player, int seconds, String command) {
        if (isCountingDown(player)) {
            throw new IllegalStateException("A countdown is already running for " + player.getUniqueId().toString());
        }

        Countdown countdown = new Countdown(plugin, player, seconds, command);
        countdown.start();

        countdowns.put(player.getUniqueId(), countdown);
    }

    void forceStopCountdown(Player player) {
        if (!isCountingDown(player)) {
            return;
        }

        Countdown countdown = countdowns.remove(player.getUniqueId());
        countdown.cancel();
    }

    public boolean isCountingDown(Player player) {
        return countdowns.containsKey(player.getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        forceStopCountdown(e.getPlayer());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        forceStopCountdown(e.getEntity());
    }

}
