// Test Main for (Nearly) Optimal BST and AVL.
//	File IO is done through TextInputStream class.
//
// Bongki Moon (bkmoon@snu.ac.kr)

import java.io.*;
import java.lang.management.*;

public class MainBst {
  public static ThreadMXBean TMB;

  public static void main(String args[]) throws IOException
  {
    long cputime;

    if (args.length != 2) {
	System.err.println("Usage: java MainBst train-file query-file");
	System.exit(0);
    }

    TMB = ManagementFactory.getThreadMXBean();
    if (! TMB.isThreadCpuTimeSupported()) {
	System.out.println("ThreadCpuTime is not supported.");
	System.exit(0);
    }

    // (1) Create three plain BSTs and an AVL from the train set.
    BST bst = new BST();
    buildBST(bst, args[0]);

    BST obst = new BST();
    buildBST(obst, args[0]);

    BST nobst = new BST();
    buildBST(nobst, args[0]);

    AVL avl = new AVL();
    buildBST(avl, args[0]);
    System.out.println("Number of words in the BST: "+obst.size()
		+" (number of insertions: "+obst.sumFreq()+")");

    // (2) Probe the plain BST and AVL for the words in query set.
    System.out.println("Sum of Weighted Path Lengths (BST): "
		+bst.sumWeightedPath());
    bst.resetCounters();
    probeBST(bst,args[1]);

    System.out.println("Sum of Weighted Path Lengths (AVL): "
		+avl.sumWeightedPath());
    avl.resetCounters();
    probeBST(avl,args[1]);

    // (3) Transform another plain BST into an NOBST and repeat probing.
    cputime = TMB.getCurrentThreadCpuTime();
    nobst.nobst();
    cputime = TMB.getCurrentThreadCpuTime() - cputime;
    System.out.println("CPU time to convert to an NOBST: "
		+(cputime/1000000)+" millisec");
    System.out.println("Sum of Weighted Path Lengths (NOBST): "
		+nobst.sumWeightedPath());

    nobst.resetCounters();
    probeBST(nobst,args[1]);

    // (4) Transform the other BST to an OBST and repeat probing.
    cputime = TMB.getCurrentThreadCpuTime();
    obst.obst();
    cputime = TMB.getCurrentThreadCpuTime() - cputime;
    System.out.println("CPU time to convert to an OBST: "
		+(cputime/1000000)+" millisec");
    System.out.println("Sum of Weighted Path Lengths (OBST): "
		+obst.sumWeightedPath());

    obst.resetCounters();
    probeBST(obst,args[1]);

    Runtime runtime = Runtime.getRuntime();
    System.out.println("Memory consumption: "
		+ (runtime.totalMemory() - runtime.freeMemory()) + " bytes");
  }

  public static void buildBST(BST bst, String input)
  {
    TextInputStream sfs = new TextInputStream(input);

    long cputime = TMB.getCurrentThreadCpuTime();
    while(sfs.ready()) bst.insert(sfs.readWord());
    cputime = TMB.getCurrentThreadCpuTime() - cputime;

    bst.print();
    String bstType = (bst instanceof AVL)? "AVL" : "BST";
    System.out.println("CPU time to build a(n) "+bstType+": "
				+(cputime/1000000)+" millisec");
  }

  public static void probeBST(BST bst, String keys)
  {
    TextInputStream qfs = new TextInputStream(keys);
    int	notfound=0;

    long cputime = TMB.getCurrentThreadCpuTime();
    while(qfs.ready()) {
	String queryWord = qfs.readWord();
	if (bst.find(queryWord)==false) {
	    System.out.println("The word `"+queryWord+"' not found.");
	    notfound++;
	}
    }
    cputime = TMB.getCurrentThreadCpuTime() - cputime;

    bst.print();
    String bstType = "BST";
    if (bst instanceof AVL) bstType = "AVL";
    else if (bst.NOBSTified == true) bstType = "NOBST";
    else if (bst.OBSTified == true) bstType = "OBST";

    System.out.println("Total number of node accesses ("+bstType+"): "
		+bst.sumProbes()+" (failed searches: "+notfound+")");
    System.out.println("CPU time for searching keys ("+bstType+"): "
		+(cputime/1000000)+" millisec");
  }
}
