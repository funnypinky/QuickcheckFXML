package quickcheck.xml.data;

import java.util.Arrays;
import java.util.Date;

public class MeasData {

    private Date date;

    private double cax;

    private double g10;

    private double l10;

    private double t10;

    private double r10;

    private double g20;

    private double l20;

    private double t20;

    private double r20;

    private double e1;

    private double e2;

    private double e3;

    private double e4;
    
    private double symGT;
    
    private double symLR;
    
    private double flatness;
    
    private int degree;
    
    private int ssd;
    
    private int mu;
    
    private int fieldsize;

    private double pressure;

    private double temp;

    private double caxRate;

    private Analyse analyse;
    
    private String trendDataID = new String();

    public MeasData(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getCax() {
        return cax;
    }

    public void setCax(double cax) {
        this.cax = cax;
    }

    public double getG10() {
        return g10;
    }

    public void setG10(double g10) {
        this.g10 = g10;
    }

    public double getL10() {
        return l10;
    }

    public void setL10(double l10) {
        this.l10 = l10;
    }

    public double getT10() {
        return t10;
    }

    public void setT10(double t10) {
        this.t10 = t10;
    }

    public double getR10() {
        return r10;
    }

    public void setR10(double r10) {
        this.r10 = r10;
    }

    public double getG20() {
        return g20;
    }

    public void setG20(double g20) {
        this.g20 = g20;
    }

    public double getL20() {
        return l20;
    }

    public void setL20(double l20) {
        this.l20 = l20;
    }

    public double getT20() {
        return t20;
    }

    public void setT20(double t20) {
        this.t20 = t20;
    }

    public double getR20() {
        return r20;
    }

    public void setR20(double r20) {
        this.r20 = r20;
    }

    public double getE1() {
        return e1;
    }

    public void setE1(double e1) {
        this.e1 = e1;
    }

    public double getE2() {
        return e2;
    }

    public void setE2(double e2) {
        this.e2 = e2;
    }

    public double getE3() {
        return e3;
    }

    public void setE3(double e3) {
        this.e3 = e3;
    }

    public double getE4() {
        return e4;
    }

    public void setE4(double e4) {
        this.e4 = e4;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getCaxRate() {
        return caxRate;
    }

    public void setCaxRate(double caxRate) {
        this.caxRate = caxRate;
    }

    public Analyse getAnalyse() {
        return analyse;
    }

    public void setAnalyse(Analyse analyse) {
        this.analyse = analyse;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public int getSsd() {
        return ssd;
    }

    public void setSsd(int ssd) {
        this.ssd = ssd;
    }

    public int getMu() {
        return mu;
    }

    public void setMu(int mu) {
        this.mu = mu;
    }

    public int getFieldsize() {
        return fieldsize;
    }

    public void setFieldsize(int fieldsize) {
        this.fieldsize = fieldsize;
    }

    public String getTrendDataID() {
        return trendDataID;
    }

    public void setTrendDataID(String trendDataID) {
        this.trendDataID = trendDataID;
    }

    public double getSymGT() {
        double diff10 = Math.abs(g10 - t10);
        double diff20 = Math.abs(g20 - t20);
        symGT = 100*Math.max(diff10, diff20)/cax;
        return symGT;
    }

    public double getSymLR() {
        double diff10 = Math.abs(l10 - r10);
        double diff20 = Math.abs(l20 - r20);
        symLR = 100*Math.max(diff10, diff20)/cax;
        return symLR;
    }

    public double getFlatness() {
        double[] values = {g10,t10,l10,r10,g20,t20,l20,r20};
        Arrays.sort(values);
        double max = values[values.length-1];
        double min = values[0];
        flatness = 100*Math.abs(max-min)/(max+min);
        return flatness;
    }
    
    
}
