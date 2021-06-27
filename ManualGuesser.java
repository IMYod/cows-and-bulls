
public class ManualGuesser extends ManualHandler implements Guesser {

	@Override
	public Series guess() {
		String series = readSeries();
		return new Series(series);
	}

	@Override
	public void learn(Grade grade) {
		return;
	}

}
