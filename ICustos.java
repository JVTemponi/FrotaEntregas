public interface ICustos {
    /**
     * Método calcula custo fixo (IPVA + Seguro + Taxa)
     */
    public void calculaCustoFixo();
    /**
     * Método calcula custo variável (Outros Custos + Custos extras)
     */
    public void calculaCustoVariavel();
}
