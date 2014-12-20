package wb.utils;

public class CONS {

	public static class Admin {
	
		public static final String str_MainWindow_Title = "Rectangles";
		
		public static final String dPath_Log = "log";
		
		public final static String fname_Log = "log.txt";
		
		public final static String fname_Log_Trunk = "log";
		
		public final static String fname_Log_ext = ".txt";

		public static final long logFile_MaxSize = 40000;

		public static final String format_Date = "yyyy/MM/dd HH:mm:ss.SSS";

		public static int status;
		
		public final static String str_Status = "Status = ";
		
	}
	
	public static class Views {
		
		public static int win_W = 1100;
		
		public static int win_H = 800;
		
		////////////////////////////////

		// rect

		////////////////////////////////
		public static final int lineWidth_Rect = 5;
		
		////////////////////////////////

		// rect: A

		////////////////////////////////
		public static final int rect_A_W = 200;
		
		public static final int rect_A_H = rect_A_W / 2;
		
		public static int rect_A_X;
		
		public static int rect_A_Y;
		
		////////////////////////////////
		
		// rect: B
		
		////////////////////////////////
		public static final int rect_B_W_orig = 50;
		
		public static final int rect_B_H_orig = 150;
//		public static final int rect_B_H_orig = rect_B_W_orig * 2;
		
		public static int rect_B_W_cur;
		
		public static int rect_B_H_cur;
		
		public static int rect_B_X;
		
		public static int rect_B_Y;
		
		////////////////////////////////

		// periphery

		////////////////////////////////
		public static int peri_X;
		public static int peri_Y;
		
		public static int peri_W;
		public static int peri_H;
		
	}//public static class Views
	
	public static class Strings {
		
		public static final String title_Confirm = "Confirm";
		
		public static final String msg_QuitApp = "Quit the app?";
		
	}
	
}
