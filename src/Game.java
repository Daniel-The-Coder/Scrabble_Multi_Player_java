import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Lord Daniel on 7/16/2016.
 */
public class Game {
    public boolean gameOver;

    public static boolean firstWord = true;

    public ArrayList<Player> players;
    public TilesBag tilesBag;
    public Board board;

    public Game() throws FileNotFoundException{
        this.gameOver = false;
        this.players = new ArrayList<>();
        this.tilesBag = new TilesBag();
        this.board = new Board();
    }

    public void addTiles(ArrayList<LetterPosition> tiles){
        for(LetterPosition L:tiles){
            int row = L.position[0];
            int col = L.position[1];
            this.board.add(L.letter, row, col);
        }
    }

    public char[][] copyBoard(char[][] board1){
        char[][] board2 = new char[15][15];
        for(int i=0;i<15;i++){
            for(int j=0;j<15;j++){
                board2[i][j] = new Character(board1[i][j]);
            }
        }
        return board2;
    }

    public int computeScore(ArrayList<LetterPosition> tiles){
        int score = 0;
        for(LetterPosition L:tiles){
            score += tilesBag.getLetterScore(L.letter);
        }
        if(tiles.size() == 7){
            //BONUS
            score += 50;
        }
        return score;
    }

    /**
     * strip leading and trailing hyphens
     * @param st
     * @return
     */
    public String stripHyphens(String st, int index){
        String str = "";
        int i1 = index;
        int i2 = index-1;
        while(!(st.charAt(i1)=='-')){
            str += st.substring(i1,i1+1); // add to the end
            i1+=1;
        }
        while(!(st.charAt(i2)=='-')){
            str = st.substring(i2,i2+1) + str; // add to the beginning
            i2-=1;
        }
        return str;
    }

    /**
     * based on letterPositions, figure out if word is horizontal or vertical
     * get that row and column, then strip '-' from beginning and end
     * @param letterPositions
     * @return
     */
    public String computeWord(ArrayList<LetterPosition> letterPositions){
        char[][] boardCopy = copyBoard(board.getBoard());
        for (LetterPosition L:letterPositions){
            boardCopy[L.position[0]][L.position[1]] = L.letter;
        }
        String word = "";
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
        if(rowsEqual){
            String st = "";
            int row = letterPositions.get(0).position[0];
            for(int i=0;i<15;i++){
                st += Character.toString(boardCopy[row][i]);
            }
            word = stripHyphens(st, letterPositions.get(0).position[1]);
        }
        else if (colsEqual){
            String st = "";
            int col = letterPositions.get(0).position[1];
            for(int i=0;i<15;i++){
                st += Character.toString(boardCopy[i][col]);
            }
            word = stripHyphens(st, letterPositions.get(0).position[0]);
        }
        else{
            System.out.println("\nERROR in computeWord!!!\n");
        }
        return word;
    }

    public void initializeGame() throws FileNotFoundException{
        Validator.init();
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to Daniel's Scrabble!\n");
        System.out.print("How many players? (2-4) ");
        int numOfPlayers = in.nextInt();
        while(numOfPlayers < 2 || numOfPlayers > 4){
            System.out.println("Number of players must be between 2 and 4. Try again. ");
            numOfPlayers = in.nextInt();
        }
        System.out.println("\n* * * Game with "+numOfPlayers+" players will commence. * * *\n");
        for(int i = 0; i<numOfPlayers; i++){
            System.out.print("Player "+(i+1)+": Enter name: ");
            String playerName = in.next();
            if(playerName.equals("")){
                playerName = "Player"+(i+1);
            }
            this.players.add(new Player(playerName));
        }
    }

    /**
     * takes in a character and returns a number corresponding to it
     * @return
     */
    private static int letterToNum(char c){
        return (int)Character.toUpperCase(c) - 64; //A is 65; output should be 1
    }

    /**
     * Convert String to Hashmap of letters and indexes
     * For example: X 2F Y 2G Z 2H -> { X:[2,6] ; Y:[2,7] ; Z:[2,8] }
     * @param line
     * @return
     */
    public static ArrayList<LetterPosition> parseInput(String line){
        ArrayList<LetterPosition> ar = new ArrayList<>();
        List<String> lineList = Arrays.asList(line.split(" "));
        for(int i=0;i<lineList.size();i+=2){
            char letter = lineList.get(i).charAt(0);
            String position = lineList.get(i+1);
            int row = Integer.parseInt(position.substring(0, position.length()-1)) - 1;
            int col = letterToNum(position.charAt(position.length()-1)) - 1;
            int[] pos = {row,col};
            ar.add(new LetterPosition(letter, pos));
        }
        return ar;
    }

    public void play(){
        Scanner in = new Scanner(System.in);
        while(!gameOver){
            for(Player p: players){
                if(this.tilesBag.tilesLeft()==0){
                    gameOver = true;
                    break;
                }
                //else

                boolean pass = false;

                //refill player's list of tiles
                p.addTiles(tilesBag.getTiles(7-p.gettilesLeft()));

                //display current player's tiles and prompt him to play
                System.out.println("\nIt's "+p.getName()+"'s turn!");
                System.out.println("\n"+this.board+"\n");
                System.out.println("Your tiles: "+p.getTiles());
                System.out.print("Enter letters and locations OR \"pass\": ");
                String line = in.nextLine();
                if(line.toUpperCase().equals("PASS")){
                    System.out.println("\n* * * "+p.getName()+" passes this turn. * * *\n");
                }
                else {
                    ArrayList<LetterPosition> letterPositions = parseInput(line);
                    int errorcode = Validator.validate(letterPositions, this.board.getBoard(), p.getTiles());
                    if (errorcode != 0) {
                        //loop until valid indexes given
                        while (errorcode != 0) {
                            pass = false;
                            if (errorcode == 1) {
                                System.out.print("Invalid indexes. Try again: ");
                            } else if (errorcode == 2) {
                                System.out.print("You must place your tiles on only one row or one column. Try again: ");

                            } else if(errorcode == 3) {
                                System.out.print("One or more of the indexes is occupied. Try again: ");
                            }
                            else if(errorcode == 4){
                                System.out.print("You do not have enough tiles. Try again: ");
                            }
                            else{
                                System.out.print("Your word must join the existing cluster Try again: ");
                            }
                            line = in.nextLine();
                            if(line.equals("pass")){
                                System.out.println("\n* * * "+p.getName()+" passes this turn. * * *\n");
                                break;
                            }
                            letterPositions = parseInput(line);
                            errorcode = Validator.validate(letterPositions, this.board.getBoard(), p.getTiles());
                        }
                    }
                    //now valid input has been obtained
                    //use indexes to figure out the word and check if it's valid
                    String word = computeWord(letterPositions);
                    if(!Validator.isValid(word)) {
                        //no valid words found
                        //loop until valid word found
                        while (!Validator.isValid(word)) {
                            if(pass){
                                break;
                            }
                            System.out.print("Invalid word or length is 1: "+word+". Try again.");
                            line = in.nextLine();
                            if (line.equals("pass")) {
                                System.out.println("\n* * * " + p.getName() + " passes this turn. * * *\n");
                                break;
                            } else {
                                pass = false;
                                letterPositions = parseInput(line);
                                int errorcode2 = Validator.validate(letterPositions, this.board.getBoard(), p.getTiles());
                                if (errorcode2 != 0) {
                                    //loop until valid indexes given
                                    while (errorcode2 != 0) {
                                        if (errorcode == 1) {
                                            System.out.print("Invalid indexes. Try again: ");
                                        } else if (errorcode == 2) {
                                            System.out.print("You must place your tiles on only one row or one column. Try again: ");

                                        } else if(errorcode == 3) {
                                            System.out.print("One or more of the indexes is occupied. Try again: ");
                                        }
                                        else if(errorcode == 4){
                                            System.out.print("You do not have enough tiles. Try again: ");
                                        }
                                        else{
                                            System.out.print("Your word must join the existing cluster Try again: ");
                                        }
                                        line = in.nextLine();
                                        if(line.equals("pass")){
                                            System.out.println("\n* * * "+p.getName()+" passes this turn. * * *\n");
                                            pass = true;
                                            break;
                                        }
                                        letterPositions = parseInput(line);
                                        errorcode2 = Validator.validate(letterPositions, this.board.getBoard(), p.getTiles());
                                    }
                                }
                                //now valid input has been obtained
                                //use indexes to figure out the word and check if it's valid
                                word = computeWord(letterPositions);
                            }
                        }
                    }

                    if(!pass) {
                        firstWord = false;
                        //SCORE PERPENDICULAR WORDS TOO//TODO
                        ArrayList<LetterPosition> tiles = parseInput(line);
                        p.addScore(computeScore(tiles));
                        addTiles(tiles);
                        for (LetterPosition c : tiles) {
                            p.removeTile(c.letter);
                        }
                        System.out.println("\nWord: "+word);
                        System.out.println(p.getName() + " scores " + computeScore(tiles) + " points.");
                        System.out.println(p.getName() + "'s current total score is " + p.getScore() + " points.");

                    }
                }
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException{
        Game game = new Game();
        game.initializeGame();
        game.play();
        System.out.println("\n* * * GAME OVER * * *");
        //print winner
        Player winner = game.players.get(0);
        System.out.println("\nScores:");
        for(Player p:game.players){
            //print all scores
            System.out.println(p.getName()+": "+p.getScore());
            if(p.getScore() > winner.getScore()){
                winner = p;
            }
        }
        System.out.println("\nWinner: "+winner.getName());

    }
}
