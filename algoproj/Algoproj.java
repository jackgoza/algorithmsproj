/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoproj;

import static algoproj.InputParser.createMatrix;
import java.util.ArrayList;

/**
 *
 * @author jack
 */
public class Algoproj {

    public static InitGraph mGraph;
    public static int[][] inFlow;
    public static int source;
    public static int dest;
    public static int size;
    public static int[][] distance;
    public static int[][] next;
    public static int[][] prev;
    public static int[][] trueFlow;
    public static int[][] pairs;
    public static int inf = 99999;

    public static void initMatrices() {
	inFlow = new int[size][size];
	next = new int[size][size];
	prev = new int[size][size];
	trueFlow = new int[size][size];
	pairs = new int[size][size];
    }

    public static int[][] FW() {

	for (int i = 0; i < (size); i++) {
	    for (int j = 0; j < (size); j++) {
		if (i != j) {
		    if (mGraph.adjacent[i][j] == 1) {
			mGraph.adjacent[i][j] = mGraph.edgeGraph[i][j];
		    }
		    else {
			mGraph.adjacent[i][j] = inf;
			mGraph.flowGraph[i][j] = inf;
		    }
		    next[i][j] = j + 1;
		    prev[i][j] = i + 1;
		}

	    }
	}

	for (int i = 0; i < (mGraph.size); i++) {
	    for (int j = 0; j < (mGraph.size); j++) {
		for (int k = 0; k < (mGraph.size); k++) {
		    if (mGraph.adjacent[j][i] + mGraph.adjacent[i][k] < mGraph.adjacent[j][k]) {
			mGraph.adjacent[j][k] = mGraph.adjacent[j][i] + mGraph.adjacent[i][k];
			prev[j][k] = prev[i][k];
			next[j][k] = next[j][i];
		    }
		}
	    }
	}
	return mGraph.adjacent;
    }

    static void printGraph(int[][] matrix) {
	System.out.println("\n");
	for (int i = 0; i < size; i++) {
	    for (int j = 0; j < size; j++) {
		if (matrix[i][j] == inf) {
		    System.out.print(Character.toString('\u221e') + '\t');
		}
		else {
		    System.out.print(matrix[i][j]);
		    System.out.print("\t");
		}
	    }
	    System.out.println("");
	}
    }

    public static void makeTotalFlow() {
	int current;
	ArrayList trail = new ArrayList();
	for (Path p : mGraph.flowList) {
	    ArrayList<Integer> step = new ArrayList();
	    current = p.pEnd;
	    while (prev[p.pStart][current] != p.pStart + 1) {
		current = prev[p.pStart][current] - 1;
		step.add(current + 1);

	    }
	    step.add(p.pStart + 1);
	    int start = step.get(0);
	    for (int i = 1; i < step.size(); i++) {
		current = step.get(i);
		mGraph.flowGraph[current - 1][start - 1] += p.pFlow;
		start = current;
	    }
	}

    }

    public static void main(String[] args) {
	mGraph = createMatrix();
	size = mGraph.size;
	source = mGraph.start;
	dest = mGraph.end;

	initMatrices();

	distance = FW();
	printGraph(distance);
	makeTotalFlow();

	printGraph(mGraph.flowGraph);

    }

}
