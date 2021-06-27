
public class Main {

	public static void main(String[] args) {
//		Chooser c = new ManualChooser();
		Chooser c = new RandomChooser();

//		Guesser g = new SmartGuesser();
//		Guesser g = new RandomGuesser();
		Guesser g = new ManualGuesser();
		
		Game game = new Game(c, g);
		game.openingMessage();		
		game.play();
	}

}
