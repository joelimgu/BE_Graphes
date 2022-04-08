package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;

import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;

public class LabelList {
	
	private ArrayList<Label> labels; 
	
	public LabelList(Graph graph) {
		int size = graph.size();
		this.labels = new ArrayList(size);
		for (int i = 0; i<size; i++) {
			this.labels.set(i, new Label(graph.get(i))); //TODO: constructeur label
		}
	}

	public Label get(int id){
		return this.labels.get(id);
	}

}
