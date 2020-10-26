package quickcheck.xml.data;

public abstract class AnalyseTerm {

    private double value;

    private double max;

    private double min;

    private final MeasData measData;
    
    public AnalyseTerm (MeasData measData) {
        this.measData = measData;
    }
}
