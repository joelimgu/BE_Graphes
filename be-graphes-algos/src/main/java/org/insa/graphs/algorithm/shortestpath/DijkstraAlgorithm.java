package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractSolution;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Path;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.min;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        Graph graph = data.getGraph();
        LabelList labels = new LabelList(graph);
        // initialisation
        BinaryHeap<Label> tas = new BinaryHeap();
        int originId = data.getOrigin().getId();
        notifyOriginProcessed(data.getOrigin());
        labels.get(originId).setCost(0);
        tas.insert(labels.get(originId));

        // itérations : cf poly p.46, diapo 3.2 plus courts chemins algo de dijkstra
        while (!tas.isEmpty()){
            Label x = tas.findMin();
            x.setMark(true);
            tas.remove(x);
            if (x.getNode()==data.getDestination()){
                notifyDestinationReached(data.getDestination());
                break;
            }
            for (Arc arcY : x.getNode().getSuccessors()) {
                if (!data.isAllowed(arcY)){
                    continue;
                }
                Label y = labels.get(arcY.getDestination().getId());
                if (!y.isMarked()) {
                    double oldCost = y.getCost();
                    y.setCost(min(y.getCost(), x.getCost() + arcY.getLength()));
                    if (oldCost != y.getCost()) {
                        notifyNodeReached(y.getNode());
                        tas.insert(y);
                        y.setFather(arcY);
                    }
                }
            }
        }

        ShortestPathSolution solution = null ;

        if (labels.get(data.getDestination().getId()).getFather()==null){
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }else{
            // cf bellman pour reconstruire la solution
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = labels.get(data.getDestination().getId()).getFather();
            while (arc != null) {
                arcs.add(arc);
                arc = labels.get(arc.getOrigin().getId()).getFather();
            }

            // Reverse the path...
            Collections.reverse(arcs);

            // Create the final solution.
            solution = new ShortestPathSolution(data, AbstractSolution.Status.OPTIMAL, new Path(graph, arcs));

        }
        
        return solution;
    }

}
