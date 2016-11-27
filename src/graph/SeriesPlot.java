package graph;
import java.awt.Dimension;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class SeriesPlot extends ApplicationFrame {

	private static final long serialVersionUID = 1L;

	public SeriesPlot(Double[] data, String title) {
		super(title);
		final XYDataset dataset = createSeries(data);
		final JFreeChart chart = ChartFactory.createXYLineChart("Test", "Time",
				"Value", dataset);
		final ChartPanel panel = new ChartPanel(chart);
		panel.setPreferredSize(new Dimension(560,270));
		setContentPane(panel);
		this.setVisible(true);
		this.pack();
	}

	public XYDataset createSeries(Double[] data) {
		final XYSeries series = new XYSeries("Series");
		for (int i = 0; i < data.length; i++) {
			series.add(i, data[i]);
		}
		return new XYSeriesCollection(series);
	}
}
