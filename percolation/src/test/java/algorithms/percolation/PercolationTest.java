package algorithms.percolation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import edu.princeton.cs.algs4.StdRandom;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PercolationTest {

	private Percolation percolation;
	
	private static final int N_GRID = 10000;

	@Before
	public void prepare() {
		percolation = new Percolation(N_GRID);
	}

	@Test
	public void testFindBoundarieTopMin() {
		percolation.open(0, 0);
		int root = percolation.root(0);
		Assert.assertEquals(percolation.getVirtualTopSite(), root);
	}

	@Test
	public void testFindBoundarieTopMax() {
		percolation.open(0, N_GRID-1);
		int root = percolation.root(N_GRID-1);
		Assert.assertEquals(percolation.getVirtualTopSite(), root);
	}

	@Test
	public void testFindBoundarieBottonMin() {
		percolation.open(N_GRID-1,0);
		int root = percolation.root((int)Math.pow(N_GRID,2) - N_GRID);
		Assert.assertEquals(percolation.getVirtualBottonSite(), root);
	}

    @Test
	public void testFindBoundarieBottonMax() {
    	percolation.open(N_GRID-1,N_GRID-1);
		int root = percolation.root((int)Math.pow(N_GRID,2) - 1);
		Assert.assertEquals(percolation.getVirtualBottonSite(), root);
	}

	//@Test
	public void testUnionToBotton() {
		percolation.union(10, 24);
		Assert.assertEquals(26, percolation.root(10));
	}

	//@Test
	public void testUnionToTop() {
		percolation.union(1, 9);
		Assert.assertEquals(25, percolation.root(9));
	}

	//@Test
	public void aaa_testIsOpenFalse() {
		Assert.assertFalse(percolation.isOpen(1, 0));
		Assert.assertFalse(percolation.isOpen(2, 0));
		Assert.assertFalse(percolation.isOpen(2, 1));
		Assert.assertFalse(percolation.isOpen(3, 1));
		Assert.assertFalse(percolation.isOpen(4, 1));
		percolation.open(0, 0);
		percolation.open(1, 0);
		percolation.open(2, 0);
		percolation.open(2, 1);
		percolation.open(2, 4);
		percolation.open(3, 1);
		percolation.open(4, 1);
		Assert.assertTrue(percolation.isOpen(1, 0));
		Assert.assertTrue(percolation.isOpen(2, 0));
		Assert.assertTrue(percolation.isOpen(2, 1));
		Assert.assertTrue(percolation.isOpen(3, 1));
		Assert.assertTrue(percolation.isOpen(4, 1));

	}

	@Test
	public void aab_testPercolate() {
		
		int arraySize = N_GRID - 1;
		percolation.open(arraySize, 0);
		
		for (int i = 0; i < N_GRID - 1; i++) {
			percolation.open(i, 0);
			if(i == (arraySize - 1)){
				Assert.assertTrue(percolation.percolates());
			} else {
				Assert.assertFalse(percolation.percolates());
			}
		}
	}

	//@Test
	public void aac_testOpenBottonLeftCorner() {
		for (int i = 0; i < 1000; i++) {
			System.out.println(StdRandom.uniform(20));			
		}

	}
}
