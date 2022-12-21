package me.jantesch.christmasplugin.commands;

import com.google.inject.Inject;

import java.util.Set;

public class CommandServiceImplementation implements CommandService {

    private final Set<SingleCommandExecutor> executors;

    @Inject
    public CommandServiceImplementation(Set<SingleCommandExecutor> executors) {
        this.executors = executors;
    }

    @Override
    public Set<SingleCommandExecutor> getExecutors() {
        return executors;
    }
}
