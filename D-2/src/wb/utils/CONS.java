package wb.utils;

import java.util.List;

public class CONS {

	public static class Admin {
	
		public static final String str_MainWindow_Title = "Rectangles -- 3 objects";
//		public static final String str_MainWindow_Title = "Rectangles";
		
		public static final String dPath_Log = "log";
		
		public final static String fname_Log = "log.txt";
		
		public final static String fname_Log_Trunk = "log";
		
		public final static String fname_Log_ext = ".txt";

		public static final long logFile_MaxSize = 40000;

		public static final String format_Date = "yyyy/MM/dd HH:mm:ss.SSS";

		// status
		public static int status;
		public static int status_C;
		public static int status_B;
		
		public final static String str_Status = "Status = ";
		
		public static enum Orien {
			
			HH, HV, VH, VV,
//			HORI_HORI, HORI_VERTI,
//			VERTI_VERTI, VERTI_HORI,
			
			INITIAL, NEXT_NODE, PREV_NODE,
			
			HH_, HV_,
			VV_, VH_,
			
			INIT, NN, PN,	// NN => next node; PN => prev node
			
		}
		
		public static enum NodeNames {
			
			B_UL, B_UR, B_LL, B_LR,
			A_UL, A_UR, A_LL, A_LR,
			C_UL, C_UR, C_LL, C_LR,
		}
		
		/*******************************

			UPPER => the line IS occupied at the upper part with the rectangles

		 *******************************/
		public static enum LineStates {
			
			MIDDLE_X, MIDDLE_Y,
			BOTH_Y, BOTH_X,
			UPPER, LOWER,
			LEFT, RIGHT,
			NONE, MATCH, UNKNOWN,
			
		}
		
		public static enum Lines {
			
			LX1, LX2, LY1, LY2,
			
		}
		
		public static enum CornerTypes {
			
			IN_CORNER, OUT_CORNER,
			IN_CORNER_A, IN_CORNER_B, IN_CORNER_C, IN_CORNER_X,
			
		}
		
		public static Orien orien_Current_C;
		public static Orien orien_Current_B;
		
		public static int node_Current;
		public static int node_Current_B;
		
		public static List<NodeNames> list_NodeNames_C;
		
		public static List<NodeNames> list_NodeNames_B;
		
		public static final int numOf_Positions_per_Node = 4;
		
		
	}
	
	public static class Views {
		
		public static int win_W = 1100;
		
		public static int win_H = 800;
		
		////////////////////////////////

		// canvas

		////////////////////////////////
		public static final int cv_Padding_Left = 10;
		public static final int cv_Padding_Top = 10;
		
		////////////////////////////////

		// rect

		////////////////////////////////
		public static final int lineWidth_Rect = 5;
		
		////////////////////////////////

		// rect: A

		////////////////////////////////
		public static int rect_A_W = 200;
		
		public static int rect_A_H = rect_A_W / 2;
		
		public static int rect_A_X;
		
		public static int rect_A_Y;
		
		public static int offset_Y_A = 50;
		
		////////////////////////////////
		
		// rect: B
		
		////////////////////////////////
		public static int rect_B_W_orig = 70;
//		public static final int rect_B_W_orig = 50;
		
		public static int rect_B_H_orig = 150;
//		public static final int rect_B_H_orig = rect_B_W_orig * 2;
		
		public static int rect_B_W_cur;
		
		public static int rect_B_H_cur;
		
		public static int rect_B_X;
		
		public static int rect_B_Y;
		
		////////////////////////////////
		
		// rect: C
		
		////////////////////////////////
		public static int rect_C_W_orig = 50;
		
//		public static final int rect_C_H_orig = 120;
		public static int rect_C_H_orig = 100;
//		public static final int rect_C_H_orig = rect_C_W_orig * 2;
		
		public static int rect_C_W_cur;
		
		public static int rect_C_H_cur;
		
		public static int rect_C_X;
		
		public static int rect_C_Y;
		
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
