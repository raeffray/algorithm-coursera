
import org.junit.Before;
import org.junit.Test;

public class PercolationTest {
	private Percolation percolation;
	private static final int N_GRID = 1000;

	@Before
	public void prepare() {
		percolation = new Percolation(N_GRID);
	}

	@Test
	public void testOpeningSite() {

		int n = (N_GRID * 2) - 1;
		
		int row = 1;
		int col = 1;
		for (int i = 1; i <= n; i++) {
			if (i % 2 != 0) {
				if(i!=1){
					col++;	
				}
				
			} else {
				row++;
			}
			percolation.open(row, col);
			System.out.println(row + "," + col);
			if(percolation.percolates()){
				System.out.println(percolation.percolates());
				break;
			}
		}

	}

}
