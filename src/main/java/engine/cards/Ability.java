package engine.cards;


import engine.Hero;

public interface Ability {
    void performAbility(Hero self, Hero enemy);

    void revertAbility(Hero self, Hero enemy);
}
