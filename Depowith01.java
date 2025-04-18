import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JOptionPane;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Depowith01 extends MainATM{
	static Depowith01 d = new Depowith01();
	static int size = 100;
	static Depohis []dphistory = new Depohis[size];
	static Withdrawhis []withdrawhistory = new Withdrawhis[size];
	static int countd = 0, countw = 0;

	JFrame manu = new JFrame("Main Menu");

	public static void main(String[] args){
	 d.manu();
	}

	public void manu(){
		JPanel panel1 = new JPanel();
		String Lb ="Main menu";
		JLabel label = new JLabel("<html><div style = 'text -align:center'><h1>"+Lb+"</h1></html>",SwingConstants.LEFT);

		JButton depobutton = new JButton("Deposit");
		JButton shdepobutton = new JButton("Show deposit history");
		JButton withbutton = new JButton("Withdraw");
		JButton shwithbutton = new JButton("Show withdraw history");
		JButton exbutton = new JButton("Return to main menu");
		
		panel1.setLayout(new GridLayout(5,1));

		panel1.add(depobutton);
		panel1.add(shdepobutton);
		panel1.add(withbutton);
		panel1.add(shwithbutton);
		panel1.add(exbutton);


		manu.add(label,BorderLayout.NORTH);
		manu.add(panel1,BorderLayout.CENTER);
		
		manu.setSize(300,300);
		manu.setLocationRelativeTo(null);
		manu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		manu.setVisible(true);

		depobutton.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent e){
				manu.dispose();
				d.Deposit();
				d.manu();
			}
		});

		shdepobutton.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent e){
			manu.dispose();
			d.showdeposit();
			}
		});

		withbutton.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent e){
			manu.dispose();
			d.Withdrew();
			d.manu();
			}
		});

		shwithbutton.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent e){
			manu.dispose();
			d.showwithdrew();
			}
		});

		exbutton.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent e){
			manu.dispose();
			OS.menu();
			}
		});

	}

	
	public void Deposit(){
		double depoIN = 0;
		while(depoIN<0 || depoIN<99 || depoIN%100 != 0){
			depoIN = Integer.parseInt(JOptionPane.showInputDialog("How much do you want to deposit to your account?"));
			if(depoIN < 0 || depoIN < 99 || depoIN%100 != 0  ){
				JOptionPane.showMessageDialog(null,"Unable to make a transaction. Please try again.");
			}else{
				int res = JOptionPane.showConfirmDialog(null, "Are you sure to deposit of THB "+depoIN+" ?", "Deposit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
				if (res == JOptionPane.YES_OPTION){
					A[accNo].deposit(depoIN);
					double lastBal = A[accNo].getBalance();

					Calendar d = Calendar.getInstance();
					SimpleDateFormat f = new SimpleDateFormat("dd MMMM YYYY HH:mm:ss");
					String depoDate = f.format(d.getTime());
					dphistory[countd] = new Depohis(depoDate, depoIN, lastBal);
				}
			}
		}
		countd++;
	}

	public void showdeposit(){
		String output = "**********DEPOSIT HISTORY**********";
			for(int i=0; i<countd; i++){
				output += "\nDeposit THB "+dphistory[i].getDepoIN();
				output += "\nDate : "+dphistory[i].getDepoDate();
				output += "\nLast balance since deposit : THB "+dphistory[i].getLastBal()+"\n";
			}
			JOptionPane.showMessageDialog(null,output);
			d.manu();
}

	public void Withdrew(){
		double withdrawOUT = 0;
		do{
			withdrawOUT = Integer.parseInt(JOptionPane.showInputDialog("How much you do want to withdraw out from your account?"));
			if(withdrawOUT<0 || withdrawOUT<99 || withdrawOUT%100 != 0){
				JOptionPane.showMessageDialog(null, "Unable to make a transaction. Please try again.");
			}else{
				int res = JOptionPane.showConfirmDialog(null, "Are you sure to withdraw THB "+withdrawOUT+" from your account?", "Deposit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
				if(res==JOptionPane.YES_OPTION){
					A[accNo].withdraw(withdrawOUT);
					double lastbal = A[accNo].getBalance();

					Calendar d = Calendar.getInstance();
					SimpleDateFormat f = new SimpleDateFormat("dd MMMM YYYY HH:mm:ss");
					String withdrawdate = f.format(d.getTime());
					withdrawhistory[countw] = new Withdrawhis(withdrawdate, withdrawOUT, lastbal);
				}
			}
		}while(withdrawOUT<0 || withdrawOUT<99 || withdrawOUT%100 != 0);
		countw++;
		}

	public void showwithdrew(){
		String output = "**********WITHDRAWAL HISTORY**********";
			for(int i=0; i<countw; i++){
				output += "\nWithdraw THB "+withdrawhistory[i].getWithdrawOUT();
				output += "\nDate : "+withdrawhistory[i].getWithdrawdate();
				output += "\nLast balance since deposit : THB "+withdrawhistory[i].getLastwithdrawBal()+"\n";
			}
			JOptionPane.showMessageDialog(null,output);
			d.manu();
}
	
		
	}


		






 

	

