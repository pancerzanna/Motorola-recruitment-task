import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class MemoryGame {
    private int level = 0;
    private List<String> words = new ArrayList<String>();
    private LinkedList<String> selectedWords = new LinkedList<>();
    private Card cards[][];
    private int rowsLength;
    private int columnsLength;
    private int cardsCovered;
    private int chances;
    private Board board;
    private String answer;

    private void displayGreetings(){
        System.out.println("--------------------------------------");
        System.out.println("Welcome to the memory game!");
    }

    private void loadWords() throws FileNotFoundException{
        File file = new File("Words.txt");
        Scanner fileScanner = new Scanner(file);
        while (fileScanner.hasNextLine()){
            words.add(fileScanner.nextLine());
        }
        fileScanner.close();
    }

    private void defineLevel(){
        System.out.println("Please, choose the difficulty level. Type 1 for easy level or 2 for hard level. ");
        Scanner levelScanner = new Scanner(System.in); 
        level = levelScanner.nextInt();
    }

    private void defineColumnsAndRowsLength() {
        if (level == 1) {
            this.rowsLength = 2;
            this.columnsLength = 4;
            this.cardsCovered = 8;
            this.chances = 10;
        } else if (level == 2) {
            this.rowsLength = 4;
            this.columnsLength = 4;
            this.cardsCovered = 16;
            this.chances = 15;
        }
    }

    private void selectWords(){
            for (int i = 0; i < this.rowsLength * 2 ; i++) {

                int abc = new Random().nextInt(words.size());
                String randomWord = words.get(abc);
                this.selectedWords.addAll(Collections.nCopies(2, randomWord));
                Collections.shuffle(selectedWords);
            }
    }

    private void prepareCards(){
        this.cards = new Card[this.rowsLength][this.columnsLength];
        for (int i=0; i< this.rowsLength; i++){
            for (int j=0; j< this.columnsLength; j++){
               cards[i][j] = new Card(this.selectedWords.pop(), false);
            }
        }
    }

    private Coordinates defineCoordinate(int number) {
    
        System.out.println("Enter coordinate " + number + " Ex. 00");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();  
        Coordinates coordinates = new Coordinates(Integer.valueOf(input.substring(0, 1)), Integer.valueOf(input.substring(1, 2)));
        return coordinates;
    }

    private void check(Coordinates coordinates1, Coordinates coordinates2) {
        if(cards[coordinates1.getX()][coordinates1.getY()].getName() == cards[coordinates2.getX()][coordinates2.getY()].getName()) {
            System.out.println("It's a match!");
            cards[coordinates1.getX()][coordinates1.getY()].setState(true);
            cards[coordinates2.getX()][coordinates2.getY()].setState(true);
            cardsCovered = cardsCovered - 2;
        }
        else {
            System.out.println("Try again!");
            cards[coordinates1.getX()][coordinates1.getY()].setState(false);
            cards[coordinates2.getX()][coordinates2.getY()].setState(false);
            chances = chances - 1;
        }
    }
    
    private void restart() throws FileNotFoundException, InterruptedException{
        System.out.println("Do you want to play again? Type Y for yes or N for no");
        Scanner scanner = new Scanner(System.in);
        answer = scanner.next(); 
        if (answer.equals("y") || answer.equals("Y")){
            this.board.clear();
            this.start();
        }
        while (answer.equals("n") || answer.equals("N")){
            break;
        }
    }
         
    public void start() throws FileNotFoundException, InterruptedException{
        this.displayGreetings();
        this.defineLevel();
        this.defineColumnsAndRowsLength();
        this.loadWords();
        this.selectWords();
        this.prepareCards();
        
        this.board = new Board(cards);
        this.board.clear();
        System.out.println("Guess chances: " + chances);
        this.board.displayBoard();
        
        while(chances > 0) {
            
            Coordinates coordinates1 = this.defineCoordinate(1);

            this.cards[coordinates1.getX()][coordinates1.getY()].setState(true);

            this.board.update(this.cards);
            this.board.clear();
            this.board.displayBoard();

            Coordinates coordinates2 = this.defineCoordinate(2);

            this.cards[coordinates2.getX()][coordinates2.getY()].setState(true);

            this.board.update(this.cards);
            this.board.clear();
            this.board.displayBoard();
            this.check(coordinates1, coordinates2);
            
            Thread.sleep(2000);

            this.board.update(this.cards);
            this.board.clear();
            System.out.println("Guess chances: " + chances);
            this.board.displayBoard();
    
            if (cardsCovered == 0){
                System.out.println("You win!");
                chances = 0;
                this.restart();

            } else if (chances == 0){
                System.out.println("No more guess chances!");
                this.restart();
            }
            
        }
    }

}