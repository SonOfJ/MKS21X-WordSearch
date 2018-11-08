public class WordSearch{
    private char[][]data;
    public WordSearch(int rows,int cols){
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
        data[row][i] = word.charAt(index);
        index = index + 1;
      }
      return true;
    }

   /**Attempts to add a given word to the specified position of the WordGrid.
     *The word is added from top to bottom, must fit on the WordGrid, and must
     *have a corresponding letter to match any letters that it overlaps.
     *
     *@param word is any text to be added to the word grid.
     *@param row is the vertical locaiton of where you want the word to start.
     *@param col is the horizontal location of where you want the word to start.
     *@return true when the word is added successfully. When the word doesn't fit,
     *or there are overlapping letters that do not match, then false is returned.
     *and the board is NOT modified.
     */
    public boolean addWordVertical(String word,int row, int col){
    }
}
