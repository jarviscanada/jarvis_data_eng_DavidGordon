package practice.sqljava_superhero_app.java;

public class counting_coins {
  private static final int PRICE_IN_CENTS = 247;
  private static final int RECEIVED_IN_CENTS = 300;

  private static final int ONE = 1;
  private static final int SEVEN = 7;
  private static final int TWENTY_TWO = 22;

  public static void main(String[] args) {
    // First we need to get our money from the user. In this case they owe us $2.47.
    // The user gave us $3.00, meaning we owe them 53 cents.
    int change_owed = RECEIVED_IN_CENTS - PRICE_IN_CENTS;

    // Solution: Find out the minimum amount of coins we need to get 53 cents
    // The coins we are given are 1, 7, 22
    int coins = 0;

    // Intuition: Keep adding the largest coin value until we reach our target or greater
    // Basically just get changed_owed to 0

    while(change_owed >= 0) {
      change_owed -= TWENTY_TWO;
      coins++;
    }

    // Backtrack if we went too far
    if(change_owed < 0) {
      change_owed += TWENTY_TWO;
      coins--;
    }

    // Repeat with the second-largest coin value
    while(change_owed >= 0) {
      change_owed -= SEVEN;
      coins++;
    }

    // Backtrack if we went too far
    if(change_owed < 0) {
      change_owed += SEVEN;
      coins--;
    }

    // And finally our smallest coin
    while(change_owed >= 0) {
      change_owed -= ONE;
      coins++;
    }

    // Backtrack if we went too far
    if(change_owed < 0) {
      change_owed += ONE;
      coins--;
    }

    // Total time complexity: O(n)
    // Total space complexity: O(1)
    System.out.println(coins);
  }
}