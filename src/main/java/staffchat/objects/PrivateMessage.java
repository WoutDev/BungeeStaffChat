package staffchat.objects;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import staffchat.Main;

import java.util.UUID;

/**
 * Created by root on 25-10-2014.
 */
public class PrivateMessage
{
    private UUID sender;
    private UUID receiver;
    private String message;

    public PrivateMessage(UUID sender, UUID receiver, String message)
    {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    public UUID getSender()
    {
        return sender;
    }

    public String getMessage()
    {
        ProxiedPlayer sender = Main.getBsc().getProxy().getPlayer(getSender());
        if (sender.hasPermission("staffchat.msg.format") || sender.hasPermission("staffchat.*"))
        {
            return ChatColor.translateAlternateColorCodes('&', message);
        }
        return message;
    }

    public UUID getReceiver()
    {
        return receiver;
    }
}
