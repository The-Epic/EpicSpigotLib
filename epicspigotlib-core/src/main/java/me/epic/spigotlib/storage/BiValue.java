package me.epic.spigotlib.storage;

import lombok.Getter;

@Getter
public class BiValue<F, S> {
    private final F first;
    private final S second;

    public BiValue(F first, S second) {
        this.first = first;
        this.second = second;
    }
}
