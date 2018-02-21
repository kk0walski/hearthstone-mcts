package engine;

public interface Card {

    public Card deepCopy();
    public void doAction(Hero owner, Hero enemy);
    public int getCost();
}
