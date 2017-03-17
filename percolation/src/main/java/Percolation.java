import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int n;

    private WeightedQuickUnionUF grid;

    private int virtualTopSite;

    private int virtualBottonSite;

    // the list of opened sites
    private int[] openedSites;

    private int countOpenedSites;
    
    private int countGridUtilization;

    public Percolation(int n) {
        if (n <= 0 || n >= 10000) {
            throw new IllegalArgumentException();
        }

        this.n = n;

        int gridSize = (int) Math.pow(n, 2);

        virtualTopSite = gridSize;
        virtualBottonSite = gridSize + 1;

        grid = new WeightedQuickUnionUF(gridSize + 2);

        openedSites = new int[gridSize + 2];

        countOpenedSites = 0;

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
            throw new IndexOutOfBoundsException();
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
            countGridUtilization++;
        } else if (row == n) {
            grid.union(virtualBottonSite, site);
            countGridUtilization++;
        }

        if (northIndex >= 0) {
            if (isOpen(north[0], north[1])) {
                grid.union(northIndex, site);
                countGridUtilization++;
            }
        }
        if (southIndex >= 0) {
            if (isOpen(south[0], south[1])) {
                grid.union(southIndex, site);
                countGridUtilization++;
            }
        }
        if (westIndex >= 0) {
            if (isOpen(west[0], west[1])) {
                grid.union(westIndex, site);
                countGridUtilization++;
            }
        }
        if (eastIndex >= 0) {
            if (isOpen(east[0], east[1])) {
                grid.union(eastIndex, site);
                countGridUtilization++;
            }
        }

        if(openedSites[site] == 0){
        	countOpenedSites++;	
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
     * Tells if a coordinate is connected to the virtual roots, either top or
     * bottom
     * 
     * @param row
     *            coordinate's row
     * @param col
     *            coordinate's column
     * 
     * @return boolean value
     * 
     */
    public boolean isFull(int row, int col) {

        int site = makePlainIndex(row, col);

        int topRoot = grid.find(virtualTopSite);
        countGridUtilization++;

        int root = grid.find(site);
        countGridUtilization++;

        return root == topRoot;
    }

    /**
     * retrieves the total of opened sites
     * 
     * @return the count
     * 
     */
    public int numberOfOpenSites() {
        return countOpenedSites;
    }

    public boolean percolates() {
        if (countOpenedSites > 0) {
        	countGridUtilization+=2;
            return grid.find(virtualBottonSite) == grid.find(virtualTopSite) || n == 1;
        }
        return false;
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

    public static void main(String[] args) {

        Percolation percolation = null;
        int row = -1;
        int col = -1;
        int n = -1;
        In in = new In(args[0]);

        boolean first = true;

        int count = 1;

        while (in.hasNextLine()) {
            String line = in.readLine();
            if (first) {
                n = Integer.parseInt(line);
                percolation = new Percolation(n);
                first = false;
                continue;
            }

            while (!percolation.percolates()) {
            	System.out.println(line);
                String[] rawConf = line.trim().split("\\s{1,1000}");
                row = Integer.parseInt(rawConf[0].trim());
                col = Integer.parseInt(rawConf[1].trim());
                if (row > 0 && col > 0) {
                    if (!percolation.isOpen(row, col)) {
                        percolation.open(row, col);
                        count++;
                    }
                }
                line = in.readLine();
            }
            break;
        }

    }
}
