public abstract class Product implements java.io.Serializable {
    private double price;
    private int stockQuantity;
    private int soldQuantity;

    public Product(double price, int stockQuantity){
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.soldQuantity = 0;
    }

    public double getPrice(){ return this.price; }

    public boolean isEnoughStock(int amount){
        if(amount <= stockQuantity){
            return true;
        }
        else{
            return false;
        }
    }

    public double sellUnits(int amount){
            //Updating inventory
            if(stockQuantity >= amount){
                soldQuantity += amount;
                stockQuantity -= amount;
                return amount*price;
            }
            else{
                return 0;
            }
    }

    public void addStockQuantity(int amount){
        stockQuantity += amount;
    }

    public String toString(){
        return "("+price+" dollars each)";
    }

}
