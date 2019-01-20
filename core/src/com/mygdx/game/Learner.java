
package com.mygdx.game;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

public class Learner {

    private static double bestPathAccuracy(Node current, AI ai, int tries, int correct){
        int score = 0;
        double confidence = 0;
        int numTimesLied = 0;
        double lyingconfidence = 0;
        for (int i =0;i < tries; i++) {
            ai.predict(current, current.getNeighbours());
            if (ai.recommendedPath.getId() == correct) {
                score++;
            }
            if (ai.lie){
                numTimesLied ++;
                lyingconfidence+=ai.confidence;
            } else {
                confidence += ai.confidence;
            }
        }
        //System.out.println("lying conf " + lyingconfidence/numTimesLied);
        //System.out.println("Truth conf " + confidence/(tries-numTimesLied));
        return ((double) score)/tries;
    }
    private static double[] mapEvaluate(Graph map, AI ai, int tries){
        double[] accuracies = new double[10];

        HashMap<Integer,Integer> correctPaths = new HashMap<Integer, Integer>();
        correctPaths.put(1,0);
        correctPaths.put(4,0);
        correctPaths.put(5,0);
        correctPaths.put(2,1);
        correctPaths.put(3,5);
        correctPaths.put(6,3);
        correctPaths.put(7,6);
        correctPaths.put(8,3);
        correctPaths.put(9,8);

        Deque<Node> queue = new LinkedList<Node>();
        queue.add(map.getNode(0));
        while(!queue.isEmpty()){
            Node first = queue.remove();
            if (accuracies[first.getId()] == 0) {
                for (Node neighbour : first.getNeighbours()) {
                    queue.add(neighbour);
                }
                if (first.getId() == 0) {
                    accuracies[0] = 1;
                } else {
                    accuracies[first.getId()] = bestPathAccuracy(first, ai, tries, correctPaths.get(first.getId()));
                }
            }
        }
        return accuracies;
    }

    public static void main(String[] args){
        Graph g;
        Node current;
/*
        double score = 0;
        for (int gen =0; gen<100; gen++){
            g = new Graph();
            g.run();

            double[] variables = new double[]{1.5, 0.4, 0.05};
            AI ai1 = new AI(0, variables);
            double[] accuracies1 = mapEvaluate(g, ai1, 1000);

            variables = new double[]{1.5, 0.4, 0};
            AI ai2 = new AI(0, variables);
            double[] accuracies2 = mapEvaluate(g, ai2, 1000);


            for (int i = 0;i < 10; i++){
                score += accuracies1[i] - accuracies2[i];
            }
        }
        System.out.println(score);
*/

/*
        for (int i=0;i<5;i++){
            g = new Graph();
            g.run();
            current = g.getNode(3);
            System.out.println(bestPathAccuracy(current, ai, 1000, 5));
        }
*/
    }
}
