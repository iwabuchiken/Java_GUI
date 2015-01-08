package wb.utils;

import org.eclipse.swt.graphics.Color;

import wb.utils.CONS.Admin.NodeNames;
import wb.utils.CONS.Admin.Orien;

public class Rect {

	private int x_Cur, y_Cur;
	
	private int w, h;
	
	private int w_Orig, h_Orig;
	
	private String rect_Name;
	
	Color color;
	
	Rect attachedTo;

	NodeNames attachedAt;

	Orien orien;
	
	public Rect(String rect_Name) {
		
		this.rect_Name = rect_Name;
		
	}

	public Rect(Rect rect) {
		
		this.x_Cur = rect.x_Cur;
		
		this.y_Cur = rect.y_Cur;
		
		this.w = rect.w;
		
		this.h = rect.h;
		
		this.w_Orig = rect.w_Orig;
		
		this.h_Orig = rect.h_Orig;
		
		this.rect_Name = rect.rect_Name;
		
		this.color = rect.color;
		
		this.attachedTo = rect.attachedTo;

		this.attachedAt = rect.attachedAt;

		this.orien = rect.orien;
		
	}
	
	public Orien getOrien() {
		return orien;
	}

	public void setOrien(Orien orien) {
		this.orien = orien;
	}

	public NodeNames getAttachedAt() {
		return attachedAt;
	}

	public void setAttachedAt(NodeNames attachedAt) {
		this.attachedAt = attachedAt;
	}

	public String getRect_Name() {
		return rect_Name;
	}

	public void setRect_Name(String rect_Name) {
		this.rect_Name = rect_Name;
	}

	public int getW_Orig() {
		return w_Orig;
	}

	public int getH_Orig() {
		return h_Orig;
	}

	public void setW_Orig(int w_Orig) {
		this.w_Orig = w_Orig;
	}

	public void setH_Orig(int h_Orig) {
		this.h_Orig = h_Orig;
	}

	public int getX_Cur() {
		return x_Cur;
	}

	public int getY_Cur() {
		return y_Cur;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

	public Color getColor() {
		return color;
	}

	public Rect getAttachedTo() {
		return attachedTo;
	}

	public void setX_Cur(int x_Cur) {
		this.x_Cur = x_Cur;
	}

	public void setY_Cur(int y_Cur) {
		this.y_Cur = y_Cur;
	}

	public void setW(int w) {
		this.w = w;
	}

	public void setH(int h) {
		this.h = h;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setAttachedTo(Rect attachedTo) {
		this.attachedTo = attachedTo;
	}
	
	
	
}
