public class FabricaFurgao implements IFabrica{
    @Override
    public Veiculo criar(String placa, double valorVenda){
        return new Furgao(placa, valorVenda);
    }
}
