package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Graph;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        Graph graph = data.getGraph();
        // peut-être plus simple de faire la liste ici plutôt que dans une classe à part?
        LabelList labels = new LabelList(graph);
        // TODO:
        return solution;
    }

}
