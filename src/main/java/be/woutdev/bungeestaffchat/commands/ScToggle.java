package be.woutdev.bungeestaffchat.commands;

import be.woutdev.bungeestaffchat.BungeeStaffChat;
import be.woutdev.bungeestaffchat.player.ScPlayer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created by Wout on 14/04/2016.
 */
public class ScToggle extends Command
{
    public ScToggle(String name)
    {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        if (!(sender instanceof ProxiedPlayer))
        {
            sender.sendMessage(new ComponentBuilder("This command is player only.").create());
            return;
        }

        ProxiedPlayer pp = (ProxiedPlayer) sender;
        ScPlayer player = BungeeStaffChat.getInstance().getPlayerManager().getPlayer(pp.getUniqueId());

        if (pp.hasPermission("sc.toggle") || pp.hasPermission("sc.*"))
        {
            if (player.isScToggled())
            {
                pp.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',
                                                                                           BungeeStaffChat.getInstance()
                                                                                                          .getLang()
                                                                                                          .getString(
                                                                                                                  "sc-toggle-disable")))
                                       .create());
            }
            else
            {
                pp.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',
                                                                                           BungeeStaffChat.getInstance()
                                                                                                          .getLang()
                                                                                                          .getString(
                                                                                                                  "sc-toggle-enable")))
                                       .create());
            }

            player.setScToggled(!player.isScToggled());
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
