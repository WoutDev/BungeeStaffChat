package staffchat.listeners;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import staffchat.Main;
import staffchat.updater.PluginUpdater;

/**
 * Created by root on 31-8-2014.
 */
public class PlayerJoin implements Listener
{
    @EventHandler
    public void onPlayerJoin(final PostLoginEvent e)
    {
        if (e.getPlayer().hasPermission("staffchat.update") || e.getPlayer().hasPermission("staffchat.*"))
        {
            Main.getBsc().getProxy().getScheduler().runAsync(Main.getBsc(), new Runnable()
            {
                @Override
                public void run()
                {
                    PluginUpdater updater = new PluginUpdater();
                    if (updater.updateAvailable())
                    {
                        e.getPlayer()
                         .sendMessage(ChatColor.translateAlternateColorCodes('&',
                                                                             "&6There is a newer version of &3BungeeStaffChat &6available! v&2" +
                                                                             updater.getLatestVersion() +
                                                                             " &6is downloadable now!"));
                    }
                }
            });
        }
    }
}
