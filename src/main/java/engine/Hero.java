package engine;

import engine.cards.Minion;
import engine.heroes.AbstractHero;
import engine.mcts.Node;

import java.util.List;

public interface Hero {

    boolean isDead();

    boolean performMove(Move moveToDo);

    void receiveDamage(int damage);

    List<Card> getBoard();

    List<Card> getDeck();

    void deadMinionNotification(Minion minion);

    Game getGame();

    void setGame(Game resoult);

    List<Card> getHand();

    int getMana();

    void decreaseMana(int value);

    int increaseHealth(int value);

    void startRound();

    int getHealth();

    int getPunishForEmptyDeck();

    void generateAvailableMoves();

    List<Move> getAvailableMoves();

    void setAvailableMoves(List<Move> copyMovesTo);

    void endRound();

    String getName();

    Hero deepCopy(Node root);

    List<Move> copyMovesTo(Node root, AbstractHero target, List<Move> toCopy);

    void revertDamage(int damage);

    void revertDeadMinionNotification(Minion minion);

    void decreaseHealth(int value);

    void increaseMana(int value);

    void revertStartRound();

    void chooseRandomSimulationalMove();

    void rollback(Move moveToRevert);
}
