package org.insa.graphs.algorithm.packageswitch;

import org.insa.graphs.algorithm.AbstractAlgorithm;
import org.insa.graphs.algorithm.AbstractSolution;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathObserver;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public abstract class PackageSwitchAlgorithm extends AbstractAlgorithm<PackageSwitchObserver> {

    /**
     * Create a new PackageSwitchAlgorithm with the given data.
     * 
     * @param data
     */
    protected PackageSwitchAlgorithm(PackageSwitchData data) {
        super(data);
    }

    @Override
    public PackageSwitchSolution run() {
        return (PackageSwitchSolution) super.run();
    }
    

    @Override
    protected abstract PackageSwitchSolution doRun();

    protected Path lancerDijkstra(Graph graph, Node o, Node d) {
        // shortest
        ShortestPathData dataChemin1 = new ShortestPathData(graph, o, d, ArcInspectorFactory.getAllFilters().get(0));
        DijkstraAlgorithm dijkstraS = new DijkstraAlgorithm(dataChemin1);
        return dijkstraS.doRun().getPath();
    }

    /**
     * Notify all observers that the origin has been processed.
     *
     * @param node Origin.
     */
    public void notifyOrigin1Processed(Node node) {
        for (PackageSwitchObserver obs: getObservers()) {
            obs.notifyOrigin1Processed(node);
        }
    }

    /**
     * Notify all observers that the origin has been processed.
     *
     * @param node Origin.
     */
    public void notifyOrigin2Processed(Node node) {
        for (PackageSwitchObserver obs: getObservers()) {
            obs.notifyOrigin2Processed(node);
        }
    }

    /**
     * Notify all observers that a node has been reached for the first time.
     *
     * @param node Node that has been reached.
     */
    public void notifyNodeReached(Node node) {
        for (PackageSwitchObserver obs: getObservers()) {
            obs.notifyNodeReached(node);
        }
    }

    /**
     * Notify all observers that a node has been marked, i.e. its final value has
     * been set.
     *
     * @param node Node that has been marked.
     */
    public void notifyNodeMarked(Node node) {
        for (PackageSwitchObserver obs: getObservers()) {
            obs.notifyNodeMarked(node);
        }
    }

    /**
     * Notify all observers that the destination has been reached.
     *
     * @param node Destination.
     */
    public void notifyDestination1Reached(Node node) {
        for (PackageSwitchObserver obs: getObservers()) {
            obs.notifyDestination1Reached(node);
        }
    }


    /**
     * Notify all observers that the destination has been reached.
     *
     * @param node Destination.
     */
    public void notifyDestination2Reached(Node node) {
        for (PackageSwitchObserver obs: getObservers()) {
            obs.notifyDestination2Reached(node);
        }
    }

    @Override
    public PackageSwitchData getInputData() {
        return (PackageSwitchData) super.getInputData();
    }

}
