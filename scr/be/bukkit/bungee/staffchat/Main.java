package be.bukkit.bungee.staffchat;

import be.bukkit.bungee.staffchat.commands.*;
import be.bukkit.bungee.staffchat.listeners.PlayerChat;
import be.bukkit.bungee.staffchat.listeners.PlayerJoin;
import com.google.common.io.ByteStreams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Main extends Plugin {
    private static File configFile;
    private static File langFile;
    private static Configuration configurationFile;
    private static Configuration languageFile;
    public static Plugin bsc;
    public static String currentVersion = "1.5";
    public static String checkedVersion;

    public void onEnable() {
        bsc = this;
        setupConfig();
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new StaffChat(this));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new StaffChatToggle(this));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new StaffChatReload(this));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new StaffChatDisable(this));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new StaffChatInfo(this));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new StaffChatPriority(this));
        ProxyServer.getInstance().getPluginManager().registerListener(this, new PlayerChat());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new PlayerJoin());
        BungeeMetricsLite bml = new BungeeMetricsLite(this);
        bml.start();
    }

    @Override
    public void onDisable() {
        BungeeMetricsLite bml = new BungeeMetricsLite(this);
        bml.stop();
    }

    private void setupConfig() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        configFile = new File(getDataFolder(), "config.yml");
        langFile = new File(getDataFolder(), "lang.yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                InputStream is = getResourceAsStream("config.yml");
                OutputStream os = new FileOutputStream(configFile);
                ByteStreams.copy(is, os);
            } catch (IOException e) {
                throw new RuntimeException("Error while creating the configuration!", e);
            }
        }
        if (!langFile.exists()) {
            try {
                langFile.createNewFile();
                InputStream is = getResourceAsStream("lang.yml");
                OutputStream os = new FileOutputStream(langFile);
                ByteStreams.copy(is, os);
            } catch (IOException e) {
                throw new RuntimeException("Error while creating the language file!", e);
            }
        }
        try {
            configurationFile = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            languageFile = ConfigurationProvider.getProvider(YamlConfiguration.class).load(langFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Configuration getConfig() {
        return configurationFile;
    }
    public static Configuration getLang() { return languageFile; }

    public void saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(getConfig(), configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(getLang(), langFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reloadConfig() {
        try {
            configurationFile = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            languageFile = ConfigurationProvider.getProvider(YamlConfiguration.class).load(langFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



