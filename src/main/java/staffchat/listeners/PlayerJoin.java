package staffchat.listeners;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import staffchat.Main;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by root on 31-8-2014.
 */
public class PlayerJoin implements Listener
{

    @EventHandler
    public void onPlayerJoin(PostLoginEvent e)
    {
        if (e.getPlayer().hasPermission("staffchat.update") || e.getPlayer().hasPermission("staffchat.*"))
        {
            if (checkForUpdate())
            {
                e.getPlayer()
                 .sendMessage(ChatColor.translateAlternateColorCodes('&',
                                                                     "&6There is a newer version of &3BungeeStaffChat &6available! v&2" +
                                                                     Main.checkedVersion + " &6is downloadable now!"));
                return;
            }
        }
    }

    private boolean checkForUpdate()
    {
        String v = Main.CURRENT_VERSION;
        try
        {
            v = new Scanner(new URL("http://www.woutdev.be/bungeestaffchat/version.html").openStream(),
                            "UTF-8").useDelimiter("\\A").next();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        if (!v.equalsIgnoreCase(Main.CURRENT_VERSION))
        {
            Main.checkedVersion = v;
            return true;
        }
        return false;
    }
}
