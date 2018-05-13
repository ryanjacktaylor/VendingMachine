import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

public class VendingMachine {

    //Display Constants
    private static final String INSERT_COIN_TEXT = "INSERT COIN";
    private static final String THANK_YOU_TEXT = "THANK YOU";
    private static final String PRICE_TEXT = "PRICE";
    private static final int DISPLAY_DELAY_SECONDS = 5;

    //Product Constants
    private static final String COLA_PRODUCT_NAME = "cola";
    private static final String CHIPS_PRODUCT_NAME = "chips";
    private static final String CANDY_PRODUCT_NAME = "candy";
    private static final int COLA_COST_IN_CENTS = 100;
    private static final int CHIPS_COST_IN_CENTS = 50;
    private static final int CANDY_COST_IN_CENTS = 65;

    //Vending Machine Parts
    private String mDisplay;
    private CoinAcceptor mCoinAcceptor;

    //User Credit
    private static int sUserCreditInCents;

    //Products
    private Product[] products = {new Product(COLA_PRODUCT_NAME, COLA_COST_IN_CENTS),
            new Product(CHIPS_PRODUCT_NAME, CHIPS_COST_IN_CENTS),
            new Product(CANDY_PRODUCT_NAME, CANDY_COST_IN_CENTS)};

    public VendingMachine(){

        //Initialize Display
        mDisplay = INSERT_COIN_TEXT;

        //Init user credit
        sUserCreditInCents = 0;

        //Create the coin acceptor
        mCoinAcceptor = new CoinAcceptor();
        mCoinAcceptor.setOnCoinReceivedListener(new CoinAcceptor.OnCoinReceivedListener() {
            @Override
            public void onCoinReceived(int coinValue) {
                sUserCreditInCents+=coinValue;

                //If there is a value, display it.  If it's 0, show INSERT COIN
                resetDisplay();
            }
        });
    }

    public String getDisplay(){
        return mDisplay;
    }

    public void insertCoin(float diameter, float weight){
        mCoinAcceptor.coinInserted(diameter, weight);
    }

    public ProductRequestResponse requestProduct(String productName){

        ProductRequestResponse response = new ProductRequestResponse();

        //Find the product in our array
        for (Product product:products){
            if (product.getName().equals(productName)){

                //Product found.  Check price.
                if (sUserCreditInCents >= product.getPriceInCents()){

                    //There's enough money.  Dispense product.
                    response.setProductName(product.getName());
                    response.setProductDispensed(true);

                    //Dispense Change
                    response.setChange(mCoinAcceptor.calculateChange(sUserCreditInCents-product.getPriceInCents()));

                    //Set the user credit to 0
                    sUserCreditInCents = 0;

                    //Set the display to THANK YOU
                    mDisplay = THANK_YOU_TEXT;

                    //Kick off a thread to change the display to INSERT COIN after a short delay
                    resetDisplayAfterDelay(DISPLAY_DELAY_SECONDS);

                } else {

                    //Set the display to THANK YOU
                    mDisplay = PRICE_TEXT;

                    //Kick off a thread to change the display to INSERT COIN after a short delay
                    resetDisplayAfterDelay(DISPLAY_DELAY_SECONDS);
                }

            }
        }
        return response;
    }

    public Change selectCoinReturn(){

        //Calculate change
        Change change =  mCoinAcceptor.calculateChange(sUserCreditInCents);

        //Reset user credit
        sUserCreditInCents = 0;

        //Reset the display
        resetDisplay();

        return change;

    }

    private void resetDisplayAfterDelay(int timeDelay){

        if (timeDelay>0) {

            new Thread(() -> {

                //Delay
                try {
                    TimeUnit.SECONDS.sleep(timeDelay);
                } catch (InterruptedException ex) {
                    //Doesn't really matter.  We'll set back to default either way
                }

                //Reset the display
               resetDisplay();
            }).start();
        }
    }

    private void resetDisplay(){
        if (sUserCreditInCents > 0) {
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            mDisplay = formatter.format((float) sUserCreditInCents / 100f);
        } else {
            mDisplay = INSERT_COIN_TEXT;
        }
    }
}
