package me.epic.spigotlib.storage;

import lombok.Builder;

@Builder
public record QuadValue<T, Z, V, Y>(T firstValue, Z secondValue, V thirdValue, Y fourthValue) {
}
