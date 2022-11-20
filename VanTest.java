import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class VanTest {

    @Test
    public void TesteConstrutor() {
        Veiculo van = new Van("VAN-01", 131313);
        van.abastecer(1);
        assertEquals("Gasolina", van.tiposCombustivel.get(1).getDescricao().toString());
    }
/* 
    @Test
    public void TesteTiposCombustivel() {
        Veiculo van = new Van("VAN-01", 131313);
        van.exibirTiposCombustivel();
        assertEquals(2, van.getTanque());
    }
 */   
}
