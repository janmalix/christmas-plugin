package me.jantesch.christmasplugin.listener;

import com.google.inject.Inject;
import me.jantesch.christmasplugin.service.CraftingService;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class CraftingListener implements Listener {

    private final CraftingService craftingService;

    @Inject
    public CraftingListener(CraftingService craftingService) {
        this.craftingService = craftingService;
    }

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        var result = event.getInventory().getResult();

        if (event.getInventory().getHolder() instanceof Player player && result != null) {
            Bukkit.getLogger().info("Result: " + result.getType());

            if (!craftingService.isAuthorizedFor(player, result.getType())) {
                event.getInventory().setResult(new ItemStack(Material.AIR));
            }
        }
    }
}
