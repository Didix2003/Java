package Test;

import Domain.IEntityFactory;
import Domain.Inchiriere;
import Domain.InchiriereFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.jupiter.api.Test;

public class InchiriereFactoryTest {
    @Test
    public void testMasinaFactory() {
        IEntityFactory<Inchiriere> inchiriereFactory = new InchiriereFactory();
        String inputLine = "1,123,01/01/2023,10/01/2023";
        Inchiriere inchiriere = inchiriereFactory.createEntity(inputLine);
        assertNotNull(inchiriere);
        assertEquals(1, inchiriere.getId());
        assertEquals(123, inchiriere.getIdmasina());
        assertEquals("01/01/2023", inchiriere.getData_inceput());
        assertEquals("10/01/2023", inchiriere.getData_final());
    }
}
