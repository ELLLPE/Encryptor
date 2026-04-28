package cipherCore;

import java.util.*;

public class MirrorRandomizer {
    public static int[][] mirrorRandomizer() {
        int i = 0;
        int[][] schedules = new int[100][];
        while (i < 100) {
            int size = 100; // 0–99
            int[] array = new int[size];
            Random rand = new Random();

            // Keep track of available indices
            List<Integer> available = new ArrayList<>();
            for (int b = 0; b < size; b++) {
                available.add(b);
            }

            // Fill until no valid pairs remain
            while (!available.isEmpty()) {
                // Pick a random index from the remaining
                int indexA = available.get(rand.nextInt(available.size()));

                // Pick a random indexB that is not the same as indexA and still available
                List<Integer> possible = new ArrayList<>(available);
                possible.remove(Integer.valueOf(indexA));

                if (possible.isEmpty())
                    break; // only one left → can’t pair
                int indexB = possible.get(rand.nextInt(possible.size()));

                // Assign mirror values
                array[indexA] = indexB;
                array[indexB] = indexA;

                // Remove both from availability
                available.remove(Integer.valueOf(indexA));
                available.remove(Integer.valueOf(indexB));

            }

            schedules[i] = array;

            if (i != 0) {
                int o = 0;
                while (o < i) {
                    if (schedules[i].equals(schedules[o])) {
                        System.out.println("Duplicate found at index " + i + " and " + o);
                        i--;
                        break;
                    }
                    o++;
                }
            }
            i++;
        }

        return schedules;

    }
}