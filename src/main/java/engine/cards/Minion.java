package engine.cards;

import engine.Card;
import engine.Hero;

public class Minion implements Card {

    private int cost;
    private int attack;
    private int health;
    private Ability ability;

    @Override
    public Card deepCopy() {
        Minion copy = new Minion();

        copy.setCost(cost);
        copy.setAttack(attack);
        copy.setHealth(health);

        return copy;
    }

    @Override
    public void doAction(Hero owner, Hero enemy) {
        // blank default action for minion
    }

    public void attack(Hero enemyHero) {
        enemyHero.receiveDamage(attack);
    }

    public void attack(Minion enemyMinion) {
        enemyMinion.receiveDamage(attack);
    }

    public void receiveDamage(int damage) {
        health-=damage;
        // TODO notify hero about death
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }
}
