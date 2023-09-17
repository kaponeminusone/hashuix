package structures;

public class Node <T>{

    private Node<T> son;

    private T value;

    public Node(T value){
        this.value = value;
    }
    public T getValue(){
        return value;
    }

    public Node<T> getSon(){
        return son;
    }
    public void setSon(Node<T> son){
        this.son = son;
    }
}
