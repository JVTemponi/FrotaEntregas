public enum Combustivel {

    GASOLINA("GASOLINA",4.8d,12d),
    ALCOOL("ALCOOL",3.65d,8d),
    DIESEL("DIESEL",6.65d,3.5d);

    String descricao;
    double preco;
    double consumo;
    int identificador;

    Combustivel(String desc, double preco, double consumo){
        if(preco < 1){preco = 1d;}
        if(consumo < 1){consumo = 1d;}
        this.descricao = desc;
        this.preco = preco;
        this.consumo = consumo;
    }

    public double getPreco() {
        return preco;  
    }

    public double getConsumo() {
        return consumo;
    }

    public String getDescricao() {
        return descricao;
    }
}