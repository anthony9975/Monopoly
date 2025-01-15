import java.util.ArrayList;

public class Deck<T> {
    
    // Member Variables
    private int numCards; // Number of cards in the deck
    private T[] cards; // Array representing the cards
    private ArrayList<Integer> cardNumbers; // Array representing the cards numbers

    // Constructor
    public Deck (int numCards, T[] cards){
        this.numCards = numCards;
        this.cards = cards;
    }

    // Accessors
    public int getNumCards (){
        return numCards;
    }

    public T[] getCards (){
        return cards;
    }

    // Mutators
    public void newCards (int numCards, T[] cards){
        // Initilizes numCards and cards to input values.
        this.numCards = numCards;
        this.cards = cards;

        // Initilize cardNumbers based on card count
        cardNumbers = new ArrayList<>();
        for (int i = 0; i < numCards; i++){
            cardNumbers.add(i);
        }
    }

    // Member Functions
    // Draws one card from the deck
    public T draw (){
        // Randomly choose a card number
        int num = (int)(Math.random()*cardNumbers.size());
        int cardNum = cardNumbers.get(num);

        // Remove the choosen card num
        cardNumbers.remove(num);

        return cards[cardNum]; 
    }
}