import javax.management.InvalidAttributeValueException;

public class FabricaVeiculo {
    static Veiculo criarVeiculo(String tipo, String placa, double valorVenda) throws InvalidAttributeValueException{
        switch (tipo.toUpperCase()){
            case "1": FabricaCarro car = new FabricaCarro();
                return car.criar(placa, valorVenda);
            case "2": FabricaVan van = new FabricaVan();
                return van.criar(placa, valorVenda);
            case "3": FabricaFurgao fur = new FabricaFurgao();
                return fur.criar(placa, valorVenda);
            case "4": FabricaCaminhao cam = new FabricaCaminhao();
                return cam.criar(placa, valorVenda);
            default: throw new InvalidAttributeValueException("Tipo de Veiculo inexistente");
        }
    } 
}
