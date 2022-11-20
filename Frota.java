import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Frota {

    protected Set<Veiculo> veiculos = new HashSet<Veiculo>();
    protected Set<Rota> rotas = new HashSet<Rota>();

    public Frota() {
    };

    /**
     * Método booleano para adição de rota. Valida se a rota desejada pode ser
     * atribuída ao veiculo com base na autonomia do combustível atual e na
     * capacidade máxima do tanque
     * 
     * @param rota Objeto da classe Rota
     * @return Retorna TRUE caso a rota tenha sido adicionada, retorna FALSE caso
     *         contrário
     */

    public boolean addRota(Rota rota, Veiculo veiculoParaRota) {
        rotas.add(rota);
        veiculoParaRota.autonomiaAtual -= rota.getDistancia();
        veiculoParaRota.kilometragemTotal += rota.getDistancia();
        veiculoParaRota.calculaCustoVariavel();
        return true;
    }

    /**
     * Método sem retorno para adicionar Veiculos à classe Frota
     * 
     * @param veiculo
     */
    public void adicionarVeiculos(Veiculo veiculo) {
        this.veiculos.add(veiculo);
    }

    /**
     * Metodo recebe string da placa do veiculo e retorna objeto Veiculo.
     * 
     * @param placaProcurar string da placa veiculo para procurar
     * @return Veiculo encontrado ou null.
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
     * Método Imprime veiculos com autonomia para executar a rota com um tanque de
     * combustivel cheio.
     * 
     * @param distancia quilometragem da rota a ser executada.
     * @return imprime no console todos os veiculos com autonomia para a
     *         quilometragem da rota.
     */
    public int imprimeVeiculosparaRota(double distancia) {
        List<Veiculo> ListaVeiculosRota = veiculos.stream()
                .filter(veiculos -> veiculos.getAutonomiaMaxima() >= distancia)
                .collect(Collectors.toList());
        ListaVeiculosRota.forEach(s -> s.imprimeVeiculoPlaca());

        if (ListaVeiculosRota.size() > 0) {
            return ListaVeiculosRota.size();
        } else {
            System.out.println("não tem carro");
        }
        return ListaVeiculosRota.size();
    }

    /**
     * Retorna doubel do valor da quilometragem média de todas as rotas da Frota.
     */
    public Double quilometragemMediaRotas() {
        double media = rotas.stream()
                .mapToDouble(rota -> rota.getDistancia())
                .average()
                .getAsDouble();
        return media;
    }

    /**
     * Método que imprime os 3 veiculos com mais rotas atribuidas.
     */
    public void veiculosComMaisRotas() {

        Map<Veiculo, Long> agrupaVeiculos = new HashMap<>();
        Map<Veiculo, Long> agrupaTop3Veiculos = new HashMap<>();

        veiculos.stream().forEach(v -> agrupaVeiculos.put(v, rotas.stream()
                .filter(r -> r.getVeiculoRota().getPlaca().equals(v.getPlaca()))
                .count()));

        agrupaTop3Veiculos = agrupaVeiculos.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, HashMap::new));

        agrupaTop3Veiculos.forEach((key, value) -> System.out.println(key.escreveVeiculoFrota() + value));
    }

    /**
     * Imprime veiculos com seus custos totais com ordenação decrescente.
     */
    public void custoVeiculoDescres() {
        List<Veiculo> veiculosOrenados = veiculos.stream()
                .sorted(Comparator.comparingDouble(Veiculo::retornaCustoTotal).reversed())
                .collect(Collectors.toList());
        veiculosOrenados.forEach(v -> v.imprimeVeiculoCustos());
    }

    /**
     * Filtra e imprime todas as rotas com data de execussão entre duas datas
     * recebidas por paramentro.
     * 
     * @param inicial data inicial do periodo a ser verificado.
     * @param fim     data do final do periodo a ser avaliado.
     */
    public void rotasEntreDatas(Data inicial, Data fim) {
        if (inicial.getDiaAno() < fim.getDiaAno()) {
            List<Rota> ListaVeiculosRota = rotas.stream()
                    .filter(rotas -> rotas.verificaDataPeriodo(inicial, fim))
                    .collect(Collectors.toList());
            ListaVeiculosRota.forEach(r -> r.imprimeRota());
        }
    }

    /**
     * Carrega um conjunto de veiculos arqmaenados em arquivo txt no endereço
     * recebido por parametro.
     * 
     * @param localArquivo local do arquivo contém dados dos veiculos.
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
                    case ("Carro"):
                        novoVeiculo = new Carro(veiculoLido[1], Double.parseDouble(veiculoLido[2]));
                        break;
                    case ("Van"):
                        novoVeiculo = new Van(veiculoLido[1], Double.parseDouble(veiculoLido[2]));
                        break;
                    case ("Furgao"):
                        novoVeiculo = new Furgao(veiculoLido[1], Double.parseDouble(veiculoLido[2]));
                        break;
                    case ("Caminhao"):
                        novoVeiculo = new Caminhao(veiculoLido[1], Double.parseDouble(veiculoLido[2]));
                        break;
                }
                veiculos.add(novoVeiculo);
            }
            entrada.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Exibe veiculos carregados de arquivo.
     * 
     * @param localArquivo
     */
    public void exibirVeiculosArquivo(String localArquivo) {

        Scanner entrada;
        Veiculo novoVeiculo = null;
        String linhaLida;
        String[] veiculoLido;
        try {
            entrada = new Scanner(new FileReader(localArquivo));
            while (entrada.hasNextLine()) {

                linhaLida = entrada.nextLine();
                System.out.println(linhaLida);
                veiculoLido = linhaLida.split(";");
                switch (veiculoLido[0]) {
                    case ("Carro"):
                        novoVeiculo = new Carro(veiculoLido[1], Double.parseDouble(veiculoLido[2]));
                        break;
                    case ("Van"):
                        novoVeiculo = new Van(veiculoLido[1], Double.parseDouble(veiculoLido[2]));
                        break;
                    case ("Furgao"):
                        novoVeiculo = new Furgao(veiculoLido[1], Double.parseDouble(veiculoLido[2]));
                        break;
                    case ("Caminhao"):
                        novoVeiculo = new Caminhao(veiculoLido[1], Double.parseDouble(veiculoLido[2]));
                        break;
                }
                veiculos.add(novoVeiculo);
            }
            entrada.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo salva arquivo contendo conjunto de veiculos no endereço recebido por
     * string.
     * 
     * @param nomeArquivo local para salvar arquivo.
     */
    public void salvaVeiculosFrota(String nomeArquivo) {

        File arquivo = new File(nomeArquivo);
        try {
            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }

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
     * Metodo carrega conjunto de rotas para veiculos da Frota
     * 
     * @param localArquivo local do arquivo contendo informações sobre rotas para
     *                     veiculos.
     */
    public void carregarRotasArquivo(String localArquivo) {

        Scanner entrada;
        String linhaLida;
        String[] rotaLida;

        try {
            entrada = new Scanner(new FileReader(localArquivo));
            while (entrada.hasNextLine()) {
                linhaLida = entrada.nextLine();
                rotaLida = linhaLida.split(";");
                Rota novaRota = new Rota(Double.parseDouble(rotaLida[0]), Data.verificaData(rotaLida[1]),
                        retornaVeiculoPelaPlaca(rotaLida[2]));
                rotas.add(novaRota);
            }
            entrada.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Exibe no console as rotas carregas do arquivo recebido por parametro.
     * 
     * @param localArquivo local do arquivo a ser exibido.
     */
    public void exibirRotasArquivo(String localArquivo) {

        Scanner entrada;
        String linhaLida;
        String[] rotaLida;

        try {
            entrada = new Scanner(new FileReader(localArquivo));
            while (entrada.hasNextLine()) {

                linhaLida = entrada.nextLine();
                System.out.println(linhaLida);
                rotaLida = linhaLida.split(";");
                Rota novaRota = new Rota(Double.parseDouble(rotaLida[0]), Data.verificaData(rotaLida[1]),
                        retornaVeiculoPelaPlaca(rotaLida[2]));
                rotas.add(novaRota);
            }
            entrada.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que salva conjunto de rotas em arquivo .txt no local recebido por
     * paramentro.
     * 
     * @param nomeArquivo string representa local do arquivo salvo.
     */
    public void salvaRotasFrota(String nomeArquivo) {

        File arquivo = new File(nomeArquivo);
        try {
            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }
            FileWriter arqEscrita = new FileWriter(arquivo, false);
            BufferedWriter escritor = new BufferedWriter(arqEscrita);

            for (Rota rota : rotas) {
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
