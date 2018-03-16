package engine.cards.abilities;

import java.util.List;

import engine.Card;
import engine.Game;
import engine.Hero;
import engine.cards.Ability;
import engine.cards.minions.Tabbycat;
import engine.heroes.DefaultHero;

public class BattleCrySummonTabbycat implements Ability {

    private boolean wasTabbycatSummoned = false;
    private Tabbycat summonedTabbycat;

    @Override
    public void performAbility(Hero self, Hero enemy) {
        List<Card> cardsOnBoardOfGivenHero = self.getBoard();
        if (cardsOnBoardOfGivenHero.size() < Game.MAXIMUM_CARDS_ON_BOARD) {
            summonedTabbycat = new Tabbycat();
            cardsOnBoardOfGivenHero.add(summonedTabbycat);
            wasTabbycatSummoned = true;
        }
    }

    @Override
    public void revertAbility(Hero self, Hero enemy) {
        if (wasTabbycatSummoned) {
            self.getBoard().remove(summonedTabbycat);
        }
    }

}
