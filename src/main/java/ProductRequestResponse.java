public class ProductRequestResponse {

    private String productName;
    private boolean productDispensed;
    private boolean changeDispensed;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public boolean isProductDispensed() {
        return productDispensed;
    }

    public void setProductDispensed(boolean productDispensed) {
        this.productDispensed = productDispensed;
    }

}
