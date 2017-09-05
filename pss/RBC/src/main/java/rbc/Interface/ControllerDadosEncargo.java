package rbc.Interface;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import rbc.gerenciadores.gerenciador_encargo.Encargo;
import rbc.gerenciadores.gerenciador_encargo.EncargoDAO;
import rbc.gerenciadores.gerenciador_encargo.ListaEncargos;
import rbc.gerenciadores.gerenciador_motoristas.MotoristaView;
import rbc.infraestrutura.auxEdicao;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerDadosEncargo implements Initializable {
    public Label valor_total;
    public TextField descricao;
    public TextField valor;
    public TextField mes;
    public Button Adicionar;
    public Button Editar;
    public Button Excluir;
    public Button Cancelar;
    public Button Salvar;
    public TableColumn coluna_descricao;
    public TableColumn coluna_valor;
    public Text nome_funcionario;

    public void btncancelar (ActionEvent event) throws  Exception{
        Stage stage = (Stage) Cancelar.getScene().getWindow();
        stage.close();
    }

    public void btnadicionar (ActionEvent event) throws Exception{
        /*Locale pt = new Locale ("pt", "BR");
        NumberFormat nf = NumberFormat.getInstance(pt);
        DecimalFormat decimal = new DecimalFormat( "00.00" );
        */
        ListaEncargos lista = new ListaEncargos();
        Encargo encargo = new Encargo();
        EncargoDAO dao1 = new EncargoDAO();
        encargo.setDescricao(descricao.getText());
        lista.addEncargo(encargo);
        dao1.addDatabase(encargo);
        System.out.println(encargo.getDescricao());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        MotoristaView motorista;
        motorista = (MotoristaView) auxEdicao.objeto;
        nome_funcionario.setText(motorista.getMotorista().getName());
    }
}