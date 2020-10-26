package quickcheck.xml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import quickcheck.xml.data.Machine;



/**
 * statische Klasse zum Parsen von Quickcheck-Datei der Firma PTW.
 *
 * @author Steffen Häsler
 * @version 1.0
 */
public class Parser {
	private static File tempFile;

	/**
	 * Methode zum Parsen der Quickcheck Datei.
	 * 
	 */
	public static HashMap<String, Machine> parseXMLtoMachine(String filename) {
		try {
			MachineContentHandler machineContent = new MachineContentHandler();
			XMLReader xmlReader;
			tempFile = File.createTempFile("tempQuick", ".qcw", null);
			clean(filename, tempFile.getAbsolutePath());
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			xmlReader = parser.getXMLReader();
			 
			FileInputStream fis = new FileInputStream(filename);
			InputSource inputSource = new InputSource(fis);
			xmlReader.setContentHandler(machineContent);
			xmlReader.parse(inputSource);
			return machineContent.getMachineList();
		} catch (SAXException ex) {
			Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
		} catch (FileNotFoundException ex) {
			Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} finally {
			tempFile.deleteOnExit();
		}
		return null;
	}

	private static void clean(String orignalFilename, String tempFilename) throws IOException {
		StringBuilder lesepuffer = new StringBuilder();
		BufferedReader reader = new BufferedReader(new FileReader(orignalFilename));
		try {
			String zeile;
			zeile = reader.readLine();
			System.out.println(zeile.indexOf("<"));
			zeile = zeile.substring(zeile.indexOf("<"));
			lesepuffer.append(zeile).append(System.getProperty("line.separator"));
			while ((zeile = reader.readLine()) != null) {
				zeile = zeile.trim();
				// Puffern
				lesepuffer.append(zeile).append(System.getProperty("line.separator"));
			}
			// Lesepuffer speichern
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFilename));
			writer.write(lesepuffer.toString());
			writer.flush();
			writer.close();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			reader.close();
		}
		
	}

}
