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

   /**
    * public void deleteMin()
    *
    * Delete the node containing the minimum key.
    *
    */
    public void deleteMin() {
     	return; // should be replaced by student code
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

    public void meld (FibonacciHeap heap2)
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
    	return null;
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
    	return; // should be replaced by student code
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
	private HeapNode next;
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
