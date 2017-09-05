package rbc.gerenciadores.gerenciador_motoristas;


import rbc.gerenciadores.gerenciador_depositos.ListaDepositos;
import rbc.gerenciadores.gerenciador_encargo.ListaEncargos;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by leopuglia on 06/06/17.
 */

@Entity
public class Motorista {
    @Id
    private String RG;
    private String CPF;
    private String CEP;
    private String CNH;
    private String telefone;
    private String celular;
    private String name;
    private String lastName;
    private String dataNasc;
    private String endereco;
    private String bairro;
    private String cidade;
    private String UF;
    private String nameOp;
    private String phoneOp;
    private String nameOp2;
    private String phoneOp2;
    @OneToMany
    private List<ListaEncargos> listaEncargos =  new ArrayList<ListaEncargos>();
    @OneToMany
    private List<ListaDepositos> listaDepositos =  new ArrayList<ListaDepositos>();

    public Motorista() {
    }

    public Motorista(String RG, String CPF, String CEP,String CNH, String telefone, String celular, String name, String lastName, String dataNasc, String endereco, String bairro, String cidade, String UF, String nameOp, String phoneOp, String nameOp2, String phoneOp2) {
        this.RG = RG;
        this.CPF = CPF;
        this.CEP = CEP;
        this.CNH = CNH;
        this.telefone = telefone;
        this.celular = celular;
        this.name = name;
        this.lastName = lastName;
        this.dataNasc = dataNasc;
        this.endereco = endereco;
        this.bairro = bairro;
        this.cidade = cidade;
        this.UF = UF;
        this.nameOp = nameOp;
        this.phoneOp = phoneOp;
        this.nameOp2 = nameOp2;
        this.phoneOp2 = phoneOp2;
    }

    public String getPhoneOp2() {
        return phoneOp2;
    }

    public void setPhoneOp2(String phoneOp2) {
        this.phoneOp2 = phoneOp2;
    }

    public String getNameOp2() {
        return nameOp2;
    }

    public void setNameOp2(String nameOp2) {
        this.nameOp2 = nameOp2;
    }

    public String getCNH() {
        return CNH;
    }

    public void setCNH(String CNH) {
        this.CNH = CNH;
    }

    public String getRG() {
        return RG;
    }

    public void setRG(String RG) {
        this.RG = RG;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUF() {
        return UF;
    }

    public void setUF(String UF) {
        this.UF = UF;
    }

    public String getNameOp() {
        return nameOp;
    }

    public void setNameOp(String nameOp) {
        this.nameOp = nameOp;
    }

    public String getPhoneOp() {
        return phoneOp;
    }

    public void setPhoneOp(String phoneOp) {
        this.phoneOp = phoneOp;
    }

    public List<ListaEncargos> getListaEncargos() {
        return listaEncargos;
    }

    public void setListaEncargos(List<ListaEncargos> listaEncargos) {
        this.listaEncargos = listaEncargos;
    }

    public List<ListaDepositos> getListaDepositos() {
        return listaDepositos;
    }

    public void setListaDepositos(List<ListaDepositos> listaDepositos) {
        this.listaDepositos = listaDepositos;
    }
}

