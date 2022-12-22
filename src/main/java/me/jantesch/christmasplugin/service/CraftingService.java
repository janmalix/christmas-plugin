package me.jantesch.christmasplugin.service;

import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface CraftingService {

    AttributeModifier RECIPE_NOT_EXTRACTABLE_MODIFIER = new AttributeModifier(
            UUID.fromString("38817d1d-028e-4274-94fb-d6c6be609e00"),
            "RecipeNotExtractable",
            0.0,
            AttributeModifier.Operation.MULTIPLY_SCALAR_1
    );

    boolean isAuthorizedFor(Player player, Material material);

    void authorize(Player player, Material material);

    void unauthorize(Player player, Material material);

}
