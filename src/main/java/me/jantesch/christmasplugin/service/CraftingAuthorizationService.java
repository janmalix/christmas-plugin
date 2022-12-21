package me.jantesch.christmasplugin.service;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public interface CraftingAuthorizationService {

    boolean isAuthorizedFor(Player player, Material material);

    void authorize(Player player, Material material);

}
