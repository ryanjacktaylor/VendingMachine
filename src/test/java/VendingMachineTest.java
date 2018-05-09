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

    @Test
    public void whenVendingMachineIsPassedAnInvalidWeightAndDiameterItAddsNothingToTheUserCredit(){
        //Create the vending machine
        VendingMachine vm = new VendingMachine();

        //Insert penny and check the user credit
        vm.insertCoin(19.05f , 2.5f);
        assertEquals(INSERT_COIN_TEXT,vm.getDisplay());

        //Insert Chuck E Cheese Token and check the user credit
        vm.insertCoin(25f , 5f); //http://www.hrafnstead.org/tokens/cec/CEC.html
        assertEquals(INSERT_COIN_TEXT,vm.getDisplay());
    }

    @Test
    public void whenAProductIsSelectedAndChangeIsCorrectTheVendingMachineWillReturnTheProduct(){

        //Create the vending machine
        VendingMachine vm = new VendingMachine();

        //Add Money and select Cola
        for (int i = 0; i<4; i++) {
            vm.insertCoin(QUARTER_DIA_MM, QUARTER_WEIGHT_G);
        }
        ProductRequestResponse colaResponse = vm.requestProduct(COLA_PRODUCT_NAME);
        assertEquals(colaResponse.getProductName(), COLA_PRODUCT_NAME);
        assertEquals(colaResponse.isProductDispensed(), true);

        //Add Money and select Chips
        for (int i = 0; i<2; i++) {
            vm.insertCoin(QUARTER_DIA_MM, QUARTER_WEIGHT_G);
        }
        ProductRequestResponse chipsResponse = vm.requestProduct(CHIPS_PRODUCT_NAME);
        assertEquals(chipsResponse.getProductName(), CHIPS_PRODUCT_NAME);
        assertEquals(chipsResponse.isProductDispensed(), true);

        //Add Money and select Candy
        vm.insertCoin(QUARTER_DIA_MM, QUARTER_WEIGHT_G);
        vm.insertCoin(DIME_DIA_MM, DIME_WEIGHT_G);
        vm.insertCoin(NICKEL_DIA_MM, NICKEL_WEIGHT_G);
        ProductRequestResponse candyResponse = vm.requestProduct(CANDY_PRODUCT_NAME);
        assertEquals(candyResponse.getProductName(), CANDY_PRODUCT_NAME);
        assertEquals(candyResponse.isProductDispensed(), true);
    }
}
