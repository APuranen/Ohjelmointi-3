class Circle implements IShapeMetrics {
    
    double r;

    public Circle(double radius){
        this.r = radius;
    }

    public String toString(){
        //String message = "Circle with radius: " + r;
        return String.format("Circle with radius: %,.2f", r);
    }
    public String name(){return "circle";}
    @Override
    public double area(){return PI * r * r;}
    @Override
    public double circumference(){return 2 * PI * r;}
}