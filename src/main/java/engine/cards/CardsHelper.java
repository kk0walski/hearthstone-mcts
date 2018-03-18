package engine.cards;

import engine.Card;
import engine.cards.minions.*;
import engine.cards.spells.ArcaneShot;
import engine.cards.spells.Fireball;
import engine.cards.spells.HealingTouch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CardsHelper {

    /**
     * Represents all cards types that can be used in the game.
     */
    private static List<Card> registeredStandardDeck;

    /**
     * Works on copy of registeredStandardDeck. In standard deck, every card appears twice.
     *
     * @return shuffled copy of registeredStandardDeck
     */
    public static List<Card> generateStandardDeck() {
        if (registeredStandardDeck == null) {
            registerStandardDeck();
        }

        List<Card> standardDeckCopyWithDoubledCards = deepDeckCopy(registeredStandardDeck);
        standardDeckCopyWithDoubledCards.addAll(deepDeckCopy(registeredStandardDeck));

        return standardDeckCopyWithDoubledCards;
        // return shuffle(standardDeckCopyWithDoubledCards);
    }

    /**
     * Shuffles deck. Works on original object.
     *
     * @param cards cards
     * @return original shuffled cards
     */
    public static List<Card> shuffle(List<Card> cards) {
        long seed = System.nanoTime();
        Collections.shuffle(cards, new Random(seed));
        return cards;
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

        registerCard(registeredStandardDeck, new MassiveGnoll());
        registerCard(registeredStandardDeck, new LordOfTheArena());
        registerCard(registeredStandardDeck, new Tabbycat());
        registerCard(registeredStandardDeck, new Alleycat());
        registerCard(registeredStandardDeck, new AntiqueHealbot());
        registerCard(registeredStandardDeck, new RiverCrocolisk());
        registerCard(registeredStandardDeck, new Treant());

        registerCard(registeredStandardDeck, new HealingTouch());
         registerCard(registeredStandardDeck, new ArcaneShot());
        registerCard(registeredStandardDeck, new Fireball());

    }

    private static void registerCard(List<Card> deck, Card card) {
        deck.add(card);
    }
}
