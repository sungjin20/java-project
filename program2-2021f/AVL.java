// AVL Binary Search Tree
  
public class AVL extends BST
{
	  Node rootNode;
	  public AVL() {
		  this.rootNode = null;
	  }
	  
	public int height(Node n) {
		if(n == null) {
			return 0;
		}
		return n.height;
	}
	
	public Node rightRotate(Node y) {
		Node x = y.leftchild;
		Node T2 = x.rightchild;
		x.rightchild = y;
		y.leftchild = T2;
		y.height = Math.max(height(y.leftchild), height(y.rightchild))+1;
		x.height = Math.max(height(x.leftchild), height(x.rightchild))+1;
		return x;
	}
	
	public Node leftRotate(Node x) {
		Node y = x.rightchild;
		Node T2 = y.leftchild;
		y.leftchild = x;
		x.rightchild = T2;
		x.height = Math.max(height(x.leftchild), height(x.rightchild))+1;
		y.height = Math.max(height(y.leftchild), height(y.rightchild))+1;
		return y;
	}
	
	public int getBalance(Node n) {
		if(n == null) {
			return 0;
		}
		return height(n.leftchild)- height(n.rightchild); 
	}
	
	public Node inserting(Node node, String key) {
		if(node == null) {
			return (new Node(key));
		}
		if(isbigger(node.value, key)==1) {
			node.leftchild = inserting(node.leftchild, key);
		} else if(isbigger(key, node.value)==1) {
			node.rightchild = inserting(node.rightchild, key);
		} else {
			node.frequency += 1;
			return node;
		}
		node.height = 1+Math.max(height(node.leftchild), height(node.rightchild));
		int balance = getBalance(node);
		if(balance > 1 && isbigger(node.leftchild.value, key)==1) {
			return rightRotate(node);
		}
		if(balance < -1 && isbigger(key, node.rightchild.value)==1) {
			return leftRotate(node);
		}
		if(balance > 1 && isbigger(key, node.leftchild.value)==1) {
			node.leftchild = leftRotate(node.leftchild);
			return rightRotate(node);
		}
		if(balance < -1 && isbigger(node.rightchild.value, key)==1) {
			node.rightchild = rightRotate(node.rightchild);
			return leftRotate(node);
		}
		return node;
	}
	
	public void insert(String key) {
		this.rootNode = this.inserting(this.rootNode, key);
	}
	public void print() {
		  printinorder(rootNode);
	  }
	public int sumWeightedPath() {
		  return cwprec(rootNode);
	  }
	public void resetCounters() {
		  rsc(rootNode);
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
	public int size() {
		  return keysum(rootNode);
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
 
}

	
	

  
  
