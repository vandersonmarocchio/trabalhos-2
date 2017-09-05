package rbc.gerenciadores.gerenciador_motoristas;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Diogo on 11/07/17.
 */

public class MotoristaView {
    private StringProperty RG;
    private StringProperty CPF;
    private StringProperty CEP;
    private StringProperty CNH;
    private StringProperty telefone;
    private StringProperty celular;
    private StringProperty name;
    private StringProperty lastName;
    private StringProperty dataNasc;
    private StringProperty endereco;
    private StringProperty bairro;
    private StringProperty cidade;
    private StringProperty UF;
    private StringProperty nameOp;
    private StringProperty phoneOp;
    private StringProperty nameOp2;
    private StringProperty phoneOp2;
    private Motorista motorista;

    public MotoristaView() {
    }

    public String getPhoneOp2() {
        return phoneOp2.get();
    }

    public StringProperty phoneOp2Property() {
        return phoneOp2;
    }

    public void setPhoneOp2(String phoneOp2) {
        this.phoneOp2.set(phoneOp2);
    }

    public String getNameOp2() {
        return nameOp2.get();
    }

    public StringProperty nameOp2Property() {
        return nameOp2;
    }

    public void setNameOp2(String nameOp2) {
        this.nameOp2.set(nameOp2);
    }

    public String getRG() {
        return RG.get();
    }

    public void setRG(String RG) {
        this.RG.set(RG);
    }

    public StringProperty getRGProperty() {
        return RG;
    }

    public String getCPF() {
        return CPF.get();
    }

    public void setCPF(String CPF) {
        this.CPF.set(CPF);
    }

    public StringProperty getCPFProperty() {
        return CPF;
    }

    public String getCEP() {
        return CEP.get();
    }

    public void setCEP(String CEP) {
        this.CEP.set(CEP);
    }

    public StringProperty getCEPProperty() {
        return CEP;
    }

    public String getCNH() {
        return CNH.get();
    }

    public StringProperty CNHProperty() {
        return CNH;
    }

    public void setCNH(String CNH) {
        this.CNH.set(CNH);
    }

    public String gettelefone() {
        return telefone.get();
    }

    public void settelefone(String telefone) {
        this.telefone.set(telefone);
    }

    public StringProperty getTelefoneProperty() {
        return telefone;
    }

    public String getcelular() {
        return celular.get();
    }

    public void setCelular(String celular) {
        this.celular.set(celular);
    }

    public StringProperty getCelularProperty() {
        return celular;
    }

    public String getname() {
        return name.get().toString();
    }

    public void setname(String name) {
        this.name.set(name);
    }

    public StringProperty getnameProperty() {
        return name;
    }

    public String getlastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty getLastNameProperty() {
        return lastName;
    }

    public String getdataNasc() {
        return dataNasc.get();
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc.set(dataNasc);
    }

    public StringProperty getDataNascProperty() {
        return dataNasc;
    }

    public String getEndereco() {
        return endereco.get();
    }

    public void setEndereco(String endereco) {
        this.endereco.set(endereco);
    }

    public StringProperty getEnderecoProperty() {
        return endereco;
    }

    public String getbairro() {
        return bairro.get();
    }

    public void setBairro(String bairro) {
        this.bairro.set(bairro);
    }

    public StringProperty getbairroProperty() {
        return bairro;
    }

    public String getcidade() {
        return cidade.get();
    }

    public void setcidade(String cidade) {
        this.cidade.set(cidade);
    }

    public StringProperty getcidadeProperty() {
        return cidade;
    }

    public String getUF() {
        return UF.get();
    }

    public void setUF(String UF) {
        this.UF.set(UF);
    }

    public StringProperty getUFProperty() {
        return UF;
    }

    public String getnameOp() {
        return nameOp.get();
    }

    public void setnameOp(String nameOp) {
        this.nameOp.set(nameOp);
    }

    public StringProperty getnameOpProperty() {
        return nameOp;
    }

    public String getphoneOp() {
        return phoneOp.get();
    }

    public void setphoneOp(String phoneOp) {
        this.phoneOp.set(phoneOp);
    }

    public StringProperty getphoneOpProperty() {
        return phoneOp;
    }

    public Motorista getMotorista(){
        return motorista;
    }

    public MotoristaView(Motorista motorista) {
        this.RG = new SimpleStringProperty(motorista.getRG());
        this.CPF = new SimpleStringProperty(motorista.getCPF());
        this.CEP = new SimpleStringProperty(motorista.getCEP());
        this.CNH = new SimpleStringProperty(motorista.getCNH());
        this.telefone = new SimpleStringProperty(motorista.getTelefone());
        this.celular = new SimpleStringProperty(motorista.getCelular());
        this.name = new SimpleStringProperty(motorista.getName());
        this.lastName = new SimpleStringProperty(motorista.getLastName());
        this.dataNasc = new SimpleStringProperty(motorista.getDataNasc());
        this.endereco = new SimpleStringProperty(motorista.getEndereco());
        this.bairro = new SimpleStringProperty(motorista.getBairro());
        this.cidade = new SimpleStringProperty(motorista.getCidade());
        this.UF = new SimpleStringProperty(motorista.getUF());
        this.nameOp = new SimpleStringProperty(motorista.getNameOp());
        this.phoneOp = new SimpleStringProperty(motorista.getPhoneOp());
        this.nameOp2 = new SimpleStringProperty(motorista.getNameOp2());
        this.phoneOp2 = new SimpleStringProperty(motorista.getPhoneOp2());
        this.motorista = motorista;
    }

    public String toString(){
        return this.getname();
    }
}