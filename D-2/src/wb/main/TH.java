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
	static Button button, bt_Start, bt_Stop;
	
	static boolean clicked = true;
	
	static Thread applicationThread;

	static Runnable print;
	
	 public static void main(String[] args) {

		final Display display = new Display();
		Shell shell = new Shell(display);
		
//		final Runnable print = new Run_Draw();
		
//		final Runnable print = new Runnable() {
//			public void run() {
//				
//				System.out.println("Print from thread: \t" + Thread.currentThread().getName());
//				
//				TH.bt_Start.setText("clicked!");
////				TH.button.setText("clicked!");
//				
//				System.out.println("clicked => " + clicked);
//				
//			}
//			
//		};
		
//		applicationThread = new Th_Draw(display, print);
//			applicationThread = new Thread(new Th_Draw(print), "applicationThread") {
//		applicationThread = new Thread("applicationThread") {
////			final Thread applicationThread = new Thread("applicationThread") {
//			public void run() {
//				
//				System.out.println("Hello from thread: \t" + Thread.currentThread().getName());
//				display.syncExec(print);
//				System.out.println("Bye from thread: \t" + Thread.currentThread().getName());
//				
//			}
//		};
		
		shell.setText("syncExec Example");
		shell.setSize(459, 419);
		
		bt_Start = new Button(shell, SWT.NONE);
		bt_Start.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				print = new Run_Draw();
				
				applicationThread = new Th_Draw(display, print);
				
				applicationThread.start();
				
			}
		});
		bt_Start.setBounds(122, 58, 149, 70);
		bt_Start.setText("Start");
		
		bt_Stop = new Button(shell, SWT.NONE);
		bt_Stop.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				try {
					
					TH.applicationThread.join();
					
					System.out.println("Thread => joined");
					
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				TH.bt_Start.setText("Start");
				
				print = null;
				
				TH.applicationThread = null;
				
				System.out.println("Thread => stopped");
				
			}
		});
		bt_Stop.setBounds(131, 186, 140, 70);
		bt_Stop.setText("Stop");

		////////////////////////////////

		// widgets

		////////////////////////////////
		
		shell.open();	
		
		
		while(! shell.isDisposed()) {
			if(! display.readAndDispatch()) {// If no more entries in event queue
			display.sleep();
			}
		}
		
		display.dispose();
			
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
