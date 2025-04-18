import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;



public class Tranfer01 extends MainATM {
    static int size = 1000;
    static Depohis []d = new Depohis[size];
    static Tranferhis []a = new Tranferhis[size];
    static int count=0;

    JFrame menu = new JFrame("Money Transfer");

    public static void main(String[] args){
        b.menu();
    }

    public void menu(){
        JPanel panel1 = new JPanel();
        String Lb = "Money Transfer";
        JLabel label = new JLabel("<html><div style = 'text -align:center'><h1>"+Lb+"</h1></html>",SwingConstants.LEFT);
        
        
        JButton bkbutton = new JButton("Tranfer");
        JButton sbbutton = new JButton("Show Transfer History");
        JButton exbutton = new JButton("Return to main menu");
        


        panel1.setLayout(new GridLayout(3,1));
    

        
        panel1.add(bkbutton);
        panel1.add(sbbutton);
        panel1.add(exbutton);
        

        menu.add(label,BorderLayout.NORTH);
        menu.add(panel1,BorderLayout.CENTER);

        menu.setSize(300,300);
		menu.setLocationRelativeTo(null);
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setVisible(true);

        
        
        bkbutton.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent e){
				menu.dispose();
				b.tranfer();
				b.menu();
			}
		});


        sbbutton.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent e){
			menu.dispose();
			b.showbank();
            b.menu();
			}
		});
        


        exbutton.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent e){
			menu.dispose();
			OS.menu();
			}
		});
        

    }

    public void tranfer(){
    // string account;
    Double tranfer1 = 0.00;
    boolean found = false;
    boolean isTransfered = false;
    String desaccid = "";
    int desaccid1 = 0;
    while(isTransfered==false){
        desaccid = JOptionPane.showInputDialog("Please input destination account ID\nREMARK: That account ID must registered into this system!");
        for(int i=0; i<countA; i++){
            if(A[i].accID.equals(desaccid)){
                found = true;
                desaccid1 = i;
                break;
            }else{
                found = false;
            }        
        }
    
        if(found==true){
            tranfer1 = Double.parseDouble(JOptionPane.showInputDialog("Input amount to transfer"));        
            //การโอนจะโอนน้อยกว่า 0 หรือ มากกว่ายอดคงเหลือไม่ได้
            if(tranfer1<=0 || tranfer1>A[accNo].getBalance()){
                JOptionPane.showMessageDialog(null,"Unable to make a transaction. Please try again.");
            }else{
                double res = JOptionPane.showConfirmDialog(null,"Are you sure to tranfer THB "+tranfer1+" from your account?","tranfer Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
        
                if(res == JOptionPane.YES_OPTION){
                    A[accNo].tranfer(tranfer1);
                    double bathtranfer1 = A[accNo].getBalance();
                            
                    Calendar d = Calendar.getInstance();
                    SimpleDateFormat f = new SimpleDateFormat("dd MMMM YYYY HH:mm:ss");
                    String bankdate1 = f.format(d.getTime());
                    String destinationacc = A[desaccid1].getAccID()+" ("+A[desaccid1].getAccName()+")";
                    a[count] = new Tranferhis(bankdate1,tranfer1,destinationacc,bathtranfer1);
                    A[desaccid1].setBalance(tranfer1);
                    count++;
                    isTransfered = true;
                }
            }
        }else{
            JOptionPane.showMessageDialog(null, "No account ID "+desaccid+" in the system, please try again");
        }        
    }    
}

    public void showbank(){
    String output = "*****************Transfer History*****************";
    for(int i=0 ; i<count;i++){
        output += "\nTRANSFER THB "+a[i].getTranfer();
        output +="\nDate : "+a[i].getBankdate();
        output +="\nTo : "+a[i].getDestination();
        output +="\nLast balance since transfer : THB "+a[i].getBathtranfer();
        output +="\n********************************************************";
    }
    JOptionPane.showMessageDialog(null,output);
}   
}
