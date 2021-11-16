import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

/*
   1. Compile the file with the following command. The class file named Solution1 would be created when you compile the source code.
       javac Solution1.java -encoding UTF8

   2. Run the compiled program with the following command. Output file(output.txt) should be created after the program is finished
       java Solution1

   - The encoding of the source code should be UTF8
   - You can use the 'time' command to measure your algorithm.
       time java Solution1
   - You can also halt the program with the 'timeout' command.
       timeout 0.5 java Solution1
       timeout 1 java Solution1
 */

class Solution1 {

	/*
	   Since the results cannot be covered with the boundary of int type, use long type for the variable of the result.
	   Overflowed cases will be counted in zero points.
	   We assume that Answer[0] is the number of "a", Answer[1] is the number of "b", Answer[2] is the number of "c".
	*/
	static int n;                           // string length
	static String s;                        // string sequence
	static long[] Answer = new long[3];
	
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

			long [][][] count = new long[n][n][3];
			for(int j=0; j<n; j++) {
				if(s.charAt(j)=='a') {
					count[j][j][0]+=1;
				} else if(s.charAt(j)=='b') {
					count[j][j][1]+=1;
				} else {
					count[j][j][2]+=1;
				}
			}
			for(int i=1; i<n; i++) {
				for(int j=0; j<n-i; j++) {
					for(int k=0; k<i; k++) {
						count[j][j+i][0] = count[j][j+i][0] + count[j][j+k][0]*count[j+k+1][j+i][2] + count[j][j+k][1]*count[j+k+1][j+i][2] + count[j][j+k][2]*count[j+k+1][j+i][0];
						count[j][j+i][1] = count[j][j+i][1] + count[j][j+k][0]*count[j+k+1][j+i][0] + count[j][j+k][0]*count[j+k+1][j+i][1] + count[j][j+k][1]*count[j+k+1][j+i][1];
						count[j][j+i][2] = count[j][j+i][2] + count[j][j+k][1]*count[j+k+1][j+i][0] + count[j][j+k][2]*count[j+k+1][j+i][1] + count[j][j+k][2]*count[j+k+1][j+i][2];
						
					}
				}
			}
			Answer[0] = count[0][n-1][0];
			Answer[1] = count[0][n-1][1];
			Answer[2] = count[0][n-1][2];

			// Print the answer to output.txt
			pw.println("#" + test_case + " " + Answer[0] + " " + Answer[1] + " " + Answer[2]);
			// To ensure that your answer is printed safely, please flush the string buffer while running the program
			pw.flush();
		}

		br.close();
		pw.close();
	}
}

