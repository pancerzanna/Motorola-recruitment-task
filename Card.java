public class Card {
    private String name;
    private boolean state;

    public Card() {
        this.name = "X";
        this.state = false;
    }

    public Card(String name, boolean state) {
        this.name = name;
        this.state = state;
    }

    public String getName() {
        return this.name;
    }

    public boolean getState() {
        return this.state;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setState(boolean state){
        this.state = state;
    }
}