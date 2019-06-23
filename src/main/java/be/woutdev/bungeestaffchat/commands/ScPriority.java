package be.woutdev.bungeestaffchat.commands;

import be.woutdev.bungeestaffchat.BungeeStaffChat;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created by Wout on 14/04/2016.
 */
public class ScPriority extends Command {
    public ScPriority(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender.hasPermission("sc.priority") || sender.hasPermission("sc.*")) {
            if (BungeeStaffChat.getInstance().isScPriorityEnabled()) {
                for (ProxiedPlayer pp : BungeeStaffChat.getInstance().getProxy().getPlayers()) {
                    if (pp.hasPermission("sc.priority") || pp.hasPermission("sc.priority.notify") ||
                            pp.hasPermission("sc.*")) {
                        pp.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',
                                BungeeStaffChat.getInstance()
                                        .getLang()
                                        .getString(
                                                "sc-priority-disabled")))
                                .create());
                    }
                }
            } else {
                for (ProxiedPlayer pp : BungeeStaffChat.getInstance().getProxy().getPlayers()) {
                    if (pp.hasPermission("sc.priority") || pp.hasPermission("sc.priority.notify") ||
                            pp.hasPermission("sc.*")) {
                        pp.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',
                                BungeeStaffChat.getInstance()
                                        .getLang()
                                        .getString(
                                                "sc-priority-enabled")))
                                .create());
                    }
                }
            }

            BungeeStaffChat.getInstance().setScPriorityEnabled(!BungeeStaffChat.getInstance().isScPriorityEnabled());
        } else {
            sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',
                    BungeeStaffChat.getInstance()
                            .getLang()
                            .getString(
                                    "no-permission")))
                    .create());
        }
    }
}
