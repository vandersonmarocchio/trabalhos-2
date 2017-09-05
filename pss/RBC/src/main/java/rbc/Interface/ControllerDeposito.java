package rbc.Interface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import rbc.gerenciadores.gerenciador_motoristas.Motorista;
import rbc.gerenciadores.gerenciador_motoristas.MotoristaDAO;
import rbc.gerenciadores.gerenciador_motoristas.MotoristaView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerDeposito implements Initializable{

    public Button adicionar_deposito;
    public Button editar_deposito;
    public Button excluir_deposito;
    public ComboBox funcionarios;

    public void btnadicionar(ActionEvent event) throws Exception {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DadosDepositos.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.setTitle("Dados dos Depositos");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<MotoristaView> personData = FXCollections.observableArrayList();

        for(Motorista m : MotoristaDAO.getMotoristas()){
            personData.add(new MotoristaView(m));
        }
        funcionarios.setItems(personData);
    }
}
