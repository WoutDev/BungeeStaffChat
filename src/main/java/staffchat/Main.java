package staffchat;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import staffchat.commands.*;
import staffchat.listeners.PlayerChat;
import staffchat.listeners.PlayerJoin;

import java.io.*;
import java.nio.file.Files;

public class Main extends Plugin
{
    public static final String CURRENT_VERSION = "1.6.0";
    public static Plugin bsc;
    public static String checkedVersion;
    private static File configFile;
    private static File langFile;
    private static Configuration configurationFile;
    private static Configuration languageFile;

    public static Configuration getConfig()
    {
        return configurationFile;
    }

    public static Configuration getLang()
    {
        return languageFile;
    }

    public static void reloadConfig()
    {
        try
        {
            configurationFile = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            languageFile = ConfigurationProvider.getProvider(YamlConfiguration.class).load(langFile);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void onEnable()
    {
        bsc = this;

        setupConfig();

        ProxyServer.getInstance().getPluginManager().registerCommand(this, new StaffChat(this));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new StaffChatToggle(this));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new StaffChatReload(this));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new StaffChatDisable(this));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new StaffChatInfo(this));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new StaffChatPriority(this));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new StaffChatMsg(this));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new StaffChatReply(this));

        ProxyServer.getInstance().getPluginManager().registerListener(this, new PlayerChat());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new PlayerJoin());

        BungeeMetricsLite bml = new BungeeMetricsLite(this);
        bml.start();
    }

    @Override
    public void onDisable()
    {
        BungeeMetricsLite bml = new BungeeMetricsLite(this);
        bml.stop();
    }

    private void setupConfig()
    {
        if (!getDataFolder().exists())
        {
            getDataFolder().mkdir();
        }

        configFile = new File(getDataFolder(), "config.yml");
        langFile = new File(getDataFolder(), "lang.yml");

        if (!configFile.exists())
        {
            try
            {
                Files.copy(getResourceAsStream("config.yml"), configFile.toPath());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        if (!langFile.exists())
        {
            try
            {
                Files.copy(getResourceAsStream("lang.yml"), langFile.toPath());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        try
        {
            configurationFile = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            languageFile = ConfigurationProvider.getProvider(YamlConfiguration.class).load(langFile);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}



