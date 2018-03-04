package engine.heroes;

import engine.Card;
import engine.Game;
import engine.Move;
import engine.cards.Minion;
import engine.cards.Spell;
import engine.cards.spells.HealingTouch;
import engine.moves.*;

import java.util.List;

public class PassiveHero extends AbstractHero implements HeuristicHero {

    public PassiveHero(Game game, String name, List<Card> initialDeck, int initialHandSize) {
        super(game, name, initialDeck, initialHandSize);
    }

    @Override
    public void chooseHeuristicMove() {
        Move toDo = null;
        while (!(toDo instanceof EndRound)) {
            int useless = 0;
            int bestFound = -1;
            int bestFoundValue = Integer.MIN_VALUE;
            for (int index = 0; index < availableMoves.size(); index++) {
                int currentValue = evaluate(availableMoves.get(index));
                if (currentValue > bestFoundValue) {
                    bestFound = index;
                    bestFoundValue = currentValue;
                }
            }
            toDo = availableMoves.get(bestFound);
            performMove(toDo);
        }
    }

    @Override
    public int evaluate(Move toDo) {
        if (toDo instanceof PutCard) {
            if (toDo.getCard() instanceof Minion) {
                return 3 * ((Minion) ((PutCard) toDo).getCard()).getAttack() +
                        ((Minion) ((PutCard) toDo).getCard()).getHealth();
            } else {
                return 4;
            }
        } else if (toDo instanceof UseSpell) {
            if(toDo.getCard() instanceof HealingTouch) {
                return 2 * toDo.getCard().getCost();
            } else {
                return 3 * toDo.getCard().getCost();
            } // we assume that cost is connected with spell's skill
        } else if (toDo instanceof AttackMinion) {
            return 3 * ((Minion) ((AttackMinion) toDo).getCard()).getAttack();
        } else if (toDo instanceof AttackHero) {
            return ((Minion) ((AttackHero) toDo).getCard()).getAttack();
        } else {
            return 1;
        }
    }
}
