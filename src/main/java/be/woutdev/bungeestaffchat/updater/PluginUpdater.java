package be.woutdev.bungeestaffchat.updater;

import be.woutdev.bungeestaffchat.BungeeStaffChat;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Wout on 15/04/2016.
 */
public class PluginUpdater implements Runnable
{
    private ProxiedPlayer pp;

    public PluginUpdater(ProxiedPlayer pp)
    {
        this.pp = pp;
    }

    @Override
    public void run()
    {
        try
        {
            URL url = new URL("http://www.woutdev.be/staffchat/version.txt");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() != 200)
            {
                if (BungeeStaffChat.getInstance().getProxy().getPlayer(pp.getUniqueId()) != null)
                {
                    pp.sendMessage(new ComponentBuilder(
                            "Failed to check for new BungeeStaffChat update. Please check yourself to make sure you are up to date.")
                                           .color(
                                                   ChatColor.GOLD)
                                           .create());
                    return;
                }
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String v = reader.readLine();

            if (!v.equalsIgnoreCase(BungeeStaffChat.getVersion()))
            {
                if (BungeeStaffChat.getInstance().getProxy().getPlayer(pp.getUniqueId()) != null)
                {
                    pp.sendMessage(new ComponentBuilder("New BungeeStaffChat update found! Please update to " + v +
                                                        " to make sure you are up to date.").color(
                            ChatColor.GOLD).create());
                }
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
