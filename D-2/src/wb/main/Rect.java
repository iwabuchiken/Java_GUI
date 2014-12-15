package wb.main;

//import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.PaintEvent;

import org.eclipse.swt.widgets.Display;
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
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Group;
//import org.eclipse.wb.swt.SWTResourceManager;

import wb.utils.CONS;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.wb.swt.SWTResourceManager;

public class Rect {

	protected Shell shell;
	
	//abc
	
	Display display;
	
	Label lbl_1;

	Dimension dim;

	Button bt_Execute, bt_Quit, bt_Clear, bt_Back, bt_Forward;

	Label lbl_Status, lbl_In;
	
	//test
	
	Canvas cv_1;
	
	int count = 0;

	boolean f_Executing = false;
	private Group gr_navigate, gr_ops;
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
			Rect window = new Rect();
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
		display = Display.getDefault();
//		Display display = Display.getDefault();
		
		//REF http://stackoverflow.com/questions/4322253/screen-display-size answered Dec 1 '10 at 7:57
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void 
	createContents() {
		shell = new Shell();
		
		int shell_W = (int) ((int)(float)dim.getWidth() * 0.7);
		int shell_H = (int) ((int)(float)dim.getHeight() * 0.7);
		
		shell.setSize(CONS.Views.win_W, CONS.Views.win_H);
//		shell.setSize(1200, 1000);
		shell.setText(CONS.Admin.str_MainWindow_Title);
		
		int shell_Loc_W = (int) (dim.getWidth() / 2 - shell.getSize().x / 2);
		int shell_Loc_H = (int) (dim.getHeight() / 2 - shell.getSize().y / 2);
		
		shell.setLocation(shell_Loc_W, shell_Loc_H);
		
		////////////////////////////////
		
		// group
		
		////////////////////////////////
		_init_Views__Groups(shell);
		
		_init_Views__Buttons(shell);
		
		_init_Views(shell, gr_ops);

		this._init_Views__Labels(shell, gr_ops);
		
		_init_Set_Listeners(shell);
		
	}//createContents

	private void 
	_init_Views__Groups
	(Shell shell) {
		// TODO Auto-generated method stub
	
		gr_navigate = new Group(shell, SWT.NONE);
		FormData fd_gr_navigate = new FormData();
		fd_gr_navigate.bottom = new FormAttachment(0, 184);
		fd_gr_navigate.right = new FormAttachment(0, 1002);
		fd_gr_navigate.top = new FormAttachment(0, 31);
		fd_gr_navigate.left = new FormAttachment(0, 867);
		gr_navigate.setLayoutData(fd_gr_navigate);
		
		gr_ops = new Group(shell, SWT.NONE);
		FormData fd_gr_ops = new FormData();
		fd_gr_ops.top = new FormAttachment(gr_navigate, 19);
		fd_gr_ops.right = new FormAttachment(100, -39);
		fd_gr_ops.bottom = new FormAttachment(0, 297);
		fd_gr_ops.left = new FormAttachment(0, 824);
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
				
				System.exit(0);
			}
		});

		////////////////////////////////

		// execute

		////////////////////////////////
		bt_Execute.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				//REF http://stackoverflow.com/questions/23876389/java-draw-line-with-swt-not-deleting-previous-lines asked May 26 at 19:12
				GC gc = new GC(cv_1);
//                if(lp != null) {
//                    gc.setXORMode(true);
				
				gc.setForeground(display.getSystemColor(SWT.COLOR_BLUE)); 
				
				//REF http://www.java2s.com/Tutorial/Java/0300__SWT-2D-Graphics/DrawingPointsLinesandsetlinewidth.htm
				gc.setLineWidth(10);
				
                    gc.drawLine(0,0, 100, 100);

//                    lp = new Point(e.x, e.y);
//                    gc.drawLine(fp.x, fp.y, lp.x, lp.y);
//                }else {
//                    lp = new Point(e.x, e.y);
//                }

                gc.dispose();
                
                // label
                Rect.this.f_Executing = true;
                
//				while (f_Executing) {

					Rect.this.lbl_1.setText("executing... " + count);
					
					
//        					lbl_1.setText("clicked => " + count);
					
					count ++;
					
//				}

			}
		});

		////////////////////////////////

		// message

		////////////////////////////////
		bt_Clear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				Rect.this.f_Executing = false;
//				f_Executing = false;
				
//				WB.this.lbl_1.setText("Cleared");
				
				// clear canvas
				GC gc = new GC(cv_1);
//              if(lp != null) {
//                  gc.setXORMode(true);
				
//				gc.setForeground(display.getSystemColor(SWT.COLOR_BLUE)); 
				
				//REF http://www.java2s.com/Tutorial/Java/0300__SWT-2D-Graphics/DrawingPointsLinesandsetlinewidth.htm
//				gc.setLineWidth(10);
//				
//				gc.drawLine(0,0, 100, 100);

//                  lp = new Point(e.x, e.y);
//                  gc.drawLine(fp.x, fp.y, lp.x, lp.y);
//              }else {
//                  lp = new Point(e.x, e.y);
//              }

				Rect.this.cv_1.drawBackground(gc, 0, 0, Rect.this.cv_1.getSize().x, Rect.this.cv_1.getSize().y);

				gc.dispose();
				
				
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
		bt_Back.setText("<-");
		
		bt_Forward = new Button(this.gr_ops, SWT.NONE);
		bt_Forward.setText("->");
		
	}//_init_Views__Buttons
	
	private void 
	_init_Views__Labels
	(Shell shell, Group group) {

		////////////////////////////////

		// status

		////////////////////////////////
		lbl_Status = new Label(shell, SWT.NONE);
		lbl_Status.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		FormData fd_lbl_Status = new FormData();
		fd_lbl_Status.top = new FormAttachment(gr_ops, 136);
		fd_lbl_Status.right = new FormAttachment(cv_1, 168, SWT.RIGHT);
		fd_lbl_Status.left = new FormAttachment(cv_1, 44);
		lbl_Status.setLayoutData(fd_lbl_Status);
		lbl_Status.setText("Status = ");

		////////////////////////////////

		// in

		////////////////////////////////
		lbl_In = new Label(shell, SWT.NONE);
		lbl_In.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		FormData fd_lbl_In = new FormData();
		fd_lbl_In.left = new FormAttachment(cv_1, 218);
		fd_lbl_In.right = new FormAttachment(100, -10);
		lbl_In.setLayoutData(fd_lbl_In);
		lbl_In.setText("In");

		////////////////////////////////

		// canvas size

		////////////////////////////////
		lbl_1 = new Label(shell, SWT.NONE);
		fd_lbl_In.bottom = new FormAttachment(lbl_1, -112);
		fd_lbl_Status.bottom = new FormAttachment(lbl_1, -40);
		FormData fd_lbl_1 = new FormData();
		fd_lbl_1.bottom = new FormAttachment(cv_1, 0, SWT.BOTTOM);
		fd_lbl_1.right = new FormAttachment(100, -63);
		fd_lbl_1.top = new FormAttachment(0, 585);
		fd_lbl_1.left = new FormAttachment(0, 854);
		lbl_1.setLayoutData(fd_lbl_1);
		//		lbl_1.setText("Thanks");
				
				lbl_1.setText("x = " + shell.getSize().x + "\n" + "y = " + shell.getSize().y);
				

	}//_init_Views__Labels
	
	private void 
	_init_Views
	(Shell shell, Group group) {
		shell.setLayout(new FormLayout());

//		Button bt_Back = new Button(group, SWT.NONE);
//		bt_Back.setText("<-");
//		
//		Button bt_Forward = new Button(group, SWT.NONE);
//		bt_Forward.setText("->");
		

		
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

		////////////////////////////////

		// canvas

		////////////////////////////////
		cv_1 = new Canvas(shell, SWT.NONE);
		FormData fd_cv_1 = new FormData();
		fd_cv_1.bottom = new FormAttachment(0, 651);
		fd_cv_1.right = new FormAttachment(0, 760);
		fd_cv_1.top = new FormAttachment(0, 31);
		fd_cv_1.left = new FormAttachment(0, 49);
		cv_1.setLayoutData(fd_cv_1);
		
		Color col = display.getSystemColor(SWT.COLOR_BLUE);
		
		//REF http://help.eclipse.org/indigo/index.jsp?topic=%2Forg.eclipse.platform.doc.isv%2Freference%2Fapi%2Forg%2Feclipse%2Fswt%2Fgraphics%2FColor.html
		Color col2 = new Color(display, 200, 200, 200);
		
//		cv_1.setBackground(display.getSystemColor(new Color(200, 0, 0).get));;
		cv_1.setBackground(col2);;
//		cv_1.setBackground(display.getSystemColor(SWT.COLOR_BLUE));;


	}//_init_Views
}
