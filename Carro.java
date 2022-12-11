
public class Carro extends Veiculo {
    private final int TANQUE_COMPLETO = 50;
    private final double DIST_ALINHAMENTO_CARRO = 10000d;
    private final double PRECO_ALINHAMENTO_CARRO = 80d;
    private final double TAXA_CARRO = 300d;

    /**
     * Método construtor da classe Carro.
     * @param placa String constendo a placa do veículo
     * @param valorVenda double contendo o valor de venda do veículo
     */
    public Carro(String placa, double valorVenda) {
        super(placa, valorVenda);
        this.tiposCombustivel.add(Combustivel.GASOLINA);
        this.tiposCombustivel.add(Combustivel.ALCOOL);
        this.tanque = new Tanque(TANQUE_COMPLETO);
        this.tanque.completarTanque(Combustivel.GASOLINA);
        this.autonomiaMaxima = getAutonomiaMaxima();
        this.autonomiaAtual = autonomiaMaxima;
    }

    @Override
    public void calculaCustoFixo() {
        double ipva = this.valorVenda * Tarifas.CARRO.getIpva();
        double seguro = (this.valorVenda * Tarifas.CARRO.getSeguro()) + TAXA_CARRO;
        this.custoFixo = ipva + seguro;
    }

    @Override
    public void calculaCustoVariavel() {
        double alinhamento = ((int)(this.kilometragemTotal/DIST_ALINHAMENTO_CARRO) * PRECO_ALINHAMENTO_CARRO);
        this.custoVariavel = alinhamento + this.custosExtra;
    }
}