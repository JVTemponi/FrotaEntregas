public class Rota {
    private static int sequencia = 1;
    private int idRota = 0;
    private String descHash;
    private Data dtProducao;
    private double distancia = 0;
    private Veiculo veiculoRota;

    /**
     * Método construtor da classe Rota, responsável por inicializar uma instância
     * de rota e atualizar a kilometragem total do veículo vinculado.
     * @param distancia double com a Distância da rota
     * @param dtProducao objeto da classe Data, informando a data em que ocorreu a rota
     * @param veiculoParaRota objeto da classe Veiculo, informando o veículo atrelado à rota
     */
    public Rota(double distancia, Data dtProducao, Veiculo veiculoParaRota) {
        this.idRota = sequencia++;
        this.distancia = distancia;
        this.dtProducao = dtProducao;
        this.veiculoRota = veiculoParaRota;
        this.veiculoRota.kilometragemTotal += distancia;
        this.veiculoRota.calculaCustoVariavel();
        concatenarHash();
    }

    /**
     * Método privado utilizado para criar uma string identificadora da rota. A string 
     * é a junção lógica das informações Data + Distância + Placa do Veículo 
     */
    private void concatenarHash(){
        String data, placa, dist;
        int intAno;
        intAno = Data.diaAno(this.dtProducao.getDia(), this.dtProducao.getMes(), this.dtProducao.getAno());
        data = String.valueOf(intAno);
        placa = this.veiculoRota.getPlaca();
        dist = String.valueOf(this.getDistancia());
        this.descHash = data.concat(dist).concat(placa);
    }

    /**
     * Método booleano para validar se existe uma rota entre as datas especificadas
     * no parâmetro
     * @param dtInicio objeto da classe Data, referente à data de início da busca
     * @param dtFinal objeto da classe Data, referente à data de fim da busca
     * @return Retorna true caso exista ou false caso contrário
     */
    public boolean verificaDataPeriodo(Data dtInicio, Data dtFinal) {
        return  Data.verificaDataPeriodo(dtProducao, dtInicio,  dtFinal);
    }

    /**
     * Método para imprimir rota, com as informações referentes ao id, e data em que ocorreu a rota
     */
    public void imprimeRota() {
        System.out.println("Rota número: " + this.idRota + " Data Produção da rota: " +
            this.dtProducao.getDiaSemana() + " " + this.dtProducao.getDia() + "/" + this.dtProducao.getMes()
            + "/" + this.dtProducao.getAno());
    }

    public double getDistancia() {
        return distancia;
    }

    public int getIdRota() {
        return idRota;
    }

    public Data getdtProducao() {
        return dtProducao;
    }

    public Veiculo getVeiculoRota() {
        return veiculoRota;
    }

    @Override
    public boolean equals(Object obj) {
        Rota outro = (Rota)obj;
        return this.descHash.equals(outro.descHash);
    }

    @Override
    public int hashCode() {
        return this.descHash.hashCode();
    }

    /**
     * Método retorna uma String com os dados de rota a serem escritos no arquivo txt
     * @return Retorna uma String com a distânica, data e placa do ceiículo vinculados
     * à rota
     */
    public String escreveRotaArquivo() {
        String salvaParaArquivo = 
                + this.distancia + ";"
                + this.dtProducao.getDia() + "/" + this.dtProducao.getMes() + "/" + this.dtProducao.getAno()+ ";"
                + this.veiculoRota.getPlaca();
        return salvaParaArquivo;
    }
}