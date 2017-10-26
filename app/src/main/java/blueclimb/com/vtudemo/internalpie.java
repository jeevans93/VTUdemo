package blueclimb.com.vtudemo;

/**
 * Created by 3lok on 26-Oct-17.
 */

public class internalpie {
    String internalsubbutton;
    String ia1,ia2,ia3,avg;
    String remmrks;



    public internalpie(String isb, String i1, String i2, String i3, String av, String rem)
    {
        internalsubbutton=isb;
        ia1=i1;
        ia2=i2;
        ia3=i3;
        avg=av;
        remmrks=rem;
    }

    public String getRemmrks() {
        return remmrks;
    }

    public String getAvg() {
        return avg;
    }

    public String getIa1() {
        return ia1;
    }

    public String getIa2() {
        return ia2;
    }

    public String getIa3() {
        return ia3;
    }

    public String getInternalsubbutton() {
        return internalsubbutton;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }

    public void setIa1(String ia1) {
        this.ia1 = ia1;
    }
    public void setIa2(String ia2){
        this.ia2 = ia2;
    }

    public void setIa3(String ia3) {
        this.ia3 = ia3;
    }

    public void setInternalsubbutton(String internalsubbutton) {
        this.internalsubbutton = internalsubbutton;
    }

    public void setRemmrks(String remmrks) {
        this.remmrks = remmrks;
    }

}
