
public class Series {
	private String seriesAsString;
	
/*
 * Index of all possible series 0,1,...,SerialElements-1
 * Create a series according to its serial number.
 */
	public Series(int serialNumber) {
		assert(serialNumber >= 0);
		assert(serialNumber < Settings.possibleSeries());
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<Settings.lengthOfSeries; ++i) {
			sb.append(serialNumber % Settings.possibleElements);
			serialNumber /= Settings.possibleElements;
		}
		seriesAsString = sb.reverse().toString();
	}
	
	public Series(String _seriesAsString) {
		seriesAsString = _seriesAsString;
	}
	
	public String get() {
		return seriesAsString;
	}
	
	@Override
	public String toString() {
		return seriesAsString;
	}
	
	/*
	 * Returns the frequency of each element in the series.
	 * For the series "2023" the method returns:
	 * 1,0,2,1,0,0,...
	 */
	public int[] getFrequencies() {
		int[] frequencies = new int[Settings.possibleElements];
		for (int i=0 ; i<Settings.lengthOfSeries; ++i)
			frequencies[at(i) - '0']++;
		return frequencies;
	}
	
	public char at(int i) {
		return seriesAsString.charAt(i);
	}
	
}
