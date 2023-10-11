import java.util.LinkedList;

public class Game {

  public static void toMove(GameState gameState){

  }

  public static LinkedList<Integer> getAvailableActions(GameState gameState){
    LinkedList<Integer> possibleMoves = new LinkedList<>();
    return possibleMoves;
  }

  public static boolean isCutoff(GameState gameState, int depth, int maxDepth) {
    return false;
  }

  public static boolean getEvaluation(GameState gameState, boolean isMax) {
    return false;
  }

}
