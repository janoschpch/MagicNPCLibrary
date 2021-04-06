package de.magicccrafter.npclib.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.magicccrafter.npclib.MagicNPCLibrary;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.UUID;

public class MagicNPC {

    private String name;
    private String skin;
    private Location location;
    private EntityPlayer entityPlayer;
    private GameProfile gameProfile;
    private Boolean shown;
    private UUID uuid;

    public MagicNPC(String name, String skin, Location location, UUID uuid) {
        this.name = name;
        if(skin.length() >= 17) {
            this.skin = skin;
        } else {
            String[] fetchedSkin = MagicNPCLibrary.getInstance().getSkinFetcher().getSkin(skin);
            this.skin = fetchedSkin[0] + ":_:" + fetchedSkin[1];
        }
        this.location = location;
        this.shown = false;
        this.uuid = uuid;
    }

    public void spawn() {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) this.location.getWorld()).getHandle();
        this.gameProfile = new GameProfile(this.uuid, this.name);
        this.gameProfile.getProperties().put("textures", new Property("textures", skin.split(":_:")[0], skin.split(":_:")[1]));

        this.entityPlayer = new EntityPlayer(server, world, gameProfile, new PlayerInteractManager(world));

        this.entityPlayer.getBukkitEntity().getPlayer().setPlayerListName("");
        this.entityPlayer.setLocation(this.location.getX(), this.location.getY(), this.location.getZ(), this.location.getYaw(), this.location.getPitch());


        this.shown = true;

        for(Player player : Bukkit.getOnlinePlayers()) {
            show(player);
        }

        entityPlayer.getBukkitEntity().getPlayer().teleport(this.location);
    }


    public void despawn() {
        this.shown = false;
        for(Player player : Bukkit.getOnlinePlayers()) {
            hide(player);
        }
    }

    public void hide(Player player) {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutEntityDestroy(entityPlayer.getId()));
    }

    public void show(Player player) {
        DataWatcher watcher = new DataWatcher(null);
        watcher.register(new DataWatcherObject<>(16, DataWatcherRegistry.a), (byte) 127);

        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, this.entityPlayer));
        connection.sendPacket(new PacketPlayOutEntityMetadata(entityPlayer.getId(), watcher, true));
        connection.sendPacket(new PacketPlayOutEntityHeadRotation(this.entityPlayer, (byte) (this.entityPlayer.yaw * 256 / 360)));
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(this.entityPlayer));
        Bukkit.getScheduler().runTaskLater(MagicNPCLibrary.getInstance(), new Runnable() {
            @Override
            public void run() {
                connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityPlayer));
            }
        }, 10);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isShown() {
        return shown;
    }

    public EntityPlayer getEntityPlayer() {
        return entityPlayer;
    }

    public GameProfile getGameProfile() {
        return gameProfile;
    }

    public Location getLocation() {
        return location;
    }

    public String getSkin() {
        return skin;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public String toString() {
        return name + ":-:" + skin + ":-:" + location.getWorld().getName() + ":-:" + location.getX() + ":-:" + location.getY() + ":-:" + location.getZ() + ":-:" + location.getYaw() + ":-:" + location.getPitch() + ":-:" + uuid.toString();
    }

    public UUID getUniqueId() {
        return uuid;
    }
}
