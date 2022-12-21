package me.jantesch.christmasplugin.service;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public interface CraftingService {

    boolean isAuthorizedFor(Player player, Material material);

    void authorize(Player player, Material material);

    void unauthorize(Player player, Material material);

}
