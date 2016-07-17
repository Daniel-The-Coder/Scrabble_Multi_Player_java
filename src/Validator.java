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

        //check whether current word joins existing cluster
        //if it's the first word, no error
        boolean error5 = true;
        for(LetterPosition L:letterPositions){
            for(int i = L.position[0]-1;i<L.position[0]+2;i++){
                for(int j = L.position[1]-1;j<L.position[1]+2;j++){
                    if( ! (board[i][j] == '-') ){
                        error5 = false;
                    }
                }
            }
        }
        if(error5 && !Game.firstWord){
            return 5;
        }

        //valid - code 0
        return 0;
    }


    public static boolean isValid(String word){
        //if the word is not in the words file
        if (!words.contains(word)) {
            return false;
        }
        //if the word contains '-' (empty cell)
        for(int i=0;i<word.length();i++){
            char c = word.charAt(i);
            if(c == '-'){
                return false;
            }
        }
        //if the length of the word is 1
        if(word.length()==1){
            return false;
        }
        return true;
    }
}


/*
TODO

1. Score perpendicular words.
2. Declare winner.


 */