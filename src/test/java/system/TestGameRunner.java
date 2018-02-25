package system;

import engine.Card;
import engine.Game;
import engine.Hero;
import org.hamcrest.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import engine.cards.CardsHelper;
import engine.cards.minions.Treant;
import engine.moves.UseSpell;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

public class TestGameRunner {

    @Test
    public void testGameWithSingleTreant() {

    }

    private List<Card> generateShuffledDeckWithSingleCard(Card card) {
        return TestCardsHelper.generateShuffledDeck(TestCardsHelper.deckWithSingleCard(card));
    }

    @Test
    public void simpleTestGameV2() {

    }

    @Test
    public void simpleTestGameV3() {

    }

}
