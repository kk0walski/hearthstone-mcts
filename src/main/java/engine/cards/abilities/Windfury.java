package engine.cards.abilities;

import engine.cards.Ability;

/** If a minion has Windfury, it will be able to attack twice in one turn.
 *
 * A minion with Windfury can attack once and then any other action can take place before the Hero choses to attack again.
 *
 * For example a minion with Windfury can attack once, then be healed, then attack again.
 *
 * If a minion has Windfury and it's Health is reduced to zero after attacking once, it will not be able to attack again.
 *
 * If a minion has already attacked once, and then is granted Windfury, it will not be able to attack again on the same turn, the minion must be granted Windfury, before it attacks.
 *
 * A minion with Windfury cannot attack on the turn it was played unless it has Charge.
*/
public class Windfury implements Ability {
}
