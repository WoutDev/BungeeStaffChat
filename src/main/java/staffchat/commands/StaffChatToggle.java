package staffchat.commands;

import staffchat.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffChatToggle extends Command {
    public static List<UUID> toggledPlayers = new ArrayList();

    public StaffChatToggle(Main instance) {
        super(Main.getConfig().getString("command-for-sc-toggle").substring(1));
    }

    public void execute(CommandSender sender, String[] args) {
        if ((sender instanceof ProxiedPlayer)) {
            ProxiedPlayer pp = (ProxiedPlayer) sender;
            if ((!pp.hasPermission("staffchat.toggle")) && (!pp.hasPermission("staffchat.*"))) {
                pp.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getLang().getString("no-permission")));
                return;
            }
            UUID ppuuid = pp.getUniqueId();
            if (toggledPlayers.contains(ppuuid)) {
                pp.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getLang().getString("disabled-staffchat-only")));
                toggledPlayers.remove(ppuuid);
                return;
            }
            pp.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getLang().getString("enabled-staffchat-only")));
            toggledPlayers.add(ppuuid);
            return;
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getLang().getString("console-trys-to-toggle-staffchat")));
    }
}



