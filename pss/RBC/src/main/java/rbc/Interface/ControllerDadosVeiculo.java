package rbc.Interface;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rbc.gerenciadores.gerenciador_veiculos.Carreta;
import rbc.gerenciadores.gerenciador_veiculos.Cavalo;
import rbc.gerenciadores.gerenciador_veiculos.VeiculoDAO;
import rbc.infraestrutura.RGExistsException;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerDadosVeiculo implements Initializable{
    public TextField placa_cavalo;
    public TextField modelo_cavalo;
    public TextField renavam_cavalo;
    public TextField cor_cavalo;
    public TextField chassis_cavalo;
    public TextField ano_cavalo;
    public TextField placa_carreta;
    public TextField modelo_carreta;
    public TextField renavam_carreta;
    public TextField cor_carreta;
    public TextField chassis_carreta;
    public TextField ano_carreta;
    public TextField placa_carreta2;
    public TextField modelo_carreta2;
    public TextField renavam_carreta2;
    public TextField cor_carreta2;
    public TextField chassis_carreta2;
    public TextField ano_carreta2;
    public Button Salvar;
    public Button Cancelar;

    public void btncancelar (ActionEvent event) throws  Exception{
        Stage stage = (Stage) Cancelar.getScene().getWindow();
        stage.close();
    }

    public void btnsalvar (ActionEvent event) throws Exception{
        Cavalo cavalo = new Cavalo();
        Carreta carreta = new Carreta();
        Carreta carreta2 = new Carreta();
        cavalo.setPlacaCavalo(placa_cavalo.getText());
        cavalo.setModelo(modelo_cavalo.getText());
        cavalo.setRenavam(renavam_cavalo.getText());
        cavalo.setCor(cor_cavalo.getText());
        cavalo.setChassi(chassis_cavalo.getText());
        cavalo.setAno(ano_cavalo.getText());
        carreta.setPlacaCarreta(placa_carreta.getText());
        carreta.setModelo(modelo_carreta.getText());
        carreta.setRenavam(renavam_carreta.getText());
        carreta.setCor(cor_carreta.getText());
        carreta.setChassi(chassis_carreta.getText());
        carreta.setAno(ano_carreta.getText());
        carreta2.setPlacaCarreta(placa_carreta2.getText());
        carreta2.setModelo(modelo_carreta2.getText());
        carreta2.setRenavam(renavam_carreta2.getText());
        carreta2.setCor(cor_carreta2.getText());
        carreta2.setChassi(chassis_carreta2.getText());
        carreta2.setAno(ano_carreta2.getText());

        cavalo.getListaCarretas().add(carreta2);
        cavalo.getListaCarretas().add(carreta);

        if(placa_cavalo.getText().isEmpty() || modelo_cavalo.getText().isEmpty() || renavam_cavalo.getText().isEmpty() || cor_cavalo.getText().isEmpty()
                || chassis_cavalo.getText().isEmpty() || ano_cavalo.getText().isEmpty() || placa_carreta.getText().isEmpty() || modelo_carreta.getText().isEmpty() || renavam_carreta.getText().isEmpty() || cor_carreta.getText().isEmpty()
                || chassis_carreta.getText().isEmpty() || ano_carreta.getText().isEmpty()|| placa_carreta2.getText().isEmpty() || modelo_carreta2.getText().isEmpty() || renavam_carreta2.getText().isEmpty() || cor_carreta2.getText().isEmpty()
                || chassis_carreta2.getText().isEmpty() || ano_carreta2.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos obrigatóros não preenchidos!!");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, complete os campos obrigatórios.");
            alert.showAndWait();
            alert.getOnCloseRequest();
        }
        else if (placa_cavalo.getText().equals(placa_carreta.getText()) || placa_cavalo.getText().equals(placa_carreta2.getText()) || placa_carreta.getText().equals(placa_carreta2.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Placas iguais!!");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, digite placas diferentes.");
            alert.showAndWait();
            alert.getOnCloseRequest();
        }
        else {
            VeiculoDAO dao = new VeiculoDAO();
            VeiculoDAO dao1 = new VeiculoDAO();
            VeiculoDAO dao2 = new VeiculoDAO();

            try {
                dao.addDatabase(cavalo);
                dao1.addDatabase(carreta);
                dao2.addDatabase(carreta2);
            } catch (RGExistsException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Veiculo já cadastrado");
                alert.setHeaderText(null);
                alert.setContentText("Veículo já cadastrado!!");
                alert.showAndWait();
                alert.getOnCloseRequest();
                dao.delete(cavalo);
            }

            Stage stage = (Stage) Salvar.getScene().getWindow();
            stage.close();

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        placa_cavalo.setText("");
        placa_carreta.setText("");
        placa_carreta2.setText("");
        modelo_cavalo.setText("");
        modelo_carreta.setText("");
        modelo_carreta2.setText("");
        renavam_cavalo.setText("");
        renavam_carreta.setText("");
        renavam_carreta2.setText("");
        cor_cavalo.setText("");
        cor_carreta.setText("");
        cor_carreta2.setText("");
        chassis_cavalo.setText("");
        chassis_carreta.setText("");
        chassis_carreta2.setText("");
        ano_cavalo.setText("");
        ano_carreta.setText("");
        ano_carreta2.setText("");
    }
}
