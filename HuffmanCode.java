import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Assignment4 {

	static int count = 0;
	static List<Node> sortedNodes = new ArrayList<>();
	static int totalwordcount = 0;


	public static void main(String[] args) {
		// TODO Auto-generated method stub


		//read a file and calculate frequency
		HashMap<Character,Integer> freqMap =  readFile();
		//create a huffman tree
		huffTree(freqMap);
	
		//Display the character codes
		Collections.sort(sortedNodes, (o1, o2) -> o1.getCodes().compareTo(o2.getCodes()));
		for(Node n: sortedNodes){
			if(String.valueOf(n.c).matches("[a-z.,!?'\\s]"))
				System.out.println(n.c + " - "+ n.codes  );
		}
		
		System.out.println("The text was encoded using "+count+" bits" );
		System.out.println("The text had "+ totalwordcount+ " valid characters");
		System.out.println("Using a 5-bit fixed length encoding, this would have been "+5*totalwordcount +" bits long");
		System.out.println("So we saved "+((5*totalwordcount)-count)+" bits!");
	}

	
	/**
	 * This method takes a hashmap with character as key and frequency as values
	 * 
	 * This method uses a priority queue to store the nodes
	 * @param freqMap
	 */
	private static void huffTree(HashMap<Character, Integer> freqMap) {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		for(Map.Entry<Character, Integer> entry : freqMap.entrySet()){
			pq.add(new Node(null,null,entry.getKey(),entry.getValue()));
		}

		Node root =createTree(pq);
		getCodes(root, new StringBuffer());
	}

	
	/**
	 * This method takes Nodes and a stringbuffer as input
	 * It traverses across the tree and appends 0 if its a left node and 1 to right node
	 * @param root
	 * @param codes
	 */
	private static void getCodes(Node root, StringBuffer codes) {
		// TODO Auto-generated method stub

		if(root == null){
			return;
		}
		if(root.c != '5'){
			//System.out.println(root.c+ " : " + codes + " : "  +root.freq);
			count = count + root.freq*codes.length();
			root.codes = codes.toString();
			sortedNodes.add(root);

		}else{

			codes.append('0');
			getCodes(root.left,codes);
			codes.deleteCharAt(codes.length()-1);

			codes.append('1');
			getCodes(root.right,codes);
			codes.deleteCharAt(codes.length()-1);
		}

	}


	/**
	 * This method takes priorityqueue as input 
	 * It creates a huffmantree by adding the least two frequency nodes 
	 * and adding the new frequency node to the tree
	 * @param pq
	 * @return
	 */
	private static Node createTree(PriorityQueue<Node> pq) {
		// TODO Auto-generated method stub
		while(pq.size()>1){
			Node left = pq.poll();
			Node right = pq.poll();
			Node parent = new Node(left, right, '5', left.freq+right.freq); 
			pq.offer(parent);
		}
		return pq.poll();
	}

	
	/**
	 * This method is used to read a file and 
	 * create a map with key as characters and value as frequency of the characters
	 * @return
	 */
	public static HashMap<Character, Integer> readFile(){

		String line = null;
		HashMap<Character, Integer> freqMap = new HashMap<>();
		try{
			File txtfile = new File("D:\\book4.txt");
			BufferedReader in = new BufferedReader (new FileReader (txtfile));
			while((line = in.readLine())!= null){

				if(!line.isEmpty()){

					char charArr[] = line.toLowerCase().toCharArray();

					for(int i=0;i<charArr.length;i++){

						if(freqMap.containsKey(charArr[i]) && String.valueOf(charArr[i]).matches("[a-z.,!?'\\s]")) {
							totalwordcount++;
							freqMap.put(charArr[i],freqMap.get(charArr[i])+1);
						}else if(String.valueOf(charArr[i]).matches("[a-z.,!?'\\s]")){
							totalwordcount++;
							freqMap.put(charArr[i],1);
						}else{
							freqMap.put(charArr[i],0);
						}
					}
				}	
			}

		}catch(Exception e){
			e.printStackTrace();
		}


		return freqMap;
	}

}	



class Node implements Comparable<Node>{

	Node left;
	Node right;
	char c;
	int freq;
	String codes;
	
	
	public Integer getCodes() {
		return codes.length();
	}

	public void setCodes(String codes) {
		this.codes = codes;
	}

	@Override
	public int compareTo(Node n) {
		// TODO Auto-generated method stub
		return freq - n.freq;
	}

	public Node(Node left, Node right, char c, int freq) {
		super();
		this.left = left;
		this.right = right;
		this.c = c;
		this.freq = freq;
	}

	

}