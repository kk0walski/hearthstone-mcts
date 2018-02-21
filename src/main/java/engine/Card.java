package engine;

public interface Card {

    Card deepCopy();
    void doAction(Hero owner, Hero enemy);
    int getCost();
    Hero getOwner();
    void setOwner(Hero owner);
}
