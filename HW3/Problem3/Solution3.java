import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.*;

class Solution3 {
	static final int max_e = 100000;
	static int N, E;
	static int[] start = new int[max_e];
	static int[] arrive = new int[max_e];
	static int[] weight = new int[max_e];
	static int Answer;

	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new FileReader("input3.txt"));
		StringTokenizer stk;
		PrintWriter pw = new PrintWriter("output3.txt");

		for (int test_case = 1; test_case <= 10; test_case++) {
			
			stk = new StringTokenizer(br.readLine());
			N = Integer.parseInt(stk.nextToken()); E = Integer.parseInt(stk.nextToken());
			stk = new StringTokenizer(br.readLine());
			for (int i = 0; i < E; i++) {
				start[i] = Integer.parseInt(stk.nextToken());
				arrive[i] = Integer.parseInt(stk.nextToken());
				weight[i] = Integer.parseInt(stk.nextToken());
			}
			// version1 start
			double first1 = System.currentTimeMillis();
			int[] d1 = new int[N+1];
			for(int i=1; i<N+1; i++) {
				d1[i] = 99999999;
			}
			d1[1] = 0;
			for(int i=1; i<N; i++) { 
				for(int j=0; j<E; j++) {
					if(d1[start[j]]==99999999) continue;
					if(d1[start[j]]+weight[j]<d1[arrive[j]]) {
						d1[arrive[j]] = d1[start[j]]+weight[j];
					}
				}
			}
			double second1 = System.currentTimeMillis();
			
			// version2 start
			double first2 = System.currentTimeMillis();
			LinkedList<Integer>[] L = new LinkedList[N+1];
			LinkedList<Integer>[] Lw = new LinkedList[N+1];
			for(int i=1; i<N+1; i++) {
				L[i] = new LinkedList();
				Lw[i] = new LinkedList();
			}
			for(int i=0; i<E; i++) {
				L[start[i]].add(arrive[i]);
				Lw[start[i]].add(weight[i]);
			}
			int[] d2 = new int[N+1];
			for(int i=1; i<N+1; i++) {
				d2[i] = 99999999;
			}
			d2[1] = 0;
			boolean ischange = true;
			Iterator<Integer> iter3 = L[1].iterator();
			Iterator<Integer> iter4 = Lw[1].iterator();
			while(iter3.hasNext()) {
				d2[iter3.next()] = iter4.next();
			}
			LinkedList<Integer> changed = new LinkedList<Integer>();
			LinkedList<Integer> changed2 = new LinkedList<Integer>();
			changed2 = L[1];
			for(int i=0; i<N/2; i++) {
				if(ischange) {
					ischange = false;
					for(Integer j : changed2) {
						Iterator<Integer> iter1 = L[j].iterator();
						Iterator<Integer> iter2 = Lw[j].iterator();
						while(iter1.hasNext()) {
							int next1 = iter1.next();
							int next2 = iter2.next();
							if(d2[j]+next2<d2[next1]) {
								d2[next1] = d2[j]+next2;
								ischange = true;
								changed.add(next1);
							}
						}
					}
					changed2.clear();
				} else break;
				
				if(ischange) {
					ischange = false;
					for(Integer j : changed) {
						Iterator<Integer> iter1 = L[j].iterator();
						Iterator<Integer> iter2 = Lw[j].iterator();
						while(iter1.hasNext()) {
							int next1 = iter1.next();
							int next2 = iter2.next();
							if(d2[j]+next2<d2[next1]) {
								d2[next1] = d2[j]+next2;
								ischange = true;
								changed2.add(next1);
							}
						}
					}
					changed.clear();
				} else break;
			}
			double second2 = System.currentTimeMillis();

			// Print the answer to output.txt
			pw.println("#" + test_case);
			for(int i=1; i<N; i++) {
				pw.print(d1[i] + " ");
			}
			pw.println(d1[N]);
			pw.println(second1-first1);
			for(int i=1; i<N; i++) {
				pw.print(d2[i] + " ");
			}
			pw.println(d2[N]);
			pw.println(second2-first2);
			// To ensure that your answer is printed safely, please flush the string buffer while running the program
			pw.flush();
		}

		br.close();
		pw.close();
	}
}