package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;
import org.insa.graphs.model.Point;



class LabelStar extends Label{

    private double distanceToDest;

    public LabelStar(Node node,Node dest){
        super(node);
        this.distanceToDest= Point.distance(node.getPoint(),dest.getPoint());
    }

    public double getTotalCost(){
        return this.getCost() + distanceToDest ;
    }
}
