// Main for the Polynomial ADT

import java.io.*;

public class MainPoly {
  public static void main(String args[]) throws IOException
  {
    if (args.length != 1) {
	System.err.println("Usage: java MainPoly input-file");
	System.exit(0);
    }
    TextInputStream ifs = new TextInputStream(args[0]);

    Polynomial poly1 = new Polynomial();
    Polynomial poly2 = new Polynomial();
    char op='\0';

    Polynomial p=poly1;
    while(ifs.ready()) {
        String token = ifs.readWord();
	char c = token.charAt(token.length()-1);
	//if (token.charAt(0) < '0' || '9' < token.charAt(0)) {
	if (c < '0' || '9' < c) {
		op = token.charAt(0);
		p = poly2;
		token = ifs.readWord();
	}
	int coef = Integer.parseInt(token);
	int exp = ifs.readInt();
	Polynomial temp = new Polynomial(coef,exp);
	p.add(temp);
    }

    //poly1.println();
    //poly2.println();

    System.out.print('(');
    poly1.print();

    switch(op) {
    case '+': poly1.add(poly2); break;
    case '-': poly1.sub(poly2); break;
    default:
    }

    System.out.print(") "+op+" (");
    poly2.print();
    System.out.print(") = (");
    poly1.print();
    System.out.println(')');
  }
}

