import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class PayBill extends MainATM {
    static int ch;
    static int size = 100;
    static int count = 0;
    JFrame payfinequestion = new JFrame();
    JFrame finetype = new JFrame();
    JFrame payutil = new JFrame();
    JFrame phone = new JFrame();
    static double fine = 0;
    static String opt;
    static Paybillhistory []pbh = new Paybillhistory[size];
    static double lastbal = 0;

    static String billtype, billDate;

    public static void main(String[] args){
        pb.menu();
    }

    public void menu(){
        JFrame menu = new JFrame("Pay bill");
        JPanel mpanel = new JPanel();
        JPanel mpanel2 = new JPanel();
        String mm = "What bill(s) do you want to pay ?";
        JLabel mainmenu = new JLabel("<html><div style = 'text-align: center'><h1>"+mm+"</h1></div></html>",SwingConstants.LEFT);
        JButton finebtn = new JButton("Fine bills");
        JButton utilsbtn = new JButton("Utility bills");
        JButton phoneandinternetbtn = new JButton("Phone/Internet Bills");
        JButton history = new JButton("Show bill pay history");
        JButton returnbtn = new JButton("Return to main menu");

        finebtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                menu.dispose();
                pb.chooseFineType();
            }
        });

        utilsbtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e1){
                menu.dispose();
                pb.payUtil();
            }
        });

        phoneandinternetbtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                menu.dispose();
                pb.payPhoneandInternet();
            }
        });

        history.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                menu.dispose();
                pb.showhistory();
            }
        });

        returnbtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                menu.dispose();
                OS.menu();
                return;
            }
        });

        mpanel.setLayout(new GridLayout(4,2));
        mpanel2.setLayout(new GridLayout(1,1));
        mpanel.add(finebtn);
        mpanel.add(utilsbtn);
        mpanel.add(phoneandinternetbtn);
        mpanel.add(history);
        mpanel2.add(returnbtn, SwingConstants.CENTER);
        
        menu.add(mainmenu, BorderLayout.NORTH);
        menu.add(mpanel, BorderLayout.CENTER);
        menu.add(mpanel2, BorderLayout.SOUTH);
        
        menu.setSize(300,300);
        menu.setLocationRelativeTo(null);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.setVisible(true);
    }

    public void payFineQuestion(){
        String question = "Do you want to pay "+opt+" ?";

        int ch = JOptionPane.showConfirmDialog(payfinequestion, question, "Pay Fine Bills", 
        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        switch (ch){
            case JOptionPane.YES_OPTION : A[accNo].payBill(fine); 
                lastbal = A[accNo].getBalance();
                billDate = getpaybilldate();
                pbh[count] = new Paybillhistory(billDate, opt, fine, lastbal);
                count++;
                JOptionPane.showMessageDialog(null, "You have paid "+fine+" baht for paying "+opt+" successfully\nYour Balance : "+A[accNo].getBalance()+" baht"); break;
            case JOptionPane.NO_OPTION : payfinequestion.dispose();
        }

    }

    public void chooseFineType(){
        String ftq = "What type of fines do you want to pay?";
        JPanel ftypePanel = new JPanel();
        String finestring[] = {"ค่าปรับ ข้อหาขับรถเร็วกว่าความเร็วที่กำหนด", "ค่าปรับ ข้อหาขับรถผ่าสัญญาณไฟจราจร", "ค่าปรับ ข้อหากลับรถในที่ห้ามกลับรถ", "ยกเลิก และกลับหน้าหลัก"};
        JButton speedbtn = new JButton(finestring[0]);
        JButton redlightbtn = new JButton(finestring[1]);
        JButton uturnbtn = new JButton(finestring[2]);
        JButton cancelbtn = new JButton(finestring[3]);
        JLabel ftypequestion = new JLabel("<html><div style = 'text-align: center'><h1>"+ftq+"</h1></div></html>");
        ftypePanel.setLayout(new GridLayout(5,1));
        ftypePanel.add(ftypequestion);
        ftypePanel.add(speedbtn);
        ftypePanel.add(redlightbtn);
        ftypePanel.add(uturnbtn);
        ftypePanel.add(cancelbtn);

        finetype.add(ftypePanel);

        finetype.setSize(300,300);
        finetype.setLocationRelativeTo(null);
        finetype.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        finetype.setVisible(true);

        speedbtn.addActionListener(new ActionListener(){
            public void actionPerformed (ActionEvent e){
                opt = finestring[0];
                fine = 500;
                payFineQuestion();
            }
        });

        redlightbtn.addActionListener(new ActionListener(){
            public void actionPerformed (ActionEvent e){
                opt = finestring[1];
                fine = 500;
                payFineQuestion();
            }
        });

        uturnbtn.addActionListener(new ActionListener(){
            public void actionPerformed (ActionEvent e){
                opt = finestring[2];
                fine = 500;
                payFineQuestion();
            }
        });   

        cancelbtn.addActionListener(new ActionListener(){
            public void actionPerformed (ActionEvent e){
                finetype.dispose();
                pb.menu();
            }
        });   
    }

    public void payUtil(){
        JPanel panel = new JPanel();
        JPanel panel2 = new JPanel();
        String text = "What bills you want to pay?";
        JLabel header = new JLabel("<html><div style = 'text-align: center'><h1>"+text+"</h1></div></html>");
        panel.setLayout(new GridLayout(4,1));
        panel2.setLayout(new GridLayout(1,1));

        JButton elecbtn = new JButton("Electricity Bill");
        JButton waterbtn = new JButton("Water Bill");
        JButton returnbtn = new JButton("Return to last menu");

        payutil.add(header, BorderLayout.NORTH);
        panel.add(elecbtn);
        panel.add(waterbtn);
        panel2.add(returnbtn);

        payutil.add(panel, BorderLayout.CENTER);
        payutil.add(panel2, BorderLayout.SOUTH);

        payutil.setSize(300,300);
        payutil.setLocationRelativeTo(null);
        payutil.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        payutil.setVisible(true);

        //add actionlistener
        returnbtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                payutil.dispose();
                pb.menu();
            }
        });

        elecbtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                double elec = 0;
                elec = Double.parseDouble(JOptionPane.showInputDialog("How much do you want to pay \"Electricity Bills\" ?"));
                if(elec<=A[accNo].getBalance()){
                    A[accNo].payBill(elec);
                    billtype = "Electricity Bills";
                    lastbal = A[accNo].getBalance();
                    JOptionPane.showMessageDialog(null, "You paid "+elec+" baht for "+billtype+"\nYour balance : "+A[accNo].getBalance()+" baht");
                    billDate = getpaybilldate();
                    pbh[count] = new Paybillhistory(billDate, billtype, elec, lastbal);
                    count++;
                }else{
                    JOptionPane.showMessageDialog(null, "Can\'t pay electricity bill due to insuffient balance. Please try again.");
                }//add if clause and apply into account classes
            }
        });

        waterbtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                double water = 0;
                water = Double.parseDouble(JOptionPane.showInputDialog("How much do you want to pay \"Water Bills\" ?"));
                if(water<=A[accNo].getBalance()){
                    A[accNo].payBill(water);
                    lastbal = A[accNo].getBalance();
                    billtype = "Water Bills";
                    JOptionPane.showMessageDialog(null, "You paid "+water+" baht for "+billtype+"\nYour balance : "+A[accNo].getBalance()+" baht");
                    billDate = getpaybilldate();
                    pbh[count] = new Paybillhistory(billDate, billtype, water, lastbal);
                    count++;
                }
            }
        });
    }

    public String getpaybilldate(){
        Calendar d = Calendar.getInstance();
		SimpleDateFormat f = new SimpleDateFormat("dd MMMM YYYY HH:mm:ss");
		String billdate = f.format(d.getTime());

        return billdate;
    }

    public void payPhoneandInternet(){
        JPanel panel = new JPanel();
        String maintext = "What bills do you want to pay?";
        String phoneString[] = {"Phone Bills", "Home Internet Bills", "Return and go to last menu"};
        JButton phonebill = new JButton(phoneString[0]);
        JButton homeinternetbill = new JButton(phoneString[1]);
        JButton returnbtn  = new JButton(phoneString[2]);
        JLabel header = new JLabel("<html><div style = 'text-align: center'><h1>"+maintext+"</h1></div></html>");

        panel.setLayout(new GridLayout(4,1));
        panel.add(header);
        panel.add(phonebill);
        panel.add(homeinternetbill);
        panel.add(returnbtn);

        phone.add(panel);
        phone.setSize(300,300);
        phone.setLocationRelativeTo(null);
        phone.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        phone.setVisible(true);

        phonebill.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String phonenumber = "";
                double amount =0;
                do{
                    phonenumber = JOptionPane.showInputDialog("**THIS IS POSTPAID NUMBER BILLS ONLY**\nPlease provide your 10-digit phone number :");
                    if(phonenumber.length() == 10 && phonenumber.length() > 0){
                        amount = Double.parseDouble(JOptionPane.showInputDialog("**THIS IS POSTPAID NUMBER BILLS ONLY**\nPlease enter your amonut to pay :"));
                        if(amount>0){
                            int ans = JOptionPane.showConfirmDialog(null, "Are you sure to pay "+amount+" baht to number "+phonenumber+" bill ?", "Payment confirmation", JOptionPane.YES_NO_OPTION);
                            if(ans == JOptionPane.YES_OPTION){
                                A[accNo].payBill(amount); 
                                lastbal = A[accNo].getBalance();
                                billDate = getpaybilldate(); 
                                billtype = "Phone bill number: "+phonenumber;
                                pbh[count] = new Paybillhistory(billDate, billtype, amount, lastbal); 
                                count++;
                            }else{
                                JOptionPane.showMessageDialog(null, "You've cancelled your payment...");
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Unable to make a transaction. Please try again.");
                    }
                }while((phonenumber.length()!=10 && phonenumber.length()<0 && amount<0));
            }
        });

        homeinternetbill.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String internetid;
                double amount = 0;
                do {
                    internetid = JOptionPane.showInputDialog("Please input your 10-digit internet ID :");
                    if(internetid.length()==9&&internetid.length()>0){
                        amount = Double.parseDouble(JOptionPane.showInputDialog("Please enter your amonut to pay to internet ID "+internetid+" :"));
                        if(amount>0){
                            int ans = JOptionPane.showConfirmDialog(null, "Are you sure to pay "+amount+" baht to internet ID "+internetid+" bill ?", "Payment confirmation", JOptionPane.YES_NO_OPTION);
                            if(ans == JOptionPane.YES_OPTION){
                                A[accNo].payBill(amount); 
                                lastbal = A[accNo].getBalance();
                                billDate = getpaybilldate(); 
                                billtype = "Internet bill ID: "+internetid;
                                pbh[count] = new Paybillhistory(billDate, billtype, amount, lastbal); 
                                count++;
                            }else{
                                JOptionPane.showMessageDialog(null, "You've cancelled your payment...");
                            }
                        }
                    }
                } while (internetid.length()!=9&&internetid.length()<0&&amount<0);
            }
        });

        returnbtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                phone.dispose();
                pb.menu();
            }
        });
    }

    public void showhistory(){
        String output = "Pay bill history";
        for(int i=0; i<count; i++){
            output += "\n\nBill type : "+pbh[i].getBilltype();
            output += "\nIssue date : "+pbh[i].getBilldate();
            output += "\nAmount : "+pbh[i].getAmount();
            output += "\nLast balance : "+pbh[i].getLastbal();
        }
        JOptionPane.showMessageDialog(null, output);
        pb.menu();
    }
}
