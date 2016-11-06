package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.data.DomainOrder;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.xy.XYDataset;

import utilities.ArgumentUtilities;

public class GeneralXYDataset implements XYDataset {

	private List<Double[]> xs;
	private List<Double[]> ys;
	private Map<String, Integer> sToI;
	private Map<Integer, String> iToS;

	public GeneralXYDataset() {
		xs = new ArrayList<>();
		ys = new ArrayList<>();
		iToS = new HashMap<>();
		sToI = new HashMap<>();
	}
	
	public void addSeries(Double[] x, Double[] y, String serieskey) {
		ArgumentUtilities.checkNullArgs(new Object[] {x, y});
		ArgumentUtilities.checkEmptyArray(x);
		ArgumentUtilities.checkEmptyArray(y);
		iToS.put(xs.size(), serieskey);
		sToI.put(serieskey, xs.size());
		xs.add(x);
		ys.add(y);
		
	}
	
	@Override
	public int getSeriesCount() {
		return xs.size();
	}

	@Override
	public String getSeriesKey(int index) {
		return iToS.get(index);
	}
	
	@Override
	public int indexOf(Comparable seriesKey) {
		if(!(seriesKey instanceof String)) {
			return -1;
		}
		return sToI.get(seriesKey);
	}

	@Override
	public Number getX(int series, int item) {
		return xs.get(series)[item];
	}

	@Override
	public double getXValue(int series, int item) {
		return xs.get(series)[item];
	}

	@Override
	public Number getY(int series, int item) {
		return ys.get(series)[item];
	}

	@Override
	public double getYValue(int series, int item) {
		return ys.get(series)[item];
	}
	
	@Override
	public DomainOrder getDomainOrder() {
		return DomainOrder.NONE;
	}

	@Override
	public int getItemCount(int series) {
		return ys.get(series).length;
	}
	
	@Override
	public void addChangeListener(DatasetChangeListener listener) {
	}

	@Override
	public void removeChangeListener(DatasetChangeListener listener) {
	}

	@Override
	public DatasetGroup getGroup() {
		return null;
	}

	@Override
	public void setGroup(DatasetGroup group) {
	}
}
