package me.jantesch.christmasplugin.service;

import lombok.Data;
import org.bukkit.Material;

import java.util.Objects;

@Data
public class RestrictedMaterial {

    private final Material material;
    private final boolean villagerRestricted;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RestrictedMaterial other) {
            return other.getMaterial() == material;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(material);
    }
}
