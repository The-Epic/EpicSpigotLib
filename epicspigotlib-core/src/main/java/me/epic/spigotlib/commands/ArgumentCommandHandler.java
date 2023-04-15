package me.epic.spigotlib.commands;

import me.epic.spigotlib.language.MessageConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

import java.util.*;
import java.util.Map.Entry;

/**
 * A command handler that handles subcommands based on the first argument provided.
 * Each subcommand should be associated with a separate {@link SimpleCommandHandler}.
 */
public class ArgumentCommandHandler extends SimpleCommandHandler {
	private final Map<String, SimpleCommandHandler> subcommands = new HashMap<>();
	private final MessageConfig messageConfig;
	private SimpleCommandHandler defaultExecutor;

	/**
	 * Constructs an ArgumentCommandHandler with a specified message configuration and permission.
	 *
	 * @param messageConfig the configuration for messages sent by this command handler
	 * @param permission the permission required to execute this command handler
	 */
	public ArgumentCommandHandler(MessageConfig messageConfig, String permission) {
		super(permission);
		this.messageConfig = messageConfig;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		handleCommand(sender, args);
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return handleTabCompletion(sender, args);
	}

	/**
	 * Handles the command by delegating to the appropriate subcommand executor or the default executor.
	 *
	 * @param sender the sender of the command
	 * @param args the arguments provided for the command
	 */
	public void handleCommand(CommandSender sender, String[] args) {
		if (!sender.hasPermission(getPermission())) {
			sender.sendMessage(messageConfig.getString("no-permission"));
			return;
		}

		if (args.length == 0) {
			if (this.defaultExecutor != null) {
				this.defaultExecutor.handleCommand(sender, args);
			} else {
				sendUsage(sender, "none");
			}
		} else {
			SimpleCommandHandler executor = this.subcommands.get(args[0]);

			if (executor == null) {
				sendUsage(sender, args[0]);
				return;
			}

			executor.handleCommand(sender, Arrays.copyOfRange(args, 1, args.length));
		}
	}

	/**
	 * Handles tab completion for this command handler by delegating to the appropriate subcommand executor.
	 *
	 * @param sender the sender of the tab completion request
	 * @param args the arguments provided for the tab completion request
	 * @return a list of suggestions for tab completion
	 */
	public List<String> handleTabCompletion(CommandSender sender, String[] args) {
		if (args.length == 1) {
			List<String> suggestions = new ArrayList<>();
			List<String> validSubcommands = new ArrayList<>();

			for (Entry<String, SimpleCommandHandler> entry : this.subcommands.entrySet()) {
				if (sender.hasPermission(entry.getValue().getPermission()))
					validSubcommands.add(entry.getKey());
			}

			StringUtil.copyPartialMatches(args[0], validSubcommands, suggestions);
			return suggestions;
		} else if (args.length > 1) {
			SimpleCommandHandler executor = this.subcommands.get(args[0]);
			if (executor != null)
				return executor.handleTabCompletion(sender, Arrays.copyOfRange(args, 1, args.length + 1));
		}
		return Collections.emptyList();
	}

	private void sendUsage(CommandSender sender, String arg) {
		String msg = messageConfig.getString("command-usage");
		msg = msg.replace("%arg%", arg);
		msg = msg.replace("%args%", String.join(", ", this.subcommands.keySet()));

		sender.sendMessage(msg);
	}

	/**
	 * Adds a subcommand executor for the specified argument.
	 *
	 * @param arg the argument associated with the executor
	 * @param executor the executor for the subcommand
	 */
	public void addArgumentExecutor(String arg, SimpleCommandHandler executor) {
		this.subcommands.put(arg, executor);
	}

	/**
	 * Sets the default executor to be used when no valid subcommand is provided.
	 *
	 * @param executor the default executor to be used
	 */
	public void setDefault(SimpleCommandHandler executor) {
		this.defaultExecutor = executor;
	}
}
