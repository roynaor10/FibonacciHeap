
public class test {

	public static void main(String[] args) {
		
		FibonacciHeap heap=new FibonacciHeap();
		heap.insert(5);
		heap.insert(3);
		FibonacciHeap heap2=new FibonacciHeap();
		heap2.insert(2);
		heap.meld(heap2);
		
		System.out.println(heap.findMin().key);
		System.out.println(heap.potential());

	}

}
