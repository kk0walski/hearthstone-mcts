package engine;

import engine.cards.Minion;

public interface Card {

    Card deepCopy();
    void doAction(Hero owner, Hero enemy, Hero tergetHero, Minion targetMinion);
    int getCost();
    Hero getOwner();
    void setOwner(Hero owner);
}
