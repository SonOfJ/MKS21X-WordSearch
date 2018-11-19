import java.util.*;
import java.io.*;
public class WordSearch{
  private char[][]data;
  private int seed;
  private Random randgen;
  private ArrayList<String>wordsToAdd;
  private ArrayList<String>wordsAdded;
  public WordSearch(int rows, int cols, String filename) {
    if (rows < 1 || cols < 1) {
      throw new IllegalArgumentException();
    }
    wordsToAdd = new ArrayList<String>();
    wordsAdded = new ArrayList<String>();
    try {
      Scanner reader = new Scanner(new File(filename));
      while (reader.hasNext()) {
        wordsToAdd.add(reader.next().toUpperCase());
      }
    } catch (FileNotFoundException e) {
      System.out.println(filename + "does not exist.");
      System.exit(1);
    }
    data = new char[rows][cols];
    clear();
    randgen = new Random();
    seed = randgen.nextInt(10001);
    randgen = new Random(seed);
    addAllWords();
    fill();
  }
  public WordSearch(int rows, int cols, String filename, int randSeed, Boolean key) {
    if (rows < 1 || cols < 1) {
      throw new IllegalArgumentException();
    }
    wordsToAdd = new ArrayList<String>();
    wordsAdded = new ArrayList<String>();
    try {
      Scanner reader = new Scanner(new File(filename));
      while (reader.hasNext()) {
        wordsToAdd.add(reader.next().toUpperCase());
      }
    } catch (FileNotFoundException e) {
      System.out.println(filename + "does not exist.");
      System.exit(1);
    }
    data = new char[rows][cols];
    clear();
    seed = randSeed;
    randgen = new Random(seed);
    addAllWords();
    if (key == false) {
      fill();
    }
  }
  private void clear(){
    for(int i = 0; i < data.length; i = i + 1) {
      for(int j = 0; j < data[i].length; j = j + 1) {
        data[i][j] = ' ';
      }
    }
  }
  private void fill() {
    String alpha = "QWERTYUIOPASDFGHJKLZXCVBNM";
    for(int i = 0; i < data.length; i = i + 1) {
      for(int j = 0; j < data[0].length; j = j + 1) {
        if (data[i][j] == ' ') {
          data[i][j] = alpha.charAt(randgen.nextInt(26));
        }
      }
    }
  }
  public String toString(){
    String board = "";
    for(int i = 0; i < data.length; i = i + 1) {
      board = board + "|";
      for(int j = 0; j < data[i].length; j = j + 1) {
        if (j == data[i].length - 1) {
          board = board + data[i][j];
          board = board + "|\n";
        } else {
          board = board + data[i][j] + " ";
        }
      }
    }
    board = board + "Words: ";
    for(int i = 0; i < wordsAdded.size(); i = i + 1) {
      board = board + wordsAdded.get(i);
      if (i + 1 != wordsAdded.size()) {
        board = board + ", ";
      }
    }
    board = board + " (seed: " + seed + ")";
    return board;
  }
  private boolean addWord( String word, int r, int c, int rowIncrement, int colIncrement) {
    if (rowIncrement == 0 && colIncrement == 0) {
      return false;
    }
    if (rowIncrement == -1 && word.length() > r + 1) {
      return false;
    }
    if (rowIncrement == 1 && word.length() > data.length - r) {
      return false;
    }
    if (colIncrement == -1 && word.length() > c + 1) {
      return false;
    }
    if (colIncrement == 1 && word.length() > data[0].length - c) {
      return false;
    }
    for(int i = 0; i < word.length(); i = i + 1) {
      if (data[r + i * rowIncrement][c + i * colIncrement] != word.charAt(i) && data[r + i * rowIncrement][c + i * colIncrement] != ' ') {
        return false;
      }
    }
    for(int i = 0; i < word.length(); i = i + 1) {
      data[r + i * rowIncrement][c + i * colIncrement] = word.charAt(i);
    }
    wordsAdded.add(word);
    wordsToAdd.remove(word);
    return true;
  }
  private void addAllWords() {
    for(int i = 0; wordsToAdd.size() > 0 && i < 1000; i = i + 1) {
      String word = wordsToAdd.get(randgen.nextInt(wordsToAdd.size()));
      int rowIncrement = 2;
      int colIncrement = 2;
      while (rowIncrement < -1 || rowIncrement > 1) {
        rowIncrement = randgen.nextInt() % 2;
      }
      while (colIncrement < -1 || colIncrement > 1) {
        colIncrement = randgen.nextInt() % 2;
      }
      for(int j = 0; j < 1000; j = j + 1) {
        if (addWord(word, randgen.nextInt(data.length), randgen.nextInt(data[0].length), rowIncrement, colIncrement)) {
          j = 1000;
        }
      }
    }
  }
  public static void main(String[] args) {
    WordSearch DragonBallZ;
    try {
      if (args.length < 3 || args.length > 5) {
        System.out.println("You need 3 to 5 arguments in the following order.");
        System.out.println("Argument 1: Number of rows in the board.");
        System.out.println("Argument 2: Number of columns in the board.");
        System.out.println("Argument 3: Name of word file.");
        System.out.println("Argument 4: Optional seed to generate a previous board.");
        System.out.println("Argument 5: Type in \"key\" to print only the answers.");
      } else if (args.length == 3) {
        if (Integer.parseInt(args[0]) < 1 || Integer.parseInt(args[1]) < 1) {
          throw new IllegalArgumentException("Argument 1 and Argument 2 must be greater than 0.");
        }
        Scanner reader = new Scanner(new File(args[2]));
        DragonBallZ = new WordSearch(Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2]);
        System.out.println(DragonBallZ);
      } else if (args.length == 4 || args.length == 5 && !args[4].equals("key")) {
        if (Integer.parseInt(args[0]) < 1 || Integer.parseInt(args[1]) < 1) {
          throw new IllegalArgumentException("Argument 1 and Argument 2 must be greater than 0.");
        }
        if (Integer.parseInt(args[3]) < 0 || Integer.parseInt(args[3]) > 10000) {
          throw new IllegalArgumentException("Argument 4 has to be between 0 and 10000 inclusive.");
        }
        Scanner reader = new Scanner(new File(args[2]));
        DragonBallZ = new WordSearch(Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2], Integer.parseInt(args[3]), false);
        System.out.println(DragonBallZ);
      } else if (args[4].equals("key")) {
        if (Integer.parseInt(args[0]) < 1 || Integer.parseInt(args[1]) < 1) {
          throw new IllegalArgumentException("Argument 1 and Argument 2 must be greater than 0.");
        }
        if (Integer.parseInt(args[3]) < 0 || Integer.parseInt(args[3]) > 10000) {
          throw new IllegalArgumentException("Argument 4 has to be between 0 and 10000 inclusive.");
        }
        Scanner reader = new Scanner(new File(args[2]));
        DragonBallZ = new WordSearch(Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2], Integer.parseInt(args[3]), true);
        System.out.println(DragonBallZ);
      }
    } catch (FileNotFoundException e) {
      System.out.println("File " + args[2] + " does not exist.");
    } catch (NumberFormatException e) {
      System.out.println("Please input integers for Argument 1, Argument 2, and Argument 4.");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }
}
