public class Tanque {
    private Combustivel combustivel;
    private double capacidade;
    private double litragemAtual;

    /**
     * Método construtor da classe Tanque
     * @param capacidade Capacidade em litros do tanque, valor double
     */
    public Tanque(double capacidade) {
        this.capacidade = capacidade;
        this.litragemAtual = 0;
    }   

    /**
     * Método responsável por completar o tanque, de forma que a litragem atual atinja
     * a litragem máxima
     * @param combustivel Tipo de combustível que o tanque será reabastecido
     * @return Retorna um valor double com a quantidade de litros adicionados
     */
    public double completarTanque(Combustivel combustivel) {
        double litrosAdicionados = (this.capacidade - this.litragemAtual);
        this.litragemAtual = capacidade;
        this.combustivel = combustivel;
        return litrosAdicionados;
    }

    /**
     * Método reduz a litragem atual do tanque com base na distância recebida por 
     * parâmetro, levando em consideração o combustivel atual utilizado
     * @param distancia Double com a distancia percorrida a ser deduzida em litros do tanque
     */
    public void reduzirTanque(double distancia) {
        this.litragemAtual -= (distancia / combustivel.consumo);
    }

    public Combustivel getCombustivel() {
        return combustivel;
    }

    public double getCapacidade() {
        return capacidade;
    }
}
