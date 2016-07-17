import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Lord Daniel on 7/16/2016.
 */
public class TilesBag {

    private ArrayList<Character> tiles;
    private HashMap<Character, Integer> letterScores;

    public TilesBag() throws FileNotFoundException{
        this.letterScores = new HashMap<>();
        this.letterScores.put('A',1);
        this.letterScores.put('B',3);
        this.letterScores.put('C',3);
        this.letterScores.put('D',2);
        this.letterScores.put('E',1);
        this.letterScores.put('F',4);
        this.letterScores.put('G',2);
        this.letterScores.put('H',2);
        this.letterScores.put('I',1);
        this.letterScores.put('J',8);
        this.letterScores.put('K',5);
        this.letterScores.put('L',1);
        this.letterScores.put('M',3);
        this.letterScores.put('N',1);
        this.letterScores.put('O',1);
        this.letterScores.put('P',3);
        this.letterScores.put('Q',10);
        this.letterScores.put('R',1);
        this.letterScores.put('S',1);
        this.letterScores.put('T',1);
        this.letterScores.put('U',1);
        this.letterScores.put('V',4);
        this.letterScores.put('W',4);
        this.letterScores.put('X',8);
        this.letterScores.put('Y',4);
        this.letterScores.put('Z',10);

        this.tiles = new ArrayList<>();
        Scanner in = new Scanner(new File("resources/letterFrequency"));
        while (in.hasNextLine()){
            List<String> Line1 = Arrays.asList(in.nextLine().split(" "));
            char letter = Line1.get(0).charAt(0);
            int freq = Integer.parseInt(Line1.get(1));
            for(int i=0; i<freq;i++){
                tiles.add(new Character(letter));
            }
        }
    }

    public int getLetterScore(char c){
        return letterScores.get(c);
    }

    public ArrayList<Character> getTiles(int num){
        ArrayList<Character> tilesToReturn = new ArrayList<>();
        if(num > tiles.size()){
            num = tiles.size();
        }
        int Num = num;
        for(int i=0; i<Num; i++){
            int index = 0 + (int)(Math.random() * tiles.size());
            tilesToReturn.add(this.tiles.get(index));
            this.tiles.remove(index);
        }
        return tilesToReturn;
    }

    public int tilesLeft(){
        return this.tiles.size();
    }
}
