
package javaapplicationmy;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileWriter;

import java.io.FileInputStream;

 
public class BidAsk {

private class Order {
    public int price;
    public int size;
    public Order (int price, int size){
        this.price = price;
        this.size = size;
    }
}
 private final  MyArrayList<Order> bid = new MyArrayList(100000);
 private final  MyArrayList<Order> ask = new MyArrayList(100000);
 //   private final ArrayList<Order> bid = new ArrayList(100000000);
 //   private final ArrayList<Order> ask = new ArrayList(10000000);

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        var my = new BidAsk();
        String str, strng;
        String[] words = new String [4];
        int start;
        int index;
        String fileName = "input.txt";
        String outputFileName = "output.txt";
     //   String fileName = "e:\\input.txt";
     //   String outputFileName = "e:\\output.txt";
        

                    
        
           try (FileInputStream fis = new FileInputStream(fileName); BufferedWriter writter = new BufferedWriter(new FileWriter(outputFileName))) {
        
               final int BUFSIZE = 1024;
               byte buf[] = new byte[BUFSIZE];
               int len;
               index = 0;
               words[0]="";
               words[1]="";
               words[2]="";
               words[3]="";
               while ((len = fis.read(buf)) != -1) {
               for (int i = 0; i < len; i++) {
               if (buf[i] != '\n' && buf[i] != '\r' && buf[i] != '\0' && buf[i] != ',') {
               words[index]=words[index]+(char)buf[i];}
               else if (buf[i] == ',') {
                   index++;
               }
               else if (buf[i] == '\n') {
               
                   index=0;    
                 char ch = words[0].charAt(0);
                switch (ch) {
                    case 'u' -> {
                        if (words[3].compareTo("bid") == 0) {
                            my.bids(Integer.parseInt(words[1]), Integer.parseInt(words[2]));
                        }
                        else if (words[3].compareTo("ask") == 0) {
                            my.asks(Integer.parseInt(words[1]), Integer.parseInt(words[2]));
                        }
                    }
                    case 'q' -> {
                        if (words[1].compareTo("best_bid") == 0 && !my.bid.isEmpty()) {
                            str = String.join("", String.valueOf(my.bid.get(0).price), ",", String.valueOf(my.bid.get(0).size), "\n");
                          
                            writter.write(str);
                        }
                        else if (words[1].compareTo("best_ask") == 0 && !my.ask.isEmpty()) {
                            str = String.join("", String.valueOf(my.ask.get(0).price), ",", String.valueOf(my.ask.get(0).size), "\n");
                          
                            writter.write(str);
                        }
                        else if (words[1].compareTo("size") == 0) {
                            int size = my.find_size(Integer.parseInt(words[2]));
                            writter.write(String.join("", Integer.toString(size), "\n"));
                      
                        }
                    }
                    case 'o' -> {
                        if (words[1].compareTo("buy") == 0) {
                            my.buy(Integer.parseInt(words[2]));
                        }
                        else if (words[1].compareTo("sell") == 0) {
                            my.sell(Integer.parseInt(words[2]));
                        }
                    }
                }  
               words[0]="";
               words[1]="";
               words[2]="";
               words[3]="";
               }}
            }
        writter.close();
        }catch(IOException e){
           throw new RuntimeException(e);
        }
/////////////////////////////////////////////////////////////////////
        
        long time = System.currentTimeMillis() - startTime;
        System.out.println(time);  
    }
    
  
    public void bids(int price, int size) throws IOException {
        boolean flag;
        int first, last, pos;
        Order item = new Order(price, size);
        flag = false;
        
        if (bid.isEmpty() && size > 0) {
            bid.add(item);
        }
        else {
            first = 0;
            last = bid.size();
            pos = (first + last) / 2;            
            while (last - first > 0) {
                pos = (first + last) / 2;
                if (bid.get(pos).price <= price) {  
                    flag = true;
                    last = pos; 
                } else {
                first = pos + 1;    
                }
                
            }
            if (pos < (bid.size() - 1)) {
                if (bid.get(pos+1).price == price) {pos ++;}
            }
                    
            if (pos == 0 && size == 0 && bid.get(0).price == price) {
                        bid.remove(0);
 
                    }
            else if (price == bid.get(pos).price) {
                    bid.set(pos, item);        
                    } else if (flag == true && size > 0){
                        bid.add(pos, item);
                    }
          
            if (flag == false && size > 0) {
               bid.add(item);               
            }
        }
        item = null;
    }
    
    public void asks(int price, int size) throws IOException {
        boolean flag;
        int first, last, pos;
        flag = false;

        Order item = new Order(price, size);
        if (ask.isEmpty() && size > 0) {
            ask.add(item);
            
        }
        else {
            first = 0;
            last = ask.size();
            pos = (first + last) / 2;            
            while (last - first > 0) {
                pos = (first + last) / 2;
                if (ask.get(pos).price >= price) {  
                    flag = true;
                    last = pos; 
                } else {
                first = pos + 1;    
                }
                
            }
            if (pos < (ask.size() - 1)) {
                if (ask.get(pos).price < price) {pos ++;}
            }
            
                    
            if (pos == 0 && size == 0 && ask.get(0).price == price) {
                        ask.remove(0);
                    }
            else if (price == ask.get(pos).price) {
                    //flag = true;
                    ask.set(pos, item);        
                    } else if (flag == true && size > 0){
                        ask.add(pos, item);
                    }
            
            if (flag == false && size > 0) {
                ask.add(item);
                
            }
        }
       item = null;
    }
    
    public int find_size(int price) throws IOException {
        int size;
        size = 0;
        for (int i = 0, n = bid.size(); i < n; i++) {
            if (price == bid.get(i).price) {
               size = bid.get(i).size;
               break;
            }
        }        
        if (size == 0) {
            for (int i = 0, n = ask.size(); i < n; i++) {
                if (price == ask.get(i).price) {
                    size = ask.get(i).size;
                    break;
                }
            }        
        }
        return size;
    }
    
    public void sell(int size) throws IOException {
        int remain;
        Order item = new Order(0, size);
        
        remain = bid.get(0).size - size;
        if (remain <= 0) {
            bid.remove(0);
      //      bid.trimToSize();
            int n = bid.size();
            while ((n >= 1) && (remain <= 0)) {
                remain = remain + bid.get(0).size;
                if (remain <= 0) {
                   bid.remove(0);
       //            bid.trimToSize();
                   n = bid.size();
                }
                 else {
                   item.price = bid.get(0).price;
                   item.size = remain;
                   bid.set(0, item);
                }   
            }
        }
        else {
            item.price = bid.get(0).price;
            item.size = remain;
            bid.set(0, item);
        }  
        item = null;
    }
    
    public void buy(int size) throws IOException {
        int remain;
            
        Order item = new Order(0, size);
        remain = ask.get(0).size - size;
        if (remain <= 0) {
            ask.remove(0);
            int n = ask.size();
            while ((n >= 1) && (remain <= 0)) {
                remain = remain + ask.get(0).size;
                if (remain <= 0) {
                   ask.remove(0);

                   n = ask.size();
                }
                else {
                    item.price = ask.get(0).price;
                    item.size = remain;
                    ask.set(0, item);
                }   
            }
        }
        else {
            item.price = ask.get(0).price;
            item.size = remain;
            ask.set(0, item);
        }
        item = null;
    }
}
