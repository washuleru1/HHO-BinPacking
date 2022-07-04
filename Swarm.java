
public class Swarm {
	
	private final int ps = 25;
	private final int T = 1000;
	private double escapeEnergy = 0;
    private Randoms randomValues;
    private double Upper = 1;
    private double Lower = -1;
	private double baseEnergy = 0;
	private double E1, r, S, u, v;
	private static final double beta = 1.5;
	
	private java.util.List<Hawk> swarm = null;
	private Hawk g = null;
	
	public void execute() {
		init();
		evolve();
	}
	
	private void init() {
		swarm = new java.util.ArrayList<Hawk>();
		g = new Hawk();
		Hawk hawk;
		for(int i = 1; i <= ps; i++) {
			do {
				hawk = new Hawk();	
			} while(!hawk.isFeasible());
			swarm.add(hawk);
		}
		g.copy(swarm.get(0));
		for (int i = 1; i < ps; i++) {
			if (swarm.get(i).isBetterThan(g)) {
				g.copy(swarm.get(i));
			}
		}
		log(0);
	}
	
	private void evolve() {
		int t = 1;
		Hawk hawk = new Hawk();
		while(t <= T) {
			//seteo de valores randomicos por iteración
			
			E1 = 2.0*(1 - (t/T));
			//se recorre cada halcon del enjambre
			for (int i = 0; i < ps; i++) {
				//Se setea E0
				baseEnergy = StdRandom.uniform(Lower, Upper);
				//Se le da valor a escapeEnergy
				escapeEnergy = baseEnergy * E1;
				if (Math.abs(escapeEnergy) >= 1) {
					//exploración
					do {
						//Se configuran valores randomicos y se elige un halcon aleatorio para la exploración
						randomValues = new Randoms();
						hawk.copy(swarm.get(StdRandom.uniform(ps)));
						hawk.exploration(g, randomValues, averageHawksPosition());
					} while (!hawk.isFeasible());
					swarm.get(i).copy(hawk);
				} else {
					do{
						//seteo de r randomico y halcon
						r = StdRandom.uniform();
						hawk.copy(swarm.get(i));
						//soft beseige
						if (r >= 0.5 && Math.abs(escapeEnergy) >= 0.5) {
							//System.out.println("SOFT BESEIGE");
							hawk.softBeseige(g, escapeEnergy);
						}
						//hard beseige
						if (r >= 0.5 && Math.abs(escapeEnergy) < 0.5) {
							//System.out.println("HARD BESEIGE");
							hawk.hardBeseige(g, escapeEnergy);
						}
						//soft beseige with progresive rapid dives
						if (r < 0.5 && Math.abs(escapeEnergy) >= 0.5) {
							S = StdRandom.uniform(2);
							u = StdRandom.uniform();
							v = StdRandom.uniform();
							//System.out.println("SOFT PROGRESIVE");
							hawk.softBeseigeProgresive(g, escapeEnergy, beta, S, u, v);
						}
						//hard beseige with progresive rapid dives
						if (r < 0.5 && Math.abs(escapeEnergy) < 0.5) {
							S = StdRandom.uniform(2);
							u = StdRandom.uniform();
							v = StdRandom.uniform();
							//System.out.println("HARD PROGRESIVE");
							hawk.hardBeseigeProgresive(g, escapeEnergy, beta, averageHawksPosition(), S, u, v);
						}
					} while(!hawk.isFeasible());
					swarm.get(i).copy(hawk);
				}
				if (hawk.isBetterThanPBest()) {
					hawk.updatePBest();
				}
				if (hawk.isBetterThan(g)) {
					g.copy(hawk);
				}
			}
			log(t);
			t++;
		}
	}
	
	private void log(int t) {
		StdOut.printf("t=%d,\t%s\n", t, g);
	}
	
	private double[] averageHawksPosition() {
		int nVars = swarm.get(0).nVars;
		double sum = 0;
		double[] data = new double[nVars];
		for (int i = 0; i < nVars; i++) {
			for (int j = 0; j < ps; j++) {
				sum+=swarm.get(j).getPositionVector()[i];
			}
			data[i] = sum/ps;
			sum=0;
		}
		return data;
	}

}
