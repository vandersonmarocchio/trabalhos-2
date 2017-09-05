package rbc.Interface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import rbc.gerenciadores.gerenciador_veiculos.Carreta;
import rbc.gerenciadores.gerenciador_veiculos.Cavalo;
import rbc.gerenciadores.gerenciador_veiculos.VeiculoDAO;
import rbc.gerenciadores.gerenciador_veiculos.VeiculosView;
import rbc.infraestrutura.auxEdicao;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerVeiculo implements Initializable{
    public Button adicionar_veiculo;
    public Button editar_veiculo;
    public Button excluir_veiculo;
    public TableView tabela;
    public TableColumn<VeiculosView, String> coluna_placa;
    public Text placa_cavalo;
    public Text renavam_cavalo;
    public Text chassis_cavalo;
    public Text modelo_cavalo;
    public Text cor_cavalo;
    public Text ano_cavalo;
    public Text placa_carreta;
    public Text renavam_carreta;
    public Text chassis_carreta;
    public Text modelo_carreta;
    public Text cor_carreta;
    public Text ano_carreta;
    public Text placa_carreta2;
    public Text renavam_carreta2;
    public Text chassis_carreta2;
    public Text modelo_carreta2;
    public Text cor_carreta2;
    public Text ano_carreta2;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        coluna_placa.setCellValueFactory(cellData -> cellData.getValue().getPlacaCavaloProperty());

        ObservableList<VeiculosView> personData = FXCollections.observableArrayList();

        for(Cavalo m : VeiculoDAO.getVeiculos()){
            personData.add(new VeiculosView(m));
        }

        tabela.setItems(personData);

        tabela.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> mostraDados((VeiculosView) newValue));

    }

    public void btnadicionar(ActionEvent event) throws Exception {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DadosVeiculo.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.setTitle("Dados do Veículo");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btneditar(ActionEvent event) throws Exception{
        int selectedIndex = tabela.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            try {
                auxEdicao.objeto = tabela.getSelectionModel().getSelectedItem();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditarDadosVeiculo.fxml"));
                Parent root2 = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root2));
                stage.resizableProperty().setValue(Boolean.FALSE);
                stage.setTitle("Editando dados do Veículo");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhum veiculo selecionado");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione um veiculo na tabela.");
            alert.showAndWait();
        }

    }

    private void mostraDados(VeiculosView cavalo) {
        if (cavalo != null){
            placa_cavalo.setText(cavalo.getplacaCavalo());
            renavam_cavalo.setText(cavalo.getrenavam());
            chassis_cavalo.setText(cavalo.getchassi());
            modelo_cavalo.setText(cavalo.getmodelo());
            cor_cavalo.setText(cavalo.getcor());
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
    }

    public void btnExcluir(ActionEvent event) throws  Exception{
        int selectedIndex = tabela.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            auxEdicao.objeto = tabela.getSelectionModel().getSelectedItem();
            tabela.getItems().remove(selectedIndex);
            VeiculoDAO dao = new VeiculoDAO();
            Cavalo cavalo = ((VeiculosView) auxEdicao.objeto).getCavalo();
            List<Carreta> carretas = cavalo.getListaCarretas();
            if (carretas.size() > 0){
                dao.delete(carretas.get(0));
            }
                if (carretas.size() > 1){
                dao.delete(carretas.get(1));
            }
                dao.delete(cavalo);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhum veículo selecionado!");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione uma pessoa na tabela.");
            alert.showAndWait();
        }
    }
}
