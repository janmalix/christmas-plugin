package me.jantesch.christmasplugin;

import com.google.inject.Guice;
import com.google.inject.Injector;
import me.jantesch.christmasplugin.config.ListenerModule;
import me.jantesch.christmasplugin.listener.ListenerServiceImplementation;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ChristmasPlugin extends JavaPlugin {

    private final Injector injector;

    public ChristmasPlugin() {
        injector = initInjector();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        Bukkit.getLogger().info("ChristmasPlugin started");

        var listenerService = injector.getInstance(ListenerServiceImplementation.class);

        for (var listener : listenerService.getListeners()) {
            Bukkit.getLogger().info("Listener added: " + listener.toString());
            Bukkit.getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    private Injector initInjector() {
        return Guice.createInjector(
                new ListenerModule()
        );
    }
}
