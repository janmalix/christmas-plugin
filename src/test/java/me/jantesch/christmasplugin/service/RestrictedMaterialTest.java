package me.jantesch.christmasplugin.service;

import org.bukkit.Material;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RestrictedMaterialTest {

    @Test
    public void test() {
        var r1 = new RestrictedMaterial(Material.DIAMOND, false);
        var r2 = new RestrictedMaterial(Material.DIAMOND, true);

        var set = new HashSet<RestrictedMaterial>();
        set.add(r1);

        assertTrue(set.contains(r2));
    }
}
