public class MiniMax {

  private final static int MAX_DEPTH = 6;
  private static int currentDepth = 0;
  private static int maxBeginInt;
  private static int minBeginInt;
  public GameState gameState;

  public int miniMaxSearch(GameState gameState) {
    this.gameState =  gameState;
    return maxValue(gameState, MAX_DEPTH);
  }

  public static int maxValue(GameState gameState, int depth) {
    //OR TERMINAL!!!
    if(Game.isCutoff(gameState, currentDepth, MAX_DEPTH)){

    }
    int highestVal = Integer.MIN_VALUE;
    for (int i : Game.getAvailableActions(gameState)) {

      if (board[i] == 0) {
        continue;
      }
      highestVal = Math.max(minValue(Main.updateBoard(board, i), depth - 1), highestVal);
    }
    return highestVal;
  }

  public static int minValue(GameState gameState, int depth) {
    int lowestVal = Integer.MAX_VALUE;
    if(Game.isCutoff(gameState, currentDepth, MAX_DEPTH)){

    }

    for (int i = minBeginInt; i < minBeginInt + 6; i++) {
      if (gameState.pits[i] == 0) {
        continue;
      }
      lowestVal = Math.min(maxValue(Main.updateBoard(board, i), depth - 1), lowestVal);
    }
    return lowestVal;
  }

  public static void setBeginInts(int beginInt) {
    MiniMax.maxBeginInt = beginInt;
    if (beginInt == 0) {
      MiniMax.minBeginInt = 6;
    } else {
      MiniMax.minBeginInt = 0;
    }
  }
}
