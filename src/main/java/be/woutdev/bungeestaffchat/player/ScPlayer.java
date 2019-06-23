package be.woutdev.bungeestaffchat.player;

import java.util.UUID;

/**
 * Created by Wout on 14/04/2016.
 */
public class ScPlayer {
    private UUID uniqueId;
    private boolean scDisabled;
    private boolean scToggled;
    private boolean scSpy;
    private UUID lastMsgSender;

    public ScPlayer(UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.scDisabled = false;
        this.scToggled = false;
        this.scSpy = false;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public boolean isScDisabled() {
        return scDisabled;
    }

    public void setScDisabled(boolean scDisabled) {
        this.scDisabled = scDisabled;
    }

    public boolean isScToggled() {
        return scToggled;
    }

    public void setScToggled(boolean scToggled) {
        this.scToggled = scToggled;
    }

    public UUID getLastMsgSender() {
        return lastMsgSender;
    }

    public void setLastMsgSender(UUID lastMsgSender) {
        this.lastMsgSender = lastMsgSender;
    }

    public boolean hasScSpy() {
        return scSpy;
    }

    public void setScSpy(boolean scSpy) {
        this.scSpy = scSpy;
    }
}
