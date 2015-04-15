package scheduling_package;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dung
 */
public class ShapeExample extends javax.swing.JPanel {

    /**
     * Creates new form ShapeExample
     */
    public Graph graph;
    Graphics graphic;
    NewJFrame JF;
    double unit = 1;
    int N_Pro = 10;
    int N_Vol = 4;
    Point2D [] phase1_xy = new Point2D[N_Pro];
      Point2D [] phase2_xy = new Point2D[N_Pro];
    public ShapeExample(Graph g,NewJFrame jf) {
        initComponents();
        graph = g;
        JF = jf;
        //graphic = this.getGraphics();
    }
    
    //private Ellipse2D.Double circle =    new Ellipse2D.Double(10, 10, 750, 350);
    //private Rectangle2D.Double square =    new Rectangle2D.Double(10, 10, 350, 350);
    private Rectangle2D.Double square =    new Rectangle2D.Double(10, 10, 400, 400);
    
    public void draw_phase2(Graph graph_2){
        //paintComponent(Graphics g);
        Graphics2D g2d = (Graphics2D) this.getGraphics();
        
        
      int max_height = 600;
      double maxspan = 0;
       //computaion cost per one pixel
      double time1 = 0,time2 = 0;
      int column = 0; //processor nao
      int width = 0; //voltage nao
      
      g2d.setPaint(Color.white);
      for (int i = 0; i< graph.nVerts;i++){
          time1 = graph.vertexList[i].ast;
          time2 = graph.vertexList[i].aft;
          column = graph.vertexList[i].processor;
          width = graph.vertexList[i].voltage;
          g2d.fill(new Rectangle2D.Double((float)(phase2_xy[column].getX()), (float)(phase2_xy[column].getY()+(time1/unit)), 8*(N_Vol-width), (time2/unit) - (time1/unit)));
          //g2d.drawString(String.valueOf(i) , (float)(phase1_xy[column].getX()+2), (float)(phase1_xy[column].getY()+12+(time1/unit)));
          //// System.out.println("ve: " + (phase1_xy[column].y+(time1/unit) )+ " " + ((time2/unit) - (time1/unit)));
      }
      
      Stroke stroke = new BasicStroke(3,
      BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0);
      g2d.setColor(Color.black);
      //GradientPaint white = new GradientPaint(0,0,Color.red,100, 0,Color.WHITE);
      g2d.setStroke(stroke);
      
      for (int i = 0; i< graph.nVerts;i++){
          if (maxspan < graph.vertexList[i].ast)
              maxspan = graph.vertexList[i].ast;
      }
      
      for (int i = 0; i< graph.nVerts;i++){
          time1 = graph.vertexList[i].ast;
          time2 = graph.vertexList[i].aft;
          column = graph.vertexList[i].processor;
          width = graph.vertexList[i].voltage;
          g2d.draw(new Rectangle2D.Double((float)(phase2_xy[column].getX()), (float)(phase2_xy[column].getY()+(time1/unit)), 8*(N_Vol-width), (time2/unit) - (time1/unit)));
          g2d.drawString(String.valueOf(i) , (float)(phase2_xy[column].getX()+2), (float)(phase2_xy[column].getY()+12+(time1/unit)));
      }
      
      g2d.drawString("phase 2: "  , (float)(phase2_xy[N_Pro-1].getX()+50), (float)(phase2_xy[0].getY()+100));
      g2d.drawString("maxspan: " + String.valueOf(maxspan) , (float)(phase2_xy[N_Pro-1].getX()+50), (float)(phase2_xy[0].getY()+120));
      g2d.drawString("enegy: " + String.valueOf(graph.energy) , (float)(phase2_xy[N_Pro-1].getX()+50), (float)(phase2_xy[0].getY()+140));
      
    }
    
    
    public void paintComponent(Graphics g) {
        
      int max_height = 500;
      double maxspan = 0;
      
      double time1 = 0,time2 = 0;
      int column = 0; //processor nao
      int width = 0; // chieu rong cua task=so column
      for (int i = 0; i< graph.nVerts;i++){
          if (maxspan < graph.vertexList[i].aft)
              maxspan = graph.vertexList[i].aft;
      }
      
      if (maxspan >= (max_height)){
          unit = (int) Math.ceil((float)maxspan/max_height);
      }
      else if (maxspan >= (max_height/2)) {
          unit = 1;
      }
      else if (maxspan >= (max_height/4)) {
          unit = 0.5;
      }
      else if (maxspan >= (max_height/8)) {
          unit = 0.25;
      }
      else
          unit = 0.125;
      
      clear(g);
      Graphics2D g2d = (Graphics2D)g;
      
      Stroke normal = g2d.getStroke();
      
      
      for (int i=0 ;i < N_Pro;i++){
          phase1_xy[i] = new Point();
          phase2_xy[i] = new Point();
      }
      
      int x1=10,y1=20,x2=10,y2=620;
      g2d.drawString("Column", x2+10, y1);
      g2d.draw(new Line2D.Double(x1, y1-10, x2, y2));
      g2d.draw(new Line2D.Double(x1, y1, x2+600, y2-600));
      g2d.draw(new Line2D.Double(x2, y2, x1+600, y1+600));
      g2d.draw(new Line2D.Double(x1+420, y2, x1+420, y1));
      
      
      for (int i =0; i < 20; i++){
          g2d.draw(new Line2D.Double(x1, y1, x1+600, y1));
          y1+=30;
          g2d.drawString("C"+String.valueOf(20-i-1), x1, y1-8);
      }
      x1=10;y1=20;x2=10;y2=620;
      for (int i =0; i < 20; i++){
          x1+=30;
          g2d.draw(new Line2D.Double(x1, y1, x1, y1+600));
          
          g2d.drawString(String.valueOf(i), x1-5, y2+15);
      }
      
      g2d.drawString("Time", x1+10, y2+10);
      
      
  /*    
      float dash1[] = {10.0f};
      BasicStroke dashed = new BasicStroke(1.0f,
                        BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER,
                        10.0f, dash1, 0.0f);
      g2d.setStroke(dashed);
      g2d.setColor(Color.gray);
     */
      //draw voltage line phase 1
      x1=40;y1=620;
      for (int i=0; i< N_Pro; i++) {
          phase1_xy[i].setLocation(x1, y1);
          y1-= 30;
          
      }
      
      //draw voltage line phase 2
  /*    x1+=50;y1=20;x2+=50;y2=620;
      for (int i=0; i< N_Pro; i++) {
          phase2_xy[i].setLocation(x1, y1);
          for (int j = 0; j < N_Vol+1; j++) {
              g2d.draw(new Line2D.Double(x1, y1, x2, y2));
              x1 += 8;
              x2 +=8;
          }
          x1+= 20;
          x2+= 20;
      } */
      
      
      //Ve execution part cua task
      g2d.setPaint(Color.white);
      for (int i = 0; i< graph.nVerts;i++){
          time1 = graph.vertexList[i].ast;
          time2 = graph.vertexList[i].aft;
          column = graph.vertexList[i].processor;
          width = graph.vertexList[i].weight;
          g2d.fill(new Rectangle2D.Double((float)(phase1_xy[column].getX()+time1*30), (float)(phase1_xy[column].getY()-30*graph.vertexList[i].weight), (float)(time2*30) - (time1*30),(float) 30*graph.vertexList[i].weight));
          //g2d.drawString(String.valueOf(i) , (float)(phase1_xy[column].getX()+2), (float)(phase1_xy[column].getY()+12+(time1/unit)));
          //// System.out.println("ve: " + (phase1_xy[column].y+(time1/unit) )+ " " + ((time2/unit) - (time1/unit)));
      }
      
      Stroke stroke = new BasicStroke(3,
      BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0);
      g2d.setColor(Color.black);
      //GradientPaint white = new GradientPaint(0,0,Color.red,100, 0,Color.WHITE);
      g2d.setStroke(stroke);
      for (int i = 0; i< graph.nVerts;i++){
          time1 = graph.vertexList[i].ast;
          time2 = graph.vertexList[i].aft;
          column = graph.vertexList[i].processor;
          width = graph.vertexList[i].voltage;
          g2d.draw(new Rectangle2D.Double((float)(phase1_xy[column].getX()+time1*30), (float)(phase1_xy[column].getY()-30*graph.vertexList[i].weight), (float)(time2*30) - (time1*30),(float) 30*graph.vertexList[i].weight));
          g2d.drawString("E"+String.valueOf(i) , (float)(phase1_xy[column].getX()+(time1+time2)*15-5), (float)(phase1_xy[column].getY()-15*graph.vertexList[i].weight)+2);
      }
      
       //Ve reconfiguration part cua task
      g2d.setPaint(Color.LIGHT_GRAY);
      for (int i = 1; i< graph.nVerts;i++){
          time1 = graph.vertexList[i].rst;
          time2 = graph.vertexList[i].rft;
          column = graph.vertexList[i].processor;
          width = graph.vertexList[i].weight;
          g2d.fill(new Rectangle2D.Double((float)(phase1_xy[column].getX()+time1*30), (float)(phase1_xy[column].getY()-30*graph.vertexList[i].weight), (float)(time2*30) - (time1*30),(float) 30*graph.vertexList[i].weight));

           }
      
      stroke = new BasicStroke(3,
      BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0);
      g2d.setColor(Color.black);
      //GradientPaint white = new GradientPaint(0,0,Color.red,100, 0,Color.WHITE);
      g2d.setStroke(stroke);
      for (int i = 1; i< graph.nVerts;i++){
          time1 = graph.vertexList[i].rst;
          time2 = graph.vertexList[i].rft;
          column = graph.vertexList[i].processor;
          width = graph.vertexList[i].weight;
          g2d.draw(new Rectangle2D.Double((float)(phase1_xy[column].getX()+time1*30), (float)(phase1_xy[column].getY()-30*graph.vertexList[i].weight), (float)(time2*30) - (time1*30),(float) 30*graph.vertexList[i].weight));
          if (graph.vertexList[i].rft!=0){
          g2d.drawString("R"+String.valueOf(i) , (float)(phase1_xy[column].getX()+(time1+time2)*15-5), (float)(phase1_xy[column].getY()-15*graph.vertexList[i].weight)+2);
     }
      
      }  
      
      
      //// System.out.println(unit);
      g2d.drawString("phase 1: "  , 550, 20);
      g2d.drawString("maxspan: " + String.valueOf(graph.makespan) , 550, 50);
      g2d.drawString("enegy: " + String.valueOf(graph.energy) , 550, 80);
    }

  // super.paintComponent clears offscreen pixmap,
  // since we're using double buffering by default.

  protected void clear(Graphics g) {
    super.paintComponent(g);
  }

//  protected Ellipse2D.Double getCircle() {
//    return(circle);
//  }

 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 634, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
