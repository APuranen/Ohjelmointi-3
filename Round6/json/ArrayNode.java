import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

class ArrayNode extends Node implements Iterable<Node>{

    private ArrayList<Node> nodes;
    int index = 0;
    Node next;
    
    ArrayNode(){
        nodes = new ArrayList<Node>();
    }

    public void add(Node node){
        nodes.add(node);
    } 
    public int size(){
        return 0;
    }
    public Node first(){
        try {return nodes.get(0);}
        catch (IndexOutOfBoundsException e){return null;}
    }
    @Override
    public Iterator<Node> iterator(){
        return new Iter();
    }

    private class Iter implements Iterator<Node>{

        private Integer index = 0;
        private Node item;
        private Node next = first();

        @Override
        public boolean hasNext() {return next!= null;}

        @Override
        public Node next(){
        if(next == null){throw new NoSuchElementException();}

        item = next;
        index += 1;

        if(index < nodes.size()){next = nodes.get(index);}
        else next = null;

        return item;
            }

    }
}