package rbc.Interface;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rbc.gerenciadores.gerenciador_motoristas.Motorista;
import rbc.gerenciadores.gerenciador_motoristas.MotoristaDAO;
import rbc.gerenciadores.gerenciador_motoristas.MotoristaView;
import rbc.infraestrutura.RGExistsException;
import rbc.infraestrutura.auxEdicao;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by diogo on 13/07/17.
 */
public class ControllerEditarDadosFuncionario implements Initializable {
    public TextField name;
    public TextField RG;
    public TextField dataNasc;
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
    public Button Salvar;
    public Button Cancelar;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MotoristaView motorista;
        motorista = (MotoristaView) auxEdicao.objeto;

        name.setText(motorista.getname());
        RG.setText(motorista.getRG());
        dataNasc.setText(motorista.getdataNasc());
        CPF.setText(motorista.getCPF());
        CNH.setText(motorista.getCNH());
        endereco.setText(motorista.getEndereco());
        bairro.setText(motorista.getbairro());
        CEP.setText(motorista.getCEP());
        cidade.setText(motorista.getcidade());
        UF.setText(motorista.getUF());
        telefone.setText(motorista.gettelefone());
        celular.setText(motorista.getcelular());
        if (motorista.getnameOp()==null) nameOp.setText("");
        else nameOp.setText(motorista.getnameOp());
        if (motorista.getphoneOp()==null) phoneOp.setText("");
        else phoneOp.setText(motorista.getphoneOp());
        if (motorista.getNameOp2()==null) nameOp2.setText("");
        else nameOp2.setText(motorista.getNameOp2());
        if (motorista.getPhoneOp2()==null) phoneOp2.setText("");
        else phoneOp2.setText(motorista.getPhoneOp2());
    }

    public void btncancelar (ActionEvent event) throws  Exception{
        Stage stage = (Stage) Cancelar.getScene().getWindow();
        stage.close();
    }

    public void btnsalvar(ActionEvent event) throws Exception {
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
        if (nameOp.getText().equals("")) motorista.setNameOp(null);
        else motorista.setNameOp(nameOp.getText());
        if (phoneOp.getText().equals("")) motorista.setPhoneOp(null);
        else motorista.setPhoneOp(phoneOp.getText());
        if (nameOp2.getText().equals("")) motorista.setNameOp2(null);
        else motorista.setNameOp2(nameOp2.getText());
        if (phoneOp2.getText().equals("")) motorista.setPhoneOp2(null);
        else motorista.setPhoneOp2(phoneOp2.getText());
        if (name.getText().isEmpty() || RG.getText().isEmpty() || dataNasc.getText().isEmpty() || CPF.getText().isEmpty() || CNH.getText().isEmpty() || endereco.getText().isEmpty() || bairro.getText().isEmpty() || CEP.getText().isEmpty()
                || cidade.getText().isEmpty() || UF.getText().isEmpty() || telefone.getText().isEmpty() || celular.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos obrigatóros não preenchidos!!");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, complete os campos obrigatórios.");
            alert.showAndWait();
            alert.getOnCloseRequest();
        } else {

            MotoristaDAO dao = new MotoristaDAO();
            try {
                dao.update(motorista);

            } catch (RGExistsException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Usuário já cadastrado");
                alert.setHeaderText(null);
                alert.setContentText("Usuário já cadastrado!!");
                alert.showAndWait();
                alert.getOnCloseRequest();
            }

            Stage stage = (Stage) Salvar.getScene().getWindow();
            stage.close();
        }
    }
}
