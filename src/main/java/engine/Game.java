package engine;

import engine.cards.CardsHelper;
import engine.heroes.*;

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

    public void switchActiveHero() {
    	System.out.println("-----------------------------------");
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
        activeHero = null;
    }

    public void endWithWinner(Hero winner) {
        this.winner = winner;
        end();
    }

    public void roundEndNotification() {
        switchActiveHero();
    }

    public void deadHeroNotification(Hero hero) {
        if(hero.equals(firstHero)) {
            endWithWinner(secondHero);
        } else if (hero.equals(secondHero)) {
            endWithWinner(firstHero);
        }
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
    

//  protected Game game;
//  protected List<Move> availableMoves;
    
    public Game deepCopy()
    {
    	Game resoult=new Game();
    	Hero firstHero=this.firstHero.deepCopy();
    	Hero secHero=this.secondHero.deepCopy();
    	((AbstractHero)firstHero).setGame(resoult);
    	((AbstractHero)secHero).setGame(resoult);
    	
    	resoult.firstHero=firstHero;
    	resoult.secondHero=secHero;
    	
    	if(this.activeHero == this.firstHero)
    		resoult.activeHero=firstHero;
    	else
    		resoult.activeHero=secHero;
    	
    	resoult.firstHero.setMovesInRound(this.firstHero.copyMovesTo((AbstractHero) resoult.firstHero, this.firstHero.getMovesInRound()));
    	resoult.secondHero.setMovesInRound(this.secondHero.copyMovesTo((AbstractHero) resoult.secondHero, this.secondHero.getMovesInRound()));

    	resoult.firstHero.setAvailableMoves(this.firstHero.copyMovesTo((AbstractHero) resoult.firstHero, this.firstHero.getAvailableMoves()));
    	resoult.secondHero.setAvailableMoves(this.secondHero.copyMovesTo((AbstractHero) resoult.secondHero, this.secondHero.getAvailableMoves()));
    	
    	resoult.firstHero.setGame(resoult);
    	resoult.secondHero.setGame(resoult);
    	
    	return resoult;
    	
    }
}
