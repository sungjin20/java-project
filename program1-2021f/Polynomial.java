// Skeleton of the Polynomial ADT
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Polynomial {
	ArrayList<String> a = new ArrayList<String>();
	ArrayList<String> b = new ArrayList<String>();

  // Create an empty polynomial
  public Polynomial() {
	  this.a = a;
	  this.b = b;
	  
  }

  // Create a single-term polynomial
  public Polynomial(int coef, int exp) {
	  this.a = a;
	  this.b = b;
	  if(coef!=0) {
		  this.a.add(0, String.valueOf(coef));
		  this.b.add(0, String.valueOf(exp));
	  }
	  
  }

  // Add opnd to 'this' polynomial; 'this' is returned
  public Polynomial add(Polynomial opnd) {
	  int input_term_count = opnd.b.size();
	  int this_term_count = this.b.size();
	  for(int i=0; i<input_term_count; i++) {
		  
		  String input_exp = opnd.b.get(i);
		  String input_coef = opnd.a.get(i);
		  if(this_term_count == 0) {
			  this.a.add(input_coef);
			  this.b.add(input_exp);
		  }else {
			  if(this.b.contains(input_exp)) {
				  int idx = this.b.indexOf(input_exp);
				  String r_coef = this.a.get(idx);
				  this.a.remove(idx);
				  String real_coef = String.valueOf(Integer.valueOf(input_coef)+Integer.valueOf(r_coef));
				  if(Integer.valueOf(real_coef) == 0) {
					  this.b.remove(idx);
				  }else {
					  this.a.add(idx, real_coef);
				  }
			  }else {
				  this.a.add(input_coef);
				  this.b.add(input_exp);
				  }
			  
		  }
	  }
	  return this;
  }

  // Subtract opnd from 'this' polynomial; 'this' is returned
  public Polynomial sub(Polynomial opnd) {
	  int input_term_count = opnd.b.size();
	  int this_term_count = this.b.size();
	  for(int i=0; i<input_term_count; i++) {
		  String input_exp = opnd.b.get(i);
		  String input_coef = opnd.a.get(i);
		  if(this_term_count == 0) {
			  this.a.add(String.valueOf(Integer.valueOf(input_coef)*(-1)));
			  this.b.add(input_exp);
		  }else {
			  if(this.b.contains(input_exp)) {
				  int idx = this.b.indexOf(input_exp);
				  String r_coef = this.a.get(idx);
				  this.a.remove(idx);
				  String real_coef = String.valueOf(Integer.valueOf(r_coef)-Integer.valueOf(input_coef));
				  if(Integer.valueOf(real_coef) == 0) {
					  this.b.remove(idx);
				  }else {
					  this.a.add(idx, real_coef);
				  }
			  }else {
				  this.a.add(String.valueOf(Integer.valueOf(input_coef)*(-1)));
				  this.b.add(input_exp);
				  }
			  
		  }
	  }
	  return this;
  }

  // Print the terms of 'this' polynomial in decreasing order of exponents.
  // No pair of terms can share the same exponent in the printout.
  public void print() {
	  int term_count = this.a.size();
	  ArrayList<String> a_ = new ArrayList<String>();
	  ArrayList<String> b_ = new ArrayList<String>();
	  a_.addAll(this.a);
	  b_.addAll(this.b);
	  ArrayList<String> c = new ArrayList<String>();
	  ArrayList<String> d = new ArrayList<String>();
	  String[] array = this.b.toArray(new String[this.b.size()]);
	  int[] array2 = new int[this.b.size()];
	  for (int i = 0; i < array.length; i++) {
          array2[i] = Integer.parseInt(array[i]);
      }
	  ArrayList<Integer> b2 = new ArrayList<Integer>();
	  for(int element : array2) {
		  b2.add(element);
	  }
	  for(int i=0; i<term_count; i++) {
		  int max = Collections.max(b2);
		  int idx = this.b.indexOf(String.valueOf(max));
		  d.add(String.valueOf(max));
		  c.add(this.a.get(idx));
		  this.a.remove(idx);
		  this.b.remove(idx);
		  b2.remove(idx);
	  }
	  this.a.addAll(a_);
	  this.b.addAll(b_);
	  for(int j=0; j<term_count; j++) {
		  System.out.print(c.get(j)+" "+d.get(j)+" ");
	  }
  }
}
