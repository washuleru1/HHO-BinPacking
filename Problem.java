public class Problem {

	private final int[] weight = { 73, 39, 49, 79, 54, 57, 98, 69, 67, 49, 38, 34, 96, 27, 92, 82, 69, 45, 
		69, 20, 75, 97, 51, 70, 29, 91, 98, 77, 48, 45, 43, 61, 36, 82, 89, 94, 26, 35, 58, 58, 57, 46, 44, 
		91, 49, 52, 65, 42, 33, 60, 37, 57, 91, 52, 95, 84, 72, 75, 89, 81, 67, 74, 87, 60, 32, 76, 85, 59, 
		62, 39, 64, 52, 88, 45, 29, 88, 85, 54, 40, 57, 91, 55, 60, 37, 86, 21, 21, 43, 77, 75, 92, 33, 59, 
		74, 40, 36, 62, 21, 56, 38, 22, 45, 94, 68, 83, 86, 75, 21, 40, 44, 74, 52, 61, 95, 20, 79, 76, 32, 
		21, 91, 83, 39, 31, 81, 41, 90, 74, 100, 38, 33, 74, 40, 80, 39, 22, 46, 58, 65, 67, 37, 82, 64, 26, 
		80, 74, 20, 62, 82, 40, 28, 72, 45, 62, 72, 89, 31, 92, 63, 89, 33, 25, 54, 66, 100, 20, 90, 87, 48, 
		28, 46, 76, 50, 66, 30, 26, 23, 40, 70, 57, 92, 52, 54, 27, 58, 66, 65, 93, 83, 37, 62, 94, 29, 66, 
		98, 20, 66, 42, 52, 90, 22, 30, 34, 65, 81, 90, 44, 88, 51, 97, 79, 58, 46, 65, 40, 68, 64, 34, 59, 
		99, 82, 86, 88, 52, 76, 76, 50, 51, 92, 59, 22, 60, 69, 45, 66, 50, 62, 59, 90, 54, 55, 92, 23, 97, 
		73, 39, 88, 34, 92, 74, 90 };
	private final int capacity = 150;
	protected final int nVars = Constants.N_VARS;

	protected int optimum() {
		return 101;
	}

	protected boolean checkConstraint(int[] x) {
		int chosenBin = computeFitness(x);
		if (chosenBin == 0)
			return false;

		int[] remaining = new int[chosenBin];
		for (int i = 0; i < chosenBin; i++)
			remaining[i] = capacity;
		
		if (remaining[0] < weight[0])
			return false;

		remaining[0] -= weight[0];
		for (int j = 1, i = 0; j < nVars; j++) {
			if (remaining[i] - weight[j] >= 0) {
				remaining[i] -= weight[j];
				i = 0;
			} else {
				i++;
				if (i == chosenBin)
					return false;
				j--;
			}
		}
		return true;
	}

	protected int computeFitness(int[] bin) {
		return java.util.Arrays.stream(bin).sum();
	}
}