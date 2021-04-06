package de.magicccrafter.npclib.command;

import de.magicccrafter.npclib.MagicNPCLibrary;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MagicnpcCommand implements CommandExecutor {

    public static List<Player> removeMode = new ArrayList<Player>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(player.hasPermission("magicnpc.command")) {
                if(strings.length == 4) {
                    if(strings[0].equalsIgnoreCase("create")) {
                        String name = strings[1];
                        String skin = strings[2];
                        String uuid = strings[3];
                        name = name.replace("&", "§");
                        if(isUUID(uuid)) {
                            MagicNPCLibrary.getInstance().getMagicNPCAPI().createNPC(name, skin, player.getLocation(), true, UUID.fromString(uuid));
                            player.sendMessage(MagicNPCLibrary.getInstance().getPrefix() + "§eDu hast einen NPC mit dem Namen §7[" + name + "§7] §eerstellt und mit dem Skin §7[§c" + skin + "§7] §eund mit der UUID §7[§4" + uuid + "§7]");
                            return true;
                        }
                        player.sendMessage(MagicNPCLibrary.getInstance().getPrefix() + "§cDas ist keine UUID");
                        return true;
                    }
                }
                if(strings.length == 3) {
                    if(strings[0].equalsIgnoreCase("create")) {
                        String name = strings[1];
                        String skin = strings[2];
                        name = name.replace("&", "§");
                        MagicNPCLibrary.getInstance().getMagicNPCAPI().createNPC(name, skin, player.getLocation(), true);
                        player.sendMessage(MagicNPCLibrary.getInstance().getPrefix() + "§eDu hast einen NPC mit dem Namen §7[" + name + "§7] §eerstellt und mit dem Skin §7[§c" + skin + "§7]");
                        return true;
                    }
                }
                if(strings.length == 1) {
                    if(strings[0].equalsIgnoreCase("remove")) {
                        if(removeMode.contains(player)) {
                            removeMode.remove(player);
                            player.sendMessage(MagicNPCLibrary.getInstance().getPrefix() + "§cDu hast den NPC-Remove Modus verlassen");
                        } else {
                            removeMode.add(player);
                            player.sendMessage(MagicNPCLibrary.getInstance().getPrefix() + "§aDu bist dem NPC-Remove Modus beigetreten. Sobald du auf einen NPC rechtsklickst wird dieser entfert. Du kannst den Modus mit einem erneutigen eingeben des Befehls beenden.");
                        }
                        return true;
                    }
                }
                help(player);
            }
        }
        return false;
    }

    private void help(Player player) {
        player.sendMessage(MagicNPCLibrary.getInstance().getPrefix() + "§eMagicNPC Admin Hilfe:");
        player.sendMessage(MagicNPCLibrary.getInstance().getPrefix() + "§e/magicnpc create <Name> <Skin> [UUID] §7- §eErstellt einen neuen NPC");
        player.sendMessage(MagicNPCLibrary.getInstance().getPrefix() + "§e/magicnpc remove §7- §eSchaltet den NPC-Remove Modus an");
    }

    private Boolean isUUID(String string) {
        try {
            UUID.fromString(string);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
