
public class Main {

	public static void main(String[] args) {
		int bestItters[] = new int[Constants.EPOCH];
		double[] qMetrics = new double[Constants.EPOCH];
		int nVars, ps, suma, suma2;
		double MaxFES, q;
		Swarm swarm = null;
		try {
			suma = 0;
			suma2 = 0;
			nVars = Constants.N_VARS;
			ps = Constants.POPULATION_SIZE;
			MaxFES = (10000 * nVars)/(nVars*ps);
			for(int r = 1; r <= Constants.EPOCH; r++) {
				StdRandom.newSeed();
				swarm = new Swarm();
				swarm.execute();
				bestItters[r-1] = swarm.getBestItter();
				if (MaxFES >= bestItters[r-1]) {
					suma+=1;
				}
				System.out.println("BEST EPOCH FITNESS IN SWARM: "+ swarm.getSwarmPBestFitness());
				q = ((Constants.OPTIMAL_VALUE * 1.05) - swarm.getSwarmPBestFitness())/((Constants.OPTIMAL_VALUE * 1.05) - Constants.OPTIMAL_VALUE);
				qMetrics[r-1] = (Math.pow(2,Math.pow(q,Math.pow(10,3))) -1);
				System.out.println("qMetrics: "+qMetrics[r-1]);
				
			}
			for (int i = 0; i < bestItters.length; i++) {
				System.out.println("BEST ITTER: "+bestItters[i]);
			}
			System.out.println((suma/(Constants.EPOCH)));
		} catch (Exception e) {
			StdOut.printf("%s\n%s", e.getMessage(), e.getCause());
		}
	}
} 
