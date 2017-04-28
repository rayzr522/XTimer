package com.rayzr522.xtimer.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by Rayzr522 on 4/28/17.
 */
public class ArrayUtils {

    public static <T> T[] slice(T[] arr, int start, int end) {
        if (start < 0) {
            throw new ArrayIndexOutOfBoundsException("start cannot be less than 0!");
        }
        if (end > arr.length) {
            throw new ArrayIndexOutOfBoundsException("end cannot be greater than the array length!");
        }

        if (end <= start) {
            throw new IllegalArgumentException("end cannot be less than or equal to start!");
        }

        return Arrays.copyOfRange(arr, start, end);
    }

    public static <T> T[] slice(T[] arr, int start) {
        return slice(arr, start, arr.length);
    }

    public static <T> String join(T[] arr, String delimiter) {
        return Arrays.stream(arr).map(Object::toString).collect(Collectors.joining(delimiter));
    }
}
