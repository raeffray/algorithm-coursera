package algorithms.percolation;

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

	private int n;

	/**
	 * create n-by-n grid, with all sites blocked
	 * 
	 * @param n
	 *            matrix dimension (n x n)
	 * 
	 */
	public Percolation(int n) {

		this.n = n;

		int gridSize = (int) Math.pow(n, 2);

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
	 * open site (row, col) if it is not open already
	 * 
	 */
	public void open(int row, int col) {

		int site = makePlainIndex(row, col);
		int[] north = { row - 1, col };
		int[] south = { row + 1, col };
		int[] west = { row, col - 1 };
		int[] east = { row, col + 1 };

		int northIndex = makePlainIndex(north[0], north[1]);
		int southIndex = makePlainIndex(south[0], south[1]);
		int westIndex = makePlainIndex(west[0], west[1]);
		int eastIndex = makePlainIndex(east[0], east[1]);

		if (row == 0) {
			union(site, virtualTopSite);
		} else if (row == n - 1) {
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
	 * is site (row, col) open?
	 * 
	 * O(1)
	 * 
	 */
	public boolean isOpen(int row, int col) {
		return openedSites[makePlainIndex(row, col)] == 1;
	}

	private int makePlainIndex(int row, int col) {
		if (row < 0 || col < 0 || col > n-1 || row > n-1) {
			return -1;
		}
		if (row == 1) {
			return n + col;
		} else if (row == 0) {
			return col;
		} else {
			return (row * n) + col;
		}
	}

	/**
	 * is site (row, col) full?
	 */
	public boolean isFull(int row, int col) {

		return false;
	}

	/**
	 * number of open sites
	 * 
	 */
	public int numberOfOpenSites() {
		return openedSites.length;
	}

	/**
	 * does the system percolate?
	 * 
	 */
	public boolean percolates() {
		return grid[grid.length - 1] == virtualTopSite;
	}

	// TODO: these methods are supposed to be private. they are opened for test
	// purpose. Change it back to private afterwards.

	public void union(final int from, final int to) {
		int rootFrom = root(from);
		int rootTo = root(to);

		if (rootFrom == rootTo) {
			return;
		}
		if (siteWeight[rootFrom] < siteWeight[rootTo]) {
			grid[rootFrom] = rootTo;
			siteWeight[rootTo] += siteWeight[rootFrom];
		} else {
			grid[rootTo] = rootFrom;
			siteWeight[rootFrom] += siteWeight[rootTo];
		}
	}

	public int root(final int site) {
		int root = site;
		while (virtualTopSite != root && virtualBottonSite != root && root != grid[root]) {
			root = grid[root];
		}
		return root;
	}

	public int getVirtualBottonSite() {
		return virtualBottonSite;
	}

	public int getVirtualTopSite() {
		return virtualTopSite;
	}

}