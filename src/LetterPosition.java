/**
 * Created by Lord Daniel on 7/17/2016.
 */
public class LetterPosition {
    public char letter;
    public int[] position;

    public LetterPosition(char letter, int[] position){
        this.letter = letter;
        this.position = position;
    }

    @Override
    public String toString(){
        return letter+": "+position[0]+","+position[1];
    }
}
