/**
 * FibonacciHeap
 *
 * An implementation of fibonacci heap over non-negative integers.
 */
public class FibonacciHeap {
	private HeapNode minNode; // min pointer
	private static int totalCuts; 
	private static int totalLinks;
	private int treeNum; 
	private int markedNum; // for potential
	private int size; 
	
   /**
    * public boolean empty()
    *
    * precondition: none
    * 
    * The method returns true if and only if the heap
    * is empty.
    *   
    */
    public boolean empty() {
    	return minNode == null;
    }
		
   /**
    * public HeapNode insert(int key)
    *
    * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap. 
    */

    public HeapNode insert(int key)
    {    
    	
    	if (key < 0) return null;

    	HeapNode newNode = new HeapNode(key); 
    	// Default values: rank = 0, mark = False, child = null
    	// next = null, prev = null, parent = null
    	
    	if (empty()) { //insert first node
    		minNode = newNode; 
    		minNode.next = minNode; 
    		minNode.prev = minNode; 
    	}
    	
    	else {  //insert between minnode and minnode.next
    		newNode.next = minNode.next;
    		minNode.next.prev = newNode; 
    		newNode.prev = minNode;  
    		minNode.next = newNode; 
    	}
    	
    	if (newNode.key < minNode.key) minNode = newNode; //update min
    	
    	size++;
    	treeNum++; //update fields
    	
    	return newNode; 
    }

    public String linetostring(HeapNode node) {
    	String string = node.key+" ";
    	for (HeapNode temp=node.next; temp!=node; temp=temp.next) string+= temp.key+" ";
    	return string;
	}
    
   /**
    * public void deleteMin()
    *
    * Delete the node containing the minimum key.
    *
    */
    public void deleteMin() {
    	
    	if (minNode == null) return;
    	
    	size--;
    	if(size == 0) { //if heap now empty
    		minNode = null;
    		return;
    	}
    	
    	HeapNode minNext = minNode.next;  //pointer to other node in list for concating later
    	
    	minNode.prev.next = minNode.next;
    	minNode.next.prev = minNode.prev;
    	minNode.next = null;
    	minNode.prev = null; //delete min node from tree list (bypass it)
    	
    	if (minNode.child == null) { //skip concating and successive link immediately
    		successiveLink(minNext);
			return;
		}
    	
    	HeapNode directChild = minNode.child;
    	minNode.child = null; //cut child pointer;
    	
    	directChild.parent = null;
    	HeapNode temp = directChild.next;
    	while (temp != directChild) { //we go over every node in children (until we return to beginning- circular array)
			temp.parent = null; //delete parent pointers;
			treeNum++; //added trees = children - 1
			temp = temp.next;
		}
    	//note: num of children = rank = O(logn)
    	
    	//all min node pointers deleted
    	
    	//we can now treat directChild as the start of a different heap and meld
    	
    	if(minNext == minNode) { //list to meld into is empty
    		minNode = directChild; //temporary
    	}
    	
    	else {
    		minNode = minNext; //temporary
    		
        	HeapNode currentListSecond = minNode.next;
        	HeapNode otherListEnd = directChild.prev; //since the array is circular we determine first is min and his prev is last
        	
        	minNode.next = directChild;
        	directChild.prev = minNode;
        	
        	otherListEnd.next = currentListSecond;
        	currentListSecond.prev = otherListEnd; //put "new heap" inbetween min and min.next
		}
    	
    	//we now do successive linking to find new min
    	
    	successiveLink(minNode);
    	
    	
    }
    
    /**
     * links two trees x,y such that y is now x.child (y is hanged on x's root).
     * returns the new linked tree.
     * updates rank of new root.
     */
    private HeapNode link(HeapNode x,HeapNode y) {
    	
    	if (x.key > y.key) { //if x not smaller than y switch
    		HeapNode temp =  x;
    		x = y;
    		y = temp;
    	}
    	//now x smaller - will be new root
    	
    	if(x.child == null) { //y only child, create own list
    		y.next = y;
    		y.prev = y;
    	}
    	
    	else { //insert y in son list between child and child.next
			y.next = x.child.next;
			y.prev = x.child;
			
			x.child.next.prev = y;
			x.child.next = y;
		}
    	
    	x.child = y;
    	y.parent = x; //update parent child pointers
    	
    	x.rank++; //added one child
    	
    	return x; //return new root
    	
    }
    
    /**
     * receives a heap and performs successive linking on it (adds tree by ranks as shown in class)
     * @param node pointer to heap (some root)
     * @return node pointer to new linked heap (new root)- root returned is new min node
     */
    private void successiveLink(HeapNode node) {
		
    	int maxRank = (int)(5*Math.log10(size)) + 2; //max rank <= 1.44log2(n) <= 5log10(n) (according to lecture)
    	HeapNode[] rankArray = new HeapNode[maxRank];
    	
    	node.prev.next = null; 
    	node.prev = null; //now list not circular
    	for (HeapNode temp = node.next; temp != null; temp = temp.next) temp.prev = null;
    	//delete all prev pointers (note: we go over treelist a constant number of times so ammort time fine)
    	
    	//now treelist is a simple linked list - we go over it and successive link
    	
    	HeapNode temp = node;
    	HeapNode nexttemp;
    	int tempRank;
    	
    	while (temp.next != null) {

    		nexttemp = temp.next;
    		temp.next = null; //disconnected from list completely
    		//we go through all this trouble of disconnecting pointers to avoid unwanted pointer in trees after link
    		tempRank = temp.rank;
    		

    		if (rankArray[tempRank] == null) rankArray[tempRank] = temp;  //"insert into vases"
    		
    		else {
    			rankArray[tempRank + 1] = link(rankArray[tempRank], temp); //"link and move to next vase"
    			rankArray[tempRank] = null; //"empty vase"
    		}
    		
    		temp = nexttemp;
    		
		}
    	
    	//we now have an array of linked trees- turn back into linked list
    	
    	minNode = null; //flag for first insert (we have access to trees through array)
    	
    	for (HeapNode tree : rankArray) {
			if(tree != null) { //there us a tree of this rank
				
				if (minNode == null) { //first insert
					minNode = tree;
					minNode.next = minNode;
					minNode.prev = minNode;
				}
				else { //insert into non empty list
		    		tree.next = minNode.next;
		    		minNode.next.prev = tree; 
		    		tree.prev = minNode;  
		    		minNode.next = tree; 
		    	}
		    	
		    	if (tree.key < minNode.key) minNode = tree; //update min
			}
		}
    	
	}
    
   /**
    * public HeapNode findMin()
    *
    * Return the node of the heap whose key is minimal. 
    *
    */
    public HeapNode findMin() {
    	return minNode; 
    } 
    
   /**
    * public void meld (FibonacciHeap heap2)
    *
    * Meld the heap with heap2
    *
    */

    public void meld(FibonacciHeap heap2)
    {
    	
    	size += heap2.size;
    	treeNum += heap2.treeNum;
    	markedNum += heap2.markedNum; //update fields
    	
    	
    	if (heap2.minNode == null) return; //nothing to meld if heap empty
    	else if (minNode == null) {
    		minNode=heap2.minNode;
    		return; //if this heap empty, it now looks the same as heap2 did initially
    	}
    	
    	
    	//if heap not empty these pointers exist
    	HeapNode othermin = heap2.minNode;
    	HeapNode currentListSecond = minNode.next;
    	HeapNode otherListEnd = othermin.prev; //since the array is circular we determine first is min and his prev is last
    	
    	minNode.next = othermin;
    	othermin.prev = minNode;
    	
    	otherListEnd.next = currentListSecond;
    	currentListSecond.prev = otherListEnd; //put heap 2 inbetween min and min.next
    	
    	
    	if(heap2.minNode.key<minNode.key) minNode = heap2.minNode; //update min
    	

    }

   /**
    * public int size()
    *
    * Return the number of elements in the heap
    *   
    */
    public int size() {
    	return size;
    }
    	
    /**
    * public int[] countersRep()
    *
    * Return a counters array, where the value of the i-th entry is the number of trees of order i in the heap. 
    * 
    */
    public int[] countersRep() {
    	if (empty()) return new int[] {}; 
    	int[] countersRep = new int[maxRank() + 1]; 
    	HeapNode temp = minNode; 
    	do {
    		countersRep[temp.rank]++; 
    		temp = temp.next; 
    	} while (temp != minNode); 
    	return countersRep; 
    }
    
    private int maxRank() {        
    	int maxRank = 0; 
    	HeapNode temp = minNode; 
    	do {
    		maxRank = Math.max(maxRank, temp.rank); 
    		temp = temp.next; 
    	} while (temp != minNode); 
    	return maxRank; 
    }
	
   /**
    * public void delete(HeapNode x)
    *
    * Deletes the node x from the heap. 
    *
    */
    public void delete(HeapNode x) {    
    	return; // should be replaced by student code
    }

   /**
    * public void decreaseKey(HeapNode x, int delta)
    *
    * The function decreases the key of the node x by delta. The structure of the heap should be updated
    * to reflect this change (for example, the cascading cuts procedure should be applied if needed).
    */
    public void decreaseKey(HeapNode x, int delta) { 
    	if ((x == null) || (delta < 0) || (x.key - delta < 0)) return;
    	x.key -= delta; 
    	if (x == minNode) return; 
    	if ((x.parent != null) && (x.rank < x.parent.key)) {
    		cascadingCut(x, x.parent); 
    	}
    	if (x.key < minNode.key) minNode = x;  
    }
    
	/* private void findNewMin() {
    	HeapNode temp = minNode.next; 
    	while (temp != minNode) {
    		if (temp.key < minNode.key) {
    			minNode = temp;
    			return; 
    		}
    		temp = temp.next; 
    	}
    } */
    
    private void cut(HeapNode x, HeapNode y) {
    	totalCuts++; 
    	treeNum++; // Every cut adds a new tree
    	x.parent = null; 
    	x.marked = false; 
    	--y.rank; 
    	if (x.next == x) {
    		y.child = null; 
    	}
    	else {
    		y.child = x.next; 
    		x.prev.next = x.next; 
    		x.next.prev = x.prev; 
    	}
    }
    
    private void cascadingCut(HeapNode x, HeapNode y) {
    	cut(x, y); 
    	if (y.parent != null) {
    		if (!y.marked) { // y.marked == false - y has yet lost one of its sons 
    			y.marked = true; // mark y as having lost one of its sons 
    		}
    		else {
    			cascadingCut(y, y.parent); 
    		}
    	}
    }
    

   /**
    * public int potential() 
    *
    * This function returns the current potential of the heap, which is:
    * Potential = #trees + 2*#marked
    * The potential equals to the number of trees in the heap plus twice the number of marked nodes in the heap. 
    */
    public int potential() {    
    	return treeNum + (2 * markedNum);
    }

   /**
    * public static int totalLinks() 
    *
    * This static function returns the total number of link operations made during the run-time of the program.
    * A link operation is the operation which gets as input two trees of the same rank, and generates a tree of 
    * rank bigger by one, by hanging the tree which has larger value in its root on the tree which has smaller value 
    * in its root.
    */
    public static int totalLinks() {    
    	return totalLinks;
    }

   /**
    * public static int totalCuts() 
    *
    * This static function returns the total number of cut operations made during the run-time of the program.
    * A cut operation is the operation which diconnects a subtree from its parent (during decreaseKey/delete methods). 
    */
    public static int totalCuts() {    
    	return totalCuts;
    }
    
   /**
    * public class HeapNode
    * 
    * If you wish to implement classes other than FibonacciHeap
    * (for example HeapNode), do it in this file, not in 
    * another file 
    *  
    */
    public class HeapNode {

	public int key;
	private int rank;
	private boolean marked;
	private HeapNode child;
	public HeapNode next; 
	private HeapNode prev;
	private HeapNode parent;

	public HeapNode(int key) {
		this.key = key;
		marked = false;
		rank = 0;
	}

	public int getKey() {
		return this.key;
	}
    } 
}
