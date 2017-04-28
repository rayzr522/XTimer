package com.rayzr522.xtimer.command;

import com.rayzr522.xtimer.XTimer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Rayzr522 on 4/28/17.
 */
public class CommandXTimer implements CommandExecutor {
    private XTimer plugin;

    public CommandXTimer(XTimer plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!plugin.checkPermission(sender, "admin", true)) {
            return true;
        }

        if (args.length < 1) {
            usage(sender);
            return true;
        }

        String sub = args[0].toLowerCase();
        if (sub.equals("reload")) {
            plugin.reload();

            sender.sendMessage(plugin.getMessage("config-reloaded"));
            return true;
        } else {
            usage(sender);
        }

        return true;
    }

    private void usage(CommandSender sender) {
        sender.sendMessage(plugin.getMessage("usage-admin"));
    }
}
