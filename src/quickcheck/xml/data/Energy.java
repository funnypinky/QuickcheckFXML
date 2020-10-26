package quickcheck.xml.data;

import java.util.ArrayList;
import java.util.Arrays;

public class Energy {

    private int energy;

    private ArrayList<MeasData> measValue = new ArrayList<>();
    private ArrayList<Integer> wedges = new ArrayList<Integer>();
    private  ArrayList<Integer> fields = new ArrayList<Integer>();
    private ArrayList<Integer> ssd =new ArrayList<Integer>();

    public Energy(int energy) {
        this.energy = energy;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public ArrayList<MeasData> getMeasValue() {
        return measValue;
    }

    public void setMeasValue(ArrayList<MeasData> measValue) {
        this.measValue = measValue;
    }

    public void addWedge(int wedgeValue) {
        if(!this.wedges.contains(wedgeValue)) {
        	this.wedges.add(wedgeValue);
        }
    }

    public ArrayList<Integer> getWedges() {
        return this.wedges;
    }

    public void addSSD(int ssdValue) {
    	if(!this.ssd.contains(ssdValue)) {
        	this.ssd.add(ssdValue);
        }
    }

    public ArrayList<Integer> getField() {
        for (MeasData measItem : measValue) {
        	if(!this.fields.contains(measItem.getFieldsize())) {
            	this.fields.add(measItem.getFieldsize());
            }
        }
        return this.fields;
    }

    public ArrayList<Integer> getSSD() {
        return this.ssd;
    }

    public ArrayList<MeasData> getMeasValue(int wedge, int field, int ssd) {
        ArrayList<MeasData> returnList = new ArrayList<>();
        for (MeasData measItem : measValue) {
            if ((measItem.getDegree() == wedge && measItem.getFieldsize() == field) && measItem.getSsd() == ssd) {
                returnList.add(measItem);
            }
        }
        return returnList;
    }
    
    @Override
    public String toString() {
    	return Integer.toString(this.energy);
    }
}
