
public class Caminhao extends Veiculo{
    private final int TANQUE_COMPLETO = 250;
    private final double DIST_VISTORIA_CAMINHAO = 30000d;
    private final double PRECO_VISTORIA_CAMINHAO = 1000d;
    private final double DIST_MANUTENCAO_CAMINHAO = 20000d;
    private final double PRECO_MANUTENCAO_CAMINHAO = 1000d;
    private final double TAXA_CAMINHAO = 2000d;

    /**
     * Método construtor da classe Caminhao.
     * @param placa String constendo a placa do veículo
     * @param valorVenda double contendo o valor de venda do veículo
     */
    public Caminhao(String placa, double valorVenda) {
        super(placa, valorVenda);
        this.tiposCombustivel.add(Combustivel.DIESEL);
        this.tanque = new Tanque(TANQUE_COMPLETO);
        this.tanque.completarTanque(Combustivel.DIESEL);
        this.autonomiaMaxima = getAutonomiaMaxima();
        this.autonomiaAtual =  this.autonomiaMaxima;
        
    }

    @Override
    public void calculaCustoFixo() {
        double ipva = (this.valorVenda * Tarifas.CAMINHAO.getIpva());
        double seguro = (this.valorVenda * Tarifas.CAMINHAO.getSeguro()) + TAXA_CAMINHAO;
        this.custoFixo = ipva + seguro;
    }

    @Override
    public void calculaCustoVariavel() {
        double manutencao = ((int)(this.kilometragemTotal/DIST_MANUTENCAO_CAMINHAO) * PRECO_MANUTENCAO_CAMINHAO);
        double vistoria = ((int)(this.kilometragemTotal/DIST_VISTORIA_CAMINHAO) * PRECO_VISTORIA_CAMINHAO);
        this.custoVariavel = manutencao + vistoria + this.custosExtra;
    }
}
