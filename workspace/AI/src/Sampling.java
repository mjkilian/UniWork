import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


// TODO: Auto-generated Javadoc
/**
 * The Class Sampling. Represents a sampling of sound over a given time period
 */
public class Sampling {
	
	/** The sampling, where each entry is in decibels */
	private int[] sampling;
	
	/** The sampling rate. */
	private float samplingRate;
	
	/**
	 * Instantiates a new sampling.
	 *
	 * @param file the input file for the sampling
	 * @param len the length of the sampling in milliseconds
	 */
	public Sampling(File file, float len){
		
		//instantiate file reader
		Scanner reader = null;
		try {
			reader = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//take in samples
		List<Integer> sampleQueue = new ArrayList<Integer>(1000);
		while(reader.hasNextLine()){
			sampleQueue.add(Integer.parseInt(reader.nextLine()));
		}
		
		this.sampling = new int[sampleQueue.size()];
		for(int i = 0; i < sampleQueue.size(); i++){
			sampling[i] = sampleQueue.remove(0);
		}
		sampleQueue = null;
		
		/*calculate sampling frequency (the 1000 is to convert correctly from milliseconds)*/
		this.samplingRate = (sampling.length / len) * 1000;
	}

	/**
	 * Gets the sampling data.
	 *
	 * @return the sampling
	 */
	public int[] getSampling() {
		return sampling;
	}

	/**
	 * Gets the sampling rate in Hz.
	 *
	 * @return the sampling rate
	 */
	public float getSamplingRate() {
		return samplingRate;
	}

	
	
	
}