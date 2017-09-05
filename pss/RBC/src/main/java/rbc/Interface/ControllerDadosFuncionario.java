package rbc.Interface;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rbc.gerenciadores.gerenciador_motoristas.Motorista;
import rbc.gerenciadores.gerenciador_motoristas.MotoristaDAO;
import rbc.infraestrutura.RGExistsException;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerDadosFuncionario implements Initializable {
    public Button Salvar;
    public Button Cancelar;
    public TextField name;
    public TextField RG;
    public TextField CPF;
    public TextField CNH;
    public TextField endereco;
    public TextField bairro;
    public TextField CEP;
    public TextField cidade;
    public TextField UF;
    public TextField telefone;
    public TextField celular;
    public TextField nameOp;
    public TextField phoneOp;
    public TextField nameOp2;
    public TextField phoneOp2;
    public TextField dataNasc;

    public void btncancelar (ActionEvent event) throws  Exception{
        Stage stage = (Stage) Cancelar.getScene().getWindow();
        stage.close();
    }

    public void btnsalvar (ActionEvent event) throws Exception {
        Motorista motorista = new Motorista();
        motorista.setName(name.getText());
        motorista.setRG(RG.getText());
        motorista.setDataNasc(dataNasc.getText());
        motorista.setCPF(CPF.getText());
        motorista.setCNH(CNH.getText());
        motorista.setEndereco(endereco.getText());
        motorista.setBairro(bairro.getText());
        motorista.setCEP(CEP.getText());
        motorista.setCidade(cidade.getText());
        motorista.setUF(UF.getText());
        motorista.setTelefone(telefone.getText());
        motorista.setCelular(celular.getText());
        if (nameOp.getText().isEmpty()) motorista.setNameOp(null);
        else motorista.setNameOp(nameOp.getText());
        if (phoneOp.getText().isEmpty()) motorista.setPhoneOp(null);
        else motorista.setPhoneOp(phoneOp.getText());
        if (nameOp2.getText().isEmpty()) motorista.setNameOp2(null);
        else motorista.setNameOp2(nameOp2.getText());
        if (phoneOp2.getText().isEmpty()) motorista.setPhoneOp2(null);
        else motorista.setPhoneOp2(phoneOp2.getText());
        if(name.getText().isEmpty() || RG.getText().isEmpty() || dataNasc.getText().isEmpty() || CPF.getText().isEmpty() || CNH.getText().isEmpty() || endereco.getText().isEmpty() || bairro.getText().isEmpty() || CEP.getText().isEmpty()
                || cidade.getText().isEmpty() || UF.getText().isEmpty() || telefone.getText().isEmpty() || celular.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos obrigatóros não preenchidos!!");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, complete os campos obrigatórios.");
            alert.showAndWait();
            alert.getOnCloseRequest();
        } else {

            MotoristaDAO dao = new MotoristaDAO();
            try{
                dao.addDatabase(motorista);
                Stage stage = (Stage) Salvar.getScene().getWindow();
                stage.close();

            }catch (RGExistsException e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Usuário já cadastrado");
                alert.setHeaderText(null);
                alert.setContentText("Usuário já cadastrado!!");
                alert.showAndWait();
                alert.getOnCloseRequest();
            }

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setText("");
        RG.setText("");
        dataNasc.setText("");
        CPF.setText("");
        CEP.setText("");
        endereco.setText("");
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