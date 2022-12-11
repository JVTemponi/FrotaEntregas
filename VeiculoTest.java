import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class VeiculoTest {

    static Veiculo veiculo;

    @BeforeAll
    public static void Testinit() {
        veiculo = new Carro("CAR-001", 100000d);
    }
    
    @Test
    public void TesteCustosFixos(){
        assertEquals(316.2, veiculo.custoFixo);
    }

    @Test
    public void TesteCustosVariavel() throws Exception{
        Frota frota = new Frota();
        Data dt = new Data(10, 10, 2020);
        Rota rota = new Rota(300, dt, veiculo);
        frota.addRota(rota, veiculo);
        veiculo.calculaCustoVariavel();
        System.out.println(veiculo);
        assertEquals(240, veiculo.custoVariavel);
        
    }

    @Test
    public void testeToString() {
        System.out.println(veiculo);
    }
}