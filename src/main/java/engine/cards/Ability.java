package engine.cards;


import engine.Hero;
import engine.heroes.DefaultHero;

public interface Ability {
    void performAbility(Hero self, Hero enemy);
}
