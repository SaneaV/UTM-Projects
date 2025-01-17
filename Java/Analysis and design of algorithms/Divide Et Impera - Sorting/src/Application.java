import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Application {

  private static final Scanner SCANNER = new Scanner(System.in);
  private static final Random RANDOM = new Random();
  private static int[] ARRAY;
  private static int SIZE;
  private static int COMPLEXITY = 0;

  public static void main(String[] args) {
    boolean stop = false;
    int choice;

    while (!stop) {
      System.out.println("Menu");
      System.out.println("1. Create happy occasion array");
      System.out.println("2. Create unhappy occasion array");
      System.out.println("3. Create medium occasion array");
      System.out.println("4. Print array");
      System.out.println("5. Bubble sort");
      System.out.println("6. Quick sort");
      System.out.println("7. Merge sort");
      System.out.println("8. Exit");

      System.out.print("Input your choice: ");
      choice = SCANNER.nextInt();

      switch (choice) {
        case 1: {
          System.out.println("\n\n");
          System.out.println("You have chosen: Create happy occasion array");

          inputSize();
          happyOccasionGraph();
          break;
        }
        case 2: {
          System.out.println("\n\n");
          System.out.println("You have chosen: Create unhappy occasion array");

          inputSize();
          unhappyOccasionGraph();
          break;
        }
        case 3: {
          System.out.println("\n\n");
          System.out.println("You have chosen: Create medium occasion array");

          inputSize();
          mediumOccasionGraph();
          break;
        }
        case 4: {
          System.out.println("\n\n");
          System.out.println("Print array");

          printArray();
          break;
        }
        case 5: {
          System.out.println("\n\n");
          System.out.println("You have chosen: Bubble sort");

          resetComplexity();
          int[] sortedArray = bubbleSort();
          printArray(sortedArray);
          showComplexity();
          break;
        }
        case 6: {
          System.out.println("\n\n");
          System.out.println("You have chosen: Quick sort");

          resetComplexity();
          int[] tempArray = Arrays.copyOf(ARRAY, SIZE);
          quickSort(tempArray, 0, SIZE - 1);
          printArray(tempArray);
          showComplexity();
          break;
        }
        case 7: {
          System.out.println("\n\n");
          System.out.println("You have chosen: Merge sort");

          resetComplexity();
          int[] tempArray = Arrays.copyOf(ARRAY, SIZE);
          mergeSort(tempArray, 0, SIZE - 1);
          printArray(tempArray);
          showComplexity();
          break;
        }
        case 8: {
          stop = true;
        }
      }
      System.out.println("\n\n");
    }
    System.out.println("The program has completed its execution.");
  }

  private static void happyOccasionGraph() {
    ARRAY = new int[SIZE];
    for (int i = 0; i < SIZE; i++) {
      ARRAY[i] = i;
    }
  }

  private static void unhappyOccasionGraph() {
    ARRAY = new int[SIZE];
    for (int i = SIZE - 1, j = 0; i >= 0; i--, j++) {
      ARRAY[j] = i;
    }
  }

  private static void mediumOccasionGraph() {
    ARRAY = new int[SIZE];
    for (int i = 0; i < SIZE; i++) {
      ARRAY[i] = RANDOM.nextInt(SIZE);
    }
  }

  private static void inputSize() {
    System.out.print("Input array size: ");
    SIZE = SCANNER.nextInt();
  }

  private static void printArray() {
    printArray(ARRAY);
  }

  private static void printArray(int[] array) {
    for (int i = 0; i < SIZE; i++) {
      System.out.print(array[i] + " ");
    }
    System.out.println();
  }

  public static int[] bubbleSort() {
    int[] tempArray = Arrays.copyOf(ARRAY, SIZE);

    increaseComplexity(3);
    for (int i = 0; i < SIZE - 1; i++) {
      increaseComplexity(4);
      for (int j = 0; j < SIZE - i - 1; j++) {
        increaseComplexity(7);
        if (tempArray[j] > tempArray[j + 1]) {
          increaseComplexity(5);
          int temp = tempArray[j];
          tempArray[j] = tempArray[j + 1];
          tempArray[j + 1] = temp;
        }
      }
    }
    return tempArray;
  }

  public static void quickSort(int[] arr, int low, int high) {
    increaseComplexity();
    if (low < high) {
      int pi = partition(arr, low, high);

      increaseComplexity(2);
      quickSort(arr, low, pi - 1);
      quickSort(arr, pi + 1, high);
    }
  }

  private static int partition(int[] arr, int low, int high) {
    increaseComplexity();
    int pivot = arr[high];
    increaseComplexity(2);
    int i = low - 1;

    increaseComplexity(2);
    for (int j = low; j < high; j++) {
      increaseComplexity(3);
      if (arr[j] <= pivot) {
        increaseComplexity(4);
        i++;
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
      }
    }

    increaseComplexity(6);
    int temp = arr[i + 1];
    arr[i + 1] = arr[high];
    arr[high] = temp;
    return i + 1;
  }

  static void merge(int[] arr, int l, int m, int r) {
    increaseComplexity(5);
    int n1 = m - l + 1;
    int n2 = r - m;

    int[] L = new int[n1];
    int[] R = new int[n2];

    increaseComplexity(2);
    for (int i = 0; i < n1; ++i) {
      increaseComplexity(3);
      L[i] = arr[l + i];
    }

    increaseComplexity(2);
    for (int j = 0; j < n2; ++j) {
      increaseComplexity(4);
      R[j] = arr[m + 1 + j];
    }

    increaseComplexity(2);
    int i = 0, j = 0;

    increaseComplexity(3);
    int k = l;
    while (i < n1 && j < n2) {
      increaseComplexity(1);
      if (L[i] <= R[j]) {
        increaseComplexity(2);
        arr[k] = L[i];
        i++;
      } else {
        increaseComplexity(2);
        arr[k] = R[j];
        j++;
      }
      increaseComplexity();
      k++;
    }

    increaseComplexity();
    while (i < n1) {
      increaseComplexity(3);
      arr[k] = L[i];
      i++;
      k++;
    }

    increaseComplexity();
    while (j < n2) {
      increaseComplexity(3);
      arr[k] = R[j];
      j++;
      k++;
    }
  }

  static void mergeSort(int[] arr, int l, int r) {
    increaseComplexity();
    if (l < r) {
      increaseComplexity(4);
      int m = l + (r - l) / 2;

      increaseComplexity();
      mergeSort(arr, l, m);
      mergeSort(arr, m + 1, r);

      merge(arr, l, m, r);
    }
  }

  static void resetComplexity() {
    COMPLEXITY = 0;
  }

  static void increaseComplexity() {
    increaseComplexity(1);
  }

  static void increaseComplexity(int complexity) {
    COMPLEXITY += complexity;
  }

  static void showComplexity() {
    System.out.println("\nComplexity of this algorithm is: " + COMPLEXITY);
  }
}
