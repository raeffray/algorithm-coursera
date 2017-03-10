package algorithms.percolation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PercolationTest_ {

//	private Percolation percolation;
//
//	private static final int N_GRID = 10000;
//
//	@Before
//	public void prepare() {
//		percolation = new Percolation(N_GRID);
//	}
//
//	// @Test
//	public void testRowColConvertion() {
//		int makePl	ainIndex = percolation.makePlainIndex(0, 0);
//		Assert.assertEquals(-1, makePlainIndex);
//		makePlainIndex = percolation.makePlainIndex(1, 1);
//		Assert.assertEquals(0, makePlainIndex);
//		makePlainIndex = percolation.makePlainIndex(1, 5);
//		Assert.assertEquals(4, makePlainIndex);
//		makePlainIndex = percolation.makePlainIndex(2, 1);
//		Assert.assertEquals(5, makePlainIndex);
//		makePlainIndex = percolation.makePlainIndex(2, 2);
//		Assert.assertEquals(6, makePlainIndex);
//		makePlainIndex = percolation.makePlainIndex(2, 3);
//		Assert.assertEquals(7, makePlainIndex);
//		makePlainIndex = percolation.makePlainIndex(2, 4);
//		Assert.assertEquals(8, makePlainIndex);
//		makePlainIndex = percolation.makePlainIndex(2, 5);
//		Assert.assertEquals(9, makePlainIndex);
//		makePlainIndex = percolation.makePlainIndex(3, 1);
//		Assert.assertEquals(10, makePlainIndex);
//		makePlainIndex = percolation.makePlainIndex(3, 3);
//		Assert.assertEquals(12, makePlainIndex);
//		makePlainIndex = percolation.makePlainIndex(3, 2);
//		Assert.assertEquals(11, makePlainIndex);
//		makePlainIndex = percolation.makePlainIndex(5, 1);
//		Assert.assertEquals(20, makePlainIndex);
//		makePlainIndex = percolation.makePlainIndex(5, 4);
//		Assert.assertEquals(23, makePlainIndex);
//		makePlainIndex = percolation.makePlainIndex(5, 5);
//		Assert.assertEquals(24, makePlainIndex);
//	}
//
//	// @Test
//	// public void testFindBoundarieTopMin() {
//	// percolation.open(0, 0);
//	// int root = percolation.root(0,0);
//	// Assert.assertEquals(percolation.getVirtualTopSite(), root);
//	// }
//	//
//	// @Test
//	// public void testFindBoundarieTopMax() {
//	// percolation.open(0, N_GRID-1);
//	// int root = percolation.root(0, N_GRID-1);
//	// Assert.assertEquals(percolation.getVirtualTopSite(), root);
//	// }
//	//
//	// @Test
//	// public void testFindBoundarieBottonMin() {
//	// percolation.open(N_GRID-1,0);
//	// int root = percolation.root(N_GRID-1,0);
//	// Assert.assertEquals(percolation.getVirtualBottonSite(), root);
//	// }
//	//
//	// @Test
//	// public void testFindBoundarieBottonMax() {
//	// percolation.open(N_GRID-1,N_GRID-1);
//	// int root = percolation.root(N_GRID-1,N_GRID-1);
//	// Assert.assertEquals(percolation.getVirtualBottonSite(), root);
//	// }
//	//
//	// //@Test
//	// public void testUnionToBotton() {
//	// percolation.union(10, 24);
//	// Assert.assertEquals(26, percolation.root(10));
//	// }
//	//
//	// //@Test
//	// public void testUnionToTop() {
//	// percolation.union(1, 9);
//	// Assert.assertEquals(25, percolation.root(9));
//	// }
//	//
//	// //@Test
//	// public void aaa_testIsOpenFalse() {
//	// Assert.assertFalse(percolation.isOpen(1, 0));
//	// Assert.assertFalse(percolation.isOpen(2, 0));
//	// Assert.assertFalse(percolation.isOpen(2, 1));
//	// Assert.assertFalse(percolation.isOpen(3, 1));
//	// Assert.assertFalse(percolation.isOpen(4, 1));
//	// percolation.open(0, 0);
//	// percolation.open(1, 0);
//	// percolation.open(2, 0);
//	// percolation.open(2, 1);
//	// percolation.open(2, 4);
//	// percolation.open(3, 1);
//	// percolation.open(4, 1);
//	// Assert.assertTrue(percolation.isOpen(1, 0));
//	// Assert.assertTrue(percolation.isOpen(2, 0));
//	// Assert.assertTrue(percolation.isOpen(2, 1));
//	// Assert.assertTrue(percolation.isOpen(3, 1));
//	// Assert.assertTrue(percolation.isOpen(4, 1));
//	//
//	// }
//	//
//	// @Test
//	// public void aab_testPercolate() {
//	//
//	// int arraySize = N_GRID - 1;
//	// percolation.open(arraySize, 0);
//	//
//	// for (int i = 0; i < N_GRID - 1; i++) {
//	// percolation.open(i, 0);
//	// if(i == (arraySize - 1)){
//	// Assert.assertTrue(percolation.percolates());
//	// } else {
//	// Assert.assertFalse(percolation.percolates());
//	// }
//	// }
//	// }
//	//
//	// @Test
//	// public void aac_testOpenBottonLeftCorner() {
//	// int row = -1;
//	// int col = -1;
//	// long a = 0;
//	// long b = 0;
//	// int count = 0;
//	// int countInvalidSite = 0;
//	//
//	//
//	// Stopwatch sw = new Stopwatch();
//	//
//	// while(!percolation.percolates()){
//	// row = StdRandom.uniform(N_GRID);
//	// col = StdRandom.uniform(N_GRID);
//	// if(!percolation.isOpen(row, col)){
//	// percolation.open(row, col);
//	// count++;
//	// if(count%1000000 == 0){
//	// //b =System.currentTimeMillis();
//	// //System.out.println((b-a));
//	// //a = System.currentTimeMillis();
//	// }
//	// }{
//	// countInvalidSite++;
//	// if(count%1000000 == 0){
//	// //b =System.currentTimeMillis();
//	// //System.out.println((b-a));
//	// //a = System.currentTimeMillis();
//	// }
//	// }
//	// }
//	//
//	// b =System.currentTimeMillis();
//	// System.out.println((b-a));
//	//
//	// System.out.println("count: " + count);
//	// System.out.println("wasted numbers: " + countInvalidSite);
//	// System.out.println("opened site: " + percolation.numberOfOpenSites());
//	//
//	//
//	// }
//	//
//
//	@Test
//	public void aab_testPercolate() {
//		percolation.open(N_GRID, 1);
//		for (int i = 1; i <= N_GRID; i++) {
//			percolation.open(i, 1);
//			if (i == N_GRID - 1) {
//				Assert.assertTrue(percolation.percolates());
//			} else {
//				Assert.assertFalse(percolation.percolates());
//			}
//		}
//	}
//
//	@Test
//	public void aac_testOpenBottonLeftCorner() {
//		int row = -1;
//		int col = -1;
//		long a = 0;
//		long b = 0;
//		int count = 0;
//		int countInvalidSite = 0;
//		//
//		//
//		Stopwatch sw = new Stopwatch();
//		//
//		a = System.currentTimeMillis();
//		while (!percolation.percolates()) {
//			row = StdRandom.uniform(N_GRID + 1);
//			col = StdRandom.uniform(N_GRID + 1);
//			row = row == 0 ? 1 : row;
//			col = col == 0 ? 1 : col;
//			if (row > 0 && col > 0) {
//				if (!percolation.isOpen(row, col)) {
//					percolation.open(row, col);
//					count++;
//					if (count % 1000000 == 0) {
//						//b = System.currentTimeMillis();
//						//System.out.println((b - a));
//						//a = System.currentTimeMillis();
//						System.out.println(count);
//					}
//				} else {
//					countInvalidSite++;
//				}
//			}
//		}
//		//
//		b = System.currentTimeMillis();
//		System.out.println((b - a)/1000);
//		//
//		System.out.println("count: " + count);
//		System.out.println("wasted numbers: " + countInvalidSite);
//		System.out.println("opened site: " + percolation.numberOfOpenSites());
//		//
//		//
//	}
//
}
