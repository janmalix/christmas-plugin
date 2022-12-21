package me.jantesch.christmasplugin.commands;

import com.google.inject.Inject;
import me.jantesch.christmasplugin.service.CraftingService;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.LogRecord;

public class CraftingCommand extends SingleCommandExecutor {

    private final CraftingService craftingService;

    @Inject
    public CraftingCommand(CraftingService craftingService) {
        super("christmas");
        this.craftingService = craftingService;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Bukkit.getLogger().info("Command christmas executed");

        if (sender instanceof Player player && args.length == 2) {
            var material = Material.valueOf(args[1].toUpperCase());

            switch (args[0]) {
                case "add" -> craftingService.authorize(player, material);
                case "remove" -> craftingService.unauthorize(player, material);
                default -> { return false; }
            }

            return true;
        }

        return false;
    }

}
