package rbc.Interface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import rbc.gerenciadores.gerenciador_motoristas.Motorista;
import rbc.gerenciadores.gerenciador_motoristas.MotoristaDAO;
import rbc.gerenciadores.gerenciador_motoristas.MotoristaView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerBalancoMensal implements Initializable{

    public MenuButton funcionarios;

    public void btnAdicionar(ActionEvent event) throws Exception {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DadosBalancoMensal.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.setTitle("Veículo");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<MotoristaView> personData = FXCollections.observableArrayList();
        for(Motorista m : MotoristaDAO.getMotoristas()){
            funcionarios.getItems().add(new MenuItem(m.getName()));
        }
    }
}