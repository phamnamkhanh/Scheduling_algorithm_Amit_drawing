/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling_algorithm;

/**
 *
 * @author Dung
 */


public class Graph {
    public Vertex[] vertexList;        //vertex representative
    public double adjMat[][];             //edge representative
    public double adjMat_rev[][];
    public int nVerts;                  //number of vertice
    public final int MAX_VERTS = 100;
    public double energy;            // store leakage power
    public double makespan;         // execution time
    
    // constructor
    public Graph(){
        vertexList = new Vertex[MAX_VERTS];
        adjMat = new double[MAX_VERTS][MAX_VERTS];
        adjMat_rev = new double[MAX_VERTS][MAX_VERTS];
        nVerts = 0;
        energy = 0;
        for (int i= 0; i< MAX_VERTS;i++){
            for (int j=0;j<MAX_VERTS;j++){
                adjMat[i][j] = 0;
                adjMat_rev[i][j] = 0;
            }
        }
    }
    
    public void addVertex(int lab, double comp_time, int weight){
        vertexList[nVerts++] = new Vertex(lab,comp_time,weight);
    }
    
    public void addEdge(int start, int end, double comm){
        adjMat[start][end] = comm;
        adjMat_rev[end][start] = comm;
        vertexList[end].parent.add(start);
        vertexList[start].children.add(end);
              
    }
    
    public void resetVertex(){
        for (int i=0;i<MAX_VERTS;i++){
            vertexList[i].wasVisited = false;
        }
    }
    // reset critical path
    public void resetCP(){
        for (int i=0;i<nVerts;i++){
            vertexList[i].isCP = false;
        }
    }
    
    public Graph clone(){
        Graph r = null;
        r.vertexList = new Vertex[MAX_VERTS];
        r.adjMat = new double[MAX_VERTS][MAX_VERTS];
        r.adjMat_rev = new double[MAX_VERTS][MAX_VERTS];
        r.nVerts = 0;
        
        
        
        return r;
    }
   
}
