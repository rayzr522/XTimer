package com.rayzr522.xtimer.utils;

import com.rayzr522.xtimer.XTimer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Rayzr522 on 4/28/17.
 */
public class Countdown extends BukkitRunnable {
    private final XTimer plugin;
    private final Player player;
    private final String command;

    private int time;

    public Countdown(XTimer plugin, Player player, int duration, String command) {
        this.plugin = plugin;
        this.player = player;
        this.time = duration;
        this.command = command;
    }

    public void start() {
        plugin.getXPManager().store(player);
        runTaskTimer(plugin, 0L, 20L);
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        super.cancel();
        plugin.getXPManager().restore(player);
    }

    @Override
    public void run() {
        if (time <= 0) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);

            plugin.getCountdownManager().forceStopCountdown(player);
            return;
        }

        plugin.getXPManager().setExactLevel(player, time);
        time--;
    }
}
