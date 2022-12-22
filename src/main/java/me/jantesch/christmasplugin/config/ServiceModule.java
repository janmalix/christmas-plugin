package me.jantesch.christmasplugin.config;

import com.google.inject.AbstractModule;
import me.jantesch.christmasplugin.service.CraftingService;
import me.jantesch.christmasplugin.service.CraftingServiceImplementation;

public class ServiceModule extends AbstractModule {

    @Override
    public void configure() {
        bind(CraftingService.class).to(CraftingServiceImplementation.class);
    }
}
