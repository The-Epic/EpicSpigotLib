package me.epic.spigotlib.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

public class ArgumentCommandHandler extends SimpleCommandHandler {
	private final Map<String, SimpleCommandHandler> subcommands = new HashMap<>();

	private CommandExecutor defaultExecutor;

	public ArgumentCommandHandler(String permission) {
		super(permission);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		if (!sender.hasPermission(getPermission())) {
			return false;
		}

		if (args.length == 0) {
			if (this.defaultExecutor != null) {
				return this.defaultExecutor.onCommand(sender, command, alias, args);
			} else {
				return false;
			}
		} else if (args.length > 0) {
			SimpleCommandHandler executor = this.subcommands.get(args[0]);

			if (executor == null) {
				return false;
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

	public void addArgumentExecutor(String arg, SimpleCommandHandler executor) {
		this.subcommands.put(arg, executor);
	}

	public void setDefault(SimpleCommandHandler executor) {
		this.defaultExecutor = executor;
	}
}
