
public class Main {

	public static void main(String[] args) {
		int qMetrics[], bestItters[] = new int[Constants.EPOCH];
		int nVars, ps, q;
		double MaxFES;
		Swarm swarm = null;
		try {
			nVars = Constants.N_VARS;
			ps = Constants.POPULATION_SIZE;
			MaxFES = (10000 * nVars)/(nVars*ps);
			for(int r = 1; r <= Constants.EPOCH; r++) {
				StdRandom.newSeed();
				swarm = new Swarm();
				swarm.execute();
				bestItters[r-1] = swarm.getBestItter();
			}
			for (int i = 0; i < bestItters.length; i++) {
				System.out.println("BEST ITTER: "+bestItters[i]);
			}
		} catch (Exception e) {
			StdOut.printf("%s\n%s", e.getMessage(), e.getCause());
		}
	}
} 
