import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

/*
   1. Compile the file with the following command. The class file named Solution3 would be created when you compile the source code.
       javac Solution3.java -encoding UTF8

   2. Run the compiled program with the following command. Output file(output.txt) should be created after the program is finished
       java Solution3

   - The encoding of the source code should be UTF8
   - You can use the 'time' command to measure your algorithm.
       time java Solution3
   - You can also halt the program with the 'timeout' command.
       timeout 0.5 java Solution3
       timeout 1 java Solution3
 */

class Solution3 {
	static final int max_n = 1000000;

	static int n;
	static int[][] A = new int[3][max_n];
	static int Answer;

	public static void main(String[] args) throws Exception {
		/*
		   Read the input from input.txt
		   Write your answer to output.txt
		 */
		BufferedReader br = new BufferedReader(new FileReader("input.txt"));
		StringTokenizer stk;
		PrintWriter pw = new PrintWriter("output.txt");

		for (int test_case = 1; test_case <= 10; test_case++) {
			
			stk = new StringTokenizer(br.readLine());
			n = Integer.parseInt(stk.nextToken());
			for (int i = 0; i < 3; i++) {
				stk = new StringTokenizer(br.readLine());
				for (int j = 0; j < n; j++) {
					A[i][j] = Integer.parseInt(stk.nextToken());
				}
			}
			
			int[][] w = new int[6][n];
			for(int i=0; i<n; i++) {
				w[0][i] = A[0][i] - A[1][i];
				w[1][i] = A[0][i] - A[2][i];
				w[2][i] = A[1][i] - A[0][i];
				w[3][i] = A[2][i] - A[0][i];
				w[4][i] = A[1][i] - A[2][i];
				w[5][i] = A[2][i] - A[1][i];
			}
			
			int[][] c = new int[n][6];
			c[0][0] = w[0][0];
			c[0][1] = w[1][0];
			c[0][2] = w[2][0];
			c[0][3] = w[3][0];
			c[0][4] = w[4][0];
			c[0][5] = w[5][0];
			for(int j=1; j<n; j++) {
				for(int k=0; k<6; k++) {
					int dae1 = 0;
					int dae2 = 0;
					switch(k) {
					case 0: dae1 = 3;
							dae2 = 4;
							break;
					case 1: dae1 = 2;
							dae2 = 5;
							break;
					case 2: dae1 = 1;
							dae2 = 5;
							break;
					case 3: dae1 = 0;
							dae2 = 4;
							break;
					case 4: dae1 = 0;
							dae2 = 3;
							break;
					case 5: dae1 = 1;
							dae2 = 2;
							break;
					}
					int max = 0;
					if(c[j-1][dae1] < c[j-1][dae2]) {
						max = c[j-1][dae2];
					} else {
						max = c[j-1][dae1];
					}
					c[j][k] = max + w[k][j];
				}
			}
			Answer = -999999;
			for(int i=0; i<6; i++) {
				if(c[n-1][i] > Answer) {
					Answer = c[n-1][i];
				}
			}


			// Print the answer to output.txt
			pw.println("#" + test_case + " " + Answer);
			// To ensure that your answer is printed safely, please flush the string buffer while running the program
			pw.flush();
		}

		br.close();
		pw.close();
	}
}

