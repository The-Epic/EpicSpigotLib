package me.epic.spigotlib.collections;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Big thanks to 7smile7, <a href=
 * "https://www.spigotmc.org/threads/guide-on-workload-distribution-or-how-to-handle-heavy-splittable-tasks.409003/">See
 * his thread here</a>
 * <p>Example usage:
 * <pre>{@code
 * Consumer<String> action = System.out::println;
 * Predicate<String> escapeCondition = value -> value.isEmpty();
 * int distributionSize = 4;
 *
 * TypedDistributionTask<String> task = new TypedDistributionTask<>(action, escapeCondition, distributionSize);
 *
 * task.addValue("Hello");
 * task.addValue("World");
 * // Add more values...
 *
 * // Execute the distribution task
 * task.run();
 * }</pre>
 *
 * @param <T> the type of values stored in the distribution task.
 */
public class TypedDistributionTask<T> implements Runnable {

    protected final Consumer<T> action;
    protected final Predicate<T> escapeCondition;
    protected final List<LinkedList<T>> valueMatrix;
    protected final int distributionSize;
    protected int currentPosition = 0;

    public TypedDistributionTask(Consumer<T> action, Predicate<T> escapeCondition, int distributionSize) {
        this.distributionSize = distributionSize;
        this.action = action;
        this.escapeCondition = escapeCondition;
        this.valueMatrix = new ArrayList<>(distributionSize);

        for (int i = 0; i < distributionSize; i++) {
            this.valueMatrix.add(new LinkedList<>());
        }
    }

    public void addValue(T value) {
        List<T> smallest = this.valueMatrix.get(0);
        if (!smallest.isEmpty()) {
            for (int index = 0; index < this.distributionSize; index++) {
                List<T> next = this.valueMatrix.get(index);
                if (next.size() < smallest.size()) {
                    smallest = next;
                }
            }
        }
        smallest.add(value);
    }

    protected void incrementPosition() {
        if (++this.currentPosition == this.distributionSize) {
            this.currentPosition = 0;
        }
    }

    @Override
    public void run() {
        this.valueMatrix.get(this.currentPosition).removeIf(this::checkThenExecute);
        this.incrementPosition();
    }

    protected boolean checkThenExecute(T value) {
        if (this.escapeCondition.test(value)) {
            return true;
        }
        this.action.accept(value);
        return false;
    }

}