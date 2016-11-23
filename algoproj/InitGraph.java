/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoproj;

import java.util.ArrayList;

/**
 *
 * @author jack
 */
public class InitGraph {

    public int start, end, size;
    public int[][] edgeGraph;
    public int[][] adjacent;
    public ArrayList<Path> flowList;
    public int[][] flowGraph;

    public InitGraph() {
	this.start = 0;
	this.end = 0;
	this.size = 0;
	flowList = new ArrayList<>();
    }

    public InitGraph(int st, int en, int si, int[][] graph) {
	this.start = st;
	this.end = en;
	this.size = si;
	this.edgeGraph = graph;
    }

}
