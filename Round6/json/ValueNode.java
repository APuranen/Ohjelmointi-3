class ValueNode extends Node{

    private Double number;
    private Boolean truthValue;
    private String stringValue;

    ValueNode(double value){
        this.number = value;
    }
    ValueNode(boolean value){
        this.truthValue = value;
    }
    ValueNode(String value){
        this.stringValue = value;
    }

    public boolean isNumber(){
        return number != null;
    }
    public boolean isBoolean(){
        return truthValue != null;
    }
    public boolean isString(){
        return stringValue != null;
    }
    public boolean isNull(){
        return stringValue == null;
    }

    public double getNumber(){
        return number;
    }
    public boolean getBoolean(){
        return truthValue;
    }
    public String getString(){
        return stringValue;
    }
}