package org.insa.graphs.algorithm.utils;

import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
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

import static org.junit.Assert.*;

public class AStarAlgorithmTest {

    protected Graph graph = null;

    @Before
    // charger carte
    public void ChargerCarte() throws Exception{
        // load la map
        String mapName = "/home/joel/Documents/Code/INSA/BE_Graphes/bretagne.mapgr";
        // code de Launch
        final GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

        try {
            this.graph = reader.read();
        } catch (IOException err) {
            System.out.println("Unable to read the map file: " + err);
            System.exit(-1);
        }
    }
    @Test
    public void TestFastestPath() {
        Node origin = graph.getNodes().get(367769);
        Node destination = graph.getNodes().get(91810);
        // shortest
        ShortestPathData dataS = new ShortestPathData(graph, origin, destination, ArcInspectorFactory.getAllFilters().get(0));
        AStarAlgorithm dijkstraS = new AStarAlgorithm(dataS);
        Path shortest = dijkstraS.doRun().getPath();

        // fastest
        ShortestPathData dataF = new ShortestPathData(graph, origin, destination, ArcInspectorFactory.getAllFilters().get(2));
        AStarAlgorithm dijkstraF = new AStarAlgorithm(dataF);
        Path fastest = dijkstraF.doRun().getPath();

        assertTrue(Double.compare(shortest.getMinimumTravelTime(), fastest.getMinimumTravelTime()) >= 0);
    }

    @Test
    public void TestCheminNull(){
        Node origin = graph.getNodes().get(6969);
        Node destination = graph.getNodes().get(6969);
        // shortest
        ShortestPathData dataS = new ShortestPathData(graph, origin, destination, ArcInspectorFactory.getAllFilters().get(0));
        AStarAlgorithm dijkstraS = new AStarAlgorithm(dataS);
        Path shortest = dijkstraS.doRun().getPath();
        assertNull(shortest);
    }

    @Test
    public void TestCheminNonConnexe(){
        Node origin = graph.getNodes().get(250038);
        Node destination = graph.getNodes().get(628387);
        // shortest
        ShortestPathData dataS = new ShortestPathData(graph, origin, destination, ArcInspectorFactory.getAllFilters().get(0));
        AStarAlgorithm dijkstraS = new AStarAlgorithm(dataS);
        Path shortest = dijkstraS.doRun().getPath();
        assertNull(shortest);
    }

    @Test
    public void DistanceCoherente(){
        Node origin = graph.getNodes().get(367769);
        Node destination = graph.getNodes().get(91810);
        // shortest
        ShortestPathData dataS = new ShortestPathData(graph, origin, destination, ArcInspectorFactory.getAllFilters().get(0));
        AStarAlgorithm dijkstraS = new AStarAlgorithm(dataS);
        Path shortest = dijkstraS.doRun().getPath();

        assertTrue(Double.compare(shortest.getLength(),origin.getPoint().distanceTo(destination.getPoint())) >= 0);
    }

    @Test
    public void TestSolutionCorrecte() {
        Node origin = graph.getNodes().get(367769);
        Node destination = graph.getNodes().get(91810);
        // shortest
        ShortestPathData data = new ShortestPathData(graph, origin, destination, ArcInspectorFactory.getAllFilters().get(0));
        AStarAlgorithm dijkstraD = new AStarAlgorithm(data);
        Path dij = dijkstraD.doRun().getPath();

        BellmanFordAlgorithm bellmanFord= new BellmanFordAlgorithm(data);
        Path bellF = bellmanFord.doRun().getPath();

        assertEquals(bellF.getLength(), dij.getLength(), 1);
    }

}
