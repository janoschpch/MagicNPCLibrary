package de.magicccrafter.npclib.listener;

import de.magicccrafter.npclib.MagicNPCLibrary;
import de.magicccrafter.npclib.utils.MagicNPC;
import de.magicccrafter.npclib.utils.PacketReader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event) {
        for(MagicNPC npc : MagicNPCLibrary.getInstance().getMagicNPCManager().getNpcs()) {
            if(npc.isShown()) {
                npc.show(event.getPlayer());
            }
        }

        PacketReader packetReader = new PacketReader(event.getPlayer());
        packetReader.injectPlayer();

        MagicNPCLibrary.getInstance().getPacketManager().insertPacketReader(packetReader, event.getPlayer());
    }

}
