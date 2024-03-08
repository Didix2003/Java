package Test;

import Domain.Masina;
import org.junit.jupiter.api.Test;
public class MasinaTest {
    @Test
    public void testMasina() {
        Masina c = new Masina(10, "orice", "orice");
        assert c.getId() == 10;
        assert "orice".equals(c.getMarca());
        assert "orice".equals(c.getModel());
    }
}
