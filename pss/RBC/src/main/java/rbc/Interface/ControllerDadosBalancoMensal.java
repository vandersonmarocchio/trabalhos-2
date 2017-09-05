package rbc.Interface;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerDadosBalancoMensal {
    public void btnAdicionar(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DadosViagem.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.setTitle("Dados Viagem");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
