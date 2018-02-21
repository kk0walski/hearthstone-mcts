package engine.cards;

import engine.Card;

import java.util.ArrayList;
import java.util.List;

public class CardsHelper {

    /**
     * Represents all cards types that can be used in the game.
     */
    private static List<Card> registeredStandardDeck;

    public static List<Card> generateStandardDeck() {
        if(registeredStandardDeck == null) {
            registerStandardDeck();
        }

        return deepDeckCopy(registeredStandardDeck);
    }

    private static List<Card> deepDeckCopy(List<Card> deckToCopy) {
        List<Card> deckCopy = new ArrayList<>();

        deckToCopy.forEach(cardToCopy -> deckCopy.add(cardToCopy.deepCopy()));

        return deckCopy;
    }

    /**
     * Here we instantiate all cards that are to be included in standard deck
     */
    private static void registerStandardDeck() {
        registeredStandardDeck = new ArrayList<>();

        registerCard(registeredStandardDeck, new TestCardOne());
        registerCard(registeredStandardDeck, new TestCardTwo());
    }

    private static void registerCard(List<Card> deck, Card card) {
        deck.add(card);
    }
}
