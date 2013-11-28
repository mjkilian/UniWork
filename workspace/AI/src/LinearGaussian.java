
// TODO: Auto-generated Javadoc
/**
 * The Class LinearGaussian. Represents a Linear Gaussian distribution
 */
public class LinearGaussian {
	
	/** The values. */
	private double[] values;
	
	/** The mean. */
	private double mean;
	
	/** The variance. */
	private double variance;
	
	/**
	 * Works out the (Ei - m)^2 term for each value
	 * The produced array can be used to calculate the variance by finding its average.
	 * Note it returns a copy of the array.
	 *
	 * @return the double[]
	 */
	private double[] varianceArray(){
		double[] varianceVals = new double[values.length];
		for(int i = 0; i < values.length; i++){
			varianceVals[i] = Math.pow(values[i] - mean , 2.0);
		}
		return varianceVals;
	}
	
	/**
	 * Instantiates a new linear gaussian.
	 *
	 * @param values the values
	 */
	public LinearGaussian(double[] values) {
		super();
		this.values = values;
		
		/*calculate mean*/
		mean = Utilities.averageValueOfArray(values);
		variance = Utilities.averageValueOfArray(varianceArray());
	}
	
	/**
	 * Calculates the probability of a point x belonging to class C.
	 *
	 * @param x the x
	 * @return the probability
	 */
	public double probability(double x){
		double firstTerm = 1.0 / Math.sqrt(2 * Math.PI * variance);
		double secondTerm = Math.exp( -(Math.pow((x-mean),2.0)) / (2 * variance) );
		return firstTerm * secondTerm;
	}

	/**
	 * Gets the values.
	 *
	 * @return the values
	 */
	public double[] getValues() {
		return values;
	}

	/**
	 * Gets the mean.
	 *
	 * @return the mean
	 */
	public double getMean() {
		return mean;
	}

	/**
	 * Gets the variance.
	 *
	 * @return the variance
	 */
	public double getVariance() {
		return variance;
	}
	
	
	
}
