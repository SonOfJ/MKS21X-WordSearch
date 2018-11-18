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
    seed = randgen.nextInt();
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
    if (!key) {
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
    int j = 0;
    if (word.length() == 0 ||
    r < 0 ||
    c < 0 ||
    rowIncrement == 0 && colIncrement == 0 ||
    r >= data.length ||
    c >= data[0].length) {
      return false;
    }
    int i = 0;
    int row = r + i * rowIncrement;
    int col = c + i * colIncrement;
    for(; i < word.length(); i = i + 1) {
      if (data[row][col] != word.charAt(i)) {
        return false;
      }
      if (data[row][col] != ' ') {
        return false;
      }
    }
    i = 0;
    for(; i < word.length(); i = i + 1) {
      data[row][col] = word.charAt(i);
    }
    wordsAdded.add(word);
    wordsToAdd.remove(word);
    return true;
  }
  private void addAllWords() {
    for(int i = 0; i < 9000; i = i + 1) {
      if (wordsToAdd.size() > 0) {
        String word = wordsToAdd.get(randgen.nextInt(wordsToAdd.size()));
        int rowIncrement = 0;
        int colIncrement = 0;
        while (rowIncrement == 0) {
          rowIncrement = randgen.nextInt() % 2;
        }
        while (colIncrement == 0) {
          colIncrement = randgen.nextInt() % 2;
        }
        for(int j = 0; j < 100 &&
        !addWord(word, randgen.nextInt(data.length), randgen.nextInt(data[0].length), rowIncrement, colIncrement); j = j + 1);
      }
    }
  }
  public String printWordsToAdd() {
    String why = "";
    for(int i = 0; i < wordsToAdd.size(); i = i + 1) {
      why = why + wordsToAdd.get(i);
    }
    return why;
  }
  public static void main(String[] args) {
    WordSearch Die = new WordSearch(10, 10, "words.txt");
    System.out.println(Die.printWordsToAdd());
    System.out.println(Die);
  }
}
