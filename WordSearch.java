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
      int rowIncrement = 0;
      int colIncrement = 0;
      while (rowIncrement == 0) {
        rowIncrement = randgen.nextInt() % 2;
      }
      while (colIncrement == 0) {
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
    WordSearch Die = new WordSearch(10, 10, "words.txt");
    WordSearch Live = new WordSearch(10, 10, "words.txt", Die.seed, true);
    System.out.println(Die);
    System.out.println(Live);
  }
}
