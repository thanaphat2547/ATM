public class Withdrawhis extends Accounts{
    public String withdrawdate;
    public String getWithdrawdate() {
        return withdrawdate;
    }

    public void setWithdrawdate(String withdrawdate) {
        this.withdrawdate = withdrawdate;
    }

    public double withdrawOUT;
    public double getWithdrawOUT() {
        return withdrawOUT;
    }

    public void setWithdrawOUT(double withdrawOUT) {
        this.withdrawOUT = withdrawOUT;
    }

    public double lastwithdrawBal;

    public double getLastwithdrawBal() {
        return lastwithdrawBal;
    }

    public void setLastwithdrawBal(double lastwithdrawBal) {
        this.lastwithdrawBal = lastwithdrawBal;
    }

    Withdrawhis(){}

    Withdrawhis(String withdrawdate, double withdrawOUT, double lastwithdrawBal){
        this.withdrawdate = withdrawdate;
        this.withdrawOUT = withdrawOUT;
        this.lastwithdrawBal = lastwithdrawBal;
    }
}
