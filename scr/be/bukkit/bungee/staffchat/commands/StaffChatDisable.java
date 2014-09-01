package be.bukkit.bungee.staffchat.commands;

import be.bukkit.bungee.staffchat.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffChatDisable extends Command {
    public static List<UUID> disabled = new ArrayList();

    public StaffChatDisable(Main instance) {
        super(Main.getConfig().getString("command-for-sc-disable").substring(1));
    }

    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer pp = (ProxiedPlayer) sender;
            if ((pp.hasPermission("staffchat.disable")) || (pp.hasPermission("staffchat.*"))) {
                if (disabled.contains(pp.getUniqueId())) {
                    pp.sendMessage(ChatColor.GREEN + "You enabled the staffchat again!");
                    disabled.remove(pp.getUniqueId());
                    return;
                }
                pp.sendMessage(ChatColor.RED + "You disabled the staffchat!");
                disabled.add(pp.getUniqueId());
                return;
            }
            pp.sendMessage(ChatColor.RED + "You don't have permission to execute this command!");
            return;
        }
        sender.sendMessage("You can't disable the staffchat as an console.");
    }
}



