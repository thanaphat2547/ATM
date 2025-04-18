import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class MainATM extends Accounts{
    static final int size = 100;
	static MainATM OS = new MainATM();
    static PayBill pb = new PayBill();
    static Depowith01 d = new Depowith01();
    static Tranfer01 b = new Tranfer01();
    static TopUp T = new TopUp();
    static int countA = 4;
    static Accounts []A = new Accounts[size];
    public double bal = 0;
    static int accNo = 0;
    static int i;
    static int attempt = 0;
    static boolean loginstatus = false;

    static JTextField idlogin = new JTextField(10);
    static JPasswordField passlogin = new JPasswordField(10);

    JPanel loginpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

    JFrame menu = new JFrame("Main Menu...");
    JFrame exitframe = new JFrame();
    JFrame startup = new JFrame();
    JPanel regis = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JFrame LOGIN = new JFrame("Login account");

    public static void main(String[] args){
        A[0] = new Accounts("651435", "Nutkamon Manosatcharak", "nutkamon0");
        A[1] = new Accounts("650873", "Sopida Chiangsaen", "11111111");
        A[2] = new Accounts("651688", "Thanaphat Mothong", "11111111");
        A[3] = new Accounts("godmode", "GOD", "godmode");
        OS.createStartupWindow();
    }

    public void createStartupWindow(){
        int result = JOptionPane.showConfirmDialog(startup, "Do you have an account already?", "User Confirmation",
        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        switch (result){
            case JOptionPane.YES_OPTION : OS.login(); break;
            case JOptionPane.NO_OPTION : OS.register(); break;
        }
    }

    public void exitConfirmation(){
        Object stringArray[] = {"Quit", "Cancel"};
        int result = JOptionPane.showOptionDialog(exitframe, "Do you want to quit the system?", "Quit",
        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, stringArray, stringArray[0]);
        
        switch (result){
            case JOptionPane.YES_OPTION : System.exit(0); break;
            case JOptionPane.NO_OPTION : break;
        }
    }

    public void register(){
        //ใช้ JPanel ในการสร้างโครงร่างในการทำ field รับค่าซึ่งใช้ JTextField
        JTextField id = new JTextField(10);
        JTextField name = new JTextField(10);
        JTextField pass = new JTextField(10);

        //สร้าง JFrame เพื่อนำทุกอย่างไปไว้ใน Frame
        JFrame regisFrame = new JFrame("Register new account");
        regisFrame.setLayout(new GridLayout(3,1));
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5,1));
        JLabel idL = new JLabel("Account ID: ");
        JLabel nameL = new JLabel("Account Name: ");
        JLabel passL = new JLabel("Password: ");


        panel.add(idL);
        panel.add(regis.add(id));
        panel.add(nameL);
        panel.add(regis.add(name));
        panel.add(passL);
        panel.add(regis.add(pass));

        JButton addbtn = new JButton();
        addbtn.setText("Register Account");
        panel.add(addbtn);
        addbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String accID = id.getText();
                String accName = name.getText();
                String password = pass.getText();
                A[countA] = new Accounts(accID, accName, password);
                accNo = countA;
                bal = A[accNo].getBalance();
                
                regisFrame.dispose();
                String out = "Account details: \nAccount ID : "+A[countA].getAccID()+"\nAccount Name : "+A[countA].getAccName();
                JOptionPane.showMessageDialog(null, out);
                
                countA++;
                OS.menu();
            }
        });

        regisFrame.add(panel);

        regisFrame.setSize(300, 300);
        regisFrame.setLocationRelativeTo(null);
        regisFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        regisFrame.setVisible(true);
    }

    public void login(){
        //ใช้ JPanel ในการสร้างโครงร่างในการทำ field รับค่าซึ่งใช้ JTextField
        //สร้าง JFrame เพื่อนำทุกอย่างไปไว้ใน Frame
        // LOGIN.setLayout(new GridLayout(3,1));
        JPanel panel = new JPanel();
        loginpanel.setLayout(new GridLayout(10,1));
        JLabel idLg = new JLabel("Account ID: ");
        JLabel passLg = new JLabel("Password: ");
        JButton loginbtn = new JButton();


        loginpanel.add(idLg);
        loginpanel.add(idlogin);
        loginpanel.add(passLg);
        loginpanel.add(passlogin);
        loginpanel.add(loginbtn);

        
        loginbtn.setText("LOG IN");
        panel.add(loginpanel);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                char[] pw1 = passlogin.getPassword();
                boolean isLoggedIn = false;
                String pw = new String(pw1);
                String user = idlogin.getText();
                for(int i=0; i<countA; i++){
                    if (user.equals(A[i].getAccID()) && pw.equals(A[i].getPassword())) {
                        isLoggedIn = true;
                        accNo = i;
                        break;                      
                    }else{
                        isLoggedIn = false;
                    }
                }

                if(isLoggedIn==true){
                    A[accNo].setBalance(bal);
                    loginstatus = true;
                    OS.menu();
                    LOGIN.dispose();
                }else{
                    attempt++;
                    if(attempt == 4){
                        JOptionPane.showMessageDialog(null, "You've exceeded the attempts. Program will terminate.");
                        System.exit(0);
                    }else{
                        JOptionPane.showMessageDialog(null, "Incorrect password, you have "+(4-attempt)+" attempt(s) remaining...");
                        idlogin.setText("");
                        passlogin.setText("");
                    }
                }                     
            }     
        });

        LOGIN.add(panel);

        LOGIN.setSize(220, 300);
        LOGIN.setLocationRelativeTo(null);
        LOGIN.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        LOGIN.setVisible(true);
    }

    public void menu(){
        JPanel mpanel = new JPanel();
        JPanel mpanel2 = new JPanel();
        String mm = "Welcome, "+A[accNo].getAccName()+"! What do you want to do?";
        JLabel mainmenu = new JLabel("<html><div style = 'text-align: center'><h1>"+mm+"</h1></div></html>",SwingConstants.LEFT);
        JButton depbtn = new JButton("Deposit/Withdrawal");
        JButton trfbtn = new JButton("Transfer");
        JButton pbbtn = new JButton("Pay Bills");
        JButton tupbtn = new JButton("Top Up");
        JButton checkbtn = new JButton("Check Balance");

        JButton logoutbtn = new JButton("Logout / Exit");

        depbtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                d.manu();
                menu.dispose();
            }
        });

        trfbtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                b.menu();
                menu.dispose();
            }
        });

        pbbtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // JOptionPane.showMessageDialog(null, "Proceed to Top-Up Section");
                pb.menu();
                menu.dispose();
            }
        });

        tupbtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                T.menu();
                menu.dispose();
            }
        });

        logoutbtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                OS.exitConfirmation();
            }
        });

        checkbtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String out = "Balance\n";
                out += "Account : "+A[accNo].getAccID()+" | "+A[accNo].getAccName();
                out += "\nBalance : THB "+A[accNo].getBalance();

                JOptionPane.showMessageDialog(null, out);
            }
        });

        mpanel.setLayout(new GridLayout(6,2));
        mpanel2.setLayout(new GridLayout(1,1));
        mpanel.add(depbtn);
        mpanel.add(trfbtn);
        mpanel.add(pbbtn);
        mpanel.add(tupbtn);
        mpanel.add(checkbtn);
        mpanel2.add(logoutbtn);
        
        menu.add(mainmenu, BorderLayout.NORTH);
        menu.add(mpanel, BorderLayout.CENTER);
        menu.add(mpanel2, BorderLayout.SOUTH);
        
        menu.setSize(300,300);
        menu.setLocationRelativeTo(null);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.setVisible(true);
    }
}