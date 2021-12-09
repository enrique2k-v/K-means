/*
	@autor José Enrique Vargas Oaxaca
*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.*;

class InterfazGrafica{
    	public static JPanel panel;
    	JButton Generar;
    	JButton paso2;
    	JButton paso3;
    	JButton GenerarCentroides;
    	JButton Limpiar;
    	kmeans o = new kmeans();
	JCheckBox AgregarClase;
	JLabel SeleccioneDistancia; 
	JComboBox<String> Distancias;
    public InterfazGrafica() {
        JFrame frame = new JFrame();
        panel = new JPanel();
        frame.setTitle("Algoritmo Kmeans");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(8,2 ,10,10));
       	JTextField k = new JTextField("Ingrese el valor de k");
        JTextField aleatorios= new JTextField("Ingrese N aleatorios");
        Generar=new JButton("Generar aleatorios"); 
	paso2=new JButton("paso2");
	paso3=new JButton("paso3");
	GenerarCentroides=new JButton("Generar centroides"); 
        Limpiar=new JButton("Limpiar"); 
	AgregarClase = new JCheckBox("Agregar clase");
	paso3.setEnabled(false);
	SeleccioneDistancia= new JLabel("Escoja el tipo de distancia");
        Distancias=new JComboBox<String>();
	Distancias.addItem("Euclidiana");
        Distancias.addItem("Chebyshev");
        Distancias.addItem("Manhattan");
  	Distancias.addItem("Haversine");

	k.addMouseListener(new MouseListener() {
		@Override
			public void mouseClicked(MouseEvent e) {
			      k.setText("0");
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				          
			}
           });	

	 aleatorios.addMouseListener(new MouseListener() {
		@Override
			public void mouseClicked(MouseEvent e) {
			      aleatorios.setText("0");
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				          
			}
            
	});

        Limpiar.addActionListener(new ActionListener() {
        	@Override
            		public void actionPerformed(ActionEvent e) {
                		Generar.setEnabled(true);
                		aleatorios.setEnabled(true);   
				GenerarCentroides.setEnabled(true);  
				paso2.setEnabled(true); 
				
                		o.circulos.clear();
                		o.centroides.clear();
                		frame.repaint();
            		}
        });

        Generar.addActionListener(new ActionListener() {
            	@Override
            	public void actionPerformed(ActionEvent e) {
			int aleatorios1 = Integer.parseInt(aleatorios.getText());
                	o.GeneraPuntos(aleatorios1);
			Generar.setEnabled(false);
		
            	}  
            
        });

	GenerarCentroides.addActionListener(new ActionListener() {
            	@Override
            	public void actionPerformed(ActionEvent e) {
                	int k1 = Integer.parseInt(k.getText());
                	o.GenerarCentroidesAleatorios(k1);
			GenerarCentroides.setEnabled(false);
            	}  
        });

	paso2.addActionListener(new ActionListener() {
            	@Override
            	public void actionPerformed(ActionEvent e) {
			o.EliminarColores();			
        		String seleccionado = (String) Distancias.getSelectedItem();
			JOptionPane.showMessageDialog(null,seleccionado);
			o.Paso2(seleccionado);
			paso3.setEnabled(true);
            	}  
        });

	paso3.addActionListener(new ActionListener() {
            	@Override
            	public void actionPerformed(ActionEvent e) {
			ArrayList<Punto> copia;		
			int contadorv=0;		
			while(true){	
				copia  = new ArrayList<Punto>();
				for(int i=0;i<kmeans.centroides.size();i++){
					copia.add(new Punto(kmeans.centroides.get(i).getX(),kmeans.centroides.get(i).getY(),0.0));
				}	
				o.pintarBlanco();
				o.paso3();
				o.EliminarColores();
				String seleccionado = (String) Distancias.getSelectedItem();
				o.Paso2(seleccionado);
				o.PintarPuntos();
				o.PintarCentroides();
				for(int i=0;i<kmeans.centroides.size();i++){
					if(Double.compare(copia.get(i).getX(),kmeans.centroides.get(i).getX())==0){
						contadorv++;					
					}
				}
				if(kmeans.centroides.size()==contadorv){
					JOptionPane.showMessageDialog(null,"Exito");					
					break;		
				}
			
				contadorv=0;
				copia.clear();
			}
		paso3.setEnabled(false);	
            	}  
        });

        panel.addMouseListener(new MouseListener() {
		@Override
		public void mouseClicked(MouseEvent e) {
			Random colorAleatorio = new Random();
			double x = e.getX();
                	double y = e.getY();
			if(AgregarClase.isSelected()){	      
				Graphics g1 = panel.getGraphics();
				double r = colorAleatorio.nextDouble(),g = colorAleatorio.nextDouble(),b = colorAleatorio.nextDouble();
				kmeans.centroides.add(new Punto(x,y,0.0,r,g,b));
				Color randomColor = new Color((float)r, (float)g,(float) b);
                        	g1.setColor(randomColor);
                		g1.fillOval((int)x,(int)y,20,20);
			}else{              	  	
				
				kmeans.circulos.add(new Punto(x,y,0.0));
				Graphics g = panel.getGraphics();
				g.setColor(Color.BLACK);
				g.fillOval((int)x,(int)y,10,10);
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
          });

        
        p1.add(aleatorios); 
	p1.add(k);
	p1.add(Generar);
	p1.add(GenerarCentroides);
	p1.add(paso2);
        p1.add(Limpiar);   
	p1.add(paso3); 
	p1.add(AgregarClase);
	p1.add(SeleccioneDistancia);
	p1.add(Distancias);
        JPanel p2 = new JPanel();
        p2.setLayout(new BorderLayout());
        p2.add(p1, BorderLayout.NORTH);
        frame.add(p2, BorderLayout.EAST);
        frame.add(panel,BorderLayout.CENTER);
        frame.setSize(1050, 660);
        frame.setVisible(true);
        frame.setResizable(false);
	panel.setBackground(Color.WHITE);
    }

}

