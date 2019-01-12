
public class test {

	public static void main(String[] args) {
		
		FibonacciHeap heap=new FibonacciHeap();
		heap.insert(5);
		heap.insert(3);
		heap.insert(7);
		heap.insert(9);
		heap.insert(1);
		FibonacciHeap heap2=new FibonacciHeap();
		heap2.insert(2);
		heap.meld(heap2);
		
		System.out.println(heap.findMin().key);
		System.out.println(heap.linetostring(heap.findMin()));
		heap.deleteMin();
		System.out.println(heap.linetostring(heap.findMin()));
		System.out.println(heap.potential());

	}

}
