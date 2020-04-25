package search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * An implementation of a Searcher that performs an iterative search, storing
 * the list of next states in a Queue. This results in a breadth-first search.
 * 
 */
public class QueueBasedBreadthFirstSearcher<T> extends Searcher<T> {

  /**
   * QueueBasedBreadthFirstSearcher.
   * 
   * @param searchProblem : search problem
   */
  public QueueBasedBreadthFirstSearcher(SearchProblem<T> searchProblem) {
    super(searchProblem);
  }

  @Override
  public List<T> findSolution() {
    List<T> answer = new ArrayList<T>();
    Queue<T> queue = new LinkedList<T>();
    queue.add(searchProblem.getInitialState());
    while (!queue.isEmpty()) {
      T hold = queue.remove();
      answer.add(hold);
      visited.add(hold);
      if (searchProblem.isGoal(hold)) {
        while (queue.isEmpty() == false) {
          answer.add(0, queue.remove());
        }
        return answer;
      }

      List<T> after = searchProblem.getSuccessors(hold);
      if (after.isEmpty() != false) {
        queue.remove();
      } else {
        int count = 0;
        for (int i = 0; i < after.size(); i++) {
          if (!visited.contains(after.get(i))) {
            queue.add(after.get(i));
            break;
          } else {
            count++;
          }
        }
        if (count == after.size()) {
          queue.remove();
        }
      }
    }
    while (queue.isEmpty() == false) {
      answer.add(0, queue.remove());
    }
    return answer;
  }
}