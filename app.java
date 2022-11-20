import java.util.Scanner;

public class app {
    static Scanner obj = new Scanner(System.in);
    static String arquivoVeiculo = "/media/juvito/Data/Backup_lpm/projetos-3-4-5-grupo-1-novo-1/projetos-3-4-5-grupo-1-novo-1/codigo/Veiculos_ler.txt";
    static String arquivoRota = "/media/juvito/Data/Backup_lpm/projetos-3-4-5-grupo-1-novo-1/projetos-3-4-5-grupo-1-novo-1/codigo/Rotas_ler.txt";
    //static String arquivoVeiculo = "C:/Users/Mauricio/Documents/GitHub/projetos-3-4-5-grupo-1-novo-1/codigo/Veiculos_ler.txt";
    //static String arquivoRota = "C:/Users/Mauricio/Documents/GitHub/projetos-3-4-5-grupo-1-novo-1/codigo/Rotas_ler.txt";

    public static void main(String[] args) throws Exception {
    
        Veiculo veiculoParaRota = null;
        Frota frota = new Frota();
        String placaVeiculo;
        Rota novaRota = null;
        double kmRota = 0;
        Data dtProducao = null;

        frota.carregarVeiculosArquivo(arquivoVeiculo);
        frota.carregarRotasArquivo(arquivoRota);

        String opcao = "";
        opcao = menu(opcao);

        while (opcao != "F") {
            switch (opcao) {
                case "1":
                    System.out.println("CARREGAR UM CONJUNTO DE VEÍCULOS E ROTAS:");
                    System.out.println("Carregando lista de veiculos em: " + arquivoVeiculo);
                    System.out.println("Carregando lista de veiculos em: " + arquivoRota);
                    frota.exibirVeiculosArquivo(arquivoVeiculo);
                    frota.exibirRotasArquivo(arquivoRota);
                    opcao = menu(opcao);
                    break;

                case "2":
                    System.out.println("Salvar um conjunto de veículos em um arquivo:\n");
                    System.out.println("conjunto de veículos:" + arquivoVeiculo);
                    System.out.println("conjunto de rotas:" + arquivoRota);
                    frota.salvaVeiculosFrota(arquivoVeiculo);
                    frota.salvaRotasFrota(arquivoRota);
                    opcao = menu(opcao);
                    break;

                case "3":
                    System.out.println("Incluir um novo veículo:\n");
                    System.out.println("\nEntre com os dados do veiculo para incluir:");
                    System.out.print("\nPlaca:");
                    String placa = obj.nextLine().toUpperCase();
                    System.out.print("\nValor de venda:");
                    double valorVenda = obj.nextDouble();
                    obj.nextLine(); // Limpar buffer Scanner?
                    System.out.println("\nEntre com o tipo de Veículo:");
                    System.out.println("1 - Carro");
                    System.out.println("2 - Van");
                    System.out.println("3 - Furgão");
                    System.out.println("4 - Caminhão");
                    System.out.print("Tipo de Veículo:");
                    char tipo = obj.nextLine().charAt(0);

                    switch (tipo) {
                        case '1':
                            Veiculo novoCarro = new Carro(placa, valorVenda);
                            frota.adicionarVeiculos(novoCarro);
                            break;
                        case '2':
                            Veiculo novaVan = new Van(placa, valorVenda);
                            frota.adicionarVeiculos(novaVan);
                            break;
                        case '3':
                            Veiculo novoFurgao = new Furgao(placa, valorVenda);
                            frota.adicionarVeiculos(novoFurgao);
                            break;
                        case '4':
                            Veiculo novoCaminhao = new Caminhao(placa, valorVenda);
                            frota.adicionarVeiculos(novoCaminhao);
                            break;
                        default:
                            System.out.println("opção invalida!");
                            break;
                    }
                    opcao = menu(opcao);
                    break;

                case "4":
                    System.out.println("INCLUIR ROTAS PARA UM VEÍCULO:");
                    System.out.println("Entre com os dados da rota para incluir:");
                    System.out.println("\nEntre com data para a rota formato DD/MM/AAAA: ");
                    dtProducao = Data.verificaData(obj.nextLine());

                    System.out.print("\nQuilometros da rota:");
                    kmRota = obj.nextDouble();
                    clearBuffer(obj);
                    System.out.println("Veíulos abastecidos com autonomia para a rota de:" + kmRota + "Km.");
                    if (frota.imprimeVeiculosparaRota(kmRota) > 0) {
                        System.out.println("\nEntre com a placa do veículo para a rota:\n Procurar pela placa: ");
                        veiculoParaRota = frota.retornaVeiculoPelaPlaca(obj.nextLine().toUpperCase());
          
                        if (veiculoParaRota.getAutonomiaAtual() < kmRota) {
                            System.out.println("O veículo placa: " + veiculoParaRota.placa
                                    + " não tem combustivel suficiente para a rota.\n");
                            System.out.println("Tipos de combustivel para veículo placa: " + veiculoParaRota.placa);
                            veiculoParaRota.exibirTiposCombustivel();
                            if (veiculoParaRota.abastecer(obj.nextInt() - 1) == true) {
                                clearBuffer(obj);
                                System.out.println("abastecido");
                                veiculoParaRota.getAutonomiaAtual();
                            }

                        }
                        novaRota = new Rota(kmRota, dtProducao, veiculoParaRota);
                        frota.addRota(novaRota, veiculoParaRota);
                    } else {
                        System.out.println("Não tem um veículo com autonomia para a rota.\n");
                        frota.imprimeVeiculosparaRota(0);
                        System.out.println("Não tem um veículo com autonomia para a rota.\n");
                    }

                    opcao = menu(opcao);
                    break;

                    case "5":
                    System.out.println("Incluir custos extra");
                    System.out.println("\nEntre com a placa do Veículo para adicionar o custo extra:");
                    placaVeiculo = obj.nextLine().toUpperCase();
                    veiculoParaRota = frota.retornaVeiculoPelaPlaca(placaVeiculo);
                    System.out.printf("\nInforme o valor do custo extra: R$");
                    veiculoParaRota.addCustosExtra(obj.nextDouble());
                    veiculoParaRota.calculaCustoVariavel();
                    System.out.println("O total de custos extras atual é: R$" + veiculoParaRota.custosExtra);
                    opcao = menu(opcao);
                    break;

                case "6":
                    System.out.println("Localizar um veículo da frota.");
                    System.out.println("\nEntre com a placa do Veículo para localizar:");
                    placaVeiculo = obj.nextLine().toUpperCase();
                    veiculoParaRota = frota.retornaVeiculoPelaPlaca(placaVeiculo);
                    veiculoParaRota.imprimeVeiculoConsole();
                    opcao = menu(opcao);
                    break;

                case "7":
                    System.out.println("Imprimir um relatório do veículo com seus gastos até o momento.\n");
                    System.out.println("\nEntre com a placa do Veículo para imprimir gastos:");
                    placaVeiculo = obj.nextLine().toUpperCase();
                    if (frota.retornaVeiculoPelaPlaca(placaVeiculo) != null) {
                        veiculoParaRota = frota.retornaVeiculoPelaPlaca(placaVeiculo);
                        veiculoParaRota.imprimeDadosVeiculoConsole();
                    }
                    opcao = menu(opcao);
                    break;

                case "8":
                    System.out.println("Quilometragem média de todas as rotas da empresa.");
                    System.out.println(frota.quilometragemMediaRotas());

                    opcao = menu(opcao);
                    break;
                case "9":
                    System.out.println("Veículos que mais fizeram rotas.");

                    frota.veiculosComMaisRotas();
                    opcao = menu(opcao);
                    break;
                case "10":
                    System.out.println("veículos por custos gerados decrescente.");
                    frota.custoVeiculoDescres();
                    opcao = menu(opcao);
                    break;
                case "11":
                    System.out.println("Filtro para busca de rotas entre datas.");
                    Data dtInicial = new Data();
                    Data dtFinal = new Data();
                    System.out.println("Entre com data inicial no formato DD/MM/AAAA: ");
                    dtInicial = Data.verificaData(obj.nextLine());
                    System.out.println("Entre com data final no formato DD/MM/AAAA: ");
                    dtFinal = Data.verificaData(obj.nextLine());
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
        obj.close();
    }

    private static void clearBuffer(Scanner scanner) {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }

    public static String menu(String opc) {

        System.out.println("\n  FUNÇÕES DA FROTA:  \n");
        System.out.println("1 -  Carregar um conjunto de veículos e rotas de um arquivo.");
        System.out.println("2 -  Salvar um conjunto de veículos e rotas em um arquivo.");
        System.out.println("3 -  Incluir um novo veículo.");
        System.out.println("4 -  Incluir rotas para um veículo.");
        System.out.println("5 -  Incluir custos extra.");
        System.out.println("6 -  Localizar um veículo da frota.");
        System.out.println("7 -  Veículo com seus gastos até o momento.");
        System.out.println("8 -  Quilometragem média de todas as rotas da empresa.");
        System.out.println("9 -  Veículos que mais fizeram rotas.");
        System.out.println("10 - Veículos por custos gerados decrescente.");
        System.out.println("11 - Filtro para busca de rotas entre datas.");

        System.out.println("\nEntre com o número correspondente à função ou 'f' para finalizar:\n");
        opc = obj.nextLine().toUpperCase();

        return opc;
    }
}