package me.jantesch.christmasplugin.config;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import me.jantesch.christmasplugin.listener.*;
import org.bukkit.event.Listener;

public class ListenerModule extends AbstractModule {

    public void configure() {
        var listenerBinder = Multibinder.newSetBinder(binder(), Listener.class);

        listenerBinder.addBinding().to(LoginListener.class);
        listenerBinder.addBinding().to(CraftingListener.class);
        listenerBinder.addBinding().to(ClickListener.class);
        listenerBinder.addBinding().to(TradingListener.class);

        bind(ListenerService.class).to(ListenerServiceImplementation.class);
    }

}
