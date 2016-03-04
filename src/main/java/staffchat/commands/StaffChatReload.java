package staffchat.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import staffchat.Main;

public class StaffChatReload extends Command
{
    public StaffChatReload(Main instance)
    {
        super(Main.getConfig().getString("command-for-sc-reload").substring(1));
    }

    public void execute(CommandSender sender, String[] args)
    {
        if (sender instanceof ProxiedPlayer)
        {
            ProxiedPlayer pp = (ProxiedPlayer) sender;
            if ((pp.hasPermission("staffchat.reload")) || (pp.hasPermission("staffchat.*")))
            {
                Main.reloadConfig();
                pp.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getLang().getString("reload")));
            }
            else
            {
                pp.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getLang().getString("no-permission")));
                return;
            }
        }
        else
        {
            Main.reloadConfig();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getLang().getString("reload")));
        }
    }
}



