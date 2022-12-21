package me.jantesch.christmasplugin.listener;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import org.bukkit.event.Listener;

import java.util.Set;

public class ListenerServiceImplementation implements ListenerService {

    private final Set<Listener> listeners;

    @Inject
    public ListenerServiceImplementation(Set<Listener> listeners) {
        this.listeners = listeners;
    }

    @Override
    public Set<Listener> getListeners() {
        return Sets.newHashSet(listeners);
    }
}
