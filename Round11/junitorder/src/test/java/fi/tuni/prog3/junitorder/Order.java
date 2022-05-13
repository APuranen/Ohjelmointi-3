package fi.tuni.prog3.junitorder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;

public class Order {
    public static class Item extends Order {

        private String name;
        private double price;

        public Item(String name, double price) throws IllegalArgumentException{

            if( name == null || price < 0){
                throw new IllegalArgumentException();
            }

            this.name = name;
            this.price = price;
        }

        public String getName(){return this.name;}
        public double getPrice(){return this.price;}
        
        public String toString(){
            String message = String.format("Item(%s, %.2f)", this.name, this.price);
            return message;
        }

        public boolean equals(Item other){
            if(this.name.equals(other.name)){return true;}
            return false;
        }
    }

    public static class Entry extends Order {

        private String name;
        private double price;
        private int amount;
        private Item item;

        public Entry(Item item, int count) throws IllegalArgumentException{
            if( count < 0 ) throw new IllegalArgumentException();
            else{
                this.name = item.name;
                this.price = item.price;
                this.amount = count;
                this.item = item;
            }
        }
        
        public String getItemName(){return this.name;}
        public double getUnitPrice(){return this.price;}
        public Item getItem(){return item;}
        public int getCount(){return this.amount;}
        public String toString(){
            String message = String.format("%d units of item", amount);
            return message;
        }

    }

    private TreeMap<Item, Integer> amounts;
    private List<Entry> entries;
    private List<Item> items;

    public Order(){
        this.amounts = new TreeMap<Item, Integer>();
        this.entries = new ArrayList<Entry>();
        this.items = new ArrayList<Item>();
    }

    public boolean addItems(Item item, int count) throws IllegalArgumentException, IllegalStateException{

        if( count < 0) throw new IllegalArgumentException();

        Entry input = new Entry(item, count);
        this.entries.add(input);

        if( this.amounts.containsKey(item) ) {
            Set<Map.Entry<Item, Integer>> entrySet = amounts.entrySet();
            for( Map.Entry<Item, Integer> entry : entrySet ){
                Item currentItem = entry.getKey();
                if( currentItem.name.equals(item.name) ){
                    if( currentItem.price != item.price) throw new IllegalStateException();
                    else{
                        int current = amounts.get(item);
                        current += count;
                        this.amounts.replace(item, current);
                        return true;
                    }
                }
            }     
        }
        else{
            this.amounts.put(item, count);
            this.items.add(item);
            
            return true;
        }
        return false;
    }
    
    public boolean addItems(String name, int count) throws IllegalArgumentException, NoSuchElementException{

        if (count < 0) throw new IllegalArgumentException();

        Set<Map.Entry<Item, Integer>> entrySet = amounts.entrySet();
        for ( Map.Entry<Item, Integer> entry : entrySet) {
            Item item = entry.getKey();
            if( item.name.equals(name)){

                Entry input = new Entry(item, count);
                this.entries.add(input);

                int current = amounts.get(item);
                current += count;
                this.amounts.replace(item, current);
                return true;
            }
        }
        throw new NoSuchElementException();
    }
    
    public List<Entry> getEntries(){return entries;}
    
    public int getEntryCount(){return items.size();}
    
    public int getItemCount(){
        int totalAmount = 0;

        Set<Map.Entry<Item, Integer>> entrySet = amounts.entrySet();
        for ( Map.Entry<Item, Integer> entry : entrySet) {
            totalAmount += entry.getValue();
        }

        return totalAmount;
    }
    
    public double getTotalPrice(){
        double totalPrice = 0;

        Set<Map.Entry<Item, Integer>> entrySet = amounts.entrySet();
        for ( Map.Entry<Item, Integer> entry : entrySet) {
            Item item = entry.getKey();
            double price = item.getPrice();
            totalPrice += entry.getValue() * price;
        }

        return totalPrice;
    }
    
    public boolean isEmpty(){ 
        if( amounts.isEmpty() ) return true;
        return false;
    }
    
    public boolean removeItems(String name, int count) throws IllegalArgumentException, NoSuchElementException{
        return false;
    }
}