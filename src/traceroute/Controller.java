package traceroute;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
	TableColumn<TraceRoute, String> hostnameCol;
	@FXML
	TableColumn<TraceRoute, String> ipCol;
	@FXML
	TableColumn<TraceRoute, String> msCol;
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
		start_btn.setDisable(true);
		ObservableList<TraceRoute> data = FXCollections.observableArrayList();
		String url = textField.getText().trim();
		new Thread(() -> {

			hopCol.setCellValueFactory(new PropertyValueFactory<>("hop"));
			hostnameCol.setCellValueFactory(new PropertyValueFactory<>("ip"));
			ipCol.setCellValueFactory(new PropertyValueFactory<>("hostname"));
			msCol.setCellValueFactory(new PropertyValueFactory<>("time"));

			if (!url.isEmpty()) {
				int n = 0, numHop = 1;
				ArrayList<String> output = new ArrayList<String>();
				TraceRoute tr = new TraceRoute();
				output = tr.start(url);

				for (String x : output) {
					if (n != 0) {
						double time = 0;
						String host_n = "", ip_n = "";
						String[] s = x.split(" ");

						if(numHop < 10) host_n = s[3];
						else host_n = s[2];

						for(int i=0; i<s.length; i++){

							if(s[i].startsWith("(")) ip_n = s[i].substring(1, s[i].length()-1);
							else if(s[i].equals("ms")) time = Double.parseDouble(s[i-1]);

							if(!ip_n.isEmpty() && time != 0) break;
						}

						t = new TraceRoute(numHop, host_n, ip_n, time);

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

			}
		}).start();

		table.setItems(data);
		chart.getData().add(series);

		if(url.isEmpty()){
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText(null);
			alert.setContentText("Please input url");

			alert.showAndWait();
		}
	}

	public void handleClear(ActionEvent e) {
		start_btn.setDisable(false);
		textField.clear();
		series.getData().clear();
		chart.getData().clear();
		table.getItems().clear();
	}
	
}
