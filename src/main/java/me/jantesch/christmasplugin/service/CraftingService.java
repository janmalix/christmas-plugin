package me.jantesch.christmasplugin.service;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public interface CraftingService {

    Material RECIPE_MATERIAL = Material.MAP;
    String RECIPE_FOR_TAG = "christmasplugin_recipefor";
    String NO_RECIPE_TAG = "christmasplugin_norecipe";

    boolean isAuthorizedFor(Player player, Material material);

    void authorize(Player player, Material material);

    void unauthorize(Player player, Material material);

    boolean isRestricted(Material material);

    boolean isVillagerRestricted(Material material);

}
