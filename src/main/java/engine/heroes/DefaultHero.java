package engine.heroes;

import engine.Card;
import engine.Game;

import java.util.List;

public class DefaultHero extends AbstractHero {

    public DefaultHero(Game game, String name, List<Card> initialDeck, int initialHandSize) {
        super(game, name, initialDeck, initialHandSize);
        // TODO Auto-generated constructor stub
    }

    public DefaultHero() {
        super(null, null, null, -1);
    }
}
