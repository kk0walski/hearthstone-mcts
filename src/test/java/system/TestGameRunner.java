package system;

import engine.Card;
import engine.Game;
import engine.Hero;
import engine.Move;
import engine.cards.Minion;
import engine.cards.minions.Tabbycat;
import engine.heroes.DefaultHero;
import engine.moves.AttackHero;
import engine.moves.AttackMinion;
import engine.moves.EndRound;
import engine.moves.PutCard;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TestGameRunner {

    public static List<Card> generateShuffledDeckWithSingleCard(Card card) {
        return TestCardsHelper.generateShuffledDeck(TestCardsHelper.deckWithSingleCard(card));
    }

    @Test
    public void testGameWithSingleTabbycat() {
        // --- GIVEN
        Game testGame = new Game();
        testGame.initializeAndStartCustomGame(
                generateShuffledDeckWithSingleCard(new Tabbycat()),
                1,
                generateShuffledDeckWithSingleCard(new Tabbycat()),
                1
        );
        // ---

        // --- WHEN --- ACTIVE HERO = FIRST HERO
        Hero firstHero = testGame.getActiveHero();
        Hero secondHero = testGame.getEnemyOf(firstHero);
        // ---

        // --- THEN
        assertThat(secondHero, is(not(equalTo(firstHero))));
        assertThat(firstHero, is(equalTo(testGame.getActiveHero())));
        assertThat(secondHero, is(equalTo(testGame.getEnemyOf(firstHero))));

        assertThat(firstHero.getDeck(), is(not(empty())));
        assertThat(firstHero.getDeck(), hasSize(2));
        assertThat(firstHero.getHand(), is(empty()));
        assertThat(firstHero.getBoard(), is(empty()));
        assertThat(firstHero.getMana(), is(DefaultHero.INITIAL_MANA_POINTS));
        assertThat(firstHero.getHealth(), is(DefaultHero.INITIAL_HEALTH_POINTS));
        assertThat(firstHero.isDead(), is(false));
        assertThat(firstHero.getAvailableMoves(), is(empty()));

        assertThat(secondHero.getDeck(), is(not(empty())));
        assertThat(secondHero.getDeck(), hasSize(2));
        assertThat(secondHero.getHand(), is(empty()));
        assertThat(secondHero.getBoard(), is(empty()));
        assertThat(secondHero.getMana(), is(DefaultHero.INITIAL_MANA_POINTS));
        assertThat(secondHero.getHealth(), is(DefaultHero.INITIAL_HEALTH_POINTS));
        assertThat(secondHero.isDead(), is(false));
        assertThat(secondHero.getAvailableMoves(), is(empty()));
        // ---

        // --- WHEN
        firstHero.startRound();

        Move putCardMove = new PutCard(0, firstHero, secondHero);
        Move endFirstHeroRoundMove = new EndRound(firstHero);
        Move endSecondHeroRoundMove = new EndRound(secondHero);
        // ---

        // --- THEN
        assertThat(firstHero.getDeck(), is(not(empty())));
        assertThat(firstHero.getDeck(), hasSize(1));
        assertThat(firstHero.getHand(), hasSize(1));
        assertThat(firstHero.getBoard(), is(empty()));
        assertThat(firstHero.getMana(), is(DefaultHero.INITIAL_MANA_POINTS + 1));
        assertThat(firstHero.getHealth(), is(DefaultHero.INITIAL_HEALTH_POINTS));
        assertThat(firstHero.isDead(), is(false));
        assertThat(firstHero.getAvailableMoves(), is(not(empty())));
        assertThat(firstHero.getAvailableMoves(), hasSize(2));
        assertThat(firstHero.getAvailableMoves().get(0), is(equalTo(putCardMove)));
        assertThat(firstHero.getAvailableMoves().get(1), is(equalTo(endFirstHeroRoundMove)));
        // ---

        // --- WHEN --- PUT CARD
        firstHero.performMove(firstHero.getAvailableMoves().get(0));
        // ---

        // --- THEN
        assertThat(firstHero, is(equalTo(testGame.getActiveHero())));
        assertThat(firstHero.getDeck(), is(not(empty())));
        assertThat(firstHero.getDeck(), hasSize(1));
        assertThat(firstHero.getHand(), is(empty()));
        assertThat(firstHero.getBoard(), hasSize(1));
        assertThat(((Minion) firstHero.getBoard().get(0)).isActive(), is(false));
        assertThat(firstHero.getMana(), is(DefaultHero.INITIAL_MANA_POINTS));
        assertThat(firstHero.getHealth(), is(DefaultHero.INITIAL_HEALTH_POINTS));
        assertThat(firstHero.isDead(), is(false));
        assertThat(firstHero.getAvailableMoves(), hasSize(1));
        assertThat(firstHero.getAvailableMoves().get(0), is(equalTo(endFirstHeroRoundMove)));

        assertThat(secondHero.getHealth(), is(DefaultHero.INITIAL_HEALTH_POINTS));
        assertThat(secondHero.isDead(), is(false));
        // ---

        // --- WHEN --- ACTIVE HERO = FIRST HERO
        // we can use firstHero.endRound(); or:

        firstHero.performMove(firstHero.getAvailableMoves().get(0)); // END ROUND MOVE

        // --- ACTIVE HERO = SECOND HERO

        // --- THEN
        assertThat(testGame.getActiveHero(), is(equalTo(secondHero)));
        assertThat(testGame.getActiveHero(), is(not(equalTo(firstHero))));

        assertThat(secondHero.getDeck(), is(not(empty())));
        assertThat(secondHero.getDeck(), hasSize(2));
        assertThat(secondHero.getHand(), is(empty()));
        assertThat(secondHero.getBoard(), is(empty()));
        assertThat(secondHero.getMana(), is(DefaultHero.INITIAL_MANA_POINTS));
        assertThat(secondHero.getHealth(), is(DefaultHero.INITIAL_HEALTH_POINTS));
        assertThat(secondHero.isDead(), is(false));
        assertThat(secondHero.getAvailableMoves(), is(empty()));

        assertThat(firstHero.getDeck(), is(not(empty())));
        assertThat(firstHero.getDeck(), hasSize(1));
        assertThat(firstHero.getHand(), is(empty()));
        assertThat(firstHero.getBoard(), hasSize(1));
        assertThat(((Minion) firstHero.getBoard().get(0)).isActive(), is(false));
        assertThat(firstHero.getMana(), is(DefaultHero.INITIAL_MANA_POINTS));
        assertThat(firstHero.getHealth(), is(DefaultHero.INITIAL_HEALTH_POINTS));
        assertThat(firstHero.isDead(), is(false));
        assertThat(firstHero.getAvailableMoves(), hasSize(1));
        assertThat(firstHero.getAvailableMoves(), contains(endFirstHeroRoundMove));
        // ---

        // --- WHEN
        secondHero.startRound();

        putCardMove = new PutCard(0, secondHero, firstHero);
        // ---

        // --- THEN
        assertThat(secondHero.getDeck(), is(not(empty())));
        assertThat(secondHero.getDeck(), hasSize(1));
        assertThat(secondHero.getHand(), hasSize(1));
        assertThat(secondHero.getBoard(), is(empty()));
        assertThat(secondHero.getMana(), is(DefaultHero.INITIAL_MANA_POINTS + 1));
        assertThat(secondHero.getHealth(), is(DefaultHero.INITIAL_HEALTH_POINTS));
        assertThat(secondHero.isDead(), is(false));
        assertThat(secondHero.getAvailableMoves(), is(not(empty())));
        assertThat(secondHero.getAvailableMoves(), hasSize(2));
        assertThat(secondHero.getAvailableMoves().get(0), is(equalTo(putCardMove)));
        assertThat(secondHero.getAvailableMoves().get(1), is(equalTo(endSecondHeroRoundMove)));
        // ---

        // --- WHEN --- PUT CARD
        secondHero.performMove(secondHero.getAvailableMoves().get(0));
        // ---

        // --- THEN
        assertThat(secondHero, is(equalTo(testGame.getActiveHero())));
        assertThat(secondHero.getDeck(), is(not(empty())));
        assertThat(secondHero.getDeck(), hasSize(1));
        assertThat(secondHero.getHand(), is(empty()));
        assertThat(secondHero.getBoard(), hasSize(1));
        assertThat(((Minion) secondHero.getBoard().get(0)).isActive(), is(false));
        assertThat(secondHero.getMana(), is(DefaultHero.INITIAL_MANA_POINTS));
        assertThat(secondHero.getHealth(), is(DefaultHero.INITIAL_HEALTH_POINTS));
        assertThat(secondHero.isDead(), is(false));
        assertThat(secondHero.getAvailableMoves(), hasSize(1));
        assertThat(secondHero.getAvailableMoves(), contains(endSecondHeroRoundMove));

        assertThat(secondHero.getHealth(), is(DefaultHero.INITIAL_HEALTH_POINTS));
        assertThat(secondHero.isDead(), is(false));
        // ---

        // --- WHEN --- ACTIVE HERO = SECOND HERO
        // WE CAN USE secondHero.endRound(); OR
        secondHero.performMove(secondHero.getAvailableMoves().get(0)); // END ROUND MOVE
        // --- ACTIVE HERO = FIRST HERO

        // --- THEN
        assertThat(testGame.getActiveHero(), is(equalTo(firstHero)));
        assertThat(testGame.getActiveHero(), is(not(equalTo(secondHero))));
        // ---

        // --- WHEN
        firstHero.startRound();

        putCardMove = new PutCard(0, firstHero, secondHero);
        Move attackHeroMove = new AttackHero(0, firstHero.getBoard(), secondHero);
        Move attackMinionMove = new AttackMinion(0, firstHero.getBoard(), secondHero.getBoard().get(0), 0);
        // ---

        // --- THEN
        assertThat(firstHero.getDeck(), is(empty()));
        assertThat(firstHero.getHand(), hasSize(1));
        assertThat(firstHero.getBoard(), hasSize(1));
        assertThat(firstHero.getMana(), is(DefaultHero.INITIAL_MANA_POINTS + 1));
        assertThat(firstHero.getHealth(), is(DefaultHero.INITIAL_HEALTH_POINTS));
        assertThat(firstHero.isDead(), is(false));
        assertThat(firstHero.getAvailableMoves(), is(not(empty())));
        assertThat(firstHero.getAvailableMoves(), hasSize(4));
        assertThat(firstHero.getAvailableMoves(), contains(putCardMove, attackHeroMove, attackMinionMove, endFirstHeroRoundMove));
        // ---

        // --- WHEN --- ATTACK HERO
        firstHero.performMove(firstHero.getAvailableMoves().get(1));
        // ---

        // --- THEN
        assertThat(firstHero.getDeck(), is(empty()));
        assertThat(firstHero.getHand(), hasSize(1));
        assertThat(firstHero.getBoard(), hasSize(1));
        assertThat(firstHero.getMana(), is(DefaultHero.INITIAL_MANA_POINTS + 1));
        assertThat(firstHero.getHealth(), is(DefaultHero.INITIAL_HEALTH_POINTS));
        assertThat(firstHero.isDead(), is(false));
        assertThat(firstHero.getAvailableMoves(), is(not(empty())));
        assertThat(firstHero.getAvailableMoves(), hasSize(2));
        assertThat(firstHero.getAvailableMoves(), contains(putCardMove, endFirstHeroRoundMove));
        int attack_value = ((Minion)
                ((AttackHero) attackHeroMove).getBoard()
                        .get(
                                ((AttackHero) attackHeroMove).getCardInBoardIndex()
                        )
        ).getAttack();
        assertThat(secondHero.getHealth(), is(DefaultHero.INITIAL_HEALTH_POINTS - attack_value));

        assertThat(secondHero.isDead(), is(false));
        // ---

        // --- WHEN --- ACTIVE HERO = FIRST HERO
        firstHero.performMove(firstHero.getAvailableMoves().get(1)); // END ROUND MOVE
        // firstHero.endRound();
        // --- ACTIVE HERO = SECOND HERO
        // --- ATTACK MINION
        secondHero.startRound();
        secondHero.performMove(secondHero.getAvailableMoves().get(2));
        // ---

        // --- THEN
        assertThat(firstHero.getHealth(), is(DefaultHero.INITIAL_HEALTH_POINTS));
        assertThat(firstHero.getBoard(), is(empty()));
        // ---


        // --- WHEN --- ACTIVE HERO = SECOND HERO
        secondHero.endRound();
        firstHero.startRound();
        // --- ACTIVE HERO = FIRST HERO

        // --- THEN
        assertThat(firstHero.getDeck(), is(empty()));
        assertThat(firstHero.getHand(), hasSize(1));
        assertThat(firstHero.getBoard(), is(empty()));
        assertThat(firstHero.getMana(), is(DefaultHero.INITIAL_MANA_POINTS + 2));
        assertThat(firstHero.getHealth(), is(DefaultHero.INITIAL_HEALTH_POINTS - firstHero.getPunishForEmptyDeck()));
        assertThat(firstHero.isDead(), is(false));
        assertThat(firstHero.getAvailableMoves(), is(not(empty())));
        assertThat(firstHero.getAvailableMoves(), hasSize(2));

        // ---
        firstHero.performMove(endFirstHeroRoundMove);


        secondHero.startRound();
        secondHero.performMove(secondHero.getAvailableMoves().get(1));
        secondHero.endRound();

        while (!(testGame.isGameOver())) {
            firstHero.startRound();
            firstHero.performMove(endFirstHeroRoundMove);
            secondHero.startRound();
            secondHero.performMove(secondHero.getAvailableMoves().get(1));
            secondHero.performMove(endSecondHeroRoundMove);
        }

        System.out.println("The winner is " + testGame.getWinner().getName());
    }

}
