/*
	@autor José Enrique Vargas Oaxaca
*/

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.Comparator;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class kmeans {
	public static ArrayList<Punto> circulos  = new ArrayList<Punto>();
	public static ArrayList<Punto> centroides  = new ArrayList<Punto>();
	public Random colorAleatorio = new Random();

	public void pintarBlanco(){
		for(int i=0;i<centroides.size();i++){
			Graphics g = InterfazGrafica.panel.getGraphics();
                        g.setColor(Color.WHITE);
                	g.fillOval((int)centroides.get(i).getX(),(int)centroides.get(i).getY(),20,20);
		}
	}
	
	public void GeneraPuntos(int aleatorios){	
		for(int i=0;i<aleatorios;i++){
	        	circulos.add(new Punto((Math. random()*670+1),(Math. random()*670+1),0.0));	
			Graphics g = InterfazGrafica.panel.getGraphics();
                        g.setColor(Color.BLACK);
                	g.fillOval((int)circulos.get(i).getX(),(int)circulos.get(i).getY(),10,10);
                	
		}
		System.out.println("*****************circulos sin color**********************");
		Imprime(circulos);
		
	}
	public void PintarPuntos(){
		ArrayList<Double> c = new ArrayList<>(ObtenerColoresCirculos());
		
		int contador=0;
	        Graphics g = InterfazGrafica.panel.getGraphics();
                for(int j = 0; j<c.size();j+=3){
			double r = c.get(j),gg = c.get(j+1),b=c.get(j+2);
			Color randomColor = new Color((float)r, (float)gg,(float)b);
                        g.setColor(randomColor);
                	g.fillOval((int)circulos.get(contador).getX(),(int)circulos.get(contador).getY(),10,10);
			contador++;
			r=0.0;gg=0.0;b=0.0;
		}
		c.clear();
		contador=0;

	}

	public void PintarCentroides(){
		ArrayList<Double> c = new ArrayList<>(ObtenerColoresCentroides());
		int contador=0;
	        Graphics g = InterfazGrafica.panel.getGraphics();
                for(int j = 0; j<c.size();j+=3){
			double r = c.get(j),gg = c.get(j+1),b=c.get(j+2);
			Color randomColor = new Color((float)r, (float)gg,(float)b);
                        g.setColor(randomColor);
                	g.fillOval((int)centroides.get(contador).getX(),(int)centroides.get(contador).getY(),20,20);
			contador++;
			r=0.0;gg=0.0;b=0.0;
		}
		c.clear();

	}

	public void GenerarCentroidesAleatorios(int k){	
		for(int i=0;i<k;i++){
			double r = colorAleatorio.nextDouble(),g = colorAleatorio.nextDouble(),b = colorAleatorio.nextDouble();
	        	centroides.add(new Punto((Math. random()*670+1),(Math. random()*670+1),0.0,r,g,b));
			Graphics g1 = InterfazGrafica.panel.getGraphics();
			Color randomColor = new Color((float)r, (float)g,(float) b);
                        g1.setColor(randomColor);
                	g1.fillOval((int)centroides.get(i).getX(),(int)centroides.get(i).getY(),20,20);	
		}
		
		System.out.println("*****************primeros centroides**********************");
		Imprime(centroides);
	}

	public void EliminarColores(){
		for(int i=0;i<circulos.size();i++){
			circulos.get(i).getColor().clear();
		}
	}
	
	public void DistanciaEuclidiana(double x, double x1, double y, double y1,int j){
		centroides.get(j).setDistancia(Math.sqrt(Math.pow((x1-x),2)+Math.pow((y1-y),2)));	
	} 
	
	public void DistanciaChebyshev(double x,double x1,double y, double y1, int j){
		centroides.get(j).setDistancia(Math.max((Math.abs(((x1)-(x)))),(Math.abs(((y1)-(y))))));
	} 
	
	public void DistanciaManhattan(double x,double x1,double y,double y1,int j){
		centroides.get(j).setDistancia((Math.abs(((x1)-(x))))+(Math.abs(((y1)-(y)))));
	}	
	
	public void DistanciaHaversine(double x, double x1, double y, double y1,int j) {
		x = Math.toRadians(x);
		x1 = Math.toRadians(x1);
		y = Math.toRadians(y);
		y1 = Math.toRadians(y1);
		double lon = x1 - x;
		double lat = y1 - y;
		double sinlat = Math.sin(lat/20);
		double sinlon = Math.sin(lon/20);
		double c = 2 * Math.asin (Math.min(1.0, Math.sqrt((sinlat * sinlat) + Math.cos(y1)*Math.cos(y1)*(sinlon*sinlon))));
		centroides.get(j).setDistancia(c);
	}

	
	public void Paso2(String seleccionado){
		ArrayList<Double> colores = new ArrayList<Double>();
		for(int i=0;i<circulos.size();i++){
			for(int j=0;j<centroides.size();j++){
				double x=0.0,y=0.0,x1=0.0,y1=0.0;
				x1 = circulos.get(i).getX();
				y1 = circulos.get(i).getY();
				x = centroides.get(j).getX();
				y = centroides.get(j).getY();    
				if(seleccionado.equals("Euclidiana")){    
					 DistanciaEuclidiana(x,x1,y,y1,j);
				}else if(seleccionado.equals("Chebyshev")){
					 DistanciaChebyshev(x,x1,y,y1,j);
				}else if(seleccionado.equals("Manhattan")){
					 DistanciaManhattan(x,x1,y,y1,j);
				}else if(seleccionado.equals("Haversine")) {
					DistanciaHaversine(x,x1,y,y1,j);
				}
			}		
			centroides.sort(Comparator.comparing(Punto::getDistancia));
			colores.addAll(centroides.get(0).getColor());
			circulos.get(i).setColor(colores.get(0),colores.get(1),colores.get(2));
			
			colores.clear();		
		}
		PintarPuntos();
		System.out.println("******************los puntos de color*************");
		Imprime(circulos);
	}

	public ArrayList ObtenerColoresCentroides(){
		//obtenemos los colores de los centroides
		ArrayList<Double> colores = new ArrayList<Double>();
		for(int i=0;i<centroides.size();i++){
			colores.addAll(centroides.get(i).getColor());

		}	
		return colores;
		
	}
	public ArrayList ObtenerColoresCirculos(){
		//obtenemos los colores de los numeros aleatorios
		ArrayList<Double> colores1 = new ArrayList<Double>();
		for(int i=0;i<circulos.size();i++){
			colores1.addAll(circulos.get(i).getColor());
		}
		return colores1;	
		
	}
	public void paso3(){
		ArrayList<Double> colores = new ArrayList<Double>(ObtenerColoresCentroides());
		ArrayList<Double> colores1 = new ArrayList<Double>(ObtenerColoresCirculos());
		
		//comparamos los colores si son iguales a k se suma 
		double nuevaX = 0.0;
		double nuevaY = 0.0;
		int contador=0;	
		int contadorCentroides = 0;
		double contadorConsidencias = 0.0;
		for(int i=0;i<colores.size();i+=3){	//iniciamos con el primer color del centroide
			System.out.println("color a comparar");
			System.out.println(colores.get(i));			
			for(int j=0;j<colores1.size();j+=3){ //inciamos con los colores de los circulos
				if(Double.compare(colores.get(i),colores1.get(j))==0){				
					
					nuevaX += circulos.get(contador).getX(); //si es igual al primero
					nuevaY += circulos.get(contador).getY();
					
					contadorConsidencias+=1.0;

				}
				
				//terminamos de contar coincidencias de color con respecto al primero
				contador++;			
			}
			
			
			//actualizacion del centroide 
			if(contadorConsidencias>0.0){
				centroides.get(contadorCentroides).setX((nuevaX/contadorConsidencias));
				centroides.get(contadorCentroides).setY((nuevaY/contadorConsidencias));
				System.out.println("nuevo centroide");
				System.out.println(centroides.get(contadorCentroides).getX());
			}else if(contadorConsidencias==0.0){
				centroides.get(contadorCentroides).setX(centroides.get(contadorCentroides).getX());
				centroides.get(contadorCentroides).setY(centroides.get(contadorCentroides).getY());
			}
			nuevaX=0;
			nuevaY=0;
			contadorCentroides++;
			contador=0;
			contadorConsidencias = 0;
			
		}
		System.out.println("*****************nuevos centroides**********************");
		Imprime(centroides);
		colores.clear();
		colores1.clear();
		
		
	}

	public void Imprime(ArrayList<?> lista){
		lista.forEach(System.out::println);
	}
	

}
