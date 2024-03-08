package Test;

import Domain.IEntityFactory;
import Domain.Masina;
import Domain.MasinaFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.jupiter.api.Test;

public class MasinaFactoryTest {

    @Test
    public void testCreateEntity() {
        IEntityFactory<Masina> masinaFactory = new MasinaFactory();
        String inputLine = "1,Ford,Fiesta";
        Masina masina = masinaFactory.createEntity(inputLine);
        assertNotNull(masina);
        assertEquals(1, masina.getId());
        assertEquals("Ford", masina.getMarca());
        assertEquals("Fiesta", masina.getModel());
    }

}