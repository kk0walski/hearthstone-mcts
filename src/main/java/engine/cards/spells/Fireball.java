package engine.cards.spells;

import engine.Hero;
import engine.cards.Minion;
import engine.cards.Spell;

/**
 * Deal 1 damage.
 */
public class Fireball extends Spell {

    public Fireball() {
        setCost(4);
        setName("Fireball");
    }
    
    @Override
    public void doAction(Hero owner, Hero enemy, Hero tergetHero, Minion targetMinion) {
        if(tergetHero!=null)
        {
        	tergetHero.receiveDamage(8);
        }
        if(targetMinion!=null)
        {
        	targetMinion.receiveDamage(8);
        }
    }
}
