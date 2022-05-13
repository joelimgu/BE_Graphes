package org.insa.graphs.algorithm.packageswitch;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;

public class PackageSwitchData extends AbstractInputData {

    protected final Node o1,o2,d1,d2;

    protected PackageSwitchData(Graph graph, Node o1, Node o2, Node d1, Node d2, ArcInspector arcFilter) {
        super(graph, arcFilter);
        this.o1=o1;
        this.o2=o2;
        this.d1=d1;
        this.d2=d2;
    }

    public Node getO1(){
        return this.o1;
    }

    public Node getO2(){
        return this.o2;
    }

    public Node getD1(){
        return this.d1;
    }

    public Node getD2(){
        return this.d2;
    }
}
