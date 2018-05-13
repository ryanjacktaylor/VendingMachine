public class CoinAcceptor {

    //Coin constants
    //Note: Weight/Diameter values were determined via wikipedia, with 5% tolerance added to
    //prevent rejections for minor variations (dirt, minor damage, etc)
    private static int NUMBER_OF_COINS_ACCEPTED = 3;  //Quarter, Dime, Nickel
    private static final float COIN_TOLERANCE = 0.05f;
    private static final float QUARTER_DIA_MIN_MM = 24.26f * (1f-COIN_TOLERANCE);
    private static final float QUARTER_DIA_MAX_MM = 24.26f * (1f+COIN_TOLERANCE);
    private static final float QUARTER_WEIGHT_MIN_G = 5.67f * (1f-COIN_TOLERANCE);
    private static final float QUARTER_WEIGHT_MAX_G = 6.25f * (1f+COIN_TOLERANCE);
    private static final int   QUARTER_VALUE_CENTS = 25;
    private static final float DIME_DIA_MIN_MM = 17.91f * (1f-COIN_TOLERANCE);
    private static final float DIME_DIA_MAX_MM = 17.91f * (1f+COIN_TOLERANCE);
    private static final float DIME_WEIGHT_MIN_G = 2.268f * (1f-COIN_TOLERANCE);
    private static final float DIME_WEIGHT_MAX_G = 2.268f * (1f+COIN_TOLERANCE);
    private static final int   DIME_VALUE_CENTS = 10;
    private static final float NICKEL_DIA_MIN_MM = 21.21f * (1f-COIN_TOLERANCE);
    private static final float NICKEL_DIA_MAX_MM = 21.21f * (1f+COIN_TOLERANCE);
    private static final float NICKEL_WEIGHT_MIN_G = 5f * (1f-COIN_TOLERANCE);
    private static final float NICKEL_WEIGHT_MAX_G = 5f * (1f+COIN_TOLERANCE);
    private static final int   NICKEL_VALUE_CENTS = 5;

    //Coin inventory
    private int quarters;
    private int dimes;
    private int nickels;

    //Interface to coin acceptor
    public interface OnCoinReceivedListener{
        void onCoinReceived(int coinValue);
    }

    //Variables
    private OnCoinReceivedListener coinAcceptorInterface;

    public void coinInserted(float diameter, float weight){

        int coinValue = 0;

        //Arrays for processing
        float[] coinDiaMins = {QUARTER_DIA_MIN_MM, DIME_DIA_MIN_MM, NICKEL_DIA_MIN_MM};
        float[] coinDiaMaxs = {QUARTER_DIA_MAX_MM, DIME_DIA_MAX_MM, NICKEL_DIA_MAX_MM};
        float[] coinWeightMins = {QUARTER_WEIGHT_MIN_G, DIME_WEIGHT_MIN_G, NICKEL_WEIGHT_MIN_G};
        float[] coinWeightMaxs = {QUARTER_WEIGHT_MAX_G, DIME_WEIGHT_MAX_G, NICKEL_WEIGHT_MAX_G};
        int[] coinValues = {QUARTER_VALUE_CENTS, DIME_VALUE_CENTS, NICKEL_VALUE_CENTS};

        for (int i=0; i<NUMBER_OF_COINS_ACCEPTED; i++){
            if (diameter >= coinDiaMins[i] &&
                    diameter <= coinDiaMaxs[i] &&
                    weight >= coinWeightMins[i]&&
                    weight <= coinWeightMaxs[i]){
                coinValue = coinValues[i];
                break;
            }
        }

        coinAcceptorInterface.onCoinReceived(coinValue);
    }


    public void setOnCoinReceivedListener(OnCoinReceivedListener coinAcceptorInterface){
        this.coinAcceptorInterface = coinAcceptorInterface;
    }

    public Change calculateChange(int valueInCents, boolean dispense){
        Change change = new Change();

        //Start with the largest coin and work your way down
        //quarters
        change.setQuarters(valueInCents/QUARTER_VALUE_CENTS);

        //Dimes
        int dimesValue = valueInCents-(valueInCents/QUARTER_VALUE_CENTS)*QUARTER_VALUE_CENTS;
        change.setDimes(dimesValue/DIME_VALUE_CENTS);

        //Nickels
        int nickelsValue = dimesValue-(dimesValue/DIME_VALUE_CENTS)*DIME_VALUE_CENTS;
        change.setNickels(nickelsValue/NICKEL_VALUE_CENTS);

        //Check if we have enough coins to make the change
        //Since quarter is our largest denomination, we should never have to dispense one that we don't have
        //We should only be dispensing a maximum of 2 dimes or 4 nickels, or some combination of the two.
        if (change.getDimes() > dimes){
            //Not enough dimes.  Can we do it all with nickels?
            if (dimesValue/NICKEL_VALUE_CENTS > nickelsValue) {
                change.setInsufficentCoins(true);
            }
        }
        if (change.getNickels() > nickels){
            change.setInsufficentCoins(true);
        }

        //Subtract from inventory if dispensing
        if (dispense){
            quarters-=change.getQuarters();
            dimes-=change.getDimes();
            nickels-=change.getNickels();
        }

        return change;
    }

    public boolean isExactChangeOnly(int[] productValues){

        for (int value:productValues){

            //Quarter is the largest value we can accept, so let's see if we can make change with all quarters
            Change change = calculateChange(value%QUARTER_VALUE_CENTS, false);

            if (change.isInsufficentCoins()){
                return true;
            }
        }

        return false;
    }

    public void setQuarters(int quarters) {
        this.quarters = quarters;
    }

    public void setDimes(int dimes) {
        this.dimes = dimes;
    }

    public void setNickels(int nickels) {
        this.nickels = nickels;
    }
}
