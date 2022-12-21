package me.jantesch.christmasplugin.listener;

import org.bukkit.event.Listener;

import java.util.Set;

public interface ListenerService {

    Set<Listener> getListeners();

}
