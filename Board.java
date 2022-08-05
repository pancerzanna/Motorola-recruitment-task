public class Board {
    private Card cards[][];

    public Board(Card cards[][]){
        this.cards = cards;
    }

    public void displayBoard(){
        int rowsLength = this.cards.length;
        int columnsLength = this.cards[0].length;

        for (int i=0; i<rowsLength; i++){
            for (int j=0; j<columnsLength; j++){
                if(cards[i][j].getState()){
                    System.out.print(cards[i][j].getName() + "\t");
                }
                else 
                System.out.print("X ");
            }
            System.out.println();
        }
    }

    public void clear() {
            System.out.print("\033[H\033[2J");
            System.out.flush();
    }

    public void update(Card[][] cards) {
        this.cards = cards;
    }
}
