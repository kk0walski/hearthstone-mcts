import engine.Game;
import engine.Hero;
import engine.cards.minions.MassiveGnoll;
import engine.cards.minions.Tabbycat;
import engine.cards.spells.Fireball;
import engine.moves.UseSpell;

public class Runner {

    public static void main(String[] args) {
        Game game = new Game();
        game.initializeAndStartStandardGame();
        Hero hero = game.getActiveHero();
        game.getEnemyOf(game.getActiveHero()).getBoard().add(new MassiveGnoll());
        game.getEnemyOf(game.getActiveHero()).getBoard().add(new Tabbycat());
        game.getActiveHero().getBoard().add(new MassiveGnoll());
        game.getActiveHero().getHand().add(new Fireball());
        hero.startRound();
        System.out.println(hero.performMove(new UseSpell(0, hero, game.getEnemyOf(hero), null, game.getEnemyOf(hero))));


        //Game g = game.deepCopy();
        System.out.println();
    }
}