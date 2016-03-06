package staffchat.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import staffchat.Main;
import staffchat.objects.PrivateMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Created by root on 5-10-2014.
 */
public class StaffChatMsg extends Command
{
    public static List<PrivateMessage> privateMessageList = new ArrayList<PrivateMessage>();

    public StaffChatMsg(Main instance)
    {
        super(Main.getConfig().getString("command-for-sc-msg").substring(1));
    }

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        if (!sender.hasPermission("staffchat.msg") && !sender.hasPermission("staffchat.*"))
        {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getLang().getString("no-permission")));
            return;
        }
        if (!(args.length >= 2))
        {
            sender.sendMessage(
                    ChatColor.translateAlternateColorCodes('&', Main.getLang().getString("msg-invalid-args")));
            return;
        }
        ProxiedPlayer target = Main.getBsc().getProxy().getPlayer(args[0]);
        if (target == null)
        {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getLang()
                                                                               .getString("msg-given-player-offline")
                                                                               .replaceAll("%target%", args[0])));
            return;
        }
        String msg;
        StringBuilder sb = new StringBuilder("");
        for (int i = 1; i < args.length; i++)
        {
            sb.append(args[i]).append(" ");
        }
        msg = sb.toString();
        if (sender instanceof ProxiedPlayer)
        {
            ProxiedPlayer pp = (ProxiedPlayer) sender;
            if (pp.hasPermission("staffchat.msg.format") || pp.hasPermission("staffchat.*"))
            {
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getConfig()
                                                                                   .getString("msg-receiving")
                                                                                   .replaceAll("%target%",
                                                                                               target.getName())
                                                                                   .replaceAll("%message%",
                                                                                               Matcher.quoteReplacement(
                                                                                                       msg)))
                                            .replaceAll("%from%", pp.getName()));
                pp.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getConfig()
                                                                               .getString("msg-sending")
                                                                               .replaceAll("%target%", target.getName())
                                                                               .replaceAll("%message%",
                                                                                           Matcher.quoteReplacement(
                                                                                                   msg)))
                                        .replaceAll("%from%", pp.getName()));
                privateMessageList.add(
                        new PrivateMessage(((ProxiedPlayer) sender).getUniqueId(), target.getUniqueId(), msg));
            }
            else
            {
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getConfig()
                                                                                   .getString("msg-receiving")
                                                                                   .replaceAll("%target%",
                                                                                               target.getName())
                                                                                   .replaceAll("%from%", pp.getName()))
                                            .replaceAll("%message%",
                                                        ChatColor.stripColor(Matcher.quoteReplacement(msg))));
                pp.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getConfig()
                                                                               .getString("msg-sending")
                                                                               .replaceAll("%target%", target.getName())
                                                                               .replaceAll("%from%", pp.getName()))
                                        .replaceAll("%message%", ChatColor.stripColor(Matcher.quoteReplacement(msg))));
                privateMessageList.add(
                        new PrivateMessage(((ProxiedPlayer) sender).getUniqueId(), target.getUniqueId(), msg));
            }
        }
        else
        {
            target.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getConfig()
                                                                               .getString("msg-receiving")
                                                                               .replaceAll("%target%", target.getName())
                                                                               .replaceAll("%message%",
                                                                                           Matcher.quoteReplacement(
                                                                                                   msg)))
                                        .replaceAll("%from%", "CONSOLE"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getConfig()
                                                                               .getString("msg-sending")
                                                                               .replaceAll("%target%", target.getName())
                                                                               .replaceAll("%message%",
                                                                                           Matcher.quoteReplacement(
                                                                                                   msg)))
                                        .replaceAll("%from%", "CONSOLE"));
        }
    }
}
