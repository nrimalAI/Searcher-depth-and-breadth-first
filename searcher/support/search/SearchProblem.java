package search;

import java.util.List;

/**

 * @param <T>
 *            the type of each state in the search problem
 */
public interface SearchProblem<T> {
  /** getInitialState.
   * @return the initial (starting) state of the search problem
   */
  T getInitialState();

  /**
   * Returns the list of successors (that is, reachable neighbors) from this
   * state.
   * The list will be empty if there are no successors (though this behavior
   * will only appear in degenerate search problems).
   * 
   * @param currentState : current state
   * @return the list of successors of currentState
   */
  List<T> getSuccessors(T currentState);

  /** isGoal.
   * @param state : state
   * @return true iff state is a goal state for this problem
   */
  boolean isGoal(T state);
}
