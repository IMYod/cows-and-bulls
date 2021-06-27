
public class RandomGuesser implements Guesser {

	//elements[i]=false if learned in one of the queries that a series in index i is not possible 
	boolean[] elements;
	Series lastGuess;
	
	public RandomGuesser() {
		//Initialize elements <- true
		elements = new boolean[Settings.possibleSeries()];
		for (int i=0; i<elements.length; ++i)
			elements[i] = true;
	}
	
	@Override
	public Series guess() {
		//First guessing
		if (lastGuess == null)
			lastGuess = new Series(Settings.generateFirstGuessString());
		else {
			//Choose a true element randomly
			int randomStart = (int) (Math.random()*Settings.possibleSeries());
			for (int i=randomStart; i!=randomStart-1; ++i) {
				i = i % Settings.possibleSeries();
				if (elements[i]) {
					lastGuess = new Series(i);
					break;
				}
			}
		}
		System.out.println(lastGuess);
		return lastGuess;
	}

	//Update element
	@Override
	public void learn(Grade grade) {
		for (int i=0; i<Settings.possibleSeries(); ++i) {
			if (elements[i]) {
				if (!grade.isPossible(new Series(i), lastGuess))
					elements[i] = false;
			}
		}
	}
	
	//Counts "true" elements
	protected int getNumberOfPossibleElements() {
		int counter=0;
		for (int i=0; i<Settings.possibleSeries(); ++i)
			if (elements[i])
				counter++;
		return counter;
	}
}