package engine.cards.minions;

import engine.Hero;
import engine.cards.Minion;

public class Tabbycat extends Minion {

    public Tabbycat() {
        setAttack(1);
        setCost(1);
        setHealth(1);
        setBaseHealth(1);
        setName("Tabbycat");
    }

    public Tabbycat(Hero owner) {
        this();
        setOwner(owner);
    }

}
