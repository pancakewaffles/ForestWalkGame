package com.mygdx.game;

import io.improbable.keanu.algorithms.variational.optimizer.KeanuOptimizer;
import io.improbable.keanu.algorithms.variational.optimizer.Optimizer;
import io.improbable.keanu.network.BayesianNetwork;
import io.improbable.keanu.tensor.dbl.DoubleTensor;
import io.improbable.keanu.vertices.dbl.probabilistic.GaussianVertex;
import io.improbable.keanu.vertices.dbl.probabilistic.UniformVertex;
import io.improbable.keanu.distributions.continuous.Gaussian;

import java.util.ArrayList;
import java.util.HashSet;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.random;

public class AI {

    private double lyingProb;
    public Node recommendedPath;
    public double confidence;
    private double[] heurisitics = new double[]{1.0, 0.2, 0.05};
    private HashSet<Node> deadends = new HashSet<Node>();
    public boolean lie = false;

    public AI (double lyingProbability, double[] heur){
        lyingProb = lyingProbability;
        heurisitics = heur;
    }

    public AI (double lyingProbability){
        lyingProb = lyingProbability;
    }
    public Node getRecommendedPath(){  return recommendedPath;   }
    public double getProbability(){ return confidence;   }
    public void predict(Node current, ArrayList<Node> paths){
        lie = false;

        for (int i=0; i<paths.size();i++){
            if (deadends.contains(paths.get(i))){
                paths.remove(i);
            }
        }
        if (paths.size() == 1){
            deadends.add(current);
            recommendedPath = paths.get(0);
            confidence = 100;
            return;
        }

        double[] scores = new double[paths.size()];
        double currentLumin = current.getLuminosity();
        double currentHumidity = current.getHumidity();
        double currentTemp = current.getTemperature();
        double min = Double.POSITIVE_INFINITY;

        for (int i=0;i<paths.size();i++){
            double tmp;
            double calculated;

            calculated = paths.get(i).getLuminosity();
            tmp = calculated-currentLumin;
            scores[i] += tmp*Math.abs(tmp)*heurisitics[0];

            calculated = paths.get(i).getHumidity();
            tmp = calculated-currentHumidity;
            scores[i] += tmp*Math.abs(tmp)*heurisitics[1];

            calculated = paths.get(i).getTemperature();
            tmp = calculated-currentTemp;
            scores[i] += tmp*Math.abs(tmp)*heurisitics[2];

            min = Math.min(min, scores[i]);
        }

        double best = 0;
        int bestPath = 0;
        double sum = 0;
        if (min < 0){
            for (int i=0; i< scores.length; i++){
                scores[i] -= min*1.2;
            }
        }
        for (int i =0 ;i<scores.length;i++) {
            if (best < scores[i]) {
                best = scores[i];
                bestPath = i;
            }
            sum += scores[i];
        }
        confidence =(best/sum)*100;

        if (random() < lyingProb){
            lie = true;
            scores[bestPath] = 0;
            sum -= best;
            best = 0;
            for (int i =0 ;i<scores.length;i++) {
                if (best < scores[i]) {
                    best = scores[i];
                    bestPath = i;
                }
            }
            confidence = (best/sum)*70;
        }
        recommendedPath = paths.get(bestPath);
    }
}