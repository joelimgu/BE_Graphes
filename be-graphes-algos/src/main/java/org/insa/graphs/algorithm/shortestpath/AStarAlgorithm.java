package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;

import java.util.ArrayList;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }


    public void generateLabels(Graph graph){
        int size = graph.size();
        ShortestPathData data = getInputData();
        Node dest = data.getDestination();
        this.labels = new ArrayList<>();
        for (int i = 0; i<size; i++) {
            this.labels.add(new LabelStar(graph.get(i),dest));
        }


    }
}
