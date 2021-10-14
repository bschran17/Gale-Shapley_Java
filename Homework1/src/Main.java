import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;

class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = readN(br);
		int[][] proposerPref = readProposerPref(n, br);
		int[][] respondentPref = readRespondentPref(n, br);
		LinkedList<Integer> freeProposers = createFreeProposers(n);
		int[] next = createNext(n);
		Integer[] current = createCurrent(n);
		int[][] ranking = createRanking(n, respondentPref);
		int[] arr = runGaleShapleya1(n, proposerPref, respondentPref, freeProposers, next, current, ranking);
		int a1 = arr[0];
		int p0FirstRMatch = arr[1];
		freeProposers = createFreeProposers(n);
		next = createNext(n);
		current = createCurrent(n);
		int a2 = runGaleShapleya2(p0FirstRMatch, a1, n, proposerPref, respondentPref, freeProposers, next, current, ranking);
		System.out.println(a1);
		System.out.println(a2);
    }

	static int readN(BufferedReader br) throws Exception {
		return Integer.parseInt(br.readLine());
	}
	
	static int[][] readProposerPref(int n, BufferedReader br) throws Exception{
		int[][] proposerPref = new int[n][n];
		for (int i = 0; i < n; i++) {
			String nl = br.readLine();
			String[] str_arr = nl.split(" ");
			for (int j = 0; j < n; j++) {
				proposerPref[i][j] = Integer.parseInt(str_arr[j]);
			}
		}
		return proposerPref;
	}
	
	static int[][] readRespondentPref(int n, BufferedReader br) throws Exception{
		int[][] respondentPref = new int[n][n];
		for (int i = 0; i < n; i++) {
			String nl = br.readLine();
			String[] str_arr = nl.split(" ");
			for (int j = 0; j < n; j++) {
				respondentPref[i][j] = Integer.parseInt(str_arr[j]);
			}
		}
		return respondentPref;
	}			
	
	static LinkedList<Integer> createFreeProposers(int n) {
		LinkedList<Integer> ll = new LinkedList<Integer>();
		for (int i = 0; i < n; i++) {
			ll.add(i);
		}
		return ll;
	}
	
	static int[] createNext(int n) {
		int array[] = new int[n];
		Arrays.fill(array, 0);
		return array;
	}
	
	static Integer[] createCurrent(int n) {
		Integer array[] = new Integer[n];
		for (int i = 0; i < n; i++) {
			array[i] = null;
		}
		return array;
	}
	
	static int[][] createRanking(int n, int[][] respondentPref) throws Exception {
		int[][] ranking = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				Integer m = respondentPref[i][j];
				ranking[i][m] = j;
			}
		}
		return ranking;
	}
	
	static int[] runGaleShapleya1(int n, int[][] proposerPref, int[][] respondentPref, LinkedList<Integer> freeProposers, int[] next, Integer[] current, int[][] ranking) {
		Integer p0FirstR = proposerPref[0][next[0]];
		while (freeProposers.size() > 0) {
			Integer p = freeProposers.pop();
			Integer r = proposerPref[p][next[p]];
			next[p]++;
			Integer curr_p = current[r];
			if (curr_p == null) {
				current[r] = p;
			}
			else {
				if (ranking[r][p] < ranking[r][curr_p]) {
					current[r] = p;
					freeProposers.addFirst(Integer.valueOf(curr_p));
				}
				else {
					freeProposers.addFirst(Integer.valueOf(p));
				}
			}
		}
		int[] arr = new int[] {current[0], current[p0FirstR]};
		return arr;
	}
	
	static int runGaleShapleya2(int p0FirstRMatch, int a1, int n, int[][] proposerPref, int[][] respondentPref, LinkedList<Integer> freeProposers, int[] next, Integer[] current, int[][] ranking) {
		Integer p0FirstR = proposerPref[0][next[0]];
		next[0]++;
		while (freeProposers.size() > 0 && !(next[freeProposers.peek()] == n)) {
			Integer p = freeProposers.pop();
			Integer r = proposerPref[p][next[p]];
			next[p]++;
			Integer curr_p = current[r];
			if (curr_p == null) {
				current[r] = p;
			}
			else {
				if (ranking[r][p] < ranking[r][curr_p]) {
					current[r] = p;
					freeProposers.addFirst(Integer.valueOf(curr_p));
				}
				else {
					freeProposers.addFirst(Integer.valueOf(p));
				}
			}
		}
		if (current[p0FirstR] == null) {
			return 1;
		} else if (ranking[p0FirstR][current[p0FirstR]] <= ranking[p0FirstR][p0FirstRMatch]) {
			return 3;
		} else {
			return 2;
		}
	}
}	
	
