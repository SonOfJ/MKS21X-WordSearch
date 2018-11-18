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
  public WordSearch(int rows, int cols, String filename, int randSeed, boolean key) {
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
  }
  private void clear(){
    for(int i = 0; i < data.length; i = i + 1) {
      for(int j = 0; j < data[i].length; j = j + 1) {
        data[i][j] = ' ';
      }
    }
  }
  private void fill() {
    String alpha = "qwertyuiopasdfghjklzxcvbnm";
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
    int row = r + j * rowIncrement;
    int col = c + j * colIncrement;
    if (word.length() == 0 ||
    r < 0 ||
    c < 0 ||
    row < 0 ||
    col < 0 ||
    rowIncrement == 0 && colIncrement == 0 ||
    row >= data.length ||
    col >= data[row].length) {
      return false;
    }
    for(; j < word.length(); j = j + 1) {
      if (data[row][col] != '_' &&
      data[row][col] != word.charAt(j)) {
        return false;
      }
    }
    int i = 0;
    row = r + i * rowIncrement;
    col = c + i * colIncrement;
    for(; i < word.length(); i = i + 1) {
      data[row][col] = word.charAt(i);
    }
    wordsAdded.add(word);
    wordsToAdd.remove(word);
    return true;
  }
  private void addAllWords() {
    int size = wordsToAdd.size();
    for (int i = 0; wordsToAdd.size() > 0 && i < size + 100; i = i + 1) {
      if (wordsToAdd.size() > 0) {
        String word = wordsToAdd.get(randgen.nextInt(wordsToAdd.size()));
        int r = 0;
        int c = 0;
        while (r == 0) {
          r = randgen.nextInt() % 2;
        }
        while (c == 0) {
          c = randgen.nextInt() % 2;
        }
        int row = data.length;
        int col = data[0].length;
          for (int j = 0; j < 100 &&
          !addWord(word, randgen.nextInt(row + 1), randgen.nextInt(col + 1), r, c); j = j + 1);
        }
      }
    }

    public static void main(String[] args) {
      WordSearch Die = new WordSearch(10, 10, "words.txt");
      System.out.println(Die);
      }
    }
