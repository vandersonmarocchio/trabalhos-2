/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Color;
import modelo.Cidade;
import modelo.ControladorCidades;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.NumberFormat;
import modelo.SimulatedAnnealing;
import modelo.Viagem;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 *
 * @author diogo
 */
public class MoaController extends Window {

    private Window winMoa;
    private Label entrada;
    private Label distanciaInicial;
    private Label distanciaFinal;
    private Label saida;
    private Image img;
    private Image img2;
    private Viagem solucao;
    private int indiceAtual;
    private ControladorCidades controlador;

    public void onCreate() throws IOException {
        this.setWinMoa((Window) getFellow("winMoa"));
        this.setEntrada((Label) getFellow("entrada"));
        this.setDistanciaInicial((Label) getFellow("distanciaInicial"));
        this.setDistanciaFinal((Label) getFellow("distanciaFinal"));
        this.setSaida((Label) getFellow("saida"));
        this.setImg((Image) getFellow("img"));
        this.setImg2((Image) getFellow("img2"));

        this.reset();
        this.entrada.setValue("Cidades geradas: " + controlador.printa());

        this.solucao = simulatedAnnealing();

        this.saida.setValue("Caminho: " + this.solucao.toString());
        
        indiceAtual = 0;
    }

    public void reset() {

        controlador = new ControladorCidades();
        for (int i = 0; i < 5; i++) {
            Cidade cidade = new Cidade();
            controlador.adicionarCidade(cidade);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();

        XYSeries series1 = new XYSeries("Cidades");
        for (Cidade cidade : controlador.getCidadesDestino()) {
            series1.add(cidade.getX(), cidade.getY());
        }

        dataset.addSeries(series1);

        JFreeChart chart = ChartFactory.createScatterPlot("Cidades geradas", "y", "x", dataset, PlotOrientation.VERTICAL, true, true, false);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);

        XYItemRenderer r = plot.getRenderer();
        plot.setRenderer(r);
        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        domain.setRange(0.00, 250.00);
        domain.setTickUnit(new NumberTickUnit(25));
        domain.setVerticalTickLabels(false);
        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        range.setRange(0.00, 250.00);
        range.setTickUnit(new NumberTickUnit(25));
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(2); // etc.
//        XYItemLabelGenerator generator = new StandardXYItemLabelGenerator("{1}:{2}", format, format);
//        r.setBaseItemLabelGenerator(generator);
//        r.setBaseItemLabelsVisible(true);
        int width = 440;
        /* Width of the image */
        int height = 280;
        /* Height of the image */

        BufferedImage bufferedImage = chart.createBufferedImage(width, height);
        img.setContent(bufferedImage);
    }

    public Viagem simulatedAnnealing() {
        // Seta a temperatura inicial
        double temp = 10000;

        // Taxa de resfriamento
        double coolingRate = 0.003;

        // Inicializa a solução inicial
        Viagem solucaoInicial = new Viagem(this.controlador);
        solucaoInicial.geradorRandom(this.controlador);

        System.out.println("Distancia da solução inicial: " + solucaoInicial.getDistancia());
        this.distanciaInicial.setValue("Distancia da solução inicial: " + solucaoInicial.getDistancia());

        // Seta a solução inicial como a melhor
        Viagem melhorViagem = new Viagem(solucaoInicial.getViagens());

        // Loop até o sistema ser resfriado
        while (temp > 1) {
            // Cria um novo vizinho
            Viagem novaSolucao = new Viagem(solucaoInicial.getViagens());

            // Pega posições randomicas
            int pos1 = (int) (novaSolucao.getViagens().size() * Math.random());
            int pos2 = (int) (novaSolucao.getViagens().size() * Math.random());

            // Pega as cidades dessas posições
            Cidade cidadeSwap1 = novaSolucao.getCidade(pos1);
            Cidade cidadeSwap2 = novaSolucao.getCidade(pos2);

            // Troca elas
            novaSolucao.setCidade(pos2, cidadeSwap1);
            novaSolucao.setCidade(pos1, cidadeSwap2);

            // Pega a energia da solução
            int currentEnergy = solucaoInicial.getDistancia();
            int neighbourEnergy = novaSolucao.getDistancia();

            // Decide se aceita essa solução
            if (SimulatedAnnealing.atualizacaoProbabilidade(currentEnergy, neighbourEnergy, temp) > Math.random()) {
                solucaoInicial = new Viagem(novaSolucao.getViagens());
            }

            // Mantêm a melhor solução encontrada
            if (solucaoInicial.getDistancia() < melhorViagem.getDistancia()) {
                melhorViagem = new Viagem(solucaoInicial.getViagens());
            }

            // Resfria o sistema
            temp *= 1 - coolingRate;
        }

        System.out.println("Distancia da solução final: " + melhorViagem.getDistancia());
        this.distanciaFinal.setValue("Distancia da solução final: " + melhorViagem.getDistancia());
        return melhorViagem;
    }

    public void avancar() {

        if (indiceAtual <= solucao.getViagens().size()) {
//
            XYSeriesCollection dataset2 = new XYSeriesCollection();
            XYSeries series2 = new XYSeries(String.valueOf("Cidades"));

            for (int i = 0; i < indiceAtual; i++) {
                series2.add(solucao.getViagens().get(i).getX(), solucao.getViagens().get(i).getY());

            }
            dataset2.removeSeries(series2);
            dataset2.addSeries(series2);

            indiceAtual++;

//            dataset2.removeSeries(series2);
            JFreeChart chart = ChartFactory.createScatterPlot("Caminho gerado", "y", "x", dataset2, PlotOrientation.VERTICAL, true, true, true);

            XYPlot plot = (XYPlot) chart.getPlot();
            plot.setBackgroundPaint(Color.WHITE);
            plot.setDomainGridlinesVisible(true);
            plot.setDomainGridlinePaint(Color.lightGray);
            plot.setRangeGridlinePaint(Color.lightGray);

            XYItemRenderer r = plot.getRenderer();
            plot.setRenderer(r);
            NumberAxis domain = (NumberAxis) plot.getDomainAxis();
            domain.setRange(0.00, 250.00);
            domain.setTickUnit(new NumberTickUnit(25));
            domain.setVerticalTickLabels(false);
            NumberAxis range = (NumberAxis) plot.getRangeAxis();
            range.setRange(0.00, 250.00);
            range.setTickUnit(new NumberTickUnit(25));
            NumberFormat format = NumberFormat.getNumberInstance();
            format.setMaximumFractionDigits(2); // etc.
//            XYItemLabelGenerator generator = new StandardXYItemLabelGenerator("{1}:{2}", format, format);
//            r.setBaseItemLabelGenerator(generator);
//            r.setBaseItemLabelsVisible(true);

//            XYSeries series2 = new XYSeries("Cidades");
//            XYSeriesCollection dataset2 = new XYSeriesCollection();
//            for (int i = 0; i < indiceAtual; i++) {
//                series2.add(solucao.getViagens().get(i).getX(), solucao.getViagens().get(i).getY());
//            }
//
//            indiceAtual++;
//            dataset2.removeSeries(series2);
//
//            dataset2.addSeries(series2);
//
//            JFreeChart xylineChart = ChartFactory.createXYLineChart(
//                    "Caminho percorrido",
//                    "Y",
//                    "X",
//                    dataset2,
//                    PlotOrientation.HORIZONTAL,
//                    true, true, false);
//
//            XYPlot plot = xylineChart.getXYPlot();
//            XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
//            plot.setRenderer(renderer);
//            NumberAxis domain = (NumberAxis) plot.getDomainAxis();
//            domain.setRange(0.00, 210.00);
//            domain.setTickUnit(new NumberTickUnit(25));
//            domain.setVerticalTickLabels(false);
//            NumberAxis range = (NumberAxis) plot.getRangeAxis();
//            range.setRange(0.00, 210.00);
//            range.setTickUnit(new NumberTickUnit(25));
//
//            NumberFormat format = NumberFormat.getNumberInstance();
//            format.setMaximumFractionDigits(2); // etc.
//            XYItemLabelGenerator generator = new StandardXYItemLabelGenerator(String.valueOf(indiceAtual), format, format);
//            renderer.setBaseItemLabelGenerator(generator);
//            renderer.setBaseItemLabelsVisible(true);
            int width = 440;
            /* Width of the image */
            int height = 280;
            /* Height of the image */

            BufferedImage bufferedImage = chart.createBufferedImage(width, height);
            img2.setContent(bufferedImage);
        } else {
            Messagebox.show("Acabou", "Erro ao carregar arquivo", Messagebox.OK, Messagebox.INFORMATION);

        }
    }

    public Window getWinMoa() {
        return winMoa;
    }

    public void setWinMoa(Window winMoa) {
        this.winMoa = winMoa;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public Image getImg2() {
        return img2;
    }

    public void setImg2(Image img2) {
        this.img2 = img2;
    }

    public Label getEntrada() {
        return entrada;
    }

    public void setEntrada(Label entrada) {
        this.entrada = entrada;
    }

    public Label getSaida() {
        return saida;
    }

    public void setSaida(Label saida) {
        this.saida = saida;
    }

    public Viagem getSolucao() {
        return solucao;
    }

    public void setSolucao(Viagem solucao) {
        this.solucao = solucao;
    }

    public int getIndiceAtual() {
        return indiceAtual;
    }

    public void setIndiceAtual(int indiceAtual) {
        this.indiceAtual = indiceAtual;
    }

    public Label getDistanciaInicial() {
        return distanciaInicial;
    }

    public void setDistanciaInicial(Label distanciaInicial) {
        this.distanciaInicial = distanciaInicial;
    }

    public Label getDistanciaFinal() {
        return distanciaFinal;
    }

    public void setDistanciaFinal(Label distanciaFinal) {
        this.distanciaFinal = distanciaFinal;
    }

}
