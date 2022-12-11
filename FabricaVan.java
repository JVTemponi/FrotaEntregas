public class FabricaVan implements IFabrica{
    @Override
    public Veiculo criar(String placa, double valorVenda){
        return new Van(placa, valorVenda);
    }
}
