import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.*;


class Solution2 {
	static int N, E;
	static final int maxe = 40000;
	static boolean[] S;
	static int[] D;
	static int Answer;
	static int[] first = new int[maxe];
	static int[] second = new int[maxe];
	static int[] weight = new int[maxe]; 
	public static ArrayList<ArrayList<Integer>> L1, L2;
	static int[] idx, idx1; // idx[0] : what is V in heap array[0], idx1[0] : where is V:0 in heap array
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("input2.txt"));
		StringTokenizer stk;
		PrintWriter pw = new PrintWriter("output2.txt");
		for (int test_case = 1; test_case <= 10; test_case++) {
			stk = new StringTokenizer(br.readLine());
			N = Integer.parseInt(stk.nextToken()); 
			E = Integer.parseInt(stk.nextToken());
			stk = new StringTokenizer(br.readLine());
			for (int i = 0; i < E; i++) {
				first[i] = Integer.parseInt(stk.nextToken());
				second[i] = Integer.parseInt(stk.nextToken());
				weight[i] = Integer.parseInt(stk.nextToken());
			}
			L1  = new ArrayList<ArrayList<Integer>>(); // fringe
			L2  = new ArrayList<ArrayList<Integer>>(); // weight
			int[] A = new int[N];
			idx = new int[N];
			idx1 = new int[N];
			D = new int[N];
			S = new boolean[N];
			int size = N;
			Answer = 0;
			for(int i=0; i<N; i++){
				idx[i] = i;
				idx1[i] = i;
				A[i] = -99999999;
				D[i] = -99999999;
				L1.add(new ArrayList<Integer>());
				L2.add(new ArrayList<Integer>());
			}
			for (int i = 0; i < E; i++) {
				L1.get(first[i]-1).add(second[i]-1);
				L1.get(second[i]-1).add(first[i] -1);
				L2.get(first[i]-1).add(weight[i]);
				L2.get(second[i]-1).add(weight[i]);
			}
			D[0] = 0;
			A[0] = 0;
			for(int i=0; i<N; i++){ 
				int maxidx = idx[0];
				S[maxidx] = true;
				Answer += A[0];
				idx[0] = idx[size-1];
				A[0] = A[size-1];
				idx1[idx[0]] = 0;
				siftdown(A, --size, 0);
				for(int j=0; j<L1.get(maxidx).size(); j+=1){
					int iii = L1.get(maxidx).get(j);
					int jjj = L2.get(maxidx).get(j);
					if(!S[iii] && (jjj > D[iii])){
						D[iii] = jjj;
						A[idx1[iii]] = D[iii];
						heapify(A, size, idx1[iii]);
					}
				}
			}
			pw.println("#" + test_case + " " + Answer);
			
			pw.flush();
		}

		br.close();
		pw.close();
	}
	
	public static void heapify(int[] A, int n, int k){ // modify to max-heap from heap[k]
		int newidx = (k-1)/2;
		if(k == 0)
			return;
		if(A[newidx] < A[k]){
			int save = A[newidx];
			A[newidx] = A[k];
			A[k] = save;
			save = idx[newidx];
			idx[newidx] = idx[k];
			idx[k] = save;
			idx1[idx[newidx]] = newidx;
			idx1[idx[k]] = k;
			heapify(A, n, newidx);
		}
		else return;
	}
	
	public static void siftdown(int[] A, int n, int k){ // need this function after delete max and heapifying
		int left = 2*k+1;
		int right = 2*k+2;
		int bigger;
		if(right <= n){
			if(A[left] > A[right]) 
				bigger = left;
			else bigger = right;
		}
		else if(left <= n) 
			bigger = left;
		else return;
		if(A[bigger] > A[k]){
			int save = A[bigger];
			A[bigger] = A[k];
			A[k] = save;
			save = idx[bigger];
			idx[bigger] = idx[k];
			idx[k] = save;
			idx1[idx[bigger]] = bigger;
			idx1[idx[k]] = k;
		}
		siftdown(A, n, bigger);
	}
}