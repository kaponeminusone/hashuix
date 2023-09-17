package structures;

import java.util.ArrayList;

import entity.Human;

public class HashTable<T>{
    
    final int m = 256;
    public static final int MULTY = 0;
    public static final int DIV = 1;

    int mode = MULTY;
    public LinkedList<T>[] table= new LinkedList[m];

    public HashTable(int MODE){
        if(!(MODE == 0 || MODE == 1)){
            System.out.println(" Missing Mode, DEFAULT: MULTY = 0");
            MODE = MULTY;
        }

        this.mode = MODE;
        initTable();
    }

    private void initTable(){

        for(int x = 0; x < m; x++){

            table[x] = new LinkedList<T>(){
                
                public int comparableTo(T object1, T object2){
                    return comparableToTable(object1, object2);
                }

            };
        }


    }

    public int comparableToTable(T ob1,T ob2){
        throw new UnsupportedOperationException("Unimplemented  method 'compareTo'");
    }


    public int hashFunctionOne(int k){

        double A = (Math.sqrt(5) - 1.0)/2;
        double hashValue = m * (k * A % 1);
        int hash = (int) hashValue;
        return hash;

    }

    public int hashFunctionTwo(int k) {

        int hashed = k % m;
        return hashed;
    }

    public void add(String key, T value){
        
        int reduction = asciiReduction(key);
        int index = 0;
        if(mode == MULTY){
            index = hashFunctionOne(reduction);
        }else if(mode == DIV){
            index = hashFunctionTwo(reduction);
        }

        table[index].add(value);
    }

    public void add(String key, Human value, LinkedList<Human>[] auxTab){
        
        int reduction = asciiReduction(key);
        int index = 0;
        if(mode == MULTY){
            index = hashFunctionOne(reduction);
        }else if(mode == DIV){
            index = hashFunctionTwo(reduction);
        }

        auxTab[index].add(value);
    }

    public int asciiReduction(String key){

        int x = 9;
        int reduction = 0;
        for(char c: key.toCharArray()){
            reduction+= ((int)c)*Math.pow(2,x);
            x--;
        }

        return reduction;
    }

    public int getColission(){

        int count = 0;  
        // System.out.print(" Sin ocupar: ");
        // System.out.print("[");
        for(int x = 0; x < m; x++){

            
            if(table[x].getSize() > 1){
                count+= table[x].getSize();

            }else if(table[x].getSize() == 0){
                // System.out.print(x);

                if(x < m-1){
                    // System.out.print(", ");
                }
            }

        }
        // System.out.println("]");

        return count;
    }

    public LinkedList<Integer> getIndexColissionCountOne(int numberOfColissions){
        
        LinkedList<Integer> indexes = new LinkedList<>();

        for(int x = 0; x < m; x++){
            if(table[x].getSize() == numberOfColissions){
                indexes.add(x);
            }
        }   

        return indexes;
    }

    public LinkedList<T> getHashIndex(String id){

        int ascii = asciiReduction(id);
        return table[hashFunctionOne(ascii)];
    }   

    public LinkedList<T>[] getArrayHashCode(){

        return table;

    }

    public LinkedList<T> getHashIndex(int index){

        return table[index];

    }

}
