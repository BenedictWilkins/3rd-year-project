package test;

import graph.DataFrame;
import graph.DataReader;
import graph.SeriesPlot;


public class TestDataReader {

	private static String gmacornu = "C:/Users/Ben/Documents/RWorkspace/3rd year project data analysis/gmAcorn-u.txt";
	
	public static void main(String[] args) {
		DataReader dr = new DataReader();
		DataFrame frame = dr.readFile(gmacornu, " ", true);
		Double[] data = frame.getColumn(1);
		SeriesPlot plot = new SeriesPlot(data,"");
		plot.pack();
		plot.setVisible(true);
	}
}
