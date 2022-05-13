package org.insa.graphs.algorithm.packageswitch;

import org.insa.graphs.model.Node;

public interface PackageSwitchObserver {

    /**
     * Notify the observer that the origin has been processed.
     *
     * @param node Origin.
     */
    public void notifyOrigin1Processed(Node node);


    /**
     * Notify the observer that the origin has been processed.
     *
     * @param node Origin.
     */
    public void notifyOrigin2Processed(Node node);

    /**
     * Notify the observer that a node has been reached for the first
     * time.
     *
     * @param node Node that has been reached.
     */
    public void notifyNodeReached(Node node);

    /**
     * Notify the observer that a node has been marked, i.e. its final
     * value has been set.
     *
     * @param node Node that has been marked.
     */
    public void notifyNodeMarked(Node node);

    /**
     * Notify the observer that the destination has been reached.
     *
     * @param node Destination.
     */
    public void notifyDestination1Reached(Node node);


    /**
     * Notify the observer that the destination has been reached.
     *
     * @param node Destination.
     */
    public void notifyDestination2Reached(Node node);



}
