package be.woutdev.bungeestaffchat.permissions;

import net.alpenblock.bungeeperms.BungeePerms;
import net.alpenblock.bungeeperms.User;

import java.util.UUID;

public class BungeePermsHandler implements IPermissionHandler {

    @Override
    public String getPrimaryGroup(UUID uuid) {
        User bungeePermsUser = BungeePerms.getInstance()
                .getPermissionsManager()
                .getUser(uuid);

        return BungeePerms.getInstance()
                .getPermissionsManager()
                .getMainGroup(bungeePermsUser)
                .getName();
    }
}
