
public class Room {
    private int id;
    private String renter;
    private int daysToStay;
    private String startDate;
    private int moneyToPay;

    public Room(int id, String renter, int daysToStay, String startDate, int moneyToPay) {
        this.id = id;
        this.renter = renter;
        this.daysToStay = daysToStay;
        this.startDate = startDate;
        this.moneyToPay = moneyToPay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRenter() {
        return renter;
    }

    public void setRenter(String renter) {
        this.renter = renter;
    }

    public int getDaysToStay() {
        return daysToStay;
    }

    public void setDaysToStay(int daysToStay) {
        this.daysToStay = daysToStay;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getMoneyToPay() {
        return moneyToPay;
    }

    public void setMoneyToPay(int moneyToPay) {
        this.moneyToPay = moneyToPay;
    }


    
    
}
