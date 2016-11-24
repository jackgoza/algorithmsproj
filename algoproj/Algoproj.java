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
    public static Integer source;
    public static Integer dest;
    public static Integer size;
    public static Integer[][] distance;
    public static Integer[][] next;
    public static Integer[][] prev;
    public static Integer[][] trueFlow;
    public static Integer[][] pairs;
    public static Integer inf = 99999;

    public static void initMatrices() {

	distance = new Integer[size][size];
	next = new Integer[size][size];
	prev = new Integer[size][size];
	trueFlow = new Integer[size][size];
	pairs = new Integer[size][size];
    }

    public static void FW(Integer[][] matrix) {

	for (Integer i = 0; i < (size); i++) {
	    for (Integer j = 0; j < (size); j++) {
		next[i][j] = 0;
		prev[i][j] = 0;
		if (i != j) {
		    if (matrix[i][j] == null || matrix[i][j] == 0) {
			distance[i][j] = inf;
			trueFlow[i][j] = 0;
		    }

		    else if (matrix[i][j] != 0) {
			distance[i][j] = matrix[i][j];
		    }
		    else {
			distance[i][j] = 0;
		    }
		    next[i][j] = j + 1;
		    prev[i][j] = i + 1;

		}
		else {
		    distance[i][j] = 0;
		}

	    }
	}

	for (Integer i = 0; i < (mGraph.size); i++) {
	    for (Integer j = 0; j < (mGraph.size); j++) {
		for (Integer k = 0; k < (mGraph.size); k++) {
		    if (distance[j][i] + distance[i][k] < distance[j][k]) {
			distance[j][k] = distance[j][i] + distance[i][k];
			prev[j][k] = prev[i][k];
			next[j][k] = next[j][i];
		    }
		}
	    }
	}
    }

    public static void printGraph(Integer[][] matrix) {
	System.out.println("\n");
	for (Integer i = 0; i < size; i++) {
	    for (Integer j = 0; j < size; j++) {
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

    public static ArrayList<Integer> generatePath(Integer i, Integer j) {
	ArrayList<Integer> path = new ArrayList<>();
	if (next[i][j] == null) {
	    return path;
	}

	path.add(i);
	while (!i.equals(j)) {
	    i = next[i][j] - 1;
	    path.add(i);
	}

	return path;
    }

    public static void makeFlow(Integer[][] flowGraph) {

	for (Integer i = 0; i < size; i++) {
	    for (Integer j = 0; j < size; j++) {

		Integer currentFlowAmount = flowGraph[i][j];
		if (currentFlowAmount.equals(0)) {
		    trueFlow[i][j] = 0;
		}
		else {

		    ArrayList<Integer> path = generatePath(i, j);

		    for (Integer x = 0; x < path.size() - 1; x++) {
			if (trueFlow[path.get(x)][path.get(x + 1)] == null) {
			    trueFlow[path.get(x)][path.get(x + 1)] = currentFlowAmount;
			}
			else {
			    trueFlow[path.get(x)][path.get(x + 1)] += currentFlowAmount;
			}
		    }
		}
	    }
	}
    }

    public static void main(String[] args) {
	mGraph = createMatrix();
	size = mGraph.size;
	source = mGraph.start;
	dest = mGraph.end;

	initMatrices();

	FW(mGraph.edgeGraph);
	System.out.println("Distance: ");
	printGraph(distance);
	System.out.println();
	System.out.println("Next: ");
	printGraph(next);
	System.out.println();
	System.out.println("Prev: ");
	printGraph(prev);
	System.out.println();
	makeFlow(mGraph.flowGraph);
	System.out.println("Flow: ");
	printGraph(trueFlow);
	System.out.println();
	ArrayList<Integer> path = generatePath(source, dest);
	StringBuffer sneakString = "";
	for (Integer i : path) {
	    i++;
	    sneakString += i.toString() + ','

	);
	}
	printGraph(distance);

    }

}
