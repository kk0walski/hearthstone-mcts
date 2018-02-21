package engine.cards.minions;

import engine.cards.Minion;
import engine.cards.abilities.BattleCrySummonTabbycat;

public class Alleycat extends Minion{

    public Alleycat() {
        setAttack(1);
        setCost(1);
        setHealth(1);
        setName("Alleycat");
        setAbility(new BattleCrySummonTabbycat());
    }
}
