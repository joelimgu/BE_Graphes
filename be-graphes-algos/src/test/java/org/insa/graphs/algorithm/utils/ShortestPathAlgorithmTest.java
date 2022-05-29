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
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.*;

public class ShortestPathAlgorithmTest<T extends ShortestPathAlgorithm> {

    private final int nbOfTests = 16;
    protected Class<? extends ShortestPathAlgorithm> AlgorithmClass;
    protected Graph graphBretagne = null;

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

    // Teste si le shortest est >= au fastest en temps
    @Test
    public void TestFastestPath() throws Exception {
        Random rand = new Random(0);

        for (int i = 0; i < this.nbOfTests; i++) {
            // chercher deux points random differents
            int randInt1 = rand.nextInt(graphBretagne.getNodes().size() - 1);
            int randInt2 = rand.nextInt(graphBretagne.getNodes().size() - 1);

            // on s'assure que les deux numeros sont differents pour n'avoir pas de chemin null
            while (randInt1 == randInt2) {
                randInt2 = rand.nextInt(graphBretagne.getNodes().size() - 1);
            }

            // on lance deux algos de plus court et plus rapide chemin
            Node origin = this.graphBretagne.getNodes().get(randInt1);
            Node destination = this.graphBretagne.getNodes().get(randInt2);
            // shortest ( avec AlgorithmFactory )
            ShortestPathData dataS = new ShortestPathData(this.graphBretagne, origin, destination, ArcInspectorFactory.getAllFilters().get(0));
            T algo = (T) AlgorithmFactory.createAlgorithm(this.AlgorithmClass, dataS);
            Path shortest = algo.doRun().getPath();

            // fastest
            ShortestPathData dataF = new ShortestPathData(this.graphBretagne, origin, destination, ArcInspectorFactory.getAllFilters().get(2));
            T algo2 = (T) AlgorithmFactory.createAlgorithm(this.AlgorithmClass, dataF);
            Path fastest = algo2.doRun().getPath();
            // au cas où les deux points sont non connexes on le teste à côté
            if (shortest == null || fastest == null) {
                assertEquals(shortest, fastest);
            } else {
                // s'ils sont connexes on teste que le plus rapide est plus rapide que le plus court
                assertTrue(Double.compare(shortest.getMinimumTravelTime(), fastest.getMinimumTravelTime()) >= 0);
            }
        }
    }


    // un chemin du même point au même point c'est null
    @Test
    public void TestCheminNull() throws Exception {
        Random rand = new Random(0);
        for (int i = 0; i < this.nbOfTests; i++) {
            int randInt1 = rand.nextInt(this.graphBretagne.size() - 1);

            Node origin = this.graphBretagne.getNodes().get(randInt1);
            Node destination = this.graphBretagne.getNodes().get(randInt1);
            // shortest
            ShortestPathData dataS = new ShortestPathData(this.graphBretagne, origin, destination, ArcInspectorFactory.getAllFilters().get(0));
            T algo = (T) AlgorithmFactory.createAlgorithm(this.AlgorithmClass, dataS);
            Path shortest = algo.doRun().getPath();
            assertNull(shortest);
        }
    }

    //  on teste si le chemin entre deux points non connexes c'est null
    // on a pris deux points à la main pour ce teste
    @Test
    public void TestCheminNonConnexe() throws Exception {
        Node origin = this.graphBretagne.getNodes().get(250038);
        Node destination = this.graphBretagne.getNodes().get(628387);
        // shortest
        ShortestPathData dataS = new ShortestPathData(this.graphBretagne, origin, destination, ArcInspectorFactory.getAllFilters().get(0));
        T algo = (T) AlgorithmFactory.createAlgorithm(this.AlgorithmClass, dataS);
        Path shortest = algo.doRun().getPath();
        assertNull(shortest);
    }

    // on teste si la distance à vol d'oiseau est plus courte que celle trouvée par l'algo
    @Test
    public void DistanceCoherente() throws Exception {
        Random rand = new Random(0);
        for (int i = 0; i < this.nbOfTests; i++) {
            int randInt1 = rand.nextInt(this.graphBretagne.size() - 1);
            int randInt2 = rand.nextInt(this.graphBretagne.size() - 1);

            // on s'assure que les deux numeros sont differents pour n'avoir pas de chemin null
            while (randInt1 == randInt2) {
                randInt2 = rand.nextInt(this.graphBretagne.getNodes().size() - 1);
            }

            Node origin = this.graphBretagne.getNodes().get(randInt1);
            Node destination = this.graphBretagne.getNodes().get(randInt2);
            // shortest
            ShortestPathData dataS = new ShortestPathData(this.graphBretagne, origin, destination, ArcInspectorFactory.getAllFilters().get(0));
            T algo = (T) AlgorithmFactory.createAlgorithm(this.AlgorithmClass, dataS);
            Path shortest = algo.doRun().getPath();
            if (shortest != null) {
                assertTrue(Double.compare(shortest.getLength(), origin.getPoint().distanceTo(destination.getPoint())) >= 0);
            }
        }
    }


    // on compare la solution de notre algo avec celle de BellmanFord qu'on sait correcte
    @Test
    public void TestSolutionCorrecteBF() throws Exception {
        Random rand = new Random(1);
        ExecutorService executorService = Executors.newFixedThreadPool(16);
        ArrayList<Future> futures = new ArrayList<>();

        for (int i = 0; i < this.nbOfTests; i++) {
            int finalI = i;
            futures.add(executorService.submit(() -> {
                Node origin = this.graphBretagne.getNodes().get(rand.nextInt(this.graphBretagne.size() - 1));
                Node destination = this.graphBretagne.getNodes().get(rand.nextInt(this.graphBretagne.size() - 1));
                System.out.println("" + finalI + " origine : " + origin.getId() + " destination : "+ destination.getId());
                ShortestPathData data = new ShortestPathData(this.graphBretagne, origin, destination, ArcInspectorFactory.getAllFilters().get(0));
                T algo = null;
                try {
                    algo = (T) AlgorithmFactory.createAlgorithm(this.AlgorithmClass, data);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                Path dij = algo.doRun().getPath();

                BellmanFordAlgorithm BF = new BellmanFordAlgorithm(data);
                Path bell = BF.doRun().getPath();
                System.out.println("" + finalI + " bell : " + bell.getLength() + " dij : "  + dij.getLength());
                if (bell == null || dij == null) {
                    assertEquals(bell, dij);
                } else {
                    assertEquals(bell.getLength(), dij.getLength(), 1);
                }
            }));
        }

        for(Future future: futures) {
            future.get();
        }
    }
}
