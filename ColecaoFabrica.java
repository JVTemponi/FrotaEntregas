import java.util.HashMap;

public class ColecaoFabrica {
    private HashMap<String, IFabrica> catalogoVeiculos;

    public ColecaoFabrica(){
        catalogoVeiculos = new HashMap<>();
    }

    public void acrescentarFabrica(String veiculo, IFabrica fabrica){
        if(veiculo!="" && fabrica!=null)
            this.catalogoVeiculos.put(veiculo.toUpperCase(), fabrica);
    }

    public Veiculo criar(String tipo, String placa, double valorVenda){
        IFabrica fabrica = catalogoVeiculos.get(tipo.toUpperCase());
        return fabrica.criar(placa, valorVenda);
    }

}