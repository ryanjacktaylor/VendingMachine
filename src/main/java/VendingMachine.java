public class VendingMachine {

    //Display Constants
    private static final String INSERT_COIN_TEXT = "INSERT COIN";

    //Vending Machine Parts
    private String mDisplay;
    private CoinAcceptor mCoinAcceptor;

    //User Credit
    private static int sUserCreditInCents;

    public VendingMachine(){

        //Initialize Display
        mDisplay = INSERT_COIN_TEXT;

        //Create the coin acceptor
        mCoinAcceptor = new CoinAcceptor();
        mCoinAcceptor.setOnCoinReceivedListener(new CoinAcceptor.OnCoinReceivedListener() {
            @Override
            public void onCoinReceived(int coinValue) {
                sUserCreditInCents+=coinValue;
                mDisplay = "$" + String.valueOf(sUserCreditInCents/100) + "." + String.valueOf(sUserCreditInCents%100);
            }
        });
    }

    public String getDisplay(){
        return mDisplay;
    }

    public void insertCoin(float diameter, float weight){
        mCoinAcceptor.coinInserted(diameter, weight);
    }
}
