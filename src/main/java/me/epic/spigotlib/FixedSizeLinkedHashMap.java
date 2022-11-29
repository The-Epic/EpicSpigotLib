package me.epic.spigotlib;

import java.util.LinkedHashMap;

public class FixedSizeLinkedHashMap<K, V> extends LinkedHashMap<K, V> {
	private static final long serialVersionUID = -800060867094207435L;
	
	private final int maxSize;

	public FixedSizeLinkedHashMap(int size) {
		this.maxSize = size;
	}

	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
		return size() > maxSize;
	}
}
