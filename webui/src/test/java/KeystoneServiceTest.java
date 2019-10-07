import com.might.instance_controller.services.KeystoneService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest("KeystoneServiceTest")
//@RunWith(SpringJUnit4ClassRunner.class)
//@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class KeystoneServiceTest {

    @Autowired
    private KeystoneService keystoneService;

    @Test
    public void CanAutorize(){
//        Assert.assertTrue(keystoneService.authenticate() != null);
    }

}
