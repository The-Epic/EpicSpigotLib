package me.epic.spigotlib.commands;

import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

public abstract class SimpleCommandHandler implements CommandExecutor, TabCompleter {
	private final String permission;

	public SimpleCommandHandler(String permission) {
		this.permission = permission;
	}

	/**
	 *
	 * @param sender Source of the command.  For players tab-completing a
	 *     command inside a command block, this will be the player, not
	 *     the command block.
	 * @param command Command which was executed
	 * @param alias Alias of the command which was used
	 * @param args The arguments passed to the command, including final
	 *     partial argument to be completed
	 * @return StringList
	 */
	@Override
	public abstract List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args);

	@Override
	public abstract boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args);

	/**
	 * Gets the permission for the command
	 * @return The Permission
	 */
	public String getPermission() {
		return permission;
	}

}
