package wb.utils;

public class DD {

	private int area_Total;
	
	private int numOf_Residues;
	
	private int area_Residues;

	// status
	private int statuc_B;
	private int statuc_C;
	
	// Rect: Z
	private Rect Z;
	
	private Rect A;
	private Rect B;
	private Rect C;
	
	public int getArea_Total() {
		return area_Total;
	}
	public int getNumOf_Residues() {
		return numOf_Residues;
	}
	public int getArea_Residues() {
		return area_Residues;
	}
	public int getStatuc_B() {
		return statuc_B;
	}
	public int getStatuc_C() {
		return statuc_C;
	}
	public Rect getZ() {
		return Z;
	}
	public Rect getA() {
		return A;
	}
	public Rect getB() {
		return B;
	}
	public Rect getC() {
		return C;
	}
	public void setArea_Total(int area_Total) {
		this.area_Total = area_Total;
	}
	public void setNumOf_Residues(int numOf_Residues) {
		this.numOf_Residues = numOf_Residues;
	}
	public void setArea_Residues(int area_Residues) {
		this.area_Residues = area_Residues;
	}
	public void setStatus_B(int statuc_B) {
		this.statuc_B = statuc_B;
	}
	public void setStatus_C(int statuc_C) {
		this.statuc_C = statuc_C;
	}
	public void setZ(Rect z) {
		Z = z;
	}
	public void setA(Rect a) {
		A = a;
	}
	public void setB(Rect b) {
		B = b;
	}
	public void setC(Rect c) {
		C = c;
	}
	
}//public class DD
