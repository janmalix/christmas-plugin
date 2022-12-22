package me.jantesch.christmasplugin.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class CraftingServiceImplementation implements CraftingService {

    private final JavaPlugin plugin;

    private final Set<Material> restrictedMaterials;
    private final Map<UUID, Set<Material>> authorizedPlayers;

    @Inject
    public CraftingServiceImplementation(JavaPlugin plugin) {
        this.plugin = plugin;

        Set<Material> restrictedFromConfig;
        var restricted = plugin.getConfig().getStringList("restrictedMaterials");

        try {
            restrictedFromConfig = restricted.stream()
                    .map(Material::valueOf)
                    .collect(Collectors.toSet());
            Bukkit.getLogger().info("All restricted Materials: " + restrictedFromConfig);
        } catch (Exception e) {
            restrictedFromConfig = Set.of();
            Bukkit.getLogger().warning("The Materials-List is malformed");
        }

        restrictedMaterials = restrictedFromConfig;
        authorizedPlayers = Collections.synchronizedMap(new HashMap<>());

        var authorizedSection = plugin.getConfig().getConfigurationSection("players");
        if (authorizedSection != null) {
            var authorizedUuids = authorizedSection.getKeys(false);

            for (var uuid : authorizedUuids) {
                Bukkit.getLogger().info("Authorized Player: " + uuid);

                var materials = authorizedSection.getStringList(uuid);
                authorizedPlayers.put(UUID.fromString(uuid), materials.stream().map(Material::valueOf).collect(Collectors.toSet()));
            }
        }
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

        plugin.getConfig().set("players." + playerUuid, materials.stream().map(Material::toString).toList());
        plugin.saveConfig();
    }

    @Override
    public void unauthorize(Player player, Material material) {
        var playerUuid = player.getUniqueId();

        if (authorizedPlayers.containsKey(playerUuid)) {
            authorizedPlayers.get(playerUuid).remove(material);

            plugin.getConfig().set("players." + playerUuid, authorizedPlayers.get(playerUuid).stream().map(Material::toString).toList());
            plugin.saveConfig();
        }
    }

}
