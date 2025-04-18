public class Accounts{
    Accounts(){}
    // Accounts(String accID, String accName, Double balance){
    //     this.accID = accID;
    //     this.accName = accName;
    //     this.balance = balance;
    // }

    Accounts(String accID, String accName, String password){
        this.accID = accID;
        this.accName = accName;
        this.password = password;
    }

    Accounts(double balance){
        this.balance = balance;
    }

    //Attributes and Operators Below
    protected String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    protected String accID;
    public String getAccID() {
        return accID;
    }
    public void setAccID(String accID) {
        this.accID = accID;
    }

    protected String accName;
    public String getAccName() {
        return accName;
    }
    public void setAccName(String accName) {
        this.accName = accName;
    }

    protected double balance;
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double payBill(double paybill){
        balance -= paybill;
        return balance;
    }

    public double deposit(double deposit){
        balance += deposit;
        return balance;
    }

    public double withdraw (double withdraw){
        balance -= withdraw;
        return balance;
    }

    public double tranfer (double tranfer){
        balance -= tranfer;
        return balance;
    }

    public double topup (double topup){
        balance -= topup;
        return balance;
    }
}
