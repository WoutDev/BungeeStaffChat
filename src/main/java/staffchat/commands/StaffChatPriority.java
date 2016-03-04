package staffchat.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import staffchat.Main;

/**
 * Created by root on 31-8-2014.
 */
public class StaffChatPriority extends Command
{
    public static boolean priority = false;

    public StaffChatPriority(Main instance)
    {
        super(Main.getConfig().getString("command-for-sc-priority").substring(1));
    }

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        if (!sender.hasPermission("staffchat.*") && !sender.hasPermission("staffchat.priority"))
        {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getLang().getString("no-permission")));
            return;
        }
        if (priority)
        {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getLang()
                                                                               .getString(
                                                                                       "disabled-staffchat-priority")));
            priority = false;
        }
        else
        {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getLang()
                                                                               .getString(
                                                                                       "enabled-staffchat-priority")));
            priority = true;
        }
    }
}
