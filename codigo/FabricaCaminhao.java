public class FabricaCaminhao implements IFabrica {
    @Override
    public Veiculo criar(String placa, double valorVenda){
        return new Caminhao(placa, valorVenda);
    }
}
