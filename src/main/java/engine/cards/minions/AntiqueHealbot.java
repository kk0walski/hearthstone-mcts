package engine.cards.minions;

import engine.cards.Minion;
import engine.cards.abilities.BattlecryHealSelfHero;

public class AntiqueHealbot extends Minion {

    public AntiqueHealbot() {
        setAttack(2);
        setCost(2);
        setHealth(4);
        setBaseHealth(4);
        setName("Antique Healbot");
        setAbility(new BattlecryHealSelfHero());
    }
}
