package engine.heroes;

import java.util.ArrayList;
import java.util.List;

import engine.Card;
import engine.Game;
import engine.Hero;
import engine.Move;
import engine.cards.Minion;
import engine.cards.Spell;
import engine.cards.spells.DeadlyShot;
import engine.cards.spells.Fireball;
import engine.cards.spells.HealingTouch;
import engine.mcts.Node;
import engine.moves.AttackHero;
import engine.moves.AttackMinion;
import engine.moves.EndRound;
import engine.moves.PutCard;
import engine.moves.UseSpell;

public abstract class AbstractHero implements Hero {

    public static final int MAXIMUM_HAND_SIZE = 7;
    public static final int MAXIMUM_HEALTH_POINTS = 20;
    public static final int MAXIMUM_MANA_POINTS = 40; // todo defaultowo powinno byc 10
    public static final int INITIAL_HEALTH_POINTS = 10;
    public static final int INITIAL_MANA_POINTS = 0;
    public static final int INITIAL_PUNISH_FOR_EMPTY_DECK = 0;
    public static final int INITIAL_ROUND_NUMBER = 0;

    protected String name;
    protected int health;
    protected int mana;
    private int increasedMana;
    protected int round;
    protected int punishForEmptyDeck;
    private Card lastPickedCardBackup;
    protected List<Card> deck;
    protected List<Card> hand;
    protected List<Card> board;
    private List<Move> movesInRoundBackup;
    protected Game game;
    protected List<Move> availableMoves;
    private List<Move> availableMovesBackup;
    protected List<Card> activatedMinions;

    public AbstractHero(Game game, String name, List<Card> initialDeck, int initialHandSize) {
        this.game = game;
        this.name = name;
        round = INITIAL_ROUND_NUMBER;
        punishForEmptyDeck = INITIAL_PUNISH_FOR_EMPTY_DECK;
        deck = initialDeck;
        initHand(initialHandSize - 1); //TODO verify whether -1 is correct
        board = new ArrayList<>();
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

    @Override
    public void revertStartRound() {
        deactivateMinionsOnBoard();
        decreaseMana(increasedMana);
        revertPreviousPickCardFromDeck();
        revertDeadHeroNotification();
        revertAvailableMovesGeneration();
    }

    private void activateMinionsOnBoard() {
        activatedMinions = new ArrayList<>();
        for (Card c : board) {
            if (!((Minion) c).isActive()) {
                activatedMinions.add(c);
                ((Minion) c).setActive(true);
            }
        }
    }

    private void deactivateMinionsOnBoard() {
        for (Card c : activatedMinions) {
            ((Minion) c).setActive(false);
        }
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
            generateAvailableMoves();
            notifyIfDeadHero();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void rollback(Move moveToRevert) {
        moveToRevert.rollback();
        revertAvailableMovesGeneration();
        revertDeadHeroNotification();
    }

    public void generateAvailableMoves() {
        availableMovesBackup = new ArrayList<>(availableMoves);
        availableMoves = possibleMoves();
    }

    private void revertAvailableMovesGeneration() {
        availableMoves = availableMovesBackup;
    }

    private List<Move> addSpellMoves(int cardInHandIndex) {
        List<Move> resultMovesList = new ArrayList<>();
        Spell spell = (Spell) hand.get(cardInHandIndex);
        Hero enemy = game.getEnemyOf(this);

        if (spell instanceof Fireball) {
            List<Card> enemyBoard = enemy.getBoard();

            for (int i = 0; i < enemyBoard.size(); i++) {
                Card enemyCard = enemyBoard.get(i);
                resultMovesList.add(new UseSpell(cardInHandIndex, this, enemy, (Minion) enemyCard, i));
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

        for (int cardOnBoardIndex = 0; cardOnBoardIndex < board.size(); cardOnBoardIndex++) {
            if (((Minion) board.get(cardOnBoardIndex)).isActive()) {
                possibleMoves.add(new AttackHero(cardOnBoardIndex, board, game.getEnemyOf(this)));
                for (int cardOnEnemyBoardIndex = 0; cardOnEnemyBoardIndex < game.getEnemyOf(this).getBoard().size(); cardOnEnemyBoardIndex++) {
                    possibleMoves.add(new AttackMinion(cardOnBoardIndex, board, game.getEnemyOf(this).getBoard().get(cardOnEnemyBoardIndex), cardOnEnemyBoardIndex));

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
            // throw new IllegalStateException("Only active hero can end round.");
        }

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

    public void revertDamage(int damage) {
        health = health + damage;
    }

    protected void notifyIfDeadHero() {
        if (isDead()) {
            notifyAboutDeadHero();
        }
    }

    protected void notifyIfAliveHero() {
        if (isDead()) {
            notifyAboutDeadHero();
        }
    }

    protected void revertDeadHeroNotification() {
        game.checkForGameEnd(); // todo - dodane swiezo
        if (game.isGameOver()) {
            game.setGameOver(false);
            game.setWinner(null);
        }
    }

    public void deadMinionNotification(Minion minion) {
        board.remove(minion);
    }

    public void revertDeadMinionNotification(Minion minion) {
        board.add(minion);
    }

    private void notifyAboutDeadHero() {
        game.deadHeroNotification(this);
    }

    private void notifyAboutRoundEnd() {
        game.roundEndNotification();
    }


    private void increaseMana() {
        if (mana < MAXIMUM_MANA_POINTS) {
            mana++;
            increasedMana = 1;
        } else {
            increasedMana = 0;
        }
    }

    @Override
    public void decreaseMana(int value) {
        mana -= value;
    }

    @Override
    public void increaseMana(int value) {
        mana += value;
        if (mana > MAXIMUM_MANA_POINTS) {
            mana = MAXIMUM_MANA_POINTS;
        }
    }

    @Override
    public int increaseHealth(int value) {
        int oldHealth = health;

        health += value;

        return value;
    }

    @Override
    public void decreaseHealth(int value) {
        health -= value;
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
            lastPickedCardBackup = null;
            return;
        }

        if (hand.size() == MAXIMUM_HAND_SIZE) {
            lastPickedCardBackup = null;
            return;
        }

        lastPickedCardBackup = deck.get(0);
        hand.add(deck.get(0));
        hand.get(hand.size() - 1).setOwner(this);
        deck.remove(deck.get(0)); // TODO - may produce NPE on line above because i'm not sure whether after remove from deck it will change indexes
    }

    private void revertPreviousPickCardFromDeck() {
        if (lastPickedCardBackup == null) {
            if (punishForEmptyDeck > 1) {
                health = health + punishForEmptyDeck - 1;
                punishForEmptyDeck--; // todo dodane swiezo
            }
        } else {
            deck.add(hand.get(hand.size() - 1)); //get last card in hand - it should be lastPickedCardBackup
            hand.remove(hand.get(hand.size() - 1));
        }
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

    @Override
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

    @Override
    public Hero deepCopy(Node root) {
        AbstractHero hero = copyHeroInstance();

        hero.setName(new String(name));
        hero.setHealth(getHealth());
        hero.setMana(getMana());
        hero.setRound(getRound());
        hero.setPunishForEmptyDeck(getPunishForEmptyDeck());
        ArrayList<Card> deck = new ArrayList<>();
        for (Card c : getDeck()) {
            Card temp = c.deepCopy();
            temp.setOwner(hero);
            deck.add(temp);
        }
        hero.setDeck(deck);
        ArrayList<Card> hand = new ArrayList<>();
        for (Card c : getHand()) {
            Card temp = c.deepCopy();
            temp.setOwner(hero);
            hand.add(temp);
        }
        hero.setHand(hand);
        ArrayList<Card> board = new ArrayList<>();
        for (Card c : getBoard()) {
            Card temp = c.deepCopy();
            temp.setOwner(hero);
            board.add(temp);
        }
        hero.setBoard(board);

        return hero;
    }

    private AbstractHero copyHeroInstance() {
        AbstractHero res = null;
        if (this instanceof DefaultHero)
            res = new DefaultHero();
        if (this instanceof AgresiveHero)
            res = new AgresiveHero();
        if (this instanceof PassiveHero)
            res = new PassiveHero();
        if (this instanceof RandomHero)
            res = new RandomHero();
        if (this instanceof MctsHero)
            res = new MctsHero();
        return res;
    }

    public List<Move> copyMovesTo(Node root,AbstractHero target, List<Move> toCopy) {
        ArrayList<Move> copy = new ArrayList<>();
        for (Move m : toCopy) {
            Move newMove = null;
            Hero enemy = target.game.getEnemyOf(target);
            if (m instanceof AttackHero) {
                newMove = new AttackHero(m.getCardIndex(), target.board, enemy);
            }
            if (m instanceof AttackMinion) {
                AttackMinion source = (AttackMinion) m;
                newMove = new AttackMinion(m.getCardIndex(), target.board,
                        enemy.getBoard().get(source.getMinionIndex()), source.getMinionIndex());

            }
            if (m instanceof EndRound) {
                newMove = new EndRound();
            }
            if (m instanceof PutCard) {
                newMove = new PutCard(m.getCardIndex(), target, enemy);
            }
            if (m instanceof UseSpell) {
                UseSpell source = (UseSpell) m;

                if (source.getTargetMinion() != null) {

                    if (source.getSelf().getHand().get(source.getCardIndex()) instanceof HealingTouch)
                        newMove = new UseSpell(m.getCardIndex(), target, enemy, (Minion) target.getBoard().get(source.getMinionIndex()), source.getMinionIndex());
                    else
                        newMove = new UseSpell(m.getCardIndex(), target, enemy, (Minion) enemy.getBoard().get(source.getMinionIndex()), source.getMinionIndex());
                } else {
                    Hero targetOfSpell = null;
                    if (source.getTargetHero() != null) {
                        if (source.getTargetHero() == source.getEnemy())
                            targetOfSpell = enemy;
                        else
                            targetOfSpell = target;
                    }
                    newMove = new UseSpell(m.getCardIndex(), target, enemy, null, targetOfSpell);
                }
            }

            copy.add(newMove);

        }
        return copy;
    }

    public List<Move> getMovesInRoundBackup() {
        return movesInRoundBackup;
    }

    public void setMovesInRoundBackup(List<Move> movesInRoundBackup) {
        this.movesInRoundBackup = movesInRoundBackup;
    }

    public void chooseRandomSimulationalMove() {
        Move toDo = null;
        while (!(toDo instanceof EndRound)) {
            int bestFound = -1;
            int bestFoundValue = Integer.MIN_VALUE;
            for (int index = 0; index < availableMoves.size(); index++) {
                int currentValue = evaluate(availableMoves.get(index));
                if (currentValue > bestFoundValue) {
                    bestFound = index;
                    bestFoundValue = currentValue;
                }
            }
            toDo = availableMoves.get(bestFound);
            performMove(toDo);
        }
    }

    private int evaluate(Move toDo) {
        return (int) (Math.random() * 100);
    }
}
