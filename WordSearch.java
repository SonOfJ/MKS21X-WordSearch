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
      try {
        Scanner reader = new Scanner(new File(filename));
        while (reader.hasNext()) {
          wordsToAdd.add(reader.next().toUpperCase());
        }
      } catch (FileNotFoundException e) {
          System.out.println(filename + "does not exist.");
          e.printStackTrace();
          System.exit(1);
        }
      data = new char[rows][cols];
      clear();
      randgen = new Random();
      seed = randgen.nextInt();
      randgen = new Random(seed);
      addAllWords();
    }
    public WordSearch(int rows, int cols, String filename, int randSeed) {
      if (rows < 1 || cols < 1) {
        throw new IllegalArgumentException();
      }
      try {
        Scanner reader = new Scanner(new File(filename));
        while(reader.hasNext()) {
          wordsToAdd.add(reader.next().toUpperCase());
        }
      } catch (FileNotFoundException e) {
          System.out.println(filename + "does not exist.");
          e.printStackTrace();
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
          data[i][j] = '_';
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
    public boolean addWord( String word, int r, int c, int rowIncrement, int colIncrement) {
      for(int i = 0; i < word.length(); i = i + 1) {
        int row = r + i * rowIncrement;
        int col = c + i * colIncrement;
        if (word.length() == 0 ||
        r < 0 ||
        c < 0 ||
        row < 0 ||
        col < 0 ||
        rowIncrement == 0 && colIncrement == 0 ||
        row >= data.length ||
        col >= data[row].length ||
        data[row][col] != '_' && data[row][col] != word.charAt(i)) {
          return false;
        }
        data[row][col] = word.charAt(i);
      }
      wordsAdded.add(word);
      wordsToAdd.remove(word);
      return true;
    }
    public void addAllWords() {
      int size = wordsToAdd.size();
      for (int i = 0; wordsToAdd.size() > 0 && i < size + 100; i = i + 1) {
        if (wordsToAdd.size() > 0) {
          String word = wordsToAdd.get(Math.abs(randgen.nextInt() % wordsToAdd.size()));
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
          !addWord(word, Math.abs(randgen.nextInt() % (row + 1)), Math.abs(randgen.nextInt() % (col + 1)), r, c); j = j + 1);
        }
      }
    }
    public boolean addWordHorizontal(String word,int row, int col){
      if (row < 0 || col < 0 || row >= data.length || col >= data[0].length) {
        return false;
      }
      if (word.length() > data[row].length - col) {
        return false;
      }
      int index = 0;
      for(int i = col; i < word.length() + col; i = i + 1) {
        if (data[row][i] != '_' && data[row][i] != word.charAt(index)) {
          return false;
        }
        index = index + 1;
      }
      index = 0;
      for(int i = col; i < word.length() + col; i = i + 1) {
        data[row][i] = word.charAt(index);
        index = index + 1;
      }
      return true;
    }
    public boolean addWordVertical(String word,int row, int col){
      if (row < 0 || col < 0 || row >= data.length || col >= data[0].length) {
        return false;
      }
      if (word.length() > data.length - row) {
        return false;
      }
      int index = 0;
      for(int i = row; i < word.length() + row; i = i + 1) {
        if (data[i][col] != '_' && data[i][col] != word.charAt(index)) {
          return false;
        }
        index = index + 1;
      }
      index = 0;
      for(int i = row; i < word.length() + row; i = i + 1) {
        data[i][col] = word.charAt(index);
        index = index + 1;
      }
      return true;
    }
    public boolean addWordDiagonal(String word,int row, int col){
      int r = row;
      int c = col;
      if (row < 0 || col < 0 || row >= data.length || col >= data[0].length) {
        return false;
      }
      if (word.length() > data.length - row) {
        return false;
      }
      if (word.length() > data[row].length - col) {
        return false;
      }
      int index = 0;
      for(int i = r; i < word.length() + r; i = i + 1) {
        if (data[i][col] != '_' && data[i][col] != word.charAt(index)) {
          return false;
        }
        index = index + 1;
        col = col + 1;
      }
      index = 0;
      for(int i = c; i < word.length() + c; i = i + 1) {
        if (data[row][i] != '_' && data[row][i] != word.charAt(index)) {
          return false;
        }
        index = index + 1;
        row = row + 1;
      }
      index = 0;
      for(int i = r; i < word.length() + r; i = i + 1) {
        data[i][c] = word.charAt(index);
        index = index + 1;
        c = c + 1;
      }
      return true;
    }
  }
