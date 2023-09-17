package structures;

public class LinkedList<T>{
    
    private Node<T> begin;
    private Node<T> last;

    private int nodesCount = 0;

    public LinkedList(){
    }
    public LinkedList(Node<T> node){
        this.begin = node;
        //Buscar el ultimo aqui. para asignarlo
    }

    /**
     *  Metodo utilizado para la organizacion en cada agregacion
     *  de una nuevo objeto a la lista, de forma ordenada, basandose
     *  en la estructura que se mande.
     * 
     *  Indicando dentro del metodo la condicion con el ATRIBUTO
     *  que se desea comparar.
     * 
     *  se debe tener en cuenta que 1 significará que
     *  el `object1` es mayor, -1 si es menor y 0 si es igual.
     * 
     * @param object1   Objeto a comprar
     * @param object2   Objeto a comprar
     * @return          1, 0, - 1
     */
    public int comparableTo(T object1, T object2){
        throw new UnsupportedOperationException("Unimplemented  method 'compareTo'");
    }

    /**
     *  Añade al ultimo cada elemento
     * 
     *  - Es probable que se pierda completamente el ordenamiento.
     * @param value elemento
     */
    public void add(T value){

        nodesCount++;
        if(begin == null){
            begin = new Node<T>(value);
            last = begin;
            return;
        }
        last.setSon(new Node<>(value));
        last = last.getSon();

    }
    /**
     *  Añade el elemento en un Índice especifico.
     *  - Es probable que se pierda completamente el ordenamiento.
     * @param value elemento
     * @param index posicion de insercion
     */
    public void addInIndex(T value,int index){

        if(index == 0){
            addBegin(value);
            return;
        }
        
        if(nodesCount <= index){
            System.out.println(" No existe el indice: " + index);
            return;
        }

        nodesCount++;
        Node<T> aux = indexOfNode(index-1);
        Node<T> auxNode = new Node<T>(value);
        auxNode.setSon(aux.getSon()); 
        aux.setSon(auxNode);
    }

    /**
     * Añade al inicio el elemento.
     *  - Es probable que se pierda completamente el ordenamiento.
     * @param value elemento
     */
    public void addBegin(T value){

        nodesCount++;
        if(begin == null){
            begin = new Node<T>(value);
            last = begin;
        }

        Node<T> aux = new Node<>(value);
        aux.setSon(begin);
        begin = aux;
    }

    /**
     * Retorna el NODO compelto en el indice seleccionado.
     * @param index indice del nodo.
     * @return      Node<Generic>
     * @see         Node.java
     */

    public Node<T> indexOfNode(int index){
        
        if(nodesCount <= index || index < 0){
            System.out.println(" Index out bounds: " + index + " > " + (nodesCount-1));
            return null;
        }

        Node<T> aux = begin;
        int cont = 0;
        while((aux!=null) && cont < index){
            aux = aux.getSon();
            cont++;
        }
        // System.out.println("?");
        return aux;
    }
    /**
     * Retorna el elemento dentro de la lista en el indice especificado.
     * @param index posicion del elemento
     * @return      elemento
     */
    public T indexOf(int index){
        try {
            T value = indexOfNode(index).getValue();
        if( value != null){
            return value;
        }
        } catch (Exception e) {
        }
        
        return null;
    }

    /**
     * Muestra la lista de la forma: 
     * [element0.toString,element1.toString...]
     * @see Object.toString()
     */
    public void viewList(){
        
        Node<T> aux = begin;
        System.out.print(" " + nodesCount);
        System.out.print("\n[");

        while((aux!=null)){
            System.out.print(aux.getValue());

            aux = aux.getSon();
            if(aux != null){
                System.out.print(",\n ");
            }
        }
        System.out.println("]");
    }

   
    public Node<T> getNodeElement(T value){

        Node<T> aux = begin;
            while((aux!=null)){ 

                if(comparableTo(aux.getValue(), value) == 0){
                    return aux;
                }

                aux = aux.getSon();
            }
        return null;
    }

    public void deleteIndex(int index){

        if(index == 0){
            begin = begin.getSon();
            nodesCount--;
            return;
        }
        if(index >= nodesCount){
            System.out.println(" Index out bounds");
            return;
        }
        
        nodesCount--;
        Node<T> aux = indexOfNode(index-1);
        aux.setSon(aux.getSon().getSon());
    }

    public void deleteElement(T value){
        
        if(comparableTo(begin.getValue(), value) == 0){
            begin = begin.getSon();
            nodesCount--;
            return;
        }

        Node<T> aux = begin;
        Node<T> temp = begin;
        while((aux!=null)){ 

            if(comparableTo(aux.getValue(), value) == 0){
                nodesCount--;
                temp.setSon(aux.getSon());
                return;
            }

            temp = aux;
            aux = aux.getSon();
        }
        
        System.out.println(" Elemento (" + value + ")  no esta en la lista");
    }

    public boolean isEmpty(){

        if(nodesCount == 0){
            return true;
        }
        return false;

    }

    public int getSize(){

        return nodesCount;

    }

}
