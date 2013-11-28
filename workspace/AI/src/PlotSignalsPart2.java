import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;



/**
 * The Class PlotSignalsPart2. Plots the graphs for part2 (log(E) vs log(M), log(E) vs Z, log(M) vs Z)
 */
public class PlotSignalsPart2 {

	/**
	 * The main method.
	 *
	 * @param args must take as args the number of speech samples, the number of silence samples and an input file in that order
	 */
	public static void main(String[] args) {
		File input = null;
		int noSpeech; //how many of the files are speech
		int noSilence;//how many are silence
		int totalSignals;
		/*Find input file*/
		if(args.length < 3){
			System.err.println("PlotSignalsPart2 noSpeech noSilence inputFile");
			System.exit(1);
		}
		
		noSpeech = Integer.parseInt(args[0]);
		noSilence = Integer.parseInt(args[1]);
		totalSignals = noSpeech + noSilence;
		input = new File(args[2]);
		
		/*Instantiate file reader*/
		Scanner fileScan = null;
		try {
			fileScan = new Scanner(input);
		} catch (FileNotFoundException e) {
			System.err.println("Could not find or open input file provided");
			e.printStackTrace();
		}
		
		/*instantiate arrays of data*/
		double[] E = new double[totalSignals];
		double[] M = new double[totalSignals];
		double[] Z = new double[totalSignals];
	
		
		/*Retrieve data from file*/
		if(fileScan.hasNextLine()){
			fileScan.nextLine(); //read off column headers
		}
		
		int currentSample = 0; //index of sample we are currently looking at
		Scanner lineScan; //scans each line
		while(fileScan.hasNextLine() & currentSample < totalSignals){
			String line = fileScan.nextLine();
			lineScan = new Scanner(line);
			lineScan.useDelimiter(",");
			E[currentSample] = lineScan.nextDouble();
			M[currentSample] = lineScan.nextDouble();
			Z[currentSample] = lineScan.nextDouble();
			currentSample++;
			lineScan.close();
		}	
		
		
		
		
		
		
		/*Plot graphs*////////////////////////////////////////////////////////////
		//initiliase to display graphs
		JFrame frame = new JFrame("Part 2 Signal Analysis");
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(0,2));
		String[] datasetLabels = {"Speech","Silence"};
		int[] datasetLens = {noSpeech,noSilence};
		//E vs M
		container.add(GraphBuilder.buildScatter("E vs M",datasetLabels,datasetLens, "log(M) ", "log(E) ", M, E));
		//E vs Z
		container.add(GraphBuilder.buildScatter("E vs Z", datasetLabels,datasetLens, "Z", "log(E)", Z, E));
		//M vs Z
		container.add(GraphBuilder.buildScatter("M vs Z", datasetLabels,datasetLens, "Z","log(M)", Z, M));
		
		
		//pack frame and display
		JScrollPane scroll = new JScrollPane(container);
		frame.add(scroll);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.pack();
		frame.setVisible(true);
	}

}