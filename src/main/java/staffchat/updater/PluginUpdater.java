package staffchat.updater;

import staffchat.Main;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Wout on 6/03/2016.
 */
public class PluginUpdater
{
    private static final String URL = "http://www.woutdev.be/bungeestaffchat/version.txt";
    private String latestVersion;

    public PluginUpdater()
    {

    }

    public boolean updateAvailable()
    {
        String v = Main.getCurrentVersion();

        try
        {
            v = new Scanner(new java.net.URL(URL).openStream(), "UTF-8").useDelimiter("\\A").next();
            latestVersion = v;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return !v.equalsIgnoreCase(Main.getCurrentVersion());
    }

    public String getLatestVersion()
    {
        return latestVersion;
    }
}
