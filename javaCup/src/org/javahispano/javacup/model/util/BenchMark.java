/**
 * 
 */
package org.javahispano.javacup.model.util;

/**
 * @author adou
 *
 */
public class BenchMark {
	private double benchMark;
	
	public BenchMark() {
	    long t1 = System.nanoTime();

	    int result = 0;
	    for (int i = 0; i < 1000 * 1000; i++) {    // sole loop
	        result += sum();
	    }

	    long t2 = System.nanoTime();
	    
	    benchMark = ((t2 - t1) * 1e-9);
	}
	
	private static int sum() {
	    int sum = 0;
	    for (int j = 0; j < 10 * 1000; j++) {
	        sum += j;
	    }
	    return sum;
	}

	/**
	 * @return the benchMark
	 */
	public double getBenchMark() {
		return benchMark;
	}

}
