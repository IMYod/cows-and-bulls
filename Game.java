
public class Game {
	Chooser chooser;
	Guesser guesser;
	
	static final int maxRound = 30;
	
	Game(Chooser _chooser, Guesser _guesser) {
		chooser = _chooser;
		guesser = _guesser;
	}

	public void openingMessage() {
		System.out.print("Choose a " + Settings.lengthOfSeries + " digits number, from the digits:\t");
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<Settings.possibleElements; ++i) {
			sb.append(i);
			sb.append(",");
		}
		System.out.println(sb);
	}
	
	//returns the number of rounds until true guessing
	public void play() {
		Series goal = chooser.choose();
		
		int round = 1;
		while (round <= maxRound) {
			Series guess = guesser.guess();
			Grade grade = new Grade(goal, guess);
			System.out.println(grade);
			if (grade.isPerfect()) {
				winGame(round);
				return;
			}
			else {
				guesser.learn(grade);
				++round;
			}
		}
		loseGame();
	}
	
	private void winGame(int round) {
		System.out.println("Congratulations!");
		System.out.println("--- " + round + " rounds ---");
	}
	
	private void loseGame() {
		System.out.println("Game over!");
	}
}
