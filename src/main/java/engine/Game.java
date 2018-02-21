package engine;

import engine.cards.CardsHelper;
import engine.heroes.DefaultHero;

import java.util.List;

public class Game {

    private Hero firstHero;
    private Hero secondHero;
    private Hero activeHero;
    private boolean isGameOver;

    public void initializeStandardBoard() {
        initializeFirstHero(generateStandardDeck(), 3);
        initializeSecondHero(generateStandardDeck(), 4);
        setActiveHero(firstHero);
        setGameOver(false);
    }

    private void initializeFirstHero(List<Card> deck, int initialHandSize) {
        firstHero = new DefaultHero();
    }

    private void initializeSecondHero(List<Card> deck, int initialHandSize) {
        secondHero = new DefaultHero();
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
}
