import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Lord Daniel on 7/16/2016.
 */
public class Game {
    public boolean gameOver;

    public ArrayList<Player> players;
    public TilesBag tilesBag;
    public Board board;

    public Game() throws FileNotFoundException{
        this.gameOver = false;
        this.players = new ArrayList<>();
        this.tilesBag = new TilesBag();
        this.board = new Board();
    }

    public void initializeGame() throws FileNotFoundException{
        Validator.init();
        Scanner in = new Scanner(System.in);
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

    public void play(){
        Scanner in = new Scanner(System.in);
        while(!gameOver){
            for(Player p: players){
                if(this.tilesBag.tilesLeft()==0){
                    gameOver = true;
                    break;
                }
                //else

                //refill player's list of tiles
                p.addTiles(tilesBag.getTiles(7-p.gettilesLeft()));

                //display current player's tiles and prompt him to play
                System.out.println("\nIt's "+p.getName()+"'s turn!");
                System.out.println("\n"+this.board+"\n");
                System.out.println("Your tiles: "+p.getTiles());
                System.out.print("Enter letters and locations OR \"pass\": ");
                String line = in.nextLine();
                if(line.equals("pass")){
                    System.out.println("\n* * * "+p.getName()+" passes this turn. * * *\n");
                }
                else {
                    HashMap<Character, int[]> letterPositions = Validator.parseInput(line);
                    int errorcode = Validator.validate(letterPositions, this.board.getBoard(), p.getTiles());
                    if (errorcode != 0) {
                        //loop until valid indexes given
                        while (errorcode != 0) {
                            if (errorcode == 1) {
                                System.out.print("Invalid indexes. Try again: ");
                            } else if(errorcode == 2) {
                                System.out.println("One or more of the indexes is occupied. Try again: ");
                            }
                            else{
                                System.out.println("You do not have enough tiles. Try again: ");
                            }
                            letterPositions = Validator.parseInput(in.nextLine());
                            errorcode = Validator.validate(letterPositions, this.board.getBoard(), p.getTiles());
                        }
                    }
                    //now valid input has been obtained
                    //use indexes to figure out the word and check if it's valid
                    String word = ""; //TODO use letterpositions ane board to figure out word
                    if(!Validator.isValid(word)) {
                        //no valid words found
                        //loop until valid word found
                        while (!Validator.isValid(word)) {
                            System.out.print("Try again.");
                            String line2 = in.nextLine();
                            if (line.equals("pass")) {
                                System.out.println("\n* * * " + p.getName() + " passes this turn. * * *\n");
                                break;
                            } else {
                                HashMap<Character, int[]> letterPositions2 = Validator.parseInput(line2);
                                int errorcode2 = Validator.validate(letterPositions2, this.board.getBoard(), p.getTiles());
                                if (errorcode2 != 0) {
                                    //loop until valid indexes given
                                    while (errorcode2 != 0) {
                                        if (errorcode2 == 1) {
                                            System.out.print("Invalid indexes. Try again: ");
                                        } else {
                                            System.out.println("One or more of the indexes is occupied. Try again: ");
                                        }
                                        letterPositions2 = Validator.parseInput(in.nextLine());
                                        errorcode2 = Validator.validate(letterPositions2, this.board.getBoard(), p.getTiles());
                                    }
                                }
                                //now valid input has been obtained
                                //use indexes to figure out the word and check if it's valid
                                word = ""; //TODO use letterpositions2 ane board to figure out word
                            }
                        }
                    }
                    else{
                        //add it to the board and remove from player's tiles //TODO
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
    }
}
