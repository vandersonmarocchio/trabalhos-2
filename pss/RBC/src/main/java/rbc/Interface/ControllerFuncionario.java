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
import rbc.gerenciadores.gerenciador_motoristas.Motorista;
import rbc.gerenciadores.gerenciador_motoristas.MotoristaDAO;
import rbc.gerenciadores.gerenciador_motoristas.MotoristaView;
import rbc.infraestrutura.auxEdicao;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerFuncionario implements Initializable {

    public Button adicionar_funcionario;
    public Button editar_funcionario;
    public Button excluir_funcionario;
    public TableView<MotoristaView> tabela;
    public TableColumn<MotoristaView, String> coluna_primeiro_nome;
    public Text name;
    public Text RG;
    public Text CPF;
    public Text endereco;
    public Text dataNasc;
    public Text CNH;
    public Text bairro;
    public Text cidade;
    public Text telefone;
    public Text CEP;
    public Text UF;
    public Text celular;
    public Text phoneOp;
    public Text phoneOp2;
    public Text nameOp;
    public Text nameOp2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        coluna_primeiro_nome.setCellValueFactory(cellData -> cellData.getValue().getnameProperty());


        ObservableList<MotoristaView> personData = FXCollections.observableArrayList();
        for(Motorista m : MotoristaDAO.getMotoristas()){
            personData.add(new MotoristaView(m));
        }

        tabela.setItems(personData);
        tabela.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> mostraDados(newValue));

    }

    public void btnadicionar(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DadosFuncionario.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.setTitle("Dados do Funcionário");
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
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditarDadosFuncionario.fxml"));
                Parent root2 = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root2));
                stage.resizableProperty().setValue(Boolean.FALSE);
                stage.setTitle("Editando dados do Funcionário");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhum funcionário seleção");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione uma pessoa na tabela.");
            alert.showAndWait();
        }

    }


    private void mostraDados(MotoristaView motorista){
        if (motorista!=null){
            name.setText(motorista.getname());
            RG.setText(motorista.getRG());
            CPF.setText(motorista.getCPF());
            CEP.setText(motorista.getCEP());
            endereco.setText(motorista.getEndereco());
            dataNasc.setText(motorista.getdataNasc());
            CNH.setText(motorista.getCNH());
            bairro.setText(motorista.getbairro());
            cidade.setText(motorista.getcidade());
            telefone.setText(motorista.gettelefone());
            UF.setText(motorista.getUF());
            celular.setText(motorista.getcelular());
            nameOp.setText(motorista.getnameOp());
            phoneOp.setText(motorista.getphoneOp());
            nameOp2.setText(motorista.getNameOp2());
            phoneOp2.setText(motorista.getPhoneOp2());
    } else {
            name.setText("");
            RG.setText("");
            CPF.setText("");
            CEP.setText("");
            endereco.setText("");
            dataNasc.setText("");
            CNH.setText("");
            bairro.setText("");
            cidade.setText("");
            telefone.setText("");
            UF.setText("");
            celular.setText("");
            nameOp.setText("");
            phoneOp.setText("");
            nameOp2.setText("");
            phoneOp2.setText("");
        }
    }

    public void btnExcluir(ActionEvent event) throws  Exception{
        int selectedIndex = tabela.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            auxEdicao.objeto = tabela.getSelectionModel().getSelectedItem();
            tabela.getItems().remove(selectedIndex);
            if(auxEdicao.objeto == null){
                System.out.printf("NULllll");
            }
            MotoristaDAO dao = new MotoristaDAO();
            Motorista motorista = ((MotoristaView) auxEdicao.objeto).getMotorista();
            dao.delete(motorista);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhum funcionário selecionado");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione uma pessoa na tabela.");
            alert.showAndWait();
        }
    }

}