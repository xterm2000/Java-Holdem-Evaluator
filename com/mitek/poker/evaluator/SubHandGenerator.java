package com.mitek.poker.evaluator;

import java.util.ArrayList;
import java.util.List;

class SubHandGenerator {

  /**
   * generates subsets of 0,1,2,3,...,n of size r
   * 
   * @param n - size of the big set
   * @param r - size of the subsets
   * @return - List of int arrays (subsets)
   */
  public static List<int[]> generate(int n, int r) {

    List<int[]> combinations = new ArrayList<>();
    helper(combinations, new int[r], 0, n - 1, 0);
    return combinations;
  }

  private static void helper(
      List<int[]> combinations,
      int data[],
      int start,
      int end,
      int index) {

    if (index == data.length) {

      int[] combination = data.clone();
      combinations.add(combination);

    } else if (start <= end) {

      data[index] = start;
      helper(combinations, data, start + 1, end, index + 1);
      helper(combinations, data, start + 1, end, index);
    }
  }

}
