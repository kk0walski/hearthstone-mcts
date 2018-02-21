package engine.cards;

import engine.Card;
import engine.Hero;

public class Minion implements Card {

    private String name;
    private int cost;
    private int attack;
    private int health;
    private Ability ability;
    private Hero owner;

    /**
     * Does NOT copy whole owner object, only reference.
     * @return copy of minion
     */
    @Override
    public Card deepCopy() {
        Minion copy = new Minion();

        copy.setName(name);
        copy.setCost(cost);
        copy.setAttack(attack);
        copy.setHealth(health);
        copy.setOwner(owner);
        copy.setAbility(ability);

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
        notifyHeroIfDeadMinion();
    }

    public void notifyHeroIfDeadMinion() {
        if(isDead()) {
            owner.deadMinionNotification(this);
        }
    }

    public boolean isDead() {
        return health <= 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public Hero getOwner() {
        return owner;
    }

    @Override
    public void setOwner(Hero owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Minion minion = (Minion) o;

        if (cost != minion.cost) return false;
        if (attack != minion.attack) return false;
        if (health != minion.health) return false;
        return ability != null ? ability.equals(minion.ability) : minion.ability == null;
    }

    @Override
    public int hashCode() {
        int result = cost;
        result = 31 * result + attack;
        result = 31 * result + health;
        result = 31 * result + (ability != null ? ability.hashCode() : 0);
        return result;
    }
}
