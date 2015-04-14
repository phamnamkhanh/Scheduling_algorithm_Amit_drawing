/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling_algorithm;

/**
 *
 * @author namkhanh
 */
public class compute_adjacent {
     private Graph graph;
 
        // constructor
    public compute_adjacent(Graph g){
        graph = g;
        }
    
    public void compute(){
        
        for (int i=0; i<graph.nVerts;i++) {
           for (int j=0; j<graph.nVerts;j++) {
               if (graph.adjMat[i][j]!=0){
                   graph.vertexList[i].children.add(j);
                   graph.vertexList[j].parent.add(i);
               }
           }
       }
       
       for (int i=0; i<graph.nVerts;i++) {
           System.out.println("cac dinh cha cua dinh: "+i);
           System.out.println(graph.vertexList[i].parent);
           System.out.println("cac dinh con cua dinh: "+i); 
           System.out.println(graph.vertexList[i].children);
           
       }
        
        
    }
    
    
    
    
}
