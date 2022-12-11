import java.io.File;
import java.security.InvalidParameterException;
import java.util.Scanner;

public class app {
    static Scanner teclado = new Scanner(System.in);
    static String arquivoVeiculo = "/media/juvito/Data/Backup_lpm/projetos-3-4-5-grupo-1-novo-1/projetos-3-4-5-grupo-1-novo-1/codigo/Veiculos_ler.txt";
    static String arquivoRota = "/media/juvito/Data/Backup_lpm/projetos-3-4-5-grupo-1-novo-1/projetos-3-4-5-grupo-1-novo-1/codigo/Rotas_ler.txt";
    //static String arquivoVeiculo = "C:/Users/Mauricio/Documents/GitHub/projetos-3-4-5-grupo-1-novo-1/codigo/Veiculos_ler.txt";
    //static String arquivoRota = "C:/Users/Mauricio/Documents/GitHub/projetos-3-4-5-grupo-1-novo-1/codigo/Rotas_ler.txt";
    static FabricaCarro carro = new FabricaCarro();
    static FabricaVan van = new FabricaVan();
    static FabricaFurgao furgao = new FabricaFurgao();
    static FabricaCaminhao caminhao = new FabricaCaminhao();
    static ColecaoFabrica todasAsFabricas;
    static Frota frota = new Frota();


    public static void main(String[] args) throws Exception {
    
        Veiculo veiculoParaRota = null;
        String placaVeiculo;
        Rota novaRota = null;
        double kmRota = 0;
        Data dtProducao = null;
        configurarMenuReflexao();
        frota.carregarVeiculosArquivo(arquivoVeiculo);
        frota.carregarRotasArquivo(arquivoRota);

        String opcao = "";
        opcao = menu(opcao);
        
        while (opcao != "F") {
            switch (opcao) {
                case "1":
                    System.out.println(listarDadosArquivos());
                    opcao = menu(opcao);
                    break;

                case "2":
                    frota.adicionarVeiculos(criarVeiculo());
                    frota.salvaVeiculosFrota(arquivoVeiculo);
                    opcao = menu(opcao);
                    break;

                case "3":
                    System.out.println("Entre com os dados da rota para incluir:");
                    System.out.println("\nEntre com data para a rota formato DD/MM/AAAA: ");
                    dtProducao = Data.verificaData(teclado.nextLine());
                    System.out.print("\nQuilometros da rota:");
                    kmRota = teclado.nextDouble();clearBuffer(teclado);
                    System.out.println("Veíulos abastecidos com autonomia para a rota de:" + kmRota + "Km.");
                    if (frota.imprimeVeiculosparaRota(kmRota) > 0) {
                        System.out.println("\nEntre com a placa do veículo para a rota:\n Procurar pela placa: ");
                        veiculoParaRota = frota.retornaVeiculoPelaPlaca(teclado.nextLine().toUpperCase());
                        novaRota = new Rota(kmRota, dtProducao, veiculoParaRota);
                        statusRotaAdicionada(frota.addRota(novaRota, veiculoParaRota));
                    }
                    frota.salvaRotasFrota(arquivoRota);
                    opcao = menu(opcao);
                    break;

                case "4":
                    System.out.println("\nEntre com a placa do Veículo para adicionar o custo extra:");
                    veiculoParaRota = frota.retornaVeiculoPelaPlaca(teclado.nextLine().toUpperCase());
                    System.out.printf("\nInforme o valor do custo extra: R$");
                    veiculoParaRota.addCustosExtra(teclado.nextDouble());
                    veiculoParaRota.calculaCustoVariavel();
                    System.out.println("O total de custos extras atual é: R$" + veiculoParaRota.custosExtra);
                    opcao = menu(opcao);
                    break;

                case "5":
                    System.out.println("\nEntre com a placa do Veículo para localizar:");
                    veiculoParaRota = frota.retornaVeiculoPelaPlaca(teclado.nextLine().toUpperCase());
                    System.out.println(veiculoParaRota);
                    opcao = menu(opcao);
                    break;

                case "6":
                    System.out.println("\nEntre com a placa do Veículo para imprimir gastos:");
                    placaVeiculo = teclado.nextLine().toUpperCase();
                    if (frota.retornaVeiculoPelaPlaca(placaVeiculo) != null) {
                        veiculoParaRota = frota.retornaVeiculoPelaPlaca(placaVeiculo);
                        System.out.println(veiculoParaRota.gastosVeiculo());
                    }
                    opcao = menu(opcao);
                    break;

                case "7":
                    System.out.println(frota.quilometragemMediaRotas());
                    opcao = menu(opcao);
                    break;

                case "8":
                    frota.veiculosComMaisRotas();
                    opcao = menu(opcao);
                    break;

                case "9":
                    frota.custoVeiculoDescres();
                    opcao = menu(opcao);
                    break;

                case "10":
                    Data dtInicial = new Data();
                    Data dtFinal = new Data();
                    System.out.println("Entre com data inicial no formato DD/MM/AAAA: ");
                    dtInicial = Data.verificaData(teclado.nextLine());
                    System.out.println("Entre com data final no formato DD/MM/AAAA: ");
                    dtFinal = Data.verificaData(teclado.nextLine());
                    frota.rotasEntreDatas(dtInicial, dtFinal);
                    opcao = menu(opcao);
                    break;
                    
                case "F":
                    System.out.println("Finalizando...");
                    opcao = "F";
                    break;

                default:
                    System.out.println("opção invalida!");
                    opcao = menu(opcao);
                    break;
            }
        }
        teclado.close();
    }

    private static void clearBuffer(Scanner scanner) {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }

    private static void statusRotaAdicionada(int opcao) {
        switch (opcao) {
            case 1: System.out.println("Rota adicionada com sucesso."); break;
            case 2: System.out.println("Rota adicionada e veiculo abastecido com o combustível de maior autonomia."); break;
            case 3: System.out.println("O veiculo não suporta a distância da rota"); break;
        }
    }

    public static String menu(String opc) {
        System.out.println("\nFUNÇÕES DA FROTA:\n");
        System.out.println("1  -  Lista de veiculos e rotas dos arquivos.");
        System.out.println("2  -  Incluir um novo veículo.");
        System.out.println("3  -  Incluir rotas para um veículo.");
        System.out.println("4  -  Incluir custos extra.");
        System.out.println("5  -  Localizar um veículo da frota.");
        System.out.println("6  -  Veículo com seus gastos até o momento.");
        System.out.println("7  -  Quilometragem média de todas as rotas da empresa.");
        System.out.println("8  -  Veículos que mais fizeram rotas.");
        System.out.println("9  -  Veículos por custos gerados decrescente.");
        System.out.println("10 -  Filtro para busca de rotas entre datas.");
        System.out.println("\nEntre com o número correspondente à função ou 'f' para finalizar:\n");
        opc = teclado.nextLine().toUpperCase();
        return opc;
    }

    static void configurarMenuColecao(){
        todasAsFabricas = new ColecaoFabrica();
        todasAsFabricas.acrescentarFabrica("1", new FabricaCarro());
        todasAsFabricas.acrescentarFabrica("2", new FabricaVan());
        todasAsFabricas.acrescentarFabrica("3", new FabricaFurgao());
        todasAsFabricas.acrescentarFabrica("4", new FabricaCaminhao());
    }

    public static Veiculo criarVeiculo() throws Exception{
        String[] veiculos = {"CARRO","VAN","FURGAO","CAMINHAO"};
        Veiculo novoVeiculo= null;;
        int opcao;
        System.out.println("==========================");
        System.out.println("INCLUIR VEICULOS");
        for(int i = 1; i<=veiculos.length; i++){
            System.out.println(i+" - "+veiculos[i-1]);
        }                
        System.out.print("Escolha um numero: ");
        opcao = Integer.parseInt(teclado.nextLine());
        String tipo= veiculos[opcao-1];
        System.out.print("Informe os dados");
        System.out.print("\nPlaca: ");
        String placa = teclado.nextLine().toUpperCase();
        System.out.print("Valor: ");
        double valor = teclado.nextDouble();
        try{
            novoVeiculo = todasAsFabricas.criar(tipo,placa,valor);
        }catch(InvalidParameterException ipe){
            System.out.println("Tipo de veiculo inválido.");
        }
        return novoVeiculo;
    }
    
    static void configurarMenuReflexao() throws Exception{
        todasAsFabricas = new ColecaoFabrica();
        Scanner leitor = new Scanner(new File("fabricas.txt"));
        while(leitor.hasNextLine()){
            String[] dadosFabrica = leitor.nextLine().split(";");
            todasAsFabricas.acrescentarFabrica(dadosFabrica[0], 
                    (IFabrica)Class.forName(dadosFabrica[1])
                    .getConstructor().newInstance());
        }
    }

    static String listarDadosArquivos(){
        StringBuilder dadosArquivos = new StringBuilder("\nCarregando lista de veiculos em: " + arquivoVeiculo);
        dadosArquivos.append("Carregando lista de rotas em: " + arquivoRota
                            + "\n\nVEICULOS:"
                            + frota.exibirVeiculosArquivo(arquivoVeiculo)
                            + "\n\nROTAS:"
                            + frota.exibirRotasArquivo(arquivoRota));
        return dadosArquivos.toString();
    }    
}