package engine;

import engine.cards.Minion;

public interface Card {

    Card deepCopy();

    void doAction(Hero owner, Hero enemy, Hero targetHero, Minion targetMinion);

    int getCost();

    void setOwner(Hero owner);
}
