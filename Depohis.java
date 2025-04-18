public class Depohis extends Accounts {
    public String depoDate;
    public String getDepoDate() {
        return depoDate;
    }

    public void setDepoDate(String depoDate) {
        this.depoDate = depoDate;
    }

    public double depoIN;
    public double getDepoIN() {
        return depoIN;
    }

    public void setDepoIN(double depoIN) {
        this.depoIN = depoIN;
    }

    public double lastBal;
    public double getLastBal() {
        return lastBal;
    }

    public void setLastBal(double lastBal) {
        this.lastBal = lastBal;
    }

    public Depohis(String depoDate, double depoIN, double lastBal) {
        this.depoDate = depoDate;
        this.depoIN = depoIN;
        this.lastBal = lastBal;
    }
}
