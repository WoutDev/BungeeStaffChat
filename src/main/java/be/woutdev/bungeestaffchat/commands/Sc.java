package be.woutdev.bungeestaffchat.commands;

import be.woutdev.bungeestaffchat.BungeeStaffChat;
import be.woutdev.bungeestaffchat.player.ScPlayer;
import net.alpenblock.bungeeperms.BungeePerms;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.regex.Matcher;

/**
 * Created by Wout on 14/04/2016.
 */
public class Sc extends Command
{
    public Sc(String name)
    {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        if (sender.hasPermission("sc.use") || sender.hasPermission("sc.*"))
        {
            if (BungeeStaffChat.getInstance().isScPriorityEnabled() &&
                (!sender.hasPermission("sc.priority") && !sender.hasPermission("sc.*")))
            {
                sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',
                                                                                               BungeeStaffChat.getInstance()
                                                                                                              .getLang()
                                                                                                              .getString(
                                                                                                                      "sc-priority-denied")))
                                           .create());
                return;
            }

            if (sender instanceof ProxiedPlayer)
            {
                if (BungeeStaffChat.getInstance()
                                   .getPlayerManager()
                                   .getPlayer(((ProxiedPlayer) sender).getUniqueId())
                                   .isScDisabled())
                {
                    sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',
                                                                                                   BungeeStaffChat.getInstance()
                                                                                                                  .getLang()
                                                                                                                  .getString(
                                                                                                                          "sc-disabled")))
                                               .create());
                    return;
                }
            }

            StringBuilder msgBuilder = new StringBuilder();
            for (String arg : args)
            {
                msgBuilder.append(arg).append(" ");
            }

            String msg = msgBuilder.toString();

            if (sender.hasPermission("sc.format") || sender.hasPermission("sc.*"))
            {
                msg = ChatColor.translateAlternateColorCodes('&', msg);
            }

            String target = sender instanceof ProxiedPlayer ? sender.getName() : "CONSOLE";
            String server = sender instanceof ProxiedPlayer ? ((ProxiedPlayer) sender).getServer().getInfo().getName() : "N/A";

            String scMessage = BungeeStaffChat.getInstance()
                                              .getScLayout()
                                              .replaceAll("%player%",
                                                          Matcher.quoteReplacement(target))
                                              .replaceAll("%message%", Matcher.quoteReplacement(msg))
                                              .replaceAll("%server%", Matcher.quoteReplacement(server));

            if (BungeeStaffChat.getInstance().isBungeePerms())
            {
                if (sender instanceof ProxiedPlayer)
                {
                    scMessage = scMessage.replaceAll("%group%", Matcher.quoteReplacement(BungeePerms.getInstance()
                                                                                                    .getPermissionsManager()
                                                                                                    .getMainGroup(
                                                                                                            BungeePerms.getInstance()
                                                                                                                       .getPermissionsManager()
                                                                                                                       .getUser(
                                                                                                                               ((ProxiedPlayer) sender)
                                                                                                                                       .getUniqueId()))
                                                                                                    .getName()));
                }
                else
                {
                    scMessage = scMessage.replaceAll("%group%", "CONSOLE");
                }
            }

            for (ProxiedPlayer player : BungeeStaffChat.getInstance().getProxy().getPlayers())
            {
                if (player.hasPermission("sc.use") || player.hasPermission("sc.receive") ||
                    player.hasPermission("sc.*"))
                {
                    ScPlayer targetPlayer = BungeeStaffChat.getInstance()
                                                           .getPlayerManager()
                                                           .getPlayer(player.getUniqueId());

                    if (!targetPlayer.isScDisabled())
                    {
                        player.sendMessage(scMessage);
                    }
                }
            }

            if (BungeeStaffChat.getInstance().keepLog())
            {
                BungeeStaffChat.getInstance().appendToLog(ChatColor.stripColor(scMessage));
            }

            return;
        }

        sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',
                                                                                       BungeeStaffChat.getInstance()
                                                                                                      .getLang()
                                                                                                      .getString(
                                                                                                              "no-permission"))).create());
    }
}
