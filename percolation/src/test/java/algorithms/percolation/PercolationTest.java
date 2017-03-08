package algorithms.percolation;

import org.junit.Before;
import org.junit.Test;

import org.junit.Assert;

public class PercolationTest {

	private Percolation percolation;

	@Before
	public void prepare() {
		percolation = new Percolation(5);
	}

	@Test
	public void testFindBoundarieTopMin() {
		int root = percolation.root(0);
		Assert.assertEquals(percolation.getVirtualTopSite(), root);
	}

	@Test
	public void testFindBoundarieTopMax() {
		int root = percolation.root(4);
		Assert.assertEquals(percolation.getVirtualTopSite(), root);
	}

	@Test
	public void testFindBoundarieBottonMin() {
		int root = percolation.root(20);
		Assert.assertEquals(percolation.getVirtualBottonSite(), root);
	}

	@Test
	public void testFindBoundarieBottonMax() {
		int root = percolation.root(24);
		Assert.assertEquals(percolation.getVirtualBottonSite(), root);
	}

	@Test
	public void testUnionToBotton() {
		percolation.union(10, 24);
		Assert.assertEquals(26, percolation.root(10));
	}

	@Test
	public void testUnionToTop() {
		percolation.union(1, 9);
		Assert.assertEquals(25, percolation.root(9));
	}

	@Test
	public void testIsOpenFalse() {
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
	public void testPercolate() {
		percolation.open(0, 0);
		Assert.assertFalse(percolation.percolates());
		percolation.open(1, 0);
		Assert.assertFalse(percolation.percolates());
		percolation.open(2, 0);
		Assert.assertFalse(percolation.percolates());
		percolation.open(2, 1);
		Assert.assertFalse(percolation.percolates());
		percolation.open(3, 1);
		Assert.assertFalse(percolation.percolates());
		percolation.open(4, 1);
		Assert.assertTrue(percolation.percolates());
	}

	// @Test
	public void testOpenTopLeftCorner() {

		Assert.assertFalse(percolation.isOpen(3, 3));

		percolation.open(3, 3);

		Assert.assertTrue(percolation.isOpen(3, 3));

	}

	// @Test
	public void testOpenBottonLeftCorner() {
		percolation.open(5, 0);

	}
}
