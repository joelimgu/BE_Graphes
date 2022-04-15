package org.insa.graphs.algorithm.utils;


import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.ShortestPathAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)

public class ShortestPathAlgorithmTest {

    protected Graph graph = null;
    protected AbstractInputData data;

    @Before
    // charger carte
    public void ChargerCarte() throws Exception{
        // load la map
        String mapName = "C:\\Users\\dunae\\Documents\\INSA\\3A\\BE_Graphes\\Bretagne.mapgrs";
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

    // lancer


    @Test
    public void TestFastestPath(Path shortest, Path fastest){
//        Node origin = graph.getNodes().get(367769);
//        Node destination = graph.getNodes().get(91810);
//        this.data = new ShortestPathData(graph, origin, destination, ArcInspectorFactory.getAllFilters().get(0));

        assertTrue(Double.compare(shortest.getMinimumTravelTime(),fastest.getMinimumTravelTime())>=0);
    }

    @Test
    public void TestCheminNull(){

    }

    @Test
    public void TestCheminNonConnexe(Path p){
        assertNull(p);
    }

    @Test
    public void TestSolutionCorrecte(Path path_Dijkstra, Path path_bellman_ford){
        assertEquals(path_Dijkstra.getLength(), path_bellman_ford.getLength(), 1);
    }


}
