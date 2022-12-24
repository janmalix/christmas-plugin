package me.jantesch.christmasplugin.service;

import com.google.inject.Inject;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class NBTServiceImplementation implements NBTService {

    private final JavaPlugin plugin;

    @Inject
    public NBTServiceImplementation(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void setStringTag(ItemStack stack, String key, String value) {
        var namespaceKey = new NamespacedKey(plugin, key);

        var itemMeta = Objects.requireNonNullElse(
                stack.getItemMeta(),
                Bukkit.getItemFactory().getItemMeta(stack.getType())
        );

        itemMeta.getPersistentDataContainer().set(namespaceKey, PersistentDataType.STRING, value);
        stack.setItemMeta(itemMeta);
    }

    @Override
    public boolean hasTag(ItemStack stack, String key) {
        var namespaceKey = new NamespacedKey(plugin, key);
        var itemMeta = stack.getItemMeta();

        if (itemMeta != null) {
            return itemMeta.getPersistentDataContainer().has(namespaceKey, PersistentDataType.STRING);
        }

        return false;
    }

    @Override
    public String getStringTag(ItemStack stack, String key) {
        var namespaceKey = new NamespacedKey(plugin, key);
        var itemMeta = stack.getItemMeta();

        if (itemMeta != null) {
            var container = itemMeta.getPersistentDataContainer();

            if (container.has(namespaceKey, PersistentDataType.STRING)) {
                return container.get(namespaceKey, PersistentDataType.STRING);
            }
        }

        return "";
    }
}
