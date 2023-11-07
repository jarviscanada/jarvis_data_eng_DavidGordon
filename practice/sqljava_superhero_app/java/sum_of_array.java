package practice.sqljava_superhero_app.java;

import java.io.IOError;
import java.io.IOException;
import java.util.Scanner;

public class sum_of_array {
  private static final int ARRAY_LENGTH = 100;

    public static void solutionOne(int[] arr) {
    int points = 0;
    Scanner scanner = new Scanner(System.in);

    while(points <= 10) {
      System.out.print("Enter target number: ");
      int target = scanner.nextInt();

      /*
       * Search the array to try and find n
       * The input array starts out sorted, but will get unsorted as the game goes on,
       * so binary search is out the window.
       * Linear search: O(n)
       */
      for (int i = 0; i < arr.length; i++) {
        // TRUE: If we find it, swap the first and last elements
        if (arr[i] == target) {
          int temp = arr[0];
          arr[0] = arr[ARRAY_LENGTH - 1];
          arr[ARRAY_LENGTH - 1] = temp;
        }
        // FALSE: Last element becomes n
        else {
          arr[ARRAY_LENGTH - 1] = target;
        }
      }

      // Calculate the new sum of the array
      // Linear search: O(n)
      int sum = 0;
      for (int i : arr) {
        sum += i;
      }

      System.out.println("Array sum: " + sum);
      System.out.print("Guess: ");

      int guess = scanner.nextInt();
      if (guess == sum) {
        points++;
        System.out.println("Correct. You have " + points + " points!");
      } else {
        System.out.println("False");
      }

    }


    scanner.close();
    System.out.println("You win");
    // Total time complexity: O(n)
    // Total space complexity: O(1)
  }

  public static void solutionTwo(int[] arr) {
    System.out.print("Enter target number: ");
    Scanner scanner = new Scanner(System.in);
    int target = scanner.nextInt();
    boolean firstRound = true;

    // First iteration of array is sorted, so we can use binary search
    if(firstRound) {
      firstRound = false;
      int left = 0;
      int right = arr.length-1;

      while(left <= right) {
        int middle = left + right / left;

        if(arr[middle] < target) left++;
        if(arr[middle] > target) right--;
        else if(arr[middle] == target) {
          // Swap first and last elements
          int temp = arr[0];
          arr[0] = arr[ARRAY_LENGTH-1];
          arr[ARRAY_LENGTH-1] = temp;
          break;
        }

        // If we didn't find it in the array, set the last element to n
        arr[ARRAY_LENGTH-1] = target;
      }
    }

    /*
     * Search the array to try and find n
     * The input array starts out sorted, but will get unsorted as the game goes on,
     * so binary search is out the window.
     * Linear search: O(n)
     */
    for (int i = 0; i < arr.length; i++) {
      // TRUE: If we find it, swap the first and last elements
      if (arr[i] == target) {
        int temp = arr[0];
        arr[0] = arr[ARRAY_LENGTH - 1];
        arr[ARRAY_LENGTH - 1] = temp;
      }
      // FALSE: Last element becomes n
      else {
        arr[ARRAY_LENGTH - 1] = target;
      }
    }

    // Calculate the new sum of the array
    // Linear search: O(n)
    int sum = 0;
    for (int i : arr) {
      sum += i;
    }

    System.out.println("Array sum: " + sum);
    System.out.print("Guess: ");

    int guess = scanner.nextInt();
    if (guess == sum) {
      System.out.println("Correct");
    } else {
      System.out.println("False");
    }

    scanner.close();

    // First round time complexity: O(log(n))
    // Total time complexity: O(n)
    // Total space complexity: O(1)
  }

  public static void main(String[] args) {
    // Create and populate the array
    int[] arr = new int[ARRAY_LENGTH];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = i;
    }

    solutionOne(arr.clone());
  }
}
