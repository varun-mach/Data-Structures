package ctci;

public class Stack {
	
	Node top;
	//Insert into Stack
	void push(Object item) {
		if(top != null) {
			Node n = new Node((int) item);
			n.next = top;
			top = n;
		}else {
			top = new Node((int) item);
		}
	}
	//Delete from Stack
	Node pop() {
		if(top != null) {
			Node n = top;
			top = top.next;
			return n;
		}
		return top;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Stack s = new Stack();
			s.push(3);
			System.out.println(s.pop().data);
	}

}

class Node{
	int data;
	Node next;
	Node(int data){
		this.data = data;
	}
	
}
