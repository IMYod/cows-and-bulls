import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;

public class SmartGuesser extends RandomGuesser {

	//List of the grades for the guesses of this object
	ArrayList<Grade> grades;

	//Decision tree, adaptively updated.
	//Determines the optimal guess according to the series list
	JSONHandler json;
	
	public SmartGuesser() {
		grades = new ArrayList<>();
		try {
			json = new JSONHandler();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public Series guess() {
		Series desicionByTree = null;
		try {
			desicionByTree = json.read(grades);
		} catch (IOException | ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//A recommendation was received from JSON
		if (desicionByTree != null)
			lastGuess = desicionByTree;
	
		//
		else {
			//Brute force searching for optimal guessing.
			//Looking for the guess that minimized the size of elements set, even in the worst case
			int minMaxCount = Integer.MAX_VALUE;
			for (int i=0; i<Settings.possibleSeries(); ++i) {
				GradesFrequencies counter = new GradesFrequencies();
				for (int j=0; j<Settings.possibleSeries(); ++j) {
					if (elements[j]) {
						counter.increase(new Grade(new Series(j), new Series(i)));
					}
				}
				if (counter.getMax() < minMaxCount) {
					minMaxCount = counter.getMax();
					lastGuess = new Series(i);
				}
				else if (counter.getMax() == minMaxCount && elements[i]) {
					lastGuess = new Series(i);
				}
			}
			
			//update json with the optimal guess
			try {
				int lastIndex = grades.size()-1;
				json.push(grades.subList(0, lastIndex), grades.get(lastIndex), lastGuess);
			} catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(lastGuess);
		return lastGuess;
	}
	
	@Override
	public void learn(Grade grade) {
		super.learn(grade);
		grades.add(grade);
	}
	
}
