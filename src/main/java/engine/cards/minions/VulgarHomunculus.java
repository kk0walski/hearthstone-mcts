package engine.cards.minions;

import engine.cards.Minion;
import engine.cards.abilities.Battlecry;
import engine.cards.abilities.BattlecryDealDamageToSelfHero;

public class VulgarHomunculus extends Minion {

    public VulgarHomunculus() {
        setAttack(2);
        setCost(2);
        setHealth(4);
        setName("Vulgar Homunculus");
        setAbility(new BattlecryDealDamageToSelfHero());
    }
}
