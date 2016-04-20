package be.woutdev.bungeestaffchat.player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Wout on 14/04/2016.
 */
public class ScPlayerManager
{
    private Set<ScPlayer> players;

    public ScPlayerManager()
    {
        players = new HashSet<ScPlayer>();
    }

    public ScPlayer getPlayer(UUID uuid)
    {
        for (ScPlayer player : players)
        {
            if (player.getUniqueId().equals(uuid))
            {
                return player;
            }
        }

        return null;
    }

    public void addPlayer(UUID uuid)
    {
        players.add(new ScPlayer(uuid));
    }

    public void removePlayer(UUID uuid)
    {
        players.remove(getPlayer(uuid));
    }

    public Set<ScPlayer> getPlayers()
    {
        return players;
    }
}
