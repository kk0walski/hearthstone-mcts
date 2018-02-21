package engine.cards;

import engine.Card;
import engine.Hero;

public class Spell implements Card {

    private int cost;

    @Override
    public Card deepCopy() {
        return null;
    }

    @Override
    public void doAction(Hero owner, Hero enemy) {
        // blank default action for spell
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
