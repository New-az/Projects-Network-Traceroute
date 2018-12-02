package traceroute;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class Controller {
    @FXML Button start_btn;
    @FXML Button clear_btn;
    @FXML TextField textField;
    @FXML TableView<String> table;
    @FXML TableColumn<TraceRoute, String> hopCol;
    @FXML TableColumn<TraceRoute, String> ipCol;
    @FXML TableColumn<TraceRoute, String> hostnameCol;
    @FXML TableColumn<TraceRoute, String> avgCol;

    public void handleStart(ActionEvent e){
        String url = textField.getText().trim();

        if(!url.isEmpty()){
            ArrayList<String> output = new ArrayList<String>();
            TraceRoute tr = new TraceRoute();
            output = tr.start(url);

            for(String x : output){
                System.out.println(x);
            }
        }else{

        }
    }

    public void handleClear(ActionEvent e){
        textField.clear();
    }
}
