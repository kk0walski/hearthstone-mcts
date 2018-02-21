package engine.cards.minions;

import engine.cards.Minion;
import engine.cards.abilities.BattlecryDealDamageToSelfHero;

public class RiverCrocolisk extends Minion {

    public RiverCrocolisk() {
        setAttack(2);
        setCost(2);
        setHealth(3);
        setName("River Crocolisk");
        //setAbility(new BattlecryDealDamageToSelfHero());
    }
}
