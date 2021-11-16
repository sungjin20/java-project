// Bongki Moon (bkmoon@snu.ac.kr)
import java.util.LinkedList;
import java.util.Arrays;

public class Planner {
	int roadsize; // max edge number
	String[][] road; // save edge info
	LinkedList<Flight>[] flightlist;
	String[] port;
	String[] CT;
	int portsize;
	int[][][] asciiport;
	int[][] asciiroad;

  // constructor
  public Planner(LinkedList<Airport> portList, LinkedList<Flight> fltList) {
	  this.roadsize = portList.size()*(portList.size()-1);
	  this.road = new String[this.roadsize][2];
	  int now = 0;
	  int now2 = 0;
	  this.flightlist = new LinkedList[this.roadsize];
	  for(int i=0; i<this.roadsize; i++) {
		  this.flightlist[i] = new LinkedList<Flight>();
	  }
	  this.portsize = portList.size();
	  this.port = new String[this.portsize];
	  this.CT = new String[this.portsize];
	  this.asciiport = new int[26][26][26];
	  for(int i=0; i<26; i++) {
		  for(int j=0; j<26; j++) {
			  for(int k=0; k<26; k++) {
				  this.asciiport[i][j][k] = -1;
			  }
		  }
	  }
	  for(Airport i : portList) {
		  this.port[now2] = i.port;
		  this.CT[now2] = i.connectTime;
		  int ascii1 = (int)i.port.charAt(0)-65;
		  int ascii2 = (int)i.port.charAt(1)-65;
		  int ascii3 = (int)i.port.charAt(2)-65;
		  this.asciiport[ascii1][ascii2][ascii3] = now2;
		  now2 += 1;
	  }
	  this.asciiroad = new int[this.portsize][this.portsize];
	  for(int i=0; i<this.portsize; i++) {
		  for(int j=0; j<this.portsize; j++) {
			  this.asciiroad[i][j] = -1;
		  }
	  }
	  int asc1 = (int)fltList.get(0).src.charAt(0)-65;
	  int asc2 = (int)fltList.get(0).src.charAt(1)-65;
	  int asc3 = (int)fltList.get(0).src.charAt(2)-65;
	  int asc11 = (int)fltList.get(0).dest.charAt(0)-65;
	  int asc22 = (int)fltList.get(0).dest.charAt(1)-65;
	  int asc33 = (int)fltList.get(0).dest.charAt(2)-65;
	  this.asciiroad[this.asciiport[asc1][asc2][asc3]][this.asciiport[asc11][asc22][asc33]] = 0;
	  for(Flight i : fltList) {
		  asc1 = (int)i.src.charAt(0)-65;
		  asc2 = (int)i.src.charAt(1)-65;
		  asc3 = (int)i.src.charAt(2)-65;
		  asc11 = (int)i.dest.charAt(0)-65;
		  asc22 = (int)i.dest.charAt(1)-65;
		  asc33 = (int)i.dest.charAt(2)-65;
		  if(this.asciiroad[this.asciiport[asc1][asc2][asc3]][this.asciiport[asc11][asc22][asc33]]==now) {
			  this.flightlist[now].add(new Flight(i.src, i.dest, i.stime, i.dtime));
		  } else {
			  now += 1;
			  this.asciiroad[this.asciiport[asc1][asc2][asc3]][this.asciiport[asc11][asc22][asc33]] = now;
			  this.flightlist[now].add(new Flight(i.src, i.dest, i.stime, i.dtime));
		  }
	  }
  }

  public Itinerary Schedule(String start, String end, String departure) {
	  LinkedList<Flight> forder = new LinkedList<Flight>();
	  int[] A = new int[this.portsize];
	  int[] idx = new int[this.portsize];
	  int[] idx1 = new int[this.portsize];
	  int[] D = new int[this.portsize];
	  int[] F = new int[this.portsize];
	  int[] prev = new int[this.portsize];
	  boolean[] S = new boolean[this.portsize];
	  int size = this.portsize;
	  for(int i=0; i<this.portsize; i++) {
		  A[i] = 9999999;
		  D[i] = 9999999;
		  prev[i] = -1;
		  idx[i] = i;
		  idx1[i] = i;
	  }
	  int asc1 = start.charAt(0)-65;
	  int asc2 = start.charAt(1)-65;
	  int asc3 = start.charAt(2)-65;
	  int u = this.asciiport[asc1][asc2][asc3];
	  asc1 = end.charAt(0)-65;
	  asc2 = end.charAt(1)-65;
	  asc3 = end.charAt(2)-65;
	  int v = this.asciiport[asc1][asc2][asc3];
	  if((u==-1) || (v==-1)) {
		  Itinerary result2 = new Itinerary(forder);
		  result2.isticket = false;
		  return result2;
	  }
	  A[u] = 0;
	  D[u] = 0;
	  int safe = A[0];
	  A[0] = A[u];
	  A[u] = safe;
	  safe = idx[0];
	  idx[0] = idx[u];
	  idx[u] = safe;
	  idx1[idx[0]] = 0;
	  idx1[idx[u]] = u;
	  for(int i=0; i<this.portsize; i++) { // algorithm start
		  int minidx = idx[0];
		  S[minidx] = true;
		  if(minidx == v) break;
		  idx[0] = idx[size-1];
		  A[0] = A[size-1];
		  idx1[idx[0]] = 0;
		  pdown(A, 0, --size, idx, idx1);
		  for(int j=0; j<this.portsize; j++) {
			  if(this.asciiroad[minidx][j] != -1) {
				  int timeweight = D[minidx];
				  int findidx = 0;
				  if(u==minidx) {
					  int newtime = 99999999;
					  int dephour = Integer.parseInt(departure)/100;
					  int depmin = Integer.parseInt(departure)%100;
					  int dept = dephour*60+depmin;
					  int traveltime = 0;
					  int waittime = 0;
					  int iii = -1;
					  for(Flight l : this.flightlist[this.asciiroad[minidx][j]]) {
						  iii += 1;
						  int dthour = Integer.parseInt(l.dtime)/100;
						  int dtmin = Integer.parseInt(l.dtime)%100;
						  int sthour = Integer.parseInt(l.stime)/100;
						  int stmin = Integer.parseInt(l.stime)%100;
						  int dt = dthour*60+dtmin;
						  int st = sthour*60+stmin;
						  if(dt >= st) {
							  traveltime = dt-st;
						  } else {
							  traveltime = 1440-st+dt;
						  }
						  if(st >= dept) {
							  waittime = st-dept;
						  } else {
							  waittime = 1440-dept+st;
						  }
						  if(newtime > (traveltime+waittime)) {
							  newtime = traveltime+waittime;
							  findidx = iii;
						  }
						  }
					  timeweight += newtime;
				  } else {
					  int newtime = 99999999;
					  int ct = Integer.parseInt(this.CT[minidx]);
					  int cthour = ct/100;
					  int ctmin = ct%100;
					  ct = cthour*60+ctmin;
					  int dephour = Integer.parseInt(departure)/100;
					  int depmin = Integer.parseInt(departure)%100;
					  int dept = dephour*60+depmin;
					  int nowtime = (dephour*60+depmin+timeweight+ct)%1440;
					  int traveltime = 0;
					  int waittime = 0;
					  int iii = -1;
					  for(Flight l : this.flightlist[this.asciiroad[minidx][j]]) {
						  iii += 1;
						  int dthour = Integer.parseInt(l.dtime)/100;
						  int dtmin = Integer.parseInt(l.dtime)%100;
						  int sthour = Integer.parseInt(l.stime)/100;
						  int stmin = Integer.parseInt(l.stime)%100;
						  int dt = dthour*60+dtmin;
						  int st = sthour*60+stmin;
						  if(dt >= st) {
							  traveltime = dt-st;
						  } else {
							  traveltime = 1440-st+dt;
						  }
						  if(st >= nowtime) {
							  waittime = st-nowtime;
						  } else {
							  waittime = 1440-nowtime+st;
						  }
						  if(newtime > (traveltime+waittime+ct)) {
							  newtime = traveltime+waittime+ct;
							  findidx = iii;
						  }
					  }
					  timeweight += newtime;
				  }
				  if(!S[j] && timeweight < D[j]) {
					  D[j] = timeweight;
					  A[idx1[j]] = D[j];
					  prev[j] = minidx;
					  F[j] = findidx;
					  pup(A, idx1[j], size, idx, idx1);
				  }
			  }
		  }
	  }
	  int inc = v;
	  int dephour2 = Integer.parseInt(departure)/100;
	  int depmin2 = Integer.parseInt(departure)%100;
	  int dept2 = dephour2*60+depmin2;
	  while(prev[inc]!=-1) {
		  int nowtime2 = (dept2+D[inc])%1440;
		  int nowhour2 = nowtime2/60;
		  int nowmin2 = nowtime2%60;
		  forder.addFirst(this.flightlist[this.asciiroad[prev[inc]][inc]].get(F[inc]));
		  inc = prev[inc];
	  }
	  Itinerary result = new Itinerary(forder);
	  if(forder.size() == 0) {
		  result.isticket = false;
	  } else {
		  result.isticket = true;
	  }
	  return result;
  }
  
  public void pdown(int[] A, int k, int n, int[] idx, int[] idx1){
		int left = 2*k+1;
		int right = 2*k+2;
		int smaller;
		if(right <= n){
			if(A[left] < A[right]) 
				smaller = left;
			else smaller = right;
		}
		else if(left <= n) 
			smaller = left;
		else return;
		if(A[smaller] < A[k]){
			int save = A[smaller];
			A[smaller] = A[k];
			A[k] = save;
			save = idx[smaller];
			idx[smaller] = idx[k];
			idx[k] = save;
			idx1[idx[smaller]] = smaller;
			idx1[idx[k]] = k;
		}
		pdown(A, smaller, n, idx, idx1);
	}
  
  public void pup(int[] A, int k, int n, int[] idx, int[] idx1) {
	  int newidx = (k-1)/2;
	  if(k==0) return;
	  if(A[newidx] > A[k]) {
		  int save = A[newidx];
		  A[newidx] = A[k];
		  A[k] = save;
		  save = idx[newidx];
		  idx[newidx] = idx[k];
		  idx[k] = save;
		  idx1[idx[newidx]] = newidx;
		  idx1[idx[k]] = k;
		  pup(A, newidx, n, idx, idx1);
	  }
  }

}

