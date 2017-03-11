
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

	private static final String CARRIAGE_RETURN = "\n";

	private static Percolation percolation;

	private static double results[];

	private static int iterations;

	private static int gridDimension;

	// flag to export file results
	private static boolean exportResults;

	// file to export the results
	private static String pathFileResults;

	private static PrintWriter out;

	public PercolationStats(int n, int trials) {

	}

	/**
	 * sample mean of percolation threshold
	 * 
	 */
	public static double mean() {
		return StdStats.mean(results);
	}

	/**
	 * sample standard deviation of percolation threshold
	 * 
	 */
	public static double stddev() {
		return StdStats.stddev(results);
	}

	/**
	 * low endpoint of 95% confidence interval
	 * 
	 */
	public static double confidenceLo() {
		return mean() - ((1.96d * (stddev())) / Math.sqrt(iterations));
	}

	/**
	 * high endpoint of 95% confidence interval
	 * 
	 */
	public static double confidenceHi() {
		return mean() + ((1.96d * (stddev())) / Math.sqrt(iterations));
	}

	public static void main(String[] args) {
		double gridSize = 0;
		gridDimension = Integer.valueOf(args[0]);
		iterations = Integer.valueOf(args[1]);

		if (args.length >= 3) {
			pathFileResults = args[2];
			exportResults = true;
		}

		results = new double[iterations];

		long begin = System.currentTimeMillis();
		
		for (int i = 0; i < iterations; i++) {
			// if has a third parameter for exporting
			// coordinates results
			if (args.length == 4) {
				percolation = new Percolation(gridDimension, args[3]);
			} else {
				percolation = new Percolation(gridDimension);
			}
			percolation.run();
			gridSize = Math.pow(gridDimension, 2);
			results[i] = (percolation.numberOfOpenSites() / (double) gridSize);
		}

		StringBuffer buffer = new StringBuffer();

		buffer.append("mean                    = ").append(mean()).append(CARRIAGE_RETURN);
		buffer.append("stddev                  = ").append(mean()).append(CARRIAGE_RETURN);
		buffer.append("mean                    = ").append(mean()).append(CARRIAGE_RETURN);
		buffer.append("95% confidence interval = [").append(confidenceLo()).append(", ").append(confidenceHi())
				.append("]").append(CARRIAGE_RETURN);
		buffer.append(CARRIAGE_RETURN);
		buffer.append("iterations              = ").append(iterations).append(CARRIAGE_RETURN);
		buffer.append("dimension               = ").append(gridSize).append(CARRIAGE_RETURN);
		buffer.append("execution time (ms)     = ").append(System.currentTimeMillis() - begin).append(CARRIAGE_RETURN);

		if (exportResults) {
			openFile();
			printValue(buffer.toString());
			closeFile();
		} else {
			System.out.println(buffer.toString());
		}

	}

	private static void printValue(final String value) {
		if (exportResults) {
			out.print(value);
		}
	}

	private static void openFile() {
		if (out == null && exportResults) {
			try {
				out = new PrintWriter(pathFileResults);
			} catch (FileNotFoundException e) {
				System.out.print("ERROR OPENING FILE. RESULTS WILL NOT BE EXPORTED");
				exportResults = false;
			}
		}
	}

	private static void closeFile() {
		if (out != null) {
			out.flush();
			out.close();
		}
	}

}
