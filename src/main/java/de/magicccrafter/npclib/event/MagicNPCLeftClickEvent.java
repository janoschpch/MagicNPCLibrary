package de.magicccrafter.npclib.event;

import de.magicccrafter.npclib.utils.MagicNPC;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MagicNPCLeftClickEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private MagicNPC magicNPC;
    private Player player;

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public MagicNPCLeftClickEvent(MagicNPC magicNPC, Player player) {
        this.magicNPC = magicNPC;
        this.player = player;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public MagicNPC getMagicNPC() {
        return this.magicNPC;
    }

    public Player getPlayer() {
        return this.player;
    }

}
