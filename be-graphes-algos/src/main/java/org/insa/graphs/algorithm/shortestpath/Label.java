package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label {
	private Node node; 
	private boolean mark;
	private double cost;
	private Arc father; 
	
	public Label(Node node) {
		this.node=node; 
		this.mark=false; 
		this.cost=Double.POSITIVE_INFINITY; 
		this.father = null ; 
		// TODO : quelle valeur donner au père? 
	}
	
	public double getCost() {
		return cost; 
	}
	
	
	
}
