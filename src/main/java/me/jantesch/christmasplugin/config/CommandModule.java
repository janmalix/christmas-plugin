package me.jantesch.christmasplugin.config;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import me.jantesch.christmasplugin.commands.CommandService;
import me.jantesch.christmasplugin.commands.CommandServiceImplementation;
import me.jantesch.christmasplugin.commands.CraftingCommand;
import me.jantesch.christmasplugin.commands.SingleCommandExecutor;

public class CommandModule extends AbstractModule {

    @Override
    public void configure() {
        var commandBinder = Multibinder.newSetBinder(binder(), SingleCommandExecutor.class);

        commandBinder.addBinding().to(CraftingCommand.class);

        bind(CommandService.class).to(CommandServiceImplementation.class);
    }
}
