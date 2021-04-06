package de.magicccrafter.npclib.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NPCListener implements Listener {

    @EventHandler
    public void NPC(MagicNPCRightClickEvent event) {
        event.getPlayer().sendMessage(event.getMagicNPC().getName());
    }

}
