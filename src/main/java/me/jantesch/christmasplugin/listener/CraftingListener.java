package me.jantesch.christmasplugin.listener;

import com.google.inject.Inject;
import me.jantesch.christmasplugin.service.CraftingService;
import me.jantesch.christmasplugin.service.NBTService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CraftingListener implements Listener {

    private final CraftingService craftingService;
    private final NBTService nbtService;

    @Inject
    public CraftingListener(CraftingService craftingService, NBTService nbtService) {
        this.craftingService = craftingService;
        this.nbtService = nbtService;
    }

    @EventHandler
    public void onPrepareItemCraftEvent(PrepareItemCraftEvent event) {
        var result = event.getInventory().getResult();

        if (event.getInventory().getHolder() instanceof Player player && result != null) {
            Bukkit.getLogger().info("Result: " + result.getType());

            if (craftingService.isRestricted(result.getType())) {
                if (!craftingService.isAuthorizedFor(player, result.getType())) {
                    event.getInventory().setResult(new ItemStack(Material.AIR));
                } else {
                    nbtService.setStringTag(result, CraftingService.NO_RECIPE_TAG, "");
                }
            }
        }
    }

    @EventHandler
    public void onPrepareAnvilEvent(PrepareAnvilEvent event) {
        var items = event.getInventory().getContents();
        var item = extractSingleItem(items);

        if (item != null && craftingService.isRestricted(item.getType()) && !nbtService.hasTag(item, CraftingService.NO_RECIPE_TAG)) {
            var map = new ItemStack(Material.MAP);

            nbtService.setStringTag(map, CraftingService.RECIPE_FOR_TAG, item.getType().toString());

            var itemMeta = map.getItemMeta();
            itemMeta.setDisplayName(ChatColor.GOLD + "Recipe");
            itemMeta.setLore(List.of("", ChatColor.DARK_GRAY + item.getType().toString().toLowerCase()));
            map.setItemMeta(itemMeta);

            event.setResult(map);
        }
    }

    private ItemStack extractSingleItem(ItemStack[] stacks) {
        for (int i = 0; i < stacks.length; i++) {
            if (stacks[i] != null) {
                for (int j = i + 1; j < stacks.length; j++) {
                    if (stacks[j] != null) {
                        return null;
                    }
                }

                return stacks[i];
            }
        }

        return null;
    }

}
