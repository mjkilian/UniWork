
public class ShortTermAnalysis {
	
	/**
	 * Calculates the short term energy of a signal with default window and step size of 1ms. A rectangular window is used.
	 *
	 * @param s sampling of the signal
	 * @return the short term energy sequence for the signal
	 */
	
	private double energyWindow(double sampling[],double start, double windowSize){
		double sum = 0.0;
		for(int i = (int) start; i < start + windowSize ; i++){
			sum += Math.pow(sampling[i],2);
		}
		return sum;
	}
	
	public double[] shortTermEnergy(Sampling s,double windowLength,double stepSize){
		double[] energy = new double[(int) (s.getSampling().length/stepSize)];
		for(double i = 0.0; i < s.getSampling().length + windowLength; i+= stepSize){
			energy[(int) (i/stepSize)] = energyWindow(s.getSampling(),i,windowLength);
		}
		return energy;
	}
	
	/**
	 * Calculates the short term magnitude of a signal with default window and step size of 1ms. A rectangular window is used.
	 *
	 * @param s sampling of the signal
	 * @return the short term magnitude sequence for the signal
	 */
	public double[] shortTermMagnitude(Sampling s){
		double[] magnitude = new double[s.getSampling().length];
		for(int i = 0; i < magnitude.length; i++){
			magnitude[i] =  Math.abs(s.getSampling()[i]);
		}
		return magnitude;
	}
	
	/**
	 * Calculates the short term average zero crossing rate of a signal with default window and step size of 1ms. A rectangular window is used.
	 *
	 * @param s sampling of the signal
	 * @return the short term ZCR sequence for the signal
	 */
	public double[] shortTermAverageZCR(Sampling s){
		double[] zcr = new double[s.getSampling().length];
		for(int i = 1; i < zcr.length; i++){
			zcr[i] = Math.abs(Math.signum(s.getSampling()[i]) - Math.signum(s.getSampling()[i-1]));
		}
		return zcr;
	}
}
