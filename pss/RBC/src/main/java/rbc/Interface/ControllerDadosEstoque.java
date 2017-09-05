package rbc.Interface;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rbc.gerenciadores.gerenciador_estoque.Estoque;
import rbc.gerenciadores.gerenciador_estoque.EstoqueDAO;
import rbc.infraestrutura.RGExistsException;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by diogo on 20/07/17.
 */
public class ControllerDadosEstoque {
    public TextField descricao;
    public TextField valor;
    public TextField quantidade;
    public Button salvar;
    public Button cancelar;


    public void btncancelar (ActionEvent event) throws  Exception{
        Stage stage = (Stage) cancelar.getScene().getWindow();
        stage.close();
    }

    public void btnsalvar (ActionEvent event) throws  Exception{
        if(descricao.getText().isEmpty() || valor.getText().isEmpty() || quantidade.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos obrigatóros não preenchidos!!");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, complete os campos obrigatórios.");
            alert.showAndWait();
            alert.getOnCloseRequest();
        } else {
            float f;
            Locale pt = new Locale ("pt", "BR");
            NumberFormat nf = NumberFormat.getInstance(pt);
            DecimalFormat decimal = new DecimalFormat( "00.00" );
            Estoque estoque = new Estoque();
            f = nf.parse(valor.getText()).floatValue();
            estoque.setItem(descricao.getText());
            estoque.setQuantidade(Integer.parseInt(quantidade.getText()));
            estoque.setValorItem(nf.parse(valor.getText()).floatValue());
            EstoqueDAO dao = new EstoqueDAO();
            try{
                dao.addDatabase(estoque);
                Stage stage = (Stage) salvar.getScene().getWindow();
                stage.close();

            }catch (RGExistsException e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Item já cadastrado");
                alert.setHeaderText(null);
                alert.setContentText("Item já cadastrado!!");
                alert.showAndWait();
                alert.getOnCloseRequest();
            }
        }
    }
}
