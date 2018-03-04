package engine.mcts;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import engine.Game;
import engine.Move;

public class Node {

	private Game game;
	/**
	 * Stack of moves of untried moves.
	 */
	private Deque<Move> untriedMoves;
	private Node parent;
	private List<Node> childs;
	private Move moveInNode;
	private int firstHeroWins;
	private int secondHeroWins;
	private int totalGames;
	
	public Node() {
		childs = new ArrayList<>();
	}
	
	/**
	 * Root node constructor.
	 * 
	 * @param game
	 */
	public Node(Game game) {
		this();
		this.game = game;
		untriedMoves = new ArrayDeque<Move>(game.getActiveHero().getAvailableMoves());
	}
	
	/**
	 * Child node constructor.
	 * 
	 * @param parent
	 * @param moveInNode 
	 */
	public Node(Node parent, Move moveInNode) {
		this();
		this.game = parent.game;
		this.parent = parent;
		this.moveInNode = moveInNode;
		untriedMoves = new ArrayDeque<Move>(parent.getAvailableMoves());
	}
	
	public void addChild(Node child) {
		this.childs.add(child);
	}
	
	public void addFirstHeroWin() {
		firstHeroWins++;
		totalGames++;
	}
	
	public void addSecondHeroWin() {
		secondHeroWins++;
		totalGames++;
	}
	
	public List<Move> getAvailableMoves() {
		return this.game.getActiveHero().getAvailableMoves();
	}
	
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public List<Node> getChilds() {
		return childs;
	}
	public void setChilds(List<Node> childs) {
		this.childs = childs;
	}
	public Move getMoveInNode() {
		return moveInNode;
	}
	public void setMoveInNode(Move moveInNode) {
		this.moveInNode = moveInNode;
	}
	public int getFirstHeroWins() {
		return firstHeroWins;
	}
	public void setFirstHeroWins(int firstHeroWins) {
		this.firstHeroWins = firstHeroWins;
	}
	public int getSecondHeroWins() {
		return secondHeroWins;
	}
	public void setSecondHeroWins(int secondHeroWins) {
		this.secondHeroWins = secondHeroWins;
	}

	public int getTotalGames() {
		return totalGames;
	}

	public void setTotalGames(int totalGames) {
		this.totalGames = totalGames;
	}

	public Deque<Move> getUntriedMoves() {
		return untriedMoves;
	}

	public void setUntriedMoves(Deque<Move> untriedMoves) {
		this.untriedMoves = untriedMoves;
	}

}
