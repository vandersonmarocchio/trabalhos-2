package controlador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import modelo.Busca;
import modelo.Clock;
import modelo.Decodificacao;
import modelo.Escrita;
import modelo.Execucao;
import modelo.Pipeline;
import modelo.Registradores;
import org.zkoss.util.media.Media;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class MipsController extends Window {

    private Window winMips;
    private Window winAjuda;
    private Media media;
    private Label labelNomeArquivo;
    private Label labelNumClock;
    private Label labelNumPc;
    private Listbox lbMemoria;
    private Listbox lbMapaPc;
    private Listbox lbMapaLabel;
    private Listbox lbRegistradores;
    private Listbox lbPipeline;
    private Textbox arquivo;
    private Button avancarClock;

    private Pipeline pipeline;
    private List<Pair<Integer, String[]>> listaPc;
    private List<Pair<Integer, String>> listaLabel;
    private List<Clock> listaClocks;
    private List<Integer> registradores;
    private List<String[]> linhas;

    private int numeroClock;
    private int pc;
    private boolean dependenciaDeDados;
    private boolean precisaDeDoisCiclos;
    private boolean casoEspecial;
    private boolean terminouCasoEspecial;
    private boolean jump;

    public void onCreate() {
        this.setWinMips((Window) getFellow("winMips"));
        this.setWinAjuda((Window) getFellow("winAjuda"));
        this.setLabelNomeArquivo((Label) getFellow("labelNomeArquivo"));
        this.setLabelNumClock((Label) getFellow("labelNumClock"));
        this.setLabelNumPc((Label) getFellow("labelNumPc"));
        this.setLbMapaLabel((Listbox) getFellow("lbMapaLabel"));
        this.setLbMapaPc((Listbox) getFellow("lbMapaPc"));
        this.setLbMemoria((Listbox) getFellow("lbMemoria"));
        this.setLbRegistradores((Listbox) getFellow("lbRegistradores"));
        this.setLbPipeline((Listbox) getFellow("lbPipeline"));
        this.setArquivo((Textbox) getFellow("arquivo"));
        this.setAvancarClock((Button) getFellow("avancarClock"));

        this.pipeline = new Pipeline();
        this.listaPc = new ArrayList<Pair<Integer, String[]>>();
        this.listaLabel = new ArrayList<Pair<Integer, String>>();
        this.listaClocks = new ArrayList<Clock>();
        this.registradores = new Registradores().getRegistradores();
        this.linhas = new ArrayList<String[]>();
        this.numeroClock = 0;
        this.pc = 0;
        this.dependenciaDeDados = false;
        this.precisaDeDoisCiclos = false;
        this.casoEspecial = false;
        this.terminouCasoEspecial = false;
        this.labelNumPc.setValue(String.valueOf(this.pc));
        this.labelNumClock.setValue(String.valueOf(this.numeroClock));
    }

    public void abrirAjuda() {
        this.winAjuda.setVisible(true);
    }

    public void fecharAjuda() {
        this.winAjuda.setVisible(false);
    }

    public void reset() {
        this.pipeline = new Pipeline();
        this.listaPc = new ArrayList<Pair<Integer, String[]>>();
        this.listaLabel = new ArrayList<Pair<Integer, String>>();
        this.listaClocks = new ArrayList<Clock>();
        this.registradores = new Registradores().getRegistradores();
        this.linhas = new ArrayList<String[]>();
        this.numeroClock = 0;
        this.pc = 0;
        this.dependenciaDeDados = false;
        this.precisaDeDoisCiclos = false;
        this.casoEspecial = false;
        this.terminouCasoEspecial = false;
        this.labelNumPc.setValue(String.valueOf(this.pc));
        this.labelNumClock.setValue(String.valueOf(this.numeroClock));
        this.lbPipeline.getItems().clear();

        try {
            this.enviarArquivo(this.media);
        } catch (InterruptedException ex) {
            Logger.getLogger(MipsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MipsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void enviarArquivo(Media media) throws InterruptedException, FileNotFoundException, IOException {
        this.media = media;
        if (this.media != null) {
            String nomeDoArquivo = media.getName();
            if (media.getFormat().equals("txt")) {
                this.labelNomeArquivo.setValue(nomeDoArquivo);
                this.numeroClock = 1;
                this.labelNumClock.setValue(String.valueOf(this.numeroClock));
                this.pc = 1;
                this.labelNumPc.setValue(String.valueOf(this.pc));
                lerArquivo(media, nomeDoArquivo);
            } else {
                Messagebox.show("Formato incorreto. Adicione um arquivo TXT!", "Erro ao carregar arquivo", Messagebox.OK, Messagebox.ERROR);
            }
        }
    }

    public void atualizaRegistradores() {
        this.lbRegistradores.getItems().clear();
        Listitem item = new Listitem();
        for (int i = 0; i < this.registradores.size(); i++) {
            Listcell cellNumero = new Listcell();
            cellNumero.setLabel(Integer.toString(this.registradores.get(i)));
            item.appendChild(cellNumero);
        }
        item.setParent(this.lbRegistradores);
    }

    public void atualizaMemoria() {
        this.lbMemoria.getItems().clear();
        boolean aux = false;
        for (int i = 0; i < this.listaPc.size(); i++) {
            if (listaPc.get(i).getKey() == pc) {
                aux = true;
            }
            if (aux) {
                this.lbMemoria.appendItem(Arrays.toString(this.listaPc.get(i).getValue()), "");
            }
        }
    }

    public void lerArquivo(Media media, String nomeDoArquivo) throws IOException {
        try {
            this.media = media;
            Reader fileReader;

            if (this.media.isBinary()) {
                fileReader = new InputStreamReader(this.media.getStreamData());//arquivo RET
            } else {
                fileReader = this.media.getReaderData();//para arquivo TXT
            }

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String linha = "";

            int mapaPc = 1;
            arquivo.setValue("");
            while ((linha = bufferedReader.readLine()) != null) {
                arquivo.setValue(arquivo.getValue() + linha + "\n");
                linha = linha.replace("    ", "");
                linha = linha.replace("\t", "");
                linha = linha.replace(",", "");

                if (!linha.isEmpty()) {

                    String[] parts = linha.split(" ");

                    if (parts[0].contains(":")) {
                        Pair<Integer, String> pair = new Pair<Integer, String>(mapaPc, parts[0]);
                        this.listaLabel.add(pair);
                    }

                    if (!parts[0].contains(":")) {
                        linhas.add(parts);
                        Pair<Integer, String[]> pair = new Pair<Integer, String[]>(mapaPc, parts);
                        this.listaPc.add(pair);
                        mapaPc++;
                    }
                }
            }
            if (arquivo.getValue().isEmpty()) {
                this.avancarClock.setDisabled(true);
                Messagebox.show("Arquivo vazio!!", "Erro ao carregar arquivo", Messagebox.OK, Messagebox.ERROR);
            } else {
                this.avancarClock.setDisabled(false);
            }

            this.lbMapaPc.getItems().clear();
            for (Pair<Integer, String[]> pair : listaPc) {
                this.lbMapaPc.appendItem("Linha: " + pair.getKey() + " Código: " + Arrays.toString(pair.getValue()), "Linha: " + pair.getKey() + " Código: " + Arrays.toString(pair.getValue()));
            }

            this.lbMapaLabel.getItems().clear();
            for (Pair<Integer, String> pair : listaLabel) {
                this.lbMapaLabel.appendItem("Linha: " + pair.getKey() + " Label: " + pair.getValue(), "Linha: " + pair.getKey() + " Label: " + pair.getValue());
            }

            this.atualizaMemoria();
            this.atualizaRegistradores();
            fileReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            Messagebox.show("Erro: " + e, "Erro ao carregar arquivo", Messagebox.OK, Messagebox.ERROR);

        }

    }

    public void avancarClock() {
        if (this.media != null) {
            Clock clock = new Clock();
            if (this.numeroClock == 1 || this.jump) {
                Busca busca = new Busca(this.listaPc, this.pc);
                clock.setBusca(busca);
                this.pc++;
                this.jump = false;
                this.labelNumPc.setValue(String.valueOf(this.pc));

                Listitem item = new Listitem();
                Listcell cellClock = new Listcell();
                Listcell cellBusca = new Listcell();
                Listcell cellDecod = new Listcell();
                Listcell cellExecu = new Listcell();
                Listcell cellEscri = new Listcell();

                cellClock.setLabel(String.valueOf(this.numeroClock));
                cellBusca.setLabel(Arrays.toString(clock.getBusca().getComandoBuscado()));
                cellDecod.setLabel("---");
                cellExecu.setLabel("---");
                cellEscri.setLabel("---");

                item.appendChild(cellClock);
                item.appendChild(cellBusca);
                item.appendChild(cellDecod);
                item.appendChild(cellExecu);
                item.appendChild(cellEscri);

                item.setParent(this.lbPipeline);

                this.labelNumClock.setValue(String.valueOf(this.numeroClock));
                this.numeroClock++;

                this.listaClocks.add(clock);
                this.atualizaMemoria();

            } else if (this.terminouCasoEspecial) {
                this.terminouCasoEspecial = false;
                Listitem item = new Listitem();

                Listcell cellClock = new Listcell();
                Listcell cellBusca = new Listcell();
                Listcell cellDecod = new Listcell();
                Listcell cellExecu = new Listcell();
                Listcell cellEscri = new Listcell();

                cellClock.setLabel(String.valueOf(this.numeroClock));
                item.appendChild(cellClock);

                if (temInstrucao()) {
                    Busca busca = new Busca(this.listaPc, this.pc);
                    clock.setBusca(busca);
                    this.pc++;
                    this.labelNumPc.setValue(String.valueOf(this.pc));
                    cellBusca.setLabel(Arrays.toString(clock.getBusca().getComandoBuscado()));
                    item.appendChild(cellBusca);
                } else {
                    cellBusca.setLabel("---");
                    item.appendChild(cellBusca);
                }
                if (this.temBusca3()) {
                    Decodificacao decod = new Decodificacao(this.getUltimaDecodificacao());
                    clock.setDecodificacao(decod);
                    cellDecod.setLabel(Arrays.toString(clock.getDecodificacao().getExecucao()));
                    item.appendChild(cellDecod);
                } else {
                    cellDecod.setLabel("---");
                    item.appendChild(cellDecod);
                }
                if (this.temDecodificacao3()) {
                    Execucao exec = new Execucao(this.getUltimaExecucao(), this.registradores);
                    clock.setExecucao(exec);
                    if (this.listaClocks.get(this.listaClocks.size() - 3).getDecodificacao().isJump()) {
                        this.pc = exec.setaPc(listaLabel, exec.getLabel());
                        this.jump = true;
                    }
                    cellExecu.setLabel(Arrays.toString(clock.getExecucao().getEscrever()));
                    item.appendChild(cellExecu);
                } else {
                    cellExecu.setLabel("---");
                    item.appendChild(cellExecu);
                }
                cellEscri.setLabel("---");
                item.appendChild(cellEscri);
                item.setParent(this.lbPipeline);

                this.labelNumClock.setValue(String.valueOf(this.numeroClock));
                this.numeroClock++;
                this.listaClocks.add(clock);

                this.atualizaMemoria();
                this.atualizaRegistradores();

            } else if (this.casoEspecial) {

                Escrita escr = new Escrita(this.listaClocks.get(this.listaClocks.size() - 2).getExecucao().getEscrever(), this.registradores);
                this.casoEspecial = false;
                this.terminouCasoEspecial = true;
                clock.setEscrita(escr);
                Listitem item = new Listitem();

                Listcell cellClock = new Listcell();
                Listcell cellBusca = new Listcell();
                Listcell cellDecod = new Listcell();
                Listcell cellExecu = new Listcell();
                Listcell cellEscri = new Listcell();

                cellClock.setLabel(String.valueOf(this.numeroClock));
                cellBusca.setLabel("STALL");
                cellDecod.setLabel("STALL");
                cellExecu.setLabel("STALL");
                cellEscri.setLabel(Arrays.toString(clock.getEscrita().getEscrita()));

                item.appendChild(cellClock);
                item.appendChild(cellBusca);
                item.appendChild(cellDecod);
                item.appendChild(cellExecu);
                item.appendChild(cellEscri);

                item.setParent(this.lbPipeline);
                this.labelNumClock.setValue(String.valueOf(this.numeroClock));

                this.numeroClock++;
                this.listaClocks.add(clock);

                this.atualizaMemoria();
                this.atualizaRegistradores();

            } else if (this.precisaDeDoisCiclos) {

                this.precisaDeDoisCiclos = false;
                Listitem item = new Listitem();

                Listcell cellClock = new Listcell();
                Listcell cellBusca = new Listcell();
                Listcell cellDecod = new Listcell();
                Listcell cellExecu = new Listcell();
                Listcell cellEscri = new Listcell();

                cellClock.setLabel(String.valueOf(this.numeroClock));
                item.appendChild(cellClock);

                if (temInstrucao()) {
                    Busca busca = new Busca(this.listaPc, this.pc);
                    clock.setBusca(busca);
                    this.pc++;
                    this.labelNumPc.setValue(String.valueOf(this.pc));
                    cellBusca.setLabel(Arrays.toString(clock.getBusca().getComandoBuscado()));
                    item.appendChild(cellBusca);
                } else {
                    cellBusca.setLabel("---");
                    item.appendChild(cellBusca);
                }
                if (this.temBusca2()) {
                    Decodificacao decod = new Decodificacao(this.getUltimaDecodificacao());
                    clock.setDecodificacao(decod);
                    cellDecod.setLabel(Arrays.toString(clock.getDecodificacao().getExecucao()));
                    item.appendChild(cellDecod);
                } else {
                    cellDecod.setLabel("---");
                    item.appendChild(cellDecod);
                }
                if (this.temDecodificacao2()) {
                    Execucao exec = new Execucao(this.getUltimaExecucao(), this.registradores);
                    clock.setExecucao(exec);
                    if (this.listaClocks.get(this.listaClocks.size() - 2).getDecodificacao().isJump()) {
                        this.pc = exec.setaPc(listaLabel, exec.getLabel());
                        this.jump = true;
                    }
                    cellExecu.setLabel(Arrays.toString(clock.getExecucao().getEscrever()));
                    item.appendChild(cellExecu);
                } else {
                    cellExecu.setLabel("---");
                    item.appendChild(cellExecu);
                }
                if (this.temExecucao()) {
                    Escrita escr = new Escrita(this.listaClocks.get(this.listaClocks.size() - 1).getExecucao().getEscrever(), this.registradores);
                    clock.setEscrita(escr);
                    cellEscri.setLabel(Arrays.toString(clock.getEscrita().getEscrita()));
                    item.appendChild(cellEscri);
                } else {
                    cellEscri.setLabel("---");
                    item.appendChild(cellEscri);
                }
                item.setParent(this.lbPipeline);

                this.labelNumClock.setValue(String.valueOf(this.numeroClock));
                this.numeroClock++;
                this.listaClocks.add(clock);

                this.atualizaMemoria();
                this.atualizaRegistradores();
            } else if (this.dependenciaDeDados) {

                this.dependenciaDeDados = false;
                Listitem item = new Listitem();

                Listcell cellClock = new Listcell();
                Listcell cellBusca = new Listcell();
                Listcell cellDecod = new Listcell();
                Listcell cellExecu = new Listcell();
                Listcell cellEscri = new Listcell();

                cellClock.setLabel(String.valueOf(this.numeroClock));
                item.appendChild(cellClock);

                if (temInstrucao()) {
                    Busca busca = new Busca(this.listaPc, this.pc);
                    clock.setBusca(busca);
                    this.pc++;
                    this.labelNumPc.setValue(String.valueOf(this.pc));
                    cellBusca.setLabel(Arrays.toString(clock.getBusca().getComandoBuscado()));
                    item.appendChild(cellBusca);
                } else {
                    cellBusca.setLabel("---");
                    item.appendChild(cellBusca);
                }
                if (this.temBusca2()) {
                    Decodificacao decod = new Decodificacao(this.getUltimaDecodificacao());
                    clock.setDecodificacao(decod);
                    cellDecod.setLabel(Arrays.toString(clock.getDecodificacao().getExecucao()));
                    item.appendChild(cellDecod);
                } else {
                    cellDecod.setLabel("---");
                    item.appendChild(cellDecod);
                }
                if (this.temDecodificacao2()) {
                    Execucao exec = new Execucao(this.getUltimaExecucao(), this.registradores);
                    clock.setExecucao(exec);
                    if (this.listaClocks.get(this.listaClocks.size() - 2).getDecodificacao().isJump()) {
                        this.pc = exec.setaPc(listaLabel, exec.getLabel());
                        this.jump = true;
                    }
                    cellExecu.setLabel(Arrays.toString(clock.getExecucao().getEscrever()));
                    item.appendChild(cellExecu);
                } else {
                    cellExecu.setLabel("---");
                    item.appendChild(cellExecu);
                }
                cellEscri.setLabel("---");
                item.appendChild(cellEscri);
                item.setParent(this.lbPipeline);

                this.labelNumClock.setValue(String.valueOf(this.numeroClock));
                this.numeroClock++;
                this.listaClocks.add(clock);

                this.atualizaMemoria();
                this.atualizaRegistradores();

            } else if (this.precisaDeDoisCiclos() && this.temDependenciaDeDados()) {

                Execucao exec = this.listaClocks.get(this.listaClocks.size() - 1).getExecucao();

                this.casoEspecial = true;
                clock.setExecucao(exec);
                clock.getExecucao().setPrimeiraVez(false);
                Listitem item = new Listitem();

                Listcell cellClock = new Listcell();
                Listcell cellBusca = new Listcell();
                Listcell cellDecod = new Listcell();
                Listcell cellExecu = new Listcell();
                Listcell cellEscri = new Listcell();

                cellClock.setLabel(String.valueOf(this.numeroClock));
                cellBusca.setLabel("STALL");
                cellDecod.setLabel("STALL");
                cellExecu.setLabel(Arrays.toString(clock.getExecucao().getEscrever()));
                cellEscri.setLabel("STALL");

                item.appendChild(cellClock);
                item.appendChild(cellBusca);
                item.appendChild(cellDecod);
                item.appendChild(cellExecu);
                item.appendChild(cellEscri);

                item.setParent(this.lbPipeline);
                this.labelNumClock.setValue(String.valueOf(this.numeroClock));

                this.numeroClock++;
                this.listaClocks.add(clock);

                this.atualizaMemoria();
                this.atualizaRegistradores();

            } else if (this.temDependenciaDeDados()) {

                Escrita escr = new Escrita(this.listaClocks.get(this.listaClocks.size() - 1).getExecucao().getEscrever(), this.registradores);
                this.dependenciaDeDados = true;
                clock.setEscrita(escr);

                Listitem item = new Listitem();

                Listcell cellClock = new Listcell();
                Listcell cellBusca = new Listcell();
                Listcell cellDecod = new Listcell();
                Listcell cellExecu = new Listcell();
                Listcell cellEscri = new Listcell();

                cellClock.setLabel(String.valueOf(this.numeroClock));
                cellBusca.setLabel("STALL");
                cellDecod.setLabel("STALL");
                cellExecu.setLabel("STALL");
                cellEscri.setLabel(Arrays.toString(clock.getEscrita().getEscrita()));

                item.appendChild(cellClock);
                item.appendChild(cellBusca);
                item.appendChild(cellDecod);
                item.appendChild(cellExecu);
                item.appendChild(cellEscri);

                item.setParent(this.lbPipeline);
                this.labelNumClock.setValue(String.valueOf(this.numeroClock));

                this.numeroClock++;
                this.listaClocks.add(clock);

                this.atualizaMemoria();
                this.atualizaRegistradores();

            } else if (this.precisaDeDoisCiclos()) {

                Execucao exec = this.listaClocks.get(this.listaClocks.size() - 1).getExecucao();

                this.precisaDeDoisCiclos = true;
                clock.setExecucao(exec);
                clock.getExecucao().setPrimeiraVez(false);
                Listitem item = new Listitem();

                Listcell cellClock = new Listcell();
                Listcell cellBusca = new Listcell();
                Listcell cellDecod = new Listcell();
                Listcell cellExecu = new Listcell();
                Listcell cellEscri = new Listcell();

                cellClock.setLabel(String.valueOf(this.numeroClock));
                cellBusca.setLabel("STALL");
                cellDecod.setLabel("STALL");
                cellExecu.setLabel(Arrays.toString(clock.getExecucao().getEscrever()));
                cellEscri.setLabel("STALL");

                item.appendChild(cellClock);
                item.appendChild(cellBusca);
                item.appendChild(cellDecod);
                item.appendChild(cellExecu);
                item.appendChild(cellEscri);

                item.setParent(this.lbPipeline);
                this.labelNumClock.setValue(String.valueOf(this.numeroClock));

                this.numeroClock++;
                this.listaClocks.add(clock);

                this.atualizaMemoria();
                this.atualizaRegistradores();

            } else if (!temInstrucao() && !temBusca() && !temDecodificacao() && !temExecucao()) {
                Messagebox.show("Acabou as instruções!!", "Fim de programa", Messagebox.OK, Messagebox.INFORMATION);
            } else {
                Listitem item = new Listitem();

                Listcell cellClock = new Listcell();
                Listcell cellBusca = new Listcell();
                Listcell cellDecod = new Listcell();
                Listcell cellExecu = new Listcell();
                Listcell cellEscri = new Listcell();

                cellClock.setLabel(String.valueOf(this.numeroClock));
                item.appendChild(cellClock);

                if (temInstrucao()) {
                    Busca busca = new Busca(this.listaPc, this.pc);
                    clock.setBusca(busca);
                    this.pc++;
                    this.labelNumPc.setValue(String.valueOf(this.pc));
                    cellBusca.setLabel(Arrays.toString(clock.getBusca().getComandoBuscado()));
                    item.appendChild(cellBusca);
                } else {
                    cellBusca.setLabel("---");
                    item.appendChild(cellBusca);
                }
                if (this.temBusca()) {
                    Decodificacao decod = new Decodificacao(this.listaClocks.get(this.listaClocks.size() - 1).getBusca().getComandoBuscado());
                    clock.setDecodificacao(decod);
                    cellDecod.setLabel(Arrays.toString(clock.getDecodificacao().getExecucao()));
                    item.appendChild(cellDecod);
                } else {
                    cellDecod.setLabel("---");
                    item.appendChild(cellDecod);
                }
                if (this.temDecodificacao()) {
                    Execucao exec = new Execucao(this.listaClocks.get(this.listaClocks.size() - 1).getDecodificacao().getExecucao(), this.registradores);
                    clock.setExecucao(exec);
                    if (this.listaClocks.get(this.listaClocks.size() - 1).getDecodificacao().isJump()) {
                        this.pc = exec.setaPc(listaLabel, exec.getLabel());
                        this.jump = true;
                    }
                    cellExecu.setLabel(Arrays.toString(clock.getExecucao().getEscrever()));
                    item.appendChild(cellExecu);
                } else {
                    cellExecu.setLabel("---");
                    item.appendChild(cellExecu);
                }
                if (this.temExecucao()) {
                    Escrita escr = new Escrita(this.listaClocks.get(this.listaClocks.size() - 1).getExecucao().getEscrever(), this.registradores);
                    clock.setEscrita(escr);
                    cellEscri.setLabel(Arrays.toString(clock.getEscrita().getEscrita()));
                    item.appendChild(cellEscri);
                } else {
                    cellEscri.setLabel("---");
                    item.appendChild(cellEscri);
                }

                item.setParent(this.lbPipeline);

                this.labelNumClock.setValue(String.valueOf(this.numeroClock));
                this.numeroClock++;
                this.listaClocks.add(clock);

                this.atualizaMemoria();
                this.atualizaRegistradores();
            }
        } else {
            Messagebox.show("Por favor, carregue o arquivo primeiro!", "Erro ao avançar clock", Messagebox.OK, Messagebox.ERROR);
        }

    }

    public boolean temDependenciaDeDados() {
        if (this.temDecodificacao() && this.numeroClock > 3) {
            if (this.listaClocks.get(this.listaClocks.size() - 1).getDecodificacao() != null && this.listaClocks.get(this.listaClocks.size() - 1).getExecucao() != null) {
                String[] ex = this.listaClocks.get(this.listaClocks.size() - 1).getDecodificacao().getExecucao();
                String[] es = this.listaClocks.get(this.listaClocks.size() - 1).getExecucao().getEscrever();
                if (!ex[0].equals("j")) {
                    if ((ex[1].equals(es[1])) || (ex[2].equals(es[1])) || (ex[3].equals(es[1]))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean precisaDeDoisCiclos() {
        if (temExecucao()) {
            if (this.listaClocks.get(this.listaClocks.size() - 1).getExecucao().getComando().equals("add") && this.listaClocks.get(this.listaClocks.size() - 1).getExecucao().isPrimeiraVez()) {
                return true;
            } else if (this.listaClocks.get(this.listaClocks.size() - 1).getExecucao().getComando().equals("sub") && this.listaClocks.get(this.listaClocks.size() - 1).getExecucao().isPrimeiraVez()) {
                return true;
            }
        }
        return false;
    }

    public boolean temBusca() {
        if (this.listaClocks.get(this.listaClocks.size() - 1).getBusca() == null) {
            return false;
        }
        return true;
    }

    public boolean temBusca2() {
        if (this.listaClocks.get(this.listaClocks.size() - 2).getBusca() == null) {
            return false;
        }
        return true;
    }

    public boolean temBusca3() {
        if (this.listaClocks.get(this.listaClocks.size() - 3).getBusca() == null) {
            return false;
        }
        return true;
    }

    public boolean temDecodificacao() {
        if (this.listaClocks.get(this.listaClocks.size() - 1).getDecodificacao() == null) {
            return false;
        }
        return true;
    }

    public boolean temDecodificacao2() {
        if (this.listaClocks.get(this.listaClocks.size() - 2).getDecodificacao() == null) {
            return false;
        }
        return true;
    }

    public boolean temDecodificacao3() {
        if (this.listaClocks.get(this.listaClocks.size() - 3).getDecodificacao() == null) {
            return false;
        }
        return true;
    }

    public boolean temExecucao() {
        if (this.listaClocks.get(this.listaClocks.size() - 1).getExecucao() == null) {
            return false;
        }
        return true;
    }

    public boolean temExecucao2() {
        if (this.listaClocks.get(this.listaClocks.size() - 2).getExecucao() == null) {
            return false;
        }
        return true;
    }

    public boolean temExecucao3() {
        if (this.listaClocks.get(this.listaClocks.size() - 3).getExecucao() == null) {
            return false;
        }
        return true;
    }

    public boolean temEscrita() {
        if (this.listaClocks.get(this.listaClocks.size() - 1).getEscrita() == null) {
            return false;
        }
        return true;
    }

    public boolean temEscrita2() {
        if (this.listaClocks.get(this.listaClocks.size() - 2).getEscrita() == null) {
            return false;
        }
        return true;
    }

    public boolean temEscrita3() {
        if (this.listaClocks.get(this.listaClocks.size() - 3).getEscrita() == null) {
            return false;
        }
        return true;
    }

    public boolean temInstrucao() {
        int max = 0;
        for (Pair<Integer, String[]> pair : this.listaPc) {
            if (max < pair.getKey()) {
                max = pair.getKey();
            }
        }
        if (this.pc > max) {
            return false;
        } else {
            return true;
        }
    }

    public String[] getUltimaExecucao() {
        for (int i = this.listaClocks.size() - 1; i >= 0; i--) {
            if (this.listaClocks.get(i).getDecodificacao() != null) {
                return listaClocks.get(i).getDecodificacao().getExecucao();
            }
        }
        return null;
    }

    public Decodificacao getUltimaDeco() {
        for (int i = this.listaClocks.size() - 1; i >= 0; i--) {
            if (this.listaClocks.get(i).getDecodificacao() != null) {
                return listaClocks.get(i).getDecodificacao();
            }
        }
        return null;
    }

    public String[] getUltimaDecodificacao() {
        for (int i = this.listaClocks.size() - 1; i >= 0; i--) {
            if (this.listaClocks.get(i).getBusca() != null) {
                return listaClocks.get(i).getBusca().getComandoBuscado();
            }
        }
        return null;
    }

    public Window getWinMips() {
        return winMips;
    }

    public void setWinMips(Window winMips) {
        this.winMips = winMips;
    }

    public Listbox getLbMemoria() {
        return lbMemoria;
    }

    public void setLbMemoria(Listbox lbMemoria) {
        this.lbMemoria = lbMemoria;
    }

    public Listbox getLbRegistradores() {
        return lbRegistradores;
    }

    public void setLbRegistradores(Listbox lbRegistradores) {
        this.lbRegistradores = lbRegistradores;
    }

    public List<String[]> getLinhas() {
        return linhas;
    }

    public void setLinhas(List<String[]> linhas) {
        this.linhas = linhas;
    }

    public Listbox getLbPipeline() {
        return lbPipeline;
    }

    public void setLbPipeline(Listbox lbPipeline) {
        this.lbPipeline = lbPipeline;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public Label getLabelNumClock() {
        return labelNumClock;
    }

    public void setLabelNumClock(Label labelNumClock) {
        this.labelNumClock = labelNumClock;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public int getNumeroClock() {
        return numeroClock;
    }

    public void setNumeroClock(int numeroClock) {
        this.numeroClock = numeroClock;
    }

    public Label getLabelNomeArquivo() {
        return labelNomeArquivo;
    }

    public void setLabelNomeArquivo(Label labelNomeArquivo) {
        this.labelNomeArquivo = labelNomeArquivo;
    }

    public List<Integer> getRegistradores() {
        return registradores;
    }

    public void setRegistradores(List<Integer> registradores) {
        this.registradores = registradores;
    }

    public List<Clock> getListaClocks() {
        return listaClocks;
    }

    public void setListaClocks(List<Clock> listaClocks) {
        this.listaClocks = listaClocks;
    }

    public boolean isVaiRepetirEscrita() {
        return dependenciaDeDados;
    }

    public void setVaiRepetirEscrita(boolean vaiRepetirEscrita) {
        this.dependenciaDeDados = vaiRepetirEscrita;
    }

    public boolean isDependenciaDeDados() {
        return dependenciaDeDados;
    }

    public void setDependenciaDeDados(boolean dependenciaDeDados) {
        this.dependenciaDeDados = dependenciaDeDados;
    }

    public boolean isPrecisaDeDoisCiclos() {
        return precisaDeDoisCiclos;
    }

    public void setPrecisaDeDoisCiclos(boolean precisaDeDoisCiclos) {
        this.precisaDeDoisCiclos = precisaDeDoisCiclos;
    }

    public Listbox getLbMapaPc() {
        return lbMapaPc;
    }

    public void setLbMapaPc(Listbox lbMapaPc) {
        this.lbMapaPc = lbMapaPc;
    }

    public Label getLabelNumPc() {
        return labelNumPc;
    }

    public void setLabelNumPc(Label labelNumPc) {
        this.labelNumPc = labelNumPc;
    }

    public List<Pair<Integer, String[]>> getListaPc() {
        return listaPc;
    }

    public void setListaPc(List<Pair<Integer, String[]>> listaPc) {
        this.listaPc = listaPc;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public Window getWinAjuda() {
        return winAjuda;
    }

    public void setWinAjuda(Window winAjuda) {
        this.winAjuda = winAjuda;
    }

    public boolean isCasoEspecial() {
        return casoEspecial;
    }

    public void setCasoEspecial(boolean casoEspecial) {
        this.casoEspecial = casoEspecial;
    }

    public boolean isTerminouCasoEspecial() {
        return terminouCasoEspecial;
    }

    public void setTerminouCasoEspecial(boolean terminouCasoEspecial) {
        this.terminouCasoEspecial = terminouCasoEspecial;
    }

    public Listbox getLbMapaLabel() {
        return lbMapaLabel;
    }

    public void setLbMapaLabel(Listbox lbMapaLabel) {
        this.lbMapaLabel = lbMapaLabel;
    }

    public List<Pair<Integer, String>> getListaLabel() {
        return listaLabel;
    }

    public void setListaLabel(List<Pair<Integer, String>> listaLabel) {
        this.listaLabel = listaLabel;
    }

    public boolean isJump() {
        return jump;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public Textbox getArquivo() {
        return arquivo;
    }

    public void setArquivo(Textbox arquivo) {
        this.arquivo = arquivo;
    }

    public Button getAvancarClock() {
        return avancarClock;
    }

    public void setAvancarClock(Button avancarClock) {
        this.avancarClock = avancarClock;
    }

}
