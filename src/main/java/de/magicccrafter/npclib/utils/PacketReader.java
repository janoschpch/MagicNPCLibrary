package de.magicccrafter.npclib.utils;

import de.magicccrafter.npclib.MagicNPCLibrary;
import de.magicccrafter.npclib.command.MagicnpcCommand;
import de.magicccrafter.npclib.event.MagicNPCLeftClickEvent;
import de.magicccrafter.npclib.event.MagicNPCRightClickEvent;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.server.v1_15_R1.Packet;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.List;

public class PacketReader {

    private Player player;
    private Channel channel;
    private Boolean send;

    public PacketReader(Player player) {
        this.player = player;
        this.send = false;
    }

    public void injectPlayer() {
        CraftPlayer craftPlayer = (CraftPlayer) this.player;
        this.channel = craftPlayer.getHandle().playerConnection.networkManager.channel;
        this.channel.pipeline().addAfter("decoder", "PacketInjector", new MessageToMessageDecoder<Packet<?>>() {
            @Override
            protected void decode(ChannelHandlerContext channelHandlerContext, Packet<?> packet, List<Object> list) throws Exception {
                list.add(packet);
                readPacket(packet);
            }
        });
    }

    public void uninjectPlayer() {
        if(channel.pipeline().get("PacketInjector") != null) {
            channel.pipeline().remove("PacketInjector");
        }
    }

    public void readPacket(Packet<?> packet) {
        if(packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUseEntity")) {
            Integer id = (Integer) getPacketValue(packet, "a");

            MagicNPC npc = MagicNPCLibrary.getInstance().getMagicNPCManager().getNpcByEntityId(id);
            if(npc.getName() == null) return;

            Bukkit.getScheduler().runTask(MagicNPCLibrary.getInstance(), new Runnable() {
                @Override
                public void run() {
                    if(getPacketValue(packet, "action").toString().equalsIgnoreCase("ATTACK")) {
                        if(MagicnpcCommand.removeMode.contains(player)) {
                            MagicNPCLibrary.getInstance().getMagicNPCAPI().removeNPC(npc);
                            player.sendMessage(MagicNPCLibrary.getInstance().getPrefix() + "§2Der NPC wurde entfernt");
                            return;
                        }
                        MagicNPCLeftClickEvent magicNPCLeftClickEvent = new MagicNPCLeftClickEvent(npc, player);
                        Bukkit.getPluginManager().callEvent(magicNPCLeftClickEvent);
                    } else if(getPacketValue(packet, "action").toString().equalsIgnoreCase("INTERACT")) {
                        if(!send) {
                            send = true;
                            MagicNPCRightClickEvent magicNPCRightClickEvent = new MagicNPCRightClickEvent(npc, player);
                            Bukkit.getPluginManager().callEvent(magicNPCRightClickEvent);
                            Bukkit.getScheduler().runTaskLater(MagicNPCLibrary.getInstance(), new Runnable() {
                                @Override
                                public void run() {
                                    send = false;
                                }
                            }, 1);
                        }
                    }
                }
            });
        }
    }

    public Object getPacketValue(Object object, String name){
        try {
            Field field = object.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field.get(object);
        }  catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
