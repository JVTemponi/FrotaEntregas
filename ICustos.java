public interface ICustos {
    /**
     * Método calcula custo fixo (IPVA + Seguro + Taxa)
     */
    public void calculaCustoFixo();
    /**
     * Método calcula custo variável (Custo combustivel + outros Custos + Custos extras)
     */
    public void calculaCustoVariavel();
}
