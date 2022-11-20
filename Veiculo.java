import java.util.ArrayList;
import java.util.List;

public abstract class Veiculo implements ICustos{
    protected String placa;
    protected double valorVenda;
    protected double custoFixo;
    protected double custoVariavel;
    protected double kilometragemTotal;
    protected double autonomiaAtual;
    protected double autonomiaMaxima;
    protected double custosExtra = 0;
    protected List<Combustivel> tiposCombustivel = new ArrayList<Combustivel>();
    protected List<Rota> rotas = new ArrayList<Rota>();

    /**
     * Construtor da classe abstrata Veiculo, recebe a placa identificadora do carro e o seu valor de venda por parâmetro 
     * @param placa String contendo a placa do carro
     * @param valorVenda Double com o valor de venda paga na aquisição do carro
     */
    public Veiculo(String placa, double valorVenda) {
        this.placa = placa;
        this.valorVenda = valorVenda;
        this.kilometragemTotal = 0;
        calculaCustoFixo();
    }

    /**
     * Método exibe todos os combustíveis suportados pelo veículo
     */
    public void exibirTiposCombustivel() {
        tiposCombustivel.forEach(combustivel -> {System.out.printf("\n" + combustivel.getDescricao());});
    }

    
    /**
     * Método retorna o custo total de um veículo, somando os custos fixos com os custos
     * variáveis
     * @return Retorna o custo total do veículo
     */
    public double retornaCustoTotal() {
        return this.custoFixo + this.custoVariavel;
    }

    /**
     * Método adiciona um valor double para compor o total gasto em custos extras
     * @param valor Double com o valor do novo gasto esporadico
     * @return Retorna um valor double com o total de gastos esporadicos até o momento
     */
    public void addCustosExtra(double valor){
        this.custosExtra += valor;
    }

    /**
     * Método imprime custos do veículo, inclindo os fixos, variáveis e total.
     */
    public void imprimeVeiculoCustos() {
        System.out.println("Veículo  : Placa: " + this.placa + " - "
                + " Custo Fixo: " + String.format("%.2f", this.custoFixo) + " - "
                + " Custo Variavel: " + String.format("%.2f", this.custoVariavel) + " = "
                + " Custo Total: " + String.format("%.2f", this.retornaCustoTotal()));
    }

    @Override
    public boolean equals(Object obj) {
        Veiculo outro = (Veiculo)obj;
        return this.placa.equals(outro.placa);
    }

    @Override
    public int hashCode() {
        return this.placa.hashCode();
    }
    
    /**
     * Método abstrato implementado nas classes filhas de veículo booleano para verificar se foi possível abastecer o tanque do veículo com o combustível informado. O tanque é abastecido
     * até sua capacidade máxima
     * @param tipoCombustivel Inteiro relacionado ao identificador do combustível, sendo 1 para gasolina, 2 para etanol e 3 para diesel
     * @return Retorna TRUE caso tenha abastecido, retorna FALSE caso contrário
     */

    public abstract boolean abastecer(int tipoCombustivel);
    
    /**
     * Método abstrato implementado nas classes filhas de veículo que imprime dados básicos do veículo no console
     */
    public abstract void imprimeVeiculoConsole();

    /**
     * Método abstrato implementado nas classes filhas de veículo que imprime dados do veículo no console, incluindo os custos
     */
    public abstract void imprimeDadosVeiculoConsole();

    /**
     * Método abstrato implementado nas classes filhas de veículo que escreve os dados referentes ao veículo em um arquivo, sendo estes a placa e o valor de venda
     * @return
     */
    public abstract String escreveVeiculoArquivo();

    /**
     * Método abstrato implementado nas classes filhas de veículo com uma sugestão de toString
     * @return
     */
    public abstract String escreveVeiculoFrota();

    /**
     * Método abstrato implementado nas classes filhas de veículo que imprime a placa do veículo e capacidade atual do tanque
     */
    public void imprimeVeiculoPlaca() {
    }

    public String getPlaca() {
        return this.placa;
    }
    public double getAutonomiaAtual() {
        return autonomiaAtual;
    }

    public double getAutonomiaMaxima() {
        return this.autonomiaMaxima;
    }
}
