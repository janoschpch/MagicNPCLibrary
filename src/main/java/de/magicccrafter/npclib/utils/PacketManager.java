package de.magicccrafter.npclib.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class PacketManager {

    private HashMap<Player, PacketReader> packetReaders = new HashMap<Player, PacketReader>();

    public PacketReader getPacketReader(Player player) {
        return packetReaders.get(player);
    }

    public void insertPacketReader(PacketReader packetReader, Player player) {
        packetReaders.put(player, packetReader);
    }

    public void removePacketReader(PacketReader packetReader, Player player) {
        packetReaders.remove(player, packetReader);
    }

}
