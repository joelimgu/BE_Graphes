package org.insa.graphs.algorithm.utils;

import org.insa.graphs.algorithm.AlgorithmFactory;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.*;
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

    private final int nbOfTests = 50;
    protected Graph graph = null;
    protected Class<? extends ShortestPathAlgorithm> AlgorithmClass;
    protected Graph graphBretagne = null;



    // charger carte
    public void ShortestPathAlgorithmTest(Class<? extends ShortestPathAlgorithm> algorithm) {
        // load la map
        this.AlgorithmClass = algorithm;
        String mapBretagne = "/home/joel/Documents/Code/INSA/BE_Graphes/bretagne.mapgr";
        try {
        // code de Launch
        final GraphReader readerBretagne = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapBretagne))));

            // TODO passer map path comme argument
            this.graphBretagne = readerBretagne.read();
        } catch (IOException err) {
            System.out.println("Unable to read the map file: " + err);
            System.exit(-1);
        }
    }

    @Test
    public void TestFastestPath() throws Exception {
        Random rand = new Random();
        int randInt1 = rand.nextInt(graphBretagne.getNodes().size()-1);
        int randInt2 = rand.nextInt(graphBretagne.getNodes().size()-1);

        // on s'assure que les deux numeros sont differents pour n'avoir pas de chemin null
        while (randInt1 == randInt2) {
            randInt2 = rand.nextInt(graphBretagne.getNodes().size()-1);
        }

        Node origin = this.graphBretagne.getNodes().get(randInt1);
        Node destination = this.graphBretagne.getNodes().get(randInt2);
        // shortest
        ShortestPathData dataS = new ShortestPathData(this.graphBretagne, origin, destination, ArcInspectorFactory.getAllFilters().get(0));
        T algo = (T) AlgorithmFactory.createAlgorithm(this.AlgorithmClass,dataS);
        Path shortest = algo.doRun().getPath();

        // fastest
        ShortestPathData dataF = new ShortestPathData(this.graphBretagne, origin, destination, ArcInspectorFactory.getAllFilters().get(2));
        T algo2 = (T) AlgorithmFactory.createAlgorithm(this.AlgorithmClass,dataF);
        Path fastest = algo2.doRun().getPath();

        assertEquals(shortest.getMinimumTravelTime(), fastest.getMinimumTravelTime(), 0.01);
    }


    @Test
    public void TestCheminNull() throws Exception {
        Random rand = new Random();
        int randInt1 = rand.nextInt(graphBretagne.getNodes().size()-1);

        Node origin = graphBretagne.getNodes().get(randInt1);
        Node destination = graphBretagne.getNodes().get(randInt1);
        // shortest
        ShortestPathData dataS = new ShortestPathData(graphBretagne, origin, destination, ArcInspectorFactory.getAllFilters().get(0));
        T algo = (T) AlgorithmFactory.createAlgorithm(this.AlgorithmClass,dataS);
        Path shortest = algo.doRun().getPath();
        assertNull(shortest);
    }

    @Test
    public void TestCheminNonConnexe() throws Exception {
        Node origin = graphBretagne.getNodes().get(250038);
        Node destination = graphBretagne.getNodes().get(628387);
        // shortest
        ShortestPathData dataS = new ShortestPathData(graphBretagne, origin, destination, ArcInspectorFactory.getAllFilters().get(0));
        T algo = (T) AlgorithmFactory.createAlgorithm(this.AlgorithmClass,dataS);;
        Path shortest = algo.doRun().getPath();
        assertNull(shortest);
    }

    @Test
    public void DistanceCoherente() throws Exception {
        Random rand = new Random();
        int randInt1 = rand.nextInt(graphBretagne.getNodes().size()-1);
        int randInt2 = rand.nextInt(graphBretagne.getNodes().size()-1);

        // on s'assure que les deux numeros sont differents pour n'avoir pas de chemin null
        while (randInt1 == randInt2) {
            randInt2 = rand.nextInt(graphBretagne.getNodes().size()-1);
        }

        Node origin = graphBretagne.getNodes().get(randInt1);
        Node destination = graphBretagne.getNodes().get(randInt2);
        // shortest
        ShortestPathData dataS = new ShortestPathData(graphBretagne, origin, destination, ArcInspectorFactory.getAllFilters().get(0));
        T algo = (T) AlgorithmFactory.createAlgorithm(this.AlgorithmClass,dataS);
        Path shortest = algo.doRun().getPath();

        assertTrue(Double.compare(shortest.getLength(),origin.getPoint().distanceTo(destination.getPoint())) >= 0);
    }


    @Test
    public void TestSolutionCorrecteBF() throws Exception {
        Random rand = new Random();
        for (int i = 0; i < this.nbOfTests; i++) {
            Node origin = this.graphBretagne.getNodes().get(rand.nextInt(this.graphBretagne.size()-1));
            Node destination = this.graphBretagne.getNodes().get(rand.nextInt(this.graphBretagne.size()-1));
            ShortestPathData data = new ShortestPathData(this.graphBretagne, origin, destination, ArcInspectorFactory.getAllFilters().get(0));
            T algo = (T) AlgorithmFactory.createAlgorithm(this.AlgorithmClass,data);

            Path dij = algo.doRun().getPath();

            BellmanFordAlgorithm BF= new BellmanFordAlgorithm(data);
            Path bell = BF.doRun().getPath();

            assertEquals(bell.getLength(), dij.getLength(), 1);
        }

    }


}
