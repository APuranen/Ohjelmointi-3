class Rectangle implements IShapeMetrics{

    double h;
    double w;

    public Rectangle(double height, double width){
        this.h = height;
        this.w = width;
    }
    public String toString(){return String.format("Rectangle with height %,.2f and width %,.2f", h, w);}
    public String name(){return "rectangle";}
    @Override
    public double area(){return h*w;}
    @Override
    public double circumference(){return (2*h+2*w);}
}