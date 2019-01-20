package com.mygdx.game;

import io.improbable.keanu.vertices.dbl.probabilistic.GaussianVertex;
import java.util.Scanner;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class Graph
{
  static HashMap<Integer,Node> map = new HashMap<Integer,Node>();
  static int[][] adj;
  static int total_nodes;

  public void addEdge(int a, int b){
    adj[a][b] = 1;
    adj[b][a] = 1;
  }
  // arguments are passed using the text field below this editor
  public void run()
  {
    // user input
    /*
    Scanner sc = new Scanner(System.in);
    total_nodes = Integer.parseInt(sc.nextLine());
    adj = new int[total_nodes][total_nodes];
    String line;
    while (!(line = sc.nextLine()).isEmpty()) {
      String[] data = line.split(" ");
      int first = Integer.parseInt(data[0]);
      int second = Integer.parseInt(data[1]);
      adj[first][second] = 1;
      adj[second][first] = 1;
    }
    */
    total_nodes = 41;
    adj = new int[total_nodes][total_nodes];
    // Layer 1
    addEdge(0,1);
    addEdge(1,2);
    addEdge(1,3);
    addEdge(1,37);

    // Layer 2
    addEdge(2,39);
    addEdge(5,38);
    addEdge(3,40);
    addEdge(2,4);
    addEdge(3,5);
    addEdge(3,6);
    addEdge(6,12);
    addEdge(6,11);
    addEdge(6,9);
    addEdge(4,8);
    addEdge(4,7);
    addEdge(8,5);
    addEdge(5,10);

    // Layer 3
    addEdge(20,36);
    addEdge(10,15);
    addEdge(9,20);
    addEdge(7,13);
    addEdge(13,14);
    addEdge(15,16);
    addEdge(15,17);
    addEdge(15,18);
    addEdge(17,19);
    addEdge(18,21);

    // Layer 4
    addEdge(21,35);
    addEdge(23,32);
    addEdge(21,22);
    addEdge(22,23);
    addEdge(23,26);
    addEdge(26,31);
    addEdge(26,27);
    addEdge(26,28);
    addEdge(23,24);
    addEdge(24,25);

    // Layer 5
    addEdge(27,33);
    addEdge(29,34);
    addEdge(27,29);
    addEdge(28,30);
    addEdge(29,30);


    // generating nodes
    for(int i = 0;i<total_nodes;i++){
      Node a = new Node(i);
      map.put(i,a);
    }

    // populating nodes with their neighbours
    for(int i = 0;i<total_nodes;i++){
      ArrayList<Node> al = new ArrayList<Node>();
      for(int j = 0;j<total_nodes;j++){
        if(adj[i][j] == 1 && j!=i){
          al.add(map.get(j));
        }
      }
      map.get(i).setNeighbours(al);
    }


    // traverse the graph and give each node its values (propogation step)
    int start = 0;
    double initial_lum = 100.0;
    double initial_hum = 100.0;
    double temperature = 100.0;
    bfs(total_nodes,start, initial_lum, initial_hum, temperature);
  }

  public static void main(String[] args)
  {
    Graph g = new Graph();
    g.run();

    AI a = new AI(0.1);
    Node current = map.get(8);
    a.predict(current, current.getNeighbours());
    System.out.println(a.getProbability());
    System.out.println(a.getRecommendedPath().getId());
  }

  public Node getNode(int id){
    return map.get(id);
  }

  public static void bfs(int total_nodes, int start, double initial_lum, double initial_hum, double temperature){
    boolean[] visited = new boolean[total_nodes];
    LinkedList<Node> queue = new LinkedList<Node>();
    visited[start] = true;
    queue.add(map.get(start));
    map.get(start).setLuminosity(initial_lum);
    map.get(start).setHumidity(initial_hum);
    map.get(start).setTemperature(temperature);
    while(queue.size() != 0){
      initial_lum -= 5.0;
      initial_hum += (Math.random() < 0.7 ? -5.0 : 5.0 );
      temperature += (Math.random() < 0.5 ? -0.5 : 0.5 );
      Node a = queue.poll();
      ArrayList<Node> al = a.getNeighbours();
      for(Node t : al){
        if(!visited[t.getId()]){
          visited[t.getId()] = true;
          queue.add(t);
          t.setLuminosity(initial_lum);
          t.setHumidity(initial_hum);
          t.setTemperature(temperature);
        }
      }
    }


  }
}

// you can add other public classes to this editor in any order
class Node
{
  private int id;
  private int size;
  private double luminosity_mu;
  private double luminosity_sigma;
  private double humidity_mu;
  private double humidity_sigma;
  private double temperature_mu;
  private double temperature_sigma;
  private ArrayList<Node> neighbours;

  public Node(int id){
    this.id = id;
  }
  public void setLuminosity(double lum){
    this.luminosity_mu = lum;
    this.luminosity_sigma = Math.sqrt(Math.abs(lum)/2);
  }
  public void setHumidity(double hum){
    this.humidity_mu = hum;
    this.humidity_sigma = Math.sqrt(Math.abs(hum)/4);
  }
  public void setTemperature(double temp){
    this.temperature_mu = temp;
    this.temperature_sigma = Math.sqrt(Math.abs(temp)/8);
  }

  public ArrayList<Node> getNeighbours(){
    return this.neighbours;
  }

  public void setNeighbours(ArrayList<Node> ne){
    this.neighbours = ne;
  }

  private double getGaussianSample(double mu, double sigma){
          GaussianVertex gaussian = new GaussianVertex(mu, sigma);
          return gaussian.sample().max();
          }

  public int getId(){
    return this.id;
  }

  public double getLuminosity(){
    return getGaussianSample(this.luminosity_mu,this.luminosity_sigma);
  }
  public double getHumidity(){
    return getGaussianSample(this.humidity_mu,this.humidity_sigma);
  }
  public double getTemperature(){
    return getGaussianSample(this.temperature_mu,this.temperature_sigma);
  }

}
