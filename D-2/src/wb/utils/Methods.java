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
		}//switch(name)
		
		return -1;
		
	}//get_Status_from_NodeAndPosition

	public static Orien 
	get_NextOrien(NodeNames name, Orien orien_Current) {
		// TODO Auto-generated method stub
		
		switch(name) {
		
		case B_UL:
			
			switch(orien_Current) {
			
			case HORI_VERTI://---------------
				
				return CONS.Admin.Orien.HORI_HORI;
				
			case HORI_HORI://---------------
				
				return CONS.Admin.Orien.VERTI_HORI;
				
			case VERTI_HORI://---------------
				
				return CONS.Admin.Orien.VERTI_VERTI;
				
			case VERTI_VERTI://---------------
				
				return CONS.Admin.Orien.NEXT_NODE;
				
			case INITIAL://---------------
				
				return CONS.Admin.Orien.HORI_VERTI;
				
			}
			
			break;
			
		}

		return orien_Current;
		
	}//get_NextOrien
	
}
