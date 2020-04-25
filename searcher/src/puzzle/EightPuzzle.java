package puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import search.SearchProblem;
import search.Solver;

/**
 * A class to represent an instance of the eight-puzzle. The spaces in an
 * 8-puzzle are indexed as follows: 0 | 1 | 2 --+---+--- 3 | 4 | 5 --+---+--- 6
 * | 7 | 8 The puzzle contains the eight numbers 1-8, and an empty space. If we
 * represent the empty space as 0, then the puzzle is solved when the values in
 * the puzzle are as follows: 1 | 2 | 3 --+---+--- 4 | 5 | 6 --+---+--- 7 | 8 |
 * 0 That is, when the space at index 0 contains value 1, the space at index 1
 * contains value 2, and so on. From any given state, you can swap the empty
 * space with a space adjacent to it (that is, above, below, left, or right of
 * it, without wrapping around). For example, if the empty space is at index 2,
 * you may swap it with the value at index 1 or 5, but not any other index. Only
 * half of all possible puzzle states are solvable!
 * 
 */
public class EightPuzzle implements SearchProblem<List<Integer>> {

  /**
   * Creates a new instance of the 8 puzzle with the given starting values. The
   * values are indexed as described above, and should contain exactly the nine
   * integers from 0 to 8.
   * 
   * @param startingValues the starting values, 0 -- 8
   * @throws IllegalArgumentException if startingValues is invalid
   */
  private List<Integer> full;

  private List<Integer> game;

  public EightPuzzle(List<Integer> startingValues) {
    if (startingValues.size() != 9 || startingValues.isEmpty() || startingValues == null)
      throw new IllegalArgumentException();
    for (int i = 0; i < 9; i++) {
      if (!startingValues.contains(i))
        throw new IllegalArgumentException();
    }

    full = startingValues;
    game = Arrays.asList(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 0 });
  }

  @Override
  public List<Integer> getInitialState() {
    return full;
  }

  @Override
  public List<List<Integer>> getSuccessors(List<Integer> currentState) {
    List<List<Integer>> successes = new ArrayList<List<Integer>>();
    List<Integer> first = new ArrayList<Integer>();
    List<Integer> second = new ArrayList<Integer>();
    List<Integer> third = new ArrayList<Integer>();
    List<Integer> fourth = new ArrayList<Integer>();
    final int zeroIndex = currentState.indexOf(0);// Does not change

    for (int i = 0; i < 9; i++) {
      first.add(currentState.get(i));
      second.add(currentState.get(i));// i+1
      third.add(currentState.get(i));
      fourth.add(currentState.get(i));
    }

    int left = zeroIndex - 1;
    int right = zeroIndex + 1;
    int down = zeroIndex + 3;
    int up = zeroIndex - 3;

    if (zeroIndex == 5 || zeroIndex == 2 || zeroIndex == 8)
      right = -1;
    if (zeroIndex == 6 || zeroIndex == 3)
      left = -1;

    if (compareLessGreat(left, 0, 7)) {
      first.set(zeroIndex, first.get(left));
      first.set(left, 0);
      successes.add(first);
    }
    if (compareLessGreat(right, 1, 8)) {
      second.set(zeroIndex, second.get(right));
      second.set(right, 0);
      successes.add(second);
    }
    if (compareLessGreat(up, 0, 5)) {
      third.set(zeroIndex, third.get(up));
      third.set(up, 0);
      successes.add(third);
    }
    if (compareLessGreat(down, 3, 8)) {
      fourth.set(zeroIndex, fourth.get(down));
      fourth.set(down, 0);
      successes.add(fourth);
    }
    return successes;
  }

  private boolean compareLessGreat(int var, int check1, int check2) {
    return (var >= check1 && var <= check2);
  }

  @Override
  public boolean isGoal(List<Integer> state) {
    if (state.size() != 9)
      return false;
    for (int i = 0; i < state.size(); i++) {
      if (state.get(i) != game.get(i))
        return false;
    }
    return true;
  }

  /**
   * supporting main method.
   */
  public static void main(String[] args) {
    EightPuzzle e = new EightPuzzle(Arrays.asList(new Integer[] { 1, 2, 3, 4, 0, 6, 7, 5, 8 }));

    List<List<Integer>> r = new Solver<List<Integer>>(e).solveWithBFS();
    for (List<Integer> l : r) {
      System.out.println(l);
    }
  }
}
