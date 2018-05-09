public class VendingMachine {

    //Display Constants
    private static final String INSERT_COIN_TEXT = "INSERT COIN";

    //Vending Machine Parts
    private String mDisplay;

    public VendingMachine(){

        //Initialize Display
        mDisplay = INSERT_COIN_TEXT;
    }

    public String getDisplay(){
        return mDisplay;
    }
}
