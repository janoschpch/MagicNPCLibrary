package de.magicccrafter.npclib.api;

import de.magicccrafter.npclib.MagicNPCLibrary;
import de.magicccrafter.npclib.utils.MagicNPC;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MagicNPCAPI {

    public MagicNPC createNPC(String displayName, String skinName, Location npcLocation) {
        MagicNPC npc = new MagicNPC(displayName, skinName, npcLocation, UUID.randomUUID());
        npc.spawn();
        MagicNPCLibrary.getInstance().getMagicNPCManager().addNpc(npc);
        this.saveNPC(npc);
        return npc;
    }

    public MagicNPC createNPC(String displayName, String skinName, Location npcLocation, UUID uuid) {
        MagicNPC npc = new MagicNPC(displayName, skinName, npcLocation, uuid);
        npc.spawn();
        MagicNPCLibrary.getInstance().getMagicNPCManager().addNpc(npc);
        this.saveNPC(npc);
        return npc;
    }

    public void saveNPC(MagicNPC npc) {
        if(MagicNPCLibrary.getInstance().getConfig().getStringList("SavedNPCs") == null) {
            List<String> npcs = new ArrayList<String>();
            npcs.add(npc.toString());
            MagicNPCLibrary.getInstance().getConfig().set("SavedNPCs", npcs);
        } else {
            List<String> npcs = MagicNPCLibrary.getInstance().getConfig().getStringList("SavedNPCs");
            npcs.add(npc.toString());
            MagicNPCLibrary.getInstance().getConfig().set("SavedNPCs", npcs);
        }
        MagicNPCLibrary.getInstance().saveConfig();
    }

    public void removeNPC(MagicNPC npc) {
        npc.despawn();
        MagicNPCLibrary.getInstance().getMagicNPCManager().removeNpc(npc);
        List<String> npcs = MagicNPCLibrary.getInstance().getConfig().getStringList("SavedNPCs");
        String npc2 = null;
        for(String npc1 : npcs) {
            if(npc1.contains(npc.getUniqueId().toString())) {
                npc2 = npc1;
            }
        }
        npcs.remove(npc2);
        MagicNPCLibrary.getInstance().getConfig().set("SavedNPCs", npcs);
        MagicNPCLibrary.getInstance().saveConfig();
    }

}
