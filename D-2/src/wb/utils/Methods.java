package wb.utils;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.JOptionPane;

import org.apache.commons.lang.math.NumberUtils;

import wb.main.Rect_D11;
import wb.utils.CONS.Admin.CornerTypes;
import wb.utils.CONS.Admin.LineStates;
import wb.utils.CONS.Admin.Lines;
import wb.utils.CONS.Admin.NodeNames;
import wb.utils.CONS.Admin.Orien;

public class Methods {

	public static void 
	write_Log
	(String message,
			String fileName, int lineNumber) {
		
		////////////////////////////////

		// validate: dir exists

		////////////////////////////////
		File dpath_Log = new File(CONS.Admin.dPath_Log);
		
		if (!dpath_Log.exists()) {
			
			dpath_Log.mkdirs();
			
		} else {
			
		}
		
		////////////////////////////////

		// file

		////////////////////////////////
		File fpath_Log = new File(CONS.Admin.dPath_Log, CONS.Admin.fname_Log);
		
		////////////////////////////////

		// file exists?

		////////////////////////////////
		if (!fpath_Log.exists()) {
			
			try {
				
				fpath_Log.createNewFile();
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
				return;
				
			}
			
		} else {
			
		}
		
		////////////////////////////////

		// validate: size

		////////////////////////////////
		long len = fpath_Log.length();
		
		if (len > CONS.Admin.logFile_MaxSize) {
		
			fpath_Log.renameTo(new File(
						fpath_Log.getParent(), 
						CONS.Admin.fname_Log_Trunk
						+ "_"
//						+ Methods.get_TimeLabel(Methods.getMillSeconds_now())
						+ Methods.get_TimeLabel(fpath_Log.lastModified())
						+ CONS.Admin.fname_Log_ext
						));
			
			// new log.txt
			try {
				
				fpath_Log = new File(fpath_Log.getParent(), CONS.Admin.fname_Log);
//				File f = new File(fpath_Log.getParent(), CONS.Admin.fname_Log);
				
				fpath_Log.createNewFile();
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
				
				return;
				
			}
			
		}//if (len > CONS.Admin.logFile_MaxSize)

		////////////////////////////////

		// write

		////////////////////////////////
		try {
			
			//REF append http://stackoverflow.com/questions/8544771/how-to-write-data-with-fileoutputstream-without-losing-old-data answered Dec 17 '11 at 12:37
			FileOutputStream fos = new FileOutputStream(fpath_Log, true);
//			FileOutputStream fos = new FileOutputStream(fpath_Log);
			
			String text = String.format(Locale.JAPAN,
							"[%s] [%s : %d] %s\n", 
							Methods.conv_MillSec_to_TimeLabel(
											Methods.getMillSeconds_now()),
							fileName, lineNumber,
							message
						);
			
			//REF getBytes() http://www.adakoda.com/android/000240.html
			fos.write(text.getBytes());
//			fos.write(message.getBytes());
			
//			fos.write("\n".getBytes());
			
			fos.close();
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}//write_Log

	/****************************************
	 *	getMillSeconds_now()
	 * 
	 * <Caller> 
	 * 1. ButtonOnClickListener # case main_bt_start
	 * 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public static long getMillSeconds_now() {
		
		Calendar cal = Calendar.getInstance();
		
		return cal.getTime().getTime();
		
	}//private long getMillSeconds_now(int year, int month, int date)

	public static String
	conv_MillSec_to_TimeLabel(long millSec)
	{
		//REF http://stackoverflow.com/questions/7953725/how-to-convert-milliseconds-to-date-format-in-android answered Oct 31 '11 at 12:59
		String dateFormat = CONS.Admin.format_Date;
//		String dateFormat = "yyyy/MM/dd hh:mm:ss.SSS";
		
		DateFormat formatter = new SimpleDateFormat(dateFormat, Locale.JAPAN);
//		DateFormat formatter = new SimpleDateFormat(dateFormat);

		// Create a calendar object that will convert the date and time value in milliseconds to date. 
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTimeInMillis(millSec);
		
		return formatter.format(calendar.getTime());
		
	}//conv_MillSec_to_TimeLabel(long millSec)

	/******************************
		@return format => "yyyyMMdd_HHmmss"
	 ******************************/
	public static String get_TimeLabel(long millSec) {
		
		 SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd_HHmmss");
		 
		return sdf1.format(new Date(millSec));
		
	}//public static String get_TimeLabel(long millSec)

	public static int larger_INT(int a, int b) {
	
		return (a >= b) ? a : b;
		
	}
	
	public static int smaller_INT(int a, int b) {
		
		return (a < b) ? a : b;
		
	}

	
	public static int 
	get_NodeNumber_frmo_Status(int status) {
		
		// validate
		if (status < 0) {
			
			return -1;
			
		}
		
		int a = status / CONS.Admin.numOf_Positions_per_Node;
		int b = status % CONS.Admin.numOf_Positions_per_Node;
//		int a = status / 2;
//		int b = status % 2;
		
		if (b > 0) {
			
			return a + 1;
			
		} else {
			
			return a;

		}
		
	}//get_NodeNumber

	public static int 
	get_Status_from_NodeAndPosition
	(NodeNames name, Orien orien) {
		// TODO Auto-generated method stub
		
//		int node_index;
		int node_index = Methods.get_Node_Index__C(name);
		
		//log
		String text = String.format(Locale.JAPAN, 
							"name = %s, orien = %s / node_index = %d\n", 
							name.toString(), orien.toString(), node_index);
		
		String fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		int line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		

		
		////////////////////////////////

		// names

		////////////////////////////////
		switch(name) {
		
		case B_UL://-----------------------------
		case A_UL://-----------------------------
			
			switch(orien) {
			
			case HV: return node_index * CONS.Admin.numOf_Positions_per_Node + 1;
			case HH: return node_index * CONS.Admin.numOf_Positions_per_Node + 2;
			case VH: return node_index * CONS.Admin.numOf_Positions_per_Node + 3;
			case VV: return node_index * CONS.Admin.numOf_Positions_per_Node + 4;
//			case HORI_VERTI: return 1;
//			case HORI_HORI: return 2;
//			case VERTI_HORI: return 3;
//			case VERTI_VERTI: return 4;
			
			}//switch(orien)
			
			break;
			
		case B_UR://-----------------------------
		case A_UR://-----------------------------
			
			switch(orien) {
			
			case INITIAL: 
			case HV: return node_index * CONS.Admin.numOf_Positions_per_Node + 1;
			case HH: return node_index * CONS.Admin.numOf_Positions_per_Node + 2;
			case VH: return node_index * CONS.Admin.numOf_Positions_per_Node + 3;
			case VV: return node_index * CONS.Admin.numOf_Positions_per_Node + 4;
			
			}//switch(orien)
			
			break;
			
		case B_LR://-----------------------------
		case A_LR://-----------------------------
			
			switch(orien) {
			
			case INITIAL: 
			case VH: return node_index * CONS.Admin.numOf_Positions_per_Node + 1;
			case VV: return node_index * CONS.Admin.numOf_Positions_per_Node + 2;
			case HV: return node_index * CONS.Admin.numOf_Positions_per_Node + 3;
			case HH: return node_index * CONS.Admin.numOf_Positions_per_Node + 4;
			
			}//switch(orien)
			
			break;

		case B_LL://-----------------------------
		case A_LL://-----------------------------
			
//			node_index = Methods.get_Node_Index__C(CONS.Admin.NodeNames.B_LL);
			
			
			if (node_index == -1) {
				
				return 1;
				
			}
			
			switch(orien) {
			
			case HV: return node_index * CONS.Admin.numOf_Positions_per_Node + 1;
			case HH: return node_index * CONS.Admin.numOf_Positions_per_Node + 2;
			case VH: return node_index * CONS.Admin.numOf_Positions_per_Node + 3;
			case VV: return node_index * CONS.Admin.numOf_Positions_per_Node + 4;
			
			}//switch(orien)
			
			break;

//		case A_UR://-----------------------------
//			
//			switch(orien) {
//			
//			case INITIAL: 
//			case HORI_VERTI: return 13;
//			case HORI_HORI: return 14;
//			case VERTI_HORI: return 15;
//			case VERTI_VERTI: return 16;
//			
//			}//switch(orien)
//			
//			break;
//			
//		case A_LR://-----------------------------
//			
//			switch(orien) {
//			
//			case INITIAL: 
//			case VERTI_HORI: return 17;
//			case VERTI_VERTI: return 18;
//			case HORI_VERTI: return 19;
//			case HORI_HORI: return 20;
//			
//			}//switch(orien)
//			
//			break;
//			
//		case A_LL://-----------------------------
//			
//			switch(orien) {
//			
//			case HORI_VERTI: return 21;
//			case HORI_HORI: return 22;
//			case VERTI_HORI: return 23;
//			case VERTI_VERTI: return 24;
//			
//			}//switch(orien)
//			
//			break;
			
		}//switch(name)
		
		return -1;
		
	}//get_Status_from_NodeAndPosition

	/*******************************

		@return
		default => -1

	 *******************************/
	private static int 
	get_Node_Index__C
	(NodeNames name) {
		// TODO Auto-generated method stub
		
		for (int i = 0; i < CONS.Admin.list_NodeNames_C.size(); i++) {
			
			if (name == CONS.Admin.list_NodeNames_C.get(i)) {
				
				return i;
				
			}
			
		}
		
		return -1;
		
	}

	public static int 
	get_Status_from_NodeAndPosition_B
	(NodeNames name, Orien orien) {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// names
		
		////////////////////////////////
		switch(name) {
		
		case A_UL://-----------------------------
			
			switch(orien) {
			
			case HV: return 1;
			case HH: return 2;
			case VH: return 3;
			case VV: return 4;
			
			}//switch(orien)
			
			break;
			
		case A_UR://-----------------------------
			
			switch(orien) {
			
			case INITIAL: 
			case HV: return 5;
			case HH: return 6;
			case VH: return 7;
			case VV: return 8;
			
			}//switch(orien)
			
			break;
			
		case A_LR://-----------------------------
			
			switch(orien) {
			
			case INITIAL: 
			case VH: return 9;
			case VV: return 10;
			case HV: return 11;
			case HH: return 12;
			
			}//switch(orien)
			
			break;

		case A_LL://-----------------------------
			
			switch(orien) {
			
			case HV: return 13;
			case HH: return 14;
			case VH: return 15;
			case VV: return 16;
			
			}//switch(orien)
			
			break;
			
//		case A_UR://-----------------------------
//			
//			switch(orien) {
//			
//			case INITIAL: 
//			case HORI_VERTI: return 13;
//			case HORI_HORI: return 14;
//			case VERTI_HORI: return 15;
//			case VERTI_VERTI: return 16;
//			
//			}//switch(orien)
//			
//			break;
//			
//		case A_LR://-----------------------------
//			
//			switch(orien) {
//			
//			case INITIAL: 
//			case VERTI_HORI: return 17;
//			case VERTI_VERTI: return 18;
//			case HORI_VERTI: return 19;
//			case HORI_HORI: return 20;
//			
//			}//switch(orien)
//			
//			break;
//			
		}//switch(name)
		
		return -1;
		
	}//get_Status_from_NodeAndPosition
	
	public static Orien 
	get_NextOrien(NodeNames name, Orien orien_Current) {
		// TODO Auto-generated method stub
		
		switch(name) {
		
		case B_UL:
			
			switch(orien_Current) {
			
			case HV: return CONS.Admin.Orien.HH;
				
			case HH: return CONS.Admin.Orien.VH;
				
			case VH: return CONS.Admin.Orien.VV;
				
			case VV: return CONS.Admin.Orien.NEXT_NODE;
				
			case INITIAL: return CONS.Admin.Orien.HV;
				
			}
			
			break;
			
		case B_UR:
			
			switch(orien_Current) {
			
			case HV: return CONS.Admin.Orien.HH;
			
			case HH: return CONS.Admin.Orien.VH;
			
			case VH: return CONS.Admin.Orien.VV;
			
			case VV: return CONS.Admin.Orien.NEXT_NODE;
			
			case INITIAL: return CONS.Admin.Orien.HV;
			
			}
			
			break;
			
		case B_LR:
			
			switch(orien_Current) {
			
			case VH: return CONS.Admin.Orien.VV;
			
			case VV: return CONS.Admin.Orien.HV;
			
			case HV: return CONS.Admin.Orien.HH;
			
			case HH: return CONS.Admin.Orien.NEXT_NODE;
			
			case INITIAL: return CONS.Admin.Orien.VH;
			
			}
			
			break;
			
		case A_UR:
			
			switch(orien_Current) {
			
			case HV: return CONS.Admin.Orien.HH;
			
			case HH: return CONS.Admin.Orien.VH;
			
			case VH: return CONS.Admin.Orien.VV;
			
			case VV: return CONS.Admin.Orien.NEXT_NODE;
			
			case INITIAL: return CONS.Admin.Orien.HV;
			
			}
			
			break;
			
		case A_LR:
			
			switch(orien_Current) {
			
			case VH: return CONS.Admin.Orien.VV;
			
			case VV: return CONS.Admin.Orien.HV;
			
			case HV: return CONS.Admin.Orien.HH;
			
			case HH: return CONS.Admin.Orien.NEXT_NODE;
			
			case INITIAL: return CONS.Admin.Orien.VH;
			
			}
			
			break;

		case A_LL://-----------------------------
			
			switch(orien_Current) {
			
			case HV: return CONS.Admin.Orien.HH;
				
			case HH: return CONS.Admin.Orien.VH;
				
			case VH: return CONS.Admin.Orien.VV;
				
			case VV: return CONS.Admin.Orien.NEXT_NODE;
				
			case INITIAL: return CONS.Admin.Orien.HV;
				
			}
			
			break;	// case A_LL
			

		}//switch(name)

		return orien_Current;
		
	}//get_NextOrien
	
	public static Orien 
	get_PrevOrien(NodeNames name, Orien orien_Current) {
		// TODO Auto-generated method stub
		
		switch(name) {
		
		case B_UL://------------------------------
			
			switch(orien_Current) {
			
			case HV: return CONS.Admin.Orien.PREV_NODE;
				
			case HH: return CONS.Admin.Orien.HV;
				
			case VH: return CONS.Admin.Orien.HH;
				
			case VV: return CONS.Admin.Orien.VH;
				
			case INITIAL: return CONS.Admin.Orien.HV;
				
			}//switch(orien_Current)
			
			break;
			
		case B_UR://------------------------------
			
			switch(orien_Current) {
			
			case HV: return CONS.Admin.Orien.PREV_NODE;
				
			case HH: return CONS.Admin.Orien.HV;
				
			case VH: return CONS.Admin.Orien.HH;
				
			case VV: return CONS.Admin.Orien.VH;
				
			case INITIAL: return CONS.Admin.Orien.HV;
				
			}//switch(orien_Current)
			
			break;
			
		case B_LR://------------------------------
			
			switch(orien_Current) {
			
			case HH: return CONS.Admin.Orien.HV;
				
			case HV: return CONS.Admin.Orien.VV;
				
			case VV: return CONS.Admin.Orien.VH;
				
			case VH: return CONS.Admin.Orien.PREV_NODE;
				
			case INITIAL: return CONS.Admin.Orien.PREV_NODE;
				
			}//switch(orien_Current)
			
			break;

		case A_UR://------------------------------
			
			switch(orien_Current) {
			
			case HV: return CONS.Admin.Orien.PREV_NODE;
				
			case HH: return CONS.Admin.Orien.HV;
				
			case VH: return CONS.Admin.Orien.HH;
				
			case VV: return CONS.Admin.Orien.VH;
				
			case INITIAL: return CONS.Admin.Orien.HV;
				
			}//switch(orien_Current)
			
			break;
			
		case A_LR://------------------------------
			
			switch(orien_Current) {
			
			case HH: return CONS.Admin.Orien.HV;
				
			case HV: return CONS.Admin.Orien.VV;
				
			case VV: return CONS.Admin.Orien.VH;
				
			case VH: return CONS.Admin.Orien.PREV_NODE;
				
			case INITIAL: return CONS.Admin.Orien.PREV_NODE;
				
			}//switch(orien_Current)
			
			break;	// case A_LR

		case A_LL://------------------------------
			
			switch(orien_Current) {
			
			case HV: return CONS.Admin.Orien.PREV_NODE;
				
			case HH: return CONS.Admin.Orien.HV;
				
			case VH: return CONS.Admin.Orien.HH;
				
			case VV: return CONS.Admin.Orien.VH;
				
			case INITIAL: return CONS.Admin.Orien.HV;
				
			}//switch(orien_Current)
			
			break;	// case A_LL
			
		}//switch(name)
		
		return orien_Current;
		
	}//get_PrevOrien(NodeNames name, Orien orien_Current)

	public static int 
	smallest(int[] list) {
		// TODO Auto-generated method stub
		////////////////////////////////

		// validate

		////////////////////////////////
		if (list == null || list.length < 1) {
			
			return -1;
			
		}
		
		////////////////////////////////

		// length: 1

		////////////////////////////////
		if (list.length == 1) {
			
			return list[0];
			
		}
		
		////////////////////////////////

		// get: largest

		////////////////////////////////
		int temp = list[0];
		
		for (int i = 1; i < list.length; i++) {
			
			if (temp > list[i]) {
				
				temp = list[i];
				
			}
			
		}
		
		return temp;
		
	}//smallest
	
	public static int 
	largest(int[] list) {
		// TODO Auto-generated method stub
		////////////////////////////////
		
		// validate
		
		////////////////////////////////
		if (list == null || list.length < 1) {
			
			return -1;
			
		}
		
		////////////////////////////////
		
		// length: 1
		
		////////////////////////////////
		if (list.length == 1) {
			
			return list[0];
			
		}
		
		////////////////////////////////
		
		// get: largest
		
		////////////////////////////////
		int temp = list[0];
		
		for (int i = 1; i < list.length; i++) {
			
			if (temp < list[i]) {
				
				temp = list[i];
				
			}
			
		}
		
		return temp;
		
	}//smallest

	/*******************************

		@return
		default => HORI_VERTI

	 *******************************/
	public static Orien 
	get_InitialOrien(NodeNames name) {
		// TODO Auto-generated method stub
		
		switch(name) {
		
		case B_UL: return CONS.Admin.Orien.HV;
		case B_UR: return CONS.Admin.Orien.HV;
		case B_LR: return CONS.Admin.Orien.VH;
		
		case A_UR: return CONS.Admin.Orien.HV;
		case A_LR: return CONS.Admin.Orien.VH;
		case A_LL: return CONS.Admin.Orien.HV;
		
		}
		
		return CONS.Admin.Orien.HV;
		
	}//get_InitialOrien(NodeNames name)

	/*******************************

		@return
		default => HORI_VERTI

	 *******************************/
	public static Orien 
	get_LastOrien(NodeNames name) {
		// TODO Auto-generated method stub
		
		switch(name) {
		
		case B_UL: return CONS.Admin.Orien.VV;
		case B_UR: return CONS.Admin.Orien.VV;
		case B_LR: return CONS.Admin.Orien.HH;
		
		case A_UR: return CONS.Admin.Orien.VV;
		case A_LR: return CONS.Admin.Orien.HH;
		case A_LL: return CONS.Admin.Orien.VV;
		
		}
		
		return CONS.Admin.Orien.HV;
		
	}//get_InitialOrien(NodeNames name)
	
	public static Object[] 
	get_NodeNameAndOrien_frmo_Status__C(int status) {
		// TODO Auto-generated method stub
		
//		CONS.Admin.NodeNames name
		int node_Number = Methods.get_NodeNumber_frmo_Status(status);
		
		/*******************************

			validate: node exists

		 *******************************/
		if (node_Number < 1 || node_Number > CONS.Admin.list_NodeNames_C.size()) {

			//log
			String text = String.format(Locale.JAPAN, 
								"no node for: status => %d / node_Numer => %d\n", 
								status, node_Number);
			
			String fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			int line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

			return new Object[]{null, null};
			
		}
		
		// node name
		NodeNames name = CONS.Admin.list_NodeNames_C.get(node_Number - 1);
		
		int index_Orien = status % 4;
		
		if (index_Orien == 0) index_Orien = 4;

		//log
		String text = String.format(Locale.JAPAN, 
							"name = %s / index_Orien = %d\n", 
							name.toString(), index_Orien);
		
		String fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		int line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		
		CONS.Admin.Orien orien = null;
		
		////////////////////////////////

		// orien

		////////////////////////////////
		switch(name) {
		
		case B_UL://----------------------------------
		case A_UL://----------------------------------
			
			switch(index_Orien) {
			
			case 1: orien = CONS.Admin.Orien.HV; break; 
			case 2: orien = CONS.Admin.Orien.HH; break; 
			case 3: orien = CONS.Admin.Orien.VH; break; 
			case 4: orien = CONS.Admin.Orien.VV; break; 
			
			}
			
			break;//case B_UL
		
		case B_UR://----------------------------------
			
			switch(index_Orien) {
			
			case 1: orien = CONS.Admin.Orien.HV; break; 
			case 2: orien = CONS.Admin.Orien.HH; break; 
			case 3: orien = CONS.Admin.Orien.VH; break; 
			case 4: orien = CONS.Admin.Orien.VV; break; 
			
			}
			
			break;//case B_UL
			
		case B_LR://----------------------------------
			
			switch(index_Orien) {
			
			case 1: orien = CONS.Admin.Orien.VH; break; 
			case 2: orien = CONS.Admin.Orien.VV; break; 
			case 3: orien = CONS.Admin.Orien.HV; break; 
			case 4: orien = CONS.Admin.Orien.HH; break; 
			
			}
			
			break;//case B_UL

		case B_LL://----------------------------------
			
			switch(index_Orien) {
			
			case 1: orien = CONS.Admin.Orien.HV; break; 
			case 2: orien = CONS.Admin.Orien.HH; break; 
			case 3: orien = CONS.Admin.Orien.VH; break; 
			case 4: orien = CONS.Admin.Orien.VV; break; 
			
			}
			
			break;//case B_LL

		case A_UR://----------------------------------
			
			switch(index_Orien) {
			
			case 1: orien = CONS.Admin.Orien.HV; break; 
			case 2: orien = CONS.Admin.Orien.HH; break; 
			case 3: orien = CONS.Admin.Orien.VH; break; 
			case 4: orien = CONS.Admin.Orien.VV; break; 
			
			}
			
			break;//case A_UR
			
		case A_LR://----------------------------------
			
			switch(index_Orien) {
			
			case 1: orien = CONS.Admin.Orien.VH; break; 
			case 2: orien = CONS.Admin.Orien.VV; break; 
			case 3: orien = CONS.Admin.Orien.HV; break; 
			case 4: orien = CONS.Admin.Orien.HH; break; 
			
			}
			
			break;//case A_UL

		case A_LL://----------------------------------
			
			switch(index_Orien) {
			
			case 1: orien = CONS.Admin.Orien.HV; break; 
			case 2: orien = CONS.Admin.Orien.HH; break; 
			case 3: orien = CONS.Admin.Orien.VH; break; 
			case 4: orien = CONS.Admin.Orien.VV; break; 
			
			}
			
			break;//case A_LL
		
		}//switch(name)
		
		return new Object[]{name, orien};
		
	}//get_NodeNameAndOrien_frmo_Status

	public static Object[] 
	get_NodeNameAndOrien_frmo_Status__B(int status) {
		// TODO Auto-generated method stub
		
//		CONS.Admin.NodeNames name
		int node_Number = Methods.get_NodeNumber_frmo_Status(status);
		
		/*******************************

			validate: node exists

		 *******************************/
		if (node_Number < 1 || node_Number > CONS.Admin.list_NodeNames_B.size()) {
			
			//log
			String text = String.format(Locale.JAPAN, 
					"no node for: status => %d / node_Numer => %d\n", 
					status, node_Number);
			
			String fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			int line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
			
			return new Object[]{null, null};
			
		}
		
		// node name
		NodeNames name = CONS.Admin.list_NodeNames_B.get(node_Number - 1);
		
		int index_Orien = status % 4;
		
		if (index_Orien == 0) index_Orien = 4;
		
		CONS.Admin.Orien orien = null;
		
		////////////////////////////////
		
		// orien
		
		////////////////////////////////
		switch(name) {
		
		case A_UL://----------------------------------
			
			switch(index_Orien) {
			
			case 1: orien = CONS.Admin.Orien.HV; break; 
			case 2: orien = CONS.Admin.Orien.HH; break; 
			case 3: orien = CONS.Admin.Orien.VH; break; 
			case 4: orien = CONS.Admin.Orien.VV; break; 
			
			}
			
			break;//case B_UL
			
//		case B_UR://----------------------------------
//			
//			switch(index_Orien) {
//			
//			case 1: orien = CONS.Admin.Orien.HORI_VERTI; break; 
//			case 2: orien = CONS.Admin.Orien.HORI_HORI; break; 
//			case 3: orien = CONS.Admin.Orien.VERTI_HORI; break; 
//			case 4: orien = CONS.Admin.Orien.VERTI_VERTI; break; 
//			
//			}
//			
//			break;//case B_UL
//			
//		case B_LR://----------------------------------
//			
//			switch(index_Orien) {
//			
//			case 1: orien = CONS.Admin.Orien.VERTI_HORI; break; 
//			case 2: orien = CONS.Admin.Orien.VERTI_VERTI; break; 
//			case 3: orien = CONS.Admin.Orien.HORI_VERTI; break; 
//			case 4: orien = CONS.Admin.Orien.HORI_HORI; break; 
//			
//			}
//			
//			break;//case B_UL
//			
		case A_UR://----------------------------------
			
			switch(index_Orien) {
			
			case 1: orien = CONS.Admin.Orien.HV; break; 
			case 2: orien = CONS.Admin.Orien.HH; break; 
			case 3: orien = CONS.Admin.Orien.VH; break; 
			case 4: orien = CONS.Admin.Orien.VV; break; 
			
			}
			
			break;//case A_UR
			
		case A_LL://----------------------------------
			
			switch(index_Orien) {
			
			case 1: orien = CONS.Admin.Orien.HV; break; 
			case 2: orien = CONS.Admin.Orien.HH; break; 
			case 3: orien = CONS.Admin.Orien.VH; break; 
			case 4: orien = CONS.Admin.Orien.VV; break; 
			
			}
			
			break;//case A_LL
			
		case A_LR://----------------------------------
			
			switch(index_Orien) {
			
			case 1: orien = CONS.Admin.Orien.VH; break; 
			case 2: orien = CONS.Admin.Orien.VV; break; 
			case 3: orien = CONS.Admin.Orien.HV; break; 
			case 4: orien = CONS.Admin.Orien.HH; break; 
			
			}
			
			break;//case A_UL
			
		}//switch(name)
		
		return new Object[]{name, orien};
		
	}//get_NodeNameAndOrien_frmo_Status__B
	
	public static boolean 
	create_PropertiesFile(File fpath_Config) {
		// TODO Auto-generated method stub
	
		Properties prop = new Properties();
		OutputStream output = null;
		
		try {
			
			output = new FileOutputStream(fpath_Config, true);
			
			prop.setProperty("rect_B_W", "250");
	 
			// save properties to project root folder
			prop.store(output, null);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();

			String msg = String.format(
					Locale.JAPAN,
					"can't open the file: " + fpath_Config.getAbsolutePath(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
			
			JOptionPane.showMessageDialog(null,
					msg,
					"message", JOptionPane.ERROR_MESSAGE);

			return false;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			String msg = String.format(
					Locale.JAPAN,
					"IOException: " + fpath_Config.getAbsolutePath(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
			
			JOptionPane.showMessageDialog(null,
					msg,
					"message", JOptionPane.ERROR_MESSAGE);

			return false;
			
		}
		
		
		return true;
		
	}//create_PropertiesFile

	public static Properties
	load_Properties(File fpath_Config) {
		// TODO Auto-generated method stub

		Properties prop = new Properties();		
		InputStream input = null;
		
		try {
			
			input = new FileInputStream(fpath_Config);
			
			prop.load(input);
//			prop.setProperty("rect_B_W", "250");
	 
			// save properties to project root folder
//			prop.store(output, null);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();

			String msg = String.format(
					Locale.JAPAN,
					"can't open the file: " + fpath_Config.getAbsolutePath(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
			
			JOptionPane.showMessageDialog(null,
					msg,
					"message", JOptionPane.ERROR_MESSAGE);

			return null;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			String msg = String.format(
					Locale.JAPAN,
					"IOException: " + fpath_Config.getAbsolutePath(),
					Thread.currentThread().getStackTrace()[1].getLineNumber());
			
			JOptionPane.showMessageDialog(null,
					msg,
					"message", JOptionPane.ERROR_MESSAGE);

			return null;
			
		}

		return prop;
		
	}//load_Properties(File fpath_Config)

	public static void 
	update_AttachedTo
	(Rect_D11 rect_D6, Rect rect_C, NodeNames name) {
		// TODO Auto-generated method stub
		
		/*******************************

			validate

		 *******************************/
		if (name == null) {
			
			//log
			String text = String.format(Locale.JAPAN, "name => null\n");
			
			String fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			int line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

			return;
			
		}
		
		////////////////////////////////

		// update

		////////////////////////////////
		String rect_Name = name.toString().split("_")[0];
		
		if (rect_Name.equals("A")) rect_C.setAttachedTo(rect_D6.getRect_A());
		else if (rect_Name.equals("B")) rect_C.setAttachedTo(rect_D6.getRect_B());
		else {
			
			rect_C.setAttachedTo(rect_D6.getRect_B());
			
			//log
			String text = String.format(Locale.JAPAN, "rect name => unknow; set to default, i.e. rect B\n");
			
			String fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			int line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

		}//if (rect_Name.equals("A"))

		//log
		String text = String.format(Locale.JAPAN, 
							"attached to => %s\n", 
							rect_C.getAttachedTo().getRect_Name());
		
		String fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		int line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		
	}//update_AttachedTo

	public static boolean 
	is_SamePoint(NodeNames n1, NodeNames n2, Rect rect_A, Rect rect_B) {
		// TODO Auto-generated method stub
		
		Point p_n1 = Methods.get_Node_Coordinates(n1, rect_A, rect_B);
		Point p_n2 = Methods.get_Node_Coordinates(n2, rect_A, rect_B);
		
//		/*******************************
//
//			debug
//
//		 *******************************/
//		//log
//		String text = String.format(Locale.JAPAN, 
//							"n1 = %s, n2 = %s / "
//							+ "p_n1.x = %d, p_n1.y = %d / "
//							+ "p_n2.x = %d, p_n2.y = %d\n",
//							n1.toString(), n2.toString(),
//							p_n1.x, p_n1.y,
//							p_n2.x, p_n2.y
//							
//				);
//		
//		String fname = Thread.currentThread().getStackTrace()[1].getFileName();
//		
//		int line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
//		
//		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		

		
		if (p_n1.x == p_n2.x && p_n1.y == p_n2.y) {

//			//log
//			text = String.format(Locale.JAPAN, 
//								"same point\n"
//					);
//			
//			fname = Thread.currentThread().getStackTrace()[1].getFileName();
//			
//			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
//			
//			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

			return true;
			
		}
		
		return false;
		
	}//is_SamePoint

	private static Point 
	get_Node_Coordinates(NodeNames name, Rect rect_A, Rect rect_B) {
		// TODO Auto-generated method stub
		
		switch(name) {
		
		case A_UL: return new Point(rect_A.getX_Cur(), rect_A.getY_Cur());
		case A_UR: return new Point(rect_A.getX_Cur() + rect_A.getW_Orig(), 
										rect_A.getY_Cur());
		case A_LL: return new Point(rect_A.getX_Cur(), 
									rect_A.getY_Cur() + rect_A.getH_Orig());
		case A_LR: return new Point(rect_A.getX_Cur() + rect_A.getW_Orig(), 
									rect_A.getY_Cur() + rect_A.getH_Orig());

		case B_UL: return new Point(rect_B.getX_Cur(), rect_B.getY_Cur());
		case B_UR: return new Point(rect_B.getX_Cur() + rect_B.getW(), 
				rect_B.getY_Cur());
		case B_LL: return new Point(rect_B.getX_Cur(), 
				rect_B.getY_Cur() + rect_B.getH());
		case B_LR: return new Point(rect_B.getX_Cur() + rect_B.getW(), 
				rect_B.getY_Cur() + rect_B.getH());
		
//		case B_UL: return new Point(rect_B.getX_Cur(), rect_B.getY_Cur());
//		case B_UR: return new Point(rect_B.getX_Cur() + rect_B.getW_Orig(), 
//				rect_B.getY_Cur());
//		case B_LL: return new Point(rect_B.getX_Cur(), 
//				rect_B.getY_Cur() + rect_B.getH_Orig());
//		case B_LR: return new Point(rect_B.getX_Cur() + rect_B.getW_Orig(), 
//				rect_B.getY_Cur() + rect_B.getH_Orig());
//		
		}
		
		return null;
		
	}//get_Node_Coordinates

	public static Orien 
	conv_OrieFull_to_OrienShort
	(Orien orien) {
		// TODO Auto-generated method stub
		
		switch(orien) {
		
		case HH: return CONS.Admin.Orien.HH_;
		case HV: return CONS.Admin.Orien.HV_;
		case VV: return CONS.Admin.Orien.VV_;
		case VH: return CONS.Admin.Orien.VH_;
		
//		VERTI_VERTI, VERTI_HORI,
		
		}
		
		return null;
		
	}

	public static boolean 
	overWrap_on_A
	(Rect rect_A, Rect rect_B, Rect rect_C, int status_C) {
		// TODO Auto-generated method stub
		
		String text, fname;
		int line_Num;

		////////////////////////////////

		// dispatch: cases

		////////////////////////////////
		switch(rect_B.getAttachedAt()) {
		
		case A_UL:
			
			return overWrap_on_A__B_at_AUL(rect_A, rect_B, rect_C, status_C);
//			break;
		
		case A_UR:
			
//			return overWrap_on_A__B_at_A_UR(rect_A, rect_B, rect_C, status_C);
			
		default:
			
			return false;
			
		}
		
//		////////////////////////////////
//		
//		// prep data: rects
//		
//		////////////////////////////////
//		rect_A = rect_C.getAttachedTo().getAttachedTo();
//		rect_B = rect_C.getAttachedTo();
//		
//		////////////////////////////////
//
//		// prep data: coordinates
//
//		////////////////////////////////
//		// rect A
//		int a_X1 = rect_A.getX_Cur();
//		int a_Y1 = rect_A.getY_Cur();
//		
//		int a_X2 = rect_A.getX_Cur() + rect_A.getW_Orig();
//		
//		// rect B
//		int b_X1 = rect_B.getX_Cur();
//		int b_Y1 = rect_B.getY_Cur();
//		
//		int b_X2 = rect_B.getX_Cur() + rect_B.getW();
////		int b_X2 = rect_B.getX_Cur() + rect_B.getW_Orig();
//		
//		// rect C
//		int c_X1 = rect_C.getX_Cur();
//		int c_Y1 = rect_C.getY_Cur();
//		int c_X2 = rect_C.getX_Cur() + rect_C.getW();
//		int c_Y2 = rect_C.getY_Cur() + rect_C.getH();
//
////		int x_A = rect_A.getX_Cur();
////		int x_B = rect_B.getX_Cur();
////		int x_C = rect_C.getX_Cur();
//		
//		
//		////////////////////////////////
//
//		// filter: Rect C attached at the left-hand end
//
//		////////////////////////////////
//		if ((c_X1 <= b_X1 && c_X1 <= a_X1)
//				&& (c_Y1 <= b_Y1 && c_Y1 <= a_Y1)) {
//
//			//log
//			text = String.format(Locale.JAPAN, "rect C => left-hand-most\n");
//			
//			fname = Thread.currentThread().getStackTrace()[1].getFileName();
//			
//			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
//			
//			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
//
//			return false;
//			
//		}
//		
//		////////////////////////////////
//		
//		// filter: Rect C attached at the right-hand end
//		
//		////////////////////////////////
//		if (c_X2 >= b_X2 && c_X2 >= a_X2) {
//			
//			//log
//			text = String.format(Locale.JAPAN, "rect C => right-hand-most\n");
//			
//			fname = Thread.currentThread().getStackTrace()[1].getFileName();
//			
//			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
//			
//			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
//			
//			return false;
//			
//		}
//		
//		////////////////////////////////
//
//		// report
//
//		////////////////////////////////
//		//log
//		text = String.format(Locale.JAPAN, 
//					"rect C => passed the filters: "
//					+ "c_X1 = %d / b_X1 = %d / a_X1 = %d / "
//					+ "c_Y1 = %d / b_Y1 = %d / a_Y1 = %d\n",
//					c_X1, b_X1, a_X1,
//					c_Y1, b_Y1, a_Y1
//				);
//		
//		fname = Thread.currentThread().getStackTrace()[1].getFileName();
//		
//		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
//		
//		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
//		
//		
//		////////////////////////////////
//
//		// when rect C overwraps on A from the upper edge
//
//		////////////////////////////////
////		int a_X1 = rect_A.getX_Cur();
////		int a_X2 = rect_A.getX_Cur() + rect_A.getW_Orig();
////		
////		int a_Y1 = rect_A.getY_Cur();
////		
////		int c_X2 = rect_C.getX_Cur() + rect_C.getW();
////		int c_Y2 = rect_C.getY_Cur() + rect_C.getH();
//		
////		//log
////		text = String.format(Locale.JAPAN, "a_Y1 = %d, c_Y2 = %d\n", a_Y1, c_Y2);
////		
////		fname = Thread.currentThread().getStackTrace()[1].getFileName();
////		
////		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
////		
////		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
//		
//		//log
//		text = String.format(Locale.JAPAN, 
//							"Rect B: node name => %s\n", rect_B.getAttachedAt().toString());
//		
//		fname = Thread.currentThread().getStackTrace()[1].getFileName();
//		
//		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
//		
//		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
//		
//		////////////////////////////////
//
//		// judge
//
//		////////////////////////////////
//		if ((a_X1 < c_X2 && c_X2 < a_X2)
//				&& (a_Y1 < c_Y2)) {
//			
//			return true;
//			
//		} else {
//			
//			return false;
//			
//		}
//		
//		
////		return true;
////		return false;
		
	}//overWrap_on_A

	public static boolean 
	overWrap_V2
	(Rect rect_A, Rect rect_B, Rect rect_C, int status_C) {
		// TODO Auto-generated method stub
		
		String text, fname;
		int line_Num;
		
		////////////////////////////////
		
		// dispatch: cases
		
		////////////////////////////////
		switch(rect_B.getOrien()) {
		
		case HV:
		case HH:
		case VH: return false;
			
		case VV:
			
			return overWrap_V2_Borien_VH(rect_A, rect_B, rect_C);
			
		default:
			
			return false;
			
		}
		
	}//overWrap_V2
	
	/*******************************

		use => crossing-edge method

	 *******************************/
	public static boolean 
	overWrap_V3
	(Rect rect_A, Rect rect_B, Rect rect_C, int status_C) {
		// TODO Auto-generated method stub
		
		String text, fname;
		int line_Num;

		////////////////////////////////

		// get: rectangle NOT attached by rect C

		////////////////////////////////
		Rect rect_NotAttached = Methods.get_NotAttachedRect(rect_A, rect_B, rect_C);

		/*******************************

			validater: if null => return false (not overwrapping)

		 *******************************/
		if (rect_NotAttached == null) {
		
			//log
			text = String.format(Locale.JAPAN, "rect_NotAttached => null\n");
			
			fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

			return false;
			
		}

		//log
		text = String.format(Locale.JAPAN, 
						"rect_NotAttached => %s\n", rect_NotAttached.getRect_Name());
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

		////////////////////////////////

		// detect: crossing edges

		////////////////////////////////
		boolean crossing = Methods.isEdges_Crossing(rect_C, rect_NotAttached);

		if (crossing == true) {
			
			//log
			text = String.format(Locale.JAPAN, "edges => crossing\n");
			
			fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);


			return true;
			
		}
		
		////////////////////////////////

		// detect: nodes in the rect

		////////////////////////////////
		boolean within = Methods.isNodes_Within(rect_C, rect_NotAttached);
		
		if (within == true) {
			
			//log
			text = String.format(Locale.JAPAN, "nodes => within\n");
			
			fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

			return true;
			
		}
		
		////////////////////////////////

		// detect: overwrap from edge to edge

		////////////////////////////////
		boolean edge2edge = Methods.isOverwrap_Edge2Edge(rect_C, rect_NotAttached);
		
		if (edge2edge == true) {
			
			return true;
			
		}
		
//		return crossing;
		return false;
		
	}//overWrap_V3
	
	////////////////////////////////

	// caller => overWrap_V3()

	private static boolean 
	isOverwrap_Edge2Edge
	(Rect rect_C, Rect rect_NotAttached) {

		////////////////////////////////

		// prep: vars

		////////////////////////////////
		int c_X1 = rect_C.getX_Cur();
		int c_Y1 = rect_C.getY_Cur();
		int c_X2 = rect_C.getX_Cur() + rect_C.getW();
		int c_Y2 = rect_C.getY_Cur() + rect_C.getH();
		
		int r_X1 = rect_NotAttached.getX_Cur();
		int r_Y1 = rect_NotAttached.getY_Cur();
		int r_X2 = rect_NotAttached.getX_Cur() + rect_NotAttached.getW();
		int r_Y2 = rect_NotAttached.getY_Cur() + rect_NotAttached.getH();
		
		// rect: not attached
		Point R1_UL = new Point(r_X1, r_Y1);
		Point R1_UR = new Point(r_X2, r_Y1);
		Point R1_LL = new Point(r_X1, r_Y2);
		Point R1_LR = new Point(r_X2, r_Y2);

		// rect: C
		Point C_UL = new Point(c_X1, c_Y1);
		Point C_UR = new Point(c_X2, c_Y1);
		Point C_LL = new Point(c_X1, c_Y2);
		Point C_LR = new Point(c_X2, c_Y2);
		
		//////////////////////////////////////////////////////////////

		// judge

		////////////////////////////////
		////////////////////////////////

		// C at: UR of the attached rect
		// C at: overwrap => horizontally, from the right

		////////////////////////////////
		if (rect_C.getH() == rect_NotAttached.getH()
				&& C_UL.y == R1_UR.y
				&& (C_UL.x < R1_UR.x) && (R1_UR.x < C_UR.x)) {
			
			//log
			String text, fname; int line_Num;
			
			text = String.format(Locale.JAPAN, 
						"overwrap => edge to edge: horizontal, from right: C at %s, orien = %s\n", 
						rect_C.getAttachedAt(), rect_C.getOrien());
			
			fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

			return true;
			
		}

		////////////////////////////////
		
		// 
		// C at: overwrap => horizontally, from the left
		
		////////////////////////////////
		if (rect_C.getH() == rect_NotAttached.getH()
				&& C_UL.y == R1_UR.y
				&& (C_UL.x < R1_UL.x) && (R1_UL.x < C_UR.x)) {
			
			//log
			String text, fname; int line_Num;
			
			text = String.format(Locale.JAPAN, 
					"overwrap => edge to edge: horizontal, from right: C at %s, orien = %s\n", 
					rect_C.getAttachedAt(), rect_C.getOrien());
			
			fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
			
			return true;
			
		}

		////////////////////////////////
		
		// 
		// C at: overwrap => vertically, from the top
		
		////////////////////////////////
		if (rect_C.getW() == rect_NotAttached.getW()
				&& C_LL.x == R1_UL.x
				&& (C_UL.y < R1_UL.y) && (R1_UL.y < C_LL.y)) {
			
			//log
			String text, fname; int line_Num;
			
			text = String.format(Locale.JAPAN, 
					"overwrap => edge to edge: vertical, from top: C at %s, orien = %s\n", 
					rect_C.getAttachedAt(), rect_C.getOrien());
			
			fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
			
			return true;
			
		}

		////////////////////////////////
		
		// 
		// C at: overwrap => vertically, from the bottom
		
		////////////////////////////////
		if (rect_C.getW() == rect_NotAttached.getW()
				&& C_UL.x == R1_LL.x
				&& (C_UL.y < R1_LL.y) && (R1_LL.y < C_LL.y)) {
			
			//log
			String text, fname; int line_Num;
			
			text = String.format(Locale.JAPAN, 
					"overwrap => edge to edge: vertical, from top: C at %s, orien = %s\n", 
					rect_C.getAttachedAt(), rect_C.getOrien());
			
			fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
			
			return true;
			
		}
		
		////////////////////////////////

		// default

		////////////////////////////////
		return false;
	}
	

	private static boolean 
	isNodes_Within
	(Rect rect_C, Rect rect_NotAttached) {
		
		////////////////////////////////

		// prep: vars

		////////////////////////////////
		int c_X1 = rect_C.getX_Cur();
		int c_Y1 = rect_C.getY_Cur();
		int c_X2 = rect_C.getX_Cur() + rect_C.getW();
		int c_Y2 = rect_C.getY_Cur() + rect_C.getH();
		
		int b_X1 = rect_NotAttached.getX_Cur();
		int b_Y1 = rect_NotAttached.getY_Cur();
		int b_X2 = rect_NotAttached.getX_Cur() + rect_NotAttached.getW();
		int b_Y2 = rect_NotAttached.getY_Cur() + rect_NotAttached.getH();
		
		////////////////////////////////////////////////////////////////

		// nodes in C => included in B

		////////////////////////////////
		////////////////////////////////
		
		// x1, y1
		
		////////////////////////////////
		boolean included = Methods.is_WithinRect(
								rect_NotAttached, 
								new Point(c_X1, c_Y1));
		
		if (included == true) return true;
		
		////////////////////////////////
		
		// x2, y1
		
		////////////////////////////////
		included = Methods.is_WithinRect(
				rect_NotAttached, 
				new Point(c_X2, c_Y1));
		
		if (included == true) return true;
		
		////////////////////////////////
		
		// x1, y2
		
		////////////////////////////////
		included = Methods.is_WithinRect(
				rect_NotAttached, 
				new Point(c_X1, c_Y2));
		
		if (included == true) return true;
		
		////////////////////////////////
		
		// x2, y2
		
		////////////////////////////////
		included = Methods.is_WithinRect(
				rect_NotAttached, 
				new Point(c_X2, c_Y2));
		
		if (included == true) return true;

		////////////////////////////////

		// default: false

		////////////////////////////////
		return false;
		
	}//isNodes_Within

	////////////////////////////////
	private static boolean 
	isEdges_Crossing
	(Rect rect_C, Rect rect_NotAttached) {
		// TODO Auto-generated method stub

		////////////////////////////////

		// prep: vars

		////////////////////////////////
		int c_X1 = rect_C.getX_Cur();
		int c_Y1 = rect_C.getY_Cur();
		int c_X2 = rect_C.getX_Cur() + rect_C.getW();
		int c_Y2 = rect_C.getY_Cur() + rect_C.getH();
		
		int b_X1 = rect_NotAttached.getX_Cur();
		int b_Y1 = rect_NotAttached.getY_Cur();
		int b_X2 = rect_NotAttached.getX_Cur() + rect_NotAttached.getW();
		int b_Y2 = rect_NotAttached.getY_Cur() + rect_NotAttached.getH();
		
		//////////////////////////////////////////////////////////////

		// edge: c_Lx1 => C.x1,y1 -- C.x2,y1
		// with: r.Ly1

		////////////////////////////////
//		boolean crossing = false;
		
		if ((c_X1 < b_X1 && b_X1 < c_X2)
				&& (b_Y1 < c_Y1 && c_Y1 < b_Y2)) {
			
//			crossing = true;
			return true;
			
		}
		
		////////////////////////////////
		
		// edge: c_Lx1 => C.x1,y1 -- C.x2,y1
		// with: r.Ly2
		
		////////////////////////////////
		if ((c_X1 < b_X2 && b_X2 < c_X2)
				&& (b_Y1 < c_Y1 && c_Y1 < b_Y2)) {
			
			return true;
			
		}
		
		//////////////////////////////////////////////////////////////
		
		// edge: c_Ly1 => C.x1,y1 -- C.x1,y2
		// with: r.Lx1
		
		////////////////////////////////
		if ((b_X1 < c_X1 && c_X1 < b_X2)
				&& (c_Y1 < b_Y1 && b_Y1 < c_Y2)) {
			
			return true;
			
		}
		
		/////////////////////////////////
		
		// edge: c_Ly1 => C.x1,y1 -- C.x1,y2
		// with: r.Lx2
		
		////////////////////////////////
		if ((b_X1 < c_X1 && c_X1 < b_X2)
				&& (c_Y1 < b_Y2 && b_Y2 < c_Y2)) {
			
			return true;
			
		}
		
		//////////////////////////////////////////////////////////////
		
		// edge: c_Lx2 => C.x1,y2 -- C.x2,y2
		// with: r.Ly1
		
		////////////////////////////////
		boolean Bx1_bet_Cx1Cx2 = c_X1 < b_X1 && b_X1 < c_X2;
		boolean Cy2_bet_By1By2 = b_Y1 < c_Y2 && c_Y2 < b_Y2;
		
		if (Bx1_bet_Cx1Cx2 && Cy2_bet_By1By2) {
			
//			crossing = true;
			return true;
			
		}

		////////////////////////////////
		
		// edge: c_Lx2 => C.x1,y2 -- C.x2,y2
		// with: r.Ly2
		
		////////////////////////////////
		boolean Bx2_bet_Cx1Cx2 = c_X1 < b_X2 && b_X2 < c_X2;
//		boolean Cy2_bet_By1By2 = b_Y1 < c_Y2 && c_Y2 < b_Y2;
		
		if (Bx2_bet_Cx1Cx2 && Cy2_bet_By1By2) {
			
//			crossing = true;
			return true;
			
		}
		
		//////////////////////////////////////////////////////////////
		
		// edge: c_Ly2 => C.x1,y1 -- C.x1,y2
		// with: r.Lx1
		
		////////////////////////////////
		boolean Cx2_bet_Bx1Bx2 = b_X1 < c_X2 && c_X2 < b_X2;
		boolean By1_bet_Cy1Cy2 = c_Y1 < b_Y1 && b_Y1 < c_Y2;
		
		if (Cx2_bet_Bx1Bx2 && By1_bet_Cy1Cy2) {
			
			return true;
			
		}

		/////////////////////////////////
		
		// edge: c_Ly2 => C.x1,y1 -- C.x1,y2
		// with: r.Lx2
		
		////////////////////////////////
		boolean By2_bet_Cy1Cy2 = c_Y1 < b_Y2 && b_Y2 < c_Y2;
		
		if (Cx2_bet_Bx1Bx2
				&& By2_bet_Cy1Cy2) {
			
			return true;
			
		}

		////////////////////////////////

		// default => false

		////////////////////////////////
		return false;
//		return crossing;
		
	}//isEdges_Crossing
	

	private static boolean 
	is_WithinRect(Rect rect, Point point) {
		// TODO Auto-generated method stub
		
		int rect_X1 = rect.getX_Cur();
		int rect_Y1 = rect.getY_Cur();
		int rect_X2 = rect.getX_Cur() + rect.getW();
		int rect_Y2 = rect.getY_Cur() + rect.getH();

		boolean bet_X1X2 = (rect_X1 < point.x) && (point.x < rect_X2);
		boolean bet_Y1Y2 = (rect_Y1 < point.y) && (point.y < rect_Y2);
		
		return bet_X1X2 && bet_Y1Y2;
		
	}//is_WithinRect

	private static Rect 
	get_NotAttachedRect(Rect rect_A, Rect rect_B, Rect rect_C) {
		// TODO Auto-generated method stub
		
		Rect rect_Attachee = rect_C.getAttachedTo();
		
		if (rect_Attachee == rect_A) {
			
			return rect_B;
			
		} else if (rect_Attachee == rect_B) {
				
				return rect_A;
				
		} else {
			
			return null;
			
		}
		
	}//get_NotAttachedRect

	private static boolean 
	overWrap_V2_Borien_VH
	(Rect rect_A, Rect rect_B, Rect rect_C) {
		// TODO Auto-generated method stub
		
		switch(rect_B.getAttachedAt()) {
		
		case A_UL:
			
			return overWrap_V2_Borien_VH__atAUL(rect_A, rect_B, rect_C);
			
		case A_LL:
		case A_UR:
		case A_LR:
			
		}
		
		
		return false;
		
	}//overWrap_V2_Borien_VH

	private static boolean 
	overWrap_V2_Borien_VH__atAUL
	(Rect rect_A, Rect rect_B, Rect rect_C) {
		// TODO Auto-generated method stub
		
		
		return false;
		
	}//overWrap_V2_Borien_VH__atAUL

	private static boolean 
	overWrap_on_A__B_at_AUL
	(Rect rect_A, Rect rect_B, Rect rect_C, int status_C) {
		
		String text, fname;
		int line_Num;

		//log
		text = String.format(Locale.JAPAN, "overWrap_on_A__B_at_AUL\n");
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

		////////////////////////////////

		// dispatch

		////////////////////////////////
		String rect_Name = rect_C.getAttachedAt().toString().split("_")[0];
		
		//log
		text = String.format(Locale.JAPAN, "overWrap_on_A__B_at_AUL: rect_Name => %s\n", rect_Name);
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		
		if (rect_Name.equals("A")) {
			
			switch(rect_C.getAttachedAt()) {
			
			case A_UR://-------------------------
			
				return overWrap_on_A__Bat_AUL_Cat_AUR(rect_B, rect_C);
//				return overWrap_on_A__B_at_A_UL_C_at_A_UR(rect_B, rect_C);
				
			case A_LR:
				
				return overWrap_on_A__Bat_AUL_Cat_ALR(rect_B, rect_C);
				
			case A_LL: 
				
				return overWrap_on_A__Bat_AUL_Cat_ALL(rect_B, rect_C);
				
			default: return false;//-------------------------
				
			}//switch(rect_C.getAttachedAt())
			
		} else if (rect_Name.equals("B")) {//if (rect_Name.equals("A"))

			//log
			text = String.format(Locale.JAPAN, 
							"overWrap_on_A__B_at_AUL: rect_B.orien => %s\n", rect_B.getOrien());
			
			fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

			switch(rect_B.getOrien()) {
			
			case HV:
			case HH:
				
				return overWrap_on_A__Bat_AUL_HV_Cat_B(rect_A, rect_B, rect_C);
			
			case VH:
				
				return overWrap_on_A__Bat_AUL_VH(rect_A, rect_B, rect_C);
				
			case VV:
				
				return false;
				
			}
			
//			return overWrap_on_A__B_at_A_UL_C_at_B(rect_A, rect_B, rect_C);
			
		}//if (rect_Name.equals("A"))
		
		
		////////////////////////////////

		// default

		////////////////////////////////
		//log
		text = String.format(Locale.JAPAN, "overWrap_on_A__B_at_AUL: default => false\n");
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

		return false;
		
//		////////////////////////////////
//		
//		// prep data: rects
//		
//		////////////////////////////////
//		rect_A = rect_C.getAttachedTo().getAttachedTo();
//		rect_B = rect_C.getAttachedTo();
//		
//		////////////////////////////////
//
//		// prep data: coordinates
//
//		////////////////////////////////
//		// rect A
//		int a_X1 = rect_A.getX_Cur();
//		int a_Y1 = rect_A.getY_Cur();
//		
//		int a_X2 = rect_A.getX_Cur() + rect_A.getW_Orig();
//		
//		// rect B
//		int b_X1 = rect_B.getX_Cur();
//		int b_Y1 = rect_B.getY_Cur();
//		
//		int b_X2 = rect_B.getX_Cur() + rect_B.getW();
////		int b_X2 = rect_B.getX_Cur() + rect_B.getW_Orig();
//		
//		// rect C
//		int c_X1 = rect_C.getX_Cur();
//		int c_Y1 = rect_C.getY_Cur();
//		int c_X2 = rect_C.getX_Cur() + rect_C.getW();
//		int c_Y2 = rect_C.getY_Cur() + rect_C.getH();
//
////		int x_A = rect_A.getX_Cur();
////		int x_B = rect_B.getX_Cur();
////		int x_C = rect_C.getX_Cur();
//		
//		
//		////////////////////////////////
//
//		// filter: Rect C attached at the left-hand end
//
//		////////////////////////////////
//		if ((c_X1 <= b_X1 && c_X1 <= a_X1)
//				&& (c_Y1 <= b_Y1 && c_Y1 <= a_Y1)) {
//
//			//log
//			text = String.format(Locale.JAPAN, "rect C => left-hand-most\n");
//			
//			fname = Thread.currentThread().getStackTrace()[1].getFileName();
//			
//			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
//			
//			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
//
//			return false;
//			
//		}
//		
//		////////////////////////////////
//		
//		// filter: Rect C attached at the right-hand end
//		
//		////////////////////////////////
//		if (c_X2 >= b_X2 && c_X2 >= a_X2) {
//			
//			//log
//			text = String.format(Locale.JAPAN, "rect C => right-hand-most\n");
//			
//			fname = Thread.currentThread().getStackTrace()[1].getFileName();
//			
//			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
//			
//			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
//			
//			return false;
//			
//		}
//		
//		////////////////////////////////
//
//		// report
//
//		////////////////////////////////
//		//log
//		text = String.format(Locale.JAPAN, 
//					"rect C => passed the filters: "
//					+ "c_X1 = %d / b_X1 = %d / a_X1 = %d / "
//					+ "c_Y1 = %d / b_Y1 = %d / a_Y1 = %d\n",
//					c_X1, b_X1, a_X1,
//					c_Y1, b_Y1, a_Y1
//				);
//		
//		fname = Thread.currentThread().getStackTrace()[1].getFileName();
//		
//		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
//		
//		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
//		
//		
//		////////////////////////////////
//
//		// when rect C overwraps on A from the upper edge
//
//		////////////////////////////////
////		int a_X1 = rect_A.getX_Cur();
////		int a_X2 = rect_A.getX_Cur() + rect_A.getW_Orig();
////		
////		int a_Y1 = rect_A.getY_Cur();
////		
////		int c_X2 = rect_C.getX_Cur() + rect_C.getW();
////		int c_Y2 = rect_C.getY_Cur() + rect_C.getH();
//		
////		//log
////		text = String.format(Locale.JAPAN, "a_Y1 = %d, c_Y2 = %d\n", a_Y1, c_Y2);
////		
////		fname = Thread.currentThread().getStackTrace()[1].getFileName();
////		
////		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
////		
////		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
//		
//		//log
//		text = String.format(Locale.JAPAN, 
//							"Rect B: node name => %s\n", rect_B.getAttachedAt().toString());
//		
//		fname = Thread.currentThread().getStackTrace()[1].getFileName();
//		
//		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
//		
//		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
//		
//		////////////////////////////////
//
//		// judge
//
//		////////////////////////////////
//		if ((a_X1 < c_X2 && c_X2 < a_X2)
//				&& (a_Y1 < c_Y2)) {
//			
//			return true;
//			
//		} else {
//			
//			return false;
//			
//		}

	}//overWrap_on_A__B_at_A_UL

	private static boolean 
	overWrap_on_A__Bat_AUL_VH
	(Rect rect_A, Rect rect_B, Rect rect_C) {
		
		String text, fname;
		int line_Num;

		//log
		text = String.format(Locale.JAPAN, "overWrap_on_A__Bat_AUL_VH\n");
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);


		////////////////////////////////
		
		// prep data: rects
		
		////////////////////////////////
		rect_A = rect_C.getAttachedTo().getAttachedTo();
		rect_B = rect_C.getAttachedTo();
		
		////////////////////////////////

		// prep data: coordinates

		////////////////////////////////
		// rect A
		int a_X1 = rect_A.getX_Cur();
		int a_Y1 = rect_A.getY_Cur();
		
		int a_X2 = rect_A.getX_Cur() + rect_A.getW_Orig();
		
		// rect B
		int b_X1 = rect_B.getX_Cur();
		int b_Y1 = rect_B.getY_Cur();
		
		int b_X2 = rect_B.getX_Cur() + rect_B.getW();
//		int b_X2 = rect_B.getX_Cur() + rect_B.getW_Orig();
		
		// rect C
		int c_X1 = rect_C.getX_Cur();
		int c_Y1 = rect_C.getY_Cur();
		int c_X2 = rect_C.getX_Cur() + rect_C.getW();
		int c_Y2 = rect_C.getY_Cur() + rect_C.getH();

		////////////////////////////////

		// dispatch

		////////////////////////////////
		switch(rect_C.getAttachedAt()) {
		
		case B_UL: return false;
		case B_UR: return false;	// when B at A_UL, the node B_UR
									// 	is not in the node name list.
									// Put the case here, anyway
		case B_LL: 
			
			return Methods.overWrap_on_A__Bat_AUL_VH_Cat_BLL(rect_A, rect_B, rect_C);
		
		case B_LR: 
			
			return Methods.overWrap_on_A__Bat_AUL_VH_Cat_BLR(rect_A, rect_B, rect_C);
			
		case A_UL:
		case A_UR: return false;
		
		case A_LR:
			
			return Methods.overWrap_on_A__Bat_AUL_VH_Cat_ALR(rect_A, rect_B, rect_C);
			
		case A_LL:
			
			return Methods.overWrap_on_A__Bat_AUL_VH_Cat_ALL(rect_A, rect_B, rect_C);
			
		}//switch(rect_C.getAttachedAt())
		
		return false;
		
	}//overWrap_on_A__Bat_AUL_VH

	private static boolean 
	overWrap_on_A__Bat_AUL_VH_Cat_BLL
	(Rect rect_A, Rect rect_B, Rect rect_C) {
		
		String text, fname;
		int line_Num;

		//log
		text = String.format(Locale.JAPAN, "overWrap_on_A__Bat_AUL_VH_Cat_BLL\n");
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);


		////////////////////////////////
		
		// prep data: rects
		
		////////////////////////////////
		rect_A = rect_C.getAttachedTo().getAttachedTo();
		rect_B = rect_C.getAttachedTo();
		
		////////////////////////////////

		// prep data: coordinates

		////////////////////////////////
		// rect A
		int a_X1 = rect_A.getX_Cur();
		int a_Y1 = rect_A.getY_Cur();
		
		int a_X2 = rect_A.getX_Cur() + rect_A.getW_Orig();
		
		// rect B
		int b_X1 = rect_B.getX_Cur();
		int b_Y1 = rect_B.getY_Cur();
		
		int b_X2 = rect_B.getX_Cur() + rect_B.getW();
//		int b_X2 = rect_B.getX_Cur() + rect_B.getW_Orig();
		
		// rect C
		int c_X1 = rect_C.getX_Cur();
		int c_Y1 = rect_C.getY_Cur();
		int c_X2 = rect_C.getX_Cur() + rect_C.getW();
		int c_Y2 = rect_C.getY_Cur() + rect_C.getH();
		
		////////////////////////////////

		// dispatch

		////////////////////////////////
		switch(rect_C.getOrien()) {
		
		case VH:
		case VV: return false;
		
		case HV:
			
			if (rect_C.getW() < rect_B.getW()) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		case HH:
			
			if (rect_C.getW() < rect_B.getW()) {
				
				return false;
				
			} else {
				
				return true;
				
			}
		
		}
		
		return false;
		
	}//overWrap_on_A__Bat_AUL_VH_Cat_BLL
	
	private static boolean 
	overWrap_on_A__Bat_AUL_VH_Cat_BLR
	(Rect rect_A, Rect rect_B, Rect rect_C) {
		
		String text, fname;
		int line_Num;
		
		//log
		text = String.format(Locale.JAPAN, "overWrap_on_A__Bat_AUL_VH_Cat_BLR\n");
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		
		
		////////////////////////////////
		
		// prep data: rects
		
		////////////////////////////////
		rect_A = rect_C.getAttachedTo().getAttachedTo();
		rect_B = rect_C.getAttachedTo();
		
		////////////////////////////////
		
		// prep data: coordinates
		
		////////////////////////////////
		// rect A
		int a_X1 = rect_A.getX_Cur();
		int a_Y1 = rect_A.getY_Cur();
		
		int a_X2 = rect_A.getX_Cur() + rect_A.getW_Orig();
		
		// rect B
		int b_X1 = rect_B.getX_Cur();
		int b_Y1 = rect_B.getY_Cur();
		
		int b_X2 = rect_B.getX_Cur() + rect_B.getW();
//		int b_X2 = rect_B.getX_Cur() + rect_B.getW_Orig();
		
		// rect C
		int c_X1 = rect_C.getX_Cur();
		int c_Y1 = rect_C.getY_Cur();
		int c_X2 = rect_C.getX_Cur() + rect_C.getW();
		int c_Y2 = rect_C.getY_Cur() + rect_C.getH();
		
		////////////////////////////////
		
		// dispatch
		
		////////////////////////////////
		switch(rect_C.getOrien()) {
		
		case VH:
		case VV: return true;
		
		case HV:
		case HH: return false;
			
		}
		
		return false;
		
	}//overWrap_on_A__Bat_AUL_VH_Cat_BLR
	
	private static boolean 
	overWrap_on_A__Bat_AUL_VH_Cat_ALR
	(Rect rect_A, Rect rect_B, Rect rect_C) {
		
		String text, fname;
		int line_Num;
		
		//log
		text = String.format(Locale.JAPAN, "overWrap_on_A__Bat_AUL_VH_Cat_ALL\n");
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		
		
		////////////////////////////////
		
		// prep data: rects
		
		////////////////////////////////
		rect_A = rect_C.getAttachedTo().getAttachedTo();
		rect_B = rect_C.getAttachedTo();
		
		////////////////////////////////
		
		// prep data: coordinates
		
		////////////////////////////////
		// rect A
		int a_X1 = rect_A.getX_Cur();
		int a_Y1 = rect_A.getY_Cur();
		
		int a_X2 = rect_A.getX_Cur() + rect_A.getW_Orig();
		
		// rect B
		int b_X1 = rect_B.getX_Cur();
		int b_Y1 = rect_B.getY_Cur();
		
		int b_X2 = rect_B.getX_Cur() + rect_B.getW();
		int b_Y2 = rect_B.getY_Cur() + rect_B.getH();
//		int b_X2 = rect_B.getX_Cur() + rect_B.getW_Orig();
		
		// rect C
		int c_X1 = rect_C.getX_Cur();
		int c_Y1 = rect_C.getY_Cur();
		int c_X2 = rect_C.getX_Cur() + rect_C.getW();
		int c_Y2 = rect_C.getY_Cur() + rect_C.getH();
		
		////////////////////////////////
		
		// dispatch
		
		////////////////////////////////
		switch(rect_C.getOrien()) {
		
		case VH:
		case VV: return false;
		
		case HV: return false;
		case HH: 
			
			if ((c_Y1 < b_Y2) && (c_X1 < b_X2)) {
				
				return true;
				
			} else {
				
				return false;
				
			}
			
		}
		
		return false;
		
	}//overWrap_on_A__Bat_AUL_VH_Cat_ALR
	
	private static boolean 
	overWrap_on_A__Bat_AUL_VH_Cat_ALL
	(Rect rect_A, Rect rect_B, Rect rect_C) {
		
		String text, fname;
		int line_Num;
		
		//log
		text = String.format(Locale.JAPAN, "overWrap_on_A__Bat_AUL_VH_Cat_ALL\n");
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		
		
		////////////////////////////////
		
		// prep data: rects
		
		////////////////////////////////
		rect_A = rect_C.getAttachedTo().getAttachedTo();
		rect_B = rect_C.getAttachedTo();
		
		////////////////////////////////
		
		// prep data: coordinates
		
		////////////////////////////////
		// rect A
		int a_X1 = rect_A.getX_Cur();
		int a_Y1 = rect_A.getY_Cur();
		
		int a_X2 = rect_A.getX_Cur() + rect_A.getW_Orig();
		
		// rect B
		int b_X1 = rect_B.getX_Cur();
		int b_Y1 = rect_B.getY_Cur();
		
		int b_X2 = rect_B.getX_Cur() + rect_B.getW();
		int b_Y2 = rect_B.getY_Cur() + rect_B.getH();
//		int b_X2 = rect_B.getX_Cur() + rect_B.getW_Orig();
		
		// rect C
		int c_X1 = rect_C.getX_Cur();
		int c_Y1 = rect_C.getY_Cur();
		int c_X2 = rect_C.getX_Cur() + rect_C.getW();
		int c_Y2 = rect_C.getY_Cur() + rect_C.getH();
		
		////////////////////////////////
		
		// dispatch
		
		////////////////////////////////
		switch(rect_C.getOrien()) {
		
		case HV:
		case HH: return false;
		
		case VH:
		case VV:
			
			if (c_Y1 < b_Y2) {
				
				return true;
				
			} else {
				
				return false;
				
			}
		
		}
		
		return false;
		
	}//overWrap_on_A__Bat_AUL_VH_Cat_ALL
	
	
	
	

	private static boolean 
	overWrap_on_A__Bat_AUL_HV_Cat_B
	(Rect rect_A, Rect rect_B, Rect rect_C) {
		
		String text, fname;
		int line_Num;

//		//log
//		text = String.format(Locale.JAPAN, "overWrap_on_A__B_at_A_UL_C_at_B\n");
//		
//		fname = Thread.currentThread().getStackTrace()[1].getFileName();
//		
//		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
//		
//		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);


		////////////////////////////////
		
		// prep data: rects
		
		////////////////////////////////
		rect_A = rect_C.getAttachedTo().getAttachedTo();
		rect_B = rect_C.getAttachedTo();
		
		////////////////////////////////

		// prep data: coordinates

		////////////////////////////////
		// rect A
		int a_X1 = rect_A.getX_Cur();
		int a_Y1 = rect_A.getY_Cur();
		
		int a_X2 = rect_A.getX_Cur() + rect_A.getW_Orig();
		
		// rect B
		int b_X1 = rect_B.getX_Cur();
		int b_Y1 = rect_B.getY_Cur();
		
		int b_X2 = rect_B.getX_Cur() + rect_B.getW();
//		int b_X2 = rect_B.getX_Cur() + rect_B.getW_Orig();
		
		// rect C
		int c_X1 = rect_C.getX_Cur();
		int c_Y1 = rect_C.getY_Cur();
		int c_X2 = rect_C.getX_Cur() + rect_C.getW();
		int c_Y2 = rect_C.getY_Cur() + rect_C.getH();

		////////////////////////////////

		// dispatch

		////////////////////////////////
		switch(rect_C.getAttachedAt()) {
		
		case B_UL: return false;
		
		case B_UR: return Methods.overWrap_on_A__B_at_A_UL_C_at_B_UR(
										rect_A, rect_B, rect_C);
		
		case B_LL: 
			
			if (rect_B.getOrien() == Orien.VH) return false;
			else if (rect_B.getOrien() == Orien.VV) return false;
			else if (rect_B.getOrien() == Orien.HV) return false;
//			else if (rect_B.getOrien() == Orien.HORI_HORI) {
//				
//				return Methods.overWrap_on_A__B_at_A_UL_C_at_B_LL_B_Orien_HH(
//						rect_A, rect_B, rect_C);;
//			}
				
			else return false;
				
			
//			return Methods.overWrap_on_A__B_at_A_UL_C_at_B_LL(
//										rect_A, rect_B, rect_C);
		}
		
		////////////////////////////////

		// filter: Rect C attached at the left-hand end

		////////////////////////////////
		if ((c_X1 <= b_X1 && c_X1 <= a_X1)
				&& (c_Y1 <= b_Y1 && c_Y1 <= a_Y1)) {

			//log
			text = String.format(Locale.JAPAN, "rect C => left-hand-most\n");
			
			fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

			return false;
			
		}
		
		////////////////////////////////
		
		// filter: Rect C attached at the right-hand end
		
		////////////////////////////////
		if (c_X2 >= b_X2 && c_X2 >= a_X2) {
			
			//log
			text = String.format(Locale.JAPAN, "rect C => right-hand-most\n");
			
			fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
			
			return false;
			
		}
		
		////////////////////////////////

		// report

		////////////////////////////////
		//log
		text = String.format(Locale.JAPAN, 
					"rect C => passed the filters: "
					+ "c_X1 = %d / b_X1 = %d / a_X1 = %d / "
					+ "c_Y1 = %d / b_Y1 = %d / a_Y1 = %d\n",
					c_X1, b_X1, a_X1,
					c_Y1, b_Y1, a_Y1
				);
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		
		
		////////////////////////////////

		// when rect C overwraps on A from the upper edge

		////////////////////////////////
//		int a_X1 = rect_A.getX_Cur();
//		int a_X2 = rect_A.getX_Cur() + rect_A.getW_Orig();
//		
//		int a_Y1 = rect_A.getY_Cur();
//		
//		int c_X2 = rect_C.getX_Cur() + rect_C.getW();
//		int c_Y2 = rect_C.getY_Cur() + rect_C.getH();
		
//		//log
//		text = String.format(Locale.JAPAN, "a_Y1 = %d, c_Y2 = %d\n", a_Y1, c_Y2);
//		
//		fname = Thread.currentThread().getStackTrace()[1].getFileName();
//		
//		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
//		
//		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		
		//log
		text = String.format(Locale.JAPAN, 
							"Rect B: node name => %s\n", rect_B.getAttachedAt().toString());
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		
		////////////////////////////////

		// judge

		////////////////////////////////
		if ((a_X1 < c_X2 && c_X2 < a_X2)
				&& (a_Y1 < c_Y2)) {
			
			return true;
			
		} else {
			
			return false;
			
		}

	}//overWrap_on_A__B_at_A_UL_C_at_B
	

	private static boolean 
	overWrap_on_A__B_at_A_UL_C_at_B_UR
	(Rect rect_A, Rect rect_B, Rect rect_C) {
		
		////////////////////////////////

		// prep data: coordinates

		////////////////////////////////
		// rect A
		int a_X1 = rect_A.getX_Cur();
		int a_Y1 = rect_A.getY_Cur();
		
		int a_X2 = rect_A.getX_Cur() + rect_A.getW_Orig();
		
		// rect B
		int b_X1 = rect_B.getX_Cur();
		int b_Y1 = rect_B.getY_Cur();
		
		int b_X2 = rect_B.getX_Cur() + rect_B.getW();
//		int b_X2 = rect_B.getX_Cur() + rect_B.getW_Orig();
		
		// rect C
		int c_X1 = rect_C.getX_Cur();
		int c_Y1 = rect_C.getY_Cur();
		int c_X2 = rect_C.getX_Cur() + rect_C.getW();
		int c_Y2 = rect_C.getY_Cur() + rect_C.getH();

		////////////////////////////////

		// dispatch: overWrap_on_A__B_at_A_UL_C_at_B_UR

		////////////////////////////////
		Orien orien_B = rect_B.getOrien();
		Orien orien_C = rect_C.getOrien();
		
		switch(orien_B) {
		
		case HV:// B.orien --------------------------
		case HH:// B.orien --------------------------
			
			switch(orien_C) {
			
			case HV:
			case HH: return false;
			case VH: // if: C.w_Orig > B.h ---> return true
				
				if (rect_C.getW_Orig() > rect_B.getH()) {
					
					return true;
					
				} else {
					
					return false;
					
				}
				
			case VV:
				
				if ((c_X2 >= a_X1 && c_X2 <= a_X2)
						&& (c_Y2 > a_Y1)) {
					
					return true;
					
				} else {
					
					return false;
					
				}
				
			}//switch(orien_C)
		
		}//switch(orien_B)
		
//		switch(orien_C) {
//		
//		case HORI_VERTI:
//		case HORI_HORI: return false;
//		case VERTI_HORI: // if: C.w_Orig > B.h ---> return true
//			
//			if (rect_C.getW_Orig() > rect_B.getH()) {
//				
//				return true;
//				
//			} else {
//				
//				return false;
//				
//			}
//			
//		case VERTI_VERTI:
//			
//			if ((c_X2 >= a_X1 && c_X2 <= a_X2)
//					&& (c_Y2 > a_Y1)) {
//				
//				return true;
//				
//			} else {
//				
//				return false;
//				
//			}
//			
//		}//switch(rect_C.getOrien())
		
		return false;
		
	}//overWrap_on_A__B_at_A_UL_C_at_B_UR
	
	private static boolean 
	overWrap_on_A__B_at_A_UL_C_at_B_LL
	(Rect rect_A, Rect rect_B, Rect rect_C) {
		
		////////////////////////////////
		
		// prep data: coordinates
		
		////////////////////////////////
		// rect A
		int a_X1 = rect_A.getX_Cur();
		int a_Y1 = rect_A.getY_Cur();
		
		int a_X2 = rect_A.getX_Cur() + rect_A.getW_Orig();
		
		// rect B
		int b_X1 = rect_B.getX_Cur();
		int b_Y1 = rect_B.getY_Cur();
		
		int b_X2 = rect_B.getX_Cur() + rect_B.getW();
//		int b_X2 = rect_B.getX_Cur() + rect_B.getW_Orig();
		
		// rect C
		int c_X1 = rect_C.getX_Cur();
		int c_Y1 = rect_C.getY_Cur();
		int c_X2 = rect_C.getX_Cur() + rect_C.getW();
		int c_Y2 = rect_C.getY_Cur() + rect_C.getH();
		
		////////////////////////////////
		
		// dispatch: overWrap_on_A__B_at_A_UL_C_at_B_LL
		
		////////////////////////////////
		Orien orien_B = rect_B.getOrien();
		Orien orien_C = rect_C.getOrien();
		
		switch(orien_B) {
		
		case HV:// B.orien --------------------------
		case HH:// B.orien --------------------------
			
			switch(orien_C) {
			
			case HV:
			case HH: return false;
			case VH: // if: C.w_Orig > B.h ---> return true
				
				if (rect_C.getW_Orig() > rect_B.getH()) {
					
					return true;
					
				} else {
					
					return false;
					
				}
				
			case VV:
				
				if ((c_X2 >= a_X1 && c_X2 <= a_X2)
						&& (c_Y2 > a_Y1)) {
					
					return true;
					
				} else {
					
					return false;
					
				}
				
			}//switch(orien_C)
			
		}//switch(orien_B)
		
//		switch(orien_C) {
//		
//		case HORI_VERTI:
//		case HORI_HORI: return false;
//		case VERTI_HORI: // if: C.w_Orig > B.h ---> return true
//			
//			if (rect_C.getW_Orig() > rect_B.getH()) {
//				
//				return true;
//				
//			} else {
//				
//				return false;
//				
//			}
//			
//		case VERTI_VERTI:
//			
//			if ((c_X2 >= a_X1 && c_X2 <= a_X2)
//					&& (c_Y2 > a_Y1)) {
//				
//				return true;
//				
//			} else {
//				
//				return false;
//				
//			}
//			
//		}//switch(rect_C.getOrien())
		
		return false;
		
	}//overWrap_on_A__B_at_A_UL_C_at_B_LL
	

	private static boolean 
	overWrap_on_A__B_at_A_UL_C_at_A_UR
	(Rect rect_B, Rect rect_C) {
		// TODO Auto-generated method stub
		switch(rect_C.getOrien()) {
		
		case HV:
			
			if (rect_C.getX_Cur() >= (rect_B.getX_Cur() + rect_B.getW())) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		case HH:
			
			if (rect_C.getX_Cur() >= (rect_B.getX_Cur() + rect_B.getW())) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		case VH:
		case VV:
		
			return false;
		
//		defalult: return false;
			
		}

		return false;
		
	}//overWrap_on_A__B_at_A_UL_C_at_A_UR
	
	private static boolean 
	overWrap_on_A__Bat_AUL_Cat_ALL
	(Rect rect_B, Rect rect_C) {
		// TODO Auto-generated method stub

		switch(rect_B.getOrien()) {
		
		case VH:
			
			return overWrap_on_A__Bat_AUL_Cat_ALL_Borien_VH(rect_B, rect_C);
		
		}
		
		return false;
		
	}//overWrap_on_A__Bat_AUL_Cat_ALL

	private static boolean 
	overWrap_on_A__Bat_AUL_Cat_ALR
	(Rect rect_B, Rect rect_C) {
		// TODO Auto-generated method stub
		
		switch(rect_B.getOrien()) {
		
		case VH:
			
			return overWrap_on_A__Bat_AUL_Cat_ALR_Borien_VH(rect_B, rect_C);
			
		}
		
		return false;
		
	}//overWrap_on_A__Bat_AUL_Cat_ALL
	
	private static boolean 
	overWrap_on_A__Bat_AUL_Cat_AUR
	(Rect rect_B, Rect rect_C) {
		// TODO Auto-generated method stub
		
		switch(rect_B.getOrien()) {
		
		case VH:
			
			return overWrap_on_A__Bat_AUL_Cat_AUR_Borien_VH(rect_B, rect_C);
			
		}
		
		return false;
		
	}//overWrap_on_A__Bat_AUL_Cat_AUR
	
	
	private static boolean 
	overWrap_on_A__Bat_AUL_Cat_ALL_Borien_VH
	(Rect rect_B, Rect rect_C) {
		
		String text, fname;
		int line_Num;		
		
		//log
		text = String.format(Locale.JAPAN, "overWrap_on_A__Bat_AUL_Cat_ALL_Borien_VH\n");
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

		////////////////////////////////

		// dispatch

		////////////////////////////////
		switch(rect_C.getOrien()) {
		
		case HV:
		case HH: return false;
		
		case VH:
		case VV:
		
			if (rect_C.getY_Cur() < (rect_B.getY_Cur() + rect_B.getH())) {
				
				return true;
				
			} else {
				
				return false;
				
			}
			
		}//switch(rect_C.getOrien())
		
		return false;
		
	}//overWrap_on_A__Bat_AUL_Cat_ALL_Borien_VH
	
	private static boolean 
	overWrap_on_A__Bat_AUL_Cat_ALR_Borien_VH
	(Rect rect_B, Rect rect_C) {
		
		String text, fname;
		int line_Num;		
		
		//log
		text = String.format(Locale.JAPAN, "overWrap_on_A__Bat_AUL_Cat_ALL_Borien_VH\n");
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		
		////////////////////////////////
		
		// dispatch
		
		////////////////////////////////
		switch(rect_C.getOrien()) {
		
		case VH:
		case VV: return false;
			
		case HV: return false;
		case HH: return false;
		
			
		}//switch(rect_C.getOrien())
		
		return false;
		
	}//overWrap_on_A__Bat_AUL_Cat_ALR_Borien_VH
	
	private static boolean 
	overWrap_on_A__Bat_AUL_Cat_AUR_Borien_VH
	(Rect rect_B, Rect rect_C) {
		
		String text, fname;
		int line_Num;		
		
		//log
		text = String.format(Locale.JAPAN, "overWrap_on_A__Bat_AUL_Cat_AUR_Borien_VH\n");
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		
		////////////////////////////////
		
		// dispatch
		
		////////////////////////////////
		switch(rect_C.getOrien()) {
		
		case VH:
		case VV:
		case HV:
		case HH: return false;
		
		
		}//switch(rect_C.getOrien())
		
		return false;
		
	}//overWrap_on_A__Bat_AUL_Cat_AUR_Borien_VH

	public static Rect 
	get_Rect_Z
	(Rect rect_A, Rect rect_B, Rect rect_C) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// get: sizes

		////////////////////////////////
		int x1 = Methods.smallest(
					new int[]{rect_A.getX_Cur(), rect_B.getX_Cur(), rect_C.getX_Cur()});
		
		int y1 = Methods.smallest(
					new int[]{rect_A.getY_Cur(), rect_B.getY_Cur(), rect_C.getY_Cur()});
		
		int x2 = Methods.largest(new int[]{
						rect_A.getX_Cur() + rect_A.getW(), 
						rect_B.getX_Cur() + rect_B.getW(), 
						rect_C.getX_Cur() + rect_C.getW()
				});
		
		int y2 = Methods.largest(new int[]{
						rect_A.getY_Cur() + rect_A.getH(), 
						rect_B.getY_Cur() + rect_B.getH(), 
						rect_C.getY_Cur() + rect_C.getH()
//						rect_A.getY_Cur() + rect_A.getW(), 
//						rect_B.getY_Cur() + rect_B.getW(), 
//						rect_C.getY_Cur() + rect_C.getW()
				});

		int w = x2 - x1;
		int h = y2 - y1;
		
		////////////////////////////////

		// rect

		////////////////////////////////
		Rect r = new Rect("rect_Z");
		
		r.setX_Cur(x1);
		r.setY_Cur(y1);
		
		r.setW(w);
		r.setW_Orig(w);
		
		r.setH(h);
		r.setH_Orig(h);
		
		return r;
		
	}//get_Rect_Z

	
	public static LineStates
	get_LineStates
	(Rect rect_Z, Rect[] rects, Lines line) {
		// TODO Auto-generated method stub
		////////////////////////////////

		// dispatch

		////////////////////////////////
		switch(line) {
		
		case LX1: return Methods._get_LineStates__LX1(rect_Z, rects);
		case LY1: return Methods._get_LineStates__LY1(rect_Z, rects);
		
		case LX2: return Methods._get_LineStates__LX2(rect_Z, rects);
		case LY2: return Methods._get_LineStates__LY2(rect_Z, rects);
					
//		case LX2: return Methods._get_LineStates__LX2(rect_Z, rects);
//		case LY2: return Methods._get_LineStates__LX2(rect_Z, rects);
		
		default: return CONS.Admin.LineStates.NONE;
			
		}
		
//		return null;
		
	}//get_LineStatus

	private static LineStates 
	_get_LineStates__LY1
	(Rect rect_Z, Rect[] rects) {
		// TODO Auto-generated method stub

		String text, fname;
		int line_Num;
		
		////////////////////////////////

		// get: rects whose y1 is equal to that of rect Z

		////////////////////////////////
		List<Rect> list_Rects = new ArrayList<Rect>();
		
		int z_X1 = rect_Z.getX_Cur();
		
		for (Rect rect : rects) {
			
			if (rect.getX_Cur() == z_X1) {
				
				list_Rects.add(rect);
				
			}
			
		}
		
//		//log
//		text = String.format(Locale.JAPAN, "list_Rects.size => %d\n", list_Rects.size());
//		
//		fname = Thread.currentThread().getStackTrace()[1].getFileName();
//		
//		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
//		
//		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		
		////////////////////////////////

		// dispatch

		////////////////////////////////
		int numOf_Rects = list_Rects.size();
		
		//log
		text = String.format(Locale.JAPAN, "numOf_Rects => %d\n", numOf_Rects);
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

		switch(numOf_Rects) {
		
		case 0: return CONS.Admin.LineStates.NONE;
		case 1: return Methods._get_LineStates__LY1__Case_1(rect_Z, list_Rects.get(0));
//		case 2: return CONS.Admin.LineStates.NONE;
		case 2: return Methods._get_LineStates__LY1__Case_2(rect_Z, list_Rects);
		case 3: return CONS.Admin.LineStates.MATCH;
		
		default: return CONS.Admin.LineStates.UNKNOWN;
		
		}
		
	}//_get_LineStates__LY1

	private static LineStates 
	_get_LineStates__LY2
	(Rect rect_Z, Rect[] rects) {
		// TODO Auto-generated method stub
		
		String text, fname;
		int line_Num;
		
		////////////////////////////////
		
		// get: rects whose y1 is equal to that of rect Z
		
		////////////////////////////////
		List<Rect> list_Rects = new ArrayList<Rect>();
		
		int z_X2 = rect_Z.getX_Cur() + rect_Z.getW();
		
		int r_X2;
		
		for (Rect rect : rects) {
			
			r_X2 = rect.getX_Cur() + rect.getW();
			
			if (r_X2 == z_X2) {
				
				list_Rects.add(rect);
				
			}
			
		}
		
//		//log
//		text = String.format(Locale.JAPAN, "list_Rects.size => %d\n", list_Rects.size());
//		
//		fname = Thread.currentThread().getStackTrace()[1].getFileName();
//		
//		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
//		
//		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		
		////////////////////////////////
		
		// dispatch
		
		////////////////////////////////
		int numOf_Rects = list_Rects.size();
		
		//log
		text = String.format(Locale.JAPAN, "numOf_Rects => %d\n", numOf_Rects);
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		
		switch(numOf_Rects) {
		
		case 0: return CONS.Admin.LineStates.NONE;
		case 1: return Methods._get_LineStates__LY2__Case_1(rect_Z, list_Rects.get(0));
//		case 1: return Methods._get_LineStates__LY1__Case_1(rect_Z, list_Rects.get(0));
//		case 2: return CONS.Admin.LineStates.NONE;
//		case 2: return CONS.Admin.LineStates.UNKNOWN;
//		case 2: return Methods._get_LineStates__LY1__Case_2(rect_Z, list_Rects);
		case 2: return Methods._get_LineStates__LY2__Case_2(rect_Z, list_Rects);
		case 3: return CONS.Admin.LineStates.MATCH;
		
		default: return CONS.Admin.LineStates.UNKNOWN;
		
		}
		
	}//_get_LineStates__LY2
	
	private static LineStates 
	_get_LineStates__LX1
	(Rect rect_Z, Rect[] rects) {
		// TODO Auto-generated method stub
		
		String text, fname;
		int line_Num;
		
		////////////////////////////////
		
		// get: rects whose y1 is equal to that of rect Z
		
		////////////////////////////////
		List<Rect> list_Rects = new ArrayList<Rect>();
		
		int z_Y1 = rect_Z.getY_Cur();
		
		for (Rect rect : rects) {
			
			if (rect.getY_Cur() == z_Y1) {
				
				list_Rects.add(rect);
				
			}
			
		}
		
		//log
		text = String.format(Locale.JAPAN, "list_Rects.size => %d\n", list_Rects.size());
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		
		////////////////////////////////
		
		// dispatch
		
		////////////////////////////////
		int numOf_Rects = list_Rects.size();
		
		//log
		text = String.format(Locale.JAPAN, "numOf_Rects => %d\n", numOf_Rects);
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		
		switch(numOf_Rects) {
		
		case 0: return CONS.Admin.LineStates.NONE;
		case 1: return Methods._get_LineStates__LX1__Case_1(rect_Z, list_Rects.get(0));
		case 2: return Methods._get_LineStates__LX1__Case_2(rect_Z, list_Rects);
		case 3: return CONS.Admin.LineStates.MATCH;
		
		default: return CONS.Admin.LineStates.UNKNOWN;
		
		}
		
	}//_get_LineStates__LX1
	
	private static LineStates 
	_get_LineStates__LX2
	(Rect rect_Z, Rect[] rects) {
		// TODO Auto-generated method stub
		
		String text, fname;
		int line_Num;
		
		////////////////////////////////
		
		// get: rects whose y1 is equal to that of rect Z
		
		////////////////////////////////
		List<Rect> list_Rects = new ArrayList<Rect>();
		
		int z_Y2 = rect_Z.getY_Cur() + rect_Z.getH();
		
		int r_Y2;
		
		for (Rect rect : rects) {
			
			r_Y2 = rect.getY_Cur() + rect.getH();
			
			if (r_Y2 == z_Y2) {
				
				list_Rects.add(rect);
				
			}
			
		}
		
		////////////////////////////////
		
		// dispatch
		
		////////////////////////////////
		int numOf_Rects = list_Rects.size();
		
		//log
		text = String.format(Locale.JAPAN, "numOf_Rects => %d\n", numOf_Rects);
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		
		switch(numOf_Rects) {
		
		case 0: return CONS.Admin.LineStates.NONE;
		case 1: return Methods._get_LineStates__LX2__Case_1(rect_Z, list_Rects.get(0));
//		case 2: return CONS.Admin.LineStates.UNKNOWN;
//		case 2: return Methods._get_LineStates__LX1__Case_2(rect_Z, list_Rects);
		case 2: return Methods._get_LineStates__LX2__Case_2(rect_Z, list_Rects);
		case 3: return CONS.Admin.LineStates.MATCH;
		
		default: return CONS.Admin.LineStates.UNKNOWN;
		
		}
		
	}//_get_LineStates__LX2
	
	private static LineStates 
	_get_LineStates__LX1__Case_2
	(Rect rect_Z, List<Rect> list_Rects) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// width => same?

		////////////////////////////////
		int z_W = rect_Z.getW();
		int r1_W = list_Rects.get(0).getW();
		int r2_W = list_Rects.get(1).getW();

		//log
		String text, fname; int line_Num;
		
		text = String.format(Locale.JAPAN, 
						"r1 => %s / r2 => %s\n", 
						list_Rects.get(0).getRect_Name(), 
						list_Rects.get(1).getRect_Name());
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

		
		int sum_W = r1_W + r2_W;
		
		if (z_W == sum_W) {
			
			return CONS.Admin.LineStates.MATCH;
		}
		
//		} else {
//		 	
//			return CONS.Admin.LineStates.UNKNOWN;
//			
//		}
		
		////////////////////////////////

		// Z.w > (r1.w + r2.w)

		////////////////////////////////
		////////////////////////////////

		// data

		////////////////////////////////
		Rect r1 = list_Rects.get(0);
		Rect r2 = list_Rects.get(1);
		
		// X1
		int z_X1 = rect_Z.getX_Cur();
		int r1_X1 = r1.getX_Cur();
		int r2_X1 = r2.getX_Cur();
		
		// X2
		int z_X2 = rect_Z.getX_Cur() + rect_Z.getW();
		int r1_X2 = r1.getX_Cur() + r1.getW();
		int r2_X2 = r2.getX_Cur() + r2.getW();
		
		// Y1
		int z_Y1 = rect_Z.getY_Cur();
		int r1_Y1 = r1.getY_Cur();
		int r2_Y1 = r2.getY_Cur();
		
		// Points
		// Z
		Point p_ZUL = new  Point(z_X1, z_Y1);
		Point p_ZUR = new  Point(z_X2, z_Y1);
		
		// B
		Point p_BUL = new  Point(r1_X1, r1_Y1);
		Point p_BUR = new  Point(r1_X2, r1_Y1);
		
		// C
		Point p_CUL = new  Point(r2_X1, r2_Y1);
		Point p_CUR = new  Point(r2_X2, r2_Y1);

		////////////////////////////////

		// judge

		////////////////////////////////
		if (Methods.isSame_Point(p_ZUL, p_BUL)
				&& Methods.isSame_Point(p_ZUR, p_CUR)) {
			
			return CONS.Admin.LineStates.BOTH_X;
			
		} else if (Methods.isSame_Point(p_ZUL, p_CUL)
				&& Methods.isSame_Point(p_ZUR, p_BUR)) {
			
			return CONS.Admin.LineStates.BOTH_X;
			
		} else if (Methods.isSame_Point(p_ZUL, p_BUL)
				&& Methods.isSame_Point(p_BUR, p_CUL)) {
			
			return CONS.Admin.LineStates.LEFT;
			
		} else if (Methods.isSame_Point(p_ZUL, p_CUL)
				&& Methods.isSame_Point(p_CUR, p_BUL)) {
			
			return CONS.Admin.LineStates.LEFT;
			
		} else if (Methods.isSame_Point(p_ZUR, p_CUR)
				&& Methods.isSame_Point(p_BUR, p_CUL)) {
			
			return CONS.Admin.LineStates.RIGHT;
			
		} else if (Methods.isSame_Point(p_ZUR, p_BUR)
				&& Methods.isSame_Point(p_CUR, p_BUL)) {
			
			return CONS.Admin.LineStates.RIGHT;
			
		} else {
			
			return CONS.Admin.LineStates.UNKNOWN;
			
		}
		
//		return CONS.Admin.LineStates.UNKNOWN;
		
	}//_get_LineStates__LX1__Case_2

	private static LineStates 
	_get_LineStates__LX2__Case_2
	(Rect rect_Z, List<Rect> list_Rects) {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// width => same?
		
		////////////////////////////////
		int z_H = rect_Z.getW();
		int r1_H = list_Rects.get(0).getW();
		int r2_H = list_Rects.get(1).getW();
		
		//log
		String text, fname; int line_Num;
		
		text = String.format(Locale.JAPAN, 
				"r1 => %s / r2 => %s\n", 
				list_Rects.get(0).getRect_Name(), 
				list_Rects.get(1).getRect_Name());
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		
		
		int sum_W = r1_H + r2_H;
		
		if (z_H == sum_W) {
			
			return CONS.Admin.LineStates.MATCH;
		}
		
//		} else {
//		 	
//			return CONS.Admin.LineStates.UNKNOWN;
//			
//		}
		
		////////////////////////////////
		
		// Z.w > (r1.w + r2.w)
		
		////////////////////////////////
		////////////////////////////////
		
		// data
		
		////////////////////////////////
		Rect r1 = list_Rects.get(0);
		Rect r2 = list_Rects.get(1);
		
		// X1
		int z_X1 = rect_Z.getX_Cur();
		int r1_X1 = r1.getX_Cur();
		int r2_X1 = r2.getX_Cur();
		
		// X2
		int z_X2 = rect_Z.getX_Cur() + rect_Z.getW();
		int r1_X2 = r1.getX_Cur() + r1.getW();
		int r2_X2 = r2.getX_Cur() + r2.getW();
		
		// Y2
		int z_Y2 = rect_Z.getY_Cur() + rect_Z.getH();
		int r1_Y2 = r1.getY_Cur() + r1.getH();
		int r2_Y2 = r2.getY_Cur() + r2.getH();
		
		// Points
		// Z
		Point p_ZUL = new  Point(z_X1, z_Y2);
		Point p_ZUR = new  Point(z_X2, z_Y2);
		
		// B
		Point p_BUL = new  Point(r1_X1, r1_Y2);
		Point p_BUR = new  Point(r1_X2, r1_Y2);
		
		// C
		Point p_CUL = new  Point(r2_X1, r2_Y2);
		Point p_CUR = new  Point(r2_X2, r2_Y2);
		
		////////////////////////////////
		
		// judge
		
		////////////////////////////////
		if (Methods.isSame_Point(p_ZUL, p_BUL)
				&& Methods.isSame_Point(p_ZUR, p_CUR)) {
			
			return CONS.Admin.LineStates.BOTH_X;
			
		} else if (Methods.isSame_Point(p_ZUL, p_CUL)
				&& Methods.isSame_Point(p_ZUR, p_BUR)) {
			
			return CONS.Admin.LineStates.BOTH_X;
			
		} else if (Methods.isSame_Point(p_ZUL, p_BUL)
				&& Methods.isSame_Point(p_BUR, p_CUL)) {
			
			return CONS.Admin.LineStates.LEFT;
			
		} else if (Methods.isSame_Point(p_ZUL, p_CUL)
				&& Methods.isSame_Point(p_CUR, p_BUL)) {
			
			return CONS.Admin.LineStates.LEFT;
			
		} else if (Methods.isSame_Point(p_ZUR, p_CUR)
				&& Methods.isSame_Point(p_BUR, p_CUL)) {
			
			return CONS.Admin.LineStates.RIGHT;
			
		} else if (Methods.isSame_Point(p_ZUR, p_BUR)
				&& Methods.isSame_Point(p_CUR, p_BUL)) {
			
			return CONS.Admin.LineStates.RIGHT;
			
		} else {
			
			return CONS.Admin.LineStates.UNKNOWN;
			
		}
		
//		return CONS.Admin.LineStates.UNKNOWN;
		
	}//_get_LineStates__LX2__Case_2
	
	private static LineStates 
	_get_LineStates__LY1__Case_2
	(Rect rect_Z, List<Rect> list_Rects) {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// width => same?
		
		////////////////////////////////
		int z_H = rect_Z.getH();
		int r1_H = list_Rects.get(0).getH();
		int r2_H = list_Rects.get(1).getH();
		
		//log
		String text, fname; int line_Num;
		
		text = String.format(Locale.JAPAN, 
				"r1 => %s / r2 => %s\n", 
				list_Rects.get(0).getRect_Name(), 
				list_Rects.get(1).getRect_Name());
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		
		
		int sum_H = r1_H + r2_H;
		
		if (z_H == sum_H) {
			
			return CONS.Admin.LineStates.MATCH;
		}
		
//		} else {
//		 	
//			return CONS.Admin.LineStates.UNKNOWN;
//			
//		}
		
		////////////////////////////////
		
		// Z.w > (r1.w + r2.w)
		
		////////////////////////////////
		////////////////////////////////
		
		// data
		
		////////////////////////////////
		Rect r1 = list_Rects.get(0);
		Rect r2 = list_Rects.get(1);
		
		// X1
		int z_X1 = rect_Z.getX_Cur();
		int r1_X1 = r1.getX_Cur();
		int r2_X1 = r2.getX_Cur();
		
		// Y1
		int z_Y1 = rect_Z.getY_Cur();
		int r1_Y1 = r1.getY_Cur();
		int r2_Y1 = r2.getY_Cur();
		
		// Y2
		int z_Y2 = rect_Z.getY_Cur() + rect_Z.getH();
		int r1_Y2 = r1.getY_Cur() + r1.getH();
		int r2_Y2 = r2.getY_Cur() + r2.getH();
		
		// Points
		// Z
		Point p_ZUL = new  Point(z_X1, z_Y1);
		Point p_ZLL = new  Point(z_X1, z_Y2);
		
		// R1
		Point p_R1UL = new  Point(r1_X1, r1_Y1);
		Point p_R1LL = new  Point(r1_X1, r1_Y2);
//		Point p_BUR = new  Point(r1_X2, r1_Y1);
		
		// C
		Point p_R2UL = new  Point(r2_X1, r2_Y1);
		Point p_R2LL = new  Point(r2_X1, r2_Y2);
//		Point p_CUR = new  Point(r2_X2, r2_Y1);
		
		////////////////////////////////
		
		// judge
		
		////////////////////////////////
		if (Methods.isSame_Point(p_ZUL, p_R2UL)
				&& Methods.isSame_Point(p_ZLL, p_R1LL)) {
			
			return CONS.Admin.LineStates.BOTH_Y;
			
		} else if (Methods.isSame_Point(p_ZUL, p_R1UL)
				&& Methods.isSame_Point(p_ZLL, p_R2LL)) {
			
			return CONS.Admin.LineStates.BOTH_Y;
			
		} else if (Methods.isSame_Point(p_ZLL, p_R1LL)
				&& Methods.isSame_Point(p_R1UL, p_R2LL)) {
			
			return CONS.Admin.LineStates.LOWER;
			
		} else if (Methods.isSame_Point(p_ZLL, p_R2LL)
				&& Methods.isSame_Point(p_R2UL, p_R1LL)) {
			
			return CONS.Admin.LineStates.LOWER;
			
		} else if (Methods.isSame_Point(p_ZUL, p_R1UL)
				&& Methods.isSame_Point(p_R1LL, p_R2UL)) {
			
			return CONS.Admin.LineStates.UPPER;
			
		} else if (Methods.isSame_Point(p_ZUL, p_R2UL)
				&& Methods.isSame_Point(p_R2LL, p_R1UL)) {
			
			return CONS.Admin.LineStates.UPPER;
			
		} else {
			
			return CONS.Admin.LineStates.UNKNOWN;
			
		}
		
//		return CONS.Admin.LineStates.UNKNOWN;
		
	}//_get_LineStates__LY1__Case_2
	
	private static LineStates 
	_get_LineStates__LY2__Case_2
	(Rect rect_Z, List<Rect> list_Rects) {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// width => same?
		
		////////////////////////////////
		int z_H = rect_Z.getH();
		int r1_H = list_Rects.get(0).getH();
		int r2_H = list_Rects.get(1).getH();
		
		//log
		String text, fname; int line_Num;
		
		text = String.format(Locale.JAPAN, 
				"r1 => %s / r2 => %s\n", 
				list_Rects.get(0).getRect_Name(), 
				list_Rects.get(1).getRect_Name());
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		
		
		int sum_H = r1_H + r2_H;
		
		if (z_H == sum_H) {
			
			return CONS.Admin.LineStates.MATCH;
		}
		
//		} else {
//		 	
//			return CONS.Admin.LineStates.UNKNOWN;
//			
//		}
		
		////////////////////////////////
		
		// Z.w > (r1.w + r2.w)
		
		////////////////////////////////
		////////////////////////////////
		
		// data
		
		////////////////////////////////
		Rect r1 = list_Rects.get(0);
		Rect r2 = list_Rects.get(1);
		
		// X1
		int z_X2 = rect_Z.getX_Cur() + rect_Z.getW();
		int r1_X2 = r1.getX_Cur() + r1.getW();
		int r2_X2 = r2.getX_Cur() + r2.getW();
		
		// Y1
		int z_Y1 = rect_Z.getY_Cur();
		int r1_Y1 = r1.getY_Cur();
		int r2_Y1 = r2.getY_Cur();
		
		// Y2
		int z_Y2 = rect_Z.getY_Cur() + rect_Z.getH();
		int r1_Y2 = r1.getY_Cur() + r1.getH();
		int r2_Y2 = r2.getY_Cur() + r2.getH();
		
		// Points
		// Z
		Point p_ZUR = new  Point(z_X2, z_Y1);
		Point p_ZLR = new  Point(z_X2, z_Y2);
		
		// R1
		Point p_R1UR = new  Point(r1_X2, r1_Y1);
		Point p_R1LR = new  Point(r1_X2, r1_Y2);
		
		// R2
		Point p_R2UR = new  Point(r2_X2, r2_Y1);
		Point p_R2LR = new  Point(r2_X2, r2_Y2);
		
		////////////////////////////////
		
		// judge
		
		////////////////////////////////
		if (Methods.isSame_Point(p_ZUR, p_R2UR)
				&& Methods.isSame_Point(p_ZLR, p_R1LR)) {
			
			return CONS.Admin.LineStates.BOTH_Y;
			
		} else if (Methods.isSame_Point(p_ZUR, p_R1UR)
				&& Methods.isSame_Point(p_ZLR, p_R2LR)) {
			
			return CONS.Admin.LineStates.BOTH_Y;
			
		} else if (Methods.isSame_Point(p_ZLR, p_R1LR)
				&& Methods.isSame_Point(p_R1UR, p_R2LR)) {
			
			return CONS.Admin.LineStates.LOWER;
			
		} else if (Methods.isSame_Point(p_ZLR, p_R2LR)
				&& Methods.isSame_Point(p_R2UR, p_R1LR)) {
			
			return CONS.Admin.LineStates.LOWER;
			
		} else if (Methods.isSame_Point(p_ZUR, p_R1UR)
				&& Methods.isSame_Point(p_R1LR, p_R2UR)) {
			
			return CONS.Admin.LineStates.UPPER;
			
		} else if (Methods.isSame_Point(p_ZUR, p_R2UR)
				&& Methods.isSame_Point(p_R2LR, p_R1UR)) {
			
			return CONS.Admin.LineStates.UPPER;
			
		} else {
			
			return CONS.Admin.LineStates.UNKNOWN;
			
		}
		
//		return CONS.Admin.LineStates.UNKNOWN;
		
	}//_get_LineStates__LY2__Case_2
	
	
	private static boolean 
	isSame_Point(Point p1, Point p2) {
	
		if (p1.x == p2.x && (p1.y == p2.y)) {
			
			return true;
			
		} else {
			
			return false;
			
		}
		
	}//is_SamePoint(Point p1, Point p2)
	
	private static LineStates 
	_get_LineStates__LX1__Case_1
	(Rect rect_Z, Rect rect) {
		// TODO Auto-generated method stub

		////////////////////////////////

		// width => same?

		////////////////////////////////
		int z_W = rect_Z.getW();
		int r_W = rect.getW();
		
		if (z_W == r_W) {
			
			return CONS.Admin.LineStates.MATCH;
		}
		
//		} else {
//			
//			return CONS.Admin.LineStates.UNKNOWN;
//			
//		}
		
		////////////////////////////////

		// not same

		////////////////////////////////
		int z_X1 = rect_Z.getX_Cur();
		int r_X1 = rect.getX_Cur();
		
		int z_Y1 = rect_Z.getY_Cur();
		int r_Y1 = rect.getY_Cur();
		
		int z_X2 = rect_Z.getX_Cur() + rect_Z.getW();
		int r_X2 = rect.getX_Cur() + rect.getW();
		
		if ((z_X1 == r_X1) && (z_Y1 == r_Y1)) {	// Z_UL = R_UL
			
			return CONS.Admin.LineStates.LEFT;
			
		} else if ((z_X2 == r_X2) && (z_Y1 == r_Y1)) {	// Z_UR = R_UR

			return CONS.Admin.LineStates.RIGHT;
			
		} else {	// line(R, x1) => at the middle of LX1
			
			return CONS.Admin.LineStates.MIDDLE_X;
			
		}
		
//		return CONS.Admin.LineStates.UNKNOWN;
		
	}//_get_LineStates__LX1__Case_1

	private static LineStates 
	_get_LineStates__LX2__Case_1
	(Rect rect_Z, Rect rect) {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// width => same?
		
		////////////////////////////////
		int z_W = rect_Z.getW();
		int r_W = rect.getW();
		
		if (z_W == r_W) {
			
			return CONS.Admin.LineStates.MATCH;
		}
		
//		} else {
//			
//			return CONS.Admin.LineStates.UNKNOWN;
//			
//		}
		
		////////////////////////////////
		
		// not same
		
		////////////////////////////////
		int z_X1 = rect_Z.getX_Cur();
		int r_X1 = rect.getX_Cur();
		
		int z_Y2 = rect_Z.getY_Cur() + rect_Z.getH();
		int r_Y2 = rect.getY_Cur() + rect.getH();
		
		int z_X2 = rect_Z.getX_Cur() + rect_Z.getW();
		int r_X2 = rect.getX_Cur() + rect.getW();
		
		Point p_ZLL = new Point(z_X1, z_Y2);
		Point p_ZLR = new Point(z_X2, z_Y2);
		
		Point p_RLL = new Point(r_X1, r_Y2);
		Point p_RLR = new Point(r_X2, r_Y2);
		
		if (Methods.isSame_Point(p_ZLL, p_RLL)) {	// Z_UL = R_UL
			
			return CONS.Admin.LineStates.LEFT;
			
		} else if (Methods.isSame_Point(p_ZLR, p_RLR)) {	// Z_UR = R_UR
			
			return CONS.Admin.LineStates.RIGHT;
			
		} else {	// line(R, x1) => at the middle of LX1
			
			return CONS.Admin.LineStates.MIDDLE_X;
			
		}
		
//		return CONS.Admin.LineStates.UNKNOWN;
		
	}//_get_LineStates__LX2__Case_1
	
	private static LineStates 
	_get_LineStates__LY1__Case_1
	(Rect rect_Z, Rect rect) {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// width => same?
		
		///////////////////////////
		int z_H = rect_Z.getH();
		int r_H = rect.getH();
		
		//log
		String text, fname; int line_Num;
		
		text = String.format(Locale.JAPAN, "rect => %s / z_H = %d, r_H = %d\n", rect.getRect_Name(), z_H, r_H);
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

		
		if (z_H == r_H) {
			
			return CONS.Admin.LineStates.MATCH;
		}
		
//		} else {
//			
//			return CONS.Admin.LineStates.UNKNOWN;
//			
//		}
		
		////////////////////////////////
		
		// not same
		
		////////////////////////////////
		////////////////////////////////

		// data

		////////////////////////////////
		Rect r1 = rect;
		
		// X1
		int z_X1 = rect_Z.getX_Cur();
		int r1_X1 = r1.getX_Cur();
		
		// Y1
		int z_Y1 = rect_Z.getY_Cur();
		int r1_Y1 = r1.getY_Cur();
		
		// Y2
		int z_Y2 = rect_Z.getY_Cur() + rect_Z.getH();
		int r1_Y2 = r1.getY_Cur() + r1.getH();
		
		// Points
		// Z
		Point p_ZUL = new  Point(z_X1, z_Y1);
		Point p_ZLL = new  Point(z_X1, z_Y2);
		
		// B
		Point p_RUL = new  Point(r1_X1, r1_Y1);
		Point p_RLL = new  Point(r1_X1, r1_Y2);

		////////////////////////////////

		// judge

		////////////////////////////////
		if (Methods.isSame_Point(p_ZUL, p_RUL)) {
			
			return CONS.Admin.LineStates.UPPER;
			
		} else if (Methods.isSame_Point(p_ZLL, p_RLL)) {
			
			return CONS.Admin.LineStates.LOWER;
			
		} else {
			
			return CONS.Admin.LineStates.MIDDLE_Y;
//			return CONS.Admin.LineStates.UNKNOWN;
			
		}
		
//		if ((z_X1 == r_X1) && (z_Y1 == r_Y1)) {	// Z_UL = R_UL
//			
//			return CONS.Admin.LineStates.LEFT;
//			
//		} else if ((z_X2 == r_X2) && (z_Y1 == r_Y1)) {	// Z_UR = R_UR
//			
//			return CONS.Admin.LineStates.RIGHT;
//			
//		} else {	// line(R, x1) => at the middle of LX1
//			
//			return CONS.Admin.LineStates.MIDDLE_Y;
//			
//		}
		
//		return CONS.Admin.LineStates.UNKNOWN;
		
	}//_get_LineStates__LY1__Case_1
	
	private static LineStates 
	_get_LineStates__LY2__Case_1
	(Rect rect_Z, Rect rect) {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// width => same?
		
		///////////////////////////
		int z_H = rect_Z.getH();
		int r_H = rect.getH();
		
		//log
		String text, fname; int line_Num;
		
		text = String.format(Locale.JAPAN, "rect => %s / z_H = %d, r_H = %d\n", rect.getRect_Name(), z_H, r_H);
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		
		
		if (z_H == r_H) {
			
			return CONS.Admin.LineStates.MATCH;
		}
		
//		} else {
//			
//			return CONS.Admin.LineStates.UNKNOWN;
//			
//		}
		
		////////////////////////////////
		
		// not same
		
		////////////////////////////////
		////////////////////////////////
		
		// data
		
		////////////////////////////////
		Rect r1 = rect;
		
		// X2
		int z_X2 = rect_Z.getX_Cur() + rect_Z.getW();
		int r1_X2 = r1.getX_Cur() + r1.getW();
		
		// Y1
		int z_Y1 = rect_Z.getY_Cur();
		int r1_Y1 = r1.getY_Cur();
		
		// Y2
		int z_Y2 = rect_Z.getY_Cur() + rect_Z.getH();
		int r1_Y2 = r1.getY_Cur() + r1.getH();
		
		// Points
		// Z
		Point p_ZUR = new  Point(z_X2, z_Y1);
		Point p_ZLR = new  Point(z_X2, z_Y2);
		
		// r1
		Point p_RUR = new  Point(r1_X2, r1_Y1);
		Point p_RLR = new  Point(r1_X2, r1_Y2);
		
		////////////////////////////////
		
		// judge
		
		////////////////////////////////
		if (Methods.isSame_Point(p_ZUR, p_RUR)) {
			
			return CONS.Admin.LineStates.UPPER;
			
		} else if (Methods.isSame_Point(p_ZLR, p_RLR)) {
			
			return CONS.Admin.LineStates.LOWER;
			
		} else {
			
			return CONS.Admin.LineStates.MIDDLE_Y;
//			return CONS.Admin.LineStates.UNKNOWN;
			
		}
		
//		if ((z_X1 == r_X1) && (z_Y1 == r_Y1)) {	// Z_UL = R_UL
//			
//			return CONS.Admin.LineStates.LEFT;
//			
//		} else if ((z_X2 == r_X2) && (z_Y1 == r_Y1)) {	// Z_UR = R_UR
//			
//			return CONS.Admin.LineStates.RIGHT;
//			
//		} else {	// line(R, x1) => at the middle of LX1
//			
//			return CONS.Admin.LineStates.MIDDLE_Y;
//			
//		}
		
//		return CONS.Admin.LineStates.UNKNOWN;
		
	}//_get_LineStates__LY2__Case_1
	
	/*******************************

		@return
		Object[] => {Point, NodeTypes}

	 *******************************/
	public static Map<NodeNames, Object[]> 
//	public static Map<NodeNames, CornerTypes> 
	get_Map_AllNodes_XY_and_CornerTypes
	(List<NodeNames> list_NodeNames, Rect rect_A, Rect rect_B, Rect rect_C) {
		// TODO Auto-generated method stub
		
		//log
		String text, fname; int line_Num;

		////////////////////////////////

		// get: all nodes map

		////////////////////////////////
		Map<NodeNames, Point> map_AllNodes_XY = 
						Methods.get_Map_AllNodes(list_NodeNames, rect_A, rect_B, rect_C);

		////////////////////////////////

		// get: map => all nodes and corner types

		////////////////////////////////
		Map<NodeNames, Object[]> map_AllNodes_XY_CornerTypes = 
						Methods.get_Map_AllNodes_CornerTypes(
									map_AllNodes_XY, rect_A, rect_B, rect_C);
		
		//log
		text = String.format(Locale.JAPAN, "get_Map_AllNodes_XY_CornerTypes => done\n");
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

		return null;
		
	}//get_Map_AllNodes_XY_CornerTypes

	private static Map<NodeNames, Object[]> 
	get_Map_AllNodes_CornerTypes
	(Map<NodeNames, Point> map_AllNodes_XY, 
			Rect rect_A, Rect rect_B, Rect rect_C) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// try: node at index 0

		////////////////////////////////
		Iterator it = map_AllNodes_XY.keySet().iterator();
//		NodeNames name = (NodeNames)it.next();
		
		// iterate
		NodeNames name = null;
		
		String text, fname; int line_Num;
		String name_String;
		
		//log
		text = String.format(Locale.JAPAN, "map_AllNodes_XY.size => %d\n", map_AllNodes_XY.keySet().size());
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

		
		////////////////////////////////

		// build: list

		////////////////////////////////
		Map<NodeNames, Object[]> map = new HashMap<NodeNames, Object[]>();

		CornerTypes type = null;
		
		Point point = null;

		while(it.hasNext()) {

			// get name
			name = (NodeNames)it.next();
			
			//log
			text = String.format(Locale.JAPAN, "name => %s\n", name.toString());
			
			fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

			////////////////////////////////

			// dispatch

			////////////////////////////////
			name_String = name.toString();
			
//			if (name_String.startsWith("C")) {
				
			switch(name) {
			
			case C_UL: 
			case C_UR: 
			case C_LL: //return null;
			
			case C_LR: 
				
//					type = Methods.get_CornerTypes__Node_C(
				type = Methods.get_CornerTypes__Node_ABC(
										map_AllNodes_XY, 
										name, 
										rect_A, rect_B 
										);
//				map_AllNodes_XY.get(name), name, rect_A, rect_B);
				
				point = map_AllNodes_XY.get(name);
				
				map.put(name, new Object[]{point, type});
		
				break;
				
			case B_UL: 
			case B_UR: 
			case B_LL: //return null;
				
			case B_LR: 
				
//					type = Methods.get_CornerTypes__Node_C(
				type = Methods.get_CornerTypes__Node_ABC(
						map_AllNodes_XY, name, rect_A, rect_C);
//				map_AllNodes_XY.get(name), name, rect_A, rect_B);
				
				point = map_AllNodes_XY.get(name);
				
				map.put(name, new Object[]{point, type});
				
				break;
				
			default:
				
				point = map_AllNodes_XY.get(name);
				
				type = null;
				
				map.put(name, new Object[]{point, type});
				
				break;
				
			}
				
//			} else {
				
//				point = map_AllNodes_XY.get(name);
//
//				type = null;
//				
//				map.put(name, new Object[]{point, type});
				
//			}
			
		}//while(it.hasNext())
		
		//log
		text = String.format(Locale.JAPAN, "map.size() => %d\n", map.size());
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

		it = map_AllNodes_XY.keySet().iterator();
		
		while(it.hasNext()) {

			// get name
			name = (NodeNames)it.next();
			
			type = (CornerTypes) map.get(name)[1];
			
			if (type != null) {
				
				text = String.format(Locale.JAPAN, 
						"name => %s, corner type => %s\n", 
						name, type.toString());
				
			} else {
				
				text = String.format(Locale.JAPAN, 
						"name => %s, corner type => null\n", 
						name);
				
			}
			
			//log
			
			fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
			
		}		
		
		return null;
		
	}//get_Map_AllNodes
	

//	private static Map<NodeNames, Object[]> 
	private static CornerTypes 
	get_CornerTypes__Node_C
	(Point point, NodeNames name, Rect rect_A, Rect rect_B) {
		// TODO Auto-generated method stub
		////////////////////////////////

		// test

		////////////////////////////////
		switch(name) {
		
		case C_LR: return Methods._get_CornerTypes__Node_C__CLR(point, name, rect_A, rect_B);
		case C_LL: return Methods._get_CornerTypes__Node_C__CLL(point, name, rect_A, rect_B);
		case C_UR: return Methods._get_CornerTypes__Node_C__CUR(point, name, rect_A, rect_B);
		case C_UL: return Methods._get_CornerTypes__Node_C__CUL(point, name, rect_A, rect_B);
		
		
		}
		
		//log
		String text, fname; int line_Num;
		
		text = String.format(Locale.JAPAN, "switch passed: name => %s\n", name.toString());
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

		return null;
		
	}//get_CornerTypes

	private static CornerTypes 
	get_CornerTypes__Node_ABC
	(Map<NodeNames, Point> map_AllNodes_XY, 
			NodeNames name, 
			Rect rect_1, Rect rect_2) {
		// TODO Auto-generated method stub
		
		Point point = map_AllNodes_XY.get(name);
		
		////////////////////////////////
		
		// test
		
		////////////////////////////////
		switch(name) {
		
		case A_LR: return Methods._get_CornerTypes__Node_C__CLR(point, name, rect_1, rect_2);
		case B_LR: return Methods._get_CornerTypes__Node_C__CLR(point, name, rect_1, rect_2);
		case C_LR: return Methods._get_CornerTypes__Node_C__CLR(point, name, rect_1, rect_2);
		
		case A_LL: return Methods._get_CornerTypes__Node_C__CLL(point, name, rect_1, rect_2);
		case B_LL: return Methods._get_CornerTypes__Node_C__CLL(point, name, rect_1, rect_2);
		case C_LL: return Methods._get_CornerTypes__Node_C__CLL(point, name, rect_1, rect_2);
		
		case A_UR: return Methods._get_CornerTypes__Node_C__CUR(point, name, rect_1, rect_2);
		case B_UR: return Methods._get_CornerTypes__Node_C__CUR(point, name, rect_1, rect_2);
		case C_UR: return Methods._get_CornerTypes__Node_C__CUR(point, name, rect_1, rect_2);
		
		case A_UL: return Methods._get_CornerTypes__Node_C__CUL(point, name, rect_1, rect_2);
		case B_UL: return Methods._get_CornerTypes__Node_C__CUL(point, name, rect_1, rect_2);
		case C_UL: return Methods._get_CornerTypes__Node_C__CUL(point, name, rect_1, rect_2);
		
		
		}
		
		//log
		String text, fname; int line_Num;
		
		text = String.format(Locale.JAPAN, "switch passed: name => %s\n", name.toString());
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		
		return null;
		
	}//get_CornerTypes__Node_ABC
	
//	private static Map<NodeNames, Object[]> 
	private static CornerTypes 
	_get_CornerTypes__Node_C__CLR
	(Point point, NodeNames name, Rect rect_1, Rect rect_2) {
		// TODO Auto-generated method stub
		////////////////////////////////

		// Y + 1

		////////////////////////////////
		CornerTypes type = Methods._get_CornerTypes__Node_C__Yplus1(point, rect_1, rect_2);
		
		// is in?
		if (type != null) {

			//log
			String text, fname; int line_Num;
			
			text = String.format(Locale.JAPAN, "node name => %s / type => %s\n", name.toString(), type.toString());
			
			fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

			return type;
			
//			Map<NodeNames, Object[]> map = new HashMap<NodeNames, Object[]>();
//			
//			map.put(name, new Object[]{point, type});
//			
//			return map;
			
//			return (new HashMap<name, Object[]>());
			
		}
		
		////////////////////////////////
		
		// X - 1
		
		////////////////////////////////
		type = Methods._get_CornerTypes__Node_C__Xplus1(point, rect_1, rect_2);
		
		// is in?
		if (type != null) {
			
			return type;
			
//			Map<NodeNames, Object[]> map = new HashMap<NodeNames, Object[]>();
//			
//			map.put(name, new Object[]{point, type});
//			
//			return map;
			
//			return (new HashMap<name, Object[]>());
			
		}
		
		//log
		String text, fname; int line_Num;

		if (type != null) {
			
			text = String.format(Locale.JAPAN, "type => %s\n", type.toString());
			
		} else {
			
			text = String.format(Locale.JAPAN, "type => null\n");
			
		}
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

		return null;
		
	}//_get_CornerTypes__Node_C__CLR
	
	private static CornerTypes 
	_get_CornerTypes__Node_C__CUR
	(Point point, NodeNames name, Rect rect_A, Rect rect_B) {
		// TODO Auto-generated method stub
		////////////////////////////////
		
		// Y - 1
		
		////////////////////////////////
		CornerTypes type = Methods._get_CornerTypes__Node_C__Yminus1(point, rect_A, rect_B);
		
		// is in?
		if (type != null) {

			//log
			String text, fname; int line_Num;
			
			text = String.format(Locale.JAPAN, "node name => %s / type => %s\n", name.toString(), type.toString());
			
			fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

			return type;
			
//			Map<NodeNames, Object[]> map = new HashMap<NodeNames, Object[]>();
//			
//			map.put(name, new Object[]{point, type});
//			
//			return map;
			
//			return (new HashMap<name, Object[]>());
			
		}
		
		////////////////////////////////
		
		// X + 1
		
		////////////////////////////////
		type = Methods._get_CornerTypes__Node_C__Xplus1(point, rect_A, rect_B);
		
		// is in?
		if (type != null) {
			
			return type;
			
//			Map<NodeNames, Object[]> map = new HashMap<NodeNames, Object[]>();
//			
//			map.put(name, new Object[]{point, type});
//			
//			return map;
			
//			return (new HashMap<name, Object[]>());
			
		}
		
		//log
		String text, fname; int line_Num;
		
		if (type != null) {
			
			text = String.format(Locale.JAPAN, "type => %s\n", type.toString());
			
		} else {
			
			text = String.format(Locale.JAPAN, "type => null\n");
			
		}
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		
		return null;
		
	}//_get_CornerTypes__Node_C__CLR
	
//	private static Map<NodeNames, Object[]> 
	private static CornerTypes 
	_get_CornerTypes__Node_C__CLL
	(Point point, NodeNames name, Rect rect_A, Rect rect_B) {
		// TODO Auto-generated method stub
		////////////////////////////////
		
		// Y + 1
		
		////////////////////////////////
		CornerTypes type = Methods._get_CornerTypes__Node_C__Yplus1(point, rect_A, rect_B);
		
		// is in?
		if (type != null) {

			//log
			String text, fname; int line_Num;
			
			text = String.format(Locale.JAPAN, "node name => %s / type => %s\n", name.toString(), type.toString());
			
			fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);


			return type;
			
//			Map<NodeNames, Object[]> map = new HashMap<NodeNames, Object[]>();
//			
//			map.put(name, new Object[]{point, type});
//			
//			return map;
			
//			return (new HashMap<name, Object[]>());
			
		}
		
		////////////////////////////////
		
		// X - 1
		
		////////////////////////////////
		type = Methods._get_CornerTypes__Node_C__Xminus1(point, rect_A, rect_B);
		
		// is in?
		if (type != null) {
			
			return type;
			
//			Map<NodeNames, Object[]> map = new HashMap<NodeNames, Object[]>();
//			
//			map.put(name, new Object[]{point, type});
//			
//			return map;
			
//			return (new HashMap<name, Object[]>());
			
		}
		
		//log
		String text, fname; int line_Num;
		
		if (type != null) {
			
			text = String.format(Locale.JAPAN, "type => %s\n", type.toString());
			
		} else {
			
			text = String.format(Locale.JAPAN, "type => null\n");
			
		}
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		
		return null;
		
	}//_get_CornerTypes__Node_C__CLR
	
	private static CornerTypes 
	_get_CornerTypes__Node_C__CUL
	(Point point, NodeNames name, Rect rect_A, Rect rect_B) {
		// TODO Auto-generated method stub
		////////////////////////////////
		
		// Y - 1
		
		////////////////////////////////
		CornerTypes type = Methods._get_CornerTypes__Node_C__Yminus1(point, rect_A, rect_B);
		
		// is in?
		if (type != null) {
			
			//log
			String text, fname; int line_Num;
			
			text = String.format(Locale.JAPAN, "node name => %s / type => %s\n", name.toString(), type.toString());
			
			fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
			
			return type;
			
//			Map<NodeNames, Object[]> map = new HashMap<NodeNames, Object[]>();
//			
//			map.put(name, new Object[]{point, type});
//			
//			return map;
			
//			return (new HashMap<name, Object[]>());
			
		}
		
		////////////////////////////////
		
		// X - 1
		
		////////////////////////////////
		type = Methods._get_CornerTypes__Node_C__Xminus1(point, rect_A, rect_B);
		
		// is in?
		if (type != null) {
			
			return type;
			
//			Map<NodeNames, Object[]> map = new HashMap<NodeNames, Object[]>();
//			
//			map.put(name, new Object[]{point, type});
//			
//			return map;
			
//			return (new HashMap<name, Object[]>());
			
		}
		
		//log
		String text, fname; int line_Num;
		
		if (type != null) {
			
			text = String.format(Locale.JAPAN, "type => %s\n", type.toString());
			
		} else {
			
			text = String.format(Locale.JAPAN, "type => null\n");
			
		}
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		
		return null;
		
	}//_get_CornerTypes__Node_C__CLR
	

	
	private static CornerTypes 
	_get_CornerTypes__Node_C__Yplus1
	(Point point, Rect rect_1, Rect rect_2) {
		// TODO Auto-generated method stub
		////////////////////////////////

		// point.y + 1

		////////////////////////////////
		int node_X = point.x;
		int node_Y = point.y + 1;
		
		
		////////////////////////////////

		// Rect A

		////////////////////////////////
		boolean isIn = Methods.is_WithinRect(rect_1, new Point(node_X, node_Y));

		if (isIn == true) {
		
			String text, fname; int line_Num;
			
			text = String.format(Locale.JAPAN, 
							"Point: %d, %d: [y + 1] isIn => %s (rect_A, Y plus 1)\n",
							point.x, point.y,
							isIn);
			
			fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
			
			////////////////////////////////

			// return

			////////////////////////////////
			String rect_Name = rect_1.getRect_Name();
			
			if (rect_Name.equals("rect_A")) return CONS.Admin.CornerTypes.IN_CORNER_A;
			else if (rect_Name.equals("rect_B")) return CONS.Admin.CornerTypes.IN_CORNER_B;
			else if (rect_Name.equals("rect_C")) return CONS.Admin.CornerTypes.IN_CORNER_C;
			else return CONS.Admin.CornerTypes.IN_CORNER_X;
			
		}//if (isIn == true)
		
		////////////////////////////////
		
		// Rect B
		
		////////////////////////////////
		isIn = Methods.is_WithinRect(rect_2, new Point(node_X, node_Y));
		
		if (isIn == true) {
			
			String text, fname; int line_Num;
			
			text = String.format(Locale.JAPAN, 
					"Point: %d, %d: [y + 1] isIn => %s (rect_B, Y plus 1))\n",
					point.x, point.y,
					isIn);
			
			fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
	
			////////////////////////////////

			// return

			////////////////////////////////
			String rect_Name = rect_2.getRect_Name();
			
			if (rect_Name.equals("rect_A")) return CONS.Admin.CornerTypes.IN_CORNER_A;
			else if (rect_Name.equals("rect_B")) return CONS.Admin.CornerTypes.IN_CORNER_B;
			else if (rect_Name.equals("rect_C")) return CONS.Admin.CornerTypes.IN_CORNER_C;
			else return CONS.Admin.CornerTypes.IN_CORNER_X;

//			return CONS.Admin.CornerTypes.IN_CORNER_B;
			
		}
		
		//log
		String text, fname; int line_Num;
		
		text = String.format(Locale.JAPAN, 
						"Point: %d, %d: [y + 1] isIn => %s\n",
						point.x, point.y,
						isIn);
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

		return null;
		
	}//_get_CornerTypes__Node_C__Yplus1

	private static CornerTypes 
	_get_CornerTypes__Node_C__Yminus1
	(Point point, Rect rect_1, Rect rect_2) {
		// TODO Auto-generated method stub
		////////////////////////////////
		
		// point.y + 1
		
		////////////////////////////////
		int node_X = point.x;
		int node_Y = point.y - 1;
		
		
		////////////////////////////////
		
		// Rect A
		
		////////////////////////////////
		boolean isIn = Methods.is_WithinRect(rect_1, new Point(node_X, node_Y));
		
		if (isIn == true) {
			
			String text, fname; int line_Num;
			
			text = String.format(Locale.JAPAN, 
					"Point: %d, %d: [y - 1] isIn => %s (rect_A, Y minus 1)\n",
					point.x, point.y,
					isIn);
			
			fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
			
			////////////////////////////////

			// return

			////////////////////////////////
			String rect_Name = rect_1.getRect_Name();
			
			if (rect_Name.equals("rect_A")) return CONS.Admin.CornerTypes.IN_CORNER_A;
			else if (rect_Name.equals("rect_B")) return CONS.Admin.CornerTypes.IN_CORNER_B;
			else if (rect_Name.equals("rect_C")) return CONS.Admin.CornerTypes.IN_CORNER_C;
			else return CONS.Admin.CornerTypes.IN_CORNER_X;
			
//			return CONS.Admin.CornerTypes.IN_CORNER_A;
			
		}
		
		////////////////////////////////
		
		// Rect B
		
		////////////////////////////////
		isIn = Methods.is_WithinRect(rect_2, new Point(node_X, node_Y));
		
		if (isIn == true) {
			
			String text, fname; int line_Num;
			
			text = String.format(Locale.JAPAN, 
					"Point: %d, %d: [y - 1] isIn => %s (rect_B, Y minus 1))\n",
					point.x, point.y,
					isIn);
			
			fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
			
			////////////////////////////////

			// return

			////////////////////////////////
			String rect_Name = rect_2.getRect_Name();
			
			if (rect_Name.equals("rect_A")) return CONS.Admin.CornerTypes.IN_CORNER_A;
			else if (rect_Name.equals("rect_B")) return CONS.Admin.CornerTypes.IN_CORNER_B;
			else if (rect_Name.equals("rect_C")) return CONS.Admin.CornerTypes.IN_CORNER_C;
			else return CONS.Admin.CornerTypes.IN_CORNER_X;

//			return CONS.Admin.CornerTypes.IN_CORNER_B;
			
		}
		
		//log
		String text, fname; int line_Num;
		
		text = String.format(Locale.JAPAN, 
				"Point: %d, %d: [y + 1] isIn => %s\n",
				point.x, point.y,
				isIn);
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		
		return null;
		
	}//_get_CornerTypes__Node_C__Yplus1
	
	private static CornerTypes 
	_get_CornerTypes__Node_C__Xplus1
	(Point point, Rect rect_1, Rect rect_2) {
		// TODO Auto-generated method stub
		////////////////////////////////
		
		// point.y + 1
		
		////////////////////////////////
		int node_X = point.x + 1;
		int node_Y = point.y;
		
		
		////////////////////////////////
		
		// Rect A
		
		////////////////////////////////
		boolean isIn = Methods.is_WithinRect(rect_1, new Point(node_X, node_Y));
		
		if (isIn == true) {
			
			String text, fname; int line_Num;
			
			text = String.format(Locale.JAPAN, 
					"Point: %d, %d: [y + 1] isIn => %s (rect_A, X plus 1)\n",
					point.x, point.y,
					isIn);
			
			fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
			
			////////////////////////////////

			// return

			////////////////////////////////
			String rect_Name = rect_1.getRect_Name();
			
			if (rect_Name.equals("rect_A")) return CONS.Admin.CornerTypes.IN_CORNER_A;
			else if (rect_Name.equals("rect_B")) return CONS.Admin.CornerTypes.IN_CORNER_B;
			else if (rect_Name.equals("rect_C")) return CONS.Admin.CornerTypes.IN_CORNER_C;
			else return CONS.Admin.CornerTypes.IN_CORNER_X;

//			return CONS.Admin.CornerTypes.IN_CORNER_A;
			
		}
		
		////////////////////////////////
		
		// Rect B
		
		////////////////////////////////
		isIn = Methods.is_WithinRect(rect_2, new Point(node_X, node_Y));
		
		if (isIn == true) {
			
			String text, fname; int line_Num;
			
			text = String.format(Locale.JAPAN, 
					"Point: %d, %d: [y + 1] isIn => %s (rect_B, X plus 1)\n",
					point.x, point.y,
					isIn);
			
			fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
			
			////////////////////////////////

			// return

			////////////////////////////////
			String rect_Name = rect_2.getRect_Name();
			
			//log
			text = String.format(Locale.JAPAN, "rect_Name => %s\n", rect_Name);
			
			fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

			
			if (rect_Name.equals("rect_A")) return CONS.Admin.CornerTypes.IN_CORNER_A;
			else if (rect_Name.equals("rect_B")) return CONS.Admin.CornerTypes.IN_CORNER_B;
			else if (rect_Name.equals("rect_C")) return CONS.Admin.CornerTypes.IN_CORNER_C;
			else return CONS.Admin.CornerTypes.IN_CORNER_X;

//			return CONS.Admin.CornerTypes.IN_CORNER_B;
			
		}
		
		//log
		String text, fname; int line_Num;
		
		text = String.format(Locale.JAPAN, 
				"Point: %d, %d: [y + 1] isIn => %s\n",
				point.x, point.y,
				isIn);
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		
		return null;
		
	}//_get_CornerTypes__Node_C__Yplus1
	
	private static CornerTypes 
	_get_CornerTypes__Node_C__Xminus1
	(Point point, Rect rect_A, Rect rect_B) {
		// TODO Auto-generated method stub
		////////////////////////////////
		
		// point.y + 1
		
		////////////////////////////////
		int node_X = point.x - 1;
		int node_Y = point.y;
		
		
		////////////////////////////////
		
		// Rect A
		
		////////////////////////////////
		boolean isIn = Methods.is_WithinRect(rect_A, new Point(node_X, node_Y));
		
		if (isIn == true) {
			
			String text, fname; int line_Num;
			
			text = String.format(Locale.JAPAN, 
					"Point: %d, %d: [y + 1] isIn => %s (rect_A, X minus 1)\n",
					point.x, point.y,
					isIn);
			
			fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
			
			return CONS.Admin.CornerTypes.IN_CORNER_A;
			
		}
		
		////////////////////////////////
		
		// Rect B
		
		////////////////////////////////
		isIn = Methods.is_WithinRect(rect_B, new Point(node_X, node_Y));
		
		if (isIn == true) {
			
			String text, fname; int line_Num;
			
			text = String.format(Locale.JAPAN, 
					"Point: %d, %d: [y + 1] isIn => %s (rect_B, X minus 1)\n",
					point.x, point.y,
					isIn);
			
			fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
			
			return CONS.Admin.CornerTypes.IN_CORNER_B;
			
		}
		
		//log
		String text, fname; int line_Num;
		
		text = String.format(Locale.JAPAN, 
				"Point: %d, %d: [y + 1] isIn => %s\n",
				point.x, point.y,
				isIn);
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		
		return null;
		
	}//_get_CornerTypes__Node_C__Yplus1
	
	public static Map<NodeNames, Point> 
	get_Map_AllNodes
	(List<NodeNames> list_NodeNames, Rect rect_A, Rect rect_B, Rect rect_C) {
		// TODO Auto-generated method stub
		
		NodeNames name = null;
		
		Point nodePoints_XY = null;
		
		Map<NodeNames, Point> map_Node_XY = new HashMap<NodeNames, Point>();
		
		////////////////////////////////
		
		// build: map of the node name and the point
		
		////////////////////////////////
		for (int i = 0; i < list_NodeNames.size(); i++) {
			
			name = list_NodeNames.get(i);
			
			nodePoints_XY = Methods.get_XY_from_NodeName__NodesOn_AB(name, rect_A, rect_B);
			
			map_Node_XY.put(name, nodePoints_XY);
			
		}
		
		////////////////////////////////
		
		// build: all nodes map
		
		////////////////////////////////
		Map<NodeNames, Point> map_AllNodes_XY = new HashMap<NodeNames, Point>();
		
		map_AllNodes_XY.putAll(map_Node_XY);
		
		int c_X1 = rect_C.getX_Cur();
		int c_X2 = rect_C.getX_Cur() + rect_C.getW();
		int c_Y1 = rect_C.getY_Cur();
		int c_Y2 = rect_C.getY_Cur() + rect_C.getH();
		
		Point c_UL = new Point(c_X1, c_Y1);
		Point c_UR = new Point(c_X2, c_Y1);
		Point c_LL = new Point(c_X1, c_Y2);
		Point c_LR = new Point(c_X2, c_Y2);
		
		Point[] c_Points = new Point[]{c_UL, c_UR, c_LL, c_LR};
		NodeNames[] c_NodeNames = new NodeNames[]{
				CONS.Admin.NodeNames.C_UL, 
				CONS.Admin.NodeNames.C_UR, 
				CONS.Admin.NodeNames.C_LL, 
				CONS.Admin.NodeNames.C_LR, 
		};
		
		int c_X, c_Y;
		
		Point node_AB = null;
		
		NodeNames name_AB = null;
		
		boolean f_IsSame = false;
		
		for (int i = 0; i < c_Points.length; i++) {
			
			c_X = c_Points[i].x; c_Y = c_Points[i].y;
			
			for (int j = 0; j < list_NodeNames.size(); j++) {
				
				name_AB = list_NodeNames.get(j);
				
				node_AB = map_AllNodes_XY.get(name_AB);
				
				// validate: null
				if (node_AB == null) {
					
					continue;
					
				}
				
				// compare: same point?
				if(node_AB.x == c_X && node_AB.y == c_Y) {
					
					f_IsSame = true;
					
					break;
					
				}
				
			}//for (int j = 0; j < list_NodeNames.size(); j++)
			
			// if f_IsSame is still false, then put the rect C node
			if (f_IsSame == false) {
				
				map_AllNodes_XY.put(c_NodeNames[i], c_Points[i]);
				
			} else if (f_IsSame == true) {
				
				// remove the node_AB under comparison
				map_AllNodes_XY.remove(name_AB);
				
				f_IsSame = false;	// reset the flag
				
			}//if (f_IsSame == false)
			
		}//for (int i = 0; i < c_Points.length; i++)
		
//		////////////////////////////////
//		
//		// report
//		
//		////////////////////////////////
//		Set<NodeNames> set = map_AllNodes_XY.keySet();
//		
//		Iterator it = null;
//		
//		it = set.iterator();
//		
//		NodeNames n;
//		
//		while (it.hasNext()) {
//			
//			n = (NodeNames) it.next();
//			
//			//log
//			text = String.format(Locale.JAPAN, 
//					"node name => %s (x = %d, y = %d)\n", 
//					n.toString(),
//					map_AllNodes_XY.get(n).x,
//					map_AllNodes_XY.get(n).y
//					);
//			
//			fname = Thread.currentThread().getStackTrace()[1].getFileName();
//			
//			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
//			
//			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
//			
//		}
		
		return map_AllNodes_XY;
		
	}//get_CornerTypes
	
	private static Point 
	get_XY_from_NodeName__NodesOn_AB
	(NodeNames name, Rect rect_A, Rect rect_B) {
		
		////////////////////////////////

		// prep: data: coordinates

		////////////////////////////////
		// rect A
		int a_X1 = rect_A.getX_Cur();
		int a_Y1 = rect_A.getY_Cur();
		
		int a_X2 = rect_A.getX_Cur() + rect_A.getW();
		int a_Y2 = rect_A.getY_Cur() + rect_A.getH();
		
		// rect B
		int b_X1 = rect_B.getX_Cur();
		int b_Y1 = rect_B.getY_Cur();
		
		int b_X2 = rect_B.getX_Cur() + rect_B.getW();
		int b_Y2 = rect_B.getY_Cur() + rect_B.getH();
		
		////////////////////////////////

		// dispatch

		////////////////////////////////
		String name_String = name.toString();
		
		if (name_String.startsWith("A")) {
			
			switch(name) {
			
			case A_UL: return new Point(a_X1, a_Y1);
			case A_UR: return new Point(a_X2, a_Y1);
			case A_LL: return new Point(a_X1, a_Y2);
			case A_LR: return new Point(a_X2, a_Y2);
			
			}//switch(name)
			
		} else if (name_String.startsWith("B")) {
			
			switch(name) {
			
			case B_UL: return new Point(b_X1, b_Y1);
			case B_UR: return new Point(b_X2, b_Y1);
			case B_LL: return new Point(b_X1, b_Y2);
			case B_LR: return new Point(b_X2, b_Y2);
			
			}//switch(name)
			
		}
		
		return null;
		
	}//get_XY_from_NodeName

	public static int 
	get_NumOf_Residues
	(Map<Lines, LineStates> map) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// get: num of MATCHEs

		////////////////////////////////
		int numOf_Match;
		
		List<Integer> list_IndexOfMatches = new ArrayList<Integer>();
		
		LineStates state = null;

		Lines line = null;
		
		Iterator it = map.keySet().iterator();
		
		int count = 0;
		
		while (it.hasNext()) {
			
			line = (Lines) it.next();
			
			state = map.get(line);
			
			//log
			String text, fname; int line_Num;
			
			text = String.format(Locale.JAPAN, "state => %s\n", state.toString());
			
			fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

			if (state == CONS.Admin.LineStates.MATCH) {
				
				list_IndexOfMatches.add(count);
				
			}
			
			// count
			count ++;
			
		}
		
		////////////////////////////////

		// report

		////////////////////////////////
		for (int i = 0; i < list_IndexOfMatches.size(); i++) {
			
			//log
			String text, fname; int line_Num;
			
			text = String.format(Locale.JAPAN, "index(MATCH) => %d\n", list_IndexOfMatches.get(i));
			
			fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
			
		}
		
		// MATCH => 0
		if (list_IndexOfMatches.size() == 0) {
			
			//log
			String text, fname; int line_Num;
			
			text = String.format(Locale.JAPAN, "MATCH => 0\n");
			
			fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

		}
		
		////////////////////////////////

		// return

		////////////////////////////////
		int numOf_Matches = list_IndexOfMatches.size();
		
		switch(numOf_Matches) {

		case 0: return 2;
		case 1: return 2;
		case 2: return (Math.abs(list_IndexOfMatches.get(0) - list_IndexOfMatches.get(1)) == 1)
							? 1 : 2;
		case 3: return 1;
		case 4: return 0;
		
		default: return -1;
		
		}//switch(numOf_Matches)
		
	}//get_NumOf_Residues

	public static boolean 
	is_Overwrap(Rect_D11 rect_D11) {
		// TODO Auto-generated method stub
		
		Object[] objs = Methods.get_NodeNameAndOrien_frmo_Status__B(CONS.Admin.status_B);
		
		rect_D11.getRect_B().setAttachedAt((NodeNames)objs[0]);
		rect_D11.getRect_B().setOrien((Orien) objs[1]);
		
		boolean res = Methods.overWrap_V3(
//				boolean res = Methods.overWrap_on_A(
							rect_D11.getRect_A(), 
							rect_D11.getRect_B(), 
							rect_D11.getRect_C(), 
//							Rect_D9.this.rect_A, 
							CONS.Admin.status_C);
		
		//log
		String text, fname; int line_Num;

		text = String.format(Locale.JAPAN, "overwrap result => %s\n", res);
		
		fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

		return res;
//		return false;
		
	}//is_Overwrap
	
}//public class Methods
