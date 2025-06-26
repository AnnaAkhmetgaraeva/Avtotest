package org.example.Certification1;

    import java.util.Arrays;

    public class Task3 {
        public static void main(String[] args) {
            int[] numbers = {1, 5, 7, 3, 2, 0, 4, 9, 6};
            int secondLargest = findSecondLargestSorting(numbers);
            System.out.println("Второй по величине элемент: " + secondLargest);
        }

        public static int findSecondLargestSorting(int[] array) {
            if (array.length < 2) {
                throw new IllegalArgumentException("Массив должен содержать как минимум 2 элемента");
            }

            Arrays.sort(array);

            // Ищем первый элемент, который меньше максимального (на случай дубликатов)
            for (int i = array.length - 2; i >= 0; i--) {
                if (array[i] != array[array.length - 1]) {
                    return array[i];
                }
            }

            throw new IllegalArgumentException("Все элементы массива одинаковые");
        }
    }
