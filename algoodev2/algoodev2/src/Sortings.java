import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Sortings {
    public static <T extends Comparable<T>> void insertionSort(T[] array, int lowIndex, int highIndex, int[] comparisons, int[] swaps, boolean orderFlag){
        for (int i = lowIndex + 1; i <= highIndex; i++) {
            // Extract the key from the array.
            // Note: Reading from the array counts as one move.
            T key = array[i];
            swaps[0]++;

            int j = i - 1;

            // For each element in the sorted portion of the array,
            // shift elements to make room for the key.
            // The condition depends on the required order.
            while (j >= lowIndex) {
                // Increment comparisons for the key comparison that follows.
                comparisons[0]++;

                // For ascending order (orderFlag == false), shift if array[j] > key.
                // For descending order (orderFlag == true), shift if array[j] < key.
                if ((!orderFlag && array[j].compareTo(key) > 0) ||
                    (orderFlag && array[j].compareTo(key) < 0)) {

                    // Shift the element at j to the right (i.e., into position j+1).
                    // This assignment counts as one move.
                    array[j + 1] = array[j];
                    swaps[0]++;

                    j--;
                } else {
                    // When the current element is in the proper order, exit the loop.
                    break;
                }
            }
            // Place the key in its correct position.
            // This assignment (writing into the array) counts as one move.
            array[j + 1] = key;
            swaps[0]++;
        }
    }


    public static <T extends Comparable<T>> void selectionSort(T[] array,int lowIndex,int highIndex,int[] comparisons,int[] swaps,boolean orderFlag) {

        // Iterate through the subarray except for the last element.
        for (int i = lowIndex; i < highIndex; i++) {
            // Start by assuming the selected index is the current index.
            int selectedIndex = i;

            // Iterate the rest of the subarray to find the minimum (or maximum for descending).
            for (int j = i + 1; j <= highIndex; j++) {
                comparisons[0]++; // Each compareTo call counts as one key comparison.

                // For ascending order (orderFlag == false), choose the smallest element.
                // For descending order (orderFlag == true), choose the largest element.
                if ((!orderFlag && array[j].compareTo(array[selectedIndex]) < 0)
                        || (orderFlag && array[j].compareTo(array[selectedIndex]) > 0)) {
                    selectedIndex = j;
                }
            }

            // If selectedIndex is not the current index, swap the elements.
            if (selectedIndex != i) {
                // Swap involves three assignments:
                // 1. T temp = array[i] : Reading from an array element (counts as a move).
                T temp = array[i];
                swaps[0]++;

                // 2. array[i] = array[selectedIndex] : Writing to array[i] from an array element (counts as a move).
                array[i] = array[selectedIndex];
                swaps[0]++;

                // 3. array[selectedIndex] = temp : Writing to array[selectedIndex] (counts as a move).
                array[selectedIndex] = temp;
                swaps[0]++;
            }
        }
    }

    public static <T extends Comparable<T>> void shellSort(
            T[] array,
            int lowIndex,
            int highIndex,
            int[] comparisons,
            int[] swaps,
            boolean orderFlag) {
        
        // Determine the size of the subarray to sort.
        int n = highIndex - lowIndex + 1;

        // Start with a gap and reduce it each iteration.
        for (int gap = n / 2; gap > 0; gap /= 2) {
            // For each gap, perform a gapped insertion sort on the subarray.
            for (int i = lowIndex + gap; i <= highIndex; i++) {
                // Read the key element from the array.
                T key = array[i];
                // Count this read as a move.
                swaps[0]++;

                int j = i;

                // Shift earlier gap-sorted elements up until the correct location for key is found.
                while (j >= lowIndex + gap) {
                    // Count each key comparison.
                    comparisons[0]++;
                    
                    // For ascending order, move elements that are greater than the key.
                    // For descending order, move elements that are less than the key.
                    if ((!orderFlag && array[j - gap].compareTo(key) > 0)
                            || (orderFlag && array[j - gap].compareTo(key) < 0)) {
                        
                        // Shift the element from j-gap to j.
                        array[j] = array[j - gap];
                        swaps[0]++;
                        
                        j -= gap;
                    } else {
                        break;
                    }
                }
                // Place the key in its correct position.
                array[j] = key;
                swaps[0]++;
            }
        }
    }
    public static <T extends Comparable<T>> void mergeSort(
        T[] array,
        int lowIndex,
        int highIndex,
        int[] comparisons,
        int[] swaps,
        boolean orderFlag) {
    if (lowIndex < highIndex) {
        int mid = (lowIndex + highIndex) / 2;
        // Recursively sort left half.
        mergeSort(array, lowIndex, mid, comparisons, swaps, orderFlag);
        // Recursively sort right half.
        mergeSort(array, mid + 1, highIndex, comparisons, swaps, orderFlag);
        // Merge the two sorted halves.
        merge(array, lowIndex, mid, highIndex, comparisons, swaps, orderFlag);
    }
}
private static <T extends Comparable<T>> void merge(T[] array,int lowIndex,int mid,int highIndex,int[] comparisons,int[] swaps,boolean orderFlag) {

int n1 = mid - lowIndex + 1;  // Number of elements in left subarray.
int n2 = highIndex - mid;     // Number of elements in right subarray.

// Create temporary arrays for the left and right subarrays.
T[] left = (T[]) new Comparable[n1];
T[] right = (T[]) new Comparable[n2];

// Copy data to temporary arrays, count each assignment as a data move.
for (int i = 0; i < n1; i++) {
    left[i] = array[lowIndex + i];
    swaps[0]++; // Move: copying from array to left subarray.
}
for (int j = 0; j < n2; j++) {
    right[j] = array[mid + 1 + j];
    swaps[0]++; // Move: copying from array to right subarray.
}

// Merge the temporary arrays back into the main array.
int i = 0;      // Initial index of left subarray.
int j = 0;      // Initial index of right subarray.
int k = lowIndex; // Initial index of merged subarray.

while (i < n1 && j < n2) {
    comparisons[0]++; // Count each comparison between left[i] and right[j].
    // For ascending order (orderFlag false), choose the smaller element.
    // For descending order (orderFlag true), choose the larger element.
    if ((!orderFlag && left[i].compareTo(right[j]) <= 0)
            || (orderFlag && left[i].compareTo(right[j]) >= 0)) {
        array[k] = left[i];
        swaps[0]++; // Move: writing left[i] into array.
        i++;
    } else {
        array[k] = right[j];
        swaps[0]++; // Move: writing right[j] into array.
        j++;
    }
    k++;
}

// Copy any remaining elements of left[], if there are any.
while (i < n1) {
    array[k] = left[i];
    swaps[0]++; // Move: copying remaining left element.
    i++;
    k++;
}

// Copy any remaining elements of right[], if there are any.
while (j < n2) {
    array[k] = right[j];
    swaps[0]++; // Move: copying remaining right element.
    j++;
    k++;
}
}

public static <T extends Comparable<T>> void quickSort(
        T[] array,
        int lowIndex,
        int highIndex,
        int[] comparisons,
        int[] swaps,
        boolean orderFlag) {
    while (lowIndex < highIndex) {
        int pivotIndex = partition(array, lowIndex, highIndex, comparisons, swaps, orderFlag);

        // Recurse on the smaller partition first for reduced stack depth.
        if (pivotIndex - lowIndex < highIndex - pivotIndex) {
            quickSort(array, lowIndex, pivotIndex - 1, comparisons, swaps, orderFlag);
            lowIndex = pivotIndex + 1;  // Tail recursion for the right partition.
        } else {
            quickSort(array, pivotIndex + 1, highIndex, comparisons, swaps, orderFlag);
            highIndex = pivotIndex - 1; // Tail recursion for the left partition.
        }
    }
}

private static <T extends Comparable<T>> int partition(
        T[] array,
        int lowIndex,
        int highIndex,
        int[] comparisons,
        int[] swaps,
        boolean orderFlag) {

    // Choose the pivot as the last element in the subarray.
    T pivot = array[highIndex];
    swaps[0]++; // Reading the pivot counts as a move.

    int i = lowIndex - 1; // Index of the smaller element

    // Loop through the subarray (excluding the pivot)
    for (int j = lowIndex; j < highIndex; j++) {
        // Compute comparison once per iteration.
        int cmp = array[j].compareTo(pivot);
        comparisons[0]++;

        // For ascending order, we want elements less than pivot.
        // For descending order, we want elements greater than pivot.
        if ((!orderFlag && cmp < 0) || (orderFlag && cmp > 0)) {
            i++;
            // Swap array[i] and array[j]
            T temp = array[i];
            swaps[0]++; // Move: reading array[i] into temp.

            array[i] = array[j];
            swaps[0]++; // Move: writing array[j] into array[i].

            array[j] = temp;
            swaps[0]++; // Move: writing temp into array[j].
        }
    }

    // Place the pivot element in its correct position by swapping array[i+1] and array[highIndex]
    T temp = array[i + 1];
    swaps[0]++;

    array[i + 1] = array[highIndex];
    swaps[0]++;

    array[highIndex] = temp;
    swaps[0]++;

    return i + 1;
}

public static <T extends Comparable<T>> void bucketSort(
            T[] array,
            int lowIndex,
            int highIndex,
            int[] comparisons,
            int[] swaps,
            boolean orderFlag) {

        // Determine number of elements in the subarray.
        int n = highIndex - lowIndex + 1;
        if (n <= 0) {
            return;
        }

        // Find the minimum and maximum values in the subarray.
        T min = array[lowIndex];
        swaps[0]++; // Reading an element counts as a data move.
        T max = array[lowIndex];
        swaps[0]++;
        for (int i = lowIndex + 1; i <= highIndex; i++) {
            comparisons[0]++;
            if (array[i].compareTo(min) < 0) {
                min = array[i];
                swaps[0]++; // Updating min counts as a move.
            }
            comparisons[0]++;
            if (array[i].compareTo(max) > 0) {
                max = array[i];
                swaps[0]++; // Updating max counts as a move.
            }
        }

        // Convert min and max to double for computing bucket indices.
        double minVal = ((Number) min).doubleValue();
        double maxVal = ((Number) max).doubleValue();
        double range = maxVal - minVal;

        // Create buckets; here we use one bucket per element.
        int bucketCount = n;
        List<T>[] buckets = new ArrayList[bucketCount];
        for (int i = 0; i < bucketCount; i++) {
            buckets[i] = new ArrayList<>();
        }

        // Distribute the subarray's elements into the buckets.
        for (int i = lowIndex; i <= highIndex; i++) {
            T element = array[i];
            swaps[0]++; // Reading an element counts as a move.
            int bucketIndex;
            if (range == 0) {
                bucketIndex = 0;
            } else {
                double value = ((Number) element).doubleValue();
                bucketIndex = (int) (((value - minVal) / range) * (bucketCount - 1));
            }
            buckets[bucketIndex].add(element);
        }

        // Sort each non-empty bucket using the insertionSort method defined earlier.
        for (int i = 0; i < bucketCount; i++) {
            if (!buckets[i].isEmpty()) {
                // Convert the bucket list to an array.
                T[] bucketArray = (T[]) buckets[i].toArray(new Comparable[buckets[i].size()]);
                // Sort the bucket using insertion sort.
                insertionSort(bucketArray, 0, bucketArray.length - 1, comparisons, swaps, orderFlag);
                // Clear the bucket and add sorted elements back.
                buckets[i].clear();
                for (T item : bucketArray) {
                    buckets[i].add(item);
                }
            }
        }

        // Merge the sorted buckets back into the original array.
        int index = lowIndex;
        if (!orderFlag) {
            // Ascending order: iterate buckets from first to last.
            for (int i = 0; i < bucketCount; i++) {
                for (T item : buckets[i]) {
                    array[index] = item;
                    swaps[0]++; // Each write counts as a move.
                    index++;
                }
            }
        } else {
            // Descending order: iterate buckets in reverse order.
            for (int i = bucketCount - 1; i >= 0; i--) {
                for (T item : buckets[i]) {
                    array[index] = item;
                    swaps[0]++;
                    index++;
                }
            }
        }
    }

    private static <T extends Comparable<T>> boolean isSorted(
            T[] array,
            int lowIndex,
            int highIndex,
            int[] comparisons,
            boolean orderFlag) {
        for (int i = lowIndex; i < highIndex; i++) {
            comparisons[0]++;
            if ((!orderFlag && array[i].compareTo(array[i + 1]) > 0) ||
                (orderFlag && array[i].compareTo(array[i + 1]) < 0)) {
                return false;
            }
        }
        return true;
    }
    
    private static <T> void swap(T[] array, int i, int j, int[] swaps) {
        T temp = array[i];
        swaps[0]++;  // Reading array[i] into temp.
        array[i] = array[j];
        swaps[0]++;  // Writing array[j] into array[i].
        array[j] = temp;
        swaps[0]++;  // Writing temp into array[j].
    }

    public static <T extends Comparable<T>> void yourOwnCustomSort(
            T[] array,
            int lowIndex,
            int highIndex,
            int[] comparisons,
            int[] swaps,
            boolean orderFlag) {

        // Base case: if the partition is empty or has a single element, nothing to sort.
        if (lowIndex >= highIndex) {
            return;
        }

        // Early termination: If the subarray is already sorted, return.
        if (isSorted(array, lowIndex, highIndex, comparisons, orderFlag)) {
            return;
        }

        int size = highIndex - lowIndex + 1;
        // Use insertion sort for small partitions to reduce overhead.
        int THRESHOLD = 5;  
        if (size <= THRESHOLD) {
            insertionSort(array, lowIndex, highIndex, comparisons, swaps, orderFlag);
            return;
        }

        // -------------------------------
        // Use median-of-three pivot selection.
        // -------------------------------
        int mid = lowIndex + (highIndex - lowIndex) / 2;

        // Compare and swap: ensure that array[lowIndex], array[mid], array[highIndex] are in order.
        comparisons[0]++;
        if ((!orderFlag && array[lowIndex].compareTo(array[mid]) > 0) ||
            (orderFlag && array[lowIndex].compareTo(array[mid]) < 0)) {
            swap(array, lowIndex, mid, swaps);
        }
        comparisons[0]++;
        if ((!orderFlag && array[lowIndex].compareTo(array[highIndex]) > 0) ||
            (orderFlag && array[lowIndex].compareTo(array[highIndex]) < 0)) {
            swap(array, lowIndex, highIndex, swaps);
        }
        comparisons[0]++;
        if ((!orderFlag && array[mid].compareTo(array[highIndex]) > 0) ||
            (orderFlag && array[mid].compareTo(array[highIndex]) < 0)) {
            swap(array, mid, highIndex, swaps);
        }
        // After median-of-three, the pivot is now at array[highIndex].
        T pivot = array[highIndex];
        swaps[0]++; // Reading the pivot counts as a move.

        // -------------------------------
        // Partitioning the array based on the pivot.
        // -------------------------------
        int i = lowIndex - 1;
        for (int j = lowIndex; j < highIndex; j++) {
            comparisons[0]++; // Count each comparison between array[j] and pivot.
            if ((!orderFlag && array[j].compareTo(pivot) < 0) ||
                (orderFlag && array[j].compareTo(pivot) > 0)) {
                i++;
                swap(array, i, j, swaps);
            }
        }
        // Place the pivot in its correct position.
        swap(array, i + 1, highIndex, swaps);
        int pivotIndex = i + 1;

        // -------------------------------
        // Recursively sort the left and right partitions.
        // -------------------------------
        yourOwnCustomSort(array, lowIndex, pivotIndex - 1, comparisons, swaps, orderFlag);
        yourOwnCustomSort(array, pivotIndex + 1, highIndex, comparisons, swaps, orderFlag);
    }

    public static <T extends Comparable<T>> void printArray(T[] array, int size) {
        for (int i = 0; i < size; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public static <T extends Comparable<T>> T[] cloneArray(T[] array) {
        return array.clone();
    }

    public static long median(long[] times) {
        Arrays.sort(times);
        return times[times.length / 2];
    }

    interface Sorter<T extends Comparable<T>> {
        void sort(T[] arr, int low, int high, int[] comparisons, int[] swaps, boolean orderFlag);
    }

    

    public static <T extends Comparable<T>> void runAndPrintSort(String algorithmName, T[] originalArray, int runs,
    boolean orderFlag, Sorter<T> sorter) {

long[] times = new long[runs];
int comparisons = 0;
int swaps = 0;
T[] sortedResult = null;

for (int i = 0; i < runs; i++) {
T[] arrCopy = cloneArray(originalArray);
int[] comps = { 0 };
int[] swps = { 0 };
long start = System.nanoTime();
sorter.sort(arrCopy, 0, arrCopy.length - 1, comps, swps, orderFlag);
long end = System.nanoTime();
times[i] = end - start;

// Use the last run's statistics for output.
if (i == runs - 1) {
comparisons = comps[0];
swaps = swps[0];
sortedResult = arrCopy;
}
}
long medianTimeNs = median(times);
double medianTimeMs = medianTimeNs / 1_000_000.0;  // Convert nanoseconds to milliseconds

System.out.println("---- " + algorithmName + " ----");

System.out.println("Comparisons: " + comparisons + ", Data moves: " + swaps
+ ", Time (ms): " + String.format("%.3f", medianTimeMs));
System.out.println();
}
public static void main(String[] args) {
    // Number of elements and number of runs for each test.
    final int n = 20000; // Adjust size as needed.
    final int runs = 5;

    // Create test arrays:
    // (i) Ascending array.
    Integer[] ascending = new Integer[n];
    for (int i = 0; i < n; i++) {
        ascending[i] = i;
    }

    // (ii) Descending array.
    Integer[] descending = new Integer[n];
    for (int i = 0; i < n; i++) {
        descending[i] = n - i;  // e.g., 20, 19, 18, …, 1
    }

    // (iii) Random array.
    // Use fixed sample values to match the sample output format.
    Integer[] randomArray = new Integer[n] ;
    for (int i = 0; i < n; i++) {
        randomArray[i] = (int) (Math.random() * 100); // Random integers between 0 and 99.
    }

    // -------------------------------------------------------------------
    // Test on the Ascending array.
    // -------------------------------------------------------------------
    System.out.println("=======================================");
    System.out.println("Sorting Ascending Array:");

    System.out.println();

    // For ascending arrays we sort in ascending order (orderFlag false).
    runAndPrintSort("Insertion Sort", ascending, runs, false,
            (arr, low, high, comps, swps, order) -> insertionSort(arr, low, high, comps, swps, order));
    runAndPrintSort("Selection Sort", ascending, runs, false,
            (arr, low, high, comps, swps, order) -> selectionSort(arr, low, high, comps, swps, order));
    runAndPrintSort("Shell Sort", ascending, runs, false,
            (arr, low, high, comps, swps, order) -> shellSort(arr, low, high, comps, swps, order));
    runAndPrintSort("Merge Sort", ascending, runs, false,
            (arr, low, high, comps, swps, order) -> mergeSort(arr, low, high, comps, swps, order));
    runAndPrintSort("Quick Sort", ascending, runs, false,
            (arr, low, high, comps, swps, order) -> quickSort(arr, low, high, comps, swps, order));
    runAndPrintSort("Bucket Sort", ascending, runs, false,
            (arr, low, high, comps, swps, order) -> bucketSort(arr, low, high, comps, swps, order));
    runAndPrintSort("Custom Sort", ascending, runs, false,
            (arr, low, high, comps, swps, order) -> yourOwnCustomSort(arr, low, high, comps, swps, order));

    // -------------------------------------------------------------------
    // Test on the Descending array.
    // -------------------------------------------------------------------
    System.out.println("=======================================");
    System.out.println("Sorting Descending Array:");

    System.out.println();

    // For descending arrays, we sort in ascending order (orderFlag false) to get the final sorted order.
    runAndPrintSort("Insertion Sort", descending, runs, false,
            (arr, low, high, comps, swps, order) -> insertionSort(arr, low, high, comps, swps, order));
    runAndPrintSort("Selection Sort", descending, runs, false,
            (arr, low, high, comps, swps, order) -> selectionSort(arr, low, high, comps, swps, order));
    runAndPrintSort("Shell Sort", descending, runs, false,
            (arr, low, high, comps, swps, order) -> shellSort(arr, low, high, comps, swps, order));
    runAndPrintSort("Merge Sort", descending, runs, false,
            (arr, low, high, comps, swps, order) -> mergeSort(arr, low, high, comps, swps, order));
    runAndPrintSort("Quick Sort", descending, runs, false,
            (arr, low, high, comps, swps, order) -> quickSort(arr, low, high, comps, swps, order));
    runAndPrintSort("Bucket Sort", descending, runs, false,
            (arr, low, high, comps, swps, order) -> bucketSort(arr, low, high, comps, swps, order));
    runAndPrintSort("Custom Sort", descending, runs, false,
            (arr, low, high, comps, swps, order) -> yourOwnCustomSort(arr, low, high, comps, swps, order));

    // -------------------------------------------------------------------
    // Test on the Random array.
    // -------------------------------------------------------------------
    System.out.println("=======================================");
    System.out.println("Sorting Random Array:");
    
    System.out.println();

    runAndPrintSort("Insertion Sort", randomArray, runs, false,
            (arr, low, high, comps, swps, order) -> insertionSort(arr, low, high, comps, swps, order));
    runAndPrintSort("Selection Sort", randomArray, runs, false,
            (arr, low, high, comps, swps, order) -> selectionSort(arr, low, high, comps, swps, order));
    runAndPrintSort("Shell Sort", randomArray, runs, false,
            (arr, low, high, comps, swps, order) -> shellSort(arr, low, high, comps, swps, order));
    runAndPrintSort("Merge Sort", randomArray, runs, false,
            (arr, low, high, comps, swps, order) -> mergeSort(arr, low, high, comps, swps, order));
    runAndPrintSort("Quick Sort", randomArray, runs, false,
            (arr, low, high, comps, swps, order) -> quickSort(arr, low, high, comps, swps, order));
    runAndPrintSort("Bucket Sort", randomArray, runs, false,
            (arr, low, high, comps, swps, order) -> bucketSort(arr, low, high, comps, swps, order));
    runAndPrintSort("Custom Sort", randomArray, runs, false,
            (arr, low, high, comps, swps, order) -> yourOwnCustomSort(arr, low, high, comps, swps, order));
}
}