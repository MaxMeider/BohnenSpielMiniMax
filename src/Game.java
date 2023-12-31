import java.util.LinkedList;

public class Game {

    private static int maxBeginInt;
    private static int minBeginInt;

    public static LinkedList<Integer> getAvailableActions(GameState gameState, boolean isMax) {
        LinkedList<Integer> possibleMoves = new LinkedList<>();
        if (isMax) {
            for (int i = maxBeginInt; i < maxBeginInt + 6; i++) {
                if (gameState.pits[i] != 0) {
                    possibleMoves.add(i);
                }

            }
        } else {
            for (int i = minBeginInt; i < minBeginInt + 6; i++) {
                if (gameState.pits[i] != 0) {
                    possibleMoves.add(i);
                }
            }
        }
        return possibleMoves;
    }


    public static int getEvaluation(GameState gameState) {
        if (maxBeginInt == 6)
            return gameState.storeLeft - gameState.storeRight;
        else
            return gameState.storeRight - gameState.storeLeft;
    }

    public static boolean isTerminal(GameState gameState) {
        for (int i = 0; i < gameState.pits.length - 1; i++) {
            if (gameState.pits[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public static void setBeginInts(int beginInt) {
        Game.maxBeginInt = beginInt;
        if (beginInt == 0) {
            Game.minBeginInt = 6;
        } else {
            Game.minBeginInt = 0;
        }
    }

}
