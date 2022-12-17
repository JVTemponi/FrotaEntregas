public class FabricaCarro implements IFabrica{
    @Override
    public Veiculo criar(String placa, double valorVenda){
        return new Carro(placa, valorVenda);
    }
}
