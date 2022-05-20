package org.insa.graphs.algorithm.packageswitch;

import org.insa.graphs.algorithm.AbstractSolution;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Path;

public class MyPackageSwitchAlgorithm extends PackageSwitchAlgorithm{


    /**
     * Create a new PackageSwitchAlgorithm with the given data.
     *
     * @param data
     */
    protected MyPackageSwitchAlgorithm(PackageSwitchData data) {
        super(data);
    }

    public PackageSwitchSolution doRun(){
        final PackageSwitchData data = getInputData();
        Graph graph = data.getGraph();

        // chemin 1
        System.out.println("doRun package");
        Path chemin1 = this.lancerDijkstra(graph,data.getO1(),data.getD1());
        notifyOrigin1Processed(chemin1.getOrigin());
        notifyDestination1Reached(chemin1.getDestination());
        Path chemin2 = this.lancerDijkstra(graph,data.getO2(),data.getD2());
        notifyOrigin2Processed(chemin2.getOrigin());
        notifyDestination2Reached(chemin2.getDestination());

        for (Arc arc1 : chemin1.getArcs()){
            for (Arc arc2 : chemin2.getArcs()){
                //while (TODO){

                //}
            }
        }


        return new PackageSwitchSolution(data, AbstractSolution.Status.FEASIBLE,chemin1,chemin2);
    }


}
