package engine.moves;

import engine.Card;
import engine.Hero;
import engine.Move;
import engine.cards.Minion;

import java.util.List;

public class AttackHero implements Move {

    private int cardInBoardIndex;
    private List<Card> board;
    private Hero heroToGetAttacked;

    public AttackHero(int cardInBoardIndex, List<Card> board, Hero heroToGetAttacked) {
        this.cardInBoardIndex = cardInBoardIndex;
        this.board = board;
        this.heroToGetAttacked = heroToGetAttacked;
    }

    @Override
    public void performMove() {
        ((Minion) board.get(cardInBoardIndex)).attack(heroToGetAttacked);
    }
}
