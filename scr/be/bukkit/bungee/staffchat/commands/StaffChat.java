package be.bukkit.bungee.staffchat.commands;

import be.bukkit.bungee.staffchat.Main;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffChat extends Command {
    public StaffChat(Main instance) {
        super(Main.getConfig().getString("command-for-sc").substring(1));
    }

   public void execute(CommandSender sender, String[] args) {
       String s;
       String server;
       if (sender instanceof ProxiedPlayer) {
           ProxiedPlayer pp = (ProxiedPlayer) sender;
           if ((!pp.hasPermission("staffchat.use")) && (!pp.hasPermission("staffchat.*"))) {
               pp.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getLang().getString("no-permission")));
               return;
           }
           if (StaffChatDisable.disabled.contains(pp.getUniqueId())) {
               pp.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getLang().getString("sc-disabled-enable-first")));
               return;
           }
           if (StaffChatPriority.priority) {
               if (!pp.hasPermission("staffchat.*") && !pp.hasPermission("staffchat.priority")) {
                   pp.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getLang().getString("not-allowed-priority-mode")));
                   return;
               }
           }
           if (checkArgs(sender, args)) {
               StringBuilder sb = new StringBuilder("");
               for (int i = 0; i < args.length; i++) {
                   sb.append(args[i]).append(" ");
               }
               s = sb.toString();
               server = ((ProxiedPlayer) sender).getServer().getInfo().getName();
               for (ProxiedPlayer pl : ProxyServer.getInstance().getPlayers()) {
                   if ((pl.hasPermission("staffchat.receive")) || (pl.hasPermission("staffchat.*")) || (pl.hasPermission("staffchat.use"))) {
                       if (StaffChatDisable.disabled.contains(pl.getUniqueId())) {
                           return;
                       }
                       if (StaffChatPriority.priority) {
                           if (!pl.hasPermission("staffchat.priority") && !pl.hasPermission("staffchat.*")) {
                               return;
                           }
                       }
                       pl.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getConfig().getString("staffchat-template").replaceAll("%player%", pp.getName()).replaceAll("%message%", s)).replaceAll("%server%", server));
                       return;
                   }
               }
           } else {
               return;
           }
       } else {
           if (checkArgs(sender, args)) {
               StringBuilder sb = new StringBuilder("");
               for (int i = 0; i < args.length; i++) {
                   sb.append(args[i]).append(" ");
               }
               s = sb.toString();
               server = ChatColor.translateAlternateColorCodes('&', Main.getLang().getString("console-send-message-server"));
               for (ProxiedPlayer pl : ProxyServer.getInstance().getPlayers()) {
                   if ((pl.hasPermission("staffchat.receive")) || (pl.hasPermission("staffchat.*")) || (pl.hasPermission("staffchat.use"))) {
                       if (StaffChatDisable.disabled.contains(pl.getUniqueId())) {
                           return;
                       }
                       if (StaffChatPriority.priority) {
                           if (!pl.hasPermission("staffchat.priority") && !pl.hasPermission("staffchat.*")) {
                               return;
                           }
                       }
                       pl.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getConfig().getString("staffchat-template").replaceAll("%player%", ChatColor.translateAlternateColorCodes('&', Main.getLang().getString("console-prefix"))).replaceAll("%message%", s).replaceAll("%server%", server)));
                       return;
                   }
               }
           } else {
               return;
           }
       }
   }

    private boolean checkArgs(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getLang().getString("specify-message")));
            return false;
        }
        return true;
    }
}



