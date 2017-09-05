package rbc.Interface;


import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ControllerDadosViagem implements Initializable{
    public TextField data_saida;
    public TextField data_chegada;
    public CheckBox frete_cheio;
    public TextField frete1;
    public TextField frete2;
    public TextField frete3;
    public TextField frete4;
    public TextField frete5;
    public TextField frete6;
    public TextField frete7;
    public TextField frete8;
    public TextField combustivel1;
    public TextField combustivel2;
    public TextField combustivel3;
    public TextField combustivel4;
    public TextField combustivel5;
    public TextField combustivel6;
    public TextField combustivel7;
    public TextField combustivel8;
    public TextField pedagio1;
    public TextField pedagio2;
    public TextField pedagio3;
    public TextField pedagio4;
    public TextField pedagio5;
    public TextField pedagio6;
    public TextField pedagio7;
    public TextField pedagio8;
    public TextField pedagio9;
    public TextField pedagio10;
    public TextField pedagio11;
    public TextField pedagio12;
    public TextField pedagio13;
    public TextField pedagio14;
    public TextField pedagio15;
    public TextField pedagio16;
    public TextField mecanica1;
    public TextField mecanica2;
    public TextField mecanica3;
    public TextField mecanica4;
    public TextField mecanica5;
    public TextField mecanica6;
    public TextField mecanica7;
    public TextField mecanica8;
    public TextField limpeza1;
    public TextField limpeza2;
    public TextField limpeza3;
    public TextField limpeza4;
    public TextField limpeza5;
    public TextField limpeza6;
    public TextField limpeza7;
    public TextField limpeza8;
    public TextField borracharia1;
    public TextField borracharia2;
    public TextField borracharia3;
    public TextField borracharia4;
    public TextField borracharia5;
    public TextField borracharia6;
    public TextField borracharia7;
    public TextField borracharia8;
    public TextField outros1;
    public TextField outros2;
    public TextField outros3;
    public TextField outros4;
    public TextField outros5;
    public TextField outros6;
    public TextField outros7;
    public TextField outros8;
    public Label total_frete;
    public Label total_combustivel;
    public Label total_pedagio;
    public Label total_mecanica;
    public Label total_limpeza;
    public Label total_borracharia;
    public Label total_outros;
    public Label total_liquido;
    public Button Salvar;
    public Button Cancelar;
    public Button teste;
    public Text comi;
    float totalFrete;
    float comissao;
    float totalCombustivel;
    float totalPedagio;
    float totalMecanica;
    float totalLimpeza;
    float totalBorracharia;
    float totalOutros;
    float liquido;

    public void somarfrete(){
        Locale pt = new Locale ("pt", "BR");
        NumberFormat nf = NumberFormat.getInstance(pt);
        DecimalFormat decimal = new DecimalFormat( "00.00" );
        float f;
        try {
            f = nf.parse(frete1.getText()).floatValue() + nf.parse(frete2.getText()).floatValue() + nf.parse(frete3.getText()).floatValue()
            + nf.parse(frete4.getText()).floatValue() + nf.parse(frete5.getText()).floatValue() + nf.parse(frete6.getText()).floatValue()
            + nf.parse(frete7.getText()).floatValue() + nf.parse(frete8.getText()).floatValue();

            total_frete.setText("R$ " + decimal.format(f));
            setTotalFrete(f);
        } catch (ParseException ex){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("erro");
            alert.setHeaderText(null);
            alert.setContentText("Erro!!");
            alert.showAndWait();
            alert.getOnCloseRequest();
        }
    }

    public void somarcombustivel(){
        Locale pt = new Locale ("pt", "BR");
        NumberFormat nf = NumberFormat.getInstance(pt);
        DecimalFormat decimal = new DecimalFormat( "00.00" );
        float f;
        try {
            f = nf.parse(combustivel1.getText()).floatValue() + nf.parse(combustivel2.getText()).floatValue() + nf.parse(combustivel3.getText()).floatValue()
                    + nf.parse(combustivel4.getText()).floatValue() + nf.parse(combustivel5.getText()).floatValue() + nf.parse(combustivel6.getText()).floatValue()
                    + nf.parse(combustivel7.getText()).floatValue() + nf.parse(combustivel8.getText()).floatValue();

            total_combustivel.setText("R$ " + decimal.format(f));
            setTotalCombustivel(f);
        } catch (ParseException ex){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("erro");
            alert.setHeaderText(null);
            alert.setContentText("Erro!!");
            alert.showAndWait();
            alert.getOnCloseRequest();
        }
    }

    public void somarpedagio(){
        Locale pt = new Locale ("pt", "BR");
        NumberFormat nf = NumberFormat.getInstance(pt);
        DecimalFormat decimal = new DecimalFormat( "00.00" );
        float f;
        try {
            f = nf.parse(pedagio1.getText()).floatValue() + nf.parse(pedagio2.getText()).floatValue() + nf.parse(pedagio3.getText()).floatValue()
                    + nf.parse(pedagio4.getText()).floatValue() + nf.parse(pedagio5.getText()).floatValue() + nf.parse(pedagio6.getText()).floatValue()
                    + nf.parse(pedagio7.getText()).floatValue() + nf.parse(pedagio8.getText()).floatValue() + nf.parse(pedagio9.getText()).floatValue()
                    + nf.parse(pedagio10.getText()).floatValue() + nf.parse(pedagio11.getText()).floatValue() + nf.parse(pedagio12.getText()).floatValue()
                    + nf.parse(pedagio13.getText()).floatValue() + nf.parse(pedagio14.getText()).floatValue() + nf.parse(pedagio15.getText()).floatValue()
                    + nf.parse(pedagio16.getText()).floatValue();

            total_pedagio.setText("R$ " + decimal.format(f));
            setTotalPedagio(f);
        } catch (ParseException ex){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("erro");
            alert.setHeaderText(null);
            alert.setContentText("Erro!!");
            alert.showAndWait();
            alert.getOnCloseRequest();
        }
    }

    public void somarmecanica(){
        Locale pt = new Locale ("pt", "BR");
        NumberFormat nf = NumberFormat.getInstance(pt);
        DecimalFormat decimal = new DecimalFormat( "00.00" );
        float f;
        try {
            f = nf.parse(mecanica1.getText()).floatValue() + nf.parse(mecanica2.getText()).floatValue() + nf.parse(mecanica3.getText()).floatValue()
                    + nf.parse(mecanica4.getText()).floatValue() + nf.parse(mecanica5.getText()).floatValue() + nf.parse(mecanica6.getText()).floatValue()
                    + nf.parse(mecanica7.getText()).floatValue() + nf.parse(mecanica8.getText()).floatValue();

            total_mecanica.setText("R$ " + decimal.format(f));
            setTotalMecanica(f);
        } catch (ParseException ex){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("erro");
            alert.setHeaderText(null);
            alert.setContentText("Erro!!");
            alert.showAndWait();
            alert.getOnCloseRequest();
        }
    }

    public void somarborracharia(){
        Locale pt = new Locale ("pt", "BR");
        NumberFormat nf = NumberFormat.getInstance(pt);
        DecimalFormat decimal = new DecimalFormat( "00.00" );
        float f;
        try {
            f = nf.parse(borracharia1.getText()).floatValue() + nf.parse(borracharia2.getText()).floatValue() + nf.parse(borracharia3.getText()).floatValue()
                    + nf.parse(borracharia4.getText()).floatValue() + nf.parse(borracharia5.getText()).floatValue() + nf.parse(borracharia6.getText()).floatValue()
                    + nf.parse(borracharia7.getText()).floatValue() + nf.parse(borracharia8.getText()).floatValue();

            total_borracharia.setText("R$ " + decimal.format(f));
            setTotalBorracharia(f);
        } catch (ParseException ex){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("erro");
            alert.setHeaderText(null);
            alert.setContentText("Erro!!");
            alert.showAndWait();
            alert.getOnCloseRequest();
        }
    }

    public void somarlimpeza(){
        Locale pt = new Locale ("pt", "BR");
        NumberFormat nf = NumberFormat.getInstance(pt);
        DecimalFormat decimal = new DecimalFormat( "00.00" );
        float f;
        try {
            f = nf.parse(limpeza1.getText()).floatValue() + nf.parse(limpeza2.getText()).floatValue() + nf.parse(limpeza3.getText()).floatValue()
                    + nf.parse(limpeza4.getText()).floatValue() + nf.parse(limpeza5.getText()).floatValue() + nf.parse(limpeza6.getText()).floatValue()
                    + nf.parse(limpeza7.getText()).floatValue() + nf.parse(limpeza8.getText()).floatValue();

            total_limpeza.setText("R$ " + decimal.format(f));
            setTotalLimpeza(f);
        } catch (ParseException ex){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("erro");
            alert.setHeaderText(null);
            alert.setContentText("Erro!!");
            alert.showAndWait();
            alert.getOnCloseRequest();
        }
    }

    public void somaroutros(){
        Locale pt = new Locale ("pt", "BR");
        NumberFormat nf = NumberFormat.getInstance(pt);
        DecimalFormat decimal = new DecimalFormat( "00.00" );
        float f;
        try {
            f = nf.parse(outros1.getText()).floatValue() + nf.parse(outros2.getText()).floatValue() + nf.parse(outros3.getText()).floatValue()
                    + nf.parse(outros4.getText()).floatValue() + nf.parse(outros5.getText()).floatValue() + nf.parse(outros6.getText()).floatValue()
                    + nf.parse(outros7.getText()).floatValue() + nf.parse(outros8.getText()).floatValue();

            total_outros.setText("R$ " + decimal.format(f));
            setTotalOutros(f);
        } catch (ParseException ex){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("erro");
            alert.setHeaderText(null);
            alert.setContentText("Erro!!");
            alert.showAndWait();
            alert.getOnCloseRequest();
        }
    }

    public void somarliquido(){
        Locale pt = new Locale ("pt", "BR");
        NumberFormat nf = NumberFormat.getInstance(pt);
        DecimalFormat decimal = new DecimalFormat( "00.00" );
        float f;
        f = getTotalFrete() - getComissao() - getTotalCombustivel() - getTotalPedagio() - getTotalMecanica() - getTotalBorracharia() - getTotalLimpeza() - getTotalOutros();
        total_liquido.setText("R$ " + decimal.format(f));
        setLiquido(f);
    }

    public void comissao() {
        float f;
        DecimalFormat decimal = new DecimalFormat( "00.00" );
        if(!frete_cheio.isSelected()) {
            f = (getTotalFrete() * 12) / 100;
            setComissao(f);
            comi.setText("R$ " + decimal.format(f));
        }else{
            f = ((getTotalFrete()-getTotalPedagio()) * 12) / 100;
            setComissao(f);
            comi.setText("R$ " + decimal.format(f));
        }
    }

    public void btnCalcular(){
        somarfrete();
        somarcombustivel();
        somarpedagio();
        somarmecanica();
        somarborracharia();
        somarlimpeza();
        somaroutros();
        comissao();
        somarliquido();
    }

    public void btncancelar (ActionEvent event) throws  Exception{
        Stage stage = (Stage) Cancelar.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        frete1.setText("0");
        frete2.setText("0");
        frete3.setText("0");
        frete4.setText("0");
        frete5.setText("0");
        frete6.setText("0");
        frete7.setText("0");
        frete8.setText("0");
        combustivel1.setText("0");
        combustivel2.setText("0");
        combustivel3.setText("0");
        combustivel4.setText("0");
        combustivel5.setText("0");
        combustivel6.setText("0");
        combustivel7.setText("0");
        combustivel8.setText("0");
        pedagio1.setText("0");
        pedagio2.setText("0");
        pedagio3.setText("0");
        pedagio4.setText("0");
        pedagio5.setText("0");
        pedagio6.setText("0");
        pedagio7.setText("0");
        pedagio8.setText("0");
        pedagio9.setText("0");
        pedagio10.setText("0");
        pedagio11.setText("0");
        pedagio12.setText("0");
        pedagio13.setText("0");
        pedagio14.setText("0");
        pedagio15.setText("0");
        pedagio16.setText("0");
        mecanica1.setText("0");
        mecanica2.setText("0");
        mecanica3.setText("0");
        mecanica4.setText("0");
        mecanica5.setText("0");
        mecanica6.setText("0");
        mecanica7.setText("0");
        mecanica8.setText("0");
        limpeza1.setText("0");
        limpeza2.setText("0");
        limpeza3.setText("0");
        limpeza4.setText("0");
        limpeza5.setText("0");
        limpeza6.setText("0");
        limpeza7.setText("0");
        limpeza8.setText("0");
        borracharia1.setText("0");
        borracharia2.setText("0");
        borracharia3.setText("0");
        borracharia4.setText("0");
        borracharia5.setText("0");
        borracharia6.setText("0");
        borracharia7.setText("0");
        borracharia8.setText("0");
        outros1.setText("0");
        outros2.setText("0");
        outros3.setText("0");
        outros4.setText("0");
        outros5.setText("0");
        outros6.setText("0");
        outros7.setText("0");
        outros8.setText("0");
        total_frete.setText("R$ 00,00");
        total_combustivel.setText("R$ 00,00");
        total_pedagio.setText("R$ 00,00");
        total_mecanica.setText("R$ 00,00");
        total_limpeza.setText("R$ 00,00");
        total_outros.setText("R$ 00,00");
        total_borracharia.setText("R$ 00,00");
        total_liquido.setText("R$ 00,00");
        comi.setText("R$ 00,00");
    }

    public float getTotalFrete() {
        return totalFrete;
    }

    public void setTotalFrete(float totalFrete) {
        this.totalFrete = totalFrete;
    }

    public float getTotalCombustivel() {
        return totalCombustivel;
    }

    public void setTotalCombustivel(float totalCombustivel) {
        this.totalCombustivel = totalCombustivel;
    }

    public float getTotalPedagio() {
        return totalPedagio;
    }

    public void setTotalPedagio(float totalPedagio) {
        this.totalPedagio = totalPedagio;
    }

    public float getTotalMecanica() {
        return totalMecanica;
    }

    public void setTotalMecanica(float totalMecanica) {
        this.totalMecanica = totalMecanica;
    }

    public float getTotalBorracharia() {
        return totalBorracharia;
    }

    public void setTotalBorracharia(float totalBorracharia) {
        this.totalBorracharia = totalBorracharia;
    }

    public float getTotalOutros() {
        return totalOutros;
    }

    public void setTotalOutros(float totalOutros) {
        this.totalOutros = totalOutros;
    }

    public float getLiquido() {
        return liquido;
    }

    public void setLiquido(float liquido) {
        this.liquido = liquido;
    }

    public float getTotalLimpeza() {
        return totalLimpeza;
    }

    public void setTotalLimpeza(float totalLimpeza) {
        this.totalLimpeza = totalLimpeza;
    }

    public float getComissao() {
        return comissao;
    }

    public void setComissao(float comissao) {
        this.comissao = comissao;
    }

}
