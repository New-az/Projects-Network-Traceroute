package traceroute;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class Controller {
    @FXML Button start_btn;
    @FXML Button clear_btn;
    @FXML TextField textField;
    @FXML TableView<TraceRoute> table;
    @FXML TableColumn<TraceRoute, String> hopCol;
    @FXML TableColumn<TraceRoute, String> ipCol;
    @FXML TableColumn<TraceRoute, String> hostnameCol;
    @FXML TableColumn<TraceRoute, String> avgCol;

    private TraceRoute t;

    public void handleStart(ActionEvent e){
        ObservableList<TraceRoute> data = FXCollections.observableArrayList();
        String url = textField.getText().trim();

        hopCol.setCellValueFactory(new PropertyValueFactory<>("hop"));
        ipCol.setCellValueFactory(new PropertyValueFactory<>("ip"));
        hostnameCol.setCellValueFactory(new PropertyValueFactory<>("hostname"));

        if(!url.isEmpty()){
            int n = 0, numHop = 1;
            ArrayList<String> output = new ArrayList<String>();
            TraceRoute tr = new TraceRoute();
            output = tr.start(url);

            for(String x : output){
                if(n != 0) {
                    String[] s = x.split(" ");
                    t = new TraceRoute(numHop, s[3], s[4]);

                    data.add(t);

                    table.setItems(data);
                    numHop++;
                }
                n++;
            }
        }else{

        }
    }

    public void handleClear(ActionEvent e){
        textField.clear();
    }
}
