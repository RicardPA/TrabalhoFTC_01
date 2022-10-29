import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/*
 * Classe: Estado
 * Descrição: Representa os estados presentes nos 
 * AFNs e AFDs utilizados nos métodos.
 */
class Estado {
    private String identificador;
    private String nome;
    private String x;
    private String y;
    private boolean inicio;
    private boolean fim;

    // Construtores do Estado
    public Estado() {
        this.identificador = "";
        this.nome = "";
        this.inicio = false;
        this.fim = false;
        this.x = "";
        this.y = "";
    }

    public Estado(String identificador, String nome) {
        this.identificador = identificador;
        this.nome = nome;
    }

    // Set e Get do Identificador
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getIdentificador() {
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

    // Get e Set do X
    public void setX(String x) {
        this.x = x;
    }

    public String getX() {
        return this.x;
    }

    // Get e Set do Y
    public void setY(String y) {
        this.y = y;
    }

    public String getY() {
        return this.y;
    }
}

/*
 * Classe: Transições
 * Descrição: Representa as transições presentes nos 
 * AFNs e AFDs utilizados nos métodos.
 */
class Transicao {
    private String identificadorOrigem;
    private String identificadorDestino;
    private String valorConsumido;

    // Construtores das Dimensões
    public Transicao() {
        this.identificadorOrigem = "";
        this.identificadorDestino = "";
        this.valorConsumido = "";
    }

    public Transicao(String identificadorOrigem, String identificadorDestino, String nome) {
        this.identificadorOrigem = identificadorOrigem;
        this.identificadorDestino = identificadorDestino;
        this.valorConsumido = nome;
    }

    // Get e Set do Identificador da Origem
    public void setIdentificadorOrigem(String identificadorOrigem) {
        this.identificadorOrigem = identificadorOrigem;
    }

    public String getIdentificadorOrigem() {
        return identificadorOrigem;
    }

    // Get e Set do Identificador de Destino
    public void setIdentificadorDestino(String identificadorDestino) {
        this.identificadorDestino = identificadorDestino;
    }

    public String getIdentificadorDestino() {
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

    public EntradaAFN () {}

    public void LerArquivoXML() {
        DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder construtor = fabrica.newDocumentBuilder();
            Document documento = (Document) construtor.parse(new File("./entradaAFN.jff"));

            documento.getDocumentElement().normalize();

            NodeList listaEstados = documento.getElementsByTagName("state");
            NodeList listaTransicoes = documento.getElementsByTagName("transition");
            
            // Listar Estados
            for (int i = 0; i < listaEstados.getLength(); i++) {
                Node item = listaEstados.item(i);

                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) item;

                    String identificador = elemento.getAttribute("id");
                    String x = elemento.getElementsByTagName("x").item(0).getTextContent();
                    String y = elemento.getElementsByTagName("y").item(0).getTextContent();
                    Boolean inicio = (elemento.getElementsByTagName("initial").item(0) != null)? true : false;
                    Boolean fim = (elemento.getElementsByTagName("final").item(0) != null)? true : false;

                    /*                
                        System.out.println("__________________________________");
                        System.out.println("Elemento :" + item.getNodeName());
                        System.out.println("Id : " + identificador);
                        System.out.println("x : " + x);
                        System.out.println("y : " + y);
                        System.out.println("inicio : " + inicio);
                        System.out.println("fim : " + fim);
                    */
                }                
            }

            // Listar Transiões 
            for (int i = 0; i < listaTransicoes.getLength(); i++) {
                Node item = listaTransicoes.item(i);

                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) item;

                    String identificadorOrigem = elemento.getElementsByTagName("from").item(0).getTextContent();
                    String identificadorDestino = elemento.getElementsByTagName("to").item(0).getTextContent();
                    String valorConsumido = elemento.getElementsByTagName("read").item(0).getTextContent();
                    
                    /*
                        System.out.println("__________________________________");
                        System.out.println("Elemento :" + item.getNodeName());
                        System.out.println("Id Origem : " + identificadorOrigem);
                        System.out.println("Id Destino : " + identificadorDestino);
                        System.out.println("Valor consumido : " + valorConsumido);
                    */
                }                
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("ERRO: Não foi possível ler o arquivo XML.");
        }
    }
}

/*
 * Classe: SaidaAFD
 * Descrição: Representa o AFD que será gerado pelo programa.
 */
class SaidaAFD {
    ArrayList<Estado> estados = new ArrayList<>();
    ArrayList<Transicao> transicoes = new ArrayList<>();

    public SaidaAFD () {}

    public void EscreverArquivoXML() throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();

        DocumentBuilder construtor = fabrica.newDocumentBuilder();
        Document documento = construtor.newDocument();

        Element elemento = documento.createElement("structure");
        Element tipo = documento.createElement("type");
        Element automato = documento.createElement("automaton");

        documento.appendChild(elemento);
        elemento.appendChild(tipo);
        elemento.appendChild(automato);

        tipo.setTextContent("fa");

        if (this.estados != null) {
            this.estados.forEach(
                estado -> {
                    Element state = documento.createElement("state");
                    Element x = documento.createElement("x");
                    Element y = documento.createElement("y");
                    Element inicio = documento.createElement("initial");
                    Element fim = documento.createElement("final");

                    automato.appendChild(state);
                    state.setAttribute("id", estado.getIdentificador());
                    state.setAttribute("name", estado.getNome());

                    state.appendChild(x);
                    x.setTextContent(estado.getX());

                    state.appendChild(y);
                    x.setTextContent(estado.getY());

                    if (estado.getInicio()) {
                        state.appendChild(inicio);
                    }

                    if (estado.getFim()) {
                        state.appendChild(fim);
                    }
                }
            );
        }

        if (this.transicoes != null) {
            this.transicoes.forEach(
                Transicao -> {
                    
                }
            );
        }

        try (FileOutputStream saida = new FileOutputStream(".\\test.jff")) {
            EscreverArquivoXML(documento, saida);
        } catch (IOException e) {
            System.out.println("ERRO: Não foi possível escrever o arquivo XML.");
        }
    }

    private void EscreverArquivoXML(Document documento, OutputStream saida) throws TransformerException {
        TransformerFactory fabricaTransformador = TransformerFactory.newInstance();
        Transformer transformador = fabricaTransformador.newTransformer();
        DOMSource fonte = new DOMSource(documento);
        StreamResult resultado = new StreamResult(saida);

        transformador.setOutputProperty(OutputKeys.INDENT, "yes");
        transformador.transform(fonte, resultado);
    }
}

public class Aplicacao {
    public static void main(String args[]) {
        /* 
            EntradaAFN automatoFinitoNaoDeterministico = new EntradaAFN();
            automatoFinitoNaoDeterministico.LerArquivoXML();
        */

        try {
            SaidaAFD automatoFinitoDeterministico = new SaidaAFD();
            automatoFinitoDeterministico.EscreverArquivoXML();   
        } catch (Exception e) {
            System.out.println("ERRO: Não foi possível escrever o arquivo XML.");
        }
    }
}