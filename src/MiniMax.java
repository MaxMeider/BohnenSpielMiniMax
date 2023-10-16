public class MiniMax {

    private final static int MAX_DEPTH = 8;

    public static int miniMaxSearch(GameState gameState) {
        return maxValue(gameState, MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE)[1];
    }

    public static int[] maxValue(GameState gameState, int depth, int alpha, int beta) {
        if (depth == 0) {
            return new int[]{Game.getEvaluation(gameState, true), 0};
        }
        int highestVal = Integer.MIN_VALUE;
        int move = 0;
        for (int i : Game.getAvailableActions(gameState, true)) {
            GameState tempState = GameState.copyState(gameState);
            int[] maxVal = minValue(Main.updateBoard(tempState, i), depth - 1, alpha, beta);
            if (maxVal[0] > highestVal) {
                highestVal = maxVal[0];
                move = i;
                alpha = Math.max(alpha, highestVal);
            }
            if (highestVal >= beta) {
                return new int[]{highestVal, i};
            }
        }
        return new int[]{highestVal, move};
    }

    public static int[] minValue(GameState gameState, int depth, int alpha, int beta) {
        if (depth == 0) {
            return new int[]{Game.getEvaluation(gameState, false), 0};
        }
        int lowestVal = Integer.MAX_VALUE;
        int move = 0;
        for (int i : Game.getAvailableActions(gameState, false)) {
            GameState tempState = GameState.copyState(gameState);
            int[] minVal = maxValue(Main.updateBoard(tempState, i), depth - 1, alpha, beta);
            if (minVal[0] < lowestVal) {
                lowestVal = minVal[0];
                move = i;
                beta = Math.min(beta, lowestVal);
            }
            if (lowestVal <= alpha) {
                return new int[]{lowestVal, i};
            }
        }
        return new int[]{lowestVal, move};
    }
}
