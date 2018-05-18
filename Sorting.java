import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Arrays;

public class Sorting {

    private static int[] arr;
    private static int[] arrCopy;
    private static int[] mergeArr;
    private static BufferedReader read;
    private static Random randomGenerator;

    private static int size;
    private static int random;

    private static void printArray(String msg) {
        System.out.print(msg + " [" + arr[0]);
        for(int i=1; i<size; i++) {
            System.out.print(", " + arr[i]);
        }
        System.out.println("]");
    }

    public static void exchange(int i, int j){
        int t=arr[i];
        arr[i]=arr[j];
        arr[j]=t;
    }

    public static void insertSort(int left, int right) {
        // insertSort the subarray arr[left, right]
        int i, j;

        for(i=left+1; i<=right; i++) {
            int temp = arr[i];           // store a[i] in temp
            j = i;                       // start shifts at i
            // until one is smaller,
            while(j>left && arr[j-1] >= temp) {
                arr[j] = arr[j-1];        // shift item to right
                --j;                      // go left one position
            }
            arr[j] = temp;              // insert stored item
        }  // end for
    }  // end insertSort()

    public static void insertionSort() {
        insertSort(0, size-1);
    } // end insertionSort()

    public static void maxheapify(int i, int n) {
        // Pre: the left and right subtrees of node i are max heaps.
        // Post: make the tree rooted at node i as max heap of n nodes.
        int max = i;
        int left=2*i+1;
        int right=2*i+2;

        if(left < n && arr[left] > arr[max]) max = left;
        if(right < n && arr[right] > arr[max]) max = right;

        if (max != i) {  // node i is not maximal
            exchange(i, max);
            maxheapify(max, n);
        }
    }

    public static void heapsort(){
        // Build an in-place bottom up max heap
        for (int i=size/2; i>=0; i--) maxheapify(i, size);

        for(int i=size-1;i>0;i--) {
            exchange(0, i);       // move max from heap to position i.
            maxheapify(0, i);     // adjust heap
        }
    }

    private static boolean isSorted(int low, int high) {
        for (int i = low; i < high; i++) {
            if (arr[i] > arr[i + 1]) return false; // It is proven that the array is not sorted.
        }
        return true; // If this part has been reached, the array must be sorted.
    }

    private static void mergesort(int low, int high) {
        // sort arr[low, high-1]
        // Check if low is smaller then high, if not then the array is sorted
        if (low < high-1) {
            // Get the index of the element which is in the middle
            int middle = (high + low) / 2;
            // Sort the left side of the array
            mergesort(low, middle);
            // Sort the right side of the array
            mergesort(middle, high);
            // Combine them both
            merge(low, middle, high);
        }
    }

    private static void mergesort2(int low, int high) {
        if (high - low >= 100) { //check if size of sub-array is < 100
            int middlePoint = (high + low) / 2;
            mergesort2(low, middlePoint);
            mergesort2(middlePoint, high);
            merge(low, middlePoint, high);
        }
        else {
            insertSort(low, high - 1); //uses insertion sort if it is < 100
        }
    }

    private static void merge(int low, int middle, int high) {
        // merge arr[low, middle-1] and arr[middle, high-1] into arr[low, high-1]

        // Copy first part into the arrCopy array
        for (int i = low; i < middle; i++) {
            mergeArr[i] = arr[i];
        }

        int i = low;
        int j = middle;
        int k = low;

        // Copy the smallest values from either the left or the right side back        // to the original array
        while (i < middle && j < high)
            if (mergeArr[i] <= arr[j])
                arr[k++] = mergeArr[i++];
            else
                arr[k++] = arr[j++];

        // Copy the rest of the left part of the array into the original array
        while (i < middle) arr[k++] = mergeArr[i++];
    }

    public static void naturalMergesort() {
        int run[], i, j, s, t, m;

        run = new int[size/2];

        // Step 1: identify runs from the input array arr[]
        i = m = 1;
        run[0] = 0;
        while (i < size) {
            if (arr[i-1] > arr[i])
                if (run[m-1]+1 == i) {     // make sure each run has at least two

                    j = i+1;
                    s = 0;
                    while (j < size && arr[j-1] >= arr[j]) j++;     // not stable

                    // reverse arr[i-1, j-1];
                    s = i - 1;
                    t = j - 1;
                    while (s < t) exchange(s++, t--);

                    i = j;
                } else
                    run[m++] = i++;
            else i++;
        }

        // Step 2: merge runs bottom-up into one run                                                                       
        t = 1;
        while (t < m) {
            s = t;
            t = s<<1;
            i = 0;
            while (i+t < m) {
                merge(run[i], run[i+s], run[i+t]);
                i += t;
            }
            if (i+s < m) merge(run[i], run[i+s], size);
        }

    }

    private static void quicksort(int low, int high) {
        int i = low, j = high;

        // Get the pivot element from the middle of the list
        int pivot = arr[(high+low)/2];

        // Divide into two lists
        while (i <= j) {
            // If the current value from the left list is smaller then the pivot
            // element then get the next element from the left list
            while (arr[i] < pivot) i++;

            // If the current value from the right list is larger then the pivot
            // element then get the next element from the right list
            while (arr[j] > pivot) j--;

            // If we have found a value in the left list which is larger than
            // the pivot element and if we have found a value in the right list
            // which is smaller then the pivot element then we exchange the
            // values.
            // As we are done we can increase i and j
            if (i < j) {
                exchange(i, j);
                i++;
                j--;
            } else if (i == j) { i++; j--; }
        }

        // Recursion
        if (low < j)
            quicksort(low, j);
        if (i < high)
            quicksort(i, high);
    }

    private static void quicksort2(int low, int high, int pivotSelect) {
        int i = low, j = high;
        int pivot = 0;

        if(high - low >= 3) {
            switch (pivotSelect) {
                case 2:
                    pivot = arr[(high + low) / 2];
                    break;
                case 4:
                    if ((high - low) >= 3) {
                        pivot = quicksort4(low, high);
                    } else pivot = arr[(high + low) / 2];
                    break;
                case 5:
                    if((high - low) >= 9) {
                        pivot = quickSort5(low, high);
                    } else pivot = arr[(high + low) / 2];
                    break;
                default:
                    pivot = arr[(high + low) / 2];
                    break;
            }
        }

        while (i <= j) {
            while (arr[j] > pivot) j--;
            while (arr[i] < pivot) i++;
            if (i == j) {
                j--;
                i++;
            } else if (i < j) {
                exchange(i, j);
                j--;
                i++;
            }
        }
        if (low < j) {
            if (j - low >= 100) quicksort2(low, j, pivotSelect); //check if sub-array is < 100
            else insertSort(low, j); //uses insertion sort if it is < 100
        }
        if (i < high) {
            if (high - i >= 100) quicksort2(i, high, pivotSelect); //check if sub-array is < 100
            else insertSort(i, high); //uses insertion sort if it is < 100
        }
    }

    private static void quicksort3(int low, int high){
        if (!isSorted(low, high)) {
            int templow = low, temphigh = high;
            int pivot = arr[(high + low) / 2];
            while (templow <= temphigh){
                while (arr[templow] < pivot) templow++;
                while (arr[temphigh] > pivot) temphigh--;
                if (templow == temphigh){
                    templow++;
                    temphigh--;
                } else if (templow < temphigh){
                    exchange(templow, temphigh);
                    templow++;
                    temphigh--;
                }
            }
            if (low < temphigh){
                if (temphigh - low >= 32) quicksort3(low, temphigh);
                else insertSort(low, temphigh);
            }
            if (templow < high){
                if (high - templow >= 32) quicksort3(templow, high);
                else insertSort(templow, high);
            }
        }
    }

    private static int quicksort4(int low, int high) {
        int[] pivot = new int[3];
        pivot[0] = arr[low] ;
        pivot[1] = arr[(high + low) /2];
        pivot[2] = arr[high];
        int count;
        for (int i = 0; i <= 2; i++) {
            int temp = pivot[i];
            count = i;
            while (count > 0 && pivot[count - 1] >= temp) {
                pivot[count] = pivot[count - 1];
                --count;
            }
            pivot[count] = temp;
        }
        return pivot[1];
    }


    private static int quickSort5 (int low, int high) {
        int[] piv = new int[9];
        int divisible = (high - low + 1) - ((high - low + 1) % 9);
        int aPiece = divisible / 9;
        for (int i = 0; i < 8; i++) {
            piv[i] = arr[low + aPiece * i];
        }
        piv[8] = arr[high];
        int i, j;
        for (i = 0; i < 9; i++) {
            int temp = piv[i];
            j = i;
            while (j > 0 && piv[j - 1] >= temp) {
                piv[j] = piv[j - 1];
                --j;
            }
            piv[j] = temp;
        }
        return piv[4];
    }

    public static void demo1 (String input) {
        // demonstration of sorting algorithms on random input

        long start, finish;
        System.out.println();

        // Heap sort      
        for (int i=0; i<size; i++) arr[i] = arrCopy[i];
        if (size < 101) printArray("in");
        start = System.currentTimeMillis();
        heapsort();
        finish = System.currentTimeMillis();
        if (size < 101) printArray("out");
        System.out.println("heapsort with " + input + " input: " + (finish-start) + " milliseconds.");

        // Merge sort
        for (int i=0; i<size; i++) arr[i] = arrCopy[i];
        if (size < 101) printArray("in");
        start = System.currentTimeMillis();
        mergesort(0, size);
        finish = System.currentTimeMillis();
        if (size < 101) printArray("out");
        System.out.println("mergesort with " + input + " input: " + (finish-start) + " milliseconds.");

        // Natural Merge sort
        for (int i=0; i<size; i++) arr[i] = arrCopy[i];
        if (size < 101) printArray("in");
        start = System.currentTimeMillis();
        naturalMergesort();
        finish = System.currentTimeMillis();
        if (size < 101) printArray("out");
        System.out.println("natural mergesort on " + input + " input: " + (finish-start) + " milliseconds.");

        // Quick sort
        for (int i=0; i<size; i++) arr[i] = arrCopy[i];
        if (size < 101) printArray("in");
        start = System.currentTimeMillis();
        quicksort(0, size-1);
        finish = System.currentTimeMillis();
        if (size < 101) printArray("out");
        System.out.println("quicksort on " + input + " input: " + (finish-start) + " milliseconds.");
    }

    public static void demo2 (String input) {
        // demonstration of sorting algorithms on nearly sorted input

        long start, finish;

        demo1(input);

        // Insert sort on nearly-sorted array      
        for(int i=0; i<size; i++) arr[i] = arrCopy[i];
        if (size < 101) printArray("in");
        start = System.currentTimeMillis();
        insertionSort();
        finish = System.currentTimeMillis();
        if (size < 101) printArray("out");
        System.out.println("insertsort on " + input + " input: " + (finish-start) + " milliseconds.");
    }

    /**
     * This is a method that compares four different sorting algorithms
     * (mergeSort, mergeSort2, quickSort, quickSort2) on the same set of
     * 100 random arrays of size 1,000,000.
     *
     * Results: insertion sort works best
     */
    private static void task1() {
        long start = 0, finish = 0;

        System.out.println();

        long mergesortRT = 0;
        long mergesort2RT = 0;
        long quicksortRT = 0;
        long quicksort2RT = 0;

        size = 1000000;

        System.out.println("Task 1:");

        randomGenerator = new Random();
        for (int i = 0; i < 100; i++) {
            arr = new int[size];
            arrCopy = new int[size];
            mergeArr = new int[size];
            random = size * 10;
            for (int j = 0; j < size; j++) {
                arr[j] = arrCopy[j] = randomGenerator.nextInt(random);
            }
            // mergesort
            System.arraycopy(arrCopy, 0, arr, 0, size);
            if (size < 101) printArray("in");
            start = System.currentTimeMillis();
            mergesort(0, size);
            finish = System.currentTimeMillis();
            if (size < 101) printArray("out");
            mergesortRT += (finish - start);
            // mergesort2
            System.arraycopy(arrCopy, 0, arr, 0, size);
            if (size < 101) printArray("in");
            start = System.currentTimeMillis();
            mergesort2(0, size);
            finish = System.currentTimeMillis();
            if (size < 101) printArray("out");
            mergesort2RT += (finish - start);
            // quicksort
            System.arraycopy(arrCopy, 0, arr, 0, size);
            if (size < 101) printArray("in");
            start = System.currentTimeMillis();
            quicksort(0, size - 1);
            finish = System.currentTimeMillis();
            if (size < 101) printArray("out");
            quicksortRT += (finish - start);
            // quicksort2
            System.arraycopy(arrCopy, 0, arr, 0, size);
            if (size < 101) printArray("in");
            start = System.currentTimeMillis();
            quicksort2(0, size - 1, 2);
            finish = System.currentTimeMillis();
            if (size < 101) printArray("out");
            quicksort2RT += (finish - start);
        }
        //print all the running time for the four sorting algorithms we used.
        System.out.println("mergesort R.T.: " + mergesortRT + " ms.");
        System.out.println("mergesort2 R.T.: " + mergesort2RT + " ms.");
        System.out.println("quicksort R.T.: " + quicksortRT + " ms.");
        System.out.println("quicksort2 R.T.: " + quicksort2RT + " ms.");
    }

    /**
     * This is a method checks to see that the subarray of arr is already sorted
     *
     * Results: quicksort3 is the fastest.
     */
    private static void task2() {
        long start = 0, finish = 0;
        final int REPEATTIMES = 10;
        System.out.println();
        long quickRunningTimeSum = 0;
        long quick2RunningTimeSum = 0;
        long quick3RunningTimeSum = 0;
        size = 1000000;
        System.out.println("Task 2:");
        for (int i = 1; i <= 3; i++) {
            quickRunningTimeSum = 0;
            quick2RunningTimeSum = 0;
            quick3RunningTimeSum = 0;
            switch(i) {
                case 1:
                    System.out.println("Random array");
                    break;
                case 2:
                    System.out.println("Sorted array");
                    break;
                case 3:
                    System.out.println("Reverse sorted array");
            }
            for (int j = 0; j < REPEATTIMES; j++) {
                switch (i) {
                    case 1:
                        arr = new int[size];
                        arrCopy = new int[size];
                        mergeArr = new int[size];
                        randomGenerator = new Random();
                        random = size * 10;
                        for (int k = 0; k < size; k++)
                            arrCopy[k] = randomGenerator.nextInt(random);
                        break;
                    case 2: // using a sorted array
                        for (int k = 0; k < size; k++) arrCopy[k] = k + 1;
                        break;
                    case 3: // a reverse sorted array
                        for (int k = 0; k < size; k++) arrCopy[k] = size - k;
                        break;
                }
                // quicksort
                System.arraycopy(arrCopy, 0, arr, 0, size);
                if (size < 101) printArray("in");
                start = System.currentTimeMillis();
                quicksort(0, size - 1);
                finish = System.currentTimeMillis();
                if (size < 101) printArray("out");
                quickRunningTimeSum += (finish - start);
                // quicksort2
                System.arraycopy(arrCopy, 0, arr, 0, size);
                if (size < 101) printArray("in");
                start = System.currentTimeMillis();
                quicksort2(0, size - 1, 2);
                finish = System.currentTimeMillis();
                if (size < 101) printArray("out");
                quick2RunningTimeSum += (finish - start);
                // quicksort3
                System.arraycopy(arrCopy, 0, arr, 0, size);
                if (size < 101) printArray("in");
                start = System.currentTimeMillis();
                quicksort3(0, size - 1);
                finish = System.currentTimeMillis();
                if (size < 101) printArray("out");
                quick3RunningTimeSum += (finish - start);
            }
            switch (i) {
                case 1:
                    System.out.println
                            ("quicksort with random arrays: " + quickRunningTimeSum + " ms.");
                    System.out.println
                            ("quicksort2 with random arrays: " + quick2RunningTimeSum + " ms.");
                    System.out.println
                            ("quicksort3 with random arrays: " + quick3RunningTimeSum + " ms.");
                    break;
                case 2:
                    System.out.println
                            ("quick sort with sorted arrays: " + quickRunningTimeSum + " ms.");
                    System.out.println
                            ("quick sort 2 with sorted arrays: " + quick2RunningTimeSum + " ms.");
                    System.out.println
                            ("quick sort 3 with sorted arrays: " + quick3RunningTimeSum + " ms.");
                    break;
                case 3:
                    System.out.println
                            ("quick sort with reverse sorted arrays: " + quickRunningTimeSum + " ms.");
                    System.out.println
                            ("quick sort 2 with reverse sorted arrays: " + quick2RunningTimeSum + " ms.");
                    System.out.println
                            ("quick sort 3 with reverse sorted arrays: " + quick3RunningTimeSum + " ms.");
                    break;
            }
        }
    }

    /** This method checks the following two ideas in quicksort:
     * One uses the median of the three elements, i.e., first, middle, and last, as pivot for partition.
     * One first selects 9 elements equally spreading out in the
     * array, including the first and the last element.
     * The method will compute the median of the first three,
     * the median of the next three, and the median of the last three,
     * then use the median of three medians as pivot
     *
     * Result:
     */
    /*private static void task3() {
        long start = 0, finish = 0;
        final int REPEATTIMES = 10;
        System.out.println();
        long heapsortRT;
        long quicksortRT;
        long quicksort3RT;
        long quicksort4RT;
        long quicksort5RT;
        long naturalmergesortRT;

        size = 1000000;

        System.out.println("Task 3:");
        for (int i = 1; i <= 3; i++) {
            heapsortRT = 0;
            quicksortRT = 0;
            quicksort3RT = 0;
            quicksort4RT = 0;
            quicksort5RT = 0;
            naturalmergesortRT = 0;
            if(i == 1) {
                System.out.println("Random array");
            }
            else if (i==2){
                 System.out.println("Reverse sorted array");
            }
            else if (i==3){
                System.out.println("Organ-pipe array");
            }
            for (int j = 0; j < REPEATTIMES; j++) {
                if(i==1) {
                    arr = new int[size];
                    arrCopy = new int[size];
                    mergeArr = new int[size];
                    randomGenerator = new Random();
                    random = size * 10;
                    for (int k = 0; k < size; k++)
                        arrCopy[k] = randomGenerator.nextInt(random);
                }
                else if(i==2) { // a reverse sorted array
                    for (int k = 0; k < size; k++) arrCopy[k] = size - k;
                }
                else if (i==3) { // organ-pipe shaped array
                    arr = new int[size];
                    arrCopy = new int[size];
                    mergeArr = new int[size];
                    randomGenerator = new Random();
                    random = size * 10;
                    for (int k = 0; k < size / 2; k++) {
                        arrCopy[k] = size / 2 - k;
                        arrCopy[size - 1 - k] = size / 2 - k;
                    }
                }
                System.arraycopy(arrCopy, 0, arr, 0, size);
                if (size < 101) printArray("in");
                start = System.currentTimeMillis();
                heapsort();
                finish = System.currentTimeMillis();
                if (size < 101) printArray("out");
                heapsortRT += (finish - start);
                System.arraycopy(arrCopy, 0, arr, 0, size);
                if (size < 101) printArray("in");
                start = System.currentTimeMillis();
                quicksort(0, size - 1);
                finish = System.currentTimeMillis();
                if (size < 101) printArray("out");
                quicksortRT += (finish - start);
                // Quick sort 3
                System.arraycopy(arrCopy, 0, arr, 0, size);
                if (size < 101) printArray("in");
                start = System.currentTimeMillis();
                quicksort3(0, size - 1);
                finish = System.currentTimeMillis();
                if (size < 101) printArray("out");
                quicksort3RT += (finish - start);
                // Quick sort 4
                System.arraycopy(arrCopy, 0, arr, 0, size);
                if (size < 101) printArray("in");
                start = System.currentTimeMillis();
                quicksort2(0, size - 1, 4);
                finish = System.currentTimeMillis();
                if (size < 101) printArray("out");
                quicksort4RT += (finish - start);
                // Quick sort 5
                System.arraycopy(arrCopy, 0, arr, 0, size);
                if (size < 101) printArray("in");
                start = System.currentTimeMillis();
                quicksort2(0, size - 1, 5);
                finish = System.currentTimeMillis();
                if (size < 101) printArray("out");
                quicksort5RT += (finish - start);
                // Natural merge sort
                System.arraycopy(arrCopy, 0, arr, 0, size);
                if (size < 101) printArray("in");
                start = System.currentTimeMillis();
                naturalMergesort();
                finish = System.currentTimeMillis();
                if (size < 101) printArray("out");
                naturalmergesortRT += (finish - start);
            }
            if(i==1) {
                System.out.println
                        ("heapsort with random arrays: " + heapsortRT + " ms.");
                System.out.println
                        ("quicksort with random arrays: " + quicksortRT + " ms.");
                System.out.println
                        ("quicksort3 with random arrays: " + quicksort3RT + " ms.");
                System.out.println
                        ("quicksort4 with random arrays: " + quicksort4RT + " ms.");
                System.out.println
                        ("quicksort5 with random arrays: " + quicksort5RT + " ms.");
                System.out.println
                        ("naturalmergesort with random arrays: " + naturalmergesortRT + " ms.");
            }
            else if (i==2) {
                System.out.println
                        ("heapsort with reversely sorted arrays: " + heapsortRT + " ms.");
                System.out.println
                        ("quicksort with reversely sorted arrays: " + quicksortRT + " ms.");
                System.out.println
                        ("quicksort3 with reversely sorted arrays: " + quicksort3RT + " ms.");
                System.out.println
                        ("quicksort4 with reversely sorted arrays: " + quicksort4RT + " ms.");
                System.out.println
                        ("quicksort5 with reversely sorted arrays: " + quicksort5RT + " ms.");
                System.out.println
                        ("naturalmergesort with reversely sorted arrays: " + naturalmergesortRT + " ms.");
            }
            else if (i==3){
                System.out.println
                        ("heapsort with organ-pipe shaped arrays: " + heapsortRT + " ms.");
                System.out.println
                        ("quicksort with organ-pipe shaped arrays: " + quicksortRT + " ms.");
                System.out.println
                        ("quicksort3 with organ-pipe shaped arrays: " + quicksort3RT + " ms.");
                System.out.println
                        ("quicksort4 with organ-pipe shaped arrays: " + quicksort4RT + " ms.");
                System.out.println
                        ("quicksort5 with organ-pipe shaped arrays: " + quicksort5RT + " ms.");
                System.out.println
                        ("naturalmergesort with organ-pipe shaped arrays: " + naturalmergesortRT + " ms.");
            }
        }
    }*/

    /** This method compare the performances of quicksort (choosing
     * the best quicksort you have), heapsort, mergesort, natural
     * mergesort, and insertion sort on k-exchanges and k-distances
     * arrays for various k values. The method takes k = 10, 20, 30, ...,
     * until quicksort becomes a clear winner, creates 100 k-distance
     * arrays of size 5,000,000, runs in turn the chosen sorting methods on each array,
     * and collect running times. Then for k = 100, 200, 300, ...,
     * until quicksort becomes a clear winner, creates 100 k-exchange arrays of size 5,000,000,
     * runs in turn the chosen sorting methods on each array, and collects running times.
     *
     * Results: quicksort is the winner, going against the given conclusion
     */
    public static void task4() {
        long start = 0, finish = 0;
        final int REPEATTIMES = 100;
        System.out.println();
        long heapsortRT;
        long mergesortRT;
        long quicksortRT;
        long quicksort2RT;
        long quicksort3RT;
        long quicksort4RT;
        long quicksort5RT;
        long naturalmergesortRT;

        boolean quicksortBest = false;
        int kExchanges = 100;
        int kDistance = 10;

        long[] quicksortWorst = new long[5];
        long[] notBest = new long[3];
        size = 5000000;

        arr = new int[size];
        arrCopy = new int[size];
        mergeArr = new int[size];
        for (int j = 0; j < size; j++){
            arrCopy[j] = j + 1;
        }

        System.out.println("Task 4:");
        for (int k = 1; k <= 2; k++) {
            heapsortRT = 0;
            mergesortRT = 0;
            quicksortRT = 0;
            quicksort2RT = 0;
            quicksort3RT = 0;
            quicksort4RT = 0;
            quicksort5RT = 0;
            naturalmergesortRT = 0;
            quicksortBest = false;

            while (!quicksortBest) {

                if(k==1) {
                    System.out.format("k exchange: %d%n", kExchanges);
                    System.out.println(" ");
                }
                else if(k==2){
                    System.out.format("k distance: %d%n", kDistance);
                    System.out.println(" ");
                }
                for (int i = 0; i < REPEATTIMES; i++) {
                    switch (k) {
                        case 1:
                            randomGenerator = new Random();
                            for (int a = 0; a < kExchanges / 2; a++) {
                                random = size - 1;
                                exchange(randomGenerator.nextInt(random), randomGenerator.nextInt(random));
                            }
                            break;
                        case 2:
                            randomGenerator = new Random();
                            int startingPoint = (randomGenerator.nextInt(size - kDistance));
                            for (int a = 0; a < kDistance*1000 / 2; a++) {
                                exchange(startingPoint + randomGenerator.nextInt(kDistance),
                                        startingPoint + randomGenerator.nextInt(kDistance));
                            }
                            break;
                    }
                    // heap sort
                    System.arraycopy(arrCopy, 0, arr, 0, size);
                    if (size < 101) printArray("in");
                    start = System.currentTimeMillis();
                    heapsort();
                    finish = System.currentTimeMillis();
                    if (size < 101) printArray("out");
                    heapsortRT += (finish - start);
                    // merge sort
                    System.arraycopy(arrCopy, 0, arr, 0, size);
                    if (size < 101) printArray("in");
                    start = System.currentTimeMillis();
                    mergesort2(0, size);
                    finish = System.currentTimeMillis();
                    if (size < 101) printArray("out");
                    mergesortRT += (finish - start);
                    // Quick sort
                    System.arraycopy(arrCopy, 0, arr, 0, size);
                    if (size < 101) printArray("in");
                    start = System.currentTimeMillis();
                    quicksort(0, size - 1);
                    finish = System.currentTimeMillis();
                    if (size < 101) printArray("out");
                    quicksortRT += (finish - start);
                    // Quick sort 2
                    System.arraycopy(arrCopy, 0, arr, 0, size);
                    if (size < 101) printArray("in");
                    start = System.currentTimeMillis();
                    quicksort2(0, size - 1, 2);
                    finish = System.currentTimeMillis();
                    if (size < 101) printArray("out");
                    quicksort2RT += (finish - start);
                    // Quick sort 3
                    System.arraycopy(arrCopy, 0, arr, 0, size);
                    if (size < 101) printArray("in");
                    start = System.currentTimeMillis();
                    quicksort3(0, size - 1);
                    finish = System.currentTimeMillis();
                    if (size < 101) printArray("out");
                    quicksort3RT += (finish - start);
                    // Quick sort 4
                    System.arraycopy(arrCopy, 0, arr, 0, size);
                    if (size < 101) printArray("in");
                    start = System.currentTimeMillis();
                    quicksort2(0, size - 1, 4);
                    finish = System.currentTimeMillis();
                    if (size < 101) printArray("out");
                    quicksort4RT += (finish - start);
                    // Quick sort 5
                    System.arraycopy(arrCopy, 0, arr, 0, size);
                    if (size < 101) printArray("in");
                    start = System.currentTimeMillis();
                    quicksort2(0, size - 1, 5);
                    finish = System.currentTimeMillis();
                    if (size < 101) printArray("out");
                    quicksort5RT += (finish - start);
                    // Natural merge sort
                    System.arraycopy(arrCopy, 0, arr, 0, size);
                    if (size < 101) printArray("in");
                    start = System.currentTimeMillis();
                    naturalMergesort();
                    finish = System.currentTimeMillis();
                    if (size < 101) printArray("out");
                    naturalmergesortRT += (finish - start);
                }
                if(k==1) {
                    System.out.println
                            ("heapsort with k exchanges: " + heapsortRT + " ms.");
                    notBest[0] = heapsortRT;
                    System.out.println
                            ("mergesort with k exchanges: " + mergesortRT + " ms.");
                    notBest[1] = mergesortRT;
                    System.out.println
                            ("quicksort with k exchanges: " + quicksortRT + " ms.");
                    quicksortWorst[0] = quicksortRT;
                    System.out.println
                            ("quicksort2 with k exchanges: " + quicksort2RT + " ms.");
                    quicksortWorst[1] = quicksort2RT;
                    System.out.println
                            ("quicksort3 with k exchanges: " + quicksort3RT + " ms.");
                    quicksortWorst[2] = quicksort3RT;
                    System.out.println
                            ("quicksort4 with k exchanges: " + quicksort4RT + " ms.");
                    quicksortWorst[3] = quicksort4RT;
                    System.out.println
                            ("quicksort5 with k exchanges: " + quicksort5RT + " ms.");
                    quicksortWorst[4] = quicksort5RT;
                    System.out.println
                            ("naturalmergesort with k exchanges: " + naturalmergesortRT + " ms.");
                    notBest[2] = naturalmergesortRT;
                }
                else if(k==2) {
                    System.out.println
                            ("heapsort with k distance arrays: " + heapsortRT + " ms.");
                    System.out.println
                            ("mergesort with k distance: " + mergesortRT + " ms.");
                    System.out.println
                            ("quicksort with k distance: " + quicksortRT + " ms.");
                    System.out.println
                            ("quicksort2 with k distance: " + quicksort2RT + " ms.");
                    System.out.println
                            ("quicksort3 with k distance: " + quicksort3RT + " ms.");
                    System.out.println
                            ("quicksort4 with k distance: " + quicksort4RT + " ms.");
                    System.out.println
                            ("quicksort5 with k distance: " + quicksort5RT + " ms.");
                    System.out.println
                            ("naturalmergesort with k distance: " + naturalmergesortRT + " ms.");
                }
                if(k==1) {
                    kExchanges += 100;
                }
                else if(k==2){
                    kDistance += 10;
                }
                for (int i = 0; i < 5; i++) {
                    long temp = quicksortWorst[i];
                    int j = i;
                    while (j > 0 && quicksortWorst[j - 1] >= temp) {
                        quicksortWorst[j] = quicksortWorst[j - 1];
                        --j;
                    }
                    quicksortWorst[j] = temp;
                }
                for (int i = 0; i < 3; i++) {
                    long temp = notBest[i];
                    int j = i;
                    while (j > 0 && notBest[j - 1] >= temp) {
                        notBest[j] = notBest[j - 1];
                        --j;
                    }
                    notBest[j] = temp;
                }

                if(notBest[0] > quicksortWorst[4]) quicksortBest = true;
            }
        }
    }

    public static void main(String[] args) {

        read = new BufferedReader(new InputStreamReader(System.in));

        randomGenerator = new Random();

        try {
            System.out.print("Please enter the array size : ");
            size = Integer.parseInt(read.readLine());
        } catch(Exception ex){
            ex.printStackTrace();
        }

        // create array
        arr = new int[size];
        arrCopy = new int[size];
        mergeArr = new int[size];

        // fill array
        random = size*10;
        for(int i=0; i<size; i++)
            arr[i] = arrCopy[i] = randomGenerator.nextInt(random);
        demo1("random");

        // arr[0..size-1] is already sorted. We randomly swap 100 pairs to make it nearly-sorted.
        for (int i = 0; i < 100; i++) {
            int j  = randomGenerator.nextInt(size);
            int k  = randomGenerator.nextInt(size);
            exchange(j, k);
        }
        for(int i=0; i<size; i++) arrCopy[i] = arr[i];
        demo2("nearly sorted");

        for(int i=0; i<size; i++) arrCopy[i] = size-i;
        demo1("reversely sorted");

        //Project2 Tests
        task1();
        task2();
        //stack overflow error with task3() once it gets to organ-piped arrays. Commented out to show task4() functionality
        //task3();
        task4();
    }


}