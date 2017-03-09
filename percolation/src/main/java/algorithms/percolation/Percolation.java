package algorithms.percolation;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import edu.princeton.cs.algs4.StdRandom;

public class Percolation {

	private int virtualTopSite;

	private int virtualBottonSite;

	// the grid itself
	private int[] grid;

	// the weight for each site
	private int[] siteWeight;

	// the list of opened sites
	private int[] openedSites;

	// grid dimension
	private int n;

	// flag to export file results
	private boolean exportResults;

	// file to export the results
	private String pathFileResults;

	private PrintWriter out;

	/**
	 * create n-by-n grid, with all sites blocked
	 * 
	 * @param n
	 *            matrix dimension (n x n)
	 * 
	 */
	public Percolation(final int n) {

		this.n = n;

		int gridSize = (int) Math.pow(n, 2);

		// flag set to false by default;
		exportResults = false;

		virtualTopSite = gridSize;
		virtualBottonSite = gridSize + 1;

		grid = new int[gridSize + 2];
		siteWeight = new int[grid.length];
		openedSites = new int[grid.length];

		// prepare the grid and its weight indexing
		// set up the top virtual site
		for (int i = 0; i < virtualBottonSite + 1; i++) {
			grid[i] = i;
			if (i > virtualTopSite - 1) {
				siteWeight[i]++;
			}
		}
	}

	/**
	 * Additional constructor to be able to configure an export
	 * file
	 * 
	 * @param n the grid dimension;
	 * @param fileToExportResults file containing the results
	 * 
	 * */
	public Percolation(final int n, final String fileToExportResults) {
		this(n);
		this.pathFileResults = fileToExportResults;
		this.exportResults = true;
	}

	/**
	 * open site (row, col) if it is not open already
	 * 
	 * @param row coordinate's row
	 * @param col coordinate's column
	 * "row index i out of bounds"
	 * @throws IllegalArgumentException throws an {@link IllegalArgumentException} when
	 * row or column are invalid 
	 * 
	 */
	public void open(int row, int col) {

		if (row < 1 || col < 1) {
			throw new IllegalArgumentException();
		}

		int site = makePlainIndex(row, col);
		int[] north = { row - 1, col };
		int[] south = { row + 1, col };
		int[] west = { row, col - 1 };
		int[] east = { row, col + 1 };

		int northIndex = makePlainIndex(north[0], north[1]);
		int southIndex = makePlainIndex(south[0], south[1]);
		int westIndex = makePlainIndex(west[0], west[1]);
		int eastIndex = makePlainIndex(east[0], east[1]);

		if (row == 1) {
			union(site, virtualTopSite);
		} else if (row == n) {
			union(site, virtualBottonSite);
		}

		if (northIndex >= 0) {
			if (isOpen(north[0], north[1])) {
				union(site, northIndex);
			}
		}
		if (southIndex >= 0) {
			if (isOpen(south[0], south[1])) {
				union(site, southIndex);
			}
		}
		if (westIndex >= 0) {
			if (isOpen(west[0], west[1])) {
				union(site, westIndex);
			}
		}
		if (eastIndex >= 0) {
			if (isOpen(east[0], east[1])) {
				union(site, eastIndex);
			}
		}

		openedSites[site] = 1;
	}

	/**
	 * Tells if a given coordinate is opened
	 * O(1)
	 * 
	 * @param row coordinate's row
	 * @param col coordinate's column
	 * 
	 * @return a boolean values
	 * 
	 */
	public boolean isOpen(int row, int col) {
		return openedSites[makePlainIndex(row, col)] == 1;
	}

	/**
	 * Transforms a coordinate into plain index
	 * 
	 * @param row coordinate's row
	 * @param col coordinate's column
	 * 
	 * @return a integer number based on grid's dimension
	 * 
	 */
	public int makePlainIndex(int row, int col) {
		if (row < 1 || col < 1 || col > n || row > n) {
			return -1;
		}
		if (row == 1) {
			return col - 1;
		} else if (col == 1) {
			return ((row - 1) * n);
		} else if (col == n) {
			return (row * n) - 1;
		} else {
			return ((row - 1) * n) + col - 1;
		}
	}

	/**
	 * Tells if a coordinate is connected to the virtual
	 * roots, either top or bottom
	 * 
	 * @param row coordinate's row
	 * @param col coordinate's column
	 * 
	 * @return boolean value
	 * 
	 */
	public boolean isFull(int row, int col) {

		int site = makePlainIndex(row, col);

		int root = root(site);

		return root == virtualBottonSite || root == virtualTopSite;
	}

	/**
	 * retrieves the total of opened sites
	 * @return the count
	 * 
	 */
	public int numberOfOpenSites() {
		int count = 0;
		for (int i = 0; i < openedSites.length; i++) {
			int j = openedSites[i];
			if (j == 1) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Tells if the system percolates
	 * 
	 *  @return a boolean value
	 */
	public boolean percolates() {
		return grid[grid.length - 1] == grid[grid.length - 2];
	}

	private void union(final int from, final int to) {
		int rootFrom = root(from);
		int rootTo = root(to);

		if (rootFrom == rootTo) {
			return;
		}

		if (rootTo == virtualBottonSite || rootTo == virtualBottonSite) {
			grid[rootFrom] = rootTo;
			siteWeight[rootTo] += siteWeight[rootFrom];
		} else if (rootFrom == virtualBottonSite || rootFrom == virtualBottonSite) {
			grid[rootTo] = rootTo;
			siteWeight[rootFrom] += siteWeight[rootTo];
		} else if (siteWeight[rootFrom] < siteWeight[rootTo]) {
			grid[rootFrom] = rootTo;
			siteWeight[rootTo] += siteWeight[rootFrom];
		} else {
			grid[rootTo] = rootFrom;
			siteWeight[rootFrom] += siteWeight[rootTo];
		}
	}

	private int root(final int site) {
		int root = site;
		while (virtualTopSite != root && virtualBottonSite != root && root != grid[root]) {
			root = grid[root];
		}
		return root;
	}

	/**
	 * Run the percolation system, in a full closed grid
	 * opening the grid with random numbers generated within
	 * the grid's dimension borders.
	 * 
	 * */
	public void run() {
		int row = -1;
		int col = -1;
		openFile();
		printValue(String.valueOf(n));
		while (!percolates()) {
			row = StdRandom.uniform(n + 1);
			col = StdRandom.uniform(n + 1);
			row = row == 0 ? 1 : row;
			col = col == 0 ? 1 : col;
			if (row > 0 && col > 0) {
				if (!isOpen(row, col)) {
					open(row, col);
					printCoordinates(row, col);
				}
			}
		}
		closeFile();
	}

	private void printValue(final String value) {
		if (exportResults) {
			out.print(value);
		}
	}

	private void printCoordinates(final int row, final int col) {
		if (exportResults) {
			printValue(row + " " + col + " ");
		}
	}

	private void openFile() {
		if (out == null && exportResults) {
			try {
				out = new PrintWriter(this.pathFileResults);
			} catch (FileNotFoundException e) {
				System.out.print("ERROR OPENING FILE. RESULTS WILL NOT BE EXPORTED");
				exportResults = false;
			}
		}
	}

	private void closeFile() {
		if (out != null) {
			out.flush();
			out.close();
		}
	}
}