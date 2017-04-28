package com.rayzr522.xtimer.command;

import com.rayzr522.xtimer.XTimer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static com.rayzr522.xtimer.utils.ArrayUtils.join;
import static com.rayzr522.xtimer.utils.ArrayUtils.slice;

public class CommandTimer implements CommandExecutor {
    private XTimer plugin;

    public CommandTimer(XTimer plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!plugin.checkPermission(sender, "timer", true)) {
            return true;
        }

        if (args.length < 3) {
            usage(sender);
            return true;
        }

        @SuppressWarnings("deprecation")
        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(plugin.getMessage("invalid-player"));
            return true;
        }

        int seconds = 0;
        try {
            seconds = Integer.parseInt(args[1]);
        } catch (NumberFormatException ignore) {
        }

        if (seconds < 1) {
            sender.sendMessage(plugin.getMessage("invalid-time"));
            return true;
        }

        final String command = join(slice(args, 2), " ").replace("{player}", player.getName());

        final int startTime = seconds;

        plugin.getXPManager().store(player);

        new BukkitRunnable() {
            int time = startTime;

            @Override
            public void run() {
                if (time <= 0) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                    plugin.getXPManager().restore(player);

                    cancel();
                    return;
                }

                plugin.getXPManager().setExactLevel(player, time);
                time--;
            }
        }.runTaskTimer(plugin, 0L, 20L);


        return true;
    }

    private void usage(CommandSender sender) {
        sender.sendMessage(plugin.getMessage("usage-timer"));
    }
}
