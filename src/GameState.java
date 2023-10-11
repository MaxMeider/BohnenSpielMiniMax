import java.util.Arrays;

public class GameState {
  public int[] pits;
  public int storeLeft;
  public int storeRight;

  public GameState(){
    pits = new int[] {6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6};
    storeRight = 0;
    storeLeft = 0;
  }

  public static GameState copyState(GameState gameState) {
    GameState copy = new GameState();

    // Copy the pits array
    copy.pits = gameState.pits.clone();
    System.arraycopy(gameState.pits, 0, copy.pits, 0, gameState.pits.length);

    // Copy the storeLeft and storeRight values
    copy.storeLeft = gameState.storeLeft;
    copy.storeRight = gameState.storeRight;

    return copy;
  }

  @Override
  public String toString() {
    return "Board: " + Arrays.toString(this.pits) + ", \n" + "StoreRight: " + this.storeRight + ", \n" + "StoreLeft: " + this.storeLeft;
  }
}
