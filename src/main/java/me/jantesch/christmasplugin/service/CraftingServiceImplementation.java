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

    private final Set<RestrictedMaterial> restrictedMaterials;
    private final Map<UUID, Set<Material>> authorizedPlayers;

    @Inject
    public CraftingServiceImplementation(JavaPlugin plugin) {
        this.plugin = plugin;

        restrictedMaterials = loadRestrictedMaterials();
        authorizedPlayers = loadAuthorizedPlayers();
    }

    @Override
    public boolean isAuthorizedFor(Player player, Material material) {
        if (restrictedMaterials.contains(new RestrictedMaterial(material, false))) {

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

    @Override
    public boolean isRestricted(Material material) {
        Bukkit.getLogger().info("" + material + " is restricted: " + restrictedMaterials.contains(new RestrictedMaterial(material, false)));
        return restrictedMaterials.contains(new RestrictedMaterial(material, false));
    }

    @Override
    public boolean isVillagerRestricted(Material material) {
        return restrictedMaterials.stream()
                .filter(m -> m.equals(new RestrictedMaterial(material, false)))
                .findAny()
                .map(RestrictedMaterial::isVillagerRestricted)
                .orElse(false);
    }

    private Set<RestrictedMaterial> loadRestrictedMaterials() {
        var configMaterials = plugin.getConfig().getConfigurationSection("restricted");
        var restrictedMaterials = new HashSet<RestrictedMaterial>();

        if (configMaterials != null) {
            for (var material : configMaterials.getKeys(false)) {
                var villagerRestricted = plugin.getConfig().getBoolean("restricted." + material + ".villagerRestricted", false);

                var restrictedMaterial = new RestrictedMaterial(Material.valueOf(material), villagerRestricted);
                restrictedMaterials.add(restrictedMaterial);
            }
        }
        Bukkit.getLogger().info(restrictedMaterials.toString());

        return restrictedMaterials;
    }

    private Map<UUID, Set<Material>> loadAuthorizedPlayers() {
        var authorizedSection = plugin.getConfig().getConfigurationSection("players");
        var authorizedPlayers = new HashMap<UUID, Set<Material>>();

        if (authorizedSection != null) {
            for (var uuid : authorizedSection.getKeys(false)) {
                var materials = authorizedSection.getStringList(uuid);

                authorizedPlayers.put(
                        UUID.fromString(uuid),
                        materials.stream().map(Material::valueOf).collect(Collectors.toSet())
                );
            }
        }

        return authorizedPlayers;
    }
}
