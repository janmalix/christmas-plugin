package me.jantesch.christmasplugin.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class LoginListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Bukkit.getLogger().info("Player " + event.getPlayer().getDisplayName() + " has logged in");
    }
}
