
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final String CARRIAGE_RETURN = "\n";

    private double[] results;

    private int iterations;

    private int gridDimension;

    // flag to export statistics to a file
    private boolean exportStatResults;

    // flag to export coordinates to a file
    private boolean exportCoorResults;

    // file to export the results
    private String pathStatFileResults;

    // file to export the results
    private String pathCooFileResults;

    private Out outStat;

    private Out outCoor;

    private Percolation[] percolations;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || n >= 10000 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        gridDimension = n;
        iterations = trials;
        results = new double[iterations];

        percolations = new Percolation[iterations];
        for (int i = 0; i < iterations; i++) {
            percolations[i] = new Percolation(gridDimension);
        }

    }

    /**
     * sample mean of percolation threshold
     * 
     */
    public double mean() {
        return StdStats.mean(results);
    }

    /**
     * sample standard deviation of percolation threshold
     * 
     */
    public double stddev() {
        return StdStats.stddev(results);
    }

    /**
     * low endpoint of 95% confidence interval
     * 
     */
    public double confidenceLo() {
        return mean() - ((1.96d * (stddev())) / Math.sqrt(iterations));
    }

    /**
     * high endpoint of 95% confidence interval
     * 
     */
    public double confidenceHi() {
        return mean() + ((1.96d * (stddev())) / Math.sqrt(iterations));
    }

    public static void main(String[] args) {

        double gridSize = 0;

        if (args.length < 2) {

            StringBuffer buffer = new StringBuffer();

            buffer.append("insuficient arguments: ").append(CARRIAGE_RETURN);
            buffer.append("************ USAGE *************").append(CARRIAGE_RETURN);
            buffer.append(PercolationStats.class.getName())
                    .append(" <grid dimension[required]> <# of iteractions[required]> ")
                    .append("<export-statistic-results-file[optional]> <export-coordinates-results-file[optional]>")
                    .append(CARRIAGE_RETURN);
            buffer.append("*******************************").append(CARRIAGE_RETURN);

            throw new IllegalArgumentException(buffer.toString());
        }

        int dimension = Integer.parseInt(args[0]);
        int iterations = Integer.parseInt(args[1]);
        String pathStat = null;
        String pathCoor = null;

        if (args.length >= 3) {
            pathStat = args[2];
        }

        if (args.length == 4) {
            pathCoor = args[3];
        }

        PercolationStats percolationStats = new PercolationStats(dimension, iterations);
        percolationStats.setPathCooFileResults(pathCoor);
        percolationStats.setPathStatFileResults(pathStat);

        double[] results = new double[iterations];

        for (int i = 0; i < iterations; i++) {
            // if has a third parameter for exporting
            // coordinates results
            Percolation percolation = percolationStats.getPercolations()[i];
            percolationStats.runPercolation(percolation);
            gridSize = Math.pow(dimension, 2);
            results[i] = (percolation.numberOfOpenSites() / gridSize);
        }

        percolationStats.setResults(results);

        StringBuffer buffer = new StringBuffer();

        buffer.append("mean                    = ").append(percolationStats.mean()).append(CARRIAGE_RETURN);
        buffer.append("stddev                  = ").append(percolationStats.mean()).append(CARRIAGE_RETURN);
        buffer.append("mean                    = ").append(percolationStats.mean()).append(CARRIAGE_RETURN);
        buffer.append("95% confidence interval = [").append(percolationStats.confidenceLo()).append(", ")
                .append(percolationStats.confidenceHi()).append("]").append(CARRIAGE_RETURN);
        // buffer.append(CARRIAGE_RETURN);
        // buffer.append("iterations =
        // ").append(iterations).append(CARRIAGE_RETURN);
        // buffer.append("dimension =
        // ").append(gridSize).append(CARRIAGE_RETURN);
        // buffer.append("execution time (ms) =
        // ").append(System.currentTimeMillis() -
        // begin).append(CARRIAGE_RETURN);

        if (percolationStats.isExportStatResults()) {
            percolationStats.printValue(buffer.toString());
        } else {
            System.out.println(buffer.toString());
        }
        percolationStats.closeFiles();
    }

    private void printValue(final String value) {
        if (exportStatResults) {
            outStat.print(value);
        }
    }

    private void printSingleValueCoor(final String value) {
        if (exportCoorResults) {
            outCoor.print(value);
        }
    }

    private void printCoordinates(final int row, final int col) {
        if (exportCoorResults) {
            outCoor.print(" " + row + " " + col);
        }
    }

    private void openFiles() {
        if (outStat == null && exportStatResults) {
            outStat = new Out(pathStatFileResults);
        }
        if (outCoor == null && exportCoorResults) {
            outCoor = new Out(pathCooFileResults);
        }
    }

    private void closeFiles() {
        if (outStat != null) {
            outStat.close();
        }
        if (outCoor != null) {
            outCoor.close();
        }
    }

    /**
     * Run the percolation system, in a full closed grid opening the grid with
     * random numbers generated within the grid's dimension borders.
     * 
     */
    private void runPercolation(Percolation percolation) {
        int row = -1;
        int col = -1;
        printSingleValueCoor(String.valueOf(percolation));
        while (!percolation.percolates()) {
            row = StdRandom.uniform(getGridDimension() + 1);
            col = StdRandom.uniform(getGridDimension() + 1);
            row = row == 0 ? 1 : row;
            col = col == 0 ? 1 : col;
            if (row > 0 && col > 0) {
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                    printCoordinates(row, col);
                }
            }
        }
    }

    private int getGridDimension() {
        return gridDimension;
    }

    private boolean isExportStatResults() {
        return exportStatResults;
    }

    private void setPathStatFileResults(String pathStatFileResults) {
        if (pathStatFileResults != null) {
            this.pathStatFileResults = pathStatFileResults;
            exportStatResults = true;
            openFiles();
        }
    }

    private void setPathCooFileResults(String pathCooFileResults) {
        if (pathCooFileResults != null) {
            this.pathCooFileResults = pathCooFileResults;
            exportCoorResults = true;
            openFiles();
        }
    }

    private void setResults(double[] results) {
        this.results = results;
    }

    private Percolation[] getPercolations() {
        return percolations;
    }

}
