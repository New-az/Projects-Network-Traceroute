package traceroute;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Controller {
	@FXML
	Button start_btn;
	@FXML
	Button clear_btn;
	@FXML
	TextField textField;
	@FXML
	TableView<TraceRoute> table;
	@FXML
	TableColumn<TraceRoute, String> hopCol;
	@FXML
	TableColumn<TraceRoute, String> ipCol;
	@FXML
	TableColumn<TraceRoute, String> hostnameCol;
	@FXML
	TableColumn<TraceRoute, String> avgCol;
	@FXML
	LineChart<String, Number> chart;

	private TraceRoute t;
	private XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();

	@FXML
	public void initialize() {
		series.setName("Visual Route");
		chart.setAnimated(true);
	}

	public void handleStart(ActionEvent e) {
		ObservableList<TraceRoute> data = FXCollections.observableArrayList();
		new Thread(() -> {
			String url = textField.getText().trim();

			hopCol.setCellValueFactory(new PropertyValueFactory<>("hop"));
			ipCol.setCellValueFactory(new PropertyValueFactory<>("ip"));
			hostnameCol.setCellValueFactory(new PropertyValueFactory<>("hostname"));
			avgCol.setCellValueFactory(new PropertyValueFactory<>("time"));

			if (!url.isEmpty()) {
				int n = 0, numHop = 1;
				ArrayList<String> output = new ArrayList<String>();
				TraceRoute tr = new TraceRoute();
				double time;
				output = tr.start(url);

				for (String x : output) {
					if (n != 0) {
						String[] s = x.split(" ");
						try {
							if (numHop < 10) {
								time = Double.parseDouble(s[6]);
							} else {
								time = Double.parseDouble(s[5]);
							}
						} catch (ArrayIndexOutOfBoundsException | NumberFormatException ex) {
							time = 0;
						}
						if (numHop < 10) {
							t = new TraceRoute(numHop, s[3], s[4], time);
						} else {
							t = new TraceRoute(numHop, s[2], s[3], time);
						}
						data.add(t);
						series.getData().add(new XYChart.Data<String, Number>(t.getHop() + "", t.getTime()));

						numHop++;
					}

					n++;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			} else {

			}
		}).start();
		table.setItems(data);
		chart.getData().add(series);

	}

	public void handleClear(ActionEvent e) {
		textField.clear();
		series.getData().clear();
		chart.getData().clear();
		table.getItems().clear();
	}
	
}
