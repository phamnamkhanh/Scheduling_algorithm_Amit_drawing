/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling_algorithm;

/**
 *
 * @author Dung
 */
public class Drawing extends Thread{
    Graph graph;
    NewJFrame JF;// = NewJFrame(graph)
    public Drawing(Graph g){
        graph = g;
    }
    
    public void draw_phase2(Graph k){
        JF.draw_phase2(k);
    }
        //java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JF = new NewJFrame(graph);//.setVisible(true);
                JF.setVisible(true);
            }
        //}
    
}
