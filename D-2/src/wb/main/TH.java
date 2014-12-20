package wb.main;

//import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.PaintEvent;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Group;
//import org.eclipse.wb.swt.SWTResourceManager;






import wb.utils.CONS;
import wb.utils.Methods;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.wb.swt.SWTResourceManager;

public class TH {

	////////////////////////////////

	// fields

	////////////////////////////////
	static Button button;
	
	static boolean clicked = true;
	
	static Thread applicationThread;
	
	 public static void main(String[] args) {

		final Display display = new Display();
		Shell shell = new Shell(display);
		
		final Runnable print = new Runnable() {
			public void run() {
				
				System.out.println("Print from thread: \t" + Thread.currentThread().getName());
				
				TH.button.setText("clicked!");
				
				System.out.println("clicked => " + clicked);
				
			}
			
		};
		
		applicationThread = new Thread("applicationThread") {
//			final Thread applicationThread = new Thread("applicationThread") {
			public void run() {
				
				System.out.println("Hello from thread: \t" + Thread.currentThread().getName());
				display.syncExec(print);
				System.out.println("Bye from thread: \t" + Thread.currentThread().getName());
				
			}
		};
		
		shell.setText("syncExec Example");
		shell.setSize(404, 346);

		////////////////////////////////

		// widgets

		////////////////////////////////
		_Setup_Widgets(shell);
		
//		Button button = new Button(shell, SWT.CENTER);
//		TH.button = new Button(shell, SWT.CENTER);
//		TH.button.setText("Click to start");
//		TH.button.setBounds(shell.getClientArea());
//		TH.button.addSelectionListener(new SelectionListener() {
//			
//			public void widgetDefaultSelected(SelectionEvent e) {
//				
//			}
//			
//			public void widgetSelected(SelectionEvent e) {
//				
//				if (clicked) {
//					
//					clicked = false;
//					
//					applicationThread.start();
//					
//				}
//				
//			}
//		});
		
		shell.open();	
		
		
		while(! shell.isDisposed()) {
			if(! display.readAndDispatch()) {// If no more entries in event queue
			display.sleep();
			}
		}
		
		display.dispose();
			
			}


	private static void 
	_Setup_Widgets(Shell shell) {
		// TODO Auto-generated method stub

		////////////////////////////////

		// button: start

		////////////////////////////////
//		Thread th = applicationThread;
		
		TH.button = new Button(shell, SWT.CENTER);
		TH.button.setText("Click to start");
		TH.button.setBounds(shell.getClientArea());
		TH.button.addSelectionListener(new SelectionListener() {
			
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
			
			public void widgetSelected(SelectionEvent e) {
				
				if (clicked) {
					
					clicked = false;
					
					applicationThread.start();
					
				}
				
			}
		});
		
		////////////////////////////////
		
		// button: start
		
		////////////////////////////////
//		Thread th = applicationThread;
		
		TH.button = new Button(shell, SWT.CENTER);
		
		TH.button.setText("Stop thread");
		
		TH.button.setBounds(shell.getClientArea());
		
		TH.button.addSelectionListener(new SelectionListener() {
			
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
			
			public void widgetSelected(SelectionEvent e) {
				
				if (!clicked) {
					
					System.out.println("Stopping the thread...");
					
//					clicked = true;
//					
//					applicationThread.start();
					
				}
				
			}
		});
		
	}	
	 
}

//class XThread extends Thread{
//
//	private Rect rect;
//
//	XThread(){
//		
//	}
//	
//	XThread(String threadName){
//		super(threadName);					   // Initialize thread.
//		System.out.println(this);
//		start();
//	}
//	
//	public XThread(Rect rect) {
//		// TODO Auto-generated constructor stub
//		
//		this.rect = rect;
//		
//	}
//
//	public void run(){
//		//Display info about this particular thread
////		System.out.println(Thread.currentThread().getName());
//		
//		rect._move_Rect_B__Case_9();
//		
//	}
//}
