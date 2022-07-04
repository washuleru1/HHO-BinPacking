
public class Main {

	public static void main(String[] args) {
		int qMetrics[], bestItters[] = new int[Constants.EPOCH];
		int nVars, ps, q, suma;
		double MaxFES;
		Swarm swarm = null;
		try {
			suma = 0;
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
				if (MaxFES >= bestItters[i]){
					suma+=1;
				}
				System.out.println("BEST ITTER: "+bestItters[i]);
			}
			System.out.println((suma/(Constants.EPOCH)));
		} catch (Exception e) {
			StdOut.printf("%s\n%s", e.getMessage(), e.getCause());
		}
	}
} 
