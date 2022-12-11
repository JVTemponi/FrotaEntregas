
public class Van extends Veiculo{
    private final int TANQUE_COMPLETO = 60;
    private final double DIST_ALINHAMENTO_VAN = 10000d;
    private final double PRECO_ALINHAMENTO_VAN = 120d;
    private final double DIST_VISTORIA_VAN = 10000d;
    private final double PRECO_VISTORIA_VAN = 500d;

    /**
     * Método construtor da classe Van.
     * @param placa String constendo a placa do veículo
     * @param valorVenda double contendo o valor de venda do veículo
     */
    public Van(String placa, double valorVenda) {
        super(placa, valorVenda);
        this.tiposCombustivel.add(Combustivel.GASOLINA);
        this.tiposCombustivel.add(Combustivel.DIESEL);
        this.tanque = new Tanque(TANQUE_COMPLETO);
        this.tanque.completarTanque(Combustivel.GASOLINA);
        this.autonomiaMaxima = getAutonomiaMaxima();
        this.autonomiaAtual =  this.autonomiaMaxima;
    }

    @Override
    public void calculaCustoFixo() {
        double ipva = (this.valorVenda * Tarifas.VAN.ipva);
        double seguro = (this.valorVenda * Tarifas.VAN.seguro);
        this.custoFixo = ipva + seguro;
    }

    @Override
    public void calculaCustoVariavel() {
        double alinhamento = ((int)(this.kilometragemTotal/DIST_ALINHAMENTO_VAN) * PRECO_ALINHAMENTO_VAN);
        double vistoria = ((int)(this.kilometragemTotal/DIST_VISTORIA_VAN) * PRECO_VISTORIA_VAN);
        this.custoVariavel = alinhamento + vistoria + this.custosExtra;
    }
}
