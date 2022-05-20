package org.insa.graphs.algorithm.utils;

import org.insa.graphs.algorithm.AlgorithmFactory;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.*;

public class ShortestPathAlgorithmTest<T extends ShortestPathAlgorithm> {

    protected Graph graph = null;
    protected Class<? extends ShortestPathAlgorithm> AlgorithmClass;
    protected Graph graphBretagne = null;


    // charger carte
    public void ShortestPathAlgorithmTest(String mapPath, Class<? extends ShortestPathAlgorithm> algorithm) {
        // load la map
        String mapName = mapPath;
        this.AlgorithmClass = algorithm;
        String mapBretagne = "/home/joel/Documents/Code/INSA/BE_Graphes/bretagne.mapgr";
        try {
        // code de Launch
        final GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
        final GraphReader readerBretagne = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapBretagne))));

            // TODO passer map path comme argument
//            this.graph = reader.read();
            this.graphBretagne = reader.read();
        } catch (IOException err) {
            System.out.println("Unable to read the map file: " + err);
            System.exit(-1);
        }
    }

    @Test
    public void TestFastestPath() throws Exception {
        Node origin = graphBretagne.getNodes().get(367769);
        Node destination = graphBretagne.getNodes().get(91810);
        // shortest
        ShortestPathData dataS = new ShortestPathData(graphBretagne, origin, destination, ArcInspectorFactory.getAllFilters().get(0));
        T algo = (T) AlgorithmFactory.createAlgorithm(this.AlgorithmClass,dataS);
        Path shortest = algo.doRun().getPath();

        // fastest
        ShortestPathData dataF = new ShortestPathData(graphBretagne, origin, destination, ArcInspectorFactory.getAllFilters().get(2));
        T algo2 = (T) AlgorithmFactory.createAlgorithm(this.AlgorithmClass,dataF);
        Path fastest = algo2.doRun().getPath();

        assertTrue(Double.compare(shortest.getMinimumTravelTime(), fastest.getMinimumTravelTime()) >= 0);
    }

    public void TestCheminNull(){
        Node origin = graphBretagne.getNodes().get(6969);
        Node destination = graphBretagne.getNodes().get(6969);
        // shortest
        ShortestPathData dataS = new ShortestPathData(graphBretagne, origin, destination, ArcInspectorFactory.getAllFilters().get(0));
        AStarAlgorithm dijkstraS = new AStarAlgorithm(dataS);
        Path shortest = dijkstraS.doRun().getPath();
        assertNull(shortest);
    }

    public void TestCheminNonConnexe(){
        Node origin = graphBretagne.getNodes().get(250038);
        Node destination = graphBretagne.getNodes().get(628387);
        // shortest
        ShortestPathData dataS = new ShortestPathData(graphBretagne, origin, destination, ArcInspectorFactory.getAllFilters().get(0));
        AStarAlgorithm dijkstraS = new AStarAlgorithm(dataS);
        Path shortest = dijkstraS.doRun().getPath();
        assertNull(shortest);
    }

    public void DistanceCoherente(){
        Node origin = graphBretagne.getNodes().get(367769);
        Node destination = graphBretagne.getNodes().get(91810);
        // shortest
        ShortestPathData dataS = new ShortestPathData(graphBretagne, origin, destination, ArcInspectorFactory.getAllFilters().get(0));
        AStarAlgorithm dijkstraS = new AStarAlgorithm(dataS);
        Path shortest = dijkstraS.doRun().getPath();

        assertTrue(Double.compare(shortest.getLength(),origin.getPoint().distanceTo(destination.getPoint())) >= 0);
    }

    public void TestSolutionCorrecte() {
        Node origin = graphBretagne.getNodes().get(367769);
        Node destination = graphBretagne.getNodes().get(91810);
        // shortest
        ShortestPathData data = new ShortestPathData(graphBretagne, origin, destination, ArcInspectorFactory.getAllFilters().get(0));
        AStarAlgorithm AStar = new AStarAlgorithm(data);
        Path astarPath = AStar.doRun().getPath();

        // on sait que dijkstra fonctionne et c'est beaucoup plus rapide
        DijkstraAlgorithm dijkstraAlgorithm= new DijkstraAlgorithm(data);
        Path dij = dijkstraAlgorithm.doRun().getPath();

        assertEquals(dij.getLength(), astarPath.getLength(), 1);
    }

    public void TestRandomPath() {
        Random rand = new Random();
        int nb_tests = 10;
        for (int i = 0; i < nb_tests; i++) {
            Node origin = graph.getNodes().get(rand.nextInt(graph.size()));
            Node destination = graph.getNodes().get(rand.nextInt(graph.size()));
            // shortest
            ShortestPathData data = new ShortestPathData(graph, origin, destination, ArcInspectorFactory.getAllFilters().get(0));
            DijkstraAlgorithm dijkstraD = new DijkstraAlgorithm(data);
            Path dij = dijkstraD.doRun().getPath();

            AStarAlgorithm AStar= new AStarAlgorithm(data);
            Path astarPath = AStar.doRun().getPath();

            assertEquals(astarPath.getLength(), dij.getLength(), 1);
        }
    }

}
