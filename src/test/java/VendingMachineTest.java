import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;

public class VendingMachineTest {

    //Display Constants
    private static final String INSERT_COIN_TEXT = "INSERT COIN";
    private static final String THANK_YOU_TEXT = "THANK YOU";
    private static final String PRICE_TEXT = "PRICE";

    //Coin Diameter/Weight constants
    private static final float QUARTER_DIA_MM = 24.26f;
    private static final float QUARTER_WEIGHT_G = 6f;
    private static final float DIME_DIA_MM = 17.91f;
    private static final float DIME_WEIGHT_G = 2.268f;
    private static final float NICKEL_DIA_MM = 21.21f;
    private static final float NICKEL_WEIGHT_G = 5f;

    //Product Constants
    private static final String COLA_PRODUCT_NAME = "cola";
    private static final String CHIPS_PRODUCT_NAME = "chips";
    private static final String CANDY_PRODUCT_NAME = "candy";

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
        vm.insertCoin(QUARTER_DIA_MM, QUARTER_WEIGHT_G);
        vm.insertCoin(DIME_DIA_MM, DIME_WEIGHT_G);
        vm.insertCoin(NICKEL_DIA_MM, NICKEL_WEIGHT_G);
        ProductRequestResponse candyResponse = vm.requestProduct(CANDY_PRODUCT_NAME);
        assertEquals(candyResponse.getProductName(), CANDY_PRODUCT_NAME);
        assertEquals(candyResponse.isProductDispensed(), true);
    }

    @Test
    public void whenAProductIsDispensedVerifyTheDisplayShowsThankYouFollowedByInsertCoin(){

        //Create the vending machine
        VendingMachine vm = new VendingMachine();

        //Add Money and select Cola
        for (int i = 0; i<4; i++) {
            vm.insertCoin(QUARTER_DIA_MM, QUARTER_WEIGHT_G);
        }
        ProductRequestResponse colaResponse = vm.requestProduct(COLA_PRODUCT_NAME);

        //Immediately check the display
        assertEquals(THANK_YOU_TEXT, vm.getDisplay());

        //Check the display again after 6 seconds
        try {
            TimeUnit.SECONDS.sleep(6);
        } catch(InterruptedException ex) {
            //Fail if there's an exception
            assertEquals(true, false);
        }
        assertEquals(INSERT_COIN_TEXT, vm.getDisplay());
    }

    @Test
    public void whenAProductIsSelectedWithInsufficientFundsTheDisplayShowsPriceThenFunds(){

        //Create the vending machine
        VendingMachine vm = new VendingMachine();

        //Add Money and select Cola
        vm.insertCoin(QUARTER_DIA_MM, QUARTER_WEIGHT_G);
        ProductRequestResponse colaResponse = vm.requestProduct(COLA_PRODUCT_NAME);

        //Immediately check the display
        assertEquals(PRICE_TEXT, vm.getDisplay());

        //Check the display again after 6 seconds
        try {
            TimeUnit.SECONDS.sleep(6);
        } catch(InterruptedException ex) {
            //Fail if there's an exception
            assertEquals(true, false);
        }
        assertEquals("$0.25", vm.getDisplay());
    }

    @Test
    public void whenProductIsSelectedWithTooMuchMoneyTheMachineDispensesChange(){

        //Create the vending machine
        VendingMachine vm = new VendingMachine();

        //Add $1.40.  This will require the machine to dispense one of each coin
        for (int i = 0; i<5; i++) {
            vm.insertCoin(QUARTER_DIA_MM, QUARTER_WEIGHT_G);
        }
        vm.insertCoin(DIME_DIA_MM, DIME_WEIGHT_G);
        vm.insertCoin(NICKEL_DIA_MM, NICKEL_WEIGHT_G);
        ProductRequestResponse colaResponse = vm.requestProduct(COLA_PRODUCT_NAME);

        //Check that the product was dispensed
        assertEquals(true, colaResponse.isProductDispensed());

        //Check the change dispensed.  Should be one of each coin
        Change change = colaResponse.getChange();
        assertEquals(1, change.getQuarters());
        assertEquals(1, change.getDimes());
        assertEquals(1, change.getNickels());
    }
}
