package graph;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import utilities.ArgumentUtilities;

public class DataReader {

	private BufferedReader reader;

	public DataReader() {

	}

	public DataFrame readFile(String filepath, String sep, boolean header) throws IllegalArgumentException {
		ArgumentUtilities.checkNullArgs(new Object[] {(Object)filepath, (Object)sep});
		DataFrame frame = null;
		try {
			reader = new BufferedReader(new FileReader(filepath));
			String line;
			if(header) {
				frame = new DataFrame(reader.readLine().split(sep), null);
			} else {
				String[] firstLine = reader.readLine().split(sep);
				frame = new DataFrame(constructVoidHeaders(firstLine.length), null);
			}
			while ((line = reader.readLine()) != null) {
				frame.addRow(parseLine(line, sep));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return frame;
	}
	
	private String[] constructVoidHeaders(int numberOfHeaders) {
		String[] headers = new String[numberOfHeaders];
		for(int i = 0; i < numberOfHeaders; i++) {
			headers[i] = "V"+i;
		}
		return headers;
	}
	
	private String[] parseLine(String line, String sep) {
		return line.split(sep);
		//parse the value 
	}
	
	//type determination will be done here
	private void parseValue(String value) {
		
	}
	
	
	
	public class TimeStruct {
		private final String DEFAULTSEPERATOR = ":";
		private final String VALIDSEPERATORS = ":;./| ";
		private String seperator = DEFAULTSEPERATOR;
		
		private String[] t = new String[3];
		
		public TimeStruct(String time) {
			t = time.split(seperator);
		}
		
		public TimeStruct(String time, String seperator) {
			this.seperator = seperator;
		}
		
		
		public String getTime() {
			return t[0] + seperator + t[1] + seperator + t[2];
		}
		
		public String getHours() {
			return t[0];
		}
		
		public String getMinuites() {
			return t[1];
		}
		
		public String getSeconds() {
			return t[2];
		}
	}
}
