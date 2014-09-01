package be.bukkit.bungee.staffchat.commands;

import be.bukkit.bungee.staffchat.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created by root on 31-8-2014.
 */
public class StaffChatPriority extends Command {
    public static boolean priority = false;

    public StaffChatPriority(Main instance)  {
        super(Main.getConfig().getString("command-for-sc-priority").substring(1));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("staffchat.*") && !sender.hasPermission("staffchat.priority")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to execute this command!");
            return;
        }
        if (priority) {
            sender.sendMessage(ChatColor.RED + "You disabled StaffChat priority mode!");
            priority = false;
        } else {
            sender.sendMessage(ChatColor.GREEN + "You enabled StaffChat priority mode!");
            priority = true;
        }
    }
}
