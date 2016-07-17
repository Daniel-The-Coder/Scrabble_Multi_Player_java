import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Lord Daniel on 7/16/2016.
 */
public class Validator {

    private static ArrayList<String> words;

    /**
     * read text file with all words in the English
     * language and populate the words ArrayList
     */
    public static void init() throws FileNotFoundException{
        Scanner in = new Scanner(new File("resources/words"));
        words = new ArrayList<>();
        while(in.hasNext()){
            words.add(in.next());
        }
    }

    /**
     * make sure that all indexes are valid - error code 1
     * make sure that all column values are equal or all row values are equal - error code 1
     * make sure the indexes in the board are unoccupied - error code 2
     *
     * if valid, returns 0
     *
     * @param letterPositions
     * @param board
     * @param tiles
     * @return
     */
    public static int validate(HashMap<Character, int[]> letterPositions, char[][] board, ArrayList<Character> tiles){
        ArrayList<Character> keys= new ArrayList<>(letterPositions.keySet());

        //make sure that all indexes are valid - error code 1
        for(char c:keys){
            if(letterPositions.get(c)[0]<0 || letterPositions.get(c)[0]>14 || letterPositions.get(c)[1]<0 || letterPositions.get(c)[1]>14){
                return 1;
            }
        }

        //make sure that all column values are equal or all row values are equal - error code 1
        boolean rowsEqual = true;
        boolean colsEqual = true;
        int row1 = letterPositions.get(keys.get(0))[0];
        int col1 = letterPositions.get(keys.get(0))[1];
        for(char c:keys){
            if(!(letterPositions.get(c)[0]==row1)){
                rowsEqual = false;
            }
            if(!(letterPositions.get(c)[1]==col1)){
                colsEqual = false;
            }
        }
        //either rows are equal or cols are equal, not both
        if( ! ( (!rowsEqual && colsEqual) || (rowsEqual && !colsEqual) ) ){
            return 1;
        }

        //make sure the indexes in the board are unoccupied - error code 2
        for (char c:keys){
            int[] pos = letterPositions.get(c);
            if(board[pos[0]][pos[1]] == '-'){
                return 2;
            }
        }

        //make sure the player has enough tiles - error code 3
        //keep removing letters the player chose from the list of his tiles.
        //if player's tiles list does not contain a letter, the selection is invalid
        ArrayList<Character> tilesCopy = new ArrayList<>(tiles);
        for(char c:keys){
            if(!tilesCopy.contains(c)){
                return 3;
            }
            else{
                tilesCopy.remove(c);
            }
        }

        //valid - code 0
        return 0;
    }

    /**
     * Convert String to Hashmap of letters and indexes
     * For example: X 2F Y 2G Z 2H -> { X:[2,6] ; Y:[2,7] ; Z:[2,8] }
     * @param line
     * @return
     */
    public static HashMap<Character, int[]> parseInput(String line){
        HashMap<Character, int[]> map = new HashMap();
        List<String> lineList = Arrays.asList(line.split(" "));
        for(int i=0;i<lineList.size();i+=2){
            char letter = lineList.get(i).charAt(0);
            String position = lineList.get(i+1);
            int row = Integer.parseInt(position.substring(0, position.length()-1));
            int col = letterToNum(position.charAt(position.length()-1));
            int[] pos = {row,col};
            map.put(letter, pos);
        }
        return map;
    }

    /**
     * takes in a character and returns a number corresponding to it
     * @return
     */
    private static int letterToNum(char c){
        return (int)Character.toUpperCase(c) - 97; //A is 97; output should be 0
    }

    public static boolean isValid(String word){
        if (words.contains(word)) {
            return true;
        }
        return false; //else
    }
}
