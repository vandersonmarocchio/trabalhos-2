package rbc.Interface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import rbc.gerenciadores.gerenciador_encargo.ListaEncargosView;
import rbc.gerenciadores.gerenciador_motoristas.Motorista;
import rbc.gerenciadores.gerenciador_motoristas.MotoristaDAO;
import rbc.gerenciadores.gerenciador_motoristas.MotoristaView;
import rbc.infraestrutura.auxEdicao;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerEncargo implements Initializable{
    public Button adicionar_encargo;
    public Button editar_encargo;
    public Button excluir_encargo;
    public TableColumn<ListaEncargosView, String> coluna_mes;
    public TableColumn coluna_total;
    public TableColumn coluna_situacao;
    public ComboBox<MotoristaView> funcionarios;
    public TableView<ListaEncargosView> tabela;

    public void btnadicionar(ActionEvent event) throws Exception {
        int selectedIndex = funcionarios.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            try {
                auxEdicao.objeto = funcionarios.getSelectionModel().getSelectedItem();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DadosEncargo.fxml"));
                Parent root1 = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.resizableProperty().setValue(Boolean.FALSE);
                stage.setTitle("Dados do Encargos");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhum funcionário selecionado");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione uma pessoa na lista.");
            alert.showAndWait();
        }


    }

    public void btnfuncionario (ActionEvent event) throws Exception{
        //coluna_mes.setCellValueFactory(cellData -> cellData.getValue().getRGProperty());
        ObservableList<ListaEncargosView> encargos = FXCollections.observableArrayList();
        encargos.add(new ListaEncargosView(funcionarios.getSelectionModel().getSelectedItem().getMotorista().getListaEncargos()));
        tabela.setItems(encargos);
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
