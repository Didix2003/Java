package Test;

import Domain.Inchiriere;
import Domain.Masina;
import org.junit.jupiter.api.Test;

public class InchiriereTest {
    @Test
    public void testInchiriere() {
        Inchiriere c = new Inchiriere(10,1, "23", "24");
        assert c.getId() == 10;
        assert c.getIdmasina() == 1;
        assert "23".equals(c.getData_inceput());
        assert "24".equals(c.getData_final());
    }
}
