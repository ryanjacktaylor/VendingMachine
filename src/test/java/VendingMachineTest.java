import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class VendingMachineTest {

    //Display Constants
    private static final String INSERT_COIN_TEXT = "INSERT COIN";

    //Coin Diameter/Weight constants
    private static final float QUARTER_DIA_MM = 24.26f;
    private static final float QUARTER_WEIGHT_G = 6f;
    private static final float DIME_DIA_MM = 17.91f;
    private static final float DIME_WEIGHT_G = 2.268f;
    private static final float NICKEL_DIA_MM = 21.21f;
    private static final float NICKEL_WEIGHT_G = 5f;

    @Test
    public void whenThereIsNoMoneyInTheVendingMachineTheDisplaySaysINSERTCOIN(){

        //Create the vending machine
        VendingMachine vm = new VendingMachine();

        //Check the display
        assertEquals(vm.getDisplay(), INSERT_COIN_TEXT);
    }

    @Test
    public void whenVendingMachineIsPassedAValidWeightAndDiameterItAddsTheCoinValueToTheUserCredit(){
        //Create the vending machine
        VendingMachine vm = new VendingMachine();

        //Insert quarter and check the user credit
        vm.insertCoin(QUARTER_DIA_MM, QUARTER_WEIGHT_G);
        assertEquals("$0.25",vm.getDisplay());

        //Insert dime and check the user credit
        vm.insertCoin(DIME_DIA_MM, DIME_WEIGHT_G);
        assertEquals("$0.35",vm.getDisplay());

        //Insert nickel and check the user credit
        vm.insertCoin(NICKEL_DIA_MM, NICKEL_WEIGHT_G);
        assertEquals("$0.40",vm.getDisplay());

    }
}
