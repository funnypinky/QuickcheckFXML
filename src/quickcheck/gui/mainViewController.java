package quickcheck.gui;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import cern.extjfx.chart.NumericAxis;
import cern.extjfx.chart.XYChartPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import quickcheck.xml.Parser;
import quickcheck.xml.data.Energy;
import quickcheck.xml.data.Machine;
import quickcheck.xml.data.MeasData;
import quickcheck.xml.data.Modality;

public class mainViewController implements Initializable {
	
	@FXML
	private BorderPane mainLayout;
	
	@FXML
	private MenuItem openFile;

	@FXML
	private Label pathLabel;

	@FXML
	private ComboBox<String> machine;

	@FXML
	private ComboBox<Modality> qualtiy;

	@FXML
	private ComboBox<Energy> energy;

	@FXML
	private ComboBox<Integer> fieldSize;

	@FXML
	private ComboBox<Integer> ssd;

	@FXML
	private ComboBox<Integer> wedge;

	@FXML
	private DatePicker startDate;

	@FXML
	private DatePicker stopDate;
	
	@FXML
	private MenuBar menuBar;
	
	@FXML
	private ChartViewer caxSeries = new ChartViewer(null);
	
	@FXML
	private ChartViewer symViewer = new ChartViewer(null);
	
	@FXML
	private LineChart<String, Number> caxTest = new LineChart<String, Number>(createXAxis(), createYAxis());;
	
	@FXML
	private XYChartPane<String, Number> caxPane = new XYChartPane<String, Number>(caxTest);
	
	@FXML
	private VBox charts;

	private HashMap<String, Machine> data;

	private Machine selectedMachine;

	private Modality selectedModality;

	private Energy selectedEnergy;
	
	private final Preferences prefs = Preferences.userRoot();
	
	private final String LAST_OPEN_PATH = "QUICKCHECKlastOpenPath";

	private final String propertyValue = prefs.get(LAST_OPEN_PATH, null);

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		if (propertyValue != null) {
			MenuItem item = new MenuItem(propertyValue);
			menuBar.getMenus().get(0).getItems().add(item);
			item.setOnAction(event -> openFile(propertyValue));
        }
		
		mainLayout.widthProperty().addListener((obs, oldVal, newVal)->{
			mainLayout.getCenter().getBoundsInParent().getWidth();
			//caxChart.setPrefWidth(mainLayout.getCenter().getBoundsInParent().getWidth());
		});
		
		machine.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> ov, String arg1, String arg2) {
				selectedMachine = data.get(arg2);
				qualtiy.getItems().clear();
				qualtiy.setItems(FXCollections.observableArrayList(selectedMachine.getModality()));
			}
		});

		qualtiy.valueProperty().addListener(new ChangeListener<Modality>() {
			@Override
			public void changed(ObservableValue<? extends Modality> arg0, Modality arg1, Modality arg2) {
				selectedModality = arg2;
				energy.getItems().clear();
				energy.setItems(FXCollections.observableArrayList(selectedModality.getEnergy()));
			}

		});

		energy.valueProperty().addListener(new ChangeListener<Energy>() {

			@Override
			public void changed(ObservableValue<? extends Energy> arg0, Energy arg1, Energy arg2) {
				selectedEnergy = arg2;
				fieldSize.setItems(FXCollections.observableArrayList(selectedEnergy.getField()));
				ssd.setItems(FXCollections.observableArrayList(selectedEnergy.getSSD()));
				wedge.setItems(FXCollections.observableArrayList(selectedEnergy.getWedges()));
				fillCharts();
			}
		});
		fieldSize.valueProperty().addListener(new ChangeListener<Integer>() {

			@Override
			public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) {
				fillCharts();
			}
		});
		ssd.valueProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) {
				fillCharts();
			}
		});
		wedge.valueProperty().addListener(new ChangeListener<Integer>() {

			@Override
			public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) {
				fillCharts();
				
			}
			
		});

	}

	@FXML
	public void openFileDialog() {
		FileChooser loadFile = new FileChooser();
		File file = loadFile.showOpenDialog(null);
		openFile(file.getAbsolutePath());
	}

	private void openFile(String path) {
		pathLabel.setText("Pfad: " +path);
		data = Parser.parseXMLtoMachine(path);
		if (!data.isEmpty()) {
			prefs.put(LAST_OPEN_PATH,path);
			fillGuiWithData();
			Alert sucess = new Alert(AlertType.INFORMATION);
			sucess.setTitle("Erfolgreich");
			sucess.setHeaderText("Information");
			sucess.setContentText("Datei erfolgreich eingelesen!");
			sucess.showAndWait();
		}
	}
	
	public void fillGuiWithData() {
		machine.getItems().addAll(data.keySet());
		machine.getSelectionModel().selectFirst();
		String startDatum = LocalDate.now().getYear() + "-01-01";
		String stopDatum = LocalDate.now().getYear() + "-12-31";
		startDate.setValue(LocalDate.parse(startDatum));
		stopDate.setValue(LocalDate.parse(stopDatum));
	}

	private void fillCharts() {
		Integer ssd = this.ssd.getValue();
		Integer wedge = this.wedge.getValue();
		Integer field = this.fieldSize.getValue();
		if (ssd != null && wedge != null && field != null) {
			//caxChart.getData().clear();
			TimeSeries caxS = new TimeSeries("CAX");
			TimeSeries symGTSeries = new TimeSeries("Sym LR");
			TimeSeries symLRSeries = new TimeSeries("Sym GT");
			LineChart<String, Double> caxTest;

			ArrayList<MeasData> data = this.selectedEnergy.getMeasValue(wedge, field, ssd);
			for (MeasData measurment : data) {
				String pattern = "dd.MM.yy hh:mm";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				String datum = simpleDateFormat.format(measurment.getDate());
				XYChart.Data<String, Double> caxValue = new XYChart.Data<String, Double>(datum, measurment.getCax());
				caxS.addOrUpdate(new Day(measurment.getDate()),  measurment.getCax());
				symGTSeries.addOrUpdate(new Day(measurment.getDate()),  measurment.getSymGT());
				symLRSeries.addOrUpdate(new Day(measurment.getDate()),  measurment.getSymLR());
				
			}
			
			TimeSeriesCollection datasetCax = new TimeSeriesCollection();
			TimeSeriesCollection datasetSym = new TimeSeriesCollection();
			datasetCax.addSeries(caxS);
			datasetSym.addSeries(symLRSeries);
			datasetSym.addSeries(symGTSeries);
			JFreeChart chartCax = ChartFactory.createTimeSeriesChart("CAX", "Datum", "Dosis [Gy]", datasetCax,false,true,false);
			JFreeChart chartSym = ChartFactory.createTimeSeriesChart("Symmetrie", "Datum", "%", datasetSym,true,true,false);
			
			XYPlot plot = (XYPlot) chartCax.getPlot();
			
			caxSeries.setChart(chartCax);
			caxSeries.setDisable(false);
			
			symViewer.setChart(chartSym);
			symViewer.setDisable(false);
			
		}
	}
	
	private NumericAxis createYAxis() {
        NumericAxis yAxis = new NumericAxis();
        yAxis.setAnimated(false);
        yAxis.setForceZeroInRange(false);
        yAxis.setAutoRangePadding(0.1);
        yAxis.setAutoRangeRounding(false);
        return yAxis;
	}
	
	private CategoryAxis createXAxis() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setAnimated(false);
        return xAxis;
    }
}