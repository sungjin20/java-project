import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

/*
   1. Compile the file with the following command. The class file named Solution2 would be created when you compile the source code.
       javac Solution2.java -encoding UTF8

   2. Run the compiled program with the following command. Output file(output.txt) should be created after the program is finished
       java Solution2

   - The encoding of the source code should be UTF8
   - You can use the 'time' command to measure your algorithm.
       time java Solution2
   - You can also halt the program with the 'timeout' command.
       timeout 0.5 java Solution2
       timeout 1 java Solution2
 */

class Solution2 {
	static final int max_n = 100000;

	static int n;
	static String s;
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
			s = br.readLine();
			
			int [][] count = new int[n][n];
			int [] num = new int[n];
			for(int i=0; i<n; i++) {
				num[i] = s.charAt(i);
			}
			for(int i=0; i<n; i++) {
				count[i][i] = 1;
			}
			for(int i=0; i<n-1; i++) {
				if(num[i] == num[i+1]) {
					count[i][i+1] = 2;
				}
			}
			for(int i=2; i<n; i++) {
				for(int j=0; j<n-i; j++) {
					if(num[j]==num[j+i]) {
						count[j][j+i] = count[j+1][j+i-1]+2;
					} else {
						if(count[j+1][j+i]>count[j][j+i-1]) {
							count[j][j+i] = count[j+1][j+i];
						} else {
							count[j][j+i] = count[j][j+i-1];
						}
					}
				}
			}

			Answer = count[0][n-1];


			// Print the answer to output.txt
			pw.println("#" + test_case + " " + Answer);
			// To ensure that your answer is printed safely, please flush the string buffer while running the program
			pw.flush();
		}

		br.close();
		pw.close();
	}
}

