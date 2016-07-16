import java.util.AbstractCollection;
import java.util.ArrayList;

/**
 * Created by Lord Daniel on 7/16/2016.
 */
public class Board {

    private char[][] board;

    public Board(){
        this.board = new char[15][15];
        for(int i=0; i<15; i++){
            for(int j=0; j<15; j++){
                this.board[i][j] = '-';
            }
        }
    }

    public char[][] getBoard(){
        return this.board;
    }

    @Override
    public String toString(){
        String st = "     A B C D E F G H I J K L M N O\n";
        st += "    ------------------------------\n";
        for (int i=0;i<15;i++){
            if(i<10){
                st += " "+i+" ";
            }
            else{
                st += i+" ";
            }
            st+="| ";
            for(int j=0;j<15;j++) {
                st += board[i][j]+" ";
            }
            st += "\n";
        }
        st += "    ------------------------------";
        return st;
    }
}
