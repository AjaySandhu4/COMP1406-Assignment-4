import java.util.HashMap;

public class Customer implements Comparable<Customer>, java.io.Serializable {
    private String name;
    private double spentMoney;
    private HashMap<Product, Integer> purchasedProductsMap;

    public Customer(String name){
        this.name = name;
        this.spentMoney = 0;
        this.purchasedProductsMap = new HashMap<Product, Integer>();
    }
    public String getName(){ return name; }

    public void spendMoney(double money){
        if(money > 0){
            spentMoney += money;
        }
    }

    public void printPurchaseHistory(){
        for(Product p : purchasedProductsMap.keySet()){
            System.out.println(purchasedProductsMap.get(p) + " x " + p);
        }
    }

    public void addPurchasedProduct(Product p, int amount){
        for (Product purchasedProduct : purchasedProductsMap.keySet()) {
            if (purchasedProduct.toString().equals(p.toString())) { //if product has already been purchased before, add amount to existing product
                purchasedProductsMap.put(purchasedProduct, purchasedProductsMap.get(purchasedProduct)+amount);
                return;
            }
        }
        purchasedProductsMap.put(p, amount); //if product has not already been purchased before, add product to hashmap
    }

    public String toString(){ return name+" who has spent $"+String.format("%.2f", spentMoney); }

    //compares customers based on money spent
    public int compareTo(Customer c){
        if(c.spentMoney < this.spentMoney) {
            return -1;
        }
        else if(c.spentMoney > this.spentMoney){
            return 1;
        }
        else{
            return 0;
        }
    }

}
