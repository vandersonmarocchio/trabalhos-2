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
import javafx.stage.Stage;
import rbc.gerenciadores.gerenciador_encargo.EncargoDAO;
import rbc.gerenciadores.gerenciador_estoque.Estoque;
import rbc.gerenciadores.gerenciador_estoque.EstoqueDAO;
import rbc.gerenciadores.gerenciador_estoque.EstoqueView;
import rbc.infraestrutura.auxEdicao;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by diogo on 20/07/17.
 */

public class ControllerEstoque implements Initializable {
    public Button adicionar;
    public Button editar;
    public Button excluir;
    public TableView tabela;
    public TableColumn<EstoqueView, String> coluna_item;
    public TableColumn<EstoqueView, String> coluna_valor;
    public TableColumn<EstoqueView, Number> coluna_quantidade;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        coluna_item.setCellValueFactory(cellData -> cellData.getValue().itemProperty());
        coluna_valor.setCellValueFactory(cellData -> cellData.getValue().valorItemProperty());
        coluna_quantidade.setCellValueFactory(cellData -> cellData.getValue().quantidadeProperty());
        ObservableList<EstoqueView> personData = FXCollections.observableArrayList();
        for (Estoque m : EstoqueDAO.getEstoque()) {
            personData.add(new EstoqueView(m));
        }
        tabela.setItems(personData);
    }

    public void btnadicionar(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DadosEstoque.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.setTitle("Dados do Item");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btneditar(ActionEvent event) throws Exception {
        int selectedIndex = tabela.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            try {
                auxEdicao.objeto = tabela.getSelectionModel().getSelectedItem();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditarDadosEstoque.fxml"));
                Parent root2 = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root2));
                stage.resizableProperty().setValue(Boolean.FALSE);
                stage.setTitle("Editando dados do Item");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhum item selecionado");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione um item da tabela.");
            alert.showAndWait();
        }

    }

    public void btnExcluir(ActionEvent event) throws Exception {
        int selectedIndex = tabela.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            auxEdicao.objeto = tabela.getSelectionModel().getSelectedItem();
            tabela.getItems().remove(selectedIndex);
            EncargoDAO dao = new EncargoDAO();
            Estoque estoque = ((EstoqueView) auxEdicao.objeto).getEstoque();
            dao.delete(estoque);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhum item selecionado");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione um item da tabela.");
            alert.showAndWait();
        }
    }
}