package engine.moves;

import engine.Card;
import engine.Hero;
import engine.Move;

public class EndRound implements Move {

    private Hero self;

    public EndRound() {

    }

    public EndRound(Hero self) {
        this.self = self;
    }

    @Override
    public void performMove() {
        self.endRound();
    }

    @Override
    public boolean isMovePossible() {
        return true;
    }

    @Override
    public Card getCard() {
        return null;
    }

    public Hero getSelf() {
        return self;
    }

    public void setSelf(Hero self) {
        this.self = self;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EndRound endRound = (EndRound) o;

        return self != null ? self.equals(endRound.self) : endRound.self == null;
    }

    @Override
    public int hashCode() {
        return self != null ? self.hashCode() : 0;
    }
}
