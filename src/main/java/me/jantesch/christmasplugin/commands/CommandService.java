package me.jantesch.christmasplugin.commands;

import java.util.Set;

public interface CommandService {

    Set<SingleCommandExecutor> getExecutors();

}
