package rbc.Interface;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerDadosDepositos {
    public TextField data_saida;
    public Label valor_total;
    public TextField data;
    public TextField metodo;
    public TextField valor;
    public Button Adicionar;
    public Button Editar;
    public Button Excluir;
    public Button Salvar;
    public Button Cancelar;

    public void btncancelar (ActionEvent event) throws  Exception{
        Stage stage = (Stage) Cancelar.getScene().getWindow();
        stage.close();
    }
}
