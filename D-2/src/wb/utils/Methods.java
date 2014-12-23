package wb.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JOptionPane;

import org.apache.commons.lang.math.NumberUtils;

import wb.main.Rect_D6;
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
		
		////////////////////////////////

		// names

		////////////////////////////////
		switch(name) {
		
		case B_UL:
			
			switch(orien) {
			
			case HORI_VERTI: return 1;
			case HORI_HORI: return 2;
			case VERTI_HORI: return 3;
			case VERTI_VERTI: return 4;
			
			}//switch(orien)
			
			break;
			
		case B_UR:
			
			switch(orien) {
			
			case INITIAL: 
			case HORI_VERTI: return 5;
			case HORI_HORI: return 6;
			case VERTI_HORI: return 7;
			case VERTI_VERTI: return 8;
			
			}//switch(orien)
			
			break;
			
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
			
		}

		return orien_Current;
		
	}//get_NextOrien
	
	public static Orien 
	get_PrevOrien(NodeNames name, Orien orien_Current) {
		// TODO Auto-generated method stub
		
		switch(name) {
		
		case B_UL:
			
			switch(orien_Current) {
			
			case HORI_VERTI://---------------
				
				return CONS.Admin.Orien.PREV_NODE;
				
			case HORI_HORI://---------------
				
				return CONS.Admin.Orien.HORI_VERTI;
				
			case VERTI_HORI://---------------
				
				return CONS.Admin.Orien.HORI_HORI;
				
			case VERTI_VERTI://---------------
				
				return CONS.Admin.Orien.VERTI_HORI;
				
			case INITIAL://---------------
				
				return CONS.Admin.Orien.HORI_VERTI;
				
			}//switch(orien_Current)
			
			break;
			
		case B_UR:
			
			switch(orien_Current) {
			
			case HORI_VERTI://---------------
				
				return CONS.Admin.Orien.PREV_NODE;
				
			case HORI_HORI://---------------
				
				return CONS.Admin.Orien.HORI_VERTI;
				
			case VERTI_HORI://---------------
				
				return CONS.Admin.Orien.HORI_HORI;
				
			case VERTI_VERTI://---------------
				
				return CONS.Admin.Orien.VERTI_HORI;
				
			case INITIAL://---------------
				
				return CONS.Admin.Orien.HORI_VERTI;
				
			}//switch(orien_Current)
			
			break;
			
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
		
		}
		
		return CONS.Admin.Orien.HORI_VERTI;
		
	}//get_InitialOrien(NodeNames name)

	public static Object[] 
	get_NodeNameAndOrien_frmo_Status(int status) {
		// TODO Auto-generated method stub
		
//		CONS.Admin.NodeNames name
		int node_Number = Methods.get_NodeNumber_frmo_Status(status);
		
		NodeNames name = CONS.Admin.list_NodeNames.get(node_Number - 1);
		
//		int index_Orien = 4 - (status % 4);
		int index_Orien = status % 4;
		
		if (index_Orien == 0) index_Orien = 4;

		//log
		String text = String.format(Locale.JAPAN, 
							"status => %d / index_Orien => %d\n", 
							status, index_Orien);
		
		String fname = Thread.currentThread().getStackTrace()[1].getFileName();
		
		int line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
		
		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		

		
		CONS.Admin.Orien orien = null;
//		Orien orien;
		
		////////////////////////////////

		// orien

		////////////////////////////////
		switch(name) {
		
		case B_UL:
			
			switch(index_Orien) {
			
			case 1: orien = CONS.Admin.Orien.HORI_VERTI; break; 
			case 2: orien = CONS.Admin.Orien.HORI_HORI; break; 
			case 3: orien = CONS.Admin.Orien.VERTI_HORI; break; 
			case 4: orien = CONS.Admin.Orien.VERTI_VERTI; break; 
			
			}
			
			break;
		
		}//switch(name)
		
		return new Object[]{name, orien};
		
	}//get_NodeNameAndOrien_frmo_Status

//	public static void 
//	bt_Selected_Jump(String tmp) {
//		// TODO Auto-generated method stub
//		
//		/*******************************
//
//			validate
//	
//		 *******************************/
////		String tmp = Rect_D6.txt_Jump.getText();
//		
//		if (tmp == null) {
//			
//			return;
//			
//		}
//		
//		if (tmp.equals("")) {
//	
//			String msg;
//			
//			msg = String.format(
//					Locale.JAPAN,
//					"no input",
//					Thread.currentThread().getStackTrace()[1].getLineNumber());
//			
//			JOptionPane.showMessageDialog(null,
//					msg,
//					"message", JOptionPane.ERROR_MESSAGE);
//			
//			return;
//			
//		}
//		
//		if (!NumberUtils.isNumber(tmp)) {
//			
//			String msg = String.format(
//					Locale.JAPAN,
//					"[%s:%d]not a number => " + tmp,
//					Thread.currentThread().getStackTrace()[1].getFileName(),
//					Thread.currentThread().getStackTrace()[1].getLineNumber());
//			
//			JOptionPane.showMessageDialog(null,
//					msg,
//					"message", JOptionPane.ERROR_MESSAGE);
//			
//			return;
//			
//		}
//		
//		////////////////////////////////
//	
//		// prep data
//	
//		////////////////////////////////
//		Object[] objs = Methods.get_NodeNameAndOrien_frmo_Status(Integer.parseInt(tmp));
//	
//		NodeNames name = (NodeNames)objs[0];
//		
//		Orien orien = (Orien)objs[1];
//		
//		// validate
//		if (name == null && orien == null) {
//	
//			String msg = String.format(
//					Locale.JAPAN,
//					"[%s:%d] name and orien are both null",
//					Thread.currentThread().getStackTrace()[1].getFileName(),
//					Thread.currentThread().getStackTrace()[1].getLineNumber()
//					);
//			
//			JOptionPane.showMessageDialog(null,
//					msg,
//					"message", JOptionPane.ERROR_MESSAGE);
//			
//			return;
//	
//		} else if (name == null) {
//				
//				String msg = String.format(
//						Locale.JAPAN,
//						"[%s:%d] name is null",
//						Thread.currentThread().getStackTrace()[1].getFileName(),
//						Thread.currentThread().getStackTrace()[1].getLineNumber()
//						);
//				
//				JOptionPane.showMessageDialog(null,
//						msg,
//						"message", JOptionPane.ERROR_MESSAGE);
//				
//				return;
//				
//		} else if (orien == null) {
//				
//				String msg = String.format(
//						Locale.JAPAN,
//						"[%s:%d] orien is null",
//						Thread.currentThread().getStackTrace()[1].getFileName(),
//						Thread.currentThread().getStackTrace()[1].getLineNumber()
//						);
//				
//				JOptionPane.showMessageDialog(null,
//						msg,
//						"message", JOptionPane.ERROR_MESSAGE);
//				
//				return;
//				
//		}
//		
//		////////////////////////////////
//
//		// jump
//
//		////////////////////////////////
//		new Rect_D6()._move_Rect_C_RIGHT(name, orien);
//		
//		
////		//log
////		String text = String.format(Locale.JAPAN, 
////				"name = %s / orien = %s\n", 
////				name.toString(),
////				((Orien)objs[1]).toString()
////				);
//		
//		
//	//	String fname = Thread.currentThread().getStackTrace()[1].getFileName();
//	//	
//	//	int line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
//	//	
//	//	System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
//		
//	}//bt_Selected_Jump
	
}//public class Methods
