/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoproj;

import static algoproj.InputParser.createMatrix;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jack
 */
public class Algoproj {

    public static int fwcounter = 0;
    public static int pathcounter = 0;
    public static int nodesDuringPathCreation = 0;
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
    public static PrintWriter out;

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
			trueFlow[i][j] = inf;
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
		    fwcounter++;
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
	out.println("\n");
	for (Integer i = 0; i < size; i++) {
	    for (Integer j = 0; j < size; j++) {
		if (matrix[i][j] == inf || matrix[i][j] == null) {
		    out.print(Character.toString('\u221e') + '\t');
		}
		else {
		    out.print(matrix[i][j]);
		    out.print("\t");
		}
	    }
	    out.println("");
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
		    pathcounter++;

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

    public static int findMax(ArrayList<Integer> path) {
	Integer max = 0;
	int i, j;
	for (Integer x = 0; x < path.size() - 1; x++) {
	    i = path.get(x);
	    j = path.get(x + 1);
	    if (trueFlow[i][j] > max) {
		max = trueFlow[i][j];
	    }

	}
	return max;
    }

    public static int findMin(ArrayList<Integer> path) {
	Integer min = 0;
	int i, j;
	for (Integer x = 0; x < path.size() - 1; x++) {
	    i = path.get(x);
	    j = path.get(x + 1);
	    if (trueFlow[i][j] < min) {
		min = trueFlow[i][j];
	    }
	}
	return min;
    }

    public static double findAvg(ArrayList<Integer> path) {
	Integer sum = 0;
	int i, j;
	for (Integer x = 0; x < path.size() - 1; x++) {
	    i = path.get(x);
	    j = path.get(x + 1);
	    sum += trueFlow[i][j];
	}
	if (sum.equals(0)) {
	    return 0;
	}
	return sum / (path.size() - 1);
    }

    public static void main(String[] args) {

	String name = "Original";

	double start = System.nanoTime();
	mGraph = createMatrix("SneakyPathInput" + name + ".txt");
	double parseTime = mGraph.endParse - mGraph.startParse;
	size = mGraph.size;
	source = mGraph.start;
	dest = mGraph.end;

	initMatrices();

	try {

	    File fileOut = new File("/Users/jack/Box Sync/UMKC3/Algorithms/algoproj/output/" + name + ".txt");
	    out = new PrintWriter(fileOut);

	    FW(mGraph.edgeGraph);
	    makeFlow(mGraph.flowGraph);
	    ArrayList<Integer> path = generatePath(source, dest);

	    double finish = System.nanoTime();
	    double algoTime = finish - start;

	    out.println("Distance: ");
	    printGraph(distance);
	    out.println();
	    out.println("Next: ");
	    printGraph(next);
	    out.println();
	    out.println("Prev: ");
	    printGraph(prev);
	    out.println();
	    out.println("NonAdjusted Flow: ");
	    printGraph(mGraph.flowGraph);

	    out.println();
	    out.println("Adjusted Flow: ");
	    printGraph(trueFlow);
	    out.println();

	    StringBuilder sneakString = new StringBuilder();
	    for (Integer i : path) {
		i++;
		sneakString.append(i.toString() + ',');
	    }
	    sneakString.deleteCharAt(sneakString.lastIndexOf(","));

	    out.println("Shortest path is:");
	    out.println(sneakString);

	    out.println("Max value is:");
	    out.println(findMax(path));

	    out.println("Min value is:");
	    out.println(findMin(path));

	    out.println("Avg value is:");
	    out.println(findAvg(path));

	    out.println("Time elapsed is:");
	    out.println((algoTime / 1000000) + " ms");
	    out.println("Input parsing took:");
	    out.println((parseTime / 1000000) + "ms");
	    out.println("The FW counter is at:");
	    out.println(fwcounter);

	    out.flush();
	    out.close();

	}
	catch (Exception ex) {
	    Logger.getLogger(Algoproj.class.getName()).log(Level.SEVERE, null, ex);
	}

    }

}
