package me.epic.spigotlib.utils;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.util.StringUtil;

import com.google.common.reflect.ClassPath;

public class ReflectionUtils {
	public static Set<Class<?>> getAllClassesInPackage(String packageName, ClassLoader loader) {
		try {
			return ClassPath.from(loader).getAllClasses().stream()
					.filter(clazz -> clazz.getPackageName().equalsIgnoreCase(packageName)).map(clazz -> clazz.load())
					.collect(Collectors.toSet());
		} catch (IOException e) {
			e.printStackTrace();
			return Collections.emptySet();
		}
	}

	public static Set<Class<?>> getAllClassesInSubpackages(String packageName, ClassLoader loader) {
		try {
			return ClassPath.from(loader).getAllClasses().stream()
					.filter(clazz -> StringUtil.startsWithIgnoreCase(clazz.getPackageName(), packageName))
					.map(clazz -> clazz.load()).collect(Collectors.toSet());
		} catch (IOException e) {
			e.printStackTrace();
			return Collections.emptySet();
		}
	}
}
