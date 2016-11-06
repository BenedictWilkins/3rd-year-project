package graph;
import java.util.ArrayList;
import java.util.List;

public class DataFrame {
	private static final String[] ERRMESSAGES = { "Cannot add row with incorrect number of columns" };
	private static final int PRINTMAXCHARS = 8;
	private String printformat;
	
	private List<String[]> data;
	private Class<?>[] coltypes;
	private String[] headers;
	

	public DataFrame(String[] headers, Class<?>[] coltypes) {
		data = new ArrayList<String[]>();
		this.coltypes = coltypes;
		this.headers = headers;
		printformat = createPrintFormat();
	}
	
	//TODO tdd
	private String createPrintFormat() {
		StringBuilder b = new StringBuilder();
		for(int i = 0; i < getNumColumns(); i++) {
			b.append("%"+PRINTMAXCHARS+"s");
		}
		return b.toString();
	}

	//TODO tdd
	public void addRow(String[] row) throws IllegalArgumentException {
		checkRowSize(row);
		for(int i = 0; i < row.length; i++) {
			
		}
		//check for types 
		data.add(row);
	}

	//TODO tdd
	public int getNumRows() {
		return data.size();
	}

	//TODO tdd
	public int getNumColumns() {
		return headers.length;
	}
	
	//TODO tdd
	public void printData(int limit) {
		limit = (limit > getNumRows()) ? getNumRows() : limit;
		System.out.println(rowToString(headers));
		for(int i = 0; i < limit; i++) {
			System.out.println(rowToString(data.get(i)));
		}
	}
	
	public Double[] getColumn(int index) {
		//do a type check on the column
		Double[] d = new Double[getNumRows()];
		for(int i = 0; i < d.length; i++) {
			d[i] = Double.valueOf(data.get(i)[index]);
		}
		return d;
	}
	
	private String rowToString(String[] row) {
		StringBuilder b = new StringBuilder();
		for(String s : row) {
			b.append(s);
			b.append(" ");
		}
		return b.toString();
	}
	
	//TODO tdd
	private void checkRowSize(String[] row) throws IllegalArgumentException {
		if(row.length != getNumColumns()) {
			throw new IllegalArgumentException(ERRMESSAGES[0]);
		}
	}
}
