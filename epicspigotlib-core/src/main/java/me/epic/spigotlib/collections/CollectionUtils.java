package me.epic.spigotlib.collections;

public class CollectionUtils {

    /**
     * Shuffle an array of type T
     *
     * @param array to be shuffled
     */
    public static <T> void shuffle(T[] array) {
        int random;
        for (int i = 1; i < array.length; i++) {
            random = (int) (Math.random() * i);
            T temp = array[i - 1];
            array[i - 1] = array[random];
            array[random] = temp;
        }
    }
}
