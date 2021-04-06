package de.magicccrafter.npclib.listener;

import de.magicccrafter.npclib.MagicNPCLibrary;
import de.magicccrafter.npclib.command.MagicnpcCommand;
import de.magicccrafter.npclib.utils.PacketReader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void PlayerQuit(PlayerQuitEvent event) {
        PacketReader packetReader = MagicNPCLibrary.getInstance().getPacketManager().getPacketReader(event.getPlayer());
        packetReader.uninjectPlayer();
        MagicNPCLibrary.getInstance().getPacketManager().removePacketReader(packetReader, event.getPlayer());
        if(MagicnpcCommand.removeMode.contains(event.getPlayer())) {
            MagicnpcCommand.removeMode.remove(event.getPlayer());
        }
    }

}
