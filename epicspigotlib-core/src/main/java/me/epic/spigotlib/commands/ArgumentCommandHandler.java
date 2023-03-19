package me.epic.spigotlib.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import me.epic.spigotlib.language.MessageConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

public class ArgumentCommandHandler extends SimpleCommandHandler {
	private final Map<String, SimpleCommandHandler> subcommands = new HashMap<>();
	private final MessageConfig messageConfig;

	private CommandExecutor defaultExecutor;

	public ArgumentCommandHandler(MessageConfig messageConfig, String permission) {
		super(permission);
		this.messageConfig = messageConfig;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		if (!sender.hasPermission(getPermission())) {
			sender.sendMessage(messageConfig.getString("no-permission"));
			return true;
		}

		if (args.length == 0) {
			if (this.defaultExecutor != null) {
				return this.defaultExecutor.onCommand(sender, command, alias, args);
			} else {
				sendUsage(sender, "none");
				return true;
			}
		} else if (args.length > 0) {
			SimpleCommandHandler executor = this.subcommands.get(args[0]);

			if (executor == null) {
				sendUsage(sender, args[0]);
				return true;
			}

			return executor.onCommand(sender, command, alias, Arrays.copyOfRange(args, 1, args.length + 1));
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
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
				return executor.onTabComplete(sender, command, alias, Arrays.copyOfRange(args, 1, args.length + 1));
		}
		return Collections.emptyList();
	}

	private void sendUsage(CommandSender sender, String arg) {
		String msg = messageConfig.getString("command-usage");
		msg = msg.replace("%arg%", arg);
		msg = msg.replace("%args%", String.join(", ", this.subcommands.keySet()));

		sender.sendMessage(msg);
	}

	public void addArgumentExecutor(String arg, SimpleCommandHandler executor) {
		this.subcommands.put(arg, executor);
	}

	public void setDefault(SimpleCommandHandler executor) {
		this.defaultExecutor = executor;
	}
}
