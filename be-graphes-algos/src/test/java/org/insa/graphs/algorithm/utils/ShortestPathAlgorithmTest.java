package org.insa.graphs.algorithm.utils;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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

@RunWith(Parameterized.class)

public abstract class ShortestPathAlgorithmTest {

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
    public void TestFastestPath(){

        Node origin = graph.getNodes().get(367769);

        Node destination = graph.getNodes().get(91810);
        this.data = new ShortestPathData(graph, origin, destination, ArcInspectorFactory.getAllFilters().get(0));

        Path ShortestPath =
        Path FastestPath = FastestAlgo().getPath();
        assertTrue(Double.compare(ShortestPath.getMinimumTravelTime(),FastestPath.getMinimumTravelTime())>=0);


    }

    @Test
    public void TestCheminNull(Path path){
        assertEquals(Double.compare(path.getLength(),0),0);
    }

    @Test
    public void DistanceCoherente(Path path,double distance){
        assertTrue(Double.compare(path.getLength(),distance)>=0);
    }

}
