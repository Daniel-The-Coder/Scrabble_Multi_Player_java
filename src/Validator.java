import javax.jws.soap.SOAPBinding;
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
            words.add(in.next().toUpperCase());
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
    public static int validate(ArrayList<LetterPosition> letterPositions, char[][] board, ArrayList<Character> tiles){

        //make sure that all indexes are valid - error code 1
        for(LetterPosition L:letterPositions){
            if(L.position[0]<0 || L.position[0]>14 || L.position[1]<0 || L.position[1]>14){
                return 1;
            }
        }

        //make sure that all column values are equal or all row values are equal - error code 2
        boolean rowsEqual = true;
        boolean colsEqual = true;
        int row1 = letterPositions.get(0).position[0];
        int col1 = letterPositions.get(0).position[1];
        for(LetterPosition L:letterPositions){
            if(!(L.position[0]==row1)){
                rowsEqual = false;
            }
            if(!(L.position[1]==col1)){
                colsEqual = false;
            }
        }
        //either rows are equal or cols are equal, not both
        if( ! ( ((!rowsEqual) && colsEqual) || (rowsEqual && (!colsEqual)) || tiles.size()==1 ) ){
            return 2;
        }

        //make sure the indexes in the board are unoccupied - error code 3
        for (LetterPosition L:letterPositions){
            int[] pos = L.position;
            if(! (board[pos[0]][pos[1]] == '-') ){
                return 3;
            }
        }

        //make sure the player has enough tiles - error code 4
        //keep removing letters the player chose from the list of his tiles.
        //if player's tiles list does not contain a letter, the selection is invalid
        ArrayList<Character> tilesCopy = new ArrayList<>(tiles);
        for(LetterPosition L:letterPositions){
            if(!tilesCopy.contains(L.letter)){
                return 4;
            }
            else{
                tilesCopy.remove((Character)L.letter);
            }
        }

        //valid - code 0
        return 0;
    }


    public static boolean isValid(String word){
        if (!words.contains(word)) {
            return false;
        }
        for(int i=0;i<word.length();i++){
            char c = word.charAt(i);
            if(c == '-'){
                return false;
            }
        }
        return true;
    }
}


/*
ERRORS

1. Make sure word touches the current cluster unless its the first word.
2. Change compute word to get word from inside, to prevent reading characters of other words on same row or col. refer to image on desktop.
3. Score perpendicular words.

 */