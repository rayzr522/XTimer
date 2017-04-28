package com.rayzr522.xtimer.command;

import com.rayzr522.xtimer.XTimer;
import com.rayzr522.xtimer.utils.CountdownManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import static com.rayzr522.xtimer.utils.ArrayUtils.join;
import static com.rayzr522.xtimer.utils.ArrayUtils.slice;

public class CommandTimer implements CommandExecutor, Listener {

    private XTimer plugin;

    public CommandTimer(XTimer plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
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

        CountdownManager cm = plugin.getCountdownManager();

        if (cm.isCountingDown(player)) {
            sender.sendMessage(plugin.getMessage("already-busy"));
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

        cm.startCountdown(player, seconds, command);

        return true;
    }

    private void usage(CommandSender sender) {
        sender.sendMessage(plugin.getMessage("usage-timer"));
    }
}
