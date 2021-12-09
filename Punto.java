/*
	@autor José Enrique Vargas Oaxaca
*/



import java.util.ArrayList;

public class Punto{
	private double x;
	private double y;
	private double distancia;
	private ArrayList<Double> color = new ArrayList<Double>();


	public Punto(){
	
	}

	public Punto(double x,double y,double distancia){
		this.x = x;
		this.y = y;
		this.distancia = distancia;
		
	}

	public Punto(double x,double y,double distancia,double r,double g,double b){
		this.x = x;
		this.y = y;
		this.distancia = distancia;
		this.setColor(r,g,b);
	}
	
	public double getX(){
		return this.x;	
	}	
	
	public void setX(double x){
		this.x = x;
	}

	public double getY(){
		return this.y;	
	}	
	
	public void setY(double y){
		this.y = y;
	}


	public double getDistancia(){
		return this.distancia;	
	}	
	
	public void setDistancia(double distancia){
		this.distancia = distancia;
	}

	
	public ArrayList getColor(){
		return this.color;
	}
	
	
	public void setColor(double r,double g,double b){
		this.color.add(r);
		this.color.add(g);
		this.color.add(b);
	}	
	
	public String toString(){
		return "Punto : x "+getX()+" y: "+getY()+" distancia: "+getDistancia() + " Color: " + getColor();
	}

 	
}
