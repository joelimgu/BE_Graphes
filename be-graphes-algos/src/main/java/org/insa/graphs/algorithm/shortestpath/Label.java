package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label implements Comparable<Label> {
	private Node node; 
	private boolean mark;
	private double cost;
	private Arc father; 
	
	public Label(Node node) {
		this.node=node; 
		this.mark=false; 
		this.cost=Double.POSITIVE_INFINITY; 
		this.father = null ;
	}
	
	public double getCost() {
		return cost; 
	}

	public double getTotalCost(){
		return cost;
	}

	public Node getNode() {
		return node;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public void setFather(Arc father) {
		this.father = father;
	}

	public void setMark(boolean mark) {
		this.mark = mark;
	}

	public boolean isMarked(){
		return this.mark ;
	}

	public int compareTo(Label label){
		return (int)(this.getTotalCost()-label.getTotalCost());
	}

	public Arc getFather() {
		return this.father;
	}
}
