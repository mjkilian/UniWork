
public class ShortTermAnalysis {
	
	/**
	 * Calculates the short term energy of a signal with default window and step size of 1ms. A rectangular window is used.
	 *
	 * @param s sampling of the signal
	 * @return the short term energy sequence for the signal
	 */
	public int[] shortTermEnergy(Sampling s){
		int[] energy = new int[s.getSampling().length];
		for(int i = 0; i < energy.length; i++){
			energy[i] = (int) Math.pow(s.getSampling()[i],2);
		}
		return energy;
	}
	
	/**
	 * Calculates the short term magnitude of a signal with default window and step size of 1ms. A rectangular window is used.
	 *
	 * @param s sampling of the signal
	 * @return the short term magnitude sequence for the signal
	 */
	public int[] shortTermMagnitude(Sampling s){
		int[] magnitude = new int[s.getSampling().length];
		for(int i = 0; i < magnitude.length; i++){
			magnitude[i] = (int) Math.abs(s.getSampling()[i]);
		}
		return magnitude;
	}
	
	/**
	 * Calculates the short term average zero crossing rate of a signal with default window and step size of 1ms. A rectangular window is used.
	 *
	 * @param s sampling of the signal
	 * @return the short term ZCR sequence for the signal
	 */
	public int[] shortTermAverageZCR(Sampling s){
		int[] zcr = new int[s.getSampling().length];
		for(int i = 1; i < zcr.length; i++){
			zcr[i] = (int) Math.abs(Math.signum(s.getSampling()[i]) - Math.signum(s.getSampling()[i-1]));
		}
		return zcr;
	}
}