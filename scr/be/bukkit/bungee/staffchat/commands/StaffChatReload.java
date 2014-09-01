package be.bukkit.bungee.staffchat.commands;

import be.bukkit.bungee.staffchat.Main;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffChatReload extends Command {
    public StaffChatReload(Main instance) {
        super(Main.getConfig().getString("command-for-sc-reload").substring(1));
    }

    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer pp = (ProxiedPlayer) sender;
            if ((pp.hasPermission("staffchat.reload")) || (pp.hasPermission("staffchat.*"))) {
                Main.reloadConfig();
                pp.sendMessage(ChatColor.GREEN + "Reloaded!");
            } else {
                pp.sendMessage(ChatColor.RED + "You don't have permission to execute this command!");
                return;
            }
        } else {
            Main.reloadConfig();
            sender.sendMessage(ChatColor.GREEN + "Reloaded!");
        }
    }
}



