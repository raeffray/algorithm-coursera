import java.io.FileNotFoundException;
import java.io.PrintWriter;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	// grid dimension
	private int n;

	private WeightedQuickUnionUF grid;

	private int virtualTopSite;

	private int virtualBottonSite;

	// the list of opened sites
	private int[] openedSites;

	// flag to export file results
	private boolean exportResults;

	// file to export the results
	private String pathFileResults;

	private PrintWriter out;

	public Percolation(int n) {

		this.n = n;

		int gridSize = (int) Math.pow(n, 2);

		virtualTopSite = gridSize;
		virtualBottonSite = gridSize + 1;

		grid = new WeightedQuickUnionUF(gridSize + 2);

		openedSites = new int[gridSize + 2];

	}

	/**
	 * Additional constructor to be able to configure an export file
	 * 
	 * @param n
	 *            the grid dimension;
	 * @param fileToExportResults
	 *            file containing the results
	 * 
	 */
	public Percolation(final int n, final String fileToExportResults) {
		this(n);
		this.pathFileResults = fileToExportResults;
		this.exportResults = true;
	}

	/**
	 * open site (row, col) if it is not open already
	 * 
	 * @param row
	 *            coordinate's row
	 * @param col
	 *            coordinate's column "row index i out of bounds"
	 * @throws IllegalArgumentException
	 *             throws an {@link IllegalArgumentException} when row or column
	 *             are invalid
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
			grid.union(virtualTopSite, site);
		} else if (row == n) {
			grid.union(virtualBottonSite, site);
		}

		if (northIndex >= 0) {
			if (isOpen(north[0], north[1])) {
				grid.union(northIndex, site);
			}
		}
		if (southIndex >= 0) {
			if (isOpen(south[0], south[1])) {
				grid.union(southIndex, site);
			}
		}
		if (westIndex >= 0) {
			if (isOpen(west[0], west[1])) {
				grid.union(westIndex, site);
			}
		}
		if (eastIndex >= 0) {
			if (isOpen(east[0], east[1])) {
				grid.union(eastIndex, site);
			}
		}

		openedSites[site] = 1;
	}

	/**
	 * Tells if a given coordinate is opened O(1)
	 * 
	 * @param row
	 *            coordinate's row
	 * @param col
	 *            coordinate's column
	 * 
	 * @return a boolean values
	 * 
	 */
	public boolean isOpen(int row, int col) {
		return openedSites[makePlainIndex(row, col)] == 1;
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

		int root = grid.find(site);

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

	public boolean percolates() {
		return grid.find(virtualBottonSite) == grid.find(virtualTopSite);
	} // does the system percolate?

	/**
	 * Transforms a coordinate into plain index
	 * 
	 * @param row
	 *            coordinate's row
	 * @param col
	 *            coordinate's column
	 * 
	 * @return a integer number based on grid's dimension
	 * 
	 */
	private int makePlainIndex(int row, int col) {
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
	 * Run the percolation system, in a full closed grid opening the grid with
	 * random numbers generated within the grid's dimension borders.
	 * 
	 */
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
			printValue(" " + row + " " + col);
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
