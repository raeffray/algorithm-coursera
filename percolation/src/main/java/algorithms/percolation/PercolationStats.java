package algorithms.percolation;

import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

	private static Percolation percolation;

	private static int results[];

	public PercolationStats(int n, int trials) {

	} // perform trials independent experiments on an n-by-n grid

	public static double mean() {

		return StdStats.mean(results);

	} // sample mean of percolation threshold

	public double stddev() {
		return StdStats.stddev(results);
	} // sample standard deviation of percolation threshold

	public double confidenceLo() {
		return 0d;
	} // low endpoint of 95% confidence interval

	public double confidenceHi() {
		return 0d;
	} // high endpoint of 95% confidence interval

	public static void main(String[] args) {
		int n = Integer.valueOf(args[0]);
		int iterations = Integer.valueOf(args[1]);
		
		results = new int[iterations];

		for (int i = 0; i < iterations; i++) {
			// if has a third parameter for exporting
			// coordinates results
			if(args.length == 3){
				percolation = new Percolation(n,args[2]);
			}else{
				percolation = new Percolation(n);
			}
			percolation.run();
			results[i] = percolation.numberOfOpenSites();
			System.out.println(results[i]);
		}
		System.out.println("");
		System.out.println(mean());

	}

}
