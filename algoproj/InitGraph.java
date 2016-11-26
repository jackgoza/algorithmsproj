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

    public Integer start, end, size;
    public Integer[][] edgeGraph;
    public Integer[][] adjacent;
    public ArrayList<Path> flowList;
    public Integer[][] flowGraph;
    public double startParse, endParse;

    public InitGraph() {
	this.start = 0;
	this.end = 0;
	this.size = 0;
	flowList = new ArrayList<>();
    }

    public InitGraph(Integer st, Integer en, Integer si, Integer[][] graph) {
	this.start = st;
	this.end = en;
	this.size = si;
	this.edgeGraph = graph;
    }

}
