public class WordSearch{
    private char[][]data;
    public WordSearch(int rows,int cols){
      if (rows < 0 || cols < 0) {
        throw new IndexOutOfBoundsException("Row or column cannot be negative");
      }
      data = new char[rows][cols];
      clear();
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
        for(int j = 0; j < data[i].length; j = j + 1) {
          if (j == data[i].length - 1) {
            board = board + data[i][j];
            board = board + "\n";
          } else {
            board = board + data[i][j];
          }
        }
      }
      return board;
    }
    public boolean addWordHorizontal(String word,int row, int col){
      if (word.length() > data[row].length - col) {
        return false;
      }
      int index = 0;
      for(int i = col; i < word.length() + col; i = i + 1) {
        if (data[row][i] != '_' || data[row][i] != word.charAt(index)) {
          return false;
        }
        data[row][i] = word.charAt(index);
        index = index + 1;
      }
      return true;
    }
    public boolean addWordVertical(String word,int row, int col){
      if (word.length() > data.length - row) {
        return false;
      }
      int index = 0;
      for(int i = row; i < word.length() + row; i = i + 1) {
        if (data[i][col] != '_' || data[i][col] != word.charAt(index)) {
          return false;
        }
        data[i][col] = word.charAt(index);
        index = index + 1;
      }
      return true;
    }
  }
