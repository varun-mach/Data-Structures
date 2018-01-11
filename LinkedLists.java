package ctci;

public class LinkedLists {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Node n1 = new Node(1);
		n1.addNode(new Node(2));
		n1.addNode(new Node(3));
		
		
		while(n1 != null) {
			System.out.println(n1.data);
			n1 = n1.next;
		}
	
	}

}


class Node{
	int data;
	Node next;
	Node(int data){
		this.data = data;
	}
	
	void addNode(Node n) {
		Node temp = this;
		while(temp.next != null) {
			temp = this.next;
		}
		temp.next = n;
		
	}
	
	Node deleteNode(int d) {
		Node temp = this;;
		if(this.data == d) {
			temp = temp.next;
			return temp;
		}
		
		while(temp!=null) {
			if(temp.next.data == d) {
				temp.next = temp.next.next;
				return temp;
			}
		}
		
		return temp;
	}
	
}