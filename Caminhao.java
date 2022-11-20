
public class Caminhao extends Veiculo{
    private static final int MANUTENCAO = 20000;
    private static final int PRECO_MANUTENCAO = 1000;
    private static final int PRECO_VISTORIA = 1000;
    private static final double IPVA = 0.01d;
    private static final int VISTORIA = 30000;
    private static final double TAXA = 2000d;
    private static final double SEGURO = 0.02d;
    private final int TANQUE_COMPLETO = 250;

    /**
     * Método construtor da classe Caminhao.
     * @param placa String constendo a placa do veículo
     * @param valorVenda double contendo o valor de venda do veículo
     */
    public Caminhao(String placa, double valorVenda) {
        
        super(placa, valorVenda);
        this.tiposCombustivel.add(Combustivel.DIESEL);
        this.autonomiaMaxima = getAutonomiaMaxima();
        this.autonomiaAtual =  this.autonomiaMaxima;
    }

    @Override
    public double getAutonomiaMaxima() {
        double maiorAutonomia = 0;
        for (Combustivel consumo : tiposCombustivel) {
            if (consumo.getConsumo() > maiorAutonomia) {
                maiorAutonomia = consumo.getConsumo();
            }
        }
        return maiorAutonomia * TANQUE_COMPLETO;
    }

    @Override
    public void calculaCustoFixo() {
        double ipva = this.valorVenda * IPVA;
        double seguro = (this.valorVenda * SEGURO) + TAXA;
        this.custoFixo = ipva + seguro;
    }

    @Override
    public void calculaCustoVariavel() {
        double manutencao = ((int)(this.kilometragemTotal/MANUTENCAO)* PRECO_MANUTENCAO);
        double vistoria = ((int)(this.kilometragemTotal/VISTORIA)* PRECO_VISTORIA);
        this.custoVariavel = manutencao + vistoria + this.custosExtra;
    }

    @Override
    public boolean abastecer(int tipoCombustivel) {
        if (tipoCombustivel == 1) {
            autonomiaAtual = TANQUE_COMPLETO * tiposCombustivel.get(1).consumo;
            return true;
        }
        if (tipoCombustivel == 2) {
            autonomiaAtual = TANQUE_COMPLETO * tiposCombustivel.get(2).consumo;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void imprimeVeiculoConsole() {
        System.out.println("Caminhão: Placa: " + placa + " - "
                + " Valor de venda: " + String.format("%.2f", valorVenda) + "\n"
                + " Capacidade Tanque: " + TANQUE_COMPLETO + "\n"
                + " IPVA + Seguro: " + String.format("%.2f", this.custoFixo) + "\n"
                + " Combustíveis compatíveis: Gasolina e Etanol" + "\n");
    }

    @Override
    public void imprimeDadosVeiculoConsole() {
        System.out.println("Caminhao: Placa: " + placa + " - "
                + "\nValor de venda: " + String.format("%.2f", valorVenda) + ";"
                + "\n Quilometros rodados: " + String.format("%.2f", kilometragemTotal) + " - "
                + "\nGastos Fixos: " + String.format("%.2f", custoFixo) + " - "
                + "\nGastos Variáveis: " + String.format("%.2f", custoVariavel));
    }

    @Override
    public String escreveVeiculoArquivo() {
        String salvaParaArquivo = "Caminhao;"
                + this.placa + ";"
                + this.valorVenda;
        return salvaParaArquivo;
    }
    @Override
    public String escreveVeiculoFrota() {
        String veiculoRota = "Caminhão: "
                + this.placa + " Qtde rotas: ";
        return veiculoRota;
    }

    @Override
    public void imprimeVeiculoPlaca() {
        System.out.print("Caminhão: Placa: " + this.placa + " - "
                + " Autonomia atual: " + this.autonomiaAtual + " - "
                + " Autonomia Máxima:");
        for (Combustivel combustivel : tiposCombustivel) {
            System.out.print(" " + combustivel.getDescricao() + "=" + this.TANQUE_COMPLETO * combustivel.getConsumo());
        }
        System.out.println();
    }
}
