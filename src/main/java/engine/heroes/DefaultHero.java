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
import engine.moves.*;

import java.util.ArrayList;
import java.util.List;

public class DefaultHero implements Hero {

    public static final int MAXIMUM_HAND_SIZE = 7;
    public static final int MAXIMUM_HEALTH_POINTS = 20;
    public static final int MAXIMUM_MANA_POINTS = 10;
    public static final int INITIAL_HEALTH_POINTS = 1;
    public static final int INITIAL_MANA_POINTS = 40;
    public static final int INITIAL_PUNISH_FOR_EMPTY_DECK = 0;
    public static final int INITIAL_ROUND_NUMBER = 0;

    private String name;
    private int health;
    private int mana;
    private int round;
    private int punishForEmptyDeck;
    private List<Card> deck;
    private List<Card> hand;
    private List<Card> board;
    private List<Move> movesInRound;
    private Game game;
    private List<Move> availableMoves;

    public DefaultHero(Game game, String name, List<Card> initialDeck, int initialHandSize) {
        this.game = game;
        this.name = name;
        round = INITIAL_ROUND_NUMBER;
        punishForEmptyDeck = INITIAL_PUNISH_FOR_EMPTY_DECK;
        deck = initialDeck;
        initHand(initialHandSize - 1); //TODO verify whether -1 is correct
        board = new ArrayList<>();
        movesInRound = new ArrayList<>();
        availableMoves = new ArrayList<>();
        health = INITIAL_HEALTH_POINTS;
        mana = INITIAL_MANA_POINTS;
    }

    public void startRound() {
        activateMinionsOnBoard();
        increaseMana();
        pickCardFromDeck();
        notifyIfDeadHero();
        generateAvailableMoves();
    }

    private void activateMinionsOnBoard() {
        for (Card c : board)
            ((Minion) c).setActive(true);
    }

    /**
     * Depending on implementation - this method may contain AI algorithm, user's actions or hardcoded action.
     */
    public boolean performMove(Move moveToDo) {
        if (availableMoves.contains(moveToDo)) {
            if (moveToDo instanceof EndRound) {
                endRound();
                return true;
            }
            moveToDo.performMove();
            movesInRound.add(moveToDo);
            generateAvailableMoves();
            notifyIfDeadHero();
            return true;
        } else {
            return false;
        }
    }

    public void generateAvailableMoves() {
        availableMoves = possibleMoves();
    }

    private List<Move> addSpellMoves(int cardInHandIndex) {
        List<Move> resultMovesList = new ArrayList<>();
        Spell spell = (Spell) hand.get(cardInHandIndex);
        Hero enemy = game.getEnemyOf(this);

        if (spell instanceof Fireball) {
            List<Card> enemyBoard = enemy.getBoard();

            for (Card enemyCard : enemyBoard) {
                resultMovesList.add(new UseSpell(cardInHandIndex, this, enemy, (Minion) enemyCard, null));
            }

            resultMovesList.add(new UseSpell(cardInHandIndex, this, enemy, null, enemy));
        }

        if (spell instanceof HealingTouch) {
            for (Card card : board) {
                resultMovesList.add(new UseSpell(cardInHandIndex, this, enemy, (Minion) card, null));
            }
            resultMovesList.add(new UseSpell(cardInHandIndex, this, enemy, null, this));
        }

        if (spell instanceof DeadlyShot) {
            if (enemy.getBoard().size() > 0)
                resultMovesList.add(new UseSpell(cardInHandIndex, this, enemy, null, null));

        }
        return resultMovesList;
    }

    /**
     * Returns list of all possible moves for hero at the moment, keeping the mana constraints.
     */
    public List<Move> possibleMoves() {
        List<Move> possibleMoves = new ArrayList<>();

        for (int cardInHandIndex = 0; cardInHandIndex < hand.size(); cardInHandIndex++) {
            if (hand.get(cardInHandIndex).getCost() <= mana && hand.get(cardInHandIndex) instanceof Minion) {
                possibleMoves.add(new PutCard(cardInHandIndex, this, game.getEnemyOf(this)));
            }

            if (hand.get(cardInHandIndex).getCost() <= mana && hand.get(cardInHandIndex) instanceof Spell)
                possibleMoves.addAll(addSpellMoves(cardInHandIndex));
        }

        // TODO - remember to remove spell after usage

        for (int cardOnBoardIndex = 0; cardOnBoardIndex < board.size(); cardOnBoardIndex++) {
            if (((Minion) board.get(cardOnBoardIndex)).isActive()) {
                possibleMoves.add(new AttackHero(cardOnBoardIndex, board, game.getEnemyOf(this)));
                for (int cardOnEnemyBoardIndex = 0; cardOnEnemyBoardIndex < game.getEnemyOf(this).getBoard().size(); cardOnEnemyBoardIndex++) {
                    possibleMoves.add(new AttackMinion(cardOnBoardIndex, board, game.getEnemyOf(this).getBoard().get(cardOnEnemyBoardIndex)));
                }
            }
        }

        possibleMoves.add(new EndRound(this));

        return possibleMoves;
    }

    /**
     * Should be invoked after round.
     */
    public void endRound() {
        if (game.getActiveHero() == null) {
            return;
        }
        if (!(game.getActiveHero().equals(this))) {
            throw new IllegalStateException("Only active hero can end round.");
        }

        resetMovesInRound();
        notifyAboutRoundEnd();
    }

    @Override
    public boolean isDead() {
        return health <= 0;
    }

    public void receiveDamage(int damage) {
        health = health - damage;
        notifyIfDeadHero();
    }

    private void notifyIfDeadHero() {
        if (isDead()) {
            notifyAboutDeadHero();
        }
    }

    public void deadMinionNotification(Minion minion) {
        board.remove(minion);
    }

    private void resetMovesInRound() {
        movesInRound.removeAll(movesInRound);
    }

    private void notifyAboutDeadHero() {
        game.deadHeroNotification();
    }

    private void notifyAboutRoundEnd() {
        game.roundEndNotification();
    }


    private void increaseMana() {
        if (mana < MAXIMUM_MANA_POINTS) {
            mana++;
        }
    }

    @Override
    public void decreaseMana(int value) {
        mana -= value;
    }

    @Override
    public void increaseHealth(int value) {
        health += value;
        if (health > MAXIMUM_HEALTH_POINTS)
            health = MAXIMUM_HEALTH_POINTS;
    }


    private void initHand(int initialHandSize) {
        hand = new ArrayList<>();
        for (int i = 0; i < initialHandSize; i++) {
            pickCardFromDeck();
        }
    }

    private void pickCardFromDeck() {
        if (deck.isEmpty()) {
            punishForEmptyDeck++;
            health = health - punishForEmptyDeck;
            return;
        }

        if (hand.size() == MAXIMUM_HAND_SIZE) {
            return;
        }

        hand.add(deck.get(0));
        hand.get(hand.size() - 1).setOwner(this);
        deck.remove(deck.get(0));
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

    public List<Move> getAvailableMoves() {
        return availableMoves;
    }

    public void setAvailableMoves(List<Move> availableMoves) {
        this.availableMoves = availableMoves;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
