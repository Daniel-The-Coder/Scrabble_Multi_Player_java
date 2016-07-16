import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Lord Daniel on 7/16/2016.
 */
public class Validator {

    ArrayList<String> words;

    /**
     * read text file with all words in the English
     * language and populate the words ArrayList
     */
    public static void init(){

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
    public static int validate(HashMap letterPositions, char[][] board, ArrayList<Character> tiles){
        //make sure that all indexes are valid - error code 1
        //make sure that all column values are equal or all row values are equal - error code 1
        //make sure the indexes in the board are unoccupied - error code 2

        //valid - code 0
        return 0;
    }

    /**
     * Convert String to Hashmap of letters and indexes
     * For example: X 2F Y 2G Z 2H -> { X:[2,6] ; Y:[2,7] ; Z:[2,8] }
     * @param line
     * @return
     */
    public static HashMap<String, int[]> parseInput(String line){
        HashMap<String, int[]> map = new HashMap();

        return map;
    }

    public static boolean isValid(String word){
        return true;
    }
}
