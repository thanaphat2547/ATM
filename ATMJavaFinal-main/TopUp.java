import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class  TopUp extends MainATM{
  static int size = 100;
  static TopUpHistory []Tuphistory = new TopUpHistory[size];
  static int count=0;
  static double money = 0;
  static double lastbal = 0;
  static String topupdate;

  public static void main(String[] args) {  
	T.menu();
	
 }


 JFrame menu = new JFrame("Top Up Menu");


 public void menu(){
	JPanel panel1 = new JPanel(); 
	String lb = "What do you want to top up?";
	JLabel label = new JLabel("<html><div style = 'text-align: center'><h1>"+lb+"</h1></div></html>",SwingConstants.LEFT);
	
	
	JButton AddWalletbutton = new JButton("Top Up Wallet");//ปุ่มเติม Wallet
	JButton AddEasyPassbutton = new JButton("Top Up Easy Pass");//ปุ่มเติม Easy Pass
	JButton ShowHistorybutton = new JButton("Show top up history");//ปุ่มโชว์ประวัติ
	JButton Quitbtn = new JButton("Return to main menu");//ปุ่มออก
	
	
	panel1.setLayout(new GridLayout(6,1));

	
	panel1.add(AddWalletbutton); 
	panel1.add(AddEasyPassbutton);
	panel1.add(ShowHistorybutton);
	panel1.add(Quitbtn);

	
	menu.add(label,BorderLayout.NORTH);
	menu.add(panel1,BorderLayout.CENTER);

	
	menu.setSize(300,300);
	menu.setLocationRelativeTo(null);
	menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	menu.setVisible(true);


	AddWalletbutton.addActionListener(new ActionListener(){
	  public void actionPerformed(ActionEvent e){
			T.AddWallet();
		   
	  }
	});	

	AddEasyPassbutton.addActionListener(new ActionListener(){
	  public void actionPerformed(ActionEvent e){
			T.AddEasyPass();
		   
	  }
	});
	
	ShowHistorybutton.addActionListener(new ActionListener(){
	  public void actionPerformed(ActionEvent e){
			T.Show();
		   
	  }
	});


	  Quitbtn.addActionListener(new ActionListener(){
	  public void actionPerformed(ActionEvent e){ 
		    OS.menu();
		    menu.dispose();	
	}	
});


}


  public void AddWallet(){
	 double inputw = 0;
	 String inputwnumber = "";
	 do{
	 inputwnumber = JOptionPane.showInputDialog("Please provide your 10-digit phone number \nInput your Wallet number :");
	 if(inputwnumber.length() == 10 && inputwnumber.length() > 0){ //เบอร์เท่ากับ 10 และ เบอร์มากกว่า 0
	 }else{
			JOptionPane.showMessageDialog(null, "Unable to make a transaction. Please try again.");//ไม่ให้ใส่อีกรอบ
		}
	}while(inputwnumber.length()!=10);//Loop do while เบอร์เท่ากับ 10 ตัวให้เด้งออก

	 inputw = Double.parseDouble(JOptionPane.showInputDialog("Input amount you want to top up to your Wallet :"));
	 if(inputw<=0 || inputw>A[accNo].getBalance()){       //การเติม Wallet น้อยกว่า 0 หรือ มากกว่ายอดเงินในบัญชีไม่ได้
		JOptionPane.showMessageDialog(null,"connot");
	  }else{
		double ch = JOptionPane.showConfirmDialog(null,"Are you sure to Top up Wallet "+inputw+"THB from your account?","Wallet Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
	  
   if(ch == JOptionPane.YES_OPTION){
	   A[accNo].topup(inputw);
	   double money = A[accNo].getBalance();

	   Calendar d = Calendar.getInstance();
	SimpleDateFormat f = new SimpleDateFormat("dd MMMM YYYY HH:mm:ss");//กำหนดรูปแบบวันเวลา
	String topupdate = f.format(d.getTime());

	   String topuptype = "Top Up Wallet number "+inputwnumber;
	   Tuphistory[count] = new TopUpHistory(topupdate, topuptype, inputw, money);
        count ++;
	}else{
		JOptionPane.showMessageDialog(null, "You've cancelled your Top up...");
	} 
   }
 }


  public void AddEasyPass(){
	 double inpute;
	 String inputenumber = "";
	 do{
		inputenumber = JOptionPane.showInputDialog("Please provide your 10-digit phone number \nInput your Easy Pass number :");
		if(inputenumber.length() == 10 && inputenumber.length() > 0){ //เบอร์เท่ากับ 10 และ เบอร์มากกว่า 0
		}else{
			   JOptionPane.showMessageDialog(null, "Unable to make a transaction. Please try again.");//ไม่ให้ใส่อีกรอบ
		   }
	   }while(inputenumber.length()!=10);//Loop do while เบอร์เท่ากับ 10 ตัวให้เด้งออก

       inpute = Double.parseDouble(JOptionPane.showInputDialog("Input amount you want to top up to your Easy pass :"));
	   if(inpute<=0 || inpute>A[accNo].getBalance()){       //การเติม Easy Pass น้อยกว่า 0 หรือ มากกว่ายอดเงินในบัญชีไม่ได้
		JOptionPane.showMessageDialog(null,"connot");
	  }else{
		double ch = JOptionPane.showConfirmDialog(null,"Are you sure to Top up Easy Pass "+inpute+"THB from your account?","Easy Pass Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
	  
   if(ch == JOptionPane.YES_OPTION){
	   A[accNo].topup(inpute);
	   double money = A[accNo].getBalance();

	   Calendar d = Calendar.getInstance();
	SimpleDateFormat f = new SimpleDateFormat("dd MMMM YYYY HH:mm:ss");//กำหนดรูปแบบวันเวลา
	String topupdate = f.format(d.getTime());
 
	   String topuptype = "Top Up Easy Pass number "+inputenumber;
	   Tuphistory[count] = new TopUpHistory(topupdate, topuptype, inpute, money);
        count ++;
	}else{
		JOptionPane.showMessageDialog(null, "You've cancelled your payment...");
	}
  }
 }

 
   public void Show(){
   String output =  "\n------------------------ Top up history  ------------------------";//โชว์ประวัติการเติมเงิน
   for(int i = 0; i<count;i++){
		  output += "\nTop Up type : "+Tuphistory[i].getTopuptype();
		  output += "\nTop Up date : "+Tuphistory[i].getTopupdate();
          output += "\nAmount : "+Tuphistory[i].getTopup();
		  output += "\nLast Balance : "+Tuphistory[i].getLastbal();
		  output += "\n\n------------------------------------------------------------------------";
          
   }          
		  JOptionPane.showMessageDialog(null,output);
}

	
}