package wb.main;

import org.eclipse.swt.widgets.Display;

public class Th_Draw extends Thread {

	
	
	private Runnable print;
	private Display display;

	public Th_Draw(Display display, Runnable print) {
		// TODO Auto-generated constructor stub
		
		this.print = print;
		this.display = display;
		
	}

	public void run() {
		
		System.out.println("Hello from thread: \t" + Thread.currentThread().getName());
		display.syncExec(print);
		System.out.println("Bye from thread: \t" + Thread.currentThread().getName());
		
	}

}
