public enum Tarifas {

    CARRO(0.04d,0.05d),
    VAN(0.03d, 0.03d),
    FURGAO(0.03d, 0.03d),
    CAMINHAO(0.01d,0.02d);

    double ipva;
    double seguro;

    Tarifas(double ipva, double seguro){
        this.ipva = ipva;
        this.seguro = seguro;
    }

    public double getIpva() {
        return ipva;
    }

    public double getSeguro() {
        return seguro;
    }
}
