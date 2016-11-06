package test;

import graph.GeneralXYDataset;

import java.awt.Dimension;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;

import utilities.MathUtilities;

public class TestNormalDistributionPlot {

	public static void main(String[] args) {
		Double[] x = MathUtilities.generateSequence(0.0, 10.0, 100);
		Double[] y = MathUtilities.NormalProbabilityDistributionFunction(x, 5.0, 1.0);
		for(int i = 0; i < x.length; i++) {
			System.out.println(x[i] + "," + y[i]);
		}
		
		GeneralXYDataset data = new GeneralXYDataset();
		data.addSeries(x, y, "NormalSeries");
		final ApplicationFrame frame = new ApplicationFrame("Test Normal");
		final JFreeChart chart = ChartFactory.createXYLineChart("Normal", "X", "Y", data); 
		final ChartPanel panel = new ChartPanel(chart);
		panel.setPreferredSize(new Dimension(500,270));
		
		frame.setContentPane(panel);
		frame.pack();
		frame.setVisible(true);
	}
}
