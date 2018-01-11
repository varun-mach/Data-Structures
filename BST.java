 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.LinkedList;
 import java.util.List;
 import java.util.ListIterator;
 import java.util.Stack;

 import javax.print.attribute.standard.Sides;

 public class Assignment5 {

 public static Node root;
 public Assignment5() {
 // TODO Auto-generated constructor stub
 this.root = null;
 }


 /**
 * This method takes the key to be inserted as an argument
 *
 * This method checks if the key is greater than or less than the node and if it
 * is less than the node the left node of the parent node is compared iteratively and
 * the key is inserted at the end. Similarly if the key is greater than the node it is inserted
 * as the right child
 * @param key
 */
 public void insert(int key){

 Node newNode = new Node(key);
 Node current = root;
 Node temp = null;
 while(current != null){
 temp = current;
 if(newNode.key < current.key){
 current = current.left;
 if(current == null){
 temp.left = newNode;
 }
 }else{
 current = current.right;
 if(current == null){
 temp.right = newNode;
 }
 }
 }

 if(temp == null){
 root = newNode;
 }

 }


 /**
 * this method takes key as the argument
 * if key is less than the node then it searches in the left child else in the right child
 * @param key
 * @return
 */
 public boolean contains(int key){
 Node temp = root;
 while(temp != null ){
 if(key< temp.key){
 temp = temp.left;
 }else if(key> temp.key){
 temp = temp.right;
 }else if(temp.key == key){
 return true;
 }
 }
 return false;
 }



 /**
 * This traverses the tree and prints the tree in increasing order from left to right
 * @param node
 */
 public void print(Node node){
 if(node!=null){
 print(node.left);
 System.out.print(node.key +" ");
 print(node.right);
 }
 }


 /**
 * This traverses the tree and counts the number of nodes from left to right
 */
 static int count =0;
 private int size(Node node){
 if (node != null){
 size(node.left);
 count++;
 size(node.right);
 }
 return count;
 }

 /**
 * This gives the smallest element in the tree
 * @param root
 * @return
 */

 private Integer smallest(Node root){

 while(root.left != null){
 root = root.left;
 }
 return root.key;
 }


 /**
 * This gives the largest element in the tree
 * @param root
 * @return
 */
 private Integer largest(Node root){

 while(root.right != null){
 root = root.right;
 }

 return root.key;
 }


 /**
 * This transforms the tree such that each node contains sum of all nodes
 * greater than that node.
 */
 static int sum = 0;
 private void greaterSumTree(Node root){
 int temp;
 if(root!=null){
 greaterSumTree(root.right);
 temp = root.key;
 root.key = sum;
 sum = sum+temp;
 greaterSumTree(root.left);
 }

 }

 public static void main(String[] args) {
 // TODO Auto-generated method stub
 Assignment5 a5 = new Assignment5();

 int a[] = {4,2,3,5,7,1,9};

 System.out.println("Creating a Binary search Tree... ");
 for(int i : a ){
 a5.insert(i);
 }


 System.out.println("Check if tree contains 5 : "+a5.contains(5));
 System.out.println("Check if tree contains 11 : "+a5.contains(11));

 System.out.println("Printing the entire tree: ");
 a5.print(root);

 System.out.println("No of nodes in the tree: "+a5.size(root));

 System.out.println("Smallest node in the tree: "+a5.smallest(root));
 System.out.println("Largest node in the tree: "+a5.largest(root));
 System.out.println("Tree before calling greater sum tree: ");
 a5.print(root);
 System.out.println("Tree after calling greater sum tree: ");
 a5.greaterSumTree(root);
 a5.print(root);
 }

 }

 class Node{
 int key;
 Node left;
 Node right;
 public Node(int key){
 this.key = key;
 left = null;
 right = null;
 }
 }