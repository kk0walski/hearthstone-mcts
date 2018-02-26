package engine.cards.minions;

import engine.Hero;
import engine.cards.Minion;
import engine.cards.abilities.BattleCrySummonTabbycat;

/**
 * Summons Tabbycat.
 * <p>
 * https://www.hearthpwn.com/cards/49745-alleycat
 */
public class Alleycat extends Minion {

    public Alleycat() {
        setAttack(1);
        setCost(1);
        setHealth(1);
        setBaseHealth(1);
        setName("Alleycat");
        setAbility(new BattleCrySummonTabbycat());
    }
}
