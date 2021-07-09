package be.woutdev.bungeestaffchat;

import be.woutdev.bungeestaffchat.commands.*;
import be.woutdev.bungeestaffchat.listeners.PlayerListener;
import be.woutdev.bungeestaffchat.player.ScPlayerManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Wout on 14/04/2016.
 */
public class BungeeStaffChat extends Plugin
{
    private static BungeeStaffChat instance;
    private ScPlayerManager playerManager;
    private Configuration config;
    private Configuration lang;
    private boolean shortcutEnabled;
    private char shortcut;
    private String scLayout;
    private String scMsgLayout;
    private String scReplyLayout;
    private String scSpyLayout;
    private boolean scPriorityEnabled;
    private boolean bungeePerms;
    private boolean keepLog;
    private Path logPath;

    public static BungeeStaffChat getInstance()
    {
        return instance;
    }

    @Override
    public void onLoad()
    {
        setupConfig();
    }

    @Override
    public void onEnable()
    {
        instance = this;
        playerManager = new ScPlayerManager();

        getProxy().getPluginManager()
                  .registerCommand(this, new Sc(getConfig().getString("sc-command", "/sc").replaceAll("/", "")));
        getProxy().getPluginManager()
                  .registerCommand(this, new ScDisable(getConfig().getString("scdisable-command", "/scdisable").replaceAll("/", "")));
        getProxy().getPluginManager()
                  .registerCommand(this, new ScReload(getConfig().getString("screload-command", "/screload").replaceAll("/", "")));
        getProxy().getPluginManager()
                  .registerCommand(this, new ScToggle(getConfig().getString("sctoggle-command", "/sctoggle").replaceAll("/", "")));
        getProxy().getPluginManager()
                  .registerCommand(this,
                                   new ScPriority(getConfig().getString("scpriority-command", "/scpriority").replaceAll("/", "")));
        getProxy().getPluginManager()
                  .registerCommand(this, new ScMsg(getConfig().getString("scmsg-command", "/scmsg").replaceAll("/", "")));
        getProxy().getPluginManager()
                  .registerCommand(this, new ScReply(getConfig().getString("screply-command", "/screply").replaceAll("/", "")));
        getProxy().getPluginManager().registerCommand(this, new ScSpy(getConfig().getString("scspy-command", "/scspy").replaceAll("/", "")));
        getProxy().getPluginManager().registerCommand(this, new ScInfo());

        getProxy().getPluginManager().registerListener(this, new PlayerListener());

        scPriorityEnabled = false;

        if (getProxy().getPluginManager().getPlugin("BungeePerms") != null)
        {
            bungeePerms = true;
        }
    }

    private void setupConfig()
    {
        Path dirPath = getDataFolder().toPath();
        Path configPath = Paths.get(dirPath.toAbsolutePath().toString(), "config.yml");
        Path langPath = Paths.get(dirPath.toAbsolutePath().toString(), "lang.yml");

        if (!Files.exists(dirPath))
        {
            try
            {
                Files.createDirectory(dirPath);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        if (!Files.exists(configPath))
        {
            try
            {
                Files.copy(getResourceAsStream("config.yml"), configPath);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        if (!Files.exists(langPath))
        {
            try
            {
                Files.copy(getResourceAsStream("lang.yml"), langPath);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        try
        {
            config = YamlConfiguration.getProvider(YamlConfiguration.class).load(configPath.toFile());
            lang = YamlConfiguration.getProvider(YamlConfiguration.class).load(langPath.toFile());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        // Load configuration settings
        scLayout = getConfig().getString("sc-layout");
        scMsgLayout = getConfig().getString("scmsg-layout");
        scReplyLayout = getConfig().getString("screply-layout");
        scSpyLayout = getConfig().getString("scspy-layout");
        shortcutEnabled = getConfig().getBoolean("shortcut-enabled", false);
        keepLog = getConfig().getBoolean("keep-log", false);

        if (shortcutEnabled)
        {
            shortcut = getConfig().getString("shortcut").toCharArray()[0];
        }

        if (keepLog)
        {
            logPath = Paths.get(dirPath.toAbsolutePath().toString(), "staffchat.log");

            if (!Files.exists(logPath))
            {
                try
                {
                    Files.createFile(logPath);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public Configuration getConfig()
    {
        return config;
    }

    public Configuration getLang()
    {
        return lang;
    }

    public String getScReplyLayout()
    {
        return ChatColor.translateAlternateColorCodes('&', scReplyLayout);
    }

    public String getScMsgLayout()
    {
        return ChatColor.translateAlternateColorCodes('&', scMsgLayout);
    }

    public String getScLayout()
    {
        return ChatColor.translateAlternateColorCodes('&', scLayout);
    }

    public String getScSpyLayout()
    {
        return scSpyLayout;
    }

    public char getShortcut()
    {
        return shortcut;
    }

    public boolean isShortcutEnabled()
    {
        return shortcutEnabled;
    }

    public ScPlayerManager getPlayerManager()
    {
        return playerManager;
    }

    public boolean isScPriorityEnabled()
    {
        return scPriorityEnabled;
    }

    public void setScPriorityEnabled(boolean scPriorityEnabled)
    {
        this.scPriorityEnabled = scPriorityEnabled;
    }

    public boolean isBungeePerms()
    {
        return bungeePerms;
    }

    public boolean keepLog()
    {
        return keepLog;
    }

    public void appendToLog(String line)
    {
        try
        {
            BufferedWriter out = Files.newBufferedWriter(logPath, Charset.defaultCharset(),
                                                         StandardOpenOption.APPEND);
            out.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append(": ").append(line);
            out.newLine();
            out.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void reload()
    {
        try
        {
            config = YamlConfiguration.getProvider(YamlConfiguration.class).load(Paths.get(
                    getDataFolder().getAbsolutePath(), "config.yml").toFile());
            lang = YamlConfiguration.getProvider(YamlConfiguration.class)
                                    .load(Paths.get(getDataFolder().getAbsolutePath(), "lang.yml").toFile());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        // Load configuration settings
        scLayout = getConfig().getString("sc-layout");
        scMsgLayout = getConfig().getString("scmsg-layout");
        scReplyLayout = getConfig().getString("screply-layout");
        scSpyLayout = getConfig().getString("scspy-layout");
        shortcutEnabled = getConfig().getBoolean("shortcut-enabled", false);
        keepLog = getConfig().getBoolean("keep-log", false);

        if (shortcutEnabled)
        {
            shortcut = getConfig().getString("shortcut").toCharArray()[0];
        }

        if (keepLog)
        {
            logPath = Paths.get(getDataFolder().toPath().toAbsolutePath().toString(), "staffchat.log");

            if (!Files.exists(logPath))
            {
                try
                {
                    Files.createFile(logPath);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}