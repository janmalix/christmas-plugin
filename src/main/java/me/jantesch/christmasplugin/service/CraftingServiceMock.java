package me.jantesch.christmasplugin.service;

import com.google.inject.Singleton;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;

@Singleton
public class CraftingServiceMock implements CraftingService {

    private final Set<Material> restrictedMaterials;
    private final Map<UUID, Set<Material>> authorizedPlayers;

    public CraftingServiceMock() {
        restrictedMaterials = Set.of(
                Material.DIAMOND_AXE,
                Material.DIAMOND_SWORD
        );
        authorizedPlayers = Collections.synchronizedMap(new HashMap<>());
    }

    @Override
    public boolean isAuthorizedFor(Player player, Material material) {
        if (restrictedMaterials.contains(material)) {

            if (authorizedPlayers.containsKey(player.getUniqueId())) {
                return authorizedPlayers.get(player.getUniqueId()).contains(material);
            }
            return false;
        }

        return true;
    }

    @Override
    public void authorize(Player player, Material material) {
        var playerUuid = player.getUniqueId();
        var materials = authorizedPlayers.getOrDefault(playerUuid, new HashSet<>());

        materials.add(material);

        authorizedPlayers.put(playerUuid, materials);
    }

    @Override
    public void unauthorize(Player player, Material material) {
        var playerUuid = player.getUniqueId();

        if (authorizedPlayers.containsKey(playerUuid)) {
            authorizedPlayers.get(playerUuid).remove(material);
        }
    }

    @Override
    public boolean isRestricted(Material material) {
        return false;
    }

    @Override
    public boolean isVillagerRestricted(Material material) {
        return false;
    }
}
