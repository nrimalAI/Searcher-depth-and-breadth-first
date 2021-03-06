package search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class RecursiveDepthFirstSearcher<T> extends Searcher<T> {
  private final List<T> states;
  private final List<T> predecessors;

  /**
   * RecursiveDepthFirstSearcher.
   * @param searchProblem : search problem
   */
  public RecursiveDepthFirstSearcher(SearchProblem<T> searchProblem) {
    super(searchProblem);
    states = new ArrayList<T>();
    predecessors = new ArrayList<T>();
  }

  @Override
  public List<T> findSolution() {
    if (solution != null) {
      return solution;
    }

    final List<T> path = new ArrayList<T>();
    recursiveDFS(searchProblem.getInitialState(), path);
    if (path.size() > 0) {
      if (!isValidSolution(path)) {
        throw new RuntimeException(
            "searcher should never find an invalid solution!");
      }
    }
    return path;
  }

  private boolean recursiveDFS(T state, List<T> path) {
    path.add(state);
    if (searchProblem.isGoal(state)) {
      return true;
    }

    visited.add(state);
    for (T neighbor : searchProblem.getSuccessors(state)) {
      if (!visited.contains(neighbor)) {
        boolean found = recursiveDFS(neighbor, path);
        if (found) {
          return true;
        }
        path.remove(neighbor);
      }
    }
    return false;
  }

  @SuppressWarnings("unused")
  private List<T> findSolutionWithExplicitPredecessors() {
    if (solution != null) {
      return solution;
    }

    final T initialState = searchProblem.getInitialState();
    // add the initial state to the list of states and predecessors
    states.add(initialState);
    // initially it is marked as its own predecessor, but this is updated
    // in the recursive helper
    predecessors.add(initialState);

    // store the goal that was found (if any) in current
    T current = recursiveDfsWithExplicitPredecessors(initialState);

    final List<T> path = new ArrayList<T>();

    // if a goal was found
    if (current != null) {
      // build a path by looking up each predecessor, starting from
      // the goal state
      path.add(current);
      while (!current.equals(searchProblem.getInitialState())) {
        final T predecessor = predecessors.get(states.indexOf(current));
        path.add(predecessor);
        current = predecessor;
      }

      // the path is in reverse order (goal-to-initial), so we reverse
      // it into the correct order
      Collections.reverse(path);
    }
    if (path.size() > 0) {
      if (!isValidSolution(path)) {
        throw new RuntimeException(
            "searcher should never find an invalid solution!");
      }
    }
    return path;
  }

  /**
   * recursiveDFSWithExplicitPredecessors.
   * @param state : state
   */
  private T recursiveDfsWithExplicitPredecessors(T state) {
    if (searchProblem.isGoal(state)) {
      return state;
    }

    visited.add(state);
    for (T neighbor : searchProblem.getSuccessors(state)) {
      if (!visited.contains(neighbor)) {

        // if this neighbor hasn't been seen before
        if (!states.contains(neighbor)) {
          // add it to the list of states
          states.add(neighbor);
          // and set its predecessor to itself
          predecessors.add(neighbor);
        }

        // now set the neighbor's predecessor correctly
        predecessors.set(states.indexOf(neighbor), state);
        final T goal = recursiveDfsWithExplicitPredecessors(neighbor);
        if (goal != null) {
          return goal;
        }
      }
    }
    return null;
  }
}
