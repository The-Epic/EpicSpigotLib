package me.epic.spigotlib.collections;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

/**
 * The WeightedRandom class allows random selection from a collection of items with different weights.
 * The probability of selecting an item is proportional to its weight.
 *
 * <p>Example usage:
 * <pre>{@code
 * WeightedRandom<String> weightedRandom = new WeightedRandom<>();
 *
 * // Add items with their respective weights
 * weightedRandom.add(0.5, "Apple");
 * weightedRandom.add(0.3, "Banana");
 * weightedRandom.add(0.2, "Orange");
 *
 * // Get the size of the weighted random
 * int size = weightedRandom.size();
 * System.out.println("Size: " + size);  // Output: Size: 3
 *
 * // Get the entries (weight -> value) in the weighted random
 * Set<Map.Entry<Double, String>> entries = weightedRandom.getEntries();
 * for (Map.Entry<Double, String> entry : entries) {
 *     double weight = entry.getKey();
 *     String value = entry.getValue();
 *     System.out.println("Weight: " + weight + ", Value: " + value);
 * }
 * /*
 * Output:
 * Weight: 0.5, Value: Apple
 * Weight: 0.3, Value: Banana
 * Weight: 0.2, Value: Orange
 * *\/
 *
 * // Poll a random item based on the weights
 * String randomItem = weightedRandom.poll();
 * System.out.println("Random item: " + randomItem);
 *
 * // Check if the weighted random is empty
 * boolean isEmpty = weightedRandom.isEmpty();
 * System.out.println("Is empty: " + isEmpty);  // Output: Is empty: false
 * }</pre>
 *
 * @param <T> the type of items stored in the weighted random.
 *
 * @author Jishuna
 */
public class WeightedRandom<T> {
    private final NavigableMap<Double, T> map = new TreeMap<>();
    private final Random random;
    private double total = 0;

    public WeightedRandom() {
        this(new Random());
    }

    public WeightedRandom(Random random) {
        this.random = random;
    }

    public WeightedRandom<T> add(double weight, T result) {
        if (weight <= 0)
            return this;

        total += weight;
        map.put(total, result);
        return this;
    }

    public T poll() {
        double value = random.nextDouble() * total;
        return map.higherEntry(value).getValue();
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public int size() {
        return this.map.size();
    }

    public Set<Entry<Double, T>> getEntries() {
        double prev = 0;
        Set<Entry<Double, T>> entries = new LinkedHashSet<>();

        for (Entry<Double, T> entry : this.map.entrySet()) {
            entries.add(Map.entry(entry.getKey() - prev, entry.getValue()));
            prev = entry.getKey();
        }

        return entries;
    }
}