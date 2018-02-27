package engine;

import engine.cards.CardsHelper;
import engine.heroes.DefaultHero;

import java.util.List;

/**
 * We assume that when unique instance is necessary, we return deep copy
 * and we assume that in every place where copy is expected, we receive a copy, not an original object.
 * <p>
 * For example - standard deck generator should return deep copy of registered cards, so we do not make copy of
 * returned deck on our own but we use the returned collection, because we expect it to be a copy.
 */
public class Game {

    public static final int MAXIMUM_CARDS_ON_BOARD = 10;

    private Hero firstHero;
    private Hero secondHero;
    private Hero activeHero;
    private boolean isGameOver;
    private Hero winner;

    public void initializeAndStartStandardGame() {
        initializeStandardHeroes();
        setActiveHero(firstHero);
        setGameOver(false);
    }

    public void initializeAndStartCustomGame(List<Card> firstHeroDeck, int firstHeroInitialHandSize, List<Card> secondHeroDeck, int secondHeroInitialHandSize) {
        initializeCustomHeroes(firstHeroDeck, firstHeroInitialHandSize, secondHeroDeck, secondHeroInitialHandSize);
        setActiveHero(firstHero);
        setGameOver(false);
    }

    public void switchActiveHero() {
        if (activeHero == null) {
            return;
        }

        if (activeHero.equals(firstHero)) {
            activeHero = secondHero;
        } else {
            activeHero = firstHero;
        }
        activeHero.startRound();
    }

    public void checkForGameEnd() {
        if (firstHero.isDead()) {
            endWithWinner(secondHero);
        } else if (secondHero.isDead()) {
            endWithWinner(firstHero);
        }
    }

    public void end() {
        setGameOver(true);
        activeHero = null;
    }

    public void endWithWinner(Hero winner) {
        this.winner = winner;
        end();
    }

    public void roundEndNotification() {
        switchActiveHero();
    }

    public void deadHeroNotification() {
        checkForGameEnd();
    }

    public void gameEndNotification() {
        end();
    }

    public Hero getEnemyOf(Hero hero) {
        if (hero == firstHero) { //celowo porownanie ref
            return secondHero;
        } else {
            return firstHero;
        }
    }

    private void initializeStandardHeroes() {
        firstHero = new DefaultHero(this, "First Hero", generateStandardDeck(), 3);
        assignCardsToHero(firstHero);
        secondHero = new DefaultHero(this, "Second Hero", generateStandardDeck(), 4);
        assignCardsToHero(secondHero);
    }

    private void initializeCustomHeroes(List<Card> firstHeroDeck, int firstHeroInitialHandSize, List<Card> secondHeroDeck, int secondHeroInitialHandSize) {
        firstHero = new DefaultHero(this, "First Custom Hero", firstHeroDeck, firstHeroInitialHandSize);
        assignCardsToHero(firstHero);
        secondHero = new DefaultHero(this, "Second Custom Hero", secondHeroDeck, secondHeroInitialHandSize);
        assignCardsToHero(secondHero);
    }

    private void assignCardsToHero(Hero hero) {
        hero.getDeck().forEach(card -> card.setOwner(hero));
    }

    private List<Card> generateStandardDeck() {
        return CardsHelper.generateStandardDeck();
    }

    public Hero getFirstHero() {
        return firstHero;
    }

    public void setFirstHero(Hero firstHero) {
        this.firstHero = firstHero;
    }

    public Hero getSecondHero() {
        return secondHero;
    }

    public void setSecondHero(Hero secondHero) {
        this.secondHero = secondHero;
    }

    public Hero getActiveHero() {
        return activeHero;
    }

    public void setActiveHero(Hero activeHero) {
        this.activeHero = activeHero;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public Hero getWinner() {
        return winner;
    }

    public void setWinner(Hero winner) {
        this.winner = winner;
    }
}
