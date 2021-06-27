import java.util.Scanner;

public class ManualHandler {
	
	private Scanner scanner = new Scanner(System.in);
	
	//read series 
	public String readSeries() {
		String input = scanner.nextLine();
		while (!isSeriesCorrect(input)) {
			System.out.println("This input is invalid! Try again:");
			input = scanner.nextLine();
		}
		return input;
	}
	
	public boolean isSeriesCorrect(String str) {
		if (str.length() != Settings.lengthOfSeries)
			return false;
		for (int i=0; i<Settings.lengthOfSeries; ++i) {
			if (str.charAt(i) < '0' || str.charAt(i) >= '0' + Settings.possibleElements)
				return false;
		}
		return true;
	}
}
