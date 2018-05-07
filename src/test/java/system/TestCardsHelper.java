package system;

import engine.Card;
import engine.cards.minions.*;
import engine.cards.spells.ArcaneShot;
import engine.cards.spells.Fireball;
import engine.cards.spells.HealingTouch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TestCardsHelper {

    public static List<Card> generateNonShuffledDeck(List<Card> deck) {

        List<Card> standardDeckCopyWithDoubledCards = deepDeckCopy(deck);
        standardDeckCopyWithDoubledCards.addAll(deepDeckCopy(deck));

        return standardDeckCopyWithDoubledCards;
    }

    public static List<Card> generateShuffledDeck(List<Card> deck) {
        return shuffle(generateNonShuffledDeck(deck));
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
    public static List<Card> fullDeck() {
        List<Card> cardsList = new ArrayList<>();

        registerCard(cardsList, new MassiveGnoll());
        registerCard(cardsList, new LordOfTheArena());
        registerCard(cardsList, new Tabbycat());
        registerCard(cardsList, new Alleycat());
        registerCard(cardsList, new AntiqueHealbot());
        registerCard(cardsList, new RiverCrocolisk());
        registerCard(cardsList, new Treant());

        registerCard(cardsList, new HealingTouch());
        registerCard(cardsList, new ArcaneShot());
        registerCard(cardsList, new Fireball());

        return cardsList;
    }

    public static List<Card> deckWithSingleCard(Card card) {
        List<Card> cardsList = new ArrayList<>();

        registerCard(cardsList, card);

        return cardsList;
    }

    public static List<Card> deckWithTreantAndHealingTouch() {
        List<Card> cardsList = new ArrayList<>();

        registerCard(cardsList, new Treant());

        registerCard(cardsList, new HealingTouch());

        return cardsList;
    }

    public static List<Card> deckWithTreantAndDeadlyShot() {
        List<Card> cardsList = new ArrayList<>();

        registerCard(cardsList, new Treant());

        registerCard(cardsList, new ArcaneShot());

        return cardsList;
    }

    public static List<Card> deckWithTreantAndFireball() {
        List<Card> cardsList = new ArrayList<>();

        registerCard(cardsList, new Treant());

        registerCard(cardsList, new Fireball());

        return cardsList;
    }

    private static void registerCard(List<Card> deck, Card card) {
        deck.add(card);
    }
}