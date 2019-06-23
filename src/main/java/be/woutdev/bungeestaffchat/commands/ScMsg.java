package be.woutdev.bungeestaffchat.commands;

import be.woutdev.bungeestaffchat.BungeeStaffChat;
import be.woutdev.bungeestaffchat.player.ScPlayer;
import net.alpenblock.bungeeperms.BungeePerms;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.UUID;
import java.util.regex.Matcher;

/**
 * Created by Wout on 14/04/2016.
 */
public class ScMsg extends Command {
    public ScMsg(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("sc.msg") && !sender.hasPermission("sc.*")) {
            sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',
                    BungeeStaffChat.getInstance()
                            .getLang()
                            .getString(
                                    "no-permission")))
                    .create());
            return;
        }

        if (args.length < 2) {
            sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',
                    BungeeStaffChat.getInstance()
                            .getLang()
                            .getString(
                                    "sc-msg-invalid-args")))
                    .create());
            return;
        }

        String target = args[0];

        if (BungeeStaffChat.getInstance().getProxy().getPlayer(target) == null) {
            sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',
                    BungeeStaffChat.getInstance()
                            .getLang()
                            .getString(
                                    "sc-msg-invalid-player")))
                    .create());
            return;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            String word = args[i];
            builder.append(word).append(" ");
        }

        String server = sender instanceof ProxiedPlayer ? ((ProxiedPlayer) sender).getServer().getInfo().getName() : "N/A";

        String msg = builder.toString();

        if (sender.hasPermission("sc.format") || sender.hasPermission("sc.*")) {
            msg = ChatColor.translateAlternateColorCodes('&', msg);
        }

        if (BungeeStaffChat.getPermissionHandler() != null) {
            if (sender instanceof ProxiedPlayer) {
                msg = msg.replaceAll(Matcher.quoteReplacement("%group%"), Matcher.quoteReplacement(
                        BungeeStaffChat.getPermissionHandler().getPrimaryGroup(((ProxiedPlayer) sender).getUniqueId())
                ));
            } else {
                msg = msg.replaceAll(Matcher.quoteReplacement("%group%"), "CONSOLE");
            }
        }

        String scMsg = BungeeStaffChat.getInstance()
                .getScMsgLayout()
                .replaceAll("%target%", Matcher.quoteReplacement(target))
                .replaceAll("%message%", Matcher.quoteReplacement(msg))
                .replaceAll(Matcher.quoteReplacement("%from%"), sender.getName()).replaceAll("%server%", server);

        ScPlayer targetPlayer = BungeeStaffChat.getInstance()
                .getPlayerManager()
                .getPlayer(BungeeStaffChat.getInstance()
                        .getProxy()
                        .getPlayer(target)
                        .getUniqueId());
        targetPlayer.setLastMsgSender(
                sender instanceof ProxiedPlayer ? (((ProxiedPlayer) sender).getUniqueId()) : UUID.randomUUID());

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', scMsg));

        ProxyServer.getInstance().getPlayer(target).sendMessage(ChatColor.translateAlternateColorCodes('&', BungeeStaffChat.getInstance().getScReplyLayout().replaceAll("%from%", Matcher.quoteReplacement(sender.getName())).replaceAll("%message%", Matcher.quoteReplacement(msg))));

        for (ScPlayer player : BungeeStaffChat.getInstance().getPlayerManager().getPlayers()) {
            if (player.hasScSpy()) {
                ProxiedPlayer spyPlayer = ProxyServer.getInstance().getPlayer(player.getUniqueId());

                if (spyPlayer != null) {
                    spyPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', BungeeStaffChat.getInstance().getScSpyLayout().replaceAll("%from%", Matcher.quoteReplacement(sender.getName())).replaceAll("%message%", Matcher.quoteReplacement(msg)).replaceAll("%target%", ProxyServer.getInstance().getPlayer(targetPlayer.getUniqueId()).getName())));
                }
            }
        }
    }
}
