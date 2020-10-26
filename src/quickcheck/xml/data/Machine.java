package quickcheck.xml.data;

import java.util.ArrayList;

public class Machine {

    private int serialNumber;

    private String name;

    private ArrayList<Modality> modality = new ArrayList<>();

    public Machine() {
        this.serialNumber = (int) Math.random();
    }

    
    public Machine(String name) {
        this();
        this.name = name;
    }

    
    
    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Modality> getModality() {
        return modality;
    }

    public void setModality(ArrayList<Modality> modality) {
        this.modality = modality;
    }
    
    
}
