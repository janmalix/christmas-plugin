package me.jantesch.christmasplugin;

import com.google.inject.Guice;
import com.google.inject.Injector;
import me.jantesch.christmasplugin.commands.CommandService;
import me.jantesch.christmasplugin.config.CommandModule;
import me.jantesch.christmasplugin.config.ListenerModule;
import me.jantesch.christmasplugin.config.ServiceModule;
import me.jantesch.christmasplugin.listener.ListenerService;
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

        var listenerService = injector.getInstance(ListenerService.class);
        var commandService = injector.getInstance(CommandService.class);

        for (var listener : listenerService.getListeners()) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, this);
            Bukkit.getLogger().info("Listener added: " + listener);
        }

        for (var executor : commandService.getExecutors()) {
            getCommand(executor.getName()).setExecutor(executor);
            Bukkit.getLogger().info("CommandExecutor added: " + executor.getName());
        }
    }

    private Injector initInjector() {
        return Guice.createInjector(
                new ListenerModule(),
                new CommandModule(),
                new ServiceModule()
        );
    }
}
