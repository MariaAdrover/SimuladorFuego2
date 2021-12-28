package simuladorfuego2;

import java.awt.Container;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author miaad
 */
public class SimuladorFuego2 extends JFrame{
    private Viewer viewer;
    
    public SimuladorFuego2() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.viewer =  new Viewer();
        Container c = this.getContentPane();
        c.add(viewer);
        this.pack();
        this.setLocation(500, 200);
    }
    
    public void startSimulation() {
        viewer.start();        
    }

    public static void main(String[] args) {
        SimuladorFuego2 simulador = new SimuladorFuego2();
        simulador.setVisible(true);
        simulador.startSimulation();
    }
    

}
