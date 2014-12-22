package wb.main;

//import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.PaintEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintListener;
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
import wb.utils.CONS.Admin.NodeNames;
import wb.utils.CONS.Admin.Orien;
import wb.utils.Methods;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.wb.swt.SWTResourceManager;

public class Rect_D6 {

	protected Shell shell;
	
	//abc
	
	Display display;

	JFrame frame;
	
	Thread applicationThread;
	Runnable print;
	
	//REF http://stackoverflow.com/questions/10961714/how-to-properly-stop-the-thread-in-java answered Jun 9 '12 at 14:21
	volatile boolean running = true;
//	boolean running = true;
	
	Label lbl_AreaData;

	Dimension dim;

	Button bt_Execute, bt_Quit, bt_Clear, bt_Back, bt_Forward;

	static Label lbl_Status, lbl_In;
	
	//test
	
	Canvas cv_1;

	// colors
	Color red, blue, blue_light, burlywood2, green;
	
	int count = 0;

	boolean f_Executing = false;
	private Group gr_navigate, gr_ops;
	private Button btnOptions;
	private FormData fd_gr_navigate;
	private FormData fd_gr_ops;
//	private Group group_2;
	
	////////////////////////////////

	// methods

	////////////////////////////////
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void 
	main(String[] args) {
		
		try {
			Rect_D6 window = new Rect_D6();
			
			window.open();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void 
	open() {
		
		////////////////////////////////

		// init: vars

		////////////////////////////////
		init_Vars();
		
		display = Display.getDefault();
		
		//REF http://stackoverflow.com/questions/4322253/screen-display-size answered Dec 1 '10 at 7:57
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		createContents();
		shell.open();
		shell.layout();
		
		////////////////////////////////

		// draw: initial

		////////////////////////////////
		this.init_Sizes();
		
		draw_Initial();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
//		//log
//		String text = "while => ended";
//
//		String fname = Thread.currentThread().getStackTrace()[2].getFileName();
//
//		int line_Num = Thread.currentThread().getStackTrace()[2]
//				.getLineNumber();
//
//		Methods.write_Log(text, fname, line_Num);
		
	}//open

	private void
	terminate() {
		
		running = false;
		
	}
	
	private void 
	init_Vars() {
		// TODO Auto-generated method stub
		
		CONS.Admin.status = 1;
		CONS.Admin.status_C = 1;
		
		CONS.Admin.orien_Current = CONS.Admin.Orien.HORI_VERTI;
//		CONS.Admin.orien_Current = CONS.Admin.Orien.VERTICAL;
		
		CONS.Admin.node_Current = 1;
		
		// node list
		CONS.Admin.list_NodeNames = new ArrayList<NodeNames>();
		
		CONS.Admin.list_NodeNames.add(CONS.Admin.NodeNames.B_UL);
		CONS.Admin.list_NodeNames.add(CONS.Admin.NodeNames.B_UR);
		CONS.Admin.list_NodeNames.add(CONS.Admin.NodeNames.B_LR);
		
		CONS.Admin.list_NodeNames.add(CONS.Admin.NodeNames.A_UR);
		CONS.Admin.list_NodeNames.add(CONS.Admin.NodeNames.A_LR);
		CONS.Admin.list_NodeNames.add(CONS.Admin.NodeNames.A_LL);
		
	}

	private void 
	draw_Initial() {
		// TODO Auto-generated method stub
		
		this.draw_Rect__A();
		
		this.draw_Rect__B();
		
		this.draw_Rect__C();
		
		this.draw_Periphery();
		
		this.update_Status_Label();
		
	}

	/**
	 * Create contents of the window.
	 */
	protected void 
	createContents() {
		shell = new Shell();
		
		int shell_W = (int) ((int)(float)dim.getWidth() * 0.8);
		int shell_H = (int) ((int)(float)dim.getHeight() * 0.8);
//		int shell_W = (int) ((int)(float)dim.getWidth() * 0.7);
//		int shell_H = (int) ((int)(float)dim.getHeight() * 0.7);
		
		shell.setSize(1246, 927);
//		shell.setSize(CONS.Views.win_W, CONS.Views.win_H);
//		shell.setSize(1200, 1000);
		shell.setText(CONS.Admin.str_MainWindow_Title);
		
		int shell_Loc_W = (int) (dim.getWidth() / 2 - shell.getSize().x / 2);
		int shell_Loc_H = (int) (dim.getHeight() / 2 - shell.getSize().y / 2);
		
		shell.setLocation(shell_Loc_W, shell_Loc_H);
		
		////////////////////////////////

		// setup: colors

		////////////////////////////////
		_init_Colors();
		
		////////////////////////////////
		
		// group
		
		////////////////////////////////
		_init_Views__Groups(shell);

		////////////////////////////////

		// menu

		////////////////////////////////
		this._init_Views__Menues(shell);
		
		_init_Views__Buttons(shell);
		
		_init_Views__Canvas(shell, gr_ops);

		this._init_Views__Labels(shell, gr_ops);
		
		_init_Set_Listeners(shell);
		
	}//createContents

	private void 
	init_Sizes() {
		// TODO Auto-generated method stub
	
		////////////////////////////////

		// rect: A

		////////////////////////////////
		CONS.Views.rect_A_X = cv_1.getSize().x / 2 - CONS.Views.rect_A_W / 2; 
		CONS.Views.rect_A_Y = cv_1.getSize().y / 2 - CONS.Views.rect_A_H + CONS.Views.offset_Y_A;
		
		////////////////////////////////
		
		// rect: B
		
		////////////////////////////////
		CONS.Views.rect_B_H_cur = CONS.Views.rect_B_H_orig;
		CONS.Views.rect_B_W_cur = CONS.Views.rect_B_W_orig;
		
		CONS.Views.rect_B_X = CONS.Views.rect_A_X; 
		CONS.Views.rect_B_Y = CONS.Views.rect_A_Y - CONS.Views.rect_B_H_cur;
		
		////////////////////////////////
		
		// rect: C
		
		////////////////////////////////
		CONS.Views.rect_C_H_cur = CONS.Views.rect_C_H_orig;
		CONS.Views.rect_C_W_cur = CONS.Views.rect_C_W_orig;
		
		CONS.Views.rect_C_X = CONS.Views.rect_B_X; 
		CONS.Views.rect_C_Y = CONS.Views.rect_B_Y - CONS.Views.rect_C_H_cur;
		
	}//init_Size_Rect_A

	private void 
	_init_Colors() {
		// TODO Auto-generated method stub
		
		Device device = Display.getCurrent ();
		red = new Color (device, 255, 0, 0);
		
		blue = new Color (device, 0, 0, 255);
		
		blue_light = new Color (device, 255, 0, 255);
		
		//REF http://web.njit.edu/~kevin/rgb.txt.html
		burlywood2 = new Color (device, 238, 197, 145);
		
		green = new Color (device, 0, 255, 0);
		
	}//_init_Colors

	private void 
	draw_Rect__A() {
		// TODO Auto-generated method stub

		//debug
		this.lbl_AreaData.setText("rect A: x = " + CONS.Views.rect_A_X);
		
		//REF http://stackoverflow.com/questions/23876389/java-draw-line-with-swt-not-deleting-previous-lines asked May 26 at 19:12
		GC gc = new GC(cv_1);
//        if(lp != null) {
//            gc.setXORMode(true);
		
		gc.setForeground(display.getSystemColor(SWT.COLOR_CYAN)); 
		
		//REF http://stackoverflow.com/questions/50064/setting-colors-in-swt answered Sep 8 '08 at 16:49
//		Device device = Display.getCurrent ();
//		Color red = new Color (device, 255, 0, 0);
		
		gc.setBackground(red); 
		
		//REF http://www.java2s.com/Tutorial/Java/0300__SWT-2D-Graphics/DrawingPointsLinesandsetlinewidth.htm
		gc.setLineWidth(CONS.Views.lineWidth_Rect);
		
		
		
//        gc.drawLine(0,0, 100, 100);
		gc.fillRectangle(
				CONS.Views.rect_A_X, 
				CONS.Views.rect_A_Y, 
//				cv_1.getSize().x / 2 - CONS.Views.rect_Main_W / 2, 
//				cv_1.getSize().y / 2 - CONS.Views.rect_Main_H, 
				CONS.Views.rect_A_W, 
				CONS.Views.rect_A_H);
//		gc.fillRectangle(
//				dim.getSize().width / 2, 
//				dim.getSize().height / 2, 
//				CONS.Views.rect_Main_W, 
//				CONS.Views.rect_Main_H);
//		gc.fillRectangle(100, 100, 200, 100);
//		gc.drawRectangle(100, 100, 100, 100);
//		gc.fillRectangle(0, 0, 100, 100);

        gc.dispose();
		
	}//draw_Rect
	
	private void 
	draw_Rect__B() {
		// TODO Auto-generated method stub
		
		//debug
//		this.lbl_1.setText("rect A: x = " + CONS.Views.rect_A_X);
		
		//REF http://stackoverflow.com/questions/23876389/java-draw-line-with-swt-not-deleting-previous-lines asked May 26 at 19:12
		GC gc = new GC(cv_1);
		
		gc.setForeground(display.getSystemColor(SWT.COLOR_CYAN)); 
		
		//REF http://stackoverflow.com/questions/50064/setting-colors-in-swt answered Sep 8 '08 at 16:49
//		Device device = Display.getCurrent ();
//		Color red = new Color (device, 255, 0, 0);
		
		gc.setBackground(blue); 
		
		//REF http://www.java2s.com/Tutorial/Java/0300__SWT-2D-Graphics/DrawingPointsLinesandsetlinewidth.htm
		gc.setLineWidth(CONS.Views.lineWidth_Rect);
		
		gc.fillRectangle(
				CONS.Views.rect_B_X, 
				CONS.Views.rect_B_Y, 
//				cv_1.getSize().x / 2 - CONS.Views.rect_Main_W / 2, 
//				cv_1.getSize().y / 2 - CONS.Views.rect_Main_H, 
				CONS.Views.rect_B_W_cur, 
				CONS.Views.rect_B_H_cur);
//		gc.fillRectangle(
//				dim.getSize().width / 2, 
//				dim.getSize().height / 2, 
//				CONS.Views.rect_Main_W, 
//				CONS.Views.rect_Main_H);
//		gc.fillRectangle(100, 100, 200, 100);
//		gc.drawRectangle(100, 100, 100, 100);
//		gc.fillRectangle(0, 0, 100, 100);
		
		gc.dispose();
		
	}//draw_Rect

	private void 
	draw_Rect__C() {
		// TODO Auto-generated method stub
		
		//REF http://stackoverflow.com/questions/23876389/java-draw-line-with-swt-not-deleting-previous-lines asked May 26 at 19:12
		GC gc = new GC(cv_1);
		
		gc.setForeground(display.getSystemColor(SWT.COLOR_CYAN)); 
		
		gc.setBackground(this.green); 
		
		//REF http://www.java2s.com/Tutorial/Java/0300__SWT-2D-Graphics/DrawingPointsLinesandsetlinewidth.htm
		gc.setLineWidth(CONS.Views.lineWidth_Rect);

		gc.fillRectangle(
				CONS.Views.rect_C_X, 
				CONS.Views.rect_C_Y, 
				CONS.Views.rect_C_W_cur, 
				CONS.Views.rect_C_H_cur);
		
		gc.dispose();
		
	}//draw_Rect__C
	
	
	void
	draw_Periphery() {
		
		int x = -1, y = -1, w = -1, h = -1;
		
		boolean set = true;
		
		switch(CONS.Admin.status) {
		
		case 1:
			
			x = CONS.Views.rect_B_X;
			y = CONS.Views.rect_B_Y;
			w = CONS.Views.rect_A_W;
			h = CONS.Views.rect_A_H + CONS.Views.rect_B_H_orig;
			
			break;
		
		case 2:
			
			x = CONS.Views.rect_B_X;
			y = CONS.Views.rect_B_Y;
			w = CONS.Views.rect_A_W;
			h = CONS.Views.rect_A_H + CONS.Views.rect_B_W_orig;
			
			break;
			
		case 3:
			
			x = CONS.Views.rect_A_X;
			y = CONS.Views.rect_B_Y;
			w = CONS.Views.rect_A_W;
			h = CONS.Views.rect_A_H + CONS.Views.rect_B_H_orig;
			
			break;
			
		case 4:
			
			x = CONS.Views.rect_A_X;
			y = CONS.Views.rect_B_Y;
			w = CONS.Views.rect_A_W;
			h = CONS.Views.rect_A_H + CONS.Views.rect_B_W_orig;
			
			break;
			
		case 5:
			
			x = CONS.Views.rect_A_X;
			y = CONS.Views.rect_A_Y;
			w = CONS.Views.rect_A_W + CONS.Views.rect_B_H_orig;
			h = CONS.Views.rect_A_H; 
			
			break;
			
		case 6:
			
			x = CONS.Views.rect_A_X;
			y = CONS.Views.rect_B_Y;
			w = CONS.Views.rect_A_W + CONS.Views.rect_B_W_orig;
			h = (CONS.Views.rect_A_H >= CONS.Views.rect_B_H_orig) 
				? CONS.Views.rect_A_H
					: CONS.Views.rect_B_H_orig;
			
			break;
			
		case 7:
			
			x = CONS.Views.rect_A_X;
			y = CONS.Views.rect_A_Y;
			w = CONS.Views.rect_A_W + CONS.Views.rect_B_H_orig;
			h = (CONS.Views.rect_A_H >= CONS.Views.rect_B_W_orig) 
					? CONS.Views.rect_A_H
							: CONS.Views.rect_B_W_orig;
			
			break;
			
		case 8:
			
			x = CONS.Views.rect_A_X;
			y = (CONS.Views.rect_A_Y < CONS.Views.rect_B_Y) 
					? CONS.Views.rect_A_Y
							: CONS.Views.rect_B_Y;
			
			w = CONS.Views.rect_A_W + CONS.Views.rect_B_W_orig;
			h = (CONS.Views.rect_A_H >= CONS.Views.rect_B_H_orig) 
					? CONS.Views.rect_A_H
							: CONS.Views.rect_B_H_orig;
			
			break;
			
		case 9:
			
			x = CONS.Views.rect_A_X;
			y = CONS.Views.rect_A_Y;
			
			w = CONS.Views.rect_A_W;
			h = CONS.Views.rect_A_H + CONS.Views.rect_B_H_orig;
			
			break;
			
		case 10:
			
			x = CONS.Views.rect_A_X;
			y = CONS.Views.rect_A_Y;
			
			w = Methods.larger_INT(CONS.Views.rect_A_W, CONS.Views.rect_B_H_orig);
			h = CONS.Views.rect_A_H + CONS.Views.rect_B_W_orig;
			
			break;
			
		case 11:
			
			x = CONS.Views.rect_A_X;
			y = CONS.Views.rect_A_Y;
			
			w = CONS.Views.rect_A_W;
			h = CONS.Views.rect_A_H + CONS.Views.rect_B_H_orig;
			
			break;
			
		case 12:
			
			x = CONS.Views.rect_A_X;
			y = CONS.Views.rect_A_Y;
			
			w = Methods.larger_INT(CONS.Views.rect_A_W, CONS.Views.rect_B_H_orig);
			h = CONS.Views.rect_A_H + CONS.Views.rect_B_W_orig;
			
			break;
			
		case 13:
			
			x = CONS.Views.rect_B_X;
			y = CONS.Views.rect_A_Y;
			
			w = CONS.Views.rect_A_W + CONS.Views.rect_B_H_orig;
			h = CONS.Views.rect_A_H;
			
			break;
			
		case 14:
			
			x = CONS.Views.rect_B_X;
			y = Methods.smaller_INT(CONS.Views.rect_A_Y, CONS.Views.rect_B_Y);
			
			w = CONS.Views.rect_A_W + CONS.Views.rect_B_W_orig;
			h = Methods.larger_INT(CONS.Views.rect_A_H, CONS.Views.rect_B_H_orig);
			
			break;
			
		case 15:
			
			x = CONS.Views.rect_B_X;
			y = CONS.Views.rect_B_Y;
			
			w = CONS.Views.rect_A_W + CONS.Views.rect_B_H_orig;
			h = Methods.larger_INT(CONS.Views.rect_A_H, CONS.Views.rect_B_W_orig);
			
			break;
			
		case 16:
			
			x = CONS.Views.rect_B_X;
			y = CONS.Views.rect_B_Y;
			
			w = CONS.Views.rect_A_W + CONS.Views.rect_B_W_orig;
			h = Methods.larger_INT(CONS.Views.rect_A_H, CONS.Views.rect_B_H_orig);
			
			break;
			
//		case 9:
//			
//			x = CONS.Views.rect_A_X;
//			y = CONS.Views.rect_A_Y;
//			
//			w = CONS.Views.rect_A_W;
//			h = CONS.Views.rect_A_H + CONS.Views.rect_B_H_orig;
//			
//			break;
			
		default: set = false; break;
		
		}//switch(CONS.Admin.status)
		
		if (set == false) {
			
			return;
			
		}
		
		////////////////////////////////

		// draw

		////////////////////////////////
		//REF http://stackoverflow.com/questions/23876389/java-draw-line-with-swt-not-deleting-previous-lines asked May 26 at 19:12
		GC gc = new GC(cv_1);
		
//		gc.setForeground(display.getSystemColor(SWT.COLOR_CYAN)); 
		
		//REF http://stackoverflow.com/questions/50064/setting-colors-in-swt answered Sep 8 '08 at 16:49
//		Device device = Display.getCurrent ();
//		Color red = new Color (device, 255, 0, 0);
		
//		gc.setBackground(this.burlywood2); 
		gc.setBackground(blue_light); 
		
		//REF http://www.java2s.com/Tutorial/Java/0300__SWT-2D-Graphics/DrawingPointsLinesandsetlinewidth.htm
		gc.setLineWidth(CONS.Views.lineWidth_Rect);
		
//		gc.fillRectangle(x, y, w, h);
		gc.drawRectangle(x, y, w, h);
		
		gc.dispose();
		
		////////////////////////////////

		// update: area data

		////////////////////////////////
		this.update_Label__AreaData(x, y, w, h);
		
	}//draw_Periphery

	private void 
	update_Label__AreaData(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// calculate

		////////////////////////////////
		int area = w * h;
		
		int remain = -1;
		
		boolean set = true;
		
		switch(CONS.Admin.status) {
		
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
			
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
			
		case 11:
		case 12:
		case 13:
		case 14:
		case 15:
			
		case 16:
			
			remain = area - (CONS.Views.rect_A_W * CONS.Views.rect_A_H)
							- (CONS.Views.rect_B_W_orig * CONS.Views.rect_B_H_orig);
			
			break;
		
		default: set = false; break;
		
		}
		
		////////////////////////////////

		// update

		////////////////////////////////
		if (set == false) {
			
			return;
			
		}
		
		String text = String.format(Locale.JAPAN, 
						"w = %d\nh = %d\narea = %d\nremain = %d", 
						w, h, area, remain);
		
		this.lbl_AreaData.setText(text);
		
	}
	

	private void 
	_init_Views__Groups
	(Shell shell) {
		// TODO Auto-generated method stub
	
		gr_navigate = new Group(shell, SWT.NONE);
		fd_gr_navigate = new FormData();
		fd_gr_navigate.right = new FormAttachment(100, -93);
		gr_navigate.setLayoutData(fd_gr_navigate);
		
		gr_ops = new Group(shell, SWT.NONE);
		fd_gr_navigate.bottom = new FormAttachment(100, -645);
		fd_gr_ops = new FormData();
		fd_gr_ops.top = new FormAttachment(gr_navigate, 6);
		fd_gr_ops.right = new FormAttachment(100, -55);
		fd_gr_ops.left = new FormAttachment(0, 961);
		gr_ops.setLayoutData(fd_gr_ops);
		gr_ops.setLayout(new FillLayout(SWT.HORIZONTAL));

	}
	

	private void 
	_init_Set_Listeners
	(Shell shell) {
		// TODO Auto-generated method stub

		bt_Quit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				////////////////////////////////

				// dialog

				////////////////////////////////
				//REF http://www.vogella.com/tutorials/EclipseDialogs/article.html
				MessageBox dialog = 
						  new MessageBox(Rect_D6.this.shell, SWT.ICON_QUESTION | SWT.OK| SWT.CANCEL);
									dialog.setText(CONS.Strings.title_Confirm);
									dialog.setMessage(CONS.Strings.msg_QuitApp);

//						# open dialog and await user selection
				int returnCode = dialog.open();


				//REF return code http://help.eclipse.org/indigo/index.jsp?topic=%2Forg.eclipse.platform.doc.isv%2Freference%2Fapi%2Forg%2Feclipse%2Fswt%2Fwidgets%2FMessageBox.html
				if (returnCode == SWT.OK) {
					
					System.exit(0);
					
				}
				
//				//log
//				String text = "return code => " + returnCode;
//
//				String fname = Thread.currentThread().getStackTrace()[2]
//						.getFileName();
//
//				int line_Num = Thread.currentThread().getStackTrace()[2]
//						.getLineNumber();
//
//				Methods.write_Log(text, fname, line_Num);

//				System.exit(0);
			}
		});

		////////////////////////////////

		// execute

		////////////////////////////////
		bt_Execute.addSelectionListener(new SelectionAdapter() {
			@Override
			public void 
			widgetSelected(SelectionEvent e) {

				print = new Run_Draw();
				
				applicationThread = new Th_Draw(display, print);
				
				applicationThread.start();

				
//				applicationThread.start();
				
//				if (applicationThread == null || !applicationThread.isAlive()) {
//					
//					applicationThread = new Thread("applicationThread") {
//						public void run() {
//							
//							System.out.println("Hello from thread: \t" + Thread.currentThread().getName());
//							
//							display.asyncExec(print);
////						display.syncExec(print);
//							
//							System.out.println("Bye from thread: \t" + Thread.currentThread().getName());
//							
//						}
//					};
//					
//					applicationThread.start();
//					
//				} else {
//
//					System.out.println("Thread => not null or is alive" + Thread.currentThread().getName());
//					
//				}
				
				
//				//REF http://stackoverflow.com/questions/23876389/java-draw-line-with-swt-not-deleting-previous-lines asked May 26 at 19:12
//				GC gc = new GC(cv_1);
////                if(lp != null) {
////                    gc.setXORMode(true);
//				
//				gc.setForeground(display.getSystemColor(SWT.COLOR_BLUE)); 
//				
//				//REF http://www.java2s.com/Tutorial/Java/0300__SWT-2D-Graphics/DrawingPointsLinesandsetlinewidth.htm
//				gc.setLineWidth(10);
//		
//				gc.drawRectangle(0, 0, 100, 100);
////				gc.fillRectangle(0, 0, 100, 100);
////                    gc.drawLine(0,0, 100, 100);
//
////                    lp = new Point(e.x, e.y);
////                    gc.drawLine(fp.x, fp.y, lp.x, lp.y);
////                }else {
////                    lp = new Point(e.x, e.y);
////                }
//
//				gc.dispose();
//				
//				// label
//				Rect.this.f_Executing = true;
//				
////				while (f_Executing) {
//
//					Rect.this.lbl_AreaData.setText("executing... " + count);
//					
//					
////        					lbl_1.setText("clicked => " + count);
//					
//					count ++;
//					
////				}
//
////				////////////////////////////////
////
////				// dialog
////
////				////////////////////////////////
////				//REF http://www.vogella.com/tutorials/EclipseDialogs/article.html
////				MessageBox dialog = 
////						  new MessageBox(Rect.this.shell, SWT.ICON_QUESTION | SWT.OK| SWT.CANCEL);
////									dialog.setText("My info");
////									dialog.setMessage("Do you really want to do this?");
////
//////						# open dialog and await user selection
////				int returnCode = dialog.open();
////
////				
////				
////				//log
////				String text = "return code => " + returnCode;
////
////				String fname = Thread.currentThread().getStackTrace()[2]
////						.getFileName();
////
////				int line_Num = Thread.currentThread().getStackTrace()[2]
////						.getLineNumber();
////
////				Methods.write_Log(text, fname, line_Num);
				
			}//widgetSelected
			
		});

		////////////////////////////////

		// message

		////////////////////////////////
		bt_Clear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void 
			widgetSelected(SelectionEvent e) {

//				//test
//				if (Rect.this.frame != null) {
//					
//					Rect.this.frame.setVisible(false);
//					
//				}
				
				if (applicationThread != null) {
					
		            Rect_D6.this.terminate();
		            
		            try {
						Rect_D6.this.applicationThread.join();
						
						System.out.println("Thread => stopped: \t" + Thread.currentThread().getName());
						
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
//		            LOGGER.debug("Thread successfully stopped.");
		            
		        }
				
//				applicationThread.stop();
				
//				Rect.this.clear_Canvas();
				
//				Rect.this.f_Executing = false;
////				f_Executing = false;
//				
////				WB.this.lbl_1.setText("Cleared");
//				
//				// clear canvas
//				GC gc = new GC(cv_1);
////              if(lp != null) {
////                  gc.setXORMode(true);
//				
////				gc.setForeground(display.getSystemColor(SWT.COLOR_BLUE)); 
//				
//				//REF http://www.java2s.com/Tutorial/Java/0300__SWT-2D-Graphics/DrawingPointsLinesandsetlinewidth.htm
////				gc.setLineWidth(10);
////				
////				gc.drawLine(0,0, 100, 100);
//
////                  lp = new Point(e.x, e.y);
////                  gc.drawLine(fp.x, fp.y, lp.x, lp.y);
////              }else {
////                  lp = new Point(e.x, e.y);
////              }
//
//				Rect.this.cv_1.drawBackground(gc, 0, 0, Rect.this.cv_1.getSize().x, Rect.this.cv_1.getSize().y);
//
//				gc.dispose();
				
				
			}
			
		});//bt_Clear.addSelectionListener

		////////////////////////////////

		// options

		////////////////////////////////
		btnOptions.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				
				
			}
		});

		
	}//_init_Set_Listeners

	private void 
	_init_Views__Buttons
	(Shell shell) {

		bt_Execute = new Button(gr_navigate, SWT.NONE);
		bt_Execute.setBounds(10, 10, 115, 37);
		
		bt_Execute.setText("Execute");
		
		bt_Clear = new Button(gr_navigate, SWT.NONE);
		bt_Clear.setBounds(10, 53, 115, 37);
		bt_Clear.setText("Clear");
		
		
		
		bt_Quit = new Button(gr_navigate, SWT.NONE);
		//		bt_Quit.setFont(SWTResourceManager.getFont("メイリオ", 9, SWT.ITALIC));
		//		bt_Quit.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
				
				bt_Quit.setBounds(10, 96, 115, 37);
				bt_Quit.setText("Quit");

		bt_Back = new Button(this.gr_ops, SWT.NONE);
		
		bt_Back.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
//				Rect_D6.this._move_Rect_B_left();
				
				////////////////////////////////

				// prep: params

				////////////////////////////////
				// node name
				int node_Num = Methods.get_NodeNumber_frmo_Status(CONS.Admin.status_C);

				NodeNames name = null;
				
				name = CONS.Admin.list_NodeNames.get(node_Num - 1);
				
				// orien
				CONS.Admin.orien_Current = 
							Methods.get_PrevOrien(name, CONS.Admin.orien_Current);
//				Methods.get_NextOrien(name, CONS.Admin.orien_Current);
				
				//log
				String text = String.format(
							Locale.JAPAN, 
							"node = %s / orien = %s", 
							name.toString(), CONS.Admin.orien_Current.toString());

				String fname = Thread.currentThread().getStackTrace()[1]
//						String fname = Thread.currentThread().getStackTrace()[2]
						.getFileName();

				int line_Num = Thread.currentThread().getStackTrace()[1]
						.getLineNumber();

				System.out.format(Locale.JAPAN, "[%s:%d] %s\n", fname, line_Num,
//						System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num,
						text);

				////////////////////////////////

				// move

				////////////////////////////////
				Rect_D6.this._move_Rect_C_LEFT(name, CONS.Admin.orien_Current);
//				Rect_D6.this._move_Rect_C_RIGHT(name, CONS.Admin.orien_Current);
				
			}
		});
		
		bt_Back.setText("<-");
		
		bt_Forward = new Button(this.gr_ops, SWT.NONE);
		
		bt_Forward.addSelectionListener(new SelectionAdapter() {
			@Override
			public void 
			widgetSelected(SelectionEvent e) {
				
				////////////////////////////////

				// prep: params

				////////////////////////////////
				// node name
				int node_Num = Methods.get_NodeNumber_frmo_Status(CONS.Admin.status_C);

				NodeNames name = null;
				
				name = CONS.Admin.list_NodeNames.get(node_Num - 1);
				
				// orien
				CONS.Admin.orien_Current = 
							Methods.get_NextOrien(name, CONS.Admin.orien_Current);
				
				//log
				String text = String.format(
							Locale.JAPAN, 
							"node = %s / orien = %s", 
							name.toString(), CONS.Admin.orien_Current.toString());

				String fname = Thread.currentThread().getStackTrace()[1]
//						String fname = Thread.currentThread().getStackTrace()[2]
						.getFileName();

				int line_Num = Thread.currentThread().getStackTrace()[1]
						.getLineNumber();

				System.out.format(Locale.JAPAN, "[%s:%d] %s\n", fname, line_Num,
//						System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num,
						text);

				////////////////////////////////

				// move

				////////////////////////////////
				Rect_D6.this._move_Rect_C_RIGHT(name, CONS.Admin.orien_Current);
				
			}//widgetSelected(SelectionEvent e)

		});//addSelectionListener(new SelectionAdapter()
		
		bt_Forward.setText("->");
		
		////////////////////////////////

		// options

		////////////////////////////////
		Group group = new Group(shell, SWT.NONE);
		fd_gr_navigate.top = new FormAttachment(group, 59);
		FormData fd_group = new FormData();
		fd_group.left = new FormAttachment(0, 867);
		fd_group.right = new FormAttachment(100, -222);
		fd_group.bottom = new FormAttachment(lbl_Status, -15);
		group.setLayoutData(fd_group);

		btnOptions = new Button(group, SWT.NONE);
		btnOptions.setBounds(10, 0, 115, 37);
		btnOptions.setText("Options");

		
	}//_init_Views__Buttons
	
	protected void 
	_move_Rect_B_left() {
		// TODO Auto-generated method stub

		switch(CONS.Admin.status) {
		
		case 1: _move_Rect_B__Case_16(); break;
		
		case 2: _move_Rect_B__Case_1(); break;
		case 3: _move_Rect_B__Case_2(); break;
		case 4: _move_Rect_B__Case_3(); break;

		case 5: _move_Rect_B__Case_4(); break;
		case 6: _move_Rect_B__Case_5(); break;
		case 7: _move_Rect_B__Case_6(); break;
		
		case 8: _move_Rect_B__Case_7(); break;
		case 9: _move_Rect_B__Case_8(); break;
		case 10: _move_Rect_B__Case_9(); break;
		
		case 11: _move_Rect_B__Case_10(); break;
		case 12: _move_Rect_B__Case_11(); break;
		case 13: _move_Rect_B__Case_12(); break;
		
		case 14: _move_Rect_B__Case_13(); break;
		case 15: _move_Rect_B__Case_14(); break;
		case 16: _move_Rect_B__Case_15(); break;
		
		}//switch(CONS.Admin.status)

		////////////////////////////////

		// draw: periphery

		////////////////////////////////
		this.draw_Periphery();
		
	}//_move_Rect_B_left

	protected void 
	_move_Rect_B_left(int status) {
		// TODO Auto-generated method stub
		
		switch(status) {
		
		case 1: 
//			_move_Rect_B__Case_16(); 
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X - CONS.Views.rect_B_W_orig;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_W_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_H_orig;
			
			// status
			CONS.Admin.status = 16;

			break;
		
		case 2: 
//			_move_Rect_B__Case_1(); 
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X - CONS.Views.rect_B_H_orig;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_H_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_W_orig;
			
			// status
			CONS.Admin.status = 15;

			
			break;
		
		case 3: 
//			_move_Rect_B__Case_2(); 
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y - CONS.Views.rect_B_W_orig;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_H_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_W_orig;
			
			// status
			CONS.Admin.status = 2;

			break;
			
		case 4: 
//			_move_Rect_B__Case_3(); 
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X + CONS.Views.rect_A_W - CONS.Views.rect_B_W_orig;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y - CONS.Views.rect_B_H_orig;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_W_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_H_orig;

			// status
			CONS.Admin.status = 3;

			
			break;
		
		case 5: 
//			_move_Rect_B__Case_4(); 
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X + CONS.Views.rect_A_W - CONS.Views.rect_B_H_orig;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y - CONS.Views.rect_B_W_orig;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_H_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_W_orig;
			
			// status
			CONS.Admin.status = 4;
			
			break;
			
		case 6: 
//			_move_Rect_B__Case_5(); 
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X + CONS.Views.rect_A_W;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_H_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_W_orig;

			// status
			CONS.Admin.status = 5;
			
			break;
			
		case 7: 
//			_move_Rect_B__Case_6(); 
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X + CONS.Views.rect_A_W;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_W_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_H_orig;
			
			CONS.Admin.status = 6;
			
			break;
		
		case 8: 
//			_move_Rect_B__Case_7(); 
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X + CONS.Views.rect_A_W;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y + CONS.Views.rect_A_H - CONS.Views.rect_B_W_orig;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_H_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_W_orig;

			// status
			CONS.Admin.status = 7;
			break;
			
		case 9: 
//			_move_Rect_B__Case_8(); 
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X + CONS.Views.rect_A_W;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y + CONS.Views.rect_A_H - CONS.Views.rect_B_H_orig;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_W_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_H_orig;

			// status
			CONS.Admin.status = 8;
			
			break;
			
		case 10: 
//			_move_Rect_B__Case_9(); 
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X + CONS.Views.rect_A_W - CONS.Views.rect_B_W_orig;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y + CONS.Views.rect_A_H;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_W_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_H_orig;

			// status
			CONS.Admin.status = 9;

			break;
		
		case 11: 
//			_move_Rect_B__Case_10(); 
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X + CONS.Views.rect_A_W - CONS.Views.rect_B_H_orig;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y + CONS.Views.rect_A_H;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_H_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_W_orig;
			
			// status
			CONS.Admin.status = 10;

			break;
			
		case 12: 
//			_move_Rect_B__Case_11(); 
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y + CONS.Views.rect_A_H;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_W_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_H_orig;
			
			// status
			CONS.Admin.status = 11;

			break;
			
		case 13: 
//			_move_Rect_B__Case_12(); 
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y + CONS.Views.rect_A_H;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_H_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_W_orig;
			
			// status
			CONS.Admin.status = 12;

			break;
		
		case 14: 
//			_move_Rect_B__Case_13(); 
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X - CONS.Views.rect_B_H_orig;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y + CONS.Views.rect_A_H - CONS.Views.rect_B_W_orig;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_H_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_W_orig;
			
			// status
			CONS.Admin.status = 13;

			break;
			
		case 15: 
//			_move_Rect_B__Case_14(); 
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X - CONS.Views.rect_B_W_orig;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y + CONS.Views.rect_A_H - CONS.Views.rect_B_H_orig;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_W_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_H_orig;
			
			// status
			CONS.Admin.status = 14;

			break;
			
		case 16: 
//			_move_Rect_B__Case_15(); 
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X - CONS.Views.rect_B_H_orig;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_H_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_W_orig;
			
			// status
			CONS.Admin.status = 15;

			break;
		
		}//switch(CONS.Admin.status)

		////////////////////////////////

		// draw

		////////////////////////////////
		this.clear_Canvas();
		
		this.draw_Rect__A();
		this.draw_Rect__B();

		////////////////////////////////
		
		// draw: periphery
		
		////////////////////////////////
		this.draw_Periphery();
		
	}//_move_Rect_B_left
	
	
	private void 
	_move_Rect_B__Case_1() {
		// TODO Auto-generated method stub

		////////////////////////////////

		// update: params

		////////////////////////////////
		// X
		CONS.Views.rect_B_X = CONS.Views.rect_A_X;
		
		// Y
		CONS.Views.rect_B_Y = CONS.Views.rect_A_Y - CONS.Views.rect_B_H_orig;
		
		// W
		CONS.Views.rect_B_W_cur = CONS.Views.rect_B_W_orig;
		
		// H
		CONS.Views.rect_B_H_cur = CONS.Views.rect_B_H_orig;
		
		////////////////////////////////

		// draw

		////////////////////////////////
		this.clear_Canvas();
		
		this.draw_Rect__A();
		this.draw_Rect__B();

		////////////////////////////////
		
		// update: status value
		
		////////////////////////////////
		CONS.Admin.status = 1;
		
		////////////////////////////////

		// update: status label

		////////////////////////////////
		update_Status_Label();

	}

	protected void 
	_move_Rect_B_right() {
		// TODO Auto-generated method stub
	
//		CONS.Admin.status = 1;
		
		switch(CONS.Admin.status) {
		
		case 1: _move_Rect_B__Case_2(); break;
		case 2: _move_Rect_B__Case_3(); break;
		case 3: _move_Rect_B__Case_4(); break;
			
		case 4: _move_Rect_B__Case_5(); break;
		case 5: _move_Rect_B__Case_6(); break;
		case 6: _move_Rect_B__Case_7(); break;
			
		case 7: _move_Rect_B__Case_8(); break;
		case 8: _move_Rect_B__Case_9(); break;
		case 9: _move_Rect_B__Case_10(); break;

		case 10: _move_Rect_B__Case_11(); break;
		case 11: _move_Rect_B__Case_12(); break;
		case 12: _move_Rect_B__Case_13(); break;
		
		case 13: _move_Rect_B__Case_14(); break;
		case 14: _move_Rect_B__Case_15(); break;
		case 15: _move_Rect_B__Case_16(); break;
		
		case 16: _move_Rect_B__Case_1(); break;
		
		}//switch(CONS.Admin.status)

		////////////////////////////////

		// draw: periphery

		////////////////////////////////
		this.draw_Periphery();

	}//_move_Rect_B_right

	protected void 
	_move_Rect_B_right(int status) {
		// TODO Auto-generated method stub
		
//		CONS.Admin.status = 1;
		
		switch(status) {
	
		case 1: 
			
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y - CONS.Views.rect_B_W_orig;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_H_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_W_orig;
			
			// status
			CONS.Admin.status = 2;
			
			break;

		case 2: 
//			_move_Rect_B__Case_3(); 
			
			////////////////////////////////
			
			// update: params
			
			////////////////////////////////
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X + CONS.Views.rect_A_W - CONS.Views.rect_B_W_orig;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y - CONS.Views.rect_B_H_orig;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_W_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_H_orig;

			// status
			CONS.Admin.status = 3;
			
			break;
			
		case 3: 
//			_move_Rect_B__Case_4(); 
			
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X + CONS.Views.rect_A_W - CONS.Views.rect_B_H_orig;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y - CONS.Views.rect_B_W_orig;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_H_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_W_orig;
			
			// status
			CONS.Admin.status = 4;
			
			break;
			
		case 4: 
//			_move_Rect_B__Case_5(); 
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X + CONS.Views.rect_A_W;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_H_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_W_orig;

			// status
			CONS.Admin.status = 5;
			
			break;
			
		case 5: 
//			_move_Rect_B__Case_6(); 
			
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X + CONS.Views.rect_A_W;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_W_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_H_orig;
			
			CONS.Admin.status = 6;

			break;
			
		case 6: 
//			_move_Rect_B__Case_7(); 
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X + CONS.Views.rect_A_W;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y + CONS.Views.rect_A_H - CONS.Views.rect_B_W_orig;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_H_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_W_orig;

			// status
			CONS.Admin.status = 7;

			break;
			
		case 7: 
//			_move_Rect_B__Case_8(); 
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X + CONS.Views.rect_A_W;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y + CONS.Views.rect_A_H - CONS.Views.rect_B_H_orig;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_W_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_H_orig;

			// status
			CONS.Admin.status = 8;

			break;
			
		case 8: 
//			_move_Rect_B__Case_9(); 
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X + CONS.Views.rect_A_W - CONS.Views.rect_B_W_orig;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y + CONS.Views.rect_A_H;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_W_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_H_orig;

			// status
			CONS.Admin.status = 9;
			
			break;
			
		case 9: 
//			_move_Rect_B__Case_10(); 
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X + CONS.Views.rect_A_W - CONS.Views.rect_B_H_orig;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y + CONS.Views.rect_A_H;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_H_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_W_orig;
			
			// status
			CONS.Admin.status = 10;

			break;

		case 10: 
//			_move_Rect_B__Case_11(); 
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y + CONS.Views.rect_A_H;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_W_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_H_orig;
			
			// status
			CONS.Admin.status = 11;
			
			break;
			
		case 11: 
//			_move_Rect_B__Case_12(); 
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y + CONS.Views.rect_A_H;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_H_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_W_orig;
			
			// status
			CONS.Admin.status = 12;

			break;
			
		case 12: 
//			_move_Rect_B__Case_13(); 
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X - CONS.Views.rect_B_H_orig;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y + CONS.Views.rect_A_H - CONS.Views.rect_B_W_orig;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_H_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_W_orig;
			
			// status
			CONS.Admin.status = 13;
			
			break;
		
		case 13: 
//			_move_Rect_B__Case_14(); 
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X - CONS.Views.rect_B_W_orig;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y + CONS.Views.rect_A_H - CONS.Views.rect_B_H_orig;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_W_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_H_orig;
			
			// status
			CONS.Admin.status = 14;
			
			break;
		case 14: 
//			_move_Rect_B__Case_15(); 
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X - CONS.Views.rect_B_H_orig;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_H_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_W_orig;
			
			// status
			CONS.Admin.status = 15;
			
			break;
			
		case 15: 
//			_move_Rect_B__Case_16(); 
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X - CONS.Views.rect_B_W_orig;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_W_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_H_orig;
			
			// status
			CONS.Admin.status = 16;
			
			break;
		
		case 16: 
//			_move_Rect_B__Case_1(); 
			// X
			CONS.Views.rect_B_X = CONS.Views.rect_A_X;
			
			// Y
			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y - CONS.Views.rect_B_H_orig;
			
			// W
			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_W_orig;
			
			// H
			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_H_orig;
			
			// status
			CONS.Admin.status = 1;
			
			break;

		}//switch(CONS.Admin.status)
		
		////////////////////////////////
		
		// draw
		
		////////////////////////////////
		this.clear_Canvas();
		
		this.draw_Rect__A();
		this.draw_Rect__B();
		
		////////////////////////////////
		
		// draw: periphery
		
		////////////////////////////////
		this.draw_Periphery();

		////////////////////////////////

		// update: status label

		////////////////////////////////
		update_Status_Label();

	}//_move_Rect_B_right(int status)
	
	protected void 
	_move_Rect_C_right(int status) {
		// TODO Auto-generated method stub
		
//		CONS.Admin.status = 1;
		
		switch(status) {
		
		case 1: 

			////////////////////////////////

			// coordinates

			////////////////////////////////
			// X
			CONS.Views.rect_C_X = CONS.Views.rect_A_X;
			
			// Y
			CONS.Views.rect_C_Y = CONS.Views.rect_B_Y - CONS.Views.rect_C_W_orig;
			
			// W
			CONS.Views.rect_C_W_cur = CONS.Views.rect_C_H_orig;
			
			// H
			CONS.Views.rect_C_H_cur = CONS.Views.rect_C_W_orig;
			
			////////////////////////////////

			// meta

			////////////////////////////////
			// status
			CONS.Admin.status_C = 2;
			
			// current node
			CONS.Admin.node_Current += CONS.Admin.status_C % 2;
			
			// orientation
			CONS.Admin.orien_Current = CONS.Admin.Orien.HORI_HORI;
//			CONS.Admin.orien_Current = CONS.Admin.Orien.HORIZONTAL;
			
			break;
			
		case 2: 
//			_move_Rect_B__Case_3(); 
			
			////////////////////////////////
			
			// update: params
			
			////////////////////////////////
//			// X
//			CONS.Views.rect_B_X = CONS.Views.rect_A_X + CONS.Views.rect_A_W - CONS.Views.rect_B_W_orig;
//			
//			// Y
//			CONS.Views.rect_B_Y = CONS.Views.rect_A_Y - CONS.Views.rect_B_H_orig;
//			
//			// W
//			CONS.Views.rect_B_W_cur = CONS.Views.rect_B_W_orig;
//			
//			// H
//			CONS.Views.rect_B_H_cur = CONS.Views.rect_B_H_orig;
//			
//			// status
//			CONS.Admin.status = 3;
			
			break;
			
			
		}//switch(CONS.Admin.status)
		
		////////////////////////////////
		
		// draw
		
		////////////////////////////////
		this.clear_Canvas();
		
		this.draw_Rect__A();
		this.draw_Rect__B();
		this.draw_Rect__C();
		
		////////////////////////////////
		
		// draw: periphery
		
		////////////////////////////////
		this.draw_Periphery();
		
		////////////////////////////////
		
		// update: status label
		
		////////////////////////////////
		update_Status_Label();
		
	}//_move_Rect_B_right(int status)
	
	protected void 
	_move_Rect_C_RIGHT(NodeNames node_Name, Orien orien) {
		// TODO Auto-generated method stub
		
//		CONS.Admin.status = 1;
		
		switch(node_Name) {
		
		case B_UL://--------------------------------------
			
			switch(orien) {

			case HORI_VERTI://--------------------------------------

				CONS.Views.rect_C_H_cur = CONS.Views.rect_C_H_orig;
				CONS.Views.rect_C_W_cur = CONS.Views.rect_C_W_orig;
				
				CONS.Views.rect_C_X = CONS.Views.rect_B_X; 
				CONS.Views.rect_C_Y = CONS.Views.rect_B_Y - CONS.Views.rect_C_H_cur;

				////////////////////////////////
				
				// meta
				
				////////////////////////////////
				// status
				CONS.Admin.status_C = 1;
				
				// current node
				CONS.Admin.node_Current = 
//						CONS.Admin.node_Current += 
								Methods.get_NodeNumber_frmo_Status(CONS.Admin.status_C);
//				CONS.Admin.node_Current += CONS.Admin.status_C % 2;

				break;//case HORI_VERTI

			case HORI_HORI://--------------------------------------
				
				////////////////////////////////
				
				// coordinates
				
				////////////////////////////////
				// X
				CONS.Views.rect_C_X = CONS.Views.rect_A_X;
				
				// Y
				CONS.Views.rect_C_Y = CONS.Views.rect_B_Y - CONS.Views.rect_C_W_orig;
				
				// W
				CONS.Views.rect_C_W_cur = CONS.Views.rect_C_H_orig;
				
				// H
				CONS.Views.rect_C_H_cur = CONS.Views.rect_C_W_orig;
				
				////////////////////////////////
				
				// meta
				
				////////////////////////////////
				// status
				CONS.Admin.status_C = 2;
				
				// current node
				CONS.Admin.node_Current = 
//						CONS.Admin.node_Current += 
								Methods.get_NodeNumber_frmo_Status(CONS.Admin.status_C);
//				CONS.Admin.node_Current += CONS.Admin.status_C % 2;
				
//				// orientation
//				CONS.Admin.orien_Current = CONS.Admin.Orien.HORIZONTAL;
				
				break;//case HORI_HORI
				
			case VERTI_HORI://--------------------------------------
				
				CONS.Views.rect_C_H_cur = CONS.Views.rect_C_W_orig;
				CONS.Views.rect_C_W_cur = CONS.Views.rect_C_H_orig;
				
				CONS.Views.rect_C_X = CONS.Views.rect_B_X - CONS.Views.rect_C_H_orig; 
				CONS.Views.rect_C_Y = CONS.Views.rect_B_Y;
				
				////////////////////////////////
				
				// meta
				
				////////////////////////////////
				// status
				CONS.Admin.status_C = 
						Methods.get_Status_from_NodeAndPosition(
								node_Name, 
								orien);
//				CONS.Admin.Orien.VERTI_VERTI);
				
				// current node number
				CONS.Admin.node_Current = 
						Methods.get_NodeNumber_frmo_Status(CONS.Admin.status_C);
//				CONS.Admin.node_Current += CONS.Admin.status_C % 2;
				
				break;//case VERTI_VERTI
				
			case VERTI_VERTI://--------------------------------------
				
				CONS.Views.rect_C_H_cur = CONS.Views.rect_C_H_orig;
				CONS.Views.rect_C_W_cur = CONS.Views.rect_C_W_orig;
				
				CONS.Views.rect_C_X = CONS.Views.rect_B_X - CONS.Views.rect_C_W_orig; 
				CONS.Views.rect_C_Y = CONS.Views.rect_B_Y;
				
				////////////////////////////////
				
				// meta
				
				////////////////////////////////
				// status
				CONS.Admin.status_C = 
								Methods.get_Status_from_NodeAndPosition(
											CONS.Admin.NodeNames.B_UL, 
											CONS.Admin.Orien.VERTI_VERTI);
				
				// current node number
				CONS.Admin.node_Current = 
						Methods.get_NodeNumber_frmo_Status(CONS.Admin.status_C);
//				CONS.Admin.node_Current += CONS.Admin.status_C % 2;
				
				break;//case VERTI_VERTI
				
			}//switch(orien)
			
//			////////////////////////////////
//			
//			// coordinates
//			
//			////////////////////////////////
//			// X
//			CONS.Views.rect_C_X = CONS.Views.rect_A_X;
//			
//			// Y
//			CONS.Views.rect_C_Y = CONS.Views.rect_B_Y - CONS.Views.rect_C_W_orig;
//			
//			// W
//			CONS.Views.rect_C_W_cur = CONS.Views.rect_C_H_orig;
//			
//			// H
//			CONS.Views.rect_C_H_cur = CONS.Views.rect_C_W_orig;
//			
//			////////////////////////////////
//			
//			// meta
//			
//			////////////////////////////////
//			// status
//			CONS.Admin.status_C = 2;
//			
//			// current node
//			CONS.Admin.node_Current += CONS.Admin.status_C % 2;
//			
//			// orientation
//			CONS.Admin.orien_Current = CONS.Admin.Orien.HORIZONTAL;
			
			break;//case B_UL:
			
		}//switch(CONS.Admin.status)
		
		////////////////////////////////
		
		// draw
		
		////////////////////////////////
		this.clear_Canvas();
		
		this.draw_Rect__A();
		this.draw_Rect__B();
		this.draw_Rect__C();
		
		////////////////////////////////
		
		// draw: periphery
		
		////////////////////////////////
		this.draw_Periphery();
		
		////////////////////////////////
		
		// update: status label
		
		////////////////////////////////
		update_Status_Label();
		
	}//_move_Rect_B_right(int status)
	
	 
	
	protected void 
	_move_Rect_C_LEFT(NodeNames node_Name, Orien orien) {
		// TODO Auto-generated method stub
		
//		CONS.Admin.status = 1;
		
		switch(node_Name) {
		
		case B_UL://--------------------------------------
			
			switch(orien) {
			
			case HORI_VERTI://--------------------------------------
				
				CONS.Views.rect_C_H_cur = CONS.Views.rect_C_H_orig;
				CONS.Views.rect_C_W_cur = CONS.Views.rect_C_W_orig;
				
				CONS.Views.rect_C_X = CONS.Views.rect_B_X; 
				CONS.Views.rect_C_Y = CONS.Views.rect_B_Y - CONS.Views.rect_C_H_cur;
				
				////////////////////////////////
				
				// meta
				
				////////////////////////////////
				// status
				CONS.Admin.status_C = 1;
				
				// current node
				CONS.Admin.node_Current = 
//						CONS.Admin.node_Current += 
						Methods.get_NodeNumber_frmo_Status(CONS.Admin.status_C);
//				CONS.Admin.node_Current += CONS.Admin.status_C % 2;
				
				break;//case HORI_VERTI
				
			case HORI_HORI://--------------------------------------

				////////////////////////////////
				
				// coordinates
				
				////////////////////////////////
				CONS.Views.rect_C_H_cur = CONS.Views.rect_C_W_orig;
				CONS.Views.rect_C_W_cur = CONS.Views.rect_C_H_orig;
				
				CONS.Views.rect_C_X = CONS.Views.rect_B_X; 
				CONS.Views.rect_C_Y = CONS.Views.rect_B_Y - CONS.Views.rect_C_W_orig;
				
				////////////////////////////////
				
				// meta
				
				////////////////////////////////
				// status
				CONS.Admin.status_C = 2;
				
				// current node
				CONS.Admin.node_Current = 
//						CONS.Admin.node_Current += 
						Methods.get_NodeNumber_frmo_Status(CONS.Admin.status_C);
//				CONS.Admin.node_Current += CONS.Admin.status_C % 2;

//				////////////////////////////////
//				
//				// coordinates
//				
//				////////////////////////////////
//				// X
//				CONS.Views.rect_C_X = CONS.Views.rect_A_X;
//				
//				// Y
//				CONS.Views.rect_C_Y = CONS.Views.rect_B_Y - CONS.Views.rect_C_W_orig;
//				
//				// W
//				CONS.Views.rect_C_W_cur = CONS.Views.rect_C_H_orig;
//				
//				// H
//				CONS.Views.rect_C_H_cur = CONS.Views.rect_C_W_orig;
//				
//				////////////////////////////////
//				
//				// meta
//				
//				////////////////////////////////
//				// status
//				CONS.Admin.status_C = 2;
//				
//				// current node
//				CONS.Admin.node_Current = 
////						CONS.Admin.node_Current += 
//						Methods.get_NodeNumber_frmo_Status(CONS.Admin.status_C);
////				CONS.Admin.node_Current += CONS.Admin.status_C % 2;
//				
////				// orientation
////				CONS.Admin.orien_Current = CONS.Admin.Orien.HORIZONTAL;
				
				break;//case HORI_HORI
				
			case VERTI_HORI://--------------------------------------
				
				// => to HORI_HORI
				CONS.Views.rect_C_H_cur = CONS.Views.rect_C_W_orig;
				CONS.Views.rect_C_W_cur = CONS.Views.rect_C_H_orig;
				
				CONS.Views.rect_C_X = CONS.Views.rect_B_X - CONS.Views.rect_C_H_orig; 
				CONS.Views.rect_C_Y = CONS.Views.rect_B_Y;
				
				////////////////////////////////
				
				// meta
				
				////////////////////////////////
				//log
				String text = String.format(
							Locale.JAPAN, 
							"name = %s, orien = %s\n", 
							node_Name.toString(), orien.toString());
				
				String fname = Thread.currentThread().getStackTrace()[1].getFileName();
				
				int line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
				
				System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);


				// status
				CONS.Admin.status_C = 
						Methods.get_Status_from_NodeAndPosition(
								node_Name, 
								orien);
//				CONS.Admin.Orien.VERTI_VERTI);
				
				// current node number
				CONS.Admin.node_Current = 
						Methods.get_NodeNumber_frmo_Status(CONS.Admin.status_C);
//				CONS.Admin.node_Current += CONS.Admin.status_C % 2;
				
				break;//case VERTI_VERTI
				
			case VERTI_VERTI://--------------------------------------
				
				CONS.Views.rect_C_H_cur = CONS.Views.rect_C_H_orig;
				CONS.Views.rect_C_W_cur = CONS.Views.rect_C_W_orig;
				
				CONS.Views.rect_C_X = CONS.Views.rect_B_X - CONS.Views.rect_C_W_orig; 
				CONS.Views.rect_C_Y = CONS.Views.rect_B_Y;
				
				////////////////////////////////
				
				// meta
				
				////////////////////////////////
				// status
				CONS.Admin.status_C = 
						Methods.get_Status_from_NodeAndPosition(
								CONS.Admin.NodeNames.B_UL, 
								CONS.Admin.Orien.VERTI_VERTI);
				
				// current node number
				CONS.Admin.node_Current = 
						Methods.get_NodeNumber_frmo_Status(CONS.Admin.status_C);
//				CONS.Admin.node_Current += CONS.Admin.status_C % 2;
				
				break;//case VERTI_VERTI
				
			}//switch(orien)
			
//			////////////////////////////////
//			
//			// coordinates
//			
//			////////////////////////////////
//			// X
//			CONS.Views.rect_C_X = CONS.Views.rect_A_X;
//			
//			// Y
//			CONS.Views.rect_C_Y = CONS.Views.rect_B_Y - CONS.Views.rect_C_W_orig;
//			
//			// W
//			CONS.Views.rect_C_W_cur = CONS.Views.rect_C_H_orig;
//			
//			// H
//			CONS.Views.rect_C_H_cur = CONS.Views.rect_C_W_orig;
//			
//			////////////////////////////////
//			
//			// meta
//			
//			////////////////////////////////
//			// status
//			CONS.Admin.status_C = 2;
//			
//			// current node
//			CONS.Admin.node_Current += CONS.Admin.status_C % 2;
//			
//			// orientation
//			CONS.Admin.orien_Current = CONS.Admin.Orien.HORIZONTAL;
			
			break;//case B_UL:
			
		}//switch(CONS.Admin.status)
		
		////////////////////////////////
		
		// draw
		
		////////////////////////////////
		this.clear_Canvas();
		
		this.draw_Rect__A();
		this.draw_Rect__B();
		this.draw_Rect__C();
		
		////////////////////////////////
		
		// draw: periphery
		
		////////////////////////////////
		this.draw_Periphery();
		
		////////////////////////////////
		
		// update: status label
		
		////////////////////////////////
		update_Status_Label();
		
	}//_move_Rect_B_right(int status)
	
	private void 
	_move_Rect_B__Case_16() {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// update: params

		////////////////////////////////
		// X
		CONS.Views.rect_B_X = CONS.Views.rect_A_X - CONS.Views.rect_B_W_orig;
		
		// Y
		CONS.Views.rect_B_Y = CONS.Views.rect_A_Y;
		
		// W
		CONS.Views.rect_B_W_cur = CONS.Views.rect_B_W_orig;
		
		// H
		CONS.Views.rect_B_H_cur = CONS.Views.rect_B_H_orig;
		
		////////////////////////////////

		// draw

		////////////////////////////////
		this.clear_Canvas();
		
		this.draw_Rect__A();
		this.draw_Rect__B();

		////////////////////////////////
		
		// update: status value
		
		////////////////////////////////
		CONS.Admin.status = 16;
//		CONS.Admin.status += 1;
		
		////////////////////////////////

		// update: status label

		////////////////////////////////
		update_Status_Label();
		
		
	}//_move_Rect_B__Case_9

	
	private void 
	_move_Rect_B__Case_15() {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// update: params
		
		////////////////////////////////
		// X
		CONS.Views.rect_B_X = CONS.Views.rect_A_X - CONS.Views.rect_B_H_orig;
		
		// Y
		CONS.Views.rect_B_Y = CONS.Views.rect_A_Y;
		
		// W
		CONS.Views.rect_B_W_cur = CONS.Views.rect_B_H_orig;
		
		// H
		CONS.Views.rect_B_H_cur = CONS.Views.rect_B_W_orig;
		
		////////////////////////////////
		
		// draw
		
		////////////////////////////////
		this.clear_Canvas();
		
		this.draw_Rect__A();
		this.draw_Rect__B();
		
		////////////////////////////////
		
		// update: status value
		
		////////////////////////////////
		CONS.Admin.status = 15;
//		CONS.Admin.status += 1;
		
		////////////////////////////////
		
		// update: status label
		
		////////////////////////////////
		update_Status_Label();
		
		
	}//_move_Rect_B__Case_9
	
	
	private void 
	_move_Rect_B__Case_14() {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// update: params
		
		////////////////////////////////
		// X
		CONS.Views.rect_B_X = CONS.Views.rect_A_X - CONS.Views.rect_B_W_orig;
		
		// Y
		CONS.Views.rect_B_Y = CONS.Views.rect_A_Y + CONS.Views.rect_A_H - CONS.Views.rect_B_H_orig;
		
		// W
		CONS.Views.rect_B_W_cur = CONS.Views.rect_B_W_orig;
		
		// H
		CONS.Views.rect_B_H_cur = CONS.Views.rect_B_H_orig;
		
		////////////////////////////////
		
		// draw
		
		////////////////////////////////
		this.clear_Canvas();
		
		this.draw_Rect__A();
		this.draw_Rect__B();
		
		////////////////////////////////
		
		// update: status value
		
		////////////////////////////////
		CONS.Admin.status = 14;
//		CONS.Admin.status += 1;
		
		////////////////////////////////
		
		// update: status label
		
		////////////////////////////////
		update_Status_Label();
		
		
	}//_move_Rect_B__Case_9
	
	
	private void 
	_move_Rect_B__Case_13() {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// update: params
		
		////////////////////////////////
		// X
		CONS.Views.rect_B_X = CONS.Views.rect_A_X - CONS.Views.rect_B_H_orig;
		
		// Y
		CONS.Views.rect_B_Y = CONS.Views.rect_A_Y + CONS.Views.rect_A_H - CONS.Views.rect_B_W_orig;
		
		// W
		CONS.Views.rect_B_W_cur = CONS.Views.rect_B_H_orig;
		
		// H
		CONS.Views.rect_B_H_cur = CONS.Views.rect_B_W_orig;
		
		////////////////////////////////
		
		// draw
		
		////////////////////////////////
		this.clear_Canvas();
		
		this.draw_Rect__A();
		this.draw_Rect__B();
		
		////////////////////////////////
		
		// update: status value
		
		////////////////////////////////
		CONS.Admin.status = 13;
//		CONS.Admin.status += 1;
		
		////////////////////////////////
		
		// update: status label
		
		////////////////////////////////
		update_Status_Label();
		
		
	}//_move_Rect_B__Case_9
	
	
	private void 
	_move_Rect_B__Case_12() {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// update: params
		
		////////////////////////////////
		// X
		CONS.Views.rect_B_X = CONS.Views.rect_A_X;
		
		// Y
		CONS.Views.rect_B_Y = CONS.Views.rect_A_Y + CONS.Views.rect_A_H;
		
		// W
		CONS.Views.rect_B_W_cur = CONS.Views.rect_B_H_orig;
		
		// H
		CONS.Views.rect_B_H_cur = CONS.Views.rect_B_W_orig;
		
		////////////////////////////////
		
		// draw
		
		////////////////////////////////
		this.clear_Canvas();
		
		this.draw_Rect__A();
		this.draw_Rect__B();
		
		////////////////////////////////
		
		// update: status value
		
		////////////////////////////////
		CONS.Admin.status = 12;
//		CONS.Admin.status += 1;
		
		////////////////////////////////
		
		// update: status label
		
		////////////////////////////////
		update_Status_Label();
		
		
	}//_move_Rect_B__Case_9
	
	
	private void 
	_move_Rect_B__Case_11() {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// update: params
		
		////////////////////////////////
		// X
		CONS.Views.rect_B_X = CONS.Views.rect_A_X;
		
		// Y
		CONS.Views.rect_B_Y = CONS.Views.rect_A_Y + CONS.Views.rect_A_H;
		
		// W
		CONS.Views.rect_B_W_cur = CONS.Views.rect_B_W_orig;
		
		// H
		CONS.Views.rect_B_H_cur = CONS.Views.rect_B_H_orig;
		
		////////////////////////////////
		
		// draw
		
		////////////////////////////////
		this.clear_Canvas();
		
		this.draw_Rect__A();
		this.draw_Rect__B();
		
		////////////////////////////////
		
		// update: status value
		
		////////////////////////////////
		CONS.Admin.status = 11;
//		CONS.Admin.status += 1;
		
		////////////////////////////////
		
		// update: status label
		
		////////////////////////////////
		update_Status_Label();
		
		
	}//_move_Rect_B__Case_9
	
	
	private void 
	_move_Rect_B__Case_10() {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// update: params
		
		////////////////////////////////
		// X
		CONS.Views.rect_B_X = CONS.Views.rect_A_X + CONS.Views.rect_A_W - CONS.Views.rect_B_H_orig;
		
		// Y
		CONS.Views.rect_B_Y = CONS.Views.rect_A_Y + CONS.Views.rect_A_H;
		
		// W
		CONS.Views.rect_B_W_cur = CONS.Views.rect_B_H_orig;
		
		// H
		CONS.Views.rect_B_H_cur = CONS.Views.rect_B_W_orig;
		
		////////////////////////////////
		
		// draw
		
		////////////////////////////////
		this.clear_Canvas();
		
		this.draw_Rect__A();
		this.draw_Rect__B();
		
		////////////////////////////////
		
		// update: status value
		
		////////////////////////////////
		CONS.Admin.status = 10;
//		CONS.Admin.status += 1;
		
		////////////////////////////////
		
		// update: status label
		
		////////////////////////////////
		update_Status_Label();
		
		
	}//_move_Rect_B__Case_9
	
	
	public void 
	_move_Rect_B__Case_9() {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// update: params
		
		////////////////////////////////
		// X
		CONS.Views.rect_B_X = CONS.Views.rect_A_X + CONS.Views.rect_A_W - CONS.Views.rect_B_W_orig;
		
		// Y
		CONS.Views.rect_B_Y = CONS.Views.rect_A_Y + CONS.Views.rect_A_H;
		
		// W
		CONS.Views.rect_B_W_cur = CONS.Views.rect_B_W_orig;
		
		// H
		CONS.Views.rect_B_H_cur = CONS.Views.rect_B_H_orig;
		
		////////////////////////////////
		
		// draw
		
		////////////////////////////////
		this.clear_Canvas();
		
		this.draw_Rect__A();
		this.draw_Rect__B();
		
		////////////////////////////////
		
		// update: status value
		
		////////////////////////////////
		CONS.Admin.status = 9;
//		CONS.Admin.status += 1;
		
		////////////////////////////////
		
		// update: status label
		
		////////////////////////////////
		update_Status_Label();
		
		
	}//_move_Rect_B__Case_9
	
	
	private void 
	_move_Rect_B__Case_8() {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// update: params
		
		////////////////////////////////
		// X
		CONS.Views.rect_B_X = CONS.Views.rect_A_X + CONS.Views.rect_A_W;
		
		// Y
		CONS.Views.rect_B_Y = CONS.Views.rect_A_Y + CONS.Views.rect_A_H - CONS.Views.rect_B_H_orig;
		
		// W
		CONS.Views.rect_B_W_cur = CONS.Views.rect_B_W_orig;
		
		// H
		CONS.Views.rect_B_H_cur = CONS.Views.rect_B_H_orig;
		
		////////////////////////////////
		
		// draw
		
		////////////////////////////////
		this.clear_Canvas();
		
		this.draw_Rect__A();
		this.draw_Rect__B();
		
		////////////////////////////////
		
		// update: status value
		
		////////////////////////////////
		CONS.Admin.status = 8;
//		CONS.Admin.status += 1;
		
		////////////////////////////////
		
		// update: status label
		
		////////////////////////////////
		update_Status_Label();
		
		
	}//_move_Rect_B__Case_8
	
	
	private void 
	_move_Rect_B__Case_7() {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// update: params
		
		////////////////////////////////
		// X
		CONS.Views.rect_B_X = CONS.Views.rect_A_X + CONS.Views.rect_A_W;
		
		// Y
		CONS.Views.rect_B_Y = CONS.Views.rect_A_Y + CONS.Views.rect_A_H - CONS.Views.rect_B_W_orig;
		
		// W
		CONS.Views.rect_B_W_cur = CONS.Views.rect_B_H_orig;
		
		// H
		CONS.Views.rect_B_H_cur = CONS.Views.rect_B_W_orig;
		
		////////////////////////////////
		
		// draw
		
		////////////////////////////////
		this.clear_Canvas();
		
		this.draw_Rect__A();
		this.draw_Rect__B();
		
		////////////////////////////////
		
		// update: status value
		
		////////////////////////////////
		CONS.Admin.status = 7;
//		CONS.Admin.status += 1;
		
		////////////////////////////////
		
		// update: status label
		
		////////////////////////////////
		update_Status_Label();
		
		
	}//_move_Rect_B__Case_7
	
	
	private void 
	_move_Rect_B__Case_6() {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// update: params
		
		////////////////////////////////
		// X
		CONS.Views.rect_B_X = CONS.Views.rect_A_X + CONS.Views.rect_A_W;
		
		// Y
		CONS.Views.rect_B_Y = CONS.Views.rect_A_Y;
		
		// W
		CONS.Views.rect_B_W_cur = CONS.Views.rect_B_W_orig;
		
		// H
		CONS.Views.rect_B_H_cur = CONS.Views.rect_B_H_orig;
		
		////////////////////////////////
		
		// draw
		
		////////////////////////////////
		this.clear_Canvas();
		
		this.draw_Rect__A();
		this.draw_Rect__B();
		
		////////////////////////////////
		
		// update: status value
		
		////////////////////////////////
		CONS.Admin.status = 6;
//		CONS.Admin.status += 1;
		
		////////////////////////////////
		
		// update: status label
		
		////////////////////////////////
		update_Status_Label();
		
		
	}//_move_Rect_B__Case_6
	
	
	private void 
	_move_Rect_B__Case_5() {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// update: params
		
		////////////////////////////////
		// X
		CONS.Views.rect_B_X = CONS.Views.rect_A_X + CONS.Views.rect_A_W;
		
		// Y
		CONS.Views.rect_B_Y = CONS.Views.rect_A_Y;
		
		// W
		CONS.Views.rect_B_W_cur = CONS.Views.rect_B_H_orig;
		
		// H
		CONS.Views.rect_B_H_cur = CONS.Views.rect_B_W_orig;
		
		////////////////////////////////
		
		// draw
		
		////////////////////////////////
		this.clear_Canvas();
		
		this.draw_Rect__A();
		this.draw_Rect__B();
		
		////////////////////////////////
		
		// update: status value
		
		////////////////////////////////
		CONS.Admin.status = 5;
//		CONS.Admin.status += 1;
		
		////////////////////////////////
		
		// update: status label
		
		////////////////////////////////
		update_Status_Label();
		
		
	}//_move_Rect_B__Case_5
	
	
	private void 
	_move_Rect_B__Case_4() {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// update: params
		
		////////////////////////////////
		// X
		CONS.Views.rect_B_X = CONS.Views.rect_A_X + CONS.Views.rect_A_W - CONS.Views.rect_B_H_orig;
		
		// Y
		CONS.Views.rect_B_Y = CONS.Views.rect_A_Y - CONS.Views.rect_B_W_orig;
		
		// W
		CONS.Views.rect_B_W_cur = CONS.Views.rect_B_H_orig;
		
		// H
		CONS.Views.rect_B_H_cur = CONS.Views.rect_B_W_orig;
		
		////////////////////////////////
		
		// draw
		
		////////////////////////////////
		this.clear_Canvas();
		
		this.draw_Rect__A();
		this.draw_Rect__B();
		
		////////////////////////////////
		
		// update: status value
		
		////////////////////////////////
		CONS.Admin.status = 4;
//		CONS.Admin.status += 1;
		
		////////////////////////////////
		
		// update: status label
		
		////////////////////////////////
		update_Status_Label();
		
		
	}//_move_Rect_B__Case_4
	
	
	private void 
	_move_Rect_B__Case_3() {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// update: params
		
		////////////////////////////////
		// X
		CONS.Views.rect_B_X = CONS.Views.rect_A_X + CONS.Views.rect_A_W - CONS.Views.rect_B_W_orig;
		
		// Y
		CONS.Views.rect_B_Y = CONS.Views.rect_A_Y - CONS.Views.rect_B_H_orig;
		
		// W
		CONS.Views.rect_B_W_cur = CONS.Views.rect_B_W_orig;
		
		// H
		CONS.Views.rect_B_H_cur = CONS.Views.rect_B_H_orig;
		
		////////////////////////////////
		
		// draw
		
		////////////////////////////////
		this.clear_Canvas();
		
		this.draw_Rect__A();
		this.draw_Rect__B();
		
		////////////////////////////////
		
		// update: status value
		
		////////////////////////////////
		CONS.Admin.status = 3;
//		CONS.Admin.status += 1;
		
		////////////////////////////////
		
		// update: status label
		
		////////////////////////////////
		update_Status_Label();
		
		
	}//_move_Rect_B__Case_3
	
	void
	_move_Rect_B__Case_2() {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// update: params
		
		////////////////////////////////
		// X
		CONS.Views.rect_B_X = CONS.Views.rect_A_X;
		
		// Y
		CONS.Views.rect_B_Y = CONS.Views.rect_A_Y - CONS.Views.rect_B_W_orig;
		
		// W
		CONS.Views.rect_B_W_cur = CONS.Views.rect_B_H_orig;
		
		// H
		CONS.Views.rect_B_H_cur = CONS.Views.rect_B_W_orig;
		
		////////////////////////////////
		
		// draw
		
		////////////////////////////////
		this.clear_Canvas();
		
		this.draw_Rect__A();
		this.draw_Rect__B();
		
		////////////////////////////////
		
		// update: status value
		
		////////////////////////////////
		CONS.Admin.status = 2;
//		CONS.Admin.status += 1;
		
		////////////////////////////////
		
		// update: status label
		
		////////////////////////////////
		update_Status_Label();

//		////////////////////////////////
//
//		// thread	=> from: ThreadExample.java
//
//		////////////////////////////////
//		Thread thread1 =  new Thread(new XThread(this),"thread1");		
		
	}//_move_Rect_B__Case_1
	
	private void 
	update_Status_Label() {
		// TODO Auto-generated method stub
		
		String text = String.format(
						Locale.JAPAN, 
						"%s%d\nCurrent node = %d\nOrien = %s", 
						CONS.Admin.str_Status, 
						CONS.Admin.status_C, 
						CONS.Admin.node_Current, 
						CONS.Admin.orien_Current.toString());
		
		this.lbl_Status.setText(text);
//		this.lbl_Status.setText(CONS.Admin.str_Status + CONS.Admin.status_C);
//		this.lbl_Status.setText(CONS.Admin.str_Status + CONS.Admin.status);
		
	}

	private void 
	_init_Views__Labels
	(Shell shell, Group group) {

		////////////////////////////////

		// status

		////////////////////////////////
		lbl_Status = new Label(shell, SWT.NONE);
		fd_gr_ops.bottom = new FormAttachment(100, -545);
		lbl_Status.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		FormData fd_lbl_Status = new FormData();
		fd_lbl_Status.top = new FormAttachment(gr_ops, 59);
		fd_lbl_Status.left = new FormAttachment(cv_1, 81);
		fd_lbl_Status.right = new FormAttachment(100, -45);
		lbl_Status.setLayoutData(fd_lbl_Status);
		lbl_Status.setText("Status = ");

		////////////////////////////////

		// in

		////////////////////////////////
		lbl_In = new Label(shell, SWT.NONE);
		fd_lbl_Status.bottom = new FormAttachment(lbl_In, -21);
		lbl_In.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		FormData fd_lbl_In = new FormData();
		fd_lbl_In.top = new FormAttachment(gr_ops, 166);
		fd_lbl_In.right = new FormAttachment(gr_navigate, 0, SWT.RIGHT);
		fd_lbl_In.left = new FormAttachment(0, 1022);
		lbl_In.setLayoutData(fd_lbl_In);
		lbl_In.setText("In");

		////////////////////////////////

		// canvas size

		////////////////////////////////
		lbl_AreaData = new Label(shell, SWT.NONE);
		fd_lbl_In.bottom = new FormAttachment(lbl_AreaData, -30);
		
		lbl_AreaData.setBackground(this.burlywood2);
		FormData fd_lbl_AreaData = new FormData();
		fd_lbl_AreaData.top = new FormAttachment(0, 539);
		fd_lbl_AreaData.bottom = new FormAttachment(100, -150);
		fd_lbl_AreaData.left = new FormAttachment(gr_navigate, 0, SWT.LEFT);
		fd_lbl_AreaData.right = new FormAttachment(100, -72);
		lbl_AreaData.setLayoutData(fd_lbl_AreaData);
		//		lbl_1.setText("Thanks");
				
				lbl_AreaData.setText("x = " + shell.getSize().x + "\n" + "y = " + shell.getSize().y);
				

	}//_init_Views__Labels
	
	private void 
	_init_Views__Canvas
	(Shell shell, Group group) {
//		shell.setLayout(new FormLayout());

		////////////////////////////////

		// canvas

		////////////////////////////////
		cv_1 = new Canvas(shell, SWT.NONE);
		fd_gr_navigate.left = new FormAttachment(cv_1, 116);
		FormData fd_cv_1 = new FormData();
		
		fd_cv_1.bottom = new FormAttachment(0, 740);
		fd_cv_1.right = new FormAttachment(0, 880);
//		fd_cv_1.right = new FormAttachment(0, 760);
		fd_cv_1.top = new FormAttachment(0, CONS.Views.cv_Padding_Top);
//		fd_cv_1.top = new FormAttachment(0, 31);
		fd_cv_1.left = new FormAttachment(0, CONS.Views.cv_Padding_Left);
//		fd_cv_1.left = new FormAttachment(0, 49);
		
		cv_1.setLayoutData(fd_cv_1);
		
		Color col = display.getSystemColor(SWT.COLOR_BLUE);
		
		//REF http://help.eclipse.org/indigo/index.jsp?topic=%2Forg.eclipse.platform.doc.isv%2Freference%2Fapi%2Forg%2Feclipse%2Fswt%2Fgraphics%2FColor.html
		Color col2 = new Color(display, 210, 210, 210);
		
//		cv_1.setBackground(display.getSystemColor(new Color(200, 0, 0).get));;
		cv_1.setBackground(col2);;
//		cv_1.setBackground(display.getSystemColor(SWT.COLOR_BLUE));;

	}//_init_Views

	private void 
	_init_Views__Menues
	(Shell shell) {
		shell.setLayout(new FormLayout());
		
		////////////////////////////////
		
		// menu
		
		////////////////////////////////
		//REF http://zetcode.com/gui/javaswt/menustoolbars/
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("&File");
		
		Menu menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);
		
		MenuItem mntmOptions = new MenuItem(menu_1, SWT.NONE);
		mntmOptions.setText("Options");
		
		MenuItem mntmMiscs = new MenuItem(menu_1, SWT.CASCADE);
		mntmMiscs.setText("Miscs");
		
		Menu menu_2 = new Menu(mntmMiscs);
		mntmMiscs.setMenu(menu_2);
		
		MenuItem mntmOpenFile = new MenuItem(menu_2, SWT.NONE);
		mntmOpenFile.setText("Open file");
		
		MenuItem mntmQuit = new MenuItem(menu_1, SWT.NONE);
		mntmQuit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				System.exit(0);
				
			}
		});
		
		mntmQuit.setText("&Quit");
		
		MenuItem mntmOptions_1 = new MenuItem(menu, SWT.CASCADE);
		mntmOptions_1.setText("Options");
		
		Menu menu_3 = new Menu(mntmOptions_1);
		mntmOptions_1.setMenu(menu_3);
		
		MenuItem mntmCalcTheSmallest = new MenuItem(menu_3, SWT.NONE);
		mntmCalcTheSmallest.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				Rect_D6.this.calc_Smallest_Status();
				
			}
		});
		mntmCalcTheSmallest.setText("Calc the smallest status");
		
		MenuItem mntmOptions_2 = new MenuItem(menu, SWT.CASCADE);
		mntmOptions_2.setText("Options");
		
		Menu menu_4 = new Menu(mntmOptions_2);
		mntmOptions_2.setMenu(menu_4);
		
		MenuItem mntmSettings = new MenuItem(menu_4, SWT.NONE);
		mntmSettings.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
		        frame = new JFrame("DialogDemo");
//		        JFrame frame = new JFrame("DialogDemo");
//		        JFrame frame = new JFrame("DialogDemo");
//		        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
//		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        
		        DialogDemo newContentPane = new DialogDemo(frame, Rect_D6.this.display);
//		        DialogDemo newContentPane = new DialogDemo(frame);
		        
		        newContentPane.setOpaque(true); //content panes must be opaque
		        frame.setContentPane(newContentPane);

		        //Display the window.
		        frame.pack();
		        frame.setVisible(true);


			}
		});
		mntmSettings.setText("Settings");
		
	}//_init_Views__Menues

	protected void 
	calc_Smallest_Status() {
		// TODO Auto-generated method stub
		
		this._move_Rect_B__Case_10();
		
	}

	public void clear_Canvas() {
		
		this.f_Executing = false;
//		f_Executing = false;
		
//		WB.this.lbl_1.setText("Cleared");
		
		// clear canvas
		GC gc = new GC(cv_1);
//      if(lp != null) {
//          gc.setXORMode(true);
		
//		gc.setForeground(display.getSystemColor(SWT.COLOR_BLUE)); 
		
		//REF http://www.java2s.com/Tutorial/Java/0300__SWT-2D-Graphics/DrawingPointsLinesandsetlinewidth.htm
//		gc.setLineWidth(10);
//		
//		gc.drawLine(0,0, 100, 100);

//          lp = new Point(e.x, e.y);
//          gc.drawLine(fp.x, fp.y, lp.x, lp.y);
//      }else {
//          lp = new Point(e.x, e.y);
//      }

		this.cv_1.drawBackground(gc, 0, 0, Rect_D6.this.cv_1.getSize().x, Rect_D6.this.cv_1.getSize().y);

		gc.dispose();
		

	}

	class XThread extends Thread{
	
		private Rect_D6 rect;
	
		XThread(){
			
		}
		
		XThread(String threadName){
			super(threadName);                       // Initialize thread.
			System.out.println(this);
		    start();
		}
		
		public XThread(Rect_D6 rect) {
			// TODO Auto-generated constructor stub
			
			this.rect = rect;
			
		}
	
		public void run(){
			//Display info about this particular thread
//			System.out.println(Thread.currentThread().getName());
			
			rect._move_Rect_B__Case_9();
			
		}
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
//		super(threadName);                       // Initialize thread.
//		System.out.println(this);
//	    start();
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
