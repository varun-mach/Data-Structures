import java.util.Scanner;
public class SumN {
 public static void main (String[] args) {
	Scanner sc = new Scanner(System.in);
	for(int i=0;i<5;i++){
		int n = sc.nextInt();
		calcPattern(n);
	 }
 }


 public static void calcPattern(int n){

	 long[] a = new long[n + 1];
	 int[] arr = {1, 3, 4};
	 a[0] = 1;

	 for(int i = 1; i <= n; i++){
		 for(int j=0; j<3; j++){
			 if(arr[j]<=i){
			 a[i] = (a[i]+ a[i-arr[j]])%100000;
			 }
		 }
	 }

	 System.out.println("Input: "+ n + " Last 5 digits of no. of Patterns : " + a[n]);

 }
}

