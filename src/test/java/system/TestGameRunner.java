package system;

import engine.Game;
import engine.Hero;
import engine.moves.UseSpell;

public class TestGameRunner {

    public static void main(String[] args) {
        Game g = new Game();
        g.initializeAndStartStandardGame();
        Hero h = g.getActiveHero();
        h.startRound();
        System.out.println(h.performMove(new UseSpell(0, h, g.getEnemyOf(h), null, g.getEnemyOf(h))));
        System.out.println(h.performMove(new UseSpell(1, h, g.getEnemyOf(h), null, g.getEnemyOf(h))));
        System.out.println(h.performMove(new UseSpell(2, h, g.getEnemyOf(h), null, g.getEnemyOf(h))));

        System.out.println();

    }
}
