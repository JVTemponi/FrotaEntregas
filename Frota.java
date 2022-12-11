import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Frota {

    static protected Set<Veiculo> veiculos = new LinkedHashSet<Veiculo>();

    public Frota() {
    };

    /**
     * Método inteiro para adição de rotas aos veiculos da frota. Valida se a rota desejada pode ser 
     * atribuída ao veiculo com base na autonomia do combustível atual e na capacidade máxima do tanque
     * @param rota Objeto da classe Rota
     * @param veiculoParaRota Objeto da classe Veiculo, referente à um veiculo da lista de veiculos de frota
     * 
     * @return  1 -> caso o veiculo possua combustível e capacidade de executar a rota
     *          2 -> caso o veiculo não possua combustível o suficiente mas possua capacidade de executar a rota
     *          3 -> caso o veiculo não possua a capacidade de executar a rota mesmo que o tanque esteja completo
     * @throws Exception devido ao método abastecer
     */

    public int addRota(Rota rota, Veiculo veiculoParaRota) throws Exception {
        if (veiculoParaRota.getAutonomiaAtual() >= rota.getDistancia()) {
            veiculoParaRota.addRota(rota);
            return 1;
        }
        else if (veiculoParaRota.getAutonomiaAtual() < rota.getDistancia() && veiculoParaRota.getAutonomiaMaxima() >= rota.getDistancia()){
            veiculoParaRota.abastecer(selecionarCombustivel(veiculoParaRota));
            veiculoParaRota.addRota(rota);
            return 2;
        }
        else{return 3;}
    }

    /**
     * Método privado para selecionar o combustível de maior rendimento do veiculo
     * @param veiculo Objeto da classe Veiculo
     * @return Retorna o combustível do Enum de combustíveis
     */
    private Combustivel selecionarCombustivel(Veiculo veiculo){
        double rendimento = 0;
        Combustivel combSelecionado = null;
        for (Combustivel comb : veiculo.tiposCombustivel) {
            if(comb.getConsumo() > rendimento){
                rendimento = comb.getConsumo();
                combSelecionado = comb;
            }
        }
        return combSelecionado;
    }

    /**
     * Método reponsável por adicionar veiculos à lista de veículos em frota
     * @param veiculo Objeto a ser incluído da classe Veiculo
     */
    public void adicionarVeiculos(Veiculo veiculo) {
        this.veiculos.add(veiculo);
    }

    /**
     * Método retona veiculo da lista de veiculos da classe frota, caso exista algum veiculo
     * onde a placa seja igual à informada por parâmetro
     * @param placaProcurar String com a placa do veiculo
     * @return Objeto da classe Veiculo ou null
     */
    public Veiculo retornaVeiculoPelaPlaca(String placaProcurar) {
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getPlaca().equals(placaProcurar)) {
                return veiculo;
            }
        }
        return null;
    }

    /**
     * Método retorna a quantidade de veiculos que possuem autonomia para percorrer a distancia informada
     * por parametro, considerando o tanque como cheio cheio. Também realiza a impressão destes veiculos
     * através do método autonomiaVeiculo() da classe Veiculo
     * 
     * @param distancia quilometragem da rota a ser adicionada.
     * @return inteiro com a quantidade de veiculos capazes de realizar a rota
     */
    public int imprimeVeiculosparaRota(double distancia) {
        List<Veiculo> ListaVeiculosRota = veiculos.stream()
                .filter(veiculos -> veiculos.getAutonomiaMaxima() >= distancia)
                .collect(Collectors.toList());
        ListaVeiculosRota.forEach(s -> System.out.println(s.autonomiaVeiculo()));
        return ListaVeiculosRota.size();
    }

    /**
     * Método retorna a quilometragem media de todas as rotas armazenadas na coleção de veiculos da classe
     * Frota
     */
    public double quilometragemMediaRotas() {
        List<Rota> rotas = veiculos.stream().flatMap(r-> r.getRotas().stream()).collect(Collectors.toList());
        double media = rotas.stream()
                .mapToDouble(rota -> rota.getDistancia())
                .average()
                .getAsDouble();
        return media;
    }

    /**
     * Método imprime os 3 veiculos da coleção de veículos de frota que possuem a maior quantidade de rotas
     */
    public void veiculosComMaisRotas() {
        List<Veiculo> top3Veiculos = veiculos.stream()
            .sorted(Comparator.comparingInt(Veiculo::quantRotas).reversed())
            .limit(3)
            .collect(Collectors.toList());
        top3Veiculos.forEach(System.out::println);
    }

    /**
     * Método ordena e imprime em ordem decrescente os veiculos com base em seu gasto total. utiliza o metodo
     * gastosVeiculo() da classe Veiculo para impressão.
     */
    public void custoVeiculoDescres() {
        List<Veiculo> veiculosOrenados = veiculos.stream()
                .sorted(Comparator.comparingDouble(Veiculo::retornaCustoTotal).reversed())
                .collect(Collectors.toList());
        veiculosOrenados.forEach(v -> System.out.println(v.gastosVeiculo()));
    }

    /**
     * Método filtra todas as rotas onde a data de ocorrência se encontra entre as datas recebidas por parâmetro e as imprime
     * através do método imprimeRota() da classe Rota.
     * @param inicial data inicial do periodo a ser verificado.
     * @param fim     data do final do periodo a ser avaliado.
     */
    public void rotasEntreDatas(Data inicial, Data fim) {
        List<Rota> rotas = veiculos.stream().flatMap(r-> r.getRotas().stream()).collect(Collectors.toList());
        if (inicial.getDiaAno() < fim.getDiaAno()) {
            rotas = rotas.stream()
                    .filter(r -> r.verificaDataPeriodo(inicial, fim))
                    .collect(Collectors.toList());
                    rotas.forEach(r -> r.imprimeRota());
        }
    }

    /**
     * Método carrega o conjunto de veiculos do arquivo txt onde o caminho passado por parâmetro aponta, adicionando 
     * estes veiculos em sua lista de veículos. 
     * 
     * Exemplo de estrura de arquivo esperada:
     * {Tipo do veiculo};{Placa};{valor}
     * 
     * @param localArquivo String com o caminho de onde estão salvos os veiculos. 
     * Exemplo:
     * media/pastaLinux/trabalhoFrotas/Veiculo.txt
     */
    public void carregarVeiculosArquivo(String localArquivo) {
        Scanner entrada;
        Veiculo novoVeiculo = null;
        String linhaLida;
        String[] veiculoLido;
        try {
            entrada = new Scanner(new FileReader(localArquivo));
            while (entrada.hasNextLine()) {
                linhaLida = entrada.nextLine();
                veiculoLido = linhaLida.split(";");
                switch (veiculoLido[0]) {
                    case ("Carro"): novoVeiculo = new Carro(veiculoLido[1], Double.parseDouble(veiculoLido[2]));break;
                    case ("Van"): novoVeiculo = new Van(veiculoLido[1], Double.parseDouble(veiculoLido[2]));break;
                    case ("Furgao"): novoVeiculo = new Furgao(veiculoLido[1], Double.parseDouble(veiculoLido[2]));break;
                    case ("Caminhao"): novoVeiculo = new Caminhao(veiculoLido[1], Double.parseDouble(veiculoLido[2]));break;
                }
                veiculos.add(novoVeiculo);
            }
            entrada.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método retorna uma String formatada com os veiculos do arquivo txt onde caminho passado por parâmetro aponta
     * @param localArquivo String com o caminho de onde estão salvos os veiculos. 
     * Exemplo:
     * media/pastaLinux/trabalhoFrotas/Veiculo.txt
     */
    public String exibirVeiculosArquivo(String localArquivo) {
        Scanner entrada;
        StringBuilder veiculosArquivos = new StringBuilder("\n");
        String linhaLida;
        try {
            entrada = new Scanner(new FileReader(localArquivo));
            while (entrada.hasNextLine()) {
                linhaLida = entrada.nextLine();
                veiculosArquivos.append("\n" +linhaLida);
            }
            entrada.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return veiculosArquivos.toString();
    }

    /**
     * Método salva os veiculos de frota no arquivo txt do caminho passado por parâmetro
     * @param localArquivo String com o caminho de onde serão salvos os veículos. 
     * Exemplo:
     * /media/pastaLinux/trabalhoFrotas/Veiculos.txt
     */
    public void salvaVeiculosFrota(String localArquivo) {
        File arquivo = new File(localArquivo);
        try {
            if (!arquivo.exists()) {arquivo.createNewFile();}
            FileWriter arqEscrita = new FileWriter(arquivo, false);
            BufferedWriter escritor = new BufferedWriter(arqEscrita);
            for (Veiculo veiculo : veiculos) {
                escritor.write(veiculo.escreveVeiculoArquivo());
                escritor.newLine();
            }
            escritor.close();
            arqEscrita.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Método carrega o conjunto de rotas do arquivo txt onde o caminho passado por parâmetro aponta, carregando 
     * estas rotas em sua lista de veículos correspondentes. O metodo valida se a placa inforada no arquivo
     * faz referencia à placa de algum dos objetos de Veiculo da classe.
     * 
     * Exemplo de estrura de arquivo esperada:
     * {distancia percorrida};{data no formato dd/mm/aaa};{Placa}
     * 
     * @param localArquivo String com o caminho de onde estão salvas as rotas. 
     * Exemplo:
     * media/pastaLinux/trabalhoFrotas/Rotas.txt
     */
    public void carregarRotasArquivo(String localArquivo) {
        Scanner entrada;
        String linhaLida;
        String[] rotaLida;
        Veiculo vec;
        try {
            entrada = new Scanner(new FileReader(localArquivo));
            while (entrada.hasNextLine()) {
                linhaLida = entrada.nextLine();
                rotaLida = linhaLida.split(";");
                Rota novaRota = new Rota(Double.parseDouble(rotaLida[0]), Data.verificaData(rotaLida[1]),retornaVeiculoPelaPlaca(rotaLida[2]));
                vec = retornaVeiculoPelaPlaca(rotaLida[2]);
                vec.addRota(novaRota);
            }
            entrada.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método retorna uma String formatada com as rotas do arquivo txt onde caminho passado por parâmetro aponta
     * @param localArquivo String com o caminho de onde estão salvas as rotas. 
     * Exemplo:
     * media/pastaLinux/trabalhoFrotas/Rotas.txt
     */
    public String exibirRotasArquivo(String localArquivo) {
        StringBuilder rotasArquivos = new StringBuilder("\n");
        Scanner entrada;
        String linhaLida;
        try {
            entrada = new Scanner(new FileReader(localArquivo));
            while (entrada.hasNextLine()) {
                linhaLida = entrada.nextLine();
                rotasArquivos.append("\nRota: " + linhaLida);
            }
            entrada.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return rotasArquivos.toString();
    }

    /**
     * Método salva as rotas dos veiculos de frota no arquivo txt do caminho passado por parâmetro
     * @param localArquivo String com o caminho de onde serão salvas as rotas. 
     * Exemplo:
     * /media/pastaLinux/trabalhoFrotas/Rotas.txt
     */
    public void salvaRotasFrota(String localArquivo) {
        File arquivo = new File(localArquivo);
        List<Rota> rotas = veiculos.stream().flatMap(r-> r.getRotas().stream()).collect(Collectors.toList());
        try {
            if (!arquivo.exists()) {arquivo.createNewFile();}
            FileWriter arqEscrita = new FileWriter(arquivo, false);
            BufferedWriter escritor = new BufferedWriter(arqEscrita);
            for (Rota rota : rotas ) {
                escritor.write(rota.escreveRotaArquivo());
                escritor.newLine();
            }
            escritor.close();
            arqEscrita.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}