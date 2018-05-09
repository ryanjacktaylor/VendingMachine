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
}
