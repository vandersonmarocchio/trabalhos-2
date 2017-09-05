package rbc.Interface;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rbc.gerenciadores.gerenciador_estoque.Estoque;
import rbc.gerenciadores.gerenciador_estoque.EstoqueDAO;
import rbc.gerenciadores.gerenciador_estoque.EstoqueView;
import rbc.infraestrutura.RGExistsException;
import rbc.infraestrutura.auxEdicao;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by diogo on 20/07/17.
 */
public class ControllerEditarDadosEstoque implements Initializable {
    public TextField descricao;
    public TextField valor;
    public TextField quantidade;
    public Button salvar;
    public Button cancelar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DecimalFormat decimal = new DecimalFormat( "00.00" );
        EstoqueView estoque;
        estoque = (EstoqueView) auxEdicao.objeto;
        descricao.setText(estoque.getItem());
        valor.setText(decimal.format(estoque.getValorItem()));
        quantidade.setText(Integer.toString(estoque.getQuantidade()));
    }

    public void btncancelar (ActionEvent event) throws  Exception{
        Stage stage = (Stage) cancelar.getScene().getWindow();
        stage.close();
    }

    public void btnsalvar (ActionEvent event) throws  Exception{
        if(valor.getText().isEmpty() || quantidade.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos obrigatóros não preenchidos!!");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, complete os campos obrigatórios.");
            alert.showAndWait();
            alert.getOnCloseRequest();
        } else {
            Estoque estoque = new Estoque();
            Locale pt = new Locale ("pt", "BR");
            NumberFormat nf = NumberFormat.getInstance(pt);
            DecimalFormat decimal = new DecimalFormat( "00.00" );
            estoque.setItem(descricao.getText());
            estoque.setQuantidade(Integer.parseInt(quantidade.getText()));
            estoque.setValorItem(nf.parse(valor.getText()).floatValue());
            EstoqueDAO dao = new EstoqueDAO();
            try {
                dao.update(estoque);

            } catch (RGExistsException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Usuário já cadastrado");
                alert.setHeaderText(null);
                alert.setContentText("Usuário já cadastrado!!");
                alert.showAndWait();
                alert.getOnCloseRequest();
            }

            Stage stage = (Stage) salvar.getScene().getWindow();
            stage.close();
        }
    }
}
