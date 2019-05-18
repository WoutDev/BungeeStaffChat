package be.woutdev.bungeestaffchat.permissions;

import me.lucko.luckperms.LuckPerms;

import java.util.Objects;
import java.util.UUID;

public class LuckPermsHandler implements IPermissionHandler {
    @Override
    public String getPrimaryGroup(UUID uuid) {
        return Objects.requireNonNull(LuckPerms.getApi().getUser(uuid)).getPrimaryGroup();
    }
}
