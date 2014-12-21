package wb.main;

import javax.swing.JFrame;

public class Run_Draw implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		System.out.println("Print from thread: \t" + Thread.currentThread().getName());
		
        JFrame frame = new JFrame("DialogDemo");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        DialogDemo newContentPane = new DialogDemo(frame);
        
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);

		
//		TH_3.bt_Start.setText("clicked!");
//		
//		TH_3.count_1 = 130;
		
//		TH.button.setText("clicked!");
		
//		System.out.println("clicked => " + clicked);

	}

}
