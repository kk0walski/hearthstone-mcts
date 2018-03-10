package engine.cards;

import engine.Card;
import engine.Hero;

public class Spell implements Card {

    private String name;
    private int cost;
    private Hero owner;

    /**
     * Does NOT copy whole owner object, only reference.
     *
     * @return copy of spell
     */
    @Override
    public Card deepCopy() {
        Spell copy = null;
        try {
            copy = this.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        copy.setName(name);
        copy.setCost(cost);
        copy.setOwner(owner);

        return copy;
    }

    @Override
    public void doAction(Hero owner, Hero enemy, Hero targetHero, Minion targetMiniony) {
        // blank default action for spell
    }

    public void revertSpell(Hero owner, Hero enemy, Hero targetHero, Minion targetMinion) {
        // blank default action for revert spell
    }

    public int getCost() {
        return cost;
    }

    @Override
    public Hero getOwner() {
        return null;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setOwner(Hero owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
