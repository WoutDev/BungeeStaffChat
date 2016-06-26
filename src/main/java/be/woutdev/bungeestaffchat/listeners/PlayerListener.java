package be.woutdev.bungeestaffchat.listeners;

import be.woutdev.bungeestaffchat.BungeeStaffChat;
import be.woutdev.bungeestaffchat.player.ScPlayer;
import be.woutdev.bungeestaffchat.updater.PluginUpdater;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by Wout on 14/04/2016.
 */
public class PlayerListener implements Listener
{
    @EventHandler
    public void onPlayerConnect(PostLoginEvent e)
    {
        BungeeStaffChat.getInstance().getPlayerManager().addPlayer(e.getPlayer().getUniqueId());

        if (e.getPlayer().hasPermission("sc.notifyupdate") || e.getPlayer().hasPermission("sc.*"))
        {
            BungeeStaffChat.getInstance()
                           .getProxy()
                           .getScheduler()
                           .runAsync(BungeeStaffChat.getInstance(), new PluginUpdater(e.getPlayer()));
        }
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerDisconnectEvent e)
    {
        BungeeStaffChat.getInstance().getPlayerManager().removePlayer(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerChat(ChatEvent e)
    {
        if (e.getMessage().startsWith("/"))
        {
            return;
        }

        ProxiedPlayer pp = (ProxiedPlayer) e.getSender();

        if (pp.hasPermission("sc.toggle") || pp.hasPermission("sc.*"))
        {
            ScPlayer player = BungeeStaffChat.getInstance().getPlayerManager().getPlayer(pp.getUniqueId());

            if (player.isScToggled())
            {
                e.setCancelled(true);

                BungeeStaffChat.getInstance()
                               .getProxy()
                               .getPluginManager()
                               .dispatchCommand(pp, BungeeStaffChat.getInstance()
                                                                   .getConfig()
                                                                   .getString("sc-command")
                                                                   .replaceAll("/", "") + " " + e.getMessage());
                return;
            }
        }

        if (BungeeStaffChat.getInstance().isShortcutEnabled())
        {
            if (pp.hasPermission("sc.shortcut") || pp.hasPermission("sc.*"))
            {
                System.out.println(BungeeStaffChat.getInstance().getShortcut());
                if (e.getMessage().toCharArray()[0] == BungeeStaffChat.getInstance().getShortcut())
                {
                    if (e.getMessage().length() == 1)
                        return;

                    e.setCancelled(true);

                    BungeeStaffChat.getInstance()
                                   .getProxy()
                                   .getPluginManager()
                                   .dispatchCommand(pp, BungeeStaffChat.getInstance()
                                                                       .getConfig()
                                                                       .getString("sc-command")
                                                                       .replaceAll("/", "") + " " +
                                                        e.getMessage().substring(1, e.getMessage().length()));
                }
            }
        }
    }
}
