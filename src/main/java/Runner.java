import engine.Game;
import engine.Hero;
import engine.moves.AttackHero;
import engine.moves.PutCard;
import engine.moves.UseSpell;

public class Runner {

    public static void main(String[] args) {
    Game g=new Game();
    g.initializeStandardBoard();
    Hero h=g.getActiveHero();
    h.startRound();
    System.out.println(h.performMove(new UseSpell(0, h, g.getEnemyOf(h),null,g.getEnemyOf(h))));
    System.out.println(h.performMove(new UseSpell(1, h, g.getEnemyOf(h),null,g.getEnemyOf(h))));
    System.out.println(h.performMove(new UseSpell(2, h, g.getEnemyOf(h),null,g.getEnemyOf(h))));
    g.getEnemyOf(h).setHealth(0);
    g.checkForGameEnd();
    System.out.println(g.getWinner());
    }
}
