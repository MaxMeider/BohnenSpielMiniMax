import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

public class Main {

  // static String server = "http://127.0.0.1:5000";
  static String server = "http://bohnenspiel.informatik.uni-mannheim.de";
  static String name = "minimaxi";
  static GameState gameState = new GameState();


  public static void main(String[] args) throws Exception {
    // System.out.println(load(server));
    createGame();
    // openGames();
    // joinGame("0");
  }


  static void createGame() throws Exception {
    String url = server + "/api/creategame/" + name;
    String gameID = load(url);
    System.out.println("Spiel erstellt. ID: " + gameID);

    url = server + "/api/check/" + gameID + "/" + name;
    while (true) {
      Thread.sleep(3000);
      String state = load(url);
      System.out.print("." + " (" + state + ")");
      if (state.equals("0") || state.equals("-1")) {
        break;
      } else if (state.equals("-2")) {
        System.out.println("time out");
        return;
      }
    }
    Game.setBeginInts(0);
    play(gameID, 0);
  }


  static void openGames() throws Exception {
    String url = server + "/api/opengames";
    String[] opengames = load(url).split(";");
    for (int i = 0; i < opengames.length; i++) {
      System.out.println(opengames[i]);
    }
  }


  static void joinGame(String gameID) throws Exception {
    String url = server + "/api/joingame/" + gameID + "/" + name;
    String state = load(url);
    System.out.println("Join-Game-State: " + state);
    if (state.equals("1")) {
      Game.setBeginInts(6);
      play(gameID, 6);
    } else if (state.equals("0")) {
      System.out.println("error (join game)");
    }
  }


  static void play(String gameID, int offset) throws Exception {
    String checkURL = server + "/api/check/" + gameID + "/" + name;
    String statesMsgURL = server + "/api/statemsg/" + gameID;
    String stateIdURL = server + "/api/state/" + gameID;
    int start, end;
    if (offset == 0) {
      start = 7;
      end = 12;
    } else {
      start = 1;
      end = 6;
    }

    while (true) {
      Thread.sleep(1000);
      int moveState = Integer.parseInt(load(checkURL));
      int stateID = Integer.parseInt(load(stateIdURL));
      if (stateID != 2 && ((start <= moveState && moveState <= end) || moveState == -1)) {
        if (moveState != -1) {
          int selectedField = moveState - 1;
          updateBoard(gameState, selectedField);
          System.out.println("Gegner waehlte: " + moveState + " /\t" + gameState.storeRight + " -"
              + " " + gameState.storeLeft);
          System.out.println(printBoard(gameState.pits) + "\n");
        }
        // calculate fieldID
        int selectField = MiniMax.miniMaxSearch(gameState);

        // System.out.println("Finde Zahl: ");


        updateBoard(gameState, selectField);
        System.out.println("Waehle Feld: " + (selectField + 1) + " /\t" + gameState.storeRight +
            " - " + gameState.storeLeft);
        System.out.println(printBoard(gameState.pits) + "\n\n");

        move(gameID, selectField + 1);
      } else if (moveState == -2 || stateID == 2) {
        System.out.println("GAME Finished");
        checkURL = server + "/api/statemsg/" + gameID;
        System.out.println(load(checkURL));
        return;
      } else {
        System.out.println("- " + moveState + "\t\t" + load(statesMsgURL));
      }

    }
  }


  static GameState updateBoard(GameState gameState, int field) {
    System.out.println(gameState.toString());
    int startField = field;

    int value = gameState.pits[field];
    gameState.pits[field] = 0;
    while (value > 0) {
      field = (++field) % 12;
      gameState.pits[field]++;
      value--;
    }

    if (gameState.pits[field] == 2 || gameState.pits[field] == 4 || gameState.pits[field] == 6) {
      do {
        if (startField < 6) {
          gameState.storeRight += gameState.pits[field];
        } else {
          gameState.storeLeft += gameState.pits[field];
        }
        gameState.pits[field] = 0;
        field = (field == 0) ? field = 11 : --field;
      } while (gameState.pits[field] == 2 || gameState.pits[field] == 4 || gameState.pits[field] == 6);
    }
    return gameState;
  }


  static String printBoard(int[] board) {
    String s = "";
    for (int i = 11; i >= 6; i--) {
      if (i != 6) {
        s += board[i] + "; ";
      } else {
        s += board[i];
      }
    }

    s += "\n";
    for (int i = 0; i <= 5; i++) {
      if (i != 5) {
        s += board[i] + "; ";
      } else {
        s += board[i];
      }
    }

    return s;
  }


  static void move(String gameID, int fieldID) throws Exception {
    String url = server + "/api/move/" + gameID + "/" + name + "/" + fieldID;
    System.out.println(load(url));
  }


  static String load(String url) throws Exception {
    URI uri = new URI(url.replace(" ", ""));
    BufferedReader bufferedReader = new BufferedReader(
        new InputStreamReader(uri.toURL().openConnection().getInputStream()));
    StringBuilder sb = new StringBuilder();
    String line = null;
    while ((line = bufferedReader.readLine()) != null) {
      sb.append(line);
    }
    bufferedReader.close();
    return (sb.toString());
  }
}
