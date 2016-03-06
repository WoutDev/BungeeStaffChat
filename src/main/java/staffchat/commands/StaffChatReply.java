package staffchat.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import staffchat.Main;
import staffchat.objects.PrivateMessage;

/**
 * Created by root on 25-10-2014.
 */
public class StaffChatReply extends Command
{
    public StaffChatReply(Main instance)
    {
        super(Main.getConfig().getString("command-for-sc-reply").substring(1));
    }

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        if (sender instanceof ProxiedPlayer)
        {
            ProxiedPlayer pp = (ProxiedPlayer) sender;
            if (pp.hasPermission("staffchat.msg.reply") || pp.hasPermission("staffchat.*"))
            {
                if (args.length <= 0)
                {
                    pp.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getLang()
                                                                                   .getString(
                                                                                           "sc-reply-specify-message")));
                    return;
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < args.length; i++)
                {
                    sb.append(args[i] + " ");
                }
                String msg = sb.toString();
                for (PrivateMessage pm : StaffChatMsg.privateMessageList)
                {
                    if (pm.getReceiver().equals(pp.getUniqueId()))
                    {
                        if (Main.getBsc().getProxy().getPlayer(pm.getSender()) != null)
                        {
                            Main.getBsc().getProxy()
                                .getPluginManager()
                                .dispatchCommand(pp, Main.getConfig().getString("command-for-sc-msg").substring(1) +
                                                     " " + Main.getBsc().getProxy().getPlayer(pm.getSender()) + " " +
                                                     msg);
                            return;
                        }
                        pp.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getLang()
                                                                                       .getString(
                                                                                               "sc-reply-receiver-offline")));
                    }
                }
                pp.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getLang()
                                                                               .getString(
                                                                                       "sc-reply-reply-impossible")));
                return;
            }
            else
            {
                pp.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getLang().getString("no-permission")));
            }
        }
        else
        {
            sender.sendMessage(
                    ChatColor.translateAlternateColorCodes('&', Main.getLang().getString("sc-reply-reply-console")));
            return;
        }
    }
}
