package staffchat.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import staffchat.Main;

/**
 * Created by root on 31-8-2014.
 */
public class StaffChatInfo extends Command
{
    public StaffChatInfo(Main instance)
    {
        super("scinfo");
    }

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        sender.sendMessage(ChatColor.RED + ">>>=== " + ChatColor.GOLD + "BungeeStaffChat" + ChatColor.RED + " ===<<<");
        sender.sendMessage(ChatColor.AQUA + "Version: " + ChatColor.DARK_GREEN + Main.currentVersion);
        sender.sendMessage(ChatColor.AQUA + "Plugin by: " + ChatColor.DARK_GREEN + "WoutDev");
    }
}
