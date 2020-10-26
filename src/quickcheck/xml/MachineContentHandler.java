package quickcheck.xml;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import quickcheck.xml.data.Energy;
import quickcheck.xml.data.Machine;
import quickcheck.xml.data.MeasData;
import quickcheck.xml.data.Modality;

/**
 *
 * @author Steffen Häsler
 */
public class MachineContentHandler implements ContentHandler {

    private final HashMap<String, Machine> alleMachine = new HashMap<>();
    private String currentValue;
    private Machine machine;
    private Modality modality;
    private Energy energy;
    private MeasData measData;
    private int fieldSize;
    private int ssd;
    private int degreeWedge;
    private int mu;
    private boolean foundNewDataset = false;
    private boolean foundCAX = false;
    private boolean foundG10 = false;
    private boolean foundL10 = false;
    private boolean foundT10 = false;
    private boolean foundR10 = false;
    private boolean foundG20 = false;
    private boolean foundL20 = false;
    private boolean foundT20 = false;
    private boolean foundR20 = false;
    private boolean foundE1 = false;
    private boolean foundE2 = false;
    private boolean foundE3 = false;
    private boolean foundE4 = false;
    private boolean foundTemp = false;
    private boolean foundPressure = false;
    private Date measDate = new Date();
    

    // Aktuelle Zeichen die gelesen werden, werden in eine Zwischenvariable
    // gespeichert
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        currentValue = new String(ch, start, length);
    }

    // Methode wird aufgerufen wenn der Parser zu einem Start-Tag kommt
    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes atts) throws SAXException {
        if (qName.equals("TrendData")) {
            this.foundNewDataset = true;
            machine = null;
            SimpleDateFormat datumsformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try {
                this.measDate = datumsformat.parse(atts.getValue("date"));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        if (qName.equals("MeasData")) {
            this.measData = new MeasData(measDate);
        }
        if (qName.equals("CAX")) {
            this.foundCAX = true;
        }
        if (qName.equals("G10")) {
            this.foundG10 = true;
        }
        if (qName.equals("L10")) {
            this.foundL10 = true;
        }
        if (qName.equals("T10")) {
            this.foundT10 = true;
        }
        if (qName.equals("R10")) {
            this.foundR10 = true;
        }
        if (qName.equals("G20")) {
            this.foundG20 = true;
        }
        if (qName.equals("L20")) {
            this.foundL20 = true;
        }
        if (qName.equals("T20")) {
            this.foundT20 = true;
        }
        if (qName.equals("R20")) {
            this.foundR20 = true;
        }
        if (qName.equals("E1")) {
            this.foundE1 = true;
        }
        if (qName.equals("E2")) {
            this.foundE2 = true;
        }
        if (qName.equals("E3")) {
            this.foundE3 = true;
        }
        if (qName.equals("E4")) {
            this.foundE4 = true;
        }
        if (qName.equals("Temp")){
            this.foundTemp = true;
        }
        if (qName.equals("Pressure")){
            this.foundPressure = true;
        } 
    }

    // Methode wird aufgerufen wenn der Parser zu einem End-Tag kommt
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        if (this.foundNewDataset) {
            if (qName.equals("TreatmentUnit")) {
                if (!alleMachine.containsKey(currentValue)) {
                    this.alleMachine.put(currentValue, new Machine(currentValue));
                }
                machine = alleMachine.get(currentValue);
            }
            if (qName.equals("Modality")) {
                Modality tempModality = new Modality(currentValue);
                if (findModality(tempModality.getName(), machine) != -1) {
                    this.modality = machine.getModality().get(findModality(tempModality.getName(), this.machine));
                } else {
                    modality = tempModality;
                    this.machine.getModality().add(modality);
                }

            }
            if (qName.equals("Energy")) {
                Energy tempEnergy = new Energy(Integer.parseInt(currentValue));
                if (findEnergy(tempEnergy.getEnergy(), this.modality) != -1) {
                    this.energy = modality.getEnergy().get(findEnergy(tempEnergy.getEnergy(), this.modality));
                } else {
                    this.energy = tempEnergy;
                    this.modality.getEnergy().add(energy);
                }

            }

            if (qName.equals("Fieldsize")) {
                fieldSize = Integer.parseInt(currentValue.substring(0, 3));
            }
            if (qName.equals("SDD")) {
                this.ssd = Integer.parseInt(currentValue);
                this.energy.addSSD(ssd);
            }
            if (qName.equals("Wedge") && isNumber(currentValue)) {
                this.degreeWedge = Integer.parseInt(currentValue);
                this.energy.addWedge(this.degreeWedge);
            }
            if (qName.equals("MU")) {
                this.mu = Integer.parseInt(currentValue);
            }
            if (qName.equals("Value")) {
                if (this.foundCAX) {
                    this.measData.setCax(Double.parseDouble(currentValue));
                    this.foundCAX = false;
                }

                if (this.foundG10) {
                    this.measData.setG10(Double.parseDouble(currentValue));
                    this.foundG10 = false;
                }
                if (this.foundT10) {
                    this.measData.setT10(Double.parseDouble(currentValue));
                    this.foundT10 = false;
                }
                if (this.foundL10) {
                    this.measData.setL10(Double.parseDouble(currentValue));
                    this.foundL10 = false;
                }
                if (this.foundR10) {
                    this.measData.setR10(Double.parseDouble(currentValue));
                    this.foundR10 = false;
                }
                if (this.foundG20) {
                    this.measData.setG20(Double.parseDouble(currentValue));
                    this.foundG20 = false;
                }
                if (this.foundT20) {
                    this.measData.setT20(Double.parseDouble(currentValue));
                    this.foundT20 = false;
                }
                if (this.foundL20) {
                    this.measData.setL20(Double.parseDouble(currentValue));
                    this.foundL20 = false;
                }
                if (this.foundR20) {
                    this.measData.setR20(Double.parseDouble(currentValue));
                    this.foundR20 = false;
                }
                if (this.foundE1) {
                    this.measData.setE1(Double.parseDouble(currentValue));
                    this.foundE1 = false;
                }
                if (this.foundE2) {
                    this.measData.setE2(Double.parseDouble(currentValue));
                    this.foundE2 = false;
                }
                if (this.foundE3) {
                    this.measData.setE3(Double.parseDouble(currentValue));
                    this.foundE3 = false;
                }
                if (this.foundE4) {
                    this.measData.setE4(Double.parseDouble(currentValue));
                    this.foundE4 = false;
                }
                if (this.foundTemp) {
                    this.measData.setTemp(Double.parseDouble(currentValue));
                    this.foundTemp = false;
                }
                if (this.foundPressure) {
                    this.measData.setPressure(Double.parseDouble(currentValue));
                    this.foundPressure = false;
                }
            }

            if (qName.equals("TrendData")) {
                this.foundNewDataset = false;
                this.measData.setFieldsize(this.fieldSize);
                this.measData.setSsd(this.ssd);
                this.measData.setDegree(this.degreeWedge);
                this.measData.setMu(this.mu);
                this.energy.getMeasValue().add(measData);

            }
        }
    }

    public HashMap<String, Machine> getMachineList() {
        return this.alleMachine;
    }

    @Override
    public void setDocumentLocator(Locator locator) {
    }

    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void endDocument() throws SAXException {
    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {
    }

    @Override
    public void skippedEntity(String name) throws SAXException {
    }

    private int findModality(String name, Machine machine) {
        int returnValue = -1;
        for (int i = 0; i < machine.getModality().size(); i++) {
            if (machine.getModality().get(i).getName().equals(name)) {
                returnValue = i;
                i = machine.getModality().size();
            }
        }
        return returnValue;

    }

    private int findEnergy(int energy, Modality modality) {
        int returnValue = -1;
        for (int i = 0; i < modality.getEnergy().size(); i++) {
            if (modality.getEnergy().get(i).getEnergy() == energy) {
                returnValue = i;
                i = modality.getEnergy().size();
            }
        }
        return returnValue;

    }

    private boolean isNumber(String string) {
        try {
            Double.parseDouble(string);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
