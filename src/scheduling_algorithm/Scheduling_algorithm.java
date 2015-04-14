/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling_algorithm;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author namkhanh
 */
public class Scheduling_algorithm {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         double alpha, beta, gamma,delta,eta, sigma, mu;
         String filename="TG5";
        alpha=Double.parseDouble(args[0]);
        beta=Double.parseDouble(args[1]);
        gamma=Double.parseDouble(args[2]);
        delta=Double.parseDouble(args[3]);
        eta=Double.parseDouble(args[4]);
        sigma=Double.parseDouble(args[5]);
        mu=Double.parseDouble(args[6]);
        filename= args[7];
        // TODO code application logic here
        //VSL VSL0 = new VSL();
        double[] time= new double[10];
        double[] energy= new double[10];
        int j,n_task=0;
        String [] task_graph_string = null, comm_graph_string = null; 
        File tgff_file = new File(filename+".txt");
        if (!tgff_file.isFile())
            {
                System.out.println("Check file name");      
            }  
        try {
            Scanner scan = new Scanner(tgff_file);
            String line = "";
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                if (line.contains("@TASK_GRAPH"))
                    n_task++;
            }
                
            System.out.println(n_task);   
            
            scan = new Scanner(tgff_file);
            task_graph_string = new String [n_task];
            comm_graph_string = new String [n_task];
            
            j = 0;
            
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                if (line.contains("@TASK_GRAPH"))
                    while (scan.hasNextLine()) {
                        task_graph_string[j] = task_graph_string[j] + line + "\n";
                        line = scan.nextLine();
                        if (line.startsWith("}")){
                            System.out.println("break");
                            j++;
                            break;

                        }

                    }

                if (line.contains("@COMP"))
                    while (scan.hasNextLine()) {
                        comm_graph_string[j-1] = comm_graph_string[j-1] + line + "\n";
                        line = scan.nextLine();
                        if (line.contains("}")){
                            System.out.println("break");
                            j++;
                            break;
                        }
                    }
                }
            
//                for (j =0; j < n_task;j++){
//                    
//                }
            
                      
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Scheduling_algorithm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
       
     // for (int i=0;i<8;i++){
    //  inputGraph1(graph);
       Graph graph = new Graph();
     constructGraph(graph,task_graph_string[0],comm_graph_string[0]);
    // inputGraph1(graph);  
       
       //Giai thuat
      // algorithm al = new algorithm(graph);
       //algorithm.schedule();
       schedule sc= new schedule(graph,8,alpha, beta, gamma,delta,eta, sigma, mu);
       //al.compute_adj();
       //al.compute_b_L();
       //al.compute_path();
       //al.findCriticalPath();
      // int[] a=new int[graph.nVerts];
      // a=sc.createNodeList();
       sc.dynamic_schedule(); 
       time[0]=graph.makespan;
       energy[0]=graph.energy;
      // }
       
          
           
           //write to file
   
            {
  try{
  // Create file 
  FileWriter fstream = new FileWriter(filename+"_out.txt");
  BufferedWriter out = new BufferedWriter(fstream);
  out.write(filename+"\r\n");
  out.write("Time2   Energy2"+"\r\n");
  for (int i=0;i<1;i++){
  //BufferedWriter out = new BufferedWriter(fstream);    
  out.write(Double.toString(time[i])+'\t' + Double.toString(energy[i])+"\r\n");
      
   }
  //Close the output stream
  out.close();
  
  }catch (Exception e){//Catch exception if any
  System.err.println("Error: " + e.getMessage());
  }
  } 
      
      
      
      
         
    }
    
    
    // example from paper 2
     private static void inputGraph2(Graph graphObject) {
    
    graphObject.nVerts=9;
    
     
     
    //input vertexs
    
    graphObject.vertexList[0]= new Vertex(0,0,0);
    graphObject.vertexList[0].isEntry=true;
    graphObject.vertexList[1]= new Vertex(1,5,1);
    graphObject.vertexList[2]= new Vertex(2,3,1);           
    graphObject.vertexList[3]= new Vertex(3,2,2);          
    graphObject.vertexList[4]= new Vertex(4,3,2);              
    graphObject.vertexList[5]= new Vertex(5,4,1);    
    graphObject.vertexList[6]= new Vertex(6,3,1);            
    graphObject.vertexList[7]= new Vertex(7,4,2); 
    graphObject.vertexList[8]= new Vertex(8,3,1);
    
    
     
       // input edge (communication)
   
    graphObject.addEdge(1, 4, 1);
    graphObject.addEdge(1, 6, 1);
    graphObject.addEdge(3, 2, 1); 
    graphObject.addEdge(7, 8, 1);
    graphObject.addEdge(2, 8, 1);
    
    
    
    }
   
     
    // example from paper 1 
    private static void inputGraph1(Graph graphObject) {
    
    graphObject.nVerts=7;
    
     
     
    //input vertexs
    
    graphObject.vertexList[0]= new Vertex(0,0,0);
    graphObject.vertexList[0].isEntry=true;
    graphObject.vertexList[1]= new Vertex(1,2,2);
    graphObject.vertexList[2]= new Vertex(2,3,2);           
    graphObject.vertexList[3]= new Vertex(3,2,2);          
    graphObject.vertexList[4]= new Vertex(4,2,1);              
    graphObject.vertexList[5]= new Vertex(5,2,2);    
    graphObject.vertexList[6]= new Vertex(6,5,1);
     
       // input edge (communication)
    
    graphObject.addEdge(1, 2, 1);
    graphObject.addEdge(2, 3, 1);
    graphObject.addEdge(2, 6, 1);
    graphObject.addEdge(2, 4, 1);
    graphObject.addEdge(3, 5, 1);
    graphObject.addEdge(4, 5, 1);
    graphObject.addEdge(6, 5, 1); 
    }
     
     
     
     
     
     
     
     private static void constructGraph(Graph graph,String task_string,String value_string) {
        int check = 0;
        int kind=0;
        //replacing this with the reading file function
        //String comm_string = "ab a sd\n sd sd sd\n # type    exec_time\n    0   1.3\n 2   3\n";
        double [] weight = new double[100];
        double [] comm = new double[100];
        double [] task = new double [100];
        double [][] matrix = new double[100][100];
        
        Scanner scan = new Scanner(value_string);
        String line;
        int type = 0,i=0,j=0;
        float f,n;
        
        while (scan.hasNext()){
            line = scan.nextLine();
            if (line.contains("# type version time    weight")){
                while (scan.hasNext()){
                    f = scan.nextFloat();
                    if (i%4==0) {
                        //System.out.print((int)f +" ");
                        type = (int) f;
                    }
                    else {
                        if (i%4==2){
                            comm[type] = f; 
                        } else { if (i%4==3){
                            weight[type]=f;
                        }
                            
                        }
                        
                           
                     
                    }
                    i++;
                }
            }
        }
        
        // add tasks
        scan = new Scanner(task_string);
        i = 0;
        String split[],sub_split[];
        while (scan.hasNextLine()) {
            line = scan.nextLine();
            if (line.contains("TASK ")){
                split = line.split(" "); //should check the number of split is not correct
                
                    kind=Integer.parseInt(split[2],10);
                    
                
                graph.addVertex(i, comm[kind],(int)weight[kind]);
                i++;
                }
        }
        //3-7-9
       
        // add edges
        scan = new Scanner(task_string);
        i = 0;
        int source, dest;
       
        while (scan.hasNextLine()) {
            line = scan.nextLine();
            if (line.contains("ARC ")){
                split = line.split(" ");
                source = Integer.parseInt(split[3].split("_")[1]);
                dest =   Integer.parseInt(split[7].split("_")[1]);
                graph.addEdge(source, dest, 1);
                }
        }
        
        
  
        
        //finding entry task
        for ( i =0; i < graph.nVerts;i++){
            check = 0;
            for ( j = 0; j< graph.nVerts;j++){
                if (graph.adjMat_rev[i][j]!=0){
                    check = 1;
                    break;
                }
            }
            if (check == 0) {
                graph.vertexList[i].isEntry = true;
                System.out.println("Entry task: " + i);
            }
        }
        
        //finding exit task
        for ( i =0; i < graph.nVerts;i++){
            check = 0;
            for ( j = 0; j< graph.nVerts;j++){
                if (graph.adjMat[i][j]!=0){
                    check = 1;
                    break;
                }
            }
            if (check == 0) {
                graph.vertexList[i].isExit = true;
                System.out.println("Exit task: " + i);
            }
        }
        
    }
     // MWD
   private static void inputGraph3(Graph graphObject) {
    
    graphObject.nVerts=9;
    
     
     
    //input vertexs
    
    graphObject.vertexList[0]= new Vertex(0,2,1);
    graphObject.vertexList[0].isEntry=true;
    graphObject.vertexList[1]= new Vertex(1,1,2);
    graphObject.vertexList[2]= new Vertex(2,2,1);           
    graphObject.vertexList[3]= new Vertex(3,1,2);          
    graphObject.vertexList[4]= new Vertex(4,3,1);              
    graphObject.vertexList[5]= new Vertex(5,1,1);    
    graphObject.vertexList[6]= new Vertex(6,2,1);            
    graphObject.vertexList[7]= new Vertex(7,1,2); 
    graphObject.vertexList[8]= new Vertex(8,2,1);
    graphObject.vertexList[9]= new Vertex(9,1,3);
    graphObject.vertexList[10]= new Vertex(10,2,1);
    graphObject.vertexList[11]= new Vertex(11,1,1);
     
       // input edge (communication)
   
    graphObject.addEdge(0, 1, 1);
    graphObject.addEdge(0, 4, 1);
    graphObject.addEdge(1, 2, 1);
    graphObject.addEdge(2, 3, 1); 
    graphObject.addEdge(3, 9, 1);
    graphObject.addEdge(4, 5, 1);
    graphObject.addEdge(4, 6, 1);
    graphObject.addEdge(6, 7, 1);
    graphObject.addEdge(7, 8, 1);
    graphObject.addEdge(8, 9, 1);
    graphObject.addEdge(9, 10, 1);
    graphObject.addEdge(10,11, 1);
    
    
    
    
    }
     
     
     
     
     
     
     
     
}
