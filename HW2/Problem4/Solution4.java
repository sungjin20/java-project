import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

/*
   1. Compile the file with the following command. The class file named Solution4 would be created when you compile the source code.
       javac Solution4.java -encoding UTF8

   2. Run the compiled program with the following command. Output file(output.txt) should be created after the program is finished
       java Solution4

   - The encoding of the source code should be UTF8
   - You can use the 'time' command to measure your algorithm.
       time java Solution4
   - You can also halt the program with the 'timeout' command.
       timeout 0.5 java Solution4
       timeout 1 java Solution4
 */

class Solution4 {
	static final int max_n = 1000;

	static int n, H;
	static int[] h = new int[max_n], d = new int[max_n-1];
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
			/*
			   n is the number of blocks, and H is the max tower height
			   read each height of the block to h[0], h[1], ... , h[n-1]
			   read the heights of the holes to d[0], d[1], ... , d[n-2]
			 */
			stk = new StringTokenizer(br.readLine());
			n = Integer.parseInt(stk.nextToken()); H = Integer.parseInt(stk.nextToken());
			stk = new StringTokenizer(br.readLine());
			for (int i = 0; i < n; i++) {
				h[i] = Integer.parseInt(stk.nextToken());
			}
			stk = new StringTokenizer(br.readLine());
			for (int i = 0; i < n-1; i++) {
				d[i] = Integer.parseInt(stk.nextToken());
			}
			
			int[][] z = new int[n+1][H+1];
			int[] count = new int[H+1];
			z[1][h[0]] += 1;
			z[2][h[1]] += 1;
			int sum = h[0]+h[1]-d[0];
			if(sum <= H) {
				z[2][sum] += 1;
			}
			for(int i=3; i<n+1; i++) {
				for(int j=1; j<H+1; j++) {
					count[j] = (count[j]+z[i-2][j])%1000000;
				}
				z[i][h[i-1]] += 1;
				for(int j=1; j<H+1; j++) {
					if(z[i-1][j]>0) {
						sum = j+h[i-1]-d[i-2];
						if(sum <= H) {
							z[i][sum] = (z[i][sum]+z[i-1][j])%1000000;
						}
					}
				}
				for(int j=1; j<H+1; j++) {
					if(count[j]>0) {
						sum = j+h[i-1];
						if(sum <= H) {
							z[i][sum] = (z[i][sum]+count[j])%1000000;
						}
					}
				}
			}
			int sumall = 0;
			for(int i=1; i<n+1; i++) {
				for(int j=1; j<H+1; j++) {
					sumall = (sumall + z[i][j])%1000000;
				}
			}
			
			
			Answer = (sumall%1000000);


			// Print the answer to output.txt
			pw.println("#" + test_case + " " + Answer);
			// To ensure that your answer is printed safely, please flush the string buffer while running the program
			pw.flush();
		}

		br.close();
		pw.close();
	}
}

