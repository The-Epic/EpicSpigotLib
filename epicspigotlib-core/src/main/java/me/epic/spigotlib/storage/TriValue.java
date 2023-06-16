package me.epic.spigotlib.storage;

import lombok.Builder;

@Builder
public record TriValue<T, Z, V>(T firstValue, Z secondValue, V thirdValue) {
}
