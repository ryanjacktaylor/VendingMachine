import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class VendingMachineTest {

    //Display Constants
    private static final String INSERT_COIN_TEXT = "INSERT COIN";

    @Test
    public void whenThereIsNoMoneyInTheVendingMachineTheDisplaySaysINSERTCOIN(){

        //Create the vending machine
        VendingMachine vm = new VendingMachine();

        //Check the display
        assertEquals(vm.getDisplay(), INSERT_COIN_TEXT);
    }
}
