public class Hawk extends Problem {

	private int[] x = new int[nVars];
	private int[] p = new int[nVars];
	private final int LB = 0;
	private final int UB = 1;
	private double rabbitJump = 0;
	private double sigma = 0;
	private double deltaXt = 0;
	private double Y, levyFlight, Z = 0;

	public Hawk() {
		for (int j = 0; j < nVars; j++) {
			x[j] = StdRandom.uniform(2);
			p[j] = StdRandom.uniform(2);
		}
	}

	public int[] getPositionVector() {
		return x;
	}

	public int[] getPBest() {
		return this.p;
	}

	protected void exploration(Hawk g, Randoms randomValues, double[] averageHawksPosition) {
		for (int j = 0; j < nVars; j++) {
			if(randomValues.getQ() >= 0.5) {
				x[j]  =  toBinary(x[j] - StdRandom.uniform() * Math.abs(x[j] - (2 * StdRandom.uniform() * g.x[j])));
			} else {
				x[j] = toBinary(x[j] - averageHawksPosition[j] - randomValues.getR3() * (LB + randomValues.getR4() * (UB - LB)));
			}
		}
	}

	protected void softBeseige(Hawk g, double escapeEnergy) {
		for (int j = 0; j < nVars; j++) {
			deltaXt = g.p[j] - x[j];
			rabbitJump = (2*(1 - StdRandom.uniform()));
			x[j] = toBinary((deltaXt) - escapeEnergy * Math.abs(( rabbitJump * g.p[j]) - x[j]));
		}	
	}
	
	protected void hardBeseige(Hawk g, double escapeEnergy) {
		for (int j = 0; j < nVars; j++) {
			deltaXt = g.p[j] - x[j];
			x[j] = toBinary(g.p[j] - escapeEnergy * Math.abs(deltaXt));
		}
	}

	protected void softBeseigeProgresive(Hawk g, double escapeEnergy, double beta, double S, double u, double v) {
		double gamma1 = gammaFunction(1 + beta);
		double gamma2 = gammaFunction((1 + beta)/2);
		sigma = Math.pow((gamma1 * Math.sin((Math.PI*beta)/2))/( gamma2 * beta * (2*(beta-1)/2)), (1/beta));
		levyFlight = 0.01 * (u*sigma/Math.pow(Math.abs(v), (1/beta)));
		for (int j = 0; j < nVars; j++) {
			rabbitJump = (2*(1 - StdRandom.uniform()));
			Y = g.p[j] - escapeEnergy * Math.abs((rabbitJump * g.p[j]) - x[j]);
			Z = Y + S * levyFlight;
			if (toBinary(Y) < toBinary(computeFitness(x))) {
				x[j] = toBinary(Y);
			} 
			if (toBinary(Z) < toBinary(computeFitness(x))) {
				x[j] = toBinary(Z);
			}
		}			
	}

	protected void hardBeseigeProgresive(Hawk g, double escapeEnergy, double beta, double[] averageHawksPosition, double S, double u, double v) {
		double gamma1 = gammaFunction(1 + beta);
		double gamma2 = gammaFunction((1 + beta)/2);
		sigma = Math.pow((gamma1 * Math.sin((Math.PI*beta)/2))/( gamma2 * beta * (2*(beta-1)/2)), (1/beta));
		levyFlight = 0.01 * (u*sigma/Math.pow(Math.abs(v), (1/beta)));
		for (int j = 0; j < nVars; j++) {
			rabbitJump = (2*(1 - StdRandom.uniform()));
			Y = g.p[j] - escapeEnergy * Math.abs((rabbitJump * g.p[j]) - averageHawksPosition[j]);
			Z = Y + S * levyFlight;
			if (toBinary(Y) < toBinary(computeFitness(x))) {
				x[j] = toBinary(Y);
			} 
			if (toBinary(Z) < toBinary(computeFitness(x))) {
				x[j] = toBinary(Z);
			}
		}
					
	}

	protected boolean isFeasible() {
		return checkConstraint(x);
	}

	protected boolean isBetterThan(Hawk g) {
		return computeFitness(x) < computeFitness(g.x);
	}

	private int toBinary(double x) {
		return StdRandom.uniform() <= (1 / (1 + Math.pow(Math.E, -x))) ? 1 : 0;
	}

    private float diff() {
		return computeFitness(x) - optimum();
	}

	private float rpd() {
		return diff() / optimum() * 100f;
	}

	private String showSolution() {
		return java.util.Arrays.toString(x);
	}

	protected void copy(Object object) {
		if (object instanceof Hawk) {
			System.arraycopy(((Hawk) object).x, 0, this.x, 0, nVars);
			System.arraycopy(((Hawk) object).p, 0, this.p, 0, nVars);
		}
	}
	
	@Override
	public String toString() {
		return String.format("optimal value: %d, fitness: %d, diff: %.1f, rpd: %.2f%%, p: %s", optimum(),
				computeFitness(x), diff(), rpd(), showSolution());
	}

    public boolean isBetterThanPBest() {
        return computeFitness() < computeFitnessPBest();
    }
	
	protected int computeFitnessPBest() {
		return computeFitness(p);
	}

	protected int computeFitness() {
		return computeFitness(x);
	}

    protected void updatePBest() {
		System.arraycopy(x, 0, p, 0, x.length);
	}

	static double logGamma(double x) {
		double tmp = (x - 0.5) * Math.log(x + 4.5) - (x + 4.5);
		double ser = 1.0 + 76.18009173    / (x + 0)   - 86.50532033    / (x + 1)
						 + 24.01409822    / (x + 2)   -  1.231739516   / (x + 3)
						 +  0.00120858003 / (x + 4)   -  0.00000536382 / (x + 5);
		return tmp + Math.log(ser * Math.sqrt(2 * Math.PI));
	}

	static double gammaFunction(double x) { return Math.exp(logGamma(x)); }
}
