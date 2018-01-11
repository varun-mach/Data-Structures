import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Assigment2 {

	/**
	 * This method takes array a to be sorted, P as the first index of the array a, r as the last index of array a
	 *
	 * This method partitions the array based on the pivot element
	 * @param a
	 * @param p
	 * @param r
	 * @return
	 */
	static int partition(int a[], int p, int r){
		int x = a[r];
		int i = (p-1);

		for (int j=p; j<r; j++){
			if (a[j] <= x){
				i = i+1;
				int temp = a[i];
				a[i] = a[j];
				a[j] = temp;
			}
		}

		int temp = a[i+1];
		a[i+1] = a[r];
		a[r] = temp;

		return i+1;
	}

	/**
	 * This method takes array a to be sorted, P as the first index of the array a, r as the last index of array a
	 * @param a
	 * @param p
	 * @param r
	 */

	static void quickSort(int a[], int p, int r) {
		if (p < r){
			int q = partition(a, p, r);
			quickSort(a, p, q-1);
			quickSort(a, q+1, r);
		}
	}

	/**
	 * This method takes array a to be sorted, P as the first index of the array a, r as the last index of array a
	 * @param a
	 * @param p
	 * @param r
	 */

	static void mergeSort(int a[], int p, int r) {
		if (p < r){
			int q = (p+r)/2;
			mergeSort(a, p, q);
			mergeSort(a , q+1, r);
			merge(a, p, q, r);
		}
	}


	/**
	 * This method takes array a to be sorted, P as the first index of the array a, r as the last index of array a and
	 * q as the half of sum of p and r
	 *
	 * This method is used to merge the sorted subarrays
	 * @param a
	 * @param p
	 * @param q
	 * @param r
	 */
	static void merge(int a[], int p, int q, int r) {
		int n1 = q - p + 1;
		int n2 = r - q;
		int L[] = new int [n1+1];
		int R[] = new int [n2+1];

		for (int i=0; i<n1; ++i)
			L[i] = a[p + i];
		for (int j=0; j<n2; ++j)
			R[j] = a[q + j];

		L[n1] = 9999999;
		R[n2] = 9999999;
		int i = 0;
		int j =0;

		for(int k=p; k<=r ;k++){
			if(L[i] <= R[j]){
				a[k] = L[i];
				i = i + 1;
			}else{
				a[k]= R[j];
				j = j + 1;
			}
		}
	}


	/**
	 * This method is used to sort the input from file
	 * @param xy1
	 * @param xy2
	 * @param xy3
	 * @param input_size
	 * @param samples
	 * @param questionNo
	 */
	public static void sortFunc(XYSeries xy1, XYSeries xy2, XYSeries xy3, int input_size, int samples, String questionNo){

		while(input_size <= 10000){

			//Write to file
			inputFile(input_size,samples);
			//Read from file
			List<int[]> inputList =readFile();

			long elapsedTime_merge = 0;
			long elapsedTime_insrtn = 0;
			long elapsedTime_quick = 0;

			for(int[] input_merge : inputList){

				if(questionNo.equals("Q2")){
					//For non-decreasing
					Arrays.sort(input_merge);

				}else if(questionNo.equals("Q3")){
					//For non-increasing
					Arrays.sort(input_merge);
					int temp;

					for (int i = 0; i < input_merge.length/2; i++){
						temp = input_merge[i];
						input_merge[i] = input_merge[input_merge.length-1-i];
						input_merge[input_merge.length-1-i] = temp;
					}
				}

				int[] input_insrtn = input_merge.clone();
				int[] input_quick = input_insrtn.clone();

				//Merge Sort Algorithm Start
				long startTime_merge = System.nanoTime();
				mergeSort(input_merge, 0, input_size-1);
				elapsedTime_merge += System.nanoTime() - startTime_merge;

				//Insertion Sort Algorithm Start
				int key;
				long startTime_insrtn = System.nanoTime();
				for(int j=1;j<input_insrtn.length;j++){
					key = input_insrtn[j];
					int i=j-1;
					while ((i>0) && (input_insrtn[i] > key)){
						input_insrtn[i+1] = input_insrtn[i];
						i=i-1;
					}
					input_insrtn[i+1] = key;
				}
				elapsedTime_insrtn += System.nanoTime() - startTime_insrtn;

				//Quick sort Algorithm
				//Quick sort Algorithm
				long startTime_quick = System.nanoTime();
				quickSort(input_quick, 0, input_size - 1);
				elapsedTime_quick += System.nanoTime() - startTime_quick;

			}

			xy1.add(input_size, (double)elapsedTime_merge/10000000000.0);
			xy2.add(input_size, (double)elapsedTime_insrtn/10000000000.0);
			xy3.add(input_size, (double)elapsedTime_quick/10000000000.0);

			input_size += 500;

		}

	}


	/**
	 * This method is used to plot the graph for dataset xy1, xy2 and xy3
	 * This method uses Jfreechart to plot the graph
	 * http://www.jfree.org/
	 * @param xy1
	 * @param xy2
	 * @param chartTitle
	 */
	private static void plotFunc(XYSeries xy1, XYSeries xy2,XYSeries xy3, String chartTitle){

		XYSeriesCollection data = new XYSeriesCollection();
		data.addSeries(xy1);
		data.addSeries(xy2);
		data.addSeries(xy3);

		//Chart
		final JFreeChart chart = ChartFactory.createXYLineChart(
				chartTitle,
				"Input size",
				"Time in seconds",
				data,
				PlotOrientation.VERTICAL,
				true,
				true,
				false
				);

		XYPlot plot = chart.getXYPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

		plot.setRenderer(renderer);
		plot.getRenderer().setSeriesPaint(0, Color.YELLOW);
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(600, 600));

		ChartFrame cf= new ChartFrame("Algorithms", chart);
		cf.pack();
		cf.setVisible(true);

	}


	/**
	 * This method is used to write random integers to a file - InputFile.txt
	 * The number of samples is provided in the arguments along with input size
	 * @param input_size
	 * @param samples
	 */
	private static void inputFile(int input_size, int samples) {
		// TODO Auto-generated method stub
		int count = 0;
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("InputFile.txt"))) {
			while(count < samples){
				for(int i=0;i<input_size;i++){
					writer.write(String.valueOf(ThreadLocalRandom.current().nextInt(1, input_size))+",");
				}
				writer.write("\n");
				count++;
			}
			writer.close();

		}catch(Exception e){
			e.printStackTrace();

		}
	}


	/**
	 * This method is used to read integers from file - InputFile.txt
	 * @return
	 */
	private static List<int[]> readFile() {

		String input;
		String input;
		int[] a =null;
		List<int[]> inputList = new ArrayList<>();
		try{
			BufferedReader reader = new BufferedReader(new FileReader("InputFile.txt"));
			while ((input = reader.readLine()) != null) {
				a = new int[input.split(",").length];
				String[] s = input.split(",");
				for(int i=0;i<s.length;i++ ){
					a[i] = Integer.parseInt(s[i]);
				}
				inputList.add(a);
			}

			reader.close();
		}catch(Exception e){
			e.printStackTrace();

		}
		return inputList;
	}


	public static void main(String[] args) {

		XYSeries xy1 = new XYSeries("Merge");
		XYSeries xy2 = new XYSeries("Insertion");
		XYSeries xy3 = new XYSeries("Quick");
		sortFunc(xy1,xy2,xy3,500,10,"Q1");
		plotFunc(xy1,xy2,xy3,"Random");
		System.out.println("----------------Random Finsihed--------------------");


		xy1 = new XYSeries("Merge");
		xy2 = new XYSeries("Insertion");
		xy3 = new XYSeries("Quick");
		sortFunc(xy1,xy2,xy3,500,1,"Q2");
		plotFunc(xy1,xy2,xy3,"Non Decreasing");
		System.out.println("----------------Non Decreasing Finsihed--------------------");


		xy1 = new XYSeries("Merge");
		xy2 = new XYSeries("Insertion");
		xy3 = new XYSeries("Quick");
		sortFunc(xy1,xy2,xy3,500,1,"Q3");
		plotFunc(xy1,xy2,xy3,"Non Increasing");
		System.out.println("----------------Non Increasing Finsihed--------------------");
	}

}