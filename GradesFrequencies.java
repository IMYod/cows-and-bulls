
public class GradesFrequencies {
	private int[][] frequencies;
	
	public GradesFrequencies() {
		frequencies = new int[Settings.lengthOfSeries+1][Settings.lengthOfSeries+1];
	}
	
	public void increase(Grade grade) {
		frequencies[grade.getBlacks()][grade.getWhites()]++;
	}
	
	public int getMax() {
		int maxCount = 0;
		for (int i=0; i<frequencies.length; ++i)
			for (int j=0; j<frequencies[i].length; ++j)
				if (frequencies[i][j] > maxCount)
					maxCount = frequencies[i][j];
		
		return maxCount;
	}
}
