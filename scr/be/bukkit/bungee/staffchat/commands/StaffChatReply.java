package be.bukkit.bungee.staffchat.commands;

import be.bukkit.bungee.staffchat.Main;
import be.bukkit.bungee.staffchat.objects.PrivateMessage;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created by root on 25-10-2014.
 */
public class StaffChatReply extends Command {
    public StaffChatReply(Main instance) {
        super(Main.getConfig().getString("command-for-sc-reply").substring(1));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer pp = (ProxiedPlayer) sender;
            if (pp.hasPermission("staffchat.msg.reply") || pp.hasPermission("staffchat.*")) {
                if (args.length <= 0) {
                    pp.sendMessage(ChatColor.RED + "Please specify message!");
                    return;
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    sb.append(args[i] + " ");
                }
                String msg = sb.toString();
                for (PrivateMessage pm : StaffChatMsg.privateMessageList) {
                    if (pm.getReceiver().equals(pp.getUniqueId())) {
                        if (Main.bsc.getProxy().getPlayer(pm.getSender()) != null) {
                            Main.bsc.getProxy().getPluginManager().dispatchCommand(pp, Main.getConfig().getString("command-for-sc-msg").substring(1) + " " + Main.bsc.getProxy().getPlayer(pm.getSender()) + " " + msg);
                            return;
                        }
                        pp.sendMessage(ChatColor.RED + "Your receiver is offline!");
                    }
                }
                pp.sendMessage(ChatColor.RED + "You can't reply because nobody sended you a message before!");
                return;
            } else {
                pp.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getLang().getString("no-permission")));
            }
        } else {
            sender.sendMessage("You can't quick reply as console!");
            return;
        }
    }
}
