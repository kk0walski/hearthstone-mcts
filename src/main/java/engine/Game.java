package engine;

import engine.cards.CardsHelper;
import engine.heroes.*;
import engine.mcts.Node;

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

    public void initializeAndStartHumanWithRandomGame() {
        initializeStandardHeroAndRandom();
        setActiveHero(firstHero);
        setGameOver(false);
    }

    public void initializeAndStartHumanWithPassiveGame() {
        initializeStandardHeroAndPassive();
        setActiveHero(firstHero);
        setGameOver(false);
    }

    public void initializeAndStartHumanWithMctsGame() {
        initializeStandardHeroAndMcts();
        setActiveHero(firstHero);
        setGameOver(false);
    }

    public void initializeAndStartRandomWithMctsGame(int timeForMctsMove, int totalTimeForMctsMoves) {
        initializeRandomHeroAndMcts(timeForMctsMove, totalTimeForMctsMoves);
        setActiveHero(firstHero);
        setGameOver(false);
    }

    public void initializeAndStartAggresiveWithMctsGame(int timeForMctsMove, int totalTimeForMctsMoves) {
        initializeAggresiveHeroAndMcts(timeForMctsMove, totalTimeForMctsMoves);
        setActiveHero(firstHero);
        setGameOver(false);
    }

    public void initializeAndStartPassiveWithMctsGame(int timeForMctsMove, int totalTimeForMctsMoves) {
        initializePassiveAndMcts(timeForMctsMove, totalTimeForMctsMoves);
        setActiveHero(firstHero);
        setGameOver(false);
    }

    public void initializeAndStartInitializeRandomHeroAndPassiveGame() {
        initializeRandomHeroAndPassive();
        setActiveHero(firstHero);
        setGameOver(false);
    }

    private void initializeStandardHeroAndRandom() {
        firstHero = new DefaultHero(this, "First Hero", generateStandardDeck(), 3);
        assignCardsToHero(firstHero);
        secondHero = new RandomHero(this, "Second Hero", generateStandardDeck(), 4);
        assignCardsToHero(secondHero);
    }


    public void initializeAndStartHumanWithAgresiveGame() {
        initializeStandardHeroAndAgresive();
        setActiveHero(firstHero);
        setGameOver(false);
    }


    private void initializeStandardHeroAndAgresive() {
        firstHero = new DefaultHero(this, "First Hero", generateStandardDeck(), 3);
        assignCardsToHero(firstHero);
        secondHero = new AgresiveHero(this, "Second Hero", generateStandardDeck(), 4);
        assignCardsToHero(secondHero);
    }

    private void initializeStandardHeroAndPassive() {
        firstHero = new DefaultHero(this, "First Hero", generateStandardDeck(), 3);
        assignCardsToHero(firstHero);
        secondHero = new PassiveHero(this, "Second Hero", generateStandardDeck(), 4);
        assignCardsToHero(secondHero);
    }

    private void initializeStandardHeroAndMcts() {
        firstHero = new DefaultHero(this, "First Hero", generateStandardDeck(), 3);
        assignCardsToHero(firstHero);
        secondHero = new MctsHero(this, "Mcts Hero", generateStandardDeck(), 4);
        assignCardsToHero(secondHero);
    }

    private void initializeRandomHeroAndPassive() {
        firstHero = new RandomHero(this, "First Hero", generateStandardDeck(), 3);
        assignCardsToHero(firstHero);
        secondHero = new PassiveHero(this, "Second Hero", generateStandardDeck(), 4);
        assignCardsToHero(secondHero);
    }

    private void initializeRandomHeroAndMcts(int timeForMctsMove, int totalTimeForMctsMoves) {
        firstHero = new MctsHero(this, "Mcts Hero", generateStandardDeck(), 3, timeForMctsMove, totalTimeForMctsMoves);
        assignCardsToHero(firstHero);
        secondHero = new RandomHero(this, "Random Hero", generateStandardDeck(), 4);
        assignCardsToHero(secondHero);
    }

    private void initializeAggresiveHeroAndMcts(int timeForMctsMove, int totalTimeForMctsMoves) {
        firstHero = new MctsHero(this, "Mcts Hero", generateStandardDeck(), 3, timeForMctsMove, totalTimeForMctsMoves);
        assignCardsToHero(firstHero);
        secondHero = new AgresiveHero(this, "Aggresive Hero", generateStandardDeck(), 4);
        assignCardsToHero(secondHero);
    }

    private void initializePassiveAndMcts(int timeForMctsMove, int totalTimeForMctsMoves) {
        firstHero = new MctsHero(this, "Mcts Hero", generateStandardDeck(), 3, timeForMctsMove, totalTimeForMctsMoves);
        assignCardsToHero(firstHero);
        secondHero = new PassiveHero(this, "Passive Hero", generateStandardDeck(), 4);
        assignCardsToHero(secondHero);
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

    public void revertSwitchActiveHero() {
        activeHero.revertStartRound();

        if (activeHero.equals(firstHero)) {
            activeHero = secondHero;
        } else {
            activeHero = firstHero;
        }
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
    }

    public void endWithWinner(Hero winner) {
        this.winner = winner;
        end();
    }

    public void roundEndNotification() {
        switchActiveHero();
    }

    public void deadHeroNotification(Hero hero) {
        if (hero.equals(firstHero)) {
            endWithWinner(secondHero);
        } else if (hero.equals(secondHero)) {
            endWithWinner(firstHero);
        }
    }

    public void gameEndNotification() {
        end();
    }

    public Hero getEnemyOf(Hero hero) {
        if (hero == firstHero) {
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

    private void initializeHumanWithRandomHeroes(List<Card> firstHeroDeck, int firstHeroInitialHandSize, List<Card> secondHeroDeck, int secondHeroInitialHandSize) {
        firstHero = new DefaultHero(this, "First Human Default Hero", firstHeroDeck, firstHeroInitialHandSize);
        assignCardsToHero(firstHero);
        secondHero = new RandomHero(this, "Second Random Hero", secondHeroDeck, secondHeroInitialHandSize);
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

    public Game deepCopy(Node root) {
        Game result = new Game();
        Hero firstHero = null;
        Hero secHero = null;
        if (this.firstHero == activeHero) {
            firstHero = this.firstHero.deepCopy(root);
            secondHero.generateAvailableMoves();
            secHero = this.secondHero.deepCopy(root);
        }
        if (this.secondHero == activeHero) {
            this.firstHero.generateAvailableMoves();
            firstHero = this.firstHero.deepCopy(root);
            secHero = this.secondHero.deepCopy(root);
        }
        ((AbstractHero) firstHero).setGame(result);
        ((AbstractHero) secHero).setGame(result);

        result.firstHero = firstHero;
        result.secondHero = secHero;

        if (this.activeHero == this.firstHero)
            result.activeHero = firstHero;
        else
            result.activeHero = secHero;

        result.firstHero.setAvailableMoves(this.firstHero.copyMovesTo(root, (AbstractHero) result.firstHero, this.firstHero.getAvailableMoves()));
        result.secondHero.setAvailableMoves(this.secondHero.copyMovesTo(root, (AbstractHero) result.secondHero, this.secondHero.getAvailableMoves()));

        result.firstHero.setGame(result);
        result.secondHero.setGame(result);

        return result;

    }
}
