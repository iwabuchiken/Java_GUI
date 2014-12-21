package wb.main;

public class Run_Draw implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		System.out.println("Print from thread: \t" + Thread.currentThread().getName());
		
		TH.bt_Start.setText("clicked!");
		
		TH.count_1 = 130;
		
//		TH.button.setText("clicked!");
		
//		System.out.println("clicked => " + clicked);

	}

}
