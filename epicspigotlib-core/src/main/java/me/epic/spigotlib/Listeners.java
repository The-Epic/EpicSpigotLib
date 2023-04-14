package me.epic.spigotlib;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Listeners {

    @SneakyThrows
    public static void register(JavaPlugin plugin) {
        final File jarFile = new File(plugin.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        String pluginPackage = getPackageName(plugin);
        if (jarFile.isFile()) {
            final JarFile jar = new JarFile(jarFile);
            final Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                final JarEntry entry = entries.nextElement();
                if (entry.isDirectory()) continue;
                String name = entry.getName().replace("/", ".");
                if (!name.startsWith(pluginPackage)) continue;
                if (name.endsWith(".class")) {
                    Class<?> clazz = Class.forName(name.replace(".class", ""));
                    if (!Listener.class.isAssignableFrom(clazz)) continue;
                    Listener listener;
                    if (hasMainClassConstructor(clazz)){
                        listener = (Listener) clazz.getConstructor(EpicSpigotLib.getPlugin().getClass()).newInstance(plugin);
                    } else if (hasJavaPluginConstructor(clazz)) {
                        listener = (Listener) clazz.getConstructor(JavaPlugin.class).newInstance(plugin);

                    } else {
                        listener = (Listener) clazz.getConstructor().newInstance();
                    }
                    Bukkit.getPluginManager().registerEvents(listener, plugin);

                }

            }
        }
    }

    private static String getPackageName(JavaPlugin plugin) {
        String mainClassName = plugin.getDescription().getMain();
        int lastDot = mainClassName.lastIndexOf('.');
        return lastDot == -1 ? "" : mainClassName.substring(0, lastDot);
    }

    private static boolean hasJavaPluginConstructor(Class<?> clazz) {
        for (Constructor<?> constructor : clazz.getConstructors()) {
            if (constructor.getParameterCount() == 1 && constructor.getParameterTypes()[0] == JavaPlugin.class) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasMainClassConstructor(Class<?> clazz) {
        for (Constructor<?> constructor : clazz.getConstructors()) {
            if (constructor.getParameterCount() == 1 && constructor.getParameterTypes()[0] == EpicSpigotLib.getPlugin().getClass()) {
                return true;
            }
        }
        return false;
    }
}