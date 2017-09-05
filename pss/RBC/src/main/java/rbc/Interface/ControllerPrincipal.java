package rbc.Interface;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerPrincipal{

    public Button menu_funcionario;
    public Button menu_encargos;
    public Button menu_veiculos;
    public Button menu_depositos;
    public Button menu_relatorio;
    public Button menu_balanco;
    public Button menu_estoque;

    public void btnfuncionario(ActionEvent event) throws Exception {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Funcionario.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.setTitle("Funcionários");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnveiculo(ActionEvent event) throws Exception {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Veiculo.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.setTitle("Veículos");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void btnencargo(ActionEvent event) throws Exception {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Encargo.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.setTitle("Encargos");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void btndeposito(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Deposito.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.setTitle("Depósitos");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnbalanco(ActionEvent event) throws Exception {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BalancoMensal.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.setTitle("Balanço Mensal");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnestoque(ActionEvent event) throws Exception {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Estoque.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.setTitle("Estoque");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}