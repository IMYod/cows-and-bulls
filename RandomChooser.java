
public class RandomChooser implements Chooser {

	@Override
	public Series choose() {
		return new Series((int) (Math.random()*Settings.possibleSeries()));
	}
}
