package me.epic.spigotlib.events;

import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class EventConsumer<T extends Event> implements Listener {

	private Consumer<T> handler;
	private Class<T> eventClass;

	public EventConsumer(Class<T> eventClass, Consumer<T> handler) {
		this.handler = handler;
		this.eventClass = eventClass;
	}

	public void register(Plugin plugin) {
		register(plugin, EventPriority.NORMAL);
	}

	@SuppressWarnings("unchecked")
	public void register(Plugin plugin, EventPriority priority) {
		Bukkit.getPluginManager().registerEvent(eventClass, this, priority, (listener, event) -> handleEvent((T) event),
				plugin);
	}

	@EventHandler
	public void handleEvent(T event) {
		if (this.eventClass.isAssignableFrom(event.getClass())) {
			handler.accept(this.eventClass.cast(event));
		}
	}

}
