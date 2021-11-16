// (Nearly) Optimal Binary Search Tree
import java.util.ArrayList;
import java.lang.Math;
import java.util.Arrays;
public class BST { // Binary Search Tree implementation

  protected boolean NOBSTified = false;
  protected boolean OBSTified = false;

  
  static class Node{
	  String value;
	  Node leftchild;
	  Node rightchild;
	  int frequency;
	  int accesscount;
	  int height;
	  
	  public Node(String value) {
		  this.value = value;
		  this.leftchild = null;
		  this.rightchild = null;
		  this.frequency = 1;
		  this.accesscount = 0;
		  this.height = 1;
	  }
  }
  
  
  Node rootNode;
  
  
  
  public int isbigger(String a, String b) {
	  int result = a.compareTo(b);
	  if(result > 0) {return 1;	  
	  } else if(result == 0){
		  return 0;
	  } else {
		  return -1;
	  }
	  }
  

  public BST() {
	  this.rootNode = null;
  }

  public int size() {
	  return keysum(rootNode);
  }
  
  public void insert(String key) {
	  if(rootNode == null) {
		  rootNode = new Node(key);
	  } else {
		  Node head = rootNode;
		  Node currentNode;
		  int compare;
		  while(true) {
			  currentNode = head;
			  compare = isbigger(head.value, key);
			  if(compare == 1) {
				  head = head.leftchild;
				  if(head == null) {
					  currentNode.leftchild = new Node(key);
					  break;
				  }
			  }else if(compare == -1){
				  head = head.rightchild;
				  if(head == null) {
					  currentNode.rightchild = new Node(key);
					  break;
				  }
			  }else {
				  currentNode.frequency += 1;
				  break;
			  }
			  
		  }
	  }
  }
 
  
  public boolean find(String key) {
	  Node head = rootNode;
	  Node currentNode;
	  int compare;
	  while(true) {
		  currentNode = head;
		  currentNode.accesscount += 1;
		  compare = isbigger(head.value, key);
		  if(compare == 1) {
			  head = head.leftchild;
			  if(head == null) {
				  return false;
			  }
		  }else if(compare == -1){
			  head = head.rightchild;
			  if(head == null) {
				  return false;
			  }
		  }else {
			  return true;
		  }
		  
	  }
  }
  
  public int countfreq(Node root) {
	  int freqsum;
	  if(root != null) {
		  freqsum = root.frequency;
		  freqsum = freqsum + countfreq(root.leftchild) + countfreq(root.rightchild);
	  }else {
		  return 0;
	  }
	  return freqsum;
  }
  
  public int countac(Node root) {
	  int acsum;
	  if(root != null) {
		  acsum = root.accesscount;
		  acsum = acsum + countac(root.leftchild) + countac(root.rightchild);
	  }else {
		  return 0;
	  }
	  return acsum;
  }
  
  public int sumFreq() {
	  return countfreq(rootNode);
  }
  public int sumProbes() {
	  return countac(rootNode);
  }
  
  public void rsc(Node root) {
	  if(root != null) {
		  root.frequency = 0;
		  root.accesscount = 0;
		  rsc(root.leftchild);
		  rsc(root.rightchild);
	  }

	  
  }
  
  public int keysum(Node root) {
	  int sum;
	  if(root != null) {
		  sum = 1;
		  sum = sum+keysum(root.leftchild)+keysum(root.rightchild);
		  return sum;
	  }

	  return 0;
  }
  
  public void rsconlyac(Node root) {
	  if(root != null) {
		  root.accesscount = 0;
		  rsconlyac(root.leftchild);
		  rsconlyac(root.rightchild);
	  }

	  
  }
  
  public void resetCounters() {
	  rsc(rootNode);
  }
  
  public int countweightpath(Node root) {
	  int weightpathsum;
	  if(root != null) {
		  rsconlyac(rootNode);
		  find(root.value);
		  weightpathsum = countac(rootNode)*root.frequency;
		  return weightpathsum;
	  }else {
		  return 0;
	  }
	  
  }
  
  public int cwprec(Node root) {
	  int cwpsum;
	  if(root != null) {
		  cwpsum = countweightpath(root);
		  cwpsum = cwpsum+cwprec(root.leftchild)+cwprec(root.rightchild);
	  } else {
		  return 0;
	  }
	  return cwpsum;
  }
  
  
  public int sumWeightedPath() {
	  return cwprec(rootNode);
  }



  public void nobst() {
	  ArrayList<String> a = new ArrayList<String>();
	  ArrayList<Integer> b = new ArrayList<Integer>();
	  inorder(rootNode, a, b);
	  rootNode = null;
	  rootNode = new Node("0");
	  this.makenobst(this.rootNode, a, b);
	  NOBSTified = true;
	  a.clear();
	  b.clear();
  }	// Set NOBSTified to true.
  
  public void makenobst(Node root, ArrayList<String> a, ArrayList<Integer> b) {
	  if(root == null) {
	  } else {
		  int n = a.size();
		  int[] sumleft = new int[n+1];
		  int[] sumright = new int[n+1];
		  sumleft[0] = 0;
		  sumright[n-1] = 0;
		  for(int i=1; i<n; i++) {
			  sumleft[i] = sumleft[i-1] + b.get(i-1);
		  }
		  for(int i=1; i<n; i++) {
			  sumright[n-i-1] = sumright[n-i] + b.get(n-i);
		  }
		  int min = 999999;
		  int minidx = -1;
		  for(int i=0; i<n; i++) {
			 if(min > Math.abs(sumleft[i]-sumright[i])) {
				 min = Math.abs(sumleft[i]-sumright[i]);
				 minidx = i;
			 }
		  }
		  root.value = a.get(minidx);
		  root.frequency = b.get(minidx);

		  if(minidx != 0) {
			  root.leftchild = new Node("ll");
		  }
		  if(minidx != n-1) {
			  root.rightchild = new Node("rr");
		  }
		  ArrayList<String> c = new ArrayList<String>();
		  ArrayList<Integer> d = new ArrayList<Integer>();
		  ArrayList<String> e = new ArrayList<String>();
		  ArrayList<Integer> f = new ArrayList<Integer>();
		  for(int j=0; j<minidx; j++) {
			  c.add(a.get(j));
			  d.add(b.get(j));
		  }
		  for(int j=(minidx+1); j<n; j++) {
			  e.add(a.get(j));
			  f.add(b.get(j));
		  }
		  makenobst(root.leftchild, c, d);
		  makenobst(root.rightchild, e, f);
		  c.clear();
		  d.clear();
		  e.clear();
		  f.clear();
	  }
  }
  
  public void obst() {
	  ArrayList<String> a = new ArrayList<String>();
	  a.add(null);
	  ArrayList<Integer> b = new ArrayList<Integer>();
	  b.add(null);
	  inorder(rootNode, a, b);
	  int n = this.size();
	  int[][] A = new int[n+2][n+1];
	  int[][] R = new int[n+2][n+1];
	  int[][] esum = new int[n+1][n+1];
	  
	  for(int i=0; i<n; i++) {
		  A[i+1][i+1] = b.get(i+1);
		  R[i+1][i+1] = i+1;
		  esum[i+1][i+1] = b.get(i+1);
	  }
	  
	  for(int i=n-1; i>0; i--) {
		  for(int j=i+1; j<n+1; j++) {
			  int d=999999999;
			  int e=0;
			  int c=0;
			  for(int k=R[i][j-1]; k<(R[i+1][j]+1); k++) {
				  if((A[i][k-1]+A[k+1][j])<d) {
					  d = A[i][k-1]+A[k+1][j];
					  c = k;
				  }
				  }
			  esum[i][j] = esum[i][j-1] + esum[i+1][j] - esum[i+1][j-1];
			  d += esum[i][j];
			  A[i][j] = d;
			  R[i][j] = c;
		  }
	  }
	  rootNode = null;
	  rootNode = new Node("");
	  this.buildobst(1, n, R, this.rootNode, a, b);
	  OBSTified = true;
	  a.clear();
	  b.clear();
	  A = null;
	  R = null;
	  esum = null;
 
  }	// Set OBSTified to true.
 
  public void buildobst(int i, int j, int[][] R, Node root, ArrayList<String> a, ArrayList<Integer> b) {
	  int k = R[i][j];
	  if(k==0 || root == null) {
	  } else {
		  root.value = a.get(k);
		  root.frequency = b.get(k);
		  if(R[i][k-1]!=0) {
			  root.leftchild = new Node("");
		  }
		  if(R[k+1][j]!=0) {
			  root.rightchild = new Node("");
		  }
		  buildobst(i, k-1, R, root.leftchild, a, b);
		  buildobst(k+1, j, R, root.rightchild, a, b);
	  }
  }
  
  public void print() {
	  printinorder(rootNode);
  }
  
  public void inorder(Node root, ArrayList<String> a, ArrayList<Integer> b) {
	  if(root != null) {
		  inorder(root.leftchild, a, b);
		  a.add(root.value);
		  b.add(root.frequency);
		  inorder(root.rightchild, a, b);
	  }
  }
  
  public void printinorder(Node root) {
	  if(root != null) {
		  printinorder(root.leftchild);
		  System.out.println('['+root.value+':'+root.frequency+':'+root.accesscount+']');
		  printinorder(root.rightchild);
	  }
  }

}

