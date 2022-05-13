
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeMap;

class ObjectNode extends Node implements Iterable<String>{

    private TreeMap<String, Node> nodes;

    public ObjectNode(){
        nodes = new TreeMap<String, Node>();
    }

    public Node get(String key){
        Node value = nodes.get(key);
        if(value != null){return value;}
        return null;
    }

    public void set(String key, Node node){
        nodes.put(key, node);
    }

    public int size(){
        return nodes.size();
    }
    public String first(){
        try{return nodes.firstKey();}
        catch(NoSuchElementException e){return null;}
    }


    @Override
    public Iterator<String> iterator(){
        return new Iter();
            
    }
    private class Iter implements Iterator<String>{

        private Integer index = 0;
        private String item;
        private String next = first();
        private ArrayList<String> array = new ArrayList<>(nodes.keySet());

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public String next() {
            if(next == null){throw new NoSuchElementException();}

            item = next;
            index += 1;
            
            if(index < array.size()){next = array.get(index);}
            else {next = null;}

            return item;
        }
        
    }

}