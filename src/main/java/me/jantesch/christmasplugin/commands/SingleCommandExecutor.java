package me.jantesch.christmasplugin.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.command.CommandExecutor;

@Data
@AllArgsConstructor
public abstract class SingleCommandExecutor implements CommandExecutor {

    private final String name;

}
