public class test {

	public static void main(String[] args) {
		
		FibonacciHeap heap=new FibonacciHeap();
		heap.insert(5);
		FibonacciHeap.HeapNode a= heap.insert(3);
		FibonacciHeap.HeapNode b= heap.insert(7);
		heap.insert(9);
		heap.insert(1);
		FibonacciHeap heap2=new FibonacciHeap();
		heap2.insert(2);
		heap2.insert(4);
		heap2.insert(6);
		heap2.insert(99);
		heap2.insert(11);
		heap2.insert(22);
		heap.meld(heap2);
		
		//System.out.println(heap.findMin().key);
		System.out.println(heap.linetostring(heap.findMin()));
		//System.out.println(heap.potential());
		heap.deleteMin();
		System.out.println(heap.linetostring(heap.findMin()));
		//System.out.println(heap.potential());
		///System.out.println(heap.size());
		heap.decreaseKey(b, 4);
		System.out.println(heap.linetostring(heap.findMin()));

	}

}
