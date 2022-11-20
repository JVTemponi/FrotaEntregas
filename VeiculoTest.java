import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class VeiculoTest {

    static Veiculo veiculo;

    @BeforeAll
    public static void Testinit() {
        veiculo = new Carro("CAR-001", 180);
    }
    

    @Test
    public void TesteCustosFixos(){
        veiculo.calculaCustoFixo();
        assertEquals(316.2, veiculo.custoFixo);
    }

    @Test
    public void TesteCustosVariavel(){
        Frota frota = new Frota();
        Data dt = new Data(10, 10, 2020);
        Rota rota = new Rota(300, dt, veiculo);
        frota.addRota(rota, veiculo);
        veiculo.calculaCustoVariavel();
        assertEquals(240, veiculo.custoVariavel);
    }

    @Test
    public void name() {
        veiculo.imprimeDadosVeiculoConsole();
    }
}