package be.woutdev.bungeestaffchat.commands;

import be.woutdev.bungeestaffchat.BungeeStaffChat;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created by Wout on 14/04/2016.
 */
public class ScReload extends Command
{
    public ScReload(String name)
    {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        if (sender.hasPermission("sc.reload") || sender.hasPermission("sc.*"))
        {
            BungeeStaffChat.getInstance().reload();

            sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',
                                                                                           BungeeStaffChat.getInstance()
                                                                                                          .getLang()
                                                                                                          .getString(
                                                                                                                  "sc-reload")))
                                       .create());
        }
        else
        {
            sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',
                                                                                           BungeeStaffChat.getInstance()
                                                                                                          .getLang()
                                                                                                          .getString(
                                                                                                                  "no-permission")))
                                       .create());
        }
    }
}
