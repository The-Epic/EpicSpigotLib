package me.epic.spigotlib.commands;

import lombok.Getter;
import me.epic.spigotlib.formatting.Formatting;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * Abstract class for handling Minecraft commands.
 * Implementations should provide a method for handling commands and optionally a method for tab completion.
 */
public abstract class SimpleCommandHandler implements TabExecutor {

	@Getter
	private final String permission;
	@Getter
	private final String noPermissionMessage;

	/**
	 * Constructs a new SimpleCommandHandler with the given permission.
	 *
	 * @param permission the permission required to execute this command handler
	 * @param noPermissionMessage The message to send if the user doesnt have permission, will send plugin.yml message if null
	 */
	public SimpleCommandHandler(String permission, String noPermissionMessage) {
		this.permission = permission;
		this.noPermissionMessage = noPermissionMessage == null ? null : noPermissionMessage.contains("§") ? noPermissionMessage : Formatting.translate(noPermissionMessage);
	}

	/**
	 * Handles the command with the given arguments.
	 *
	 * @param sender the sender of the command
	 * @param args   the arguments of the command
	 */
	public abstract void handleCommand(CommandSender sender, String[] args);

	/**
	 * Handles tab completion for the command with the given arguments.
	 *
	 * @param sender the sender of the command
	 * @param args   the arguments of the command
	 * @return a list of possible completions for the last argument of the command
	 */
	public List<String> handleTabCompletion(CommandSender sender, String[] args) {
		return Collections.emptyList();
	}

	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
		return handleTabCompletion(sender, args);
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
		if (!sender.hasPermission(permission)) {
			String message = (noPermissionMessage != null) ? noPermissionMessage : (command.getPermissionMessage() != null) ? command.getPermissionMessage() : "No permission.";
			sender.sendMessage(message);
			return true;
		}

		handleCommand(sender, args);
		return true;
	}

}
