package engine.heroes;

import engine.Card;
import engine.Game;
import engine.Hero;
import engine.Move;
import engine.moves.AttackHero;
import engine.moves.AttackMinion;
import engine.moves.PutCard;

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

    public DefaultHero(Game game, List<Card> initialDeck, int initialHandSize) {
        this.game = game;
        round = 0;
        deck = initialDeck;
        initHand(initialHandSize);
        board = new ArrayList<>();
        movesInRound = new ArrayList<>();
        health = 20;
        mana = 0;
    }

    public void startRound() {
        increaseMana();
        pickCardFromDeck();
        notifyIfDeadHero();
    }

    /**
     * Depending on implementation - this method may contain AI algorithm, user's actions or hardcoded action.
     * Default hero does nothing. :) useless boy
     */
    public void performMoves() {

    }

    /**
     * Returns list of all possible moves for hero at the moment, keeping the mana constraints.
     */
    public List<Move> possibleMoves() {
        List<Move> possibleMoves = new ArrayList<>();
        // foreach spell and minion in hand, check if this.mana lets for spell usage
        for(int cardInHandIndex=0; cardInHandIndex<hand.size(); cardInHandIndex++) {
            if(hand.get(cardInHandIndex).getCost() <= mana) {
                possibleMoves.add(new PutCard(cardInHandIndex, hand, board));
            }
        }

        for(int cardOnBoardIndex=0; cardOnBoardIndex<board.size(); cardOnBoardIndex++) {
            possibleMoves.add(new AttackHero(cardOnBoardIndex, board, game.getEnemyOf(this)));
            for(int cardOnEnemyBoardIndex=0; cardOnEnemyBoardIndex<game.getEnemyOf(this).getBoard().size(); cardOnEnemyBoardIndex++) {
                possibleMoves.add(new AttackMinion(cardOnBoardIndex, board, game.getEnemyOf(this).getBoard().get(cardOnEnemyBoardIndex)));
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
}
