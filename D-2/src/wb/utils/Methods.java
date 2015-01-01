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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.apache.commons.lang.math.NumberUtils;

import wb.main.Rect_D10;
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
			
			case HORI_VERTI: return node_index * CONS.Admin.numOf_Positions_per_Node + 1;
			case HORI_HORI: return node_index * CONS.Admin.numOf_Positions_per_Node + 2;
			case VERTI_HORI: return node_index * CONS.Admin.numOf_Positions_per_Node + 3;
			case VERTI_VERTI: return node_index * CONS.Admin.numOf_Positions_per_Node + 4;
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
			case HORI_VERTI: return node_index * CONS.Admin.numOf_Positions_per_Node + 1;
			case HORI_HORI: return node_index * CONS.Admin.numOf_Positions_per_Node + 2;
			case VERTI_HORI: return node_index * CONS.Admin.numOf_Positions_per_Node + 3;
			case VERTI_VERTI: return node_index * CONS.Admin.numOf_Positions_per_Node + 4;
			
			}//switch(orien)
			
			break;
			
		case B_LR://-----------------------------
		case A_LR://-----------------------------
			
			switch(orien) {
			
			case INITIAL: 
			case VERTI_HORI: return node_index * CONS.Admin.numOf_Positions_per_Node + 1;
			case VERTI_VERTI: return node_index * CONS.Admin.numOf_Positions_per_Node + 2;
			case HORI_VERTI: return node_index * CONS.Admin.numOf_Positions_per_Node + 3;
			case HORI_HORI: return node_index * CONS.Admin.numOf_Positions_per_Node + 4;
			
			}//switch(orien)
			
			break;

		case B_LL://-----------------------------
		case A_LL://-----------------------------
			
//			node_index = Methods.get_Node_Index__C(CONS.Admin.NodeNames.B_LL);
			
			
			if (node_index == -1) {
				
				return 1;
				
			}
			
			switch(orien) {
			
			case HORI_VERTI: return node_index * CONS.Admin.numOf_Positions_per_Node + 1;
			case HORI_HORI: return node_index * CONS.Admin.numOf_Positions_per_Node + 2;
			case VERTI_HORI: return node_index * CONS.Admin.numOf_Positions_per_Node + 3;
			case VERTI_VERTI: return node_index * CONS.Admin.numOf_Positions_per_Node + 4;
			
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
			
			case HORI_VERTI: return 1;
			case HORI_HORI: return 2;
			case VERTI_HORI: return 3;
			case VERTI_VERTI: return 4;
			
			}//switch(orien)
			
			break;
			
		case A_UR://-----------------------------
			
			switch(orien) {
			
			case INITIAL: 
			case HORI_VERTI: return 5;
			case HORI_HORI: return 6;
			case VERTI_HORI: return 7;
			case VERTI_VERTI: return 8;
			
			}//switch(orien)
			
			break;
			
		case A_LR://-----------------------------
			
			switch(orien) {
			
			case INITIAL: 
			case VERTI_HORI: return 9;
			case VERTI_VERTI: return 10;
			case HORI_VERTI: return 11;
			case HORI_HORI: return 12;
			
			}//switch(orien)
			
			break;

		case A_LL://-----------------------------
			
			switch(orien) {
			
			case HORI_VERTI: return 13;
			case HORI_HORI: return 14;
			case VERTI_HORI: return 15;
			case VERTI_VERTI: return 16;
			
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
			
			case HORI_VERTI: return CONS.Admin.Orien.HORI_HORI;
				
			case HORI_HORI: return CONS.Admin.Orien.VERTI_HORI;
				
			case VERTI_HORI: return CONS.Admin.Orien.VERTI_VERTI;
				
			case VERTI_VERTI: return CONS.Admin.Orien.NEXT_NODE;
				
			case INITIAL: return CONS.Admin.Orien.HORI_VERTI;
				
			}
			
			break;
			
		case B_UR:
			
			switch(orien_Current) {
			
			case HORI_VERTI: return CONS.Admin.Orien.HORI_HORI;
			
			case HORI_HORI: return CONS.Admin.Orien.VERTI_HORI;
			
			case VERTI_HORI: return CONS.Admin.Orien.VERTI_VERTI;
			
			case VERTI_VERTI: return CONS.Admin.Orien.NEXT_NODE;
			
			case INITIAL: return CONS.Admin.Orien.HORI_VERTI;
			
			}
			
			break;
			
		case B_LR:
			
			switch(orien_Current) {
			
			case VERTI_HORI: return CONS.Admin.Orien.VERTI_VERTI;
			
			case VERTI_VERTI: return CONS.Admin.Orien.HORI_VERTI;
			
			case HORI_VERTI: return CONS.Admin.Orien.HORI_HORI;
			
			case HORI_HORI: return CONS.Admin.Orien.NEXT_NODE;
			
			case INITIAL: return CONS.Admin.Orien.VERTI_HORI;
			
			}
			
			break;
			
		case A_UR:
			
			switch(orien_Current) {
			
			case HORI_VERTI: return CONS.Admin.Orien.HORI_HORI;
			
			case HORI_HORI: return CONS.Admin.Orien.VERTI_HORI;
			
			case VERTI_HORI: return CONS.Admin.Orien.VERTI_VERTI;
			
			case VERTI_VERTI: return CONS.Admin.Orien.NEXT_NODE;
			
			case INITIAL: return CONS.Admin.Orien.HORI_VERTI;
			
			}
			
			break;
			
		case A_LR:
			
			switch(orien_Current) {
			
			case VERTI_HORI: return CONS.Admin.Orien.VERTI_VERTI;
			
			case VERTI_VERTI: return CONS.Admin.Orien.HORI_VERTI;
			
			case HORI_VERTI: return CONS.Admin.Orien.HORI_HORI;
			
			case HORI_HORI: return CONS.Admin.Orien.NEXT_NODE;
			
			case INITIAL: return CONS.Admin.Orien.VERTI_HORI;
			
			}
			
			break;

		case A_LL://-----------------------------
			
			switch(orien_Current) {
			
			case HORI_VERTI: return CONS.Admin.Orien.HORI_HORI;
				
			case HORI_HORI: return CONS.Admin.Orien.VERTI_HORI;
				
			case VERTI_HORI: return CONS.Admin.Orien.VERTI_VERTI;
				
			case VERTI_VERTI: return CONS.Admin.Orien.NEXT_NODE;
				
			case INITIAL: return CONS.Admin.Orien.HORI_VERTI;
				
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
			
			case HORI_VERTI: return CONS.Admin.Orien.PREV_NODE;
				
			case HORI_HORI: return CONS.Admin.Orien.HORI_VERTI;
				
			case VERTI_HORI: return CONS.Admin.Orien.HORI_HORI;
				
			case VERTI_VERTI: return CONS.Admin.Orien.VERTI_HORI;
				
			case INITIAL: return CONS.Admin.Orien.HORI_VERTI;
				
			}//switch(orien_Current)
			
			break;
			
		case B_UR://------------------------------
			
			switch(orien_Current) {
			
			case HORI_VERTI: return CONS.Admin.Orien.PREV_NODE;
				
			case HORI_HORI: return CONS.Admin.Orien.HORI_VERTI;
				
			case VERTI_HORI: return CONS.Admin.Orien.HORI_HORI;
				
			case VERTI_VERTI: return CONS.Admin.Orien.VERTI_HORI;
				
			case INITIAL: return CONS.Admin.Orien.HORI_VERTI;
				
			}//switch(orien_Current)
			
			break;
			
		case B_LR://------------------------------
			
			switch(orien_Current) {
			
			case HORI_HORI: return CONS.Admin.Orien.HORI_VERTI;
				
			case HORI_VERTI: return CONS.Admin.Orien.VERTI_VERTI;
				
			case VERTI_VERTI: return CONS.Admin.Orien.VERTI_HORI;
				
			case VERTI_HORI: return CONS.Admin.Orien.PREV_NODE;
				
			case INITIAL: return CONS.Admin.Orien.PREV_NODE;
				
			}//switch(orien_Current)
			
			break;

		case A_UR://------------------------------
			
			switch(orien_Current) {
			
			case HORI_VERTI: return CONS.Admin.Orien.PREV_NODE;
				
			case HORI_HORI: return CONS.Admin.Orien.HORI_VERTI;
				
			case VERTI_HORI: return CONS.Admin.Orien.HORI_HORI;
				
			case VERTI_VERTI: return CONS.Admin.Orien.VERTI_HORI;
				
			case INITIAL: return CONS.Admin.Orien.HORI_VERTI;
				
			}//switch(orien_Current)
			
			break;
			
		case A_LR://------------------------------
			
			switch(orien_Current) {
			
			case HORI_HORI: return CONS.Admin.Orien.HORI_VERTI;
				
			case HORI_VERTI: return CONS.Admin.Orien.VERTI_VERTI;
				
			case VERTI_VERTI: return CONS.Admin.Orien.VERTI_HORI;
				
			case VERTI_HORI: return CONS.Admin.Orien.PREV_NODE;
				
			case INITIAL: return CONS.Admin.Orien.PREV_NODE;
				
			}//switch(orien_Current)
			
			break;	// case A_LR

		case A_LL://------------------------------
			
			switch(orien_Current) {
			
			case HORI_VERTI: return CONS.Admin.Orien.PREV_NODE;
				
			case HORI_HORI: return CONS.Admin.Orien.HORI_VERTI;
				
			case VERTI_HORI: return CONS.Admin.Orien.HORI_HORI;
				
			case VERTI_VERTI: return CONS.Admin.Orien.VERTI_HORI;
				
			case INITIAL: return CONS.Admin.Orien.HORI_VERTI;
				
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
		
		case B_UL: return CONS.Admin.Orien.HORI_VERTI;
		case B_UR: return CONS.Admin.Orien.HORI_VERTI;
		case B_LR: return CONS.Admin.Orien.VERTI_HORI;
		
		case A_UR: return CONS.Admin.Orien.HORI_VERTI;
		case A_LR: return CONS.Admin.Orien.VERTI_HORI;
		case A_LL: return CONS.Admin.Orien.HORI_VERTI;
		
		}
		
		return CONS.Admin.Orien.HORI_VERTI;
		
	}//get_InitialOrien(NodeNames name)

	/*******************************

		@return
		default => HORI_VERTI

	 *******************************/
	public static Orien 
	get_LastOrien(NodeNames name) {
		// TODO Auto-generated method stub
		
		switch(name) {
		
		case B_UL: return CONS.Admin.Orien.VERTI_VERTI;
		case B_UR: return CONS.Admin.Orien.VERTI_VERTI;
		case B_LR: return CONS.Admin.Orien.HORI_HORI;
		
		case A_UR: return CONS.Admin.Orien.VERTI_VERTI;
		case A_LR: return CONS.Admin.Orien.HORI_HORI;
		case A_LL: return CONS.Admin.Orien.VERTI_VERTI;
		
		}
		
		return CONS.Admin.Orien.HORI_VERTI;
		
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
			
			case 1: orien = CONS.Admin.Orien.HORI_VERTI; break; 
			case 2: orien = CONS.Admin.Orien.HORI_HORI; break; 
			case 3: orien = CONS.Admin.Orien.VERTI_HORI; break; 
			case 4: orien = CONS.Admin.Orien.VERTI_VERTI; break; 
			
			}
			
			break;//case B_UL
		
		case B_UR://----------------------------------
			
			switch(index_Orien) {
			
			case 1: orien = CONS.Admin.Orien.HORI_VERTI; break; 
			case 2: orien = CONS.Admin.Orien.HORI_HORI; break; 
			case 3: orien = CONS.Admin.Orien.VERTI_HORI; break; 
			case 4: orien = CONS.Admin.Orien.VERTI_VERTI; break; 
			
			}
			
			break;//case B_UL
			
		case B_LR://----------------------------------
			
			switch(index_Orien) {
			
			case 1: orien = CONS.Admin.Orien.VERTI_HORI; break; 
			case 2: orien = CONS.Admin.Orien.VERTI_VERTI; break; 
			case 3: orien = CONS.Admin.Orien.HORI_VERTI; break; 
			case 4: orien = CONS.Admin.Orien.HORI_HORI; break; 
			
			}
			
			break;//case B_UL

		case B_LL://----------------------------------
			
			switch(index_Orien) {
			
			case 1: orien = CONS.Admin.Orien.HORI_VERTI; break; 
			case 2: orien = CONS.Admin.Orien.HORI_HORI; break; 
			case 3: orien = CONS.Admin.Orien.VERTI_HORI; break; 
			case 4: orien = CONS.Admin.Orien.VERTI_VERTI; break; 
			
			}
			
			break;//case B_LL

		case A_UR://----------------------------------
			
			switch(index_Orien) {
			
			case 1: orien = CONS.Admin.Orien.HORI_VERTI; break; 
			case 2: orien = CONS.Admin.Orien.HORI_HORI; break; 
			case 3: orien = CONS.Admin.Orien.VERTI_HORI; break; 
			case 4: orien = CONS.Admin.Orien.VERTI_VERTI; break; 
			
			}
			
			break;//case A_UR
			
		case A_LR://----------------------------------
			
			switch(index_Orien) {
			
			case 1: orien = CONS.Admin.Orien.VERTI_HORI; break; 
			case 2: orien = CONS.Admin.Orien.VERTI_VERTI; break; 
			case 3: orien = CONS.Admin.Orien.HORI_VERTI; break; 
			case 4: orien = CONS.Admin.Orien.HORI_HORI; break; 
			
			}
			
			break;//case A_UL

		case A_LL://----------------------------------
			
			switch(index_Orien) {
			
			case 1: orien = CONS.Admin.Orien.HORI_VERTI; break; 
			case 2: orien = CONS.Admin.Orien.HORI_HORI; break; 
			case 3: orien = CONS.Admin.Orien.VERTI_HORI; break; 
			case 4: orien = CONS.Admin.Orien.VERTI_VERTI; break; 
			
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
			
			case 1: orien = CONS.Admin.Orien.HORI_VERTI; break; 
			case 2: orien = CONS.Admin.Orien.HORI_HORI; break; 
			case 3: orien = CONS.Admin.Orien.VERTI_HORI; break; 
			case 4: orien = CONS.Admin.Orien.VERTI_VERTI; break; 
			
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
			
			case 1: orien = CONS.Admin.Orien.HORI_VERTI; break; 
			case 2: orien = CONS.Admin.Orien.HORI_HORI; break; 
			case 3: orien = CONS.Admin.Orien.VERTI_HORI; break; 
			case 4: orien = CONS.Admin.Orien.VERTI_VERTI; break; 
			
			}
			
			break;//case A_UR
			
		case A_LL://----------------------------------
			
			switch(index_Orien) {
			
			case 1: orien = CONS.Admin.Orien.HORI_VERTI; break; 
			case 2: orien = CONS.Admin.Orien.HORI_HORI; break; 
			case 3: orien = CONS.Admin.Orien.VERTI_HORI; break; 
			case 4: orien = CONS.Admin.Orien.VERTI_VERTI; break; 
			
			}
			
			break;//case A_LL
			
		case A_LR://----------------------------------
			
			switch(index_Orien) {
			
			case 1: orien = CONS.Admin.Orien.VERTI_HORI; break; 
			case 2: orien = CONS.Admin.Orien.VERTI_VERTI; break; 
			case 3: orien = CONS.Admin.Orien.HORI_VERTI; break; 
			case 4: orien = CONS.Admin.Orien.HORI_HORI; break; 
			
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
	(Rect_D10 rect_D6, Rect rect_C, NodeNames name) {
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
		
		case HORI_HORI: return CONS.Admin.Orien.HH;
		case HORI_VERTI: return CONS.Admin.Orien.HV;
		case VERTI_VERTI: return CONS.Admin.Orien.VV;
		case VERTI_HORI: return CONS.Admin.Orien.VH;
		
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
			
			return overWrap_on_A__B_at_A_UL(rect_A, rect_B, rect_C, status_C);
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

	private static boolean 
	overWrap_on_A__B_at_A_UL
	(Rect rect_A, Rect rect_B, Rect rect_C, int status_C) {
		
		String text, fname;
		int line_Num;

		////////////////////////////////

		// dispatch

		////////////////////////////////
		String rect_Name = rect_C.getAttachedAt().toString().split("_")[0];
		
		if (rect_Name.equals("A")) {
			
			switch(rect_C.getAttachedAt()) {
			
			case A_UR://-------------------------
			
				return overWrap_on_A__B_at_A_UL_C_at_A_UR(rect_B, rect_C);
				
			case A_LR:
			case A_LL: return false;
				
			default: return false;//-------------------------
				
			}//switch(rect_C.getAttachedAt())
			
		} else if (rect_Name.equals("B")) {//if (rect_Name.equals("A"))
			
			return overWrap_on_A__B_at_A_UL_C_at_B(rect_A, rect_B, rect_C);
			
		}//if (rect_Name.equals("A"))
		
		
		////////////////////////////////

		// default

		////////////////////////////////
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
	overWrap_on_A__B_at_A_UL_C_at_B
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

		// dispatch

		////////////////////////////////
		switch(rect_C.getOrien()) {
		
		case HORI_VERTI:
		case HORI_HORI: return false;
		case VERTI_HORI: // if: C.w_Orig > B.h ---> return true
			
			if (rect_C.getW_Orig() > rect_B.getH()) {
				
				return true;
				
			} else {
				
				return false;
				
			}
			
		case VERTI_VERTI:
			
			if ((c_X2 >= a_X1 && c_X2 <= a_X2)
					&& (c_Y2 > a_Y1)) {
				
				return true;
				
			} else {
				
				return false;
				
			}
			
		}//switch(rect_C.getOrien())
		
		return false;
		
	}//overWrap_on_A__B_at_A_UL_C_at_B_UR
	

	private static boolean 
	overWrap_on_A__B_at_A_UL_C_at_A_UR
	(Rect rect_B, Rect rect_C) {
		// TODO Auto-generated method stub
		switch(rect_C.getOrien()) {
		
		case HORI_VERTI:
			
			if (rect_C.getX_Cur() >= (rect_B.getX_Cur() + rect_B.getW())) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		case HORI_HORI:
			
			if (rect_C.getX_Cur() >= (rect_B.getX_Cur() + rect_B.getW())) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		case VERTI_HORI:
		case VERTI_VERTI:
		
			return false;
		
//		defalult: return false;
			
		}

		return false;
		
	}//overWrap_on_A__B_at_A_UL_C_at_A_UR
	
}//public class Methods
