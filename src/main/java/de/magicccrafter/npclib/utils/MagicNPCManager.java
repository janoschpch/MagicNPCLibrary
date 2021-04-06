package de.magicccrafter.npclib.utils;

import java.util.ArrayList;
import java.util.List;

public class MagicNPCManager {

    private List<MagicNPC> npcs = new ArrayList<MagicNPC>();

    public List<MagicNPC> getNpcs() {
        return npcs;
    }

    public void addNpc(MagicNPC npc) {
        npcs.add(npc);
    }

    public void removeNpc(MagicNPC npc) {
        npcs.remove(npc);
    }

    public MagicNPC getNpcByEntityId(Integer id) {
        for(MagicNPC npc : this.npcs) {
            if(npc.getEntityPlayer().getId() == id) {
                return npc;
            }
        }
        return null;
    }
}
