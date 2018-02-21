package engine.cards.minions;

import engine.cards.Minion;
import engine.cards.abilities.Battlecry;
import engine.cards.abilities.BattlecryDealDamageToSelfHero;
import engine.cards.abilities.BattlecryHealSelfHero;

public class AntiqueHealbot extends Minion {

    public AntiqueHealbot() {
        setAttack(2);
        setCost(2);
        setHealth(4);
        setName("Vulgar Homunculus");
        setAbility(new BattlecryHealSelfHero());
    }
}
