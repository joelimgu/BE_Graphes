package org.insa.graphs.algorithm.packageswitch;

import org.insa.graphs.model.Node;

import java.io.PrintStream;

public class PackageSwitchTextObserver implements PackageSwitchObserver {

    private final PrintStream stream;

    public PackageSwitchTextObserver(PrintStream stream) {
        this.stream = stream;
    }

    @Override
    public void notifyOrigin1Processed(Node node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void notifyOrigin2Processed(Node node) {

    }

    @Override
    public void notifyNodeReached(Node node) {
        stream.println("Node " + node.getId() + " reached.");
    }

    @Override
    public void notifyNodeMarked(Node node) {
        stream.println("Node " + node.getId() + " marked.");
    }

    @Override
    public void notifyDestination1Reached(Node node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void notifyDestination2Reached(Node node) {

    }


}
