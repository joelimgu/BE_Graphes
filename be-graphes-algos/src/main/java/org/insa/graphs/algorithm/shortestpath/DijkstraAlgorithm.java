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
        LabelList labels = new LabelList(graph);
        // TODO:
        // initialisation
        // probablement pas la meilleure manière de faire, TODO changer labels.get?
        labels.get(data.getOrigin().getId()).setCost(0);
        return solution;
    }

}
