package gui;

import engine.Card;
import engine.Game;
import engine.Move;
import engine.cards.Minion;
import engine.cards.Spell;
import engine.cards.minions.Tabbycat;
import engine.cards.spells.ArcaneShot;
import engine.cards.spells.Fireball;
import engine.cards.spells.HealingTouch;
import engine.heroes.AbstractHero;
import engine.heroes.HeuristicHero;
import engine.heroes.MctsHero;
import engine.moves.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class BaseTextGuiCopy {
    private final int selfHero = 1;
    private final int selfMinion = 2;
    private final int enemyHero = 1;
    private final int enemyMinion = 2;
    private final int useSpell = 1;
    private final int putCard = 2;
    private final int attack = 3;
    private final int end = 4;
    Game game;
    Scanner keyboard;

    public BaseTextGuiCopy(Game game) {
        super();
        this.game = game;
        keyboard = new Scanner(System.in);
    }

    public static void main(String[] args) {


        int timeForMctsMove;
        int totalTimeForMctsMove;
        int iterations;

        StringBuilder stringBuilder = new StringBuilder();

        /**
         Mcts vs Random
         */

//        shortBunch(stringBuilder);

//        longBunchRandom(stringBuilder);

        /**
         Mcts vs Aggresive
         */

//        longBunchAggresive(stringBuilder);

        /**
         Mcts vs Passive
         */
        longBunchPassive(stringBuilder);
    }

    private static void longBunchRandom(StringBuilder stringBuilder) {
        int timeForMctsMove;
        int totalTimeForMctsMove;
        int iterations; /**
         * move: 1s
         * total moves: 10s
         * iterations: 5
         */

        timeForMctsMove = 1;
        totalTimeForMctsMove = 10;
        iterations = 5;

        performTestMctsWithRandom(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /**
         * move: 2s
         * total moves: 10s
         * iterations: 5
         */

        timeForMctsMove = 2;
        totalTimeForMctsMove = 10;
        iterations = 5;

        performTestMctsWithRandom(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /**
         * move: 2s
         * total moves: 20s
         * iterations: 5
         */

        timeForMctsMove = 2;
        totalTimeForMctsMove = 20;
        iterations = 5;

        performTestMctsWithRandom(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /**
         * move: 4s
         * total moves: 10s
         * iterations: 5
         */

        timeForMctsMove = 4;
        totalTimeForMctsMove = 10;
        iterations = 5;

        performTestMctsWithRandom(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /**
         * move: 4s
         * total moves: 20s
         * iterations: 5
         */

        timeForMctsMove = 4;
        totalTimeForMctsMove = 20;
        iterations = 5;

        performTestMctsWithRandom(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);
    }

    private static void longBunchAggresive(StringBuilder stringBuilder) {
        int timeForMctsMove;
        int totalTimeForMctsMove;
        int iterations; /**
         * move: 1s
         * total moves: 10s
         * iterations: 5
         */

//        timeForMctsMove = 1;
//        totalTimeForMctsMove = 10;
//        iterations = 5;
//
//        performTestMctsWithAggresive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /**
         * move: 2s
         * total moves: 10s
         * iterations: 5
         */

        timeForMctsMove = 2;
        totalTimeForMctsMove = 10;
        iterations = 5;

        performTestMctsWithAggresive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /**
         * move: 2s
         * total moves: 20s
         * iterations: 5
         */

        timeForMctsMove = 2;
        totalTimeForMctsMove = 20;
        iterations = 5;

        performTestMctsWithAggresive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /**
         * move: 4s
         * total moves: 10s
         * iterations: 5
         */

        timeForMctsMove = 4;
        totalTimeForMctsMove = 10;
        iterations = 5;

        performTestMctsWithAggresive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /**
         * move: 4s
         * total moves: 20s
         * iterations: 5
         */

        timeForMctsMove = 4;
        totalTimeForMctsMove = 20;
        iterations = 5;

        performTestMctsWithAggresive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);
    }

    private static void longBunchPassive(StringBuilder stringBuilder) {
        int timeForMctsMove;
        int totalTimeForMctsMove;
        int iterations; /**
         * move: 1s
         * total moves: 10s
         * iterations: 5
         */

//        timeForMctsMove = 1;
//        totalTimeForMctsMove = 10;
//        iterations = 5;
//
//        performTestMctsWithPassive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);
//
//        /**
//         * move: 2s
//         * total moves: 10s
//         * iterations: 5
//         */
//
//        timeForMctsMove = 2;
//        totalTimeForMctsMove = 10;
//        iterations = 5;
//
//        performTestMctsWithPassive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);
//
//        /**
//         * move: 2s
//         * total moves: 20s
//         * iterations: 5
//         */
//
//        timeForMctsMove = 2;
//        totalTimeForMctsMove = 20;
//        iterations = 5;
//
//        performTestMctsWithPassive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);


//        /**
//         * move: 4s
//         * total moves: 20s
//         * iterations: 5
//         */
//
//        timeForMctsMove = 4;
//        totalTimeForMctsMove = 20;
//        iterations = 5;
//
//        performTestMctsWithPassive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        /**
         * move: 8s
         * total moves: 60s
         * iterations: 5
         */

        timeForMctsMove = 8;
        totalTimeForMctsMove = 60;
        iterations = 5;

        performTestMctsWithPassive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

//        /**
//         * move: 4s
//         * total moves: 10s
//         * iterations: 5
//         */
//
//        timeForMctsMove = 4;
//        totalTimeForMctsMove = 10;
//        iterations = 5;
//
//        performTestMctsWithPassive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);
    }

    private static void shortBunch(StringBuilder stringBuilder) {
        int timeForMctsMove;
        int totalTimeForMctsMove;
        int iterations;

        /**
         * move: 1s
         * total moves: 2s
         * iterations: 5
         */

        timeForMctsMove = 1;
        totalTimeForMctsMove = 2;
        iterations = 5;

        stringBuilder.append("single_move_time;round_time;mcts_win;maximal_number_of_playouts;max_tree_depth\n");
        try {
            Files.write(Paths.get("D:/pwr/studia magisterskie/.Wybrane zagadnienia sztucznej inteligencji/lab/hearthstone-mcts/results/random.csv"), stringBuilder.toString().getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
        stringBuilder.setLength(0);

        performTestMctsWithRandom(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        stringBuilder.append("single_move_time;round_time;mcts_win;maximal_number_of_playouts;max_tree_depth\n");
        try {
            Files.write(Paths.get("D:/pwr/studia magisterskie/.Wybrane zagadnienia sztucznej inteligencji/lab/hearthstone-mcts/results/aggresive.csv"), stringBuilder.toString().getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
        stringBuilder.setLength(0);

        performTestMctsWithAggresive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);

        stringBuilder.append("single_move_time;round_time;mcts_win;maximal_number_of_playouts;max_tree_depth\n");
        try {
            Files.write(Paths.get("D:/pwr/studia magisterskie/.Wybrane zagadnienia sztucznej inteligencji/lab/hearthstone-mcts/results/passive.csv"), stringBuilder.toString().getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
        stringBuilder.setLength(0);

        performTestMctsWithPassive(timeForMctsMove, totalTimeForMctsMove, iterations, stringBuilder);
    }

    private static void performTestMctsWithRandom(int timeForMctsMove, int totalTimeForMctsMove, int iterations, StringBuilder stringBuilder) {
        stringBuilder.setLength(0);
        for (int i = 0; i < iterations; i++) {
            BaseTextGuiCopy g = new BaseTextGuiCopy(new Game());
            int numberOfWinningHero = g.mctsWithRandomPlay(timeForMctsMove, totalTimeForMctsMove);
            int maxNumberOfPlayouts = Collections.max(((MctsHero) g.game.getFirstHero()).getNumbersOfPlayouts());
            int maxTreeDepth = Collections.max(((MctsHero) g.game.getFirstHero()).getMaximumTreeDepths());
            stringBuilder.append(timeForMctsMove);
            stringBuilder.append(";");
            stringBuilder.append(totalTimeForMctsMove);
            stringBuilder.append(";");
            stringBuilder.append(numberOfWinningHero);
            stringBuilder.append(";");
            stringBuilder.append(maxNumberOfPlayouts);
            stringBuilder.append(";");
            stringBuilder.append(maxTreeDepth);
            stringBuilder.append("\n");
            try {
                Files.write(Paths.get("D:/pwr/studia magisterskie/.Wybrane zagadnienia sztucznej inteligencji/lab/hearthstone-mcts/results/random.csv"), stringBuilder.toString().getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
            stringBuilder.setLength(0);
        }
    }

    private static void performTestMctsWithAggresive(int timeForMctsMove, int totalTimeForMctsMove, int iterations, StringBuilder stringBuilder) {
        stringBuilder.setLength(0);

        for (int i = 0; i < iterations; i++) {
            BaseTextGuiCopy g = new BaseTextGuiCopy(new Game());
            int numberOfWinningHero = g.mctsWithAggresivePlay(timeForMctsMove, totalTimeForMctsMove);
            int maxNumberOfPlayouts = Collections.max(((MctsHero) g.game.getFirstHero()).getNumbersOfPlayouts());
            int maxTreeDepth = Collections.max(((MctsHero) g.game.getFirstHero()).getMaximumTreeDepths());
            stringBuilder.append(timeForMctsMove);
            stringBuilder.append(";");
            stringBuilder.append(totalTimeForMctsMove);
            stringBuilder.append(";");
            stringBuilder.append(numberOfWinningHero);
            stringBuilder.append(";");
            stringBuilder.append(maxNumberOfPlayouts);
            stringBuilder.append(";");
            stringBuilder.append(maxTreeDepth);
            stringBuilder.append("\n");
            try {
                Files.write(Paths.get("D:/pwr/studia magisterskie/.Wybrane zagadnienia sztucznej inteligencji/lab/hearthstone-mcts/results/aggresive.csv"), stringBuilder.toString().getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
            stringBuilder.setLength(0);
        }
    }

    private static void performTestMctsWithPassive(int timeForMctsMove, int totalTimeForMctsMove, int iterations, StringBuilder stringBuilder) {
        stringBuilder.setLength(0);

        for (int i = 0; i < iterations; i++) {
            BaseTextGuiCopy g = new BaseTextGuiCopy(new Game());
            int numberOfWinningHero = g.mctsWithPassivePlay(timeForMctsMove, totalTimeForMctsMove);
            int maxNumberOfPlayouts = Collections.max(((MctsHero) g.game.getFirstHero()).getNumbersOfPlayouts());
            int maxTreeDepth = Collections.max(((MctsHero) g.game.getFirstHero()).getMaximumTreeDepths());
            stringBuilder.append(timeForMctsMove);
            stringBuilder.append(";");
            stringBuilder.append(totalTimeForMctsMove);
            stringBuilder.append(";");
            stringBuilder.append(numberOfWinningHero);
            stringBuilder.append(";");
            stringBuilder.append(maxNumberOfPlayouts);
            stringBuilder.append(";");
            stringBuilder.append(maxTreeDepth);
            stringBuilder.append("\n");
            try {
                Files.write(Paths.get("D:/pwr/studia magisterskie/.Wybrane zagadnienia sztucznej inteligencji/lab/hearthstone-mcts/results/passive.csv"), stringBuilder.toString().getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
            stringBuilder.setLength(0);
        }
    }

    public void baseInfo() {
        System.out.println("\nAktywny gracz: " + game.getActiveHero().getName() + "\n");
        System.out.println("Pierwszy gracz: " + game.getFirstHero().getName());
        System.out.println("HP: " + game.getFirstHero().getHealth());
        System.out.println("Mana: " + game.getFirstHero().getMana());
        System.out.println("Liczba kart w decku: " + game.getFirstHero().getDeck().size());
        System.out.println("Kara za pusty deck: " + game.getFirstHero().getPunishForEmptyDeck());
        System.out.println("Karty w ręce: ");
        printCards(game.getFirstHero().getHand());
        System.out.println("Karty na stole: ");
        printCards(game.getFirstHero().getBoard());
        System.out.println("------------------------------");

        System.out.println("Drugi gracz: " + game.getSecondHero().getName());
        System.out.println("HP: " + game.getSecondHero().getHealth());
        System.out.println("Mana: " + game.getSecondHero().getMana());
        System.out.println("Liczba kart w decku: " + game.getSecondHero().getDeck().size());
        System.out.println("Kara za pusty deck: " + game.getSecondHero().getPunishForEmptyDeck());
        System.out.println("Karty w ręce: ");
        printCards(game.getSecondHero().getHand());
        System.out.println("Karty na stole: ");
        printCards(game.getSecondHero().getBoard());
        System.out.println("------------------------------");
    }

    public void startGameMctsWithRandom() {
        System.out.println("Gra rozpoczeta");
        game.initializeAndStartHumanWithMctsGame();
        // game.initializeAndStartRandomWithMctsGame(timeForMctsMove, totalTimeForMctsMoves);
        prepareCards();
        // System.out.println();
        game.getActiveHero().startRound();
        baseInfo();
    }

    public void startGameMctsWithRandom(int timeForMctsMove, int totalTimeForMctsMoves) {
        System.out.println("Gra rozpoczeta");
        // game.initializeAndStartHumanWithMctsGame();
        game.initializeAndStartRandomWithMctsGame(timeForMctsMove, totalTimeForMctsMoves);
        // prepareCards();
        // System.out.println();
        game.getActiveHero().startRound();
        baseInfo();
    }

    public void startGameMctsWithAggresive(int timeForMctsMove, int totalTimeForMctsMoves) {
        System.out.println("Gra rozpoczeta");
        // game.initializeAndStartHumanWithMctsGame();
        game.initializeAndStartAggresiveWithMctsGame(timeForMctsMove, totalTimeForMctsMoves);
        // prepareCards();
        // System.out.println();
        game.getActiveHero().startRound();
        baseInfo();
    }

    public void startGameMctsWithPassive(int timeForMctsMove, int totalTimeForMctsMoves) {
        System.out.println("Gra rozpoczeta");
        // game.initializeAndStartHumanWithMctsGame();
        game.initializeAndStartPassiveWithMctsGame(timeForMctsMove, totalTimeForMctsMoves);
        // prepareCards();
        // System.out.println();
        game.getActiveHero().startRound();
        baseInfo();
    }

    private void prepareCards() {
        game.getActiveHero().getHand().clear();
        game.getActiveHero().getBoard().clear();
        game.getActiveHero().getDeck().clear();
        AbstractHero enemy = (AbstractHero) game.getEnemyOf(game.getActiveHero());
        enemy.getHand().clear();
        enemy.getBoard().clear();
        enemy.getDeck().clear();

        ArrayList<Card> deck1 = new ArrayList<>();
        deck1.add(new Tabbycat(game.getActiveHero()));
        deck1.add(new HealingTouch(game.getActiveHero()));
        deck1.add(new Fireball(game.getActiveHero()));
        ArrayList<Card> deck11 = new ArrayList<>();
        deck11.add(new Tabbycat(game.getActiveHero()));
        deck11.add(new HealingTouch(game.getActiveHero()));
        deck11.add(new Fireball(game.getActiveHero()));
        ArrayList<Card> deck2 = new ArrayList<>();
        deck2.add(new Tabbycat(enemy));
        deck2.add(new HealingTouch(enemy));
        deck2.add(new Fireball(enemy));
        ArrayList<Card> deck22 = new ArrayList<>();
        deck22.add(new Tabbycat(enemy));
        deck22.add(new HealingTouch(enemy));
        deck22.add(new Fireball(enemy));


        ((AbstractHero) game.getActiveHero()).setHand(deck1);
        ((AbstractHero) game.getActiveHero()).setDeck(deck11);
        enemy.setHand(deck2);
        enemy.setDeck(deck22);

    }

    public int play() {
        startGameMctsWithRandom();
        while ((game.getWinner() == null)) {
            makeMove();
            if (game.getWinner() != null)
                break;
            baseInfo();
        }

        if (game.getWinner() == game.getFirstHero()) {
            System.out.println("Brawo gracz 1 wygrywa");
            return 1;
        } else {
            System.out.println("Brawo gracz 2 wygrywa");
            return 0;
        }
    }

    public int mctsWithRandomPlay(int timeForMctsMove, int totalTimeForMctsMoves) {
        startGameMctsWithRandom(timeForMctsMove, totalTimeForMctsMoves);
        while ((game.getWinner() == null)) {
            makeMove();
            if (game.getWinner() != null)
                break;
            baseInfo();
        }

        if (game.getWinner() == game.getFirstHero()) { // mcts is first
            System.out.println("Brawo gracz 1 wygrywa");
            return 1;
        } else {
            System.out.println("Brawo gracz 2 wygrywa");
            return 0;
        }
    }

    public int mctsWithAggresivePlay(int timeForMctsMove, int totalTimeForMctsMoves) {
        startGameMctsWithAggresive(timeForMctsMove, totalTimeForMctsMoves);
        while ((game.getWinner() == null)) {
            makeMove();
            if (game.getWinner() != null)
                break;
            baseInfo();
        }

        if (game.getWinner() == game.getFirstHero()) { // mcts is first
            System.out.println("Brawo gracz 1 wygrywa");
            return 1;
        } else {
            System.out.println("Brawo gracz 2 wygrywa");
            return 0;
        }
    }

    public int mctsWithPassivePlay(int timeForMctsMove, int totalTimeForMctsMoves) {
        startGameMctsWithPassive(timeForMctsMove, totalTimeForMctsMoves);
        while ((game.getWinner() == null)) {
            makeMove();
            if (game.getWinner() != null)
                break;
            baseInfo();
        }

        if (game.getWinner() == game.getFirstHero()) { // mcts is first
            System.out.println("Brawo gracz 1 wygrywa");
            return 1;
        } else {
            System.out.println("Brawo gracz 2 wygrywa");
            return 0;
        }
    }

    public void makeMove() {
        if (game.getActiveHero() instanceof HeuristicHero) {
            ((HeuristicHero) game.getActiveHero()).chooseHeuristicMove();
            return;
        }
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
                        return new AttackMinion(minion, game.getActiveHero().getBoard(), game.getEnemyOf(game.getActiveHero()).getBoard().get(enymyMinion), enymyMinion);
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
            if (game.getActiveHero().getHand().get(spell) instanceof ArcaneShot) {
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
                                return new UseSpell(spell, game.getActiveHero(), game.getEnemyOf(game.getActiveHero()), (Minion) game.getActiveHero().getBoard().get(minion), minion);
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
                allCards += "[" + clazz + ": zycie " + health + " atak " + attack + " koszt " + cost + "] \n";
            } else {
                if (c instanceof Spell) {
                    String clazz = c.getClass().getSimpleName();
                    int cost = c.getCost();
                    if (c instanceof ArcaneShot)
                        allCards += "[" + clazz + ": koszt " + cost;
                    if (c instanceof Fireball)
                        allCards += "[" + clazz + ": atak 4 " + "koszt " + cost;
                    if (c instanceof HealingTouch)
                        allCards += "[" + clazz + ": uzdrowienie 8 " + "koszt " + cost;
                    allCards += "] \n";
                }
            }
        }
        System.out.println(allCards);
    }
}
