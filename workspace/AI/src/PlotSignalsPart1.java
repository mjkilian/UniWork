import java.awt.GridLayout;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;



/**
 * The Class PlotSignalsPart1. This plots the graphs required for Part 1 of the exercise and displays them
 */
public class PlotSignalsPart1 {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String args[]){
		Sampling s = new Sampling(new File("data/laboratory.dat"),0.3,1000);
		GraphBuilder builder = new GraphBuilder(s);
		
		JFrame frame = new JFrame("Signal Analysis");
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(0,2));
		
		/*Build the normalised graph showing all metrics, then an unnormalised graph for each metric*/
		container.add(builder.buildGraphAllMetrics());
		container.add(builder.buildGraph("Original Signal", "s[t] / "+s.getScalingFactor(), s.getSampling(),1.0));
		container.add(builder.buildGraph("Energy", "E[t] / "+s.getScalingFactor(), s.shortTermAverageEnergy(1, 0.03),1.0));
		container.add(builder.buildGraph("Magnitude","M[t] / "+s.getScalingFactor(),s.shortTermAverageMagnitude(1, 0.03), 1));
		container.add(builder.buildGraph("Average Zero Crossing Rate", "Z[t] / "+s.getScalingFactor(), s.shortTermAverageZeroCrossingRate(1, 0.03), 1));
	
		
		JScrollPane scroll = new JScrollPane(container);
		
	
		frame.add(scroll);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.pack();
		frame.setVisible(true);
		
	}
}
