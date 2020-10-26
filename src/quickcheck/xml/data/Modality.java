package quickcheck.xml.data;

import java.util.ArrayList;

public class Modality {

    private int serialNumber;

    private String name;

    private ArrayList<Energy> energy = new ArrayList<>();

    public Modality(String name) {
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

    public ArrayList<Energy> getEnergy() {
        return energy;
    }

    public void setEnergy(ArrayList<Energy> energy) {
        this.energy = energy;
    }
    
    @Override
    public String toString() {
    	return this.name;
    }
}
