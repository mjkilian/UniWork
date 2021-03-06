import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class PlotSignalsPart2 {

	public static void main(String[] args) {
		File input = null;
		int noOfSamples;
		/*Find input file*/
		if(args.length < 2){
			System.err.println("PlotSignalsPart2 noAudioSamples inputFile");
			System.exit(1);
		}
		
		noOfSamples = Integer.parseInt(args[0]);
		input = new File(args[01]);
		
		/*Instantiate file reader*/
		Scanner fileScan = null;
		try {
			fileScan = new Scanner(input);
		} catch (FileNotFoundException e) {
			System.err.println("Could not find or open input file provided");
			e.printStackTrace();
		}
		
		/*instantiate arrays of data*/
		double[] E = new double[noOfSamples];
		double[] M = new double[noOfSamples];
		double[] Z = new double[noOfSamples];
		
		/*Retrieve data from file*/
		if(fileScan.hasNextLine()){
			fileScan.nextLine(); //read off column headers
		}
		
		int currentSample = 0; //index of sample we are currently looking at
		Scanner lineScan; //scans each line
		while(fileScan.hasNextLine() & currentSample < noOfSamples){
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
		
		//E vs M
		container.add(GraphBuilder.buildScatter("E vs M","Signals", "log(M) ", "log(E) ", M, E));
		//E vs Z
		container.add(GraphBuilder.buildScatter("E vs Z", "Signals", "Z", "log(E)", Z, E));
		//M vs Z
		container.add(GraphBuilder.buildScatter("M vs Z", "Signals", "Z","log(M)", Z, M));
		
		
		//pack frame and display
		JScrollPane scroll = new JScrollPane(container);
		frame.add(scroll);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.pack();
		frame.setVisible(true);
	}

}
