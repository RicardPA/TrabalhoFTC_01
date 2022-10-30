import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;

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
    ArrayList<Transicao> transicoes = new ArrayList<>();

    // Construtores do Estado
    public Estado() {
        this.identificador = "";
        this.nome = "";
        this.inicio = false;
        this.fim = false;
        this.x = "";
        this.y = "";
    }

    public Estado(String identificador, String nome, String x, String y, boolean inicio, boolean fim) {
        this.identificador = identificador;
        this.nome = nome;
        this.x = x;
        this.y = y;
        this.inicio = inicio;
        this.fim = fim;
    }

    public Estado(Estado estado) {
        this.identificador = estado.getIdentificador();
        this.nome = estado.getNome();
        this.x = estado.getX();
        this.y = estado.getY();
        this.inicio = estado.getInicio();
        this.fim = estado.getFim();
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

    public static Estado procurarEstado(ArrayList<Estado> estados, String nome) {
        Estado estado = new Estado();
  
        for (int i = 0; i < estados.size(); i++) {
            if (estados.get(i).getNome().equals(nome)) {
                estado = estados.get(i);
            }
        }
  
        return (estado);
    }
}

/*
 * Classe: EstadoTemp
 * Descrição: Estado temporario usado para conversao
 */
class EstadoTemp {
    ArrayList<Estado> destinos = new ArrayList<>();
    Estado origem = new Estado();

    public EstadoTemp (ArrayList<Estado> estados) {
        this.destinos = estados;
    }

    public Estado converterEstado(ArrayList<Estado> estados, ArrayList<Estado> estadosGlob) {
        Estado resposta = new Estado();
        String nome = new String();
        String x = new String();
        String y = new String();

        ArrayList<Estado> destinosOrdenados = new ArrayList<>();
        destinosOrdenados.addAll(this.destinos);

        for (int i = 0; i < destinosOrdenados.size(); i++) {
            for (int j = 0; j < destinosOrdenados.size(); j++) {
                if (Integer.parseInt(destinosOrdenados.get(j).getIdentificador()) < Integer.parseInt(destinosOrdenados.get(i).getIdentificador())) {
                    Estado aux = destinosOrdenados.get(i);
                    destinosOrdenados.set(i, destinosOrdenados.get(j));
                    destinosOrdenados.set(j, aux);
                }
            }
        }

        this.destinos = destinosOrdenados;

        for (int i = 0; i < this.destinos.size(); i++) {
            Estado atualAFN = Estado.procurarEstado(estados, this.destinos.get(i).getNome());
            atualAFN = (Integer.parseInt(atualAFN.getIdentificador()) < 0)? this.destinos.get(i): atualAFN; 
            if(atualAFN.getInicio()){
                resposta.setInicio(true);;
            }
            
            nome += "|" + atualAFN.getNome();
            x += atualAFN.getX();
            y += atualAFN.getY();

            for (int j = 0; j < atualAFN.transicoes.size(); j++) {
                Transicao nova = new Transicao(atualAFN.transicoes.get(i).getIdentificadorOrigem(), atualAFN.transicoes.get(i).getIdentificadorDestino(), atualAFN.transicoes.get(i).getValorConsumido());
                atualAFN.transicoes.add(nova);
            }
        }

        String novoIdentificador = (estados.size() + estadosGlob.size() + "");
        resposta.setInicio(false);
        resposta.setNome(nome);
        resposta.setIdentificador(novoIdentificador);
        resposta.setX(x);
        resposta.setY(y);
        
        this.origem = resposta;

        return resposta;
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

    public Transicao(String identificadorOrigem, String identificadorDestino, String valorConsumido) {
        this.identificadorOrigem = identificadorOrigem;
        this.identificadorDestino = identificadorDestino;
        this.valorConsumido = valorConsumido;
    }

    public Transicao(Transicao transicao) {
        this.identificadorOrigem = transicao.getIdentificadorOrigem();
        this.identificadorDestino = transicao.getIdentificadorDestino();
        this.valorConsumido = transicao.getValorConsumido();
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

    public static Estado unirTransicoes (Estado origem, ArrayList<Transicao> transicoes, ArrayList<Estado> estadosBase, ArrayList<Estado> estadoGlob) {
        ArrayList<String> simbolos = new ArrayList<>();
        ArrayList<Estado> estadosDestino = new ArrayList<>();
        ArrayList<Transicao> transicaoDestino = new ArrayList<>();

        for (int i = 0; i < transicoes.size(); i++) {
            boolean existe = false;
            for (int j = 0; j < simbolos.size(); j++) {
                if (simbolos.get(j).compareTo(transicoes.get(i).getValorConsumido()) == 0) {
                    existe = true;
                }
            }

            if (!existe) {
                simbolos.add(transicoes.get(i).valorConsumido);
            }
        }

        for (int i = 0; i < simbolos.size(); i++) {
            for (int j = 0; j < transicoes.size(); j++) {
                if (transicoes.get(j).valorConsumido.compareTo(simbolos.get(i)) == 0) {
                    for (int k = 0; k < estadosBase.size(); k++) {
                        if (estadosBase.get(k).getIdentificador().compareTo(transicoes.get(j).getIdentificadorDestino()) == 0) {
                            boolean existe = false;
                            for (int l = 0; l < estadosDestino.size(); l++) {
                                if (estadosDestino.get(l).getIdentificador().compareTo(estadosBase.get(k).getIdentificador()) == 0) {
                                    existe = true;
                                }
                            }

                            if (!existe) {
                                estadosDestino.add(new Estado(estadosBase.get(k)));
                                transicaoDestino.add(new Transicao(transicoes.get(j)));
                            }
                        }
                    }
                }
            }

            if (estadosDestino.size() > 0) {
                boolean trasicaoExiste = false;
                Transicao novaTransicao = new Transicao();
                
                if (estadosDestino.size() == 1) {
                    boolean existe = false;
                    novaTransicao = new Transicao(origem.getIdentificador(), estadosDestino.get(0).getIdentificador(), simbolos.get(i));
                    
                    for (int l = 0; l < estadoGlob.size(); l++) {
                        if (estadoGlob.get(l).getIdentificador().compareTo(estadosDestino.get(0).getIdentificador()) == 0) {
                            existe = true;
                        }
                    }
    
                    if (!existe) {
                        estadoGlob.add(new Estado(estadosDestino.get(0)));
                    }
                } else {
                    boolean existe = false;
                    EstadoTemp temp = new EstadoTemp(estadosDestino);
                    Estado novo = temp.converterEstado(estadosBase, estadoGlob);
                    
                    for (int l = 0; l < estadoGlob.size(); l++) {
                        if (estadoGlob.get(l).getIdentificador().compareTo(novo.getIdentificador()) == 0) {
                            existe = true;
                        }
                    }

                    if(!existe) {
                        estadoGlob.add(novo);
                        novo = Transicao.unirTransicoes(novo, novo.transicoes, estadosBase, estadoGlob);
                    } else {
                        novo = Estado.procurarEstado(estadoGlob, novo.getNome());
                    }

                    novaTransicao = new Transicao(origem.getIdentificador(), novo.getIdentificador(), simbolos.get(i));
                }

                for (int j = 0; j < origem.transicoes.size(); j++) {
                    if (origem.transicoes.get(j).getIdentificadorOrigem().compareTo(novaTransicao.getIdentificadorOrigem()) == 0 &&
                        origem.transicoes.get(j).getIdentificadorDestino().compareTo(novaTransicao.getIdentificadorDestino()) == 0 &&
                        origem.transicoes.get(j).getValorConsumido().compareTo(novaTransicao.getValorConsumido()) == 0) {
                        trasicaoExiste = true;
                    }
                }

                if (!trasicaoExiste) {
                    origem.transicoes = Transicao.removeTransicaoComSimbolo(origem.transicoes, simbolos.get(i));
                    origem.transicoes.add(novaTransicao);
                }
            } 
        }

        return origem;
    }

    public static ArrayList<Transicao> removeTransicaoComSimbolo (ArrayList<Transicao> transicoes, String simbolo)  {
        ArrayList<Transicao> resposta = new ArrayList<>(transicoes);

        for (int i = resposta.size()-1; i >=0; i--) {
            if(resposta.get(i).getValorConsumido().compareTo(simbolo) == 0) {
                resposta.remove(i);
            }
        }

        return resposta;
    }
}

/*
 * Classe: AFN
 * Descrição: Representa o AFN lido pelo programa.
 */
class AFN {
    ArrayList<Estado> estados = new ArrayList<>();
    ArrayList<Transicao> transicoes = new ArrayList<>();

    public AFN() {
        DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder construtor = fabrica.newDocumentBuilder();
            Document documento = (Document) construtor.parse(new File("./AFN.jff"));

            documento.getDocumentElement().normalize();

            NodeList listaEstados = documento.getElementsByTagName("state");
            NodeList listaTransicoes = documento.getElementsByTagName("transition");
            
            // Listar Estados
            for (int i = 0; i < listaEstados.getLength(); i++) {
                Node item = listaEstados.item(i);

                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) item;

                    String identificador = elemento.getAttribute("id");
                    String nome = elemento.getAttribute("name");
                    String x = elemento.getElementsByTagName("x").item(0).getTextContent();
                    String y = elemento.getElementsByTagName("y").item(0).getTextContent();
                    Boolean inicio = (elemento.getElementsByTagName("initial").item(0) != null)? true : false;
                    Boolean fim = (elemento.getElementsByTagName("final").item(0) != null)? true : false;

                    this.estados.add(new Estado(identificador, nome, x, y, inicio, fim));
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
                    
                    this.transicoes.add(new Transicao(identificadorOrigem, identificadorDestino, valorConsumido));
                }                
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("ERRO: Não foi possível ler o arquivo XML.");
        }
    }

    public void printEstados () {
        if (this.estados != null) {
            System.out.println("\t - - - ESTADOS - - -");
            this.estados.forEach(
                estadoList -> {
                    System.out.println("Identificador: " + estadoList.getIdentificador());
                    System.out.println("Inicio: " + estadoList.getInicio());
                    System.out.println("Fim: " + estadoList.getFim());
                    System.out.println("\t - - - - - -");
                }
            );
        }
    }

    public void printTransicoes () {
        if (this.transicoes != null) {
            System.out.println("\t - - - TRANSICOES - - -");
            this.transicoes.forEach(
                transicaoList -> {
                    System.out.println("Identificador de Origem: " + transicaoList.getIdentificadorOrigem());
                    System.out.println("Identificador de Destino: " + transicaoList.getIdentificadorDestino());
                    System.out.println("Valor Consumido: " + transicaoList.getValorConsumido());
                    System.out.println("\t - - - - - -");
                }
            );
        }
    }

    public void toAFNString() {
        printEstados();
        System.out.println("\n.\n.\n");
        printTransicoes();
    }
}

/*
 * Classe: AFD
 * Descrição: Representa o AFD que será gerado pelo programa.
 */
class AFD {
    ArrayList<Estado> estados = new ArrayList<>();
    ArrayList<Transicao> transicoes = new ArrayList<>();

    public void obterAFD() {
        DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder construtor = fabrica.newDocumentBuilder();
            Document documento = (Document) construtor.parse(new File("./AFD.jff"));

            documento.getDocumentElement().normalize();

            NodeList listaEstados = documento.getElementsByTagName("state");
            NodeList listaTransicoes = documento.getElementsByTagName("transition");
            
            // Listar Estados
            for (int i = 0; i < listaEstados.getLength(); i++) {
                Node item = listaEstados.item(i);

                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) item;

                    String identificador = elemento.getAttribute("id");
                    String nome = elemento.getAttribute("name");
                    String x = elemento.getElementsByTagName("x").item(0).getTextContent();
                    String y = elemento.getElementsByTagName("y").item(0).getTextContent();
                    Boolean inicio = (elemento.getElementsByTagName("initial").item(0) != null)? true : false;
                    Boolean fim = (elemento.getElementsByTagName("final").item(0) != null)? true : false;

                    this.estados.add(new Estado(identificador, nome, x, y, inicio, fim));
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
                    
                    this.transicoes.add(new Transicao(identificadorOrigem, identificadorDestino, valorConsumido));
                }                
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("ERRO: Não foi possível ler o arquivo XML.");
        }
    }

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
                estadoList -> {
                    Element estado = documento.createElement("state");
                    Element x = documento.createElement("x");
                    Element y = documento.createElement("y");
                    Element inicio = documento.createElement("initial");
                    Element fim = documento.createElement("final");

                    automato.appendChild(estado);
                    estado.setAttribute("id", estadoList.getIdentificador());
                    estado.setAttribute("name", estadoList.getNome());

                    estado.appendChild(x);
                    x.setTextContent(estadoList.getX());

                    estado.appendChild(y);
                    y.setTextContent(estadoList.getY());

                    if (estadoList.getInicio()) {
                        estado.appendChild(inicio);
                    }

                    if (estadoList.getFim()) {
                        estado.appendChild(fim);
                    }
                }
            );
        }

        if (this.transicoes != null) {
            this.transicoes.forEach(
                transicaoList -> {
                    Element transicao = documento.createElement("transition");
                    Element identificadorOrigem = documento.createElement("from");
                    Element identificadorDestino = documento.createElement("to");
                    Element valorConsumido = documento.createElement("read");

                    automato.appendChild(transicao);

                    transicao.appendChild(identificadorOrigem);
                    identificadorOrigem.setTextContent(transicaoList.getIdentificadorOrigem());

                    transicao.appendChild(identificadorDestino);
                    identificadorDestino.setTextContent(transicaoList.getIdentificadorDestino());

                    transicao.appendChild(valorConsumido);
                    valorConsumido.setTextContent(transicaoList.getValorConsumido());
                }
            );
        }

        try (FileOutputStream saida = new FileOutputStream(".\\AFD.jff")) {
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

    public void printEstados () {
        if (this.estados != null) {
            System.out.println("\t - - - ESTADOS - - -");
            this.estados.forEach(
                estadoList -> {
                    System.out.println("Identificador: " + estadoList.getIdentificador());
                    System.out.println("Inicio: " + estadoList.getInicio());
                    System.out.println("Fim: " + estadoList.getFim());
                    System.out.println("\t - - - - - -");
                }
            );
        }
    }

    public void printTransicoes () {
        if (this.transicoes != null) {
            System.out.println("\t - - - TRANSICOES - - -");
            this.transicoes.forEach(
                transicaoList -> {
                    System.out.println("Identificador de Origem: " + transicaoList.getIdentificadorOrigem());
                    System.out.println("Identificador de Destino: " + transicaoList.getIdentificadorDestino());
                    System.out.println("Valor Consumido: " + transicaoList.getValorConsumido());
                    System.out.println("\t - - - - - -");
                }
            );
        }
    }

    public void toAFDString() {
        printEstados();
        System.out.println("\n.\n.\n");
        printTransicoes();
    }

    public boolean verificaAFD(String palavra) {
        Estado estadoAtual = new Estado();

        for (int i = 0; i < this.estados.size(); i++) {
            if (this.estados.get(i).getInicio()) {
                estadoAtual = this.estados.get(i);
            }
        }

        for (int i = 0; i < palavra.length(); i++) {
            String valor = palavra.charAt(i) + "";
            boolean existe = false; 
            boolean mudou = false;

            for (int j = 0; j < this.transicoes.size() && !mudou; j++) {
                if (this.transicoes.get(j).getIdentificadorOrigem().compareTo(estadoAtual.getIdentificador()) == 0 &&
                    this.transicoes.get(j).getValorConsumido().compareTo(valor) == 0) {

                    existe = true;
                    
                    for (int k = 0; k < this.estados.size(); k++) {
                        if (this.transicoes.get(j).getIdentificadorDestino().compareTo(this.estados.get(k).getIdentificador()) == 0) {
                            mudou = true;
                            estadoAtual = this.estados.get(k);
                        }
                    }
                }
            }

            if (!existe) {
                return(existe);
            }
        }

        return estadoAtual.getFim();
    }
}

public class Aplicacao {
    public static ArrayList<Estado> converterAFN (AFN afn) throws Exception {
        ArrayList<Estado> estadosGlobTotal = new ArrayList<>();
        ArrayList<Estado> estadosGlobTotalSemTransicao = new ArrayList<>();
        ArrayList<Estado> estadosGlob = new ArrayList<>();
        AFD afd = new AFD();

        try {
            estadosGlobTotalSemTransicao = new ArrayList<>(afn.estados);
        } catch (Exception e) {
            e.printStackTrace ();
        }

        afn.estados.forEach(
            estado -> {
                afn.transicoes.forEach(
                    transicao -> {
                        if (transicao.getIdentificadorOrigem() == estado.getIdentificador()) {
                            estado.transicoes.add(transicao);
                        }
                    }
                );
            }
        );

        try {
            estadosGlobTotal = new ArrayList<>(afn.estados);
        } catch (Exception e) {
            e.printStackTrace ();
        }

        estadosGlobTotal.forEach(
            estado -> {
                if (estado.getInicio()) {
                    estadosGlob.add(estado);
                }
            }
        );

        for (int i = 0; i < estadosGlob.size(); i++) {
            boolean existe = false;
            Estado atual = Estado.procurarEstado(estadosGlobTotal, estadosGlob.get(i).getNome());
            Estado novo = Transicao.unirTransicoes(estadosGlob.get(i), atual.transicoes, estadosGlobTotalSemTransicao, estadosGlob);

            for (int j = 0; j < estadosGlob.size(); j++) {
                if (novo.getIdentificador().compareTo(estadosGlob.get(j).getIdentificador()) == 0) {
                    existe = true;
                }
            }

            if(!existe) {
                estadosGlob.add(novo);
            }
        }

        return estadosGlob;
    }

    public static boolean verificarAFN (AFN afn) {
        boolean teste = false;
        if (afn.estados != null && afn.transicoes != null) {
            for (int i = 0; i < afn.estados.size(); i++) {
                ArrayList<String> valores = new ArrayList<>();
                for (int j = 0; j < afn.transicoes.size(); j++) {
                    if (afn.transicoes.get(j).getIdentificadorOrigem().compareTo(afn.estados.get(i).getIdentificador()) == 0) {
                        if (valores != null) {
                            for (int k = 0; k < valores.size(); k++) {
                                if (valores.get(k).compareTo(afn.transicoes.get(j).getValorConsumido()) == 0) {
                                    teste = true;
                                }
                            };

                            if (!teste) {
                                valores.add(afn.transicoes.get(j).getValorConsumido());
                            }
                        }
                    }
                }
            }
        }

        return(teste);
    }

    public static void limparTerminal () throws Exception {
        if (System.getProperty("os.name").contains("Windows")) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } else {
            Runtime.getRuntime().exec("clear");
        }
    }

    public static void main(String args[]) throws Exception {
        AFN afn = new AFN();
        AFD afd = new AFD();
        Scanner scan = new Scanner(System.in);
        int opcao = -1;
        int opcaoVerificacao = -1;
        String palavra = "";
        
        limparTerminal();

        while (opcao != 0) {
            System.out.println("Trabalho de FTC - Ciencia da Computacao - 2022");
            System.out.println("Autores: Ricardo Portilho de Andrade | Hugo Souza Almeida");
            System.out.println("Opcao 0 - Fechar programa.");
            System.out.println("Opcao 1 - Transformar AFN em um AFD.");
            System.out.println("Opcao 2 - Testar se o AFN e AFD aceitam a mesma linguagem.");
            System.out.print("Digite o numero da opcao: ");
            opcao = scan.nextInt();
         
            if (opcao == 1) { 
                if (verificarAFN(afn)) {
                    try {
                        afd.EscreverArquivoXML();   
                    } catch (Exception e) {
                        System.out.println("ERRO: Não foi possível escrever o arquivo XML.");
                    }
                } else {
                    System.out.println("\n\tERRO: O automoto passado nao e um AFN.\n");
                }
            } else if (opcao == 2) {
                limparTerminal();
                afd.obterAFD();
                System.out.println("\t- - - VERIFICAR PALAVRA - - -");
                while (opcaoVerificacao != 0) {
                    scan.nextLine();
                    System.out.print("\nColoque a palavra: ");
                    palavra = scan.nextLine();

                    String resultado = afd.verificaAFD(palavra)? "Aceita" : "Recusa";

                    System.out.println("Resultado teste: " + resultado);
                    System.out.println("\nOpcao 0 - Parar testes.");
                    System.out.println("Opcao 1 - Cotinuar com testes.");
                    System.out.print("Digite o numero da opcao: ");
                    opcaoVerificacao = scan.nextInt();
                }
                opcaoVerificacao = -1;
            } else if (opcao != 0) {
                System.out.println("\n\tERRO: Opcao invalida\n");
            }

            if (opcao != 0) {
                System.out.print("\nPrecione Entrer para continuar... ");
                System.in.read();
            }

            limparTerminal();
        }
    }
}