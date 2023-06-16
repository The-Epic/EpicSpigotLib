package me.epic.spigotlib.storage;

import lombok.Builder;

@Builder
public record BiValue<T, Z>(T firstValue, Z secondValue) {
}
