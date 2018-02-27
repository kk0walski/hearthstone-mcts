package gui;

import java.util.List;

import engine.Card;
import engine.Game;
import engine.Move;
import engine.cards.Minion;
import engine.cards.Spell;
import engine.cards.spells.DeadlyShot;
import engine.cards.spells.Fireball;
import engine.cards.spells.HealingTouch;
import engine.moves.AttackHero;
import engine.moves.AttackMinion;
import engine.moves.EndRound;
import engine.moves.PutCard;
import engine.moves.UseSpell;

import java.util.Scanner;

public class BaseTextGui {
    Game game;
    Scanner keyboard;

    private final int selfHero = 1;
    private final int selfMinion = 2;

    private final int enemyHero = 1;
    private final int enemyMinion = 2;

    private final int useSpell = 1;
    private final int putCard = 2;
    private final int attack = 3;
    private final int end = 4;

    public BaseTextGui(Game game) {
        super();
        this.game = game;
        keyboard = new Scanner(System.in);
    }

    public void baseInfo() {
        System.out.println("Zycie przeciwnika " + game.getEnemyOf(game.getActiveHero()).getHealth() + " mana przeciwnika " + game.getEnemyOf(game.getActiveHero()).getMana());
        System.out.println("Karty w rece przeciwnika");
        printCards(game.getEnemyOf(game.getActiveHero()).getHand());
        System.out.println("Karty na stole przeciwnika");
        printCards(game.getEnemyOf(game.getActiveHero()).getBoard());
        System.out.println();

        System.out.println("Twoje zycie " + game.getActiveHero().getHealth() + " twoja mana  " + game.getActiveHero().getMana());
        System.out.println("Karty w rece");
        printCards(game.getActiveHero().getHand());
        System.out.println("Karty na stole");
        printCards(game.getActiveHero().getBoard());
    }

    public void startGame() {
        System.out.println("Gra rozpoczeta");
        game.initializeAndStartStandardGame();
        System.out.println();
        game.getActiveHero().startRound();
        baseInfo();
    }

    public void play() {
        startGame();
        while ((game.getWinner() == null)) {
            makeMove();
            if (game.getWinner() != null)
                break;
            baseInfo();
        }

        if (game.getWinner() == game.getFirstHero()) //celowe por�wnanie referencji
            System.out.println("Brawo gracz 1 wygrywa");
        else
            System.out.println("Brawo gracz 2 wygrywa");
    }

    public void makeMove() {
        System.out.println("1.Uzyj czaru 2.Poloz karte 3.Uzyj karty ze stolu 4.Skoncz ture ");
        int move = keyboard.nextInt();
        Move m = prepareMove(move);
        boolean isMoveDone = false;
        if (m != null)
            isMoveDone = game.getActiveHero().performMove(m);
        if (isMoveDone) {
            System.out.println();
        } else {
            System.out.println("Ruch nie mozliwy do wykonania");
        }
    }

    private Move prepareMove(int move) {
        switch (move) {
            case useSpell:
                return prepareSpell();
            case putCard:
                return preparePutCard();
            case attack:
                return prepareAttack();
            case end:
                return prepareEndRound();


        }
        return null;
    }

    private EndRound prepareEndRound() {
        return new EndRound(game.getActiveHero());
    }

    private Move prepareAttack() {
        System.out.println("Wybierz karte");
        int minion = keyboard.nextInt();
        if (minion >= game.getActiveHero().getBoard().size())
            return null;
        else {
            System.out.println("Wybierz cel");
            System.out.println("1.Heros 2.Minion");
            int target = keyboard.nextInt();
            switch (target) {
                case enemyHero:
                    return new AttackHero(minion, game.getActiveHero().getBoard(), game.getEnemyOf(game.getActiveHero()));
                case enemyMinion:
                    System.out.println("Wybierz miniona");
                    int enymyMinion = keyboard.nextInt();
                    if (minion < game.getEnemyOf(game.getActiveHero()).getBoard().size())
                        return new AttackMinion(minion, game.getActiveHero().getBoard(), game.getEnemyOf(game.getActiveHero()).getBoard().get(enymyMinion));
                    else
                        return null;
            }
            return null;
        }
    }

    private PutCard preparePutCard() {
        System.out.println("Wybierz karte");
        int minion = keyboard.nextInt();
        if (game.getActiveHero().getHand().size() <= minion)
            return null;

        if (game.getActiveHero().getHand().get(minion) instanceof Minion) {
            return new PutCard(minion, game.getActiveHero(), game.getEnemyOf(game.getActiveHero()));
        } else {
            return null;
        }
    }

    private UseSpell prepareSpell() {
        System.out.println("Wybierz karte");
        int spell = keyboard.nextInt();
        if (spell >= game.getActiveHero().getHand().size())
            return null;
        if (game.getActiveHero().getHand().get(spell) instanceof Spell) {
            if (game.getActiveHero().getHand().get(spell) instanceof DeadlyShot) {
                return new UseSpell(spell, game.getActiveHero(), game.getEnemyOf(game.getActiveHero()), null, null);
            } else {
                System.out.println("Wybierz cel");
                if (game.getActiveHero().getHand().get(spell) instanceof HealingTouch) {
                    System.out.println("1.Heros 2.Minion");
                    int target = keyboard.nextInt();
                    switch (target) {
                        case selfHero:
                            return new UseSpell(spell, game.getActiveHero(), game.getEnemyOf(game.getActiveHero()), null, game.getActiveHero());
                        case selfMinion:
                            System.out.println("Wybierz miniona");
                            int minion = keyboard.nextInt();
                            if (minion < game.getActiveHero().getBoard().size())
                                return new UseSpell(spell, game.getActiveHero(), game.getEnemyOf(game.getActiveHero()), (Minion) game.getActiveHero().getBoard().get(minion), null);
                            else
                                return null;
                    }
                }

                if (game.getActiveHero().getHand().get(spell) instanceof Fireball) {
                    System.out.println("1.Heros 2.Minion");
                    int target = keyboard.nextInt();
                    switch (target) {
                        case enemyHero:
                            return new UseSpell(spell, game.getActiveHero(), game.getEnemyOf(game.getActiveHero()), null, game.getEnemyOf(game.getActiveHero()));
                        case enemyMinion:
                            System.out.println("Wybierz miniona");
                            int minion = keyboard.nextInt();
                            if (minion < game.getEnemyOf(game.getActiveHero()).getBoard().size())
                                return new UseSpell(spell, game.getActiveHero(), game.getEnemyOf(game.getActiveHero()), (Minion) game.getEnemyOf(game.getActiveHero()).getBoard().get(minion), null);
                            else
                                return null;
                    }
                }
            }
            return null;
        } else {
            System.out.println("To nie jest czar");
            return null;
        }
    }

    private void printCards(List<Card> cards) {
        String allCards = new String();
        for (Card c : cards) {
            if (c instanceof Minion) {
                String clazz = c.getClass().getSimpleName();
                int health = ((Minion) c).getHealth();
                int cost = c.getCost();
                int attack = ((Minion) c).getAttack();
                allCards += clazz + " zycie " + health + " atak " + attack + " koszt " + cost + " | ";
            } else {
                if (c instanceof Spell) {
                    String clazz = c.getClass().getSimpleName();
                    int cost = c.getCost();
                    if (c instanceof DeadlyShot)
                        allCards += clazz + " koszt " + cost;
                    if (c instanceof Fireball)
                        allCards += clazz + " atak 4 " + " koszt " + cost;
                    if (c instanceof HealingTouch)
                        allCards += clazz + " uzdrowienie 8 " + " koszt" + cost;
                    allCards += " | ";
                }
            }
        }
        System.out.println(allCards);
    }


    public static void main(String[] args) {

        BaseTextGui g = new BaseTextGui(new Game());
        g.play();
    }
}
