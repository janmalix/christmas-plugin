package me.jantesch.christmasplugin.listener;

import com.google.inject.Inject;
import me.jantesch.christmasplugin.service.CraftingService;
import me.jantesch.christmasplugin.service.NBTService;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class ClickListener implements Listener {

    private final CraftingService craftingService;
    private final NBTService nbtService;

    @Inject
    public ClickListener(CraftingService craftingService, NBTService nbtService) {
        this.craftingService = craftingService;
        this.nbtService = nbtService;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        var item = event.getItem();

        if (item != null && isRightClickAction(event.getAction()) && isRecipe(item)) {
                var material = Material.valueOf(nbtService.getStringTag(item, CraftingService.RECIPE_FOR_TAG));
                var player = event.getPlayer();

                if (!craftingService.isAuthorizedFor(player, material)) {
                    craftingService.authorize(player, material);

                    removeClickedItem(player, event.getHand());

                    player.sendMessage(ChatColor.GRAY + "You can now craft: " + ChatColor.GREEN + material);
                } else {
                    player.sendMessage(ChatColor.RED + "You have already learned this recipe");
                }

                event.setCancelled(true);
        }
    }

    private boolean isRightClickAction(Action action) {
        return action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK;
    }

    private boolean isRecipe(ItemStack stack) {
        return stack.getType() == Material.MAP && nbtService.hasTag(stack, CraftingService.RECIPE_FOR_TAG);
    }

    private void removeClickedItem(Player player, @Nullable EquipmentSlot hand) {
        if (hand != null) {
            var emptyItem = new ItemStack(Material.AIR);

            switch (hand) {
                case HAND -> player.getInventory().setItemInMainHand(emptyItem);
                case OFF_HAND -> player.getInventory().setItemInOffHand(emptyItem);
            }
        }
    }
}
