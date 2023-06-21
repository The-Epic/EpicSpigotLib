package me.epic.spigotlib.storage;

import lombok.Getter;

@Getter
public class TriValue<F, S, T> {
    private final F first;
    private final S second;
    private final T third;

    public TriValue(F first, S second, T third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }
}