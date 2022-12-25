package me.jantesch.christmasplugin.listener;

import com.google.inject.Inject;
import me.jantesch.christmasplugin.service.CraftingService;
import me.jantesch.christmasplugin.service.NBTService;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.List;

public class TradingListener implements Listener {

    private final CraftingService craftingService;
    private final NBTService nbtService;

    @Inject
    public TradingListener(CraftingService craftingService, NBTService nbtService) {
        this.craftingService = craftingService;
        this.nbtService = nbtService;
    }

    @EventHandler
    public void onTrade(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked() instanceof Villager villager) {
            var recipes = villager.getRecipes();


            villager.setRecipes(turnIntoRecipeNotExtractable(recipes));
        }
    }

    private List<MerchantRecipe> turnIntoRecipeNotExtractable(List<MerchantRecipe> merchantRecipes) {
        List<MerchantRecipe> recipes = new ArrayList<>();

        for (var recipe : merchantRecipes) {
            recipes.add(turnIntoRecipeNotExtractable(recipe));
        }

        return recipes;
    }

    private MerchantRecipe turnIntoRecipeNotExtractable(MerchantRecipe recipe) {
        var result = recipe.getResult();

        if (craftingService.isRestricted(result.getType()) && craftingService.isVillagerRestricted(result.getType())) {
            nbtService.setStringTag(result, CraftingService.NO_RECIPE_TAG, "");
        }

        var recipeNotExtractable = new MerchantRecipe(
                result, recipe.getUses(), recipe.getMaxUses(), true,
                recipe.getVillagerExperience(), recipe.getPriceMultiplier(), recipe.getDemand(), recipe.getSpecialPrice()
        );
        recipeNotExtractable.setIngredients(recipe.getIngredients());

        return recipeNotExtractable;
    }
}
