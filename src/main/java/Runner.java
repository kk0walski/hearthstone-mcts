import engine.Game;
import engine.Hero;
import engine.moves.UseSpell;

public class Runner {

    public static void main(String[] args) {
        Game game = new Game();
        game.initializeAndStartStandardGame();
        Hero hero = game.getActiveHero();
        hero.startRound();
        System.out.println(hero.performMove(new UseSpell(0, hero, game.getEnemyOf(hero), null, game.getEnemyOf(hero))));
        System.out.println(hero.performMove(new UseSpell(1, hero, game.getEnemyOf(hero), null, game.getEnemyOf(hero))));
        System.out.println(hero.performMove(new UseSpell(2, hero, game.getEnemyOf(hero), null, game.getEnemyOf(hero))));

        System.out.println();
    }
}