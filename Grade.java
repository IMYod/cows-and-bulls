
public class Grade {
	
	/*
	 * Get black point for true element in place.
	 * Get white point for true element not in place.
	 */
	private int blacks, whites;
	
	public int getBlacks() {
		return blacks;
	}

	public int getWhites() {
		return whites;
	}

	public Grade(int _blacks, int _whites) {
		blacks = _blacks;
		whites = _whites;
	}
	
	//The grade is determined by comparison between the guess and the goal
	public Grade(Series goal, Series guessing) {
		//count blacks
		for (int index = 0; index < Settings.lengthOfSeries; ++index) {
			if (goal.at(index) == guessing.at(index))
				++blacks;
		}
		
		//count whites by counting the frequency of each element 
		int[] freqGoal = goal.getFrequencies();
		int[] freqGuess = guessing.getFrequencies();
		
		for (int element = 0; element < Settings.possibleElements; ++element) {
			whites += Math.min(freqGuess[element], freqGoal[element]);
		}
		whites -= blacks;
	}
	
	public boolean isPerfect() {
		return blacks == Settings.lengthOfSeries;
	}
	
	//Can this grade describe the relationship between the following goal and guess?
	public boolean isPossible(Series goal, Series guess) {
		return equals(new Grade(goal, guess));
	}
	
	@Override
	public String toString() {
		return "blacks: " + blacks + "\t whites: " + whites;
	}

	@Override
	public boolean equals(Object o) {
	    if (o == null)
	        return false;
	    if (getClass() != o.getClass())
	        return false;
	    Grade grade = (Grade) o;
	    return (whites == grade.whites && blacks == grade.blacks);
	    
	}
	
	@Override
	public int hashCode() {
		return whites + 10*blacks;
	}
}