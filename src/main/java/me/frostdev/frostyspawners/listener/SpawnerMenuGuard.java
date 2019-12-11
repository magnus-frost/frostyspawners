package me.frostdev.frostyspawners.listener;

import me.frostdev.frostyspawners.Frostyspawners;
import me.frostdev.frostyspawners.spawners.menu.MainMenuHolder;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;

import java.util.logging.ConsoleHandler;

import static org.bukkit.Bukkit.getServer;

public class SpawnerMenuGuard implements Listener {
    private Frostyspawners main;

    public SpawnerMenuGuard(Frostyspawners as) {
        this.main = as;
    }

    @EventHandler(
            priority = EventPriority.HIGHEST,
            ignoreCancelled = true
    )
    public void onInventoryMove(InventoryMoveItemEvent e){
        Inventory menu = e.getDestination();
        if(menu.getHolder() instanceof MainMenuHolder) {
            ConsoleCommandSender console = getServer().getConsoleSender();
            console.sendMessage("Event Trigger!");
        }
    }
}
