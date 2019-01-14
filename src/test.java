import java.util.Arrays;

public class test {

	public static void main(String[] args) {
		
		FibonacciHeap heap=new FibonacciHeap();
		heap.insert(5);
	    heap.insert(3);
		FibonacciHeap.HeapNode b= heap.insert(7);
		heap.insert(9);
		heap.insert(0);
		FibonacciHeap heap2=new FibonacciHeap();
		heap2.insert(0);
		FibonacciHeap.HeapNode a= heap2.insert(4);
		FibonacciHeap.HeapNode c= heap2.insert(6);
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
		
		//heap.decreaseKey(b, 6);
		//heap.decreaseKey(c, 5);
		heap.delete(a);
		
		System.out.println(heap.linetostring(heap.findMin()));
		System.out.println(heap.potential());
		System.out.println(Arrays.toString(heap.countersRep()));
		System.out.println(heap.size());

		FibonacciHeap fibonacciHeap=new FibonacciHeap();
		for (int i = 100; i > 0; i--) fibonacciHeap.insert(i);
		System.out.println(fibonacciHeap.linetostring(fibonacciHeap.findMin()));
		fibonacciHeap.deleteMin();
		System.out.println(fibonacciHeap.linetostring(fibonacciHeap.findMin()));
		System.out.println(fibonacciHeap.totalLinks());
	}

}
