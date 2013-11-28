import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class SignalAnalyser {
	public static final double SCALING_FACTOR = 1000; //scaling factor for signals
	public static final double SAMPLE_LEN  = 0.3; //number of seconds each sample lasts
	public static final int STEP = 1; //number of milliseconds to step between each short term analysis
	public static final double WINDOW_LENGTH = 0.03; //length of analysis window in seconds (30ms)

	public static void main(String[] args) {
		
		File speechDirectory = new File("data/speechdata");
		File silenceDirectory = new File("data/silencedata");
		File output = new File("data/100SignalAnalysis.txt");
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		/*instantiate file writer*/
		try {
			if (!output.exists()) {
				output.createNewFile();
			}
			fw = new FileWriter(output,true);
			bw = new BufferedWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*write column headers*/
		try {
			bw.write("E,M,Z\n");
		} catch (IOException e1) {
			System.err.println("Problem writing column headers to output file");
			e1.printStackTrace();
		}
		/*analyse speech data*/
		if(!speechDirectory.isDirectory()){
			System.err.println("Directory for speech data not found");
			System.exit(1);
		}
		for(File speechfile : speechDirectory.listFiles()){
			Sampling speechSample = new Sampling(speechfile,SAMPLE_LEN,SCALING_FACTOR);
			double E = speechSample.averageLogarithmicValueOfSignal(speechSample.shortTermAverageEnergy(STEP, WINDOW_LENGTH));
			double M = speechSample.averageLogarithmicValueOfSignal(speechSample.shortTermAverageMagnitude(STEP, WINDOW_LENGTH));
			double Z = speechSample.averageValueOfSignal(speechSample.shortTermAverageZeroCrossingRate(STEP, WINDOW_LENGTH));
			try {
				bw.write(E + "," + M + "," + Z + "\n");
			} catch (IOException e) {
				System.err.println("Could not append speech data from " + speechfile.getName() + " to output.");
				e.printStackTrace();
			}
		}
		
		/*analyse silence data*/
		if(!silenceDirectory.isDirectory()){
			System.err.println("Directory for silence data not found");
			System.exit(1);
		}
		for(File silencefile : silenceDirectory.listFiles()){
			Sampling silenceSample = new Sampling(silencefile,SAMPLE_LEN,SCALING_FACTOR);
			double E = silenceSample.averageLogarithmicValueOfSignal(silenceSample.shortTermAverageEnergy(STEP, WINDOW_LENGTH));
			double M = silenceSample.averageLogarithmicValueOfSignal(silenceSample.shortTermAverageMagnitude(STEP, WINDOW_LENGTH));
			double Z = silenceSample.averageValueOfSignal(silenceSample.shortTermAverageZeroCrossingRate(STEP, WINDOW_LENGTH));
			try {
				bw.write(E + "," + M + "," + Z + "\n");
			} catch (IOException e) {
				System.err.println("Could not append silence data from " + silencefile.getName() + " to output.");
				e.printStackTrace();
			}
		}
		
		try {
			bw.close();
		} catch (IOException e) {
			System.out.println("Could not close file writer");
			e.printStackTrace();
		}
	}

}
