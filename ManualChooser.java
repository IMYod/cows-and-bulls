
public class ManualChooser extends ManualHandler implements Chooser {

	@Override
	public Series choose() {
		String series = readSeries();
		return new Series(series);
	}
}
