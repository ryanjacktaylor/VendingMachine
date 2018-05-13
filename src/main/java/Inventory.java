import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private class InventoryItem{
        String productName;
        int count;

        InventoryItem(String productName){
            this.productName = productName;
            this.count = 0;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    private List<InventoryItem> items;

    public Inventory(Product[] products){
        items = new ArrayList<>();
        for (Product product:products){
            InventoryItem item = new InventoryItem(product.getName());
            items.add(item);
        }
    }

    public void addProductCount(String productName, int count){

        //Find the product in inventory and add to the count
        for (InventoryItem item:items){
            if (item.getProductName().equals(productName)){
                item.count+=count;
                break;
            }
        }
    }

    public void decrementProductCount(String productName){

        //Find the product in inventory and decrement by 1
        for (InventoryItem item:items){
            if (item.getProductName().equals(productName)){
                item.count--;
                break;
            }
        }
    }

    public int getProductCount(String productName){

        int count = 0;

        //Find the product in inventory and add to the count
        for (InventoryItem item:items){
            if (item.getProductName().equals(productName)){
                count = item.count;
                break;
            }
        }

        return count;
    }
}
