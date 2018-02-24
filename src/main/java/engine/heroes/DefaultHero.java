package engine.heroes;

import engine.Card;
import engine.Game;
import engine.Hero;
import engine.Move;
import engine.cards.Minion;
import engine.cards.Spell;
import engine.cards.spells.DeadlyShot;
import engine.cards.spells.Fireball;
import engine.cards.spells.HealingTouch;
import engine.moves.AttackHero;
import engine.moves.AttackMinion;
import engine.moves.PutCard;
import engine.moves.UseSpell;

import java.util.ArrayList;
import java.util.List;

public class DefaultHero implements Hero {

    private int health;
    private int mana;
    private int round;
    private int punishForEmptyDeck;
    private List<Card> deck;
    private List<Card> hand;
    private List<Card> board;
    private List<Move> movesInRound;
    private Game game;
    private int punishment;
    private List<Move> avaliableMoves;
    
    public DefaultHero(Game game, List<Card> initialDeck, int initialHandSize) {
        this.game = game;
        round = 0;
        punishment=0;
        deck = initialDeck;
        initHand(initialHandSize-1);
        board = new ArrayList<>();
        movesInRound = new ArrayList<>();
        health = 20;
        mana = 40;
    }

    public void startRound() {
    	activate();
        increaseMana();
        pickCardFromDeck();
        notifyIfDeadHero();
        generateAvaliableMoves();
    }

    private void activate() {
		for(Card c:board)
			((Minion) c).setActive(true);
		
	}

	/**
     * Depending on implementation - this method may contain AI algorithm, user's actions or hardcoded action.
     * Default hero does nothing. :) useless boy
     */
    public boolean performMove(Move toDo) {
    	if(avaliableMoves.contains(toDo))
    	{
    		toDo.performMove();
    		generateAvaliableMoves();
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }

    private void generateAvaliableMoves()
    {
    	avaliableMoves=possibleMoves();
    }
    
    private  List<Move> addSpellMoves(int index)
    {
    	 List<Move> res= new ArrayList<>();
    	 Spell spell=(Spell) hand.get(index);
    	 Hero enymy=game.getEnemyOf(this);
    	if(spell instanceof Fireball) {
    		
    		List<Card> cards=enymy.getBoard();
    		for(Card card:cards)
    		{
    			res.add(new UseSpell(index, this, enymy, (Minion) card, null)); 
    		}
    		res.add(new UseSpell(index, this, enymy, null, enymy)); 
    	}
    	
  	  	if(spell instanceof HealingTouch)
  	  	{
  	  		for(Card card:board)
  	  		{
  	  			res.add(new UseSpell(index, this, enymy, (Minion) card, null)); 
  	  		}
  	  		res.add(new UseSpell(index, this, enymy, null, this)); 
  	  	}
  	  	
  	  	if(spell instanceof DeadlyShot)
  	  	{
  	  		if(enymy.getBoard().size()>0)
  	  			res.add(new UseSpell(index, this, enymy, null, null)); 
	  		
	  	}
  	  	return res;
    }
    
    /**
     * Returns list of all possible moves for hero at the moment, keeping the mana constraints.
     */
    public List<Move> possibleMoves() {
        List<Move> possibleMoves = new ArrayList<>();

        for(int cardInHandIndex=0; cardInHandIndex<hand.size(); cardInHandIndex++) {
            if(hand.get(cardInHandIndex).getCost() <= mana && hand.get(cardInHandIndex) instanceof Minion) {
                possibleMoves.add(new PutCard(cardInHandIndex, this,  game.getEnemyOf(this)));
            }
            if(hand.get(cardInHandIndex).getCost() <= mana && hand.get(cardInHandIndex) instanceof Spell)
            	possibleMoves.addAll(addSpellMoves(cardInHandIndex));
        }

        // TODO - verify if performing attack should not use mana
        // TODO - remember to deactivate minion after usage and activate it after round
        // TODO - remember to remove minion after death
        // TODO - remember to remove spell after usage


        for(int cardOnBoardIndex=0; cardOnBoardIndex<board.size(); cardOnBoardIndex++) {
        	if(((Minion) board.get(cardOnBoardIndex)).isActive()) {
        		possibleMoves.add(new AttackHero(cardOnBoardIndex, board, game.getEnemyOf(this)));
        		for(int cardOnEnemyBoardIndex=0; cardOnEnemyBoardIndex<game.getEnemyOf(this).getBoard().size(); cardOnEnemyBoardIndex++) {
        			possibleMoves.add(new AttackMinion(cardOnBoardIndex, board, game.getEnemyOf(this).getBoard().get(cardOnEnemyBoardIndex)));
        		}
        	}
        }

        return possibleMoves;
    }

    public void endRound() {
        resetMovesInRound();
        notifyAboutRoundEnd();
    }

    @Override
    public boolean isDead() {
        return health <= 0;
    }

    public void receiveDamage(int damage) {
        health-=damage;
        notifyIfDeadHero();
    }

    private void notifyIfDeadHero() {
        if(isDead()) {
            notifyAboutDeadHero();
        }
    }

    public void deadMinionNotification(Minion minion) {
        board.remove(minion);
    }

    private void resetMovesInRound() {
        movesInRound.removeAll(movesInRound); // TODO verify whether x.removeAll(x) is correct
    }

    private void notifyAboutDeadHero() {
        game.deadHeroNotification();
    }

    private void notifyAboutRoundEnd() {
        game.roundEndNotification();
    }


    private void increaseMana() {
        if(mana<10) {
            mana++;
        }
    }
    @Override
	public void manaDecrease(int hm) {
		if(mana>=hm)
			mana-=hm;
		
	}
    
    @Override
	public void increaseHealth(int hm) {
		health+=hm;
		if(health>20)
			health=20;
		
	}


    private void initHand(int initialHandSize) {
        hand = new ArrayList<>();
        for(int i=0; i<initialHandSize; i++) {
            pickCardFromDeck();
        }
    }

    private void pickCardFromDeck() {
        // TODO verify impl
        if(deck.isEmpty()) {
            punishForEmptyDeck++;
            health=-punishForEmptyDeck; // TODO verify whether =- is correct
            return;
        }

        if(hand.size() == 7) {
            return;
        }

        hand.add(deck.get(0));
        deck.remove(deck.get(0)); // TODO - may produce NPE on line above because i'm not sure whether after remove from deck it will change indexes
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getPunishForEmptyDeck() {
        return punishForEmptyDeck;
    }

    public void setPunishForEmptyDeck(int punishForEmptyDeck) {
        this.punishForEmptyDeck = punishForEmptyDeck;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public List<Card> getBoard() {
        return board;
    }

    public void setBoard(List<Card> board) {
        this.board = board;
    }

    public List<Move> getMovesInRound() {
        return movesInRound;
    }

    public void setMovesInRound(List<Move> movesInRound) {
        this.movesInRound = movesInRound;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

	public List<Move> getAvaliableMoves() {
		return avaliableMoves;
	}

	public void setAvaliableMoves(List<Move> avaliableMoves) {
		this.avaliableMoves = avaliableMoves;
	}

}
