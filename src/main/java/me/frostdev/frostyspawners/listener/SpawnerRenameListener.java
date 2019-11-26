package me.frostdev.frostyspawners.listener;

import me.frostdev.frostyspawners.Frostyspawners;
import me.frostdev.frostyspawners.Lang;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;

public class SpawnerRenameListener implements Listener {
    private Frostyspawners main;

    public SpawnerRenameListener(Frostyspawners as) {
        this.main = as;
    }

    @EventHandler(
            priority = EventPriority.MONITOR,
            ignoreCancelled = true
    )
    public void onSpawnerRename(InventoryClickEvent e) {
        if (e.getView().getTopInventory() instanceof AnvilInventory) {
            ItemStack item = e.getCurrentItem();
            if (item.getType() == this.main.items.spawner(1).getType() && e.getSlotType() == SlotType.RESULT) {
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
                ((Player)e.getWhoClicked()).updateInventory();
                e.getWhoClicked().sendMessage(Lang.PREFIX.toString() + Lang.SPAWNER_ANVIL_CHANGETYPE.toString());
            }
        }

    }
}
