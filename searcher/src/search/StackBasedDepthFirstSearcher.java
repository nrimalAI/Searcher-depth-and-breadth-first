package search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * An implementation of a Searcher that performs an iterative search, storing
 * the list of next states in a Stack. This results in a depth-first search.
 * 
 */
public class StackBasedDepthFirstSearcher<T> extends Searcher<T> {

  /**
   * StackBasedDepthFirstSearcher.
   * 
   * @param searchProblem : search problem
   */
  public StackBasedDepthFirstSearcher(SearchProblem<T> searchProblem) {
    super(searchProblem);
  }

  @Override
  public List<T> findSolution() {
    List<T> fin = new ArrayList<T>();
    Stack<T> nfin = new Stack<T>();
    nfin.push(searchProblem.getInitialState());
    while (!nfin.isEmpty()) {
      T curr = nfin.peek();
      visited.add(curr);
      if (searchProblem.isGoal(curr)) {
        while (!nfin.isEmpty()) {
          fin.add(0, nfin.pop());
        }
        return fin;
      }
      List<T> next = searchProblem.getSuccessors(curr);
      if (next.isEmpty())
        nfin.pop();
      else {
        int counter = 0;// To check how many ".getSuccessors" we have visited

        for (int i = 0; i < next.size(); i++) {
          if (!visited.contains(next.get(i))) {
            nfin.push(next.get(i));
            break;
          } else
            counter++;
        }
        if (counter == next.size())
          nfin.pop();
      }
    }
    while (!nfin.isEmpty()) {
      fin.add(0, nfin.pop());
    }
    return fin;
  }
}
/*
 * Stack<T> sstack = new Stack<T>();
 * sstack.push(searchProblem.getInitialState()); while (!sstack.isEmpty()) { T
 * hold = sstack.peek(); if(!visited.contains(hold)) { solution.add(hold);
 * visited.add(hold);
 * 
 * } List<T> next = searchProblem.getSuccessors(hold); for (int i = 0; i <
 * next.size(); i++) { if (!visited.contains(next.get(i))) {
 * sstack.push(next.get(i)); } } sstack.pop(); } return solution;
 */