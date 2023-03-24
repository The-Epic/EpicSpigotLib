package me.epic.spigotlib.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class SimpleCommandHandler implements CommandExecutor, TabCompleter {
	private final String permission;

	public SimpleCommandHandler(String permission) {
		this.permission = permission;
	}

	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
		return null;
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
		return false;
	}

	/**
	 * Gets the permission for the command
	 * @return The Permission
	 */
	public String getPermission() {
		return permission;
	}

}
