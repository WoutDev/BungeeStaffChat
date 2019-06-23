package be.woutdev.bungeestaffchat.commands;

import be.woutdev.bungeestaffchat.BungeeStaffChat;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created by Wout on 15/04/2016.
 */
public class ScInfo extends Command {
    public ScInfo() {
        super("scinfo");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(new ComponentBuilder("We're using").color(ChatColor.YELLOW)
                .append(" BungeeStaffChat ")
                .color(ChatColor.AQUA)
                .append( " " + BungeeStaffChat.getVersion() + " by")
                .color(ChatColor.YELLOW)
                .append(" WoutDev")
                .color(ChatColor.AQUA)
                .append(".")
                .color(ChatColor.YELLOW)
                .create());
    }
}
