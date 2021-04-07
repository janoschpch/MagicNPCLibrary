package de.magicccrafter.npclib;

import de.magicccrafter.npclib.api.MagicNPCAPI;
import de.magicccrafter.npclib.command.MagicnpcCommand;
import de.magicccrafter.npclib.listener.JoinListener;
import de.magicccrafter.npclib.listener.QuitListener;
import de.magicccrafter.npclib.utils.MagicNPC;
import de.magicccrafter.npclib.utils.MagicNPCManager;
import de.magicccrafter.npclib.utils.PacketManager;
import de.magicccrafter.npclib.utils.SkinFetcher;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;

public class MagicNPCLibrary extends JavaPlugin implements CommandExecutor {

    private static MagicNPCLibrary instance;
    private String prefix;
    private SkinFetcher skinFetcher;
    private MagicNPCManager magicNPCManager;
    private PacketManager packetManager;
    private MagicNPCAPI magicNPCAPI;

    @Override
    public void onEnable() {
        instance = this;
        prefix = "§7[§bMagic§cNPC§7] ";
        skinFetcher = new SkinFetcher();
        magicNPCManager = new MagicNPCManager();
        packetManager = new PacketManager();
        magicNPCAPI = new MagicNPCAPI();

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinListener(), this);
        pluginManager.registerEvents(new QuitListener(), this);

        getCommand("magicnpc").setExecutor(new MagicnpcCommand());

        if(getConfig().getStringList("SavedNPCs") != null) {
            List<String> npcs = getConfig().getStringList("SavedNPCs");
            for(String npc : npcs) {
                String[] npcData = npc.split(":-:");
                MagicNPC magicNPC = new MagicNPC(npcData[0], npcData[1], new Location(Bukkit.getWorld(npcData[2]), Double.parseDouble(npcData[3]), Double.parseDouble(npcData[4]), Double.parseDouble(npcData[5]), Float.parseFloat(npcData[6]), Float.parseFloat(npcData[7])), UUID.fromString(npcData[8]));
                magicNPC.spawn();
                getMagicNPCManager().addNpc(magicNPC);
            }
        }

        saveConfig();

    }

    @Override
    public void onLoad() {
    }

    @Override
    public void onDisable() {
    }

    public static MagicNPCLibrary getInstance() {
        return instance;
    }

    public String getPrefix() {
        return prefix;
    }

    public SkinFetcher getSkinFetcher() {
        return skinFetcher;
    }

    public MagicNPCManager getMagicNPCManager() {
        return magicNPCManager;
    }

    public PacketManager getPacketManager() {
        return packetManager;
    }

    public MagicNPCAPI getMagicNPCAPI() {
        return magicNPCAPI;
    }

}
