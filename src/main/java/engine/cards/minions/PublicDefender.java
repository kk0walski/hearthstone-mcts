package engine.cards.minions;

import engine.cards.Minion;
import engine.cards.abilities.Taunt;

public class PublicDefender extends Minion {

    public PublicDefender() {
        setAttack(0);
        setCost(2);
        setHealth(7);
        setName("Public Defender");
        setAbility(new Taunt());
    }

}