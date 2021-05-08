import java.io.IOException;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ElectronicStore implements java.io.Serializable {
    private String name;
    private ArrayList<Product> productList;
    private ArrayList<Customer> customerList;
    private double revenue;

    public ElectronicStore(String name){
        this.name = name;
        this.productList = new ArrayList<Product>();
        this.customerList = new ArrayList<Customer>();
        this.revenue = 0;
    }
    public ArrayList<Customer> getCustomers(){ return customerList; }


    public boolean addProduct(Product product){
        //Loops looking to find if product already exists...
        for(Product p : productList){
            if(p.toString().equals(product.toString())){
                return false; //if products already exists, the method returns false to avoid duplicates...
            }
        }
        //otherwise product is added to product list
        productList.add(product);
        return true;
    }

    //method works in similar manner to addProduct method
    public boolean registerCustomer(Customer customer){
        for(Customer c: customerList){
            if(c.toString().equals(customer.toString())){
                return false;
            }
        }
        customerList.add(customer);
        return true;
    }

    public List<Product> searchProducts(String x){
        return searchProducts(x,-1,-1);
    }

    public List<Product> searchProducts(String x, double minPrice, double maxPrice){
        ArrayList<Product> foundProducts = new ArrayList<Product>();

        if((minPrice < 0) && (maxPrice < 0)){
            for(Product p : productList){
                if(p.toString().toLowerCase().contains(x.toLowerCase())){
                    foundProducts.add(p);
                }
            }
        }
        else if(minPrice < 0){
            for(Product p : productList){
                if((p.toString().toLowerCase().contains(x.toLowerCase()) && (p.getPrice() <= maxPrice))){
                    foundProducts.add(p);
                }
            }
        }
        else if(maxPrice < 0){
            for(Product p : productList){
                if((p.toString().toLowerCase().contains(x.toLowerCase()) && (p.getPrice() >= minPrice))){
                    foundProducts.add(p);
                }
            }
        }
        else{
            for(Product p : productList){
                if((p.toString().toLowerCase().contains(x.toLowerCase()) && (p.getPrice() <= maxPrice) && (p.getPrice() >= minPrice))){
                    foundProducts.add(p);
                }
            }
        }
        return foundProducts;
    }

    public boolean addStock(Product p, int amount){
        if(productList.contains(p)){
            p.addStockQuantity(amount);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean sellProduct(Product p, Customer c, int amount){
        double spending;
        if(productList.contains(p) && customerList.contains(c) && p.isEnoughStock(amount)){

            spending = p.sellUnits(amount);
            c.spendMoney(spending);
            revenue += spending;

            //add purchased products to customers purchased products map
            c.addPurchasedProduct(p, amount);
            return true;
        }
        else{
            return false;
        }
    }

    public List<Customer> getTopXCustomers(int x){
        ArrayList<Customer> sortedCustomers = new ArrayList<Customer>(customerList);
        int sizeofList;

        if(x <= 0){
            sizeofList = 0;
        }
        else if(x > customerList.size()){
            sizeofList = customerList.size();
        }
        else{
            sizeofList = x;
        }

        Collections.sort(sortedCustomers);

        //Once list of customers is sorted by money spent, top X customers can be returned by returning sublist of desired size
        return (sortedCustomers.subList(0, sizeofList));
    }

    public boolean saveToFile(String filename){
        try{
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(this);
            out.close();
            return true;
        } catch(IOException e){
            return false;
        }
    }

    public static ElectronicStore loadFromFile(String filename){
        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
            ElectronicStore loadedStore = (ElectronicStore)(in.readObject());
            in.close();
            return loadedStore;
        } catch(ClassNotFoundException | IOException e){
            return null;
        }
    }

}
