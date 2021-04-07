# MagicNPCLibrary
 
This is a NPC Library for spigot or paper 1.15.2. You easy can create NPCs with a command and save it. Also the plugin adds two Bukkit events.

**Temporary Test Server** dev.magiccrafter.de

## Current Features

- Create NPCs with a Command
- Custom NPC Name
- Custom NPC Skin
- Custom NPC UUID
- NPC API to make own Features when clicking on the NPC
- Commands: /magicnpc

# API
The Plugin contains to extra Bukkit events and an API class to create, remove, save NPCs.

```java
import de.magicccrafter.npclib.event.MagicNPCLeftClickEvent;
import de.magicccrafter.npclib.event.MagicNPCRightClickEvent;
import de.magicccrafter.npclib.utils.MagicNPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class APIExample implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        //Create a NPC next to the Player
        //.createNPC(name, skinName, location, saveToConfig);
        MagicNPC npc = MagicNPCLibrary.getInstance().getMagicNPCAPI().createNPC(event.getPlayer().getName(), event.getPlayer().getName(), event.getPlayer().getLocation().add(5, 0, 0), false);
    }

    //This events executes if a player right clicks on a NPC
    @EventHandler
    public void onNPCRightClick(MagicNPCRightClickEvent event) {
        Player player = event.getPlayer(); //Get the player who clicked
        MagicNPC magicNPC = event.getMagicNPC(); //Get the clicked NPC

        magicNPC.getName(); // Get methods from the MagicNPC class
        magicNPC.getLocation();
        magicNPC.getSkin();
        magicNPC.getUniqueId();
        magicNPC.getEntityPlayer();
        magicNPC.getGameProfile();

        player.sendMessage("you right clicked " + magicNPC.getName());

    }

    //This events executes if a player left clicks on a NPC
    @EventHandler
    public void onNPCLeftClick(MagicNPCLeftClickEvent event) {
        Player player = event.getPlayer(); //Get the player who clicked
        MagicNPC magicNPC = event.getMagicNPC(); //Get the clicked NPC

        magicNPC.getName(); // Get methods from the MagicNPC class
        magicNPC.getLocation();
        magicNPC.getSkin();
        magicNPC.getUniqueId();
        magicNPC.getEntityPlayer();
        magicNPC.getGameProfile();

        player.sendMessage("you left clicked " + magicNPC.getName());

    }

}
```
## Pictures

![npc1](https://user-images.githubusercontent.com/67484571/113927964-740dbc80-97ee-11eb-9f53-5bc1fef3b180.png)

