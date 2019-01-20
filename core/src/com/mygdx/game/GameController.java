package com.mygdx.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


// import Graph class and Node class
public class GameController {
	int playerPosition;
	int exitPosition;
	Graph g;
	int health;
	boolean isDead;

	private AI truth;
	public AI[] bots = new AI[4];
	
	public GameController() {
		boolean isDead = false;
		this.exitPosition = 0;
		// generate graph and map
		this.g = new Graph();
		g.run();
		this.playerPosition = new Random().nextInt(31-25+1) + 25;
		this.health = 26;

		double[] probabilities = randomiseTruthTeller();

		// check which one is the truth teller
		for(int i = 0;i<probabilities.length;i++) {
			bots[i] = new AI(probabilities[i]);
			if(probabilities[i] < 0.001){
				System.out.println("Truth "+ i);
				truth = bots[i];
			}
		}

		update(g.getNode(this.playerPosition));
	}
	private double[] randomiseTruthTeller() {
		double[] d = new double[] {0.0, 0.5, 0.5, 0.5};
		for (int i = d.length - 1; i > 0; --i) {
			int j = new Random().nextInt(i + 1);
			double temp = d[i];
			d[i] = d[j];
			d[j] = temp;
		}
		return d;
	}
	public int decrementHealth(){
		health--;
		return health;
	}
	public boolean isExitNode() {
		if(playerPosition == exitPosition) {
			return true;
		}else {
			return false;
		}
	}
	public  boolean isDeadEnd(){
		return g.getNode(playerPosition).getNeighbours().size() == 1;
	}
	public void update(Node chosenNode) {
		this.playerPosition = chosenNode.getId();
		if (isExitNode()) {
			return;
		}
		decrementHealth();
		if (health<= 0){
			isDead = true;
			return;
		}
		Node current = g.getNode(playerPosition);
		ArrayList<Node> neighbours = g.getNode(playerPosition).getNeighbours();

		for (int i = 0; i < 4; i++) {
			bots[i].predict(current, neighbours);
			System.out.println("Bat " + i + " recommends " + bots[i].getRecommendedPath().getId() + " with confidence: " + bots[i].getProbability());
		}


	}
	//AI.predict(...)
	//AI.getRecommendedPath(); returns node that is recommended
	//AI.getProbability(); confidence interval
}
