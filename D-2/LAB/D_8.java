import java.util.List;
import java.util.Locale;

import java.util.ArrayList;

public class D_8 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		test_V_1_0_a();	//=> method creatin --> complete
//		test_V_1_0();
		
	}

	public static void test_V_1_0() {
		
		int i1, i2;
		
		List<Integer> list = new ArrayList<Integer>();
		
		list.add(1);
		list.add(1);
		list.add(3);
		list.add(6);
		
		////////////////////////////////

		// show all members

		////////////////////////////////
		for (int i = 0; i < list.size(); i++) {

			//log
			String text = String.format(Locale.JAPAN, 
								"list(%d) => %d\n",
								i, list.get(i)
					);
			
			String fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			int line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

		}
		
		for (int i = 0; i < list.size() - 1; i++) {
			
			i1 = list.get(i);
			
			for (int j = i + 1; j < list.size(); j++) {

				i2 = list.get(j);
				
				//log
				String text = String.format(Locale.JAPAN, 
								"i1 = %d, i2 = %d\n",
								i1, i2
						);
				
				String fname = Thread.currentThread().getStackTrace()[1].getFileName();
				
				int line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
				
				System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

				
			}
					
					
			
		}
		
//		//log
//		String text = String.format(Locale.JAPAN, "test\n");
//		
//		String fname = Thread.currentThread().getStackTrace()[1].getFileName();
//		
//		int line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
//		
//		System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
		
	}
	
	/*******************************

		filter the list => take out any duplicate members

	 *******************************/
	public static void 
	test_V_1_0_a() {
		
		String text, fname;
		int line_Num;
		
		int i1, i2;
		
		List<Integer> list_1 = new ArrayList<Integer>();
		List<Integer> list_2 = new ArrayList<Integer>();
		
		list_1.add(1);
		list_1.add(1);
		list_1.add(3);
		list_1.add(6);
		
		////////////////////////////////
		
		// show all members
		
		////////////////////////////////
		for (int i = 0; i < list_1.size(); i++) {
			
			//log
			text = String.format(Locale.JAPAN, 
					"list_1(%d) => %d\n",
					i, list_1.get(i)
					);
			
			fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
			
		}
		
		////////////////////////////////

		// filter

		////////////////////////////////
		
		boolean is_In = false;
		
		for (int i = 0; i < list_1.size(); i++) {
			
			i1 = list_1.get(i);
			
			for (int j = 0; j < list_1.size(); j++) {
			
				////////////////////////////////

				// omit: same index

				////////////////////////////////
				if (i == j) {
					
					continue;
					
				}
				
				i2 = list_1.get(j);

				//log
				text = String.format(Locale.JAPAN, 
						"Start: i1 = %d / i2 = %d (is_In = %s)\n",
						i1, i2, is_In
						);
				
				fname = Thread.currentThread().getStackTrace()[1].getFileName();
				
				line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
				
				System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

				if (i1 == i2) {
					
					is_In = true;

					//log
					text = String.format(Locale.JAPAN, 
							"is equal: i1 = %d / i2 = %d (is_In = %s)\n",
							i1, i2, is_In
							);
					
					fname = Thread.currentThread().getStackTrace()[1].getFileName();
					
					line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
					
					System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

					break;
					
				} else {
					
					//log
					text = String.format(Locale.JAPAN, 
							"not equal: i1 = %d / i2 = %d (is_In = %s)\n",
							i1, i2, is_In
							);
					
					fname = Thread.currentThread().getStackTrace()[1].getFileName();
					
					line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
					
					System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
					
				}
				
			}
			
			if (is_In == false) {
				
				list_2.add(i1);

				//log
				text = String.format(Locale.JAPAN, 
						"i1 added to list_2: i1 = %d\n",
						i1
						);
				
				fname = Thread.currentThread().getStackTrace()[1].getFileName();
				
				line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
				
				System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);

			} else {
				
				is_In = false;	// if is_In is true, then reset it to false
				
			}
			
		}

		////////////////////////////////
		
		// show: filtered list
		
		////////////////////////////////
		if (list_2.size() < 1) {
			
			//log
			text = String.format(Locale.JAPAN, 
					"list_2  => no entry\n"
					);
			
			fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
			
		}
		
		for (int i = 0; i < list_2.size(); i++) {
			
			//log
			text = String.format(Locale.JAPAN, 
					"list_2(%d) => %d\n",
					i, list_2.get(i)
					);
			
			fname = Thread.currentThread().getStackTrace()[1].getFileName();
			
			line_Num = Thread.currentThread().getStackTrace()[1].getLineNumber();
			
			System.out.format(Locale.JAPAN, "[%s:%d] %s", fname, line_Num, text);
			
		}

	}//test_V_1_0_a
	
}
