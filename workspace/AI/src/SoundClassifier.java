import java.util.HashMap;
import java.util.Map;


/**
 * The Class SoundClassifier. Classifies sound samples as either speech or silence.
 */
public class SoundClassifier {
	
	/** A map of metrics to Linear Gaussians which allow estimation of the probability that a sample belongs to class Speech. */
	private Map<String,LinearGaussian> speechGaussians;
	
	/** A map of metrics to Linear Gaussians which allow estimation of the probability that a sample belongs to class Silence. */
	private Map<String,LinearGaussian> silenceGaussians;
	
	/** The prior probability that a sample is speech. */
	private double priorProbabilitySpeech = 0.5;
	
	/**
	 * Instantiates a new sound classifier.
	 *
	 * @param priorProbabilitySpeech the prior probability speech
	 * @param speechE E values for speech training data
	 * @param speechM M values for speech training data
	 * @param speechZ Z values for speech training data
	 * @param silenceE E values for silence training data
	 * @param silenceM M values for silence training data
	 * @param silenceZ Z values for silence training data
	 */
	public SoundClassifier(double priorProbabilitySpeech, double[] speechE, double[] speechM, double[] speechZ, double[] silenceE, double[] silenceM, double[] silenceZ){
		this.priorProbabilitySpeech = priorProbabilitySpeech;
		speechGaussians = new HashMap<String,LinearGaussian>();
		silenceGaussians = new HashMap<String,LinearGaussian>();
		
		speechGaussians.put("E", new LinearGaussian(speechE));
		speechGaussians.put("M", new LinearGaussian(speechM));
		speechGaussians.put("Z", new LinearGaussian(speechZ));

		silenceGaussians.put("E", new LinearGaussian(silenceE));
		silenceGaussians.put("M", new LinearGaussian(silenceM));
		silenceGaussians.put("Z", new LinearGaussian(silenceZ));
	
	}
	
	/**
	 * Classify a sample based on its E,M and Z parameters.
	 *
	 * @param E E value for the sample
	 * @param M M value for the sample
	 * @param Z Z value for the sample
	 * @return "Speech" if the sample is classified as speech and "Silence" if it is classified as silence
	 */
	public String classify( double E, double M, double Z){
		double probabilitySpeech; 
		double probabilitySilence;
		double pE; //p(E)
		double pM; //p(M)
		double pZ; //p(Z)
		
		/*Using a Naive Bayes method we can work out the probability that a file belongs to a class C (either speech or silence)
		 * using the following:
		 * P(C|E,M,Z) = aP(C)P(E,M,Z|C)
		 * where a is the normalization factor
		 * 
		 * Using an independace assumpting we can make the following simplification
		 * P(E,M,Z|C) = P(E|C)P(M|C)P(Z|C)
		 * 
		 * We can estimate each of these values from our gaussian distributions
		 * 
		 */
		
		/*calculate probability that the file is an example of speech*/
		pE = speechGaussians.get("E").probability(E);
		pM = speechGaussians.get("M").probability(M);
		pZ = speechGaussians.get("Z").probability(Z);
	
		probabilitySpeech = (pE * pM * pZ * priorProbabilitySpeech)  ; 
	
		/*calculate probability of silence*/
		pE = silenceGaussians.get("E").probability(E);
		pM = silenceGaussians.get("M").probability(M);
		pZ = silenceGaussians.get("Z").probability(Z);
		
		probabilitySilence = (pE * pM * pZ * (1 - priorProbabilitySpeech));
		
		//System.out.println("P(speech): " + probabilitySpeech + " P(silence): " + probabilitySilence);
		if(probabilitySpeech >= probabilitySilence){
			return "Speech";
		}
		return "Silence";
		
		
	}

}
