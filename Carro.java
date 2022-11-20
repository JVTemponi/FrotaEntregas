
public class Carro extends Veiculo {
    private static final int ALINHAMENTO = 10000;
    private static final int PRECO_ALINHAMENTO = 80;
    private static final double IPVA = 0.04d;
    private static final double TAXA = 300d;
    private static final double SEGURO = 0.05d;
    private final int TANQUE_COMPLETO = 50;

    /**
     * Método construtor da classe Carro.
     * @param placa String constendo a placa do veículo
     * @param valorVenda double contendo o valor de venda do veículo
     */
    public Carro(String placa, double valorVenda) {
        super(placa, valorVenda);
        this.tiposCombustivel.add(Combustivel.GASOLINA);
        this.tiposCombustivel.add(Combustivel.ALCOOL);
        this.autonomiaMaxima = getAutonomiaMaxima();
        this.autonomiaAtual = autonomiaMaxima;
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
        double alinhamento = (int) (this.kilometragemTotal / ALINHAMENTO);
        alinhamento = alinhamento * PRECO_ALINHAMENTO;
        this.custoVariavel = alinhamento + this.custosExtra;
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
        System.out.println("Carro  : Placa: " + placa + " - "
                + " Valor de venda: " + String.format("%.2f", valorVenda) + "\n"
                + " Capacidade Tanque: " + TANQUE_COMPLETO + "\n"
                + " IPVA, Seguro + Taxa: " + String.format("%.2f", this.custoFixo) + "\n"
                + " Combustíveis compatíveis: Gasolina e Etanol" + "\n");
    }

    @Override
    public void imprimeDadosVeiculoConsole() {
        System.out.println("Carro   : Placa: " + placa + " - "
                + "\nValor de venda: " + String.format("%.2f", valorVenda) + ";"
                + "\n Quilometros rodados: " + String.format("%.2f", kilometragemTotal) + " - "
                + "\nGastos Fixos: " + String.format("%.2f", custoFixo) + " - "
                + "\nGastos Variáveis: " + String.format("%.2f", custoVariavel));
    }

    @Override
    public String escreveVeiculoArquivo() {
        String salvaParaArquivo = "Carro;"
                + this.placa + ";"
                + this.valorVenda;
        return salvaParaArquivo;
    }

    @Override
    public String escreveVeiculoFrota() {
        String veiculoRota = "Carro: "
                + this.placa + " Qtde rotas: ";
        return veiculoRota;
    }

    @Override
    public void imprimeVeiculoPlaca() {
        System.out.print("Carro   : Placa: " + this.placa + " - "
                + " Autonomia atual: " + this.autonomiaAtual + " - "
                + " Autonomia Máxima:");
        for (Combustivel combustivel : tiposCombustivel) {
            System.out.print(" " + combustivel.getDescricao() + "=" + this.TANQUE_COMPLETO * combustivel.getConsumo());
        }
        System.out.println();
    }
}
