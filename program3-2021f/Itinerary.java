// Bongki Moon (bkmoon@snu.ac.kr)
import java.util.LinkedList;

public class Itinerary
{
	boolean isticket;
	LinkedList<Flight> flightorder;
  // constructor
  public Itinerary(LinkedList<Flight> f) {
	  this.flightorder = f;
  }

  public boolean isFound() {
	  return this.isticket;
  }

  public void print() {
	  if(this.isticket) {
		  for(Flight i : this.flightorder) {
			  System.out.print("[" + i.src + "->" + i.dest + ":" + i.stime + "->" + i.dtime + "]");
		  }
		  System.out.println("");
	  } else {
		  System.out.println("No Flight Schedule Found.");
	  }
	  
  }

}
