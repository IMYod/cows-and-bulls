public class Settings {
	
	public static final int lengthOfSeries = 4;
	public static final int possibleElements = 10;	

	//returns the number of different possible series  
	public static int possibleSeries() {
		return (int)Math.pow(possibleElements, lengthOfSeries);
	}
	
	//generates the string 0123...
	public static String generateFirstGuessString() {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<lengthOfSeries; ++i)
			sb.append(i % possibleElements);
		return sb.toString();
	}
}