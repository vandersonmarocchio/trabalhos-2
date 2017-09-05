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
import rbc.gerenciadores.gerenciador_veiculos.VeiculosView;
import rbc.infraestrutura.RGExistsException;
import rbc.infraestrutura.auxEdicao;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by diogo on 19/07/17.
 */
public class ControllerEditarDadosVeiculo  implements Initializable {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        VeiculosView cavalo;
        cavalo = (VeiculosView) auxEdicao.objeto;
        placa_cavalo.setText(cavalo.getplacaCavalo());
        modelo_cavalo.setText(cavalo.getmodelo());
        renavam_cavalo.setText(cavalo.getrenavam());
        cor_cavalo.setText(cavalo.getcor());
        chassis_cavalo.setText(cavalo.getchassi());
        ano_cavalo.setText(cavalo.getano());

        List<Carreta> carretas = cavalo.getCarretas();

        if (carretas.size() > 0){
            placa_carreta.setText(carretas.get(0).getPlacaCarreta());
            renavam_carreta.setText(carretas.get(0).getRenavam());
            chassis_carreta.setText(carretas.get(0).getChassi());
            modelo_carreta.setText(carretas.get(0).getModelo());
            cor_carreta.setText(carretas.get(0).getCor());
            ano_carreta.setText(carretas.get(0).getAno());

            if (carretas.size() > 1){
                placa_carreta2.setText(carretas.get(1).getPlacaCarreta());
                renavam_carreta2.setText(carretas.get(1).getRenavam());
                chassis_carreta2.setText(carretas.get(1).getChassi());
                modelo_carreta2.setText(carretas.get(1).getModelo());
                cor_carreta2.setText(carretas.get(1).getCor());
                ano_carreta2.setText(carretas.get(1).getAno());

            } else {
                placa_carreta2.setText("");
                renavam_carreta2.setText("");
                chassis_carreta2.setText("");
                modelo_carreta2.setText("");
                cor_carreta2.setText("");
                ano_carreta2.setText("");
            }
        } else {
            placa_cavalo.setText("");
            renavam_cavalo.setText("");
            chassis_cavalo.setText("");
            modelo_cavalo.setText("");
            cor_cavalo.setText("");
            ano_cavalo.setText("");
            placa_carreta.setText("");
            renavam_carreta.setText("");
            chassis_carreta.setText("");
            modelo_carreta.setText("");
            cor_carreta.setText("");
            ano_carreta.setText("");
            placa_carreta2.setText("");
            renavam_carreta2.setText("");
            chassis_carreta2.setText("");
            modelo_carreta2.setText("");
            cor_carreta2.setText("");
            ano_carreta2.setText("");
        }
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
                dao.update(cavalo);
                dao1.update(carreta);
                dao2.update(carreta2);
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
}
