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

import java.util.regex.Matcher;

/**
 * Created by Wout on 14/04/2016.
 */
public class ScReply extends Command {
    public ScReply(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new ComponentBuilder("This command is player only.").create());
            return;
        }

        ProxiedPlayer pp = (ProxiedPlayer) sender;

        if (!sender.hasPermission("sc.reply") && !sender.hasPermission("sc.*")) {
            sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',
                    BungeeStaffChat.getInstance()
                            .getLang()
                            .getString(
                                    "no-permission")))
                    .create());
            return;
        }

        if (args.length == 0) {
            sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',
                    BungeeStaffChat.getInstance()
                            .getLang()
                            .getString(
                                    "sc-reply-invalid-args")))
                    .create());
            return;
        }

        ScPlayer player = BungeeStaffChat.getInstance().getPlayerManager().getPlayer(pp.getUniqueId());

        if (player.getLastMsgSender() == null ||
                BungeeStaffChat.getInstance().getProxy().getPlayer(player.getLastMsgSender()) == null) {
            sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',
                    BungeeStaffChat.getInstance()
                            .getLang()
                            .getString(
                                    "sc-reply-no-target")))
                    .create());
            return;
        }

        ScPlayer target = BungeeStaffChat.getInstance().getPlayerManager().getPlayer(player.getLastMsgSender());
        target.setLastMsgSender(player.getUniqueId());

        StringBuilder builder = new StringBuilder();
        for (String word : args) {
            builder.append(word).append(" ");
        }

        String msg = builder.toString();

        if (sender.hasPermission("sc.format") || sender.hasPermission("sc.*")) {
            msg = ChatColor.translateAlternateColorCodes('&', msg);
        }

        String server = ((ProxiedPlayer) sender).getServer().getInfo().getName();

        if (BungeeStaffChat.getPermissionHandler() != null) {
            msg = msg.replaceAll("%group%", Matcher.quoteReplacement(
                    BungeeStaffChat.getPermissionHandler().getPrimaryGroup(((ProxiedPlayer) sender).getUniqueId())
            ));
        }

        String scReply = BungeeStaffChat.getInstance()
                .getScReplyLayout()
                .replaceAll("%from%", Matcher.quoteReplacement(sender.getName()))
                .replaceAll("%message%", Matcher.quoteReplacement(msg))
                .replaceAll("%target%", Matcher.quoteReplacement(BungeeStaffChat.getInstance()
                        .getProxy()
                        .getPlayer(
                                target.getUniqueId())
                        .getName()).replaceAll("%server%", server));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', BungeeStaffChat.getInstance()
                .getScMsgLayout()
                .replaceAll("%target%",
                        Matcher.quoteReplacement(
                                ProxyServer.getInstance()
                                        .getPlayer(
                                                target.getUniqueId())
                                        .getName()))
                .replaceAll("%message%",
                        Matcher.quoteReplacement(
                                msg))));

        BungeeStaffChat.getInstance()
                .getProxy()
                .getPlayer(target.getUniqueId())
                .sendMessage(ChatColor.translateAlternateColorCodes('&', scReply));

        for (ScPlayer p : BungeeStaffChat.getInstance().getPlayerManager().getPlayers()) {
            if (p.hasScSpy()) {
                ProxiedPlayer spyPlayer = ProxyServer.getInstance().getPlayer(player.getUniqueId());

                if (spyPlayer != null) {
                    spyPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', BungeeStaffChat.getInstance().getScSpyLayout().replaceAll("%from%", Matcher.quoteReplacement(sender.getName())).replaceAll("%message%", Matcher.quoteReplacement(msg)).replaceAll("%target%", ProxyServer.getInstance().getPlayer(target.getUniqueId()).getName())));
                }
            }
        }
    }
}
