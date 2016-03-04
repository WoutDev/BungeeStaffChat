package staffchat.listeners;

import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import staffchat.Main;
import staffchat.commands.StaffChatToggle;

public class PlayerChat implements Listener
{
    @EventHandler
    public void onPlayerChat(ChatEvent event)
    {
        Connection ppcon = event.getSender();
        if (ppcon instanceof ProxiedPlayer)
        {
            ProxiedPlayer pp = (ProxiedPlayer) ppcon;
            if ((StaffChatToggle.toggledPlayers.contains(pp.getUniqueId())) && (!event.isCommand()) &&
                ((pp.hasPermission("staffchat.use")) || (pp.hasPermission("staffchat.*"))))
            {
                Main.bsc.getProxy()
                        .getPluginManager()
                        .dispatchCommand(pp, Main.getConfig().getString("command-for-sc").substring(1) + " " +
                                             event.getMessage());
                event.setCancelled(true);
                return;
            }
            if ((Main.getConfig().getBoolean("use-shortcut")) &&
                (Main.getConfig().getString("shortcut").equalsIgnoreCase(event.getMessage().substring(0, 1))) &&
                ((pp.hasPermission("staffchat.shortcut")) || (pp.hasPermission("staffchat.*"))))
            {
                Main.bsc.getProxy()
                        .getPluginManager()
                        .dispatchCommand(pp, Main.getConfig().getString("command-for-sc").substring(1) + " " +
                                             event.getMessage().substring(1));
                event.setCancelled(true);
            }
        }
    }
}



