public class Tranferhis extends Accounts{
    public String bankdate;
    //เงินที่จะโอน
    public double tranferin;
    //คงเหลือ
    public double bathtranfer;
    public String destination;

    public String getDestination() {
        return destination;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }
    public Tranferhis(String bankdate1,double tranfer1, String destination, double bathtranfer1){
        this.bankdate = bankdate1;
        this.tranferin = tranfer1;
        this.destination = destination;
        this.bathtranfer = bathtranfer1; 
   }
    public Tranferhis(double tranfer1,double bathtranfer1){
        this.tranferin = tranfer1;
        this.bathtranfer = bathtranfer1; 
    }

    

    public void setBankdate (String bankdate1){
        this.bankdate = bankdate1;
    }

    public String getBankdate(){
        return bankdate;
    }

    public void setTranfer(Double tranfer1 ){
        this.tranferin = tranfer1;
    }

    public Double getTranfer(){
        return tranferin;

    }

    public void setBathtranfer(Double bathtranfer1 ){
        this.bathtranfer = bathtranfer1; 
    }

    public Double getBathtranfer(){
        return bathtranfer;

    }






}
