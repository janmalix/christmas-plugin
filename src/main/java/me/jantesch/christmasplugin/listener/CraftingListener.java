package me.jantesch.christmasplugin.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class CraftingListener implements Listener {

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {

    }

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        var result = event.getInventory().getResult();

        if (result != null && result.getType() == Material.DIAMOND_AXE) {
            event.getInventory().setResult(new ItemStack(Material.AIR));
        }
    }
}
