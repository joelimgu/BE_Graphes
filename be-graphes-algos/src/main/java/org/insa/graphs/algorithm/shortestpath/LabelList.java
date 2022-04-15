package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;

import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;

public class LabelList{

	private ArrayList<Label> labels;

	public LabelList(Graph graph) {
		int size = graph.size();
		this.labels = new ArrayList();
		for (int i = 0; i<size; i++) {
			this.labels.add(new Label(graph.get(i)));
		}
	}

	public Label get(int id){
		return this.labels.get(id);
	}

}
