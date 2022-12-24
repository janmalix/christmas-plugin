package me.jantesch.christmasplugin.service;

import org.bukkit.inventory.ItemStack;

public interface NBTService {

    void setStringTag(ItemStack stack, String key, String value);

    boolean hasTag(ItemStack stack, String key);

    String getStringTag(ItemStack stack, String key);

}