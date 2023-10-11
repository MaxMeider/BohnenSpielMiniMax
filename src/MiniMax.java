public class MiniMax {

    private final static int MAX_DEPTH = 6;

    public static int miniMaxSearch(GameState gameState) {
        return maxValue(gameState, MAX_DEPTH)[1];
    }

    public static int[] maxValue(GameState gameState, int depth) {
        System.out.println(depth);
        if (depth == 0 || Game.isTerminal(gameState)) {
            return new int[]{Game.getEvaluation(gameState, true), 0};
        }
        int highestVal = Integer.MIN_VALUE;
        int move = 0;
        for (int i : Game.getAvailableActions(gameState, true)) {
            System.out.println("maxValue Available:" + Game.getAvailableActions(gameState, true).toString());
            GameState tempState = GameState.copyState(gameState);
            int[] maxVal = minValue(Main.updateBoard(tempState, i), depth - 1);
            if (maxVal[0] > highestVal){
                highestVal = maxVal[0];
                move = i;
            }
        }
        return new int[]{highestVal, move};
    }

    public static int[] minValue(GameState gameState, int depth) {
        System.out.println(depth);
        if (depth == 0 || Game.isTerminal(gameState)) {
            return new int[]{Game.getEvaluation(gameState, false), 0};
        }
        int lowestVal = Integer.MAX_VALUE;
        int move = 0;
        for (int i : Game.getAvailableActions(gameState, false)) {
            System.out.println("minValue Available:" +Game.getAvailableActions(gameState, false).toString());
            GameState tempState = GameState.copyState(gameState);
            int[] minVal = maxValue(Main.updateBoard(tempState, i), depth - 1);
            if (minVal[0] < lowestVal){
                lowestVal = minVal[0];
                move = i;
            }
        }
        return new int[]{lowestVal, move};
    }
}
