
public class Deck<T> {
    
    // Member Variables
    private int numCards; // Number of cards in the deck
    private T[] cards; // Array representing the cards
    private int[] cardNumbers; // Array representing the cards numbers

    // Constructor
    public Deck (int numCards, T[] cards){
        // Initilizes numCards and cards to input values.
        this.numCards = numCards;
        this.cards = cards;

        // Initilize cardNumbers based on card count
        cardNumbers = new int[numCards];
        for (int i = 0; i < numCards; i++){
            cardNumbers[i] = i;
        }
    }

}