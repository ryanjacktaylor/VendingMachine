public class ProductRequestResponse {

    private String productName;
    private boolean productDispensed;
    private Change change;

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

    public Change getChange() {
        return change;
    }

    public void setChange(Change change) {
        this.change = change;
    }

}
