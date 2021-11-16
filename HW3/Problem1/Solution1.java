import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;


class Solution1 {
	static final int max_e = 10000;

	static int N, E;
	static int[] start = new int[max_e];
	static int[] arrive = new int[max_e];
	static int[] weight = new int[max_e];
	static int Answer;

	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new FileReader("input1.txt"));
		StringTokenizer stk;
		PrintWriter pw = new PrintWriter("output1.txt");

		for (int test_case = 1; test_case <= 10; test_case++) {
			
			stk = new StringTokenizer(br.readLine());
			N = Integer.parseInt(stk.nextToken()); E = Integer.parseInt(stk.nextToken());
			stk = new StringTokenizer(br.readLine());
			for (int i = 0; i < E; i++) {
				start[i] = Integer.parseInt(stk.nextToken());
				arrive[i] = Integer.parseInt(stk.nextToken());
				weight[i] = Integer.parseInt(stk.nextToken());
			}
			
			int[][][] d = new int[N+1][N+1][N+1];
			for(int i=1; i<N+1; i++) {
				for(int j=1; j<N+1; j++) {
					d[i][j][0] = 999999;
				}
			}
			for(int i=0; i<E; i++) {
				d[start[i]][arrive[i]][0] = weight[i];
			}
			for(int k=1; k<=N; k++) {
				for(int i=1; i<=N; i++) {
					for(int j=1; j<=N; j++) {
						if(d[i][j][k-1] > d[i][k][k-1] + d[k][j][k-1]) {
							d[i][j][k] = d[i][k][k-1] + d[k][j][k-1];
						} else {
							d[i][j][k] = d[i][j][k-1];
						}
					}
				}
			}
			int result = 0;
			for(int i=1; i<=N; i++) {
				for(int j=1; j<=N; j++) {
					if(i!=j) {
						result = (result + d[i][j][N])%100000000;
					}
					
				}
			}
			
			Answer = (result%100000000);


			// Print the answer to output.txt
			pw.println("#" + test_case + " " + Answer);
			// To ensure that your answer is printed safely, please flush the string buffer while running the program
			pw.flush();
		}

		br.close();
		pw.close();
	}
}