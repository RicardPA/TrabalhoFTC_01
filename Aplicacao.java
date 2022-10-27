import java.io.IOException;
import java.util.ArrayList;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/*
 * Classe: Estado
 * Descrição: Representa os estados presentes nos 
 * AFNs e AFDs utilizados nos métodos.
 */
class Estado {
    private int identificador;
    private String nome;
    private boolean inicio;
    private boolean fim;
    private int[] dimencao = new int[2];

    // Construtores do Estado
    public Estado() {
        this.identificador = -1;
        this.nome = "";
        this.inicio = false;
        this.fim = false;
        this.dimencao[0] = -1;
        this.dimencao[1] = -1;
    }

    public Estado(int identificador, String nome) {
        this.identificador = identificador;
        this.nome = nome;
    }

    // Set e Get do Identificador
    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public int getIdentificador() {
        return identificador;
    }

    // Set e Get do Nome
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    // Set e Get do Inicio
    public void setInicio(boolean inicio) {
        this.inicio = inicio;
    }

    public boolean getInicio() {
        return this.inicio;
    }

    // Set e Get do FIM
    public void setFim(boolean fim) {
        this.fim = fim;
    }

    public boolean getFim() {
        return this.fim;
    }

    // Get e Set das Dimensoes
    public void setDimencao(int[] dimencao) {
        this.dimencao = dimencao;
    }

    public int[] getDimencao() {
        return dimencao;
    }
}

/*
 * Classe: Transições
 * Descrição: Representa as transições presentes nos 
 * AFNs e AFDs utilizados nos métodos.
 */
class Transicao {
    private int identificadorOrigem;
    private int identificadorDestino;
    private String valorConsumido;

    // Construtores das Dimensões
    public Transicao() {
        this.identificadorOrigem = -1;
        this.identificadorDestino = -1;
        this.valorConsumido = "";
    }

    public Transicao(int identificadorOrigem, int identificadorDestino, String nome) {
        this.identificadorOrigem = identificadorOrigem;
        this.identificadorDestino = identificadorDestino;
        this.valorConsumido = nome;
    }

    // Get e Set do Identificador da Origem
    public void setIdentificadorOrigem(int identificadorOrigem) {
        this.identificadorOrigem = identificadorOrigem;
    }

    public int getIdentificadorOrigem() {
        return identificadorOrigem;
    }

    // Get e Set do Identificador de Destino
    public void setIdentificadorDestino(int identificadorDestino) {
        this.identificadorDestino = identificadorDestino;
    }

    public int getIdentificadorDestino() {
        return identificadorDestino;
    }

    // Get e Set do Valor Consumido
    public void setValorConsumido(String valorConsumido) {
        this.valorConsumido = valorConsumido;
    }

    public String getValorConsumido() {
        return valorConsumido;
    }
}

/*
 * Classe: EntradaAFN
 * Descrição: Representa o AFN lido pelo programa.
 */
class EntradaAFN {
    ArrayList<Estado> estados = new ArrayList<>();
    ArrayList<Transicao> transicoes = new ArrayList<>();
}

/*
 * Classe: SaidaAFD
 * Descrição: Representa o AFD que será gerado pelo programa.
 */
class SaidaAFD {
    ArrayList<Estado> estados = new ArrayList<>();
    ArrayList<Transicao> transicoes = new ArrayList<>();

    public void LerArquivoXML() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = (Document) builder.parse("./entradaAFN.jff");

            NodeList listaEstados = doc.getElementsByTagName();
            
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Aplicacao {
    public static void main(String args[]) {
        
    }
}