
public class Furgao extends Veiculo {
    private final int TANQUE_COMPLETO = 80;
    private final double DIST_ALINHAMENTO_FURGAO = 10000d;
    private final double PRECO_ALINHAMENTO_FURGAO = 120d;
    private final double DIST_VISTORIA_FURGAO = 10000d;
    private final double PRECO_VISTORIA_FURGAO = 500d;

    /**
     * Método construtor da classe Furgao.
     * @param placa String constendo a placa do veículo
     * @param valorVenda double contendo o valor de venda do veículo
     */
    public Furgao(String placa, double valorVenda) {
        super(placa, valorVenda);
        this.tiposCombustivel.add(Combustivel.GASOLINA);
        this.tanque = new Tanque(TANQUE_COMPLETO);
        this.tanque.completarTanque(Combustivel.GASOLINA);
        this.autonomiaMaxima = getAutonomiaMaxima();
        this.autonomiaAtual = this.autonomiaMaxima;
    }

    @Override
    public void calculaCustoFixo() {
        double ipva = this.valorVenda * Tarifas.FURGAO.getIpva();
        double seguro = (this.valorVenda * Tarifas.FURGAO.getSeguro());
        this.custoFixo = ipva + seguro;
    }

    @Override
    public void calculaCustoVariavel() {
        double alinhamento = ((int)(this.kilometragemTotal/DIST_ALINHAMENTO_FURGAO) * PRECO_ALINHAMENTO_FURGAO);
        double vistoria = ((int)(this.kilometragemTotal/DIST_VISTORIA_FURGAO) * PRECO_VISTORIA_FURGAO);
        this.custoVariavel = alinhamento + vistoria + this.custosExtra;
    }
}
