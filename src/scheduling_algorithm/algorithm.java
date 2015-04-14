/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling_algorithm;

import java.util.ArrayList;

/**
 *
 * @author namkhanh
 */
public class algorithm {
     private Graph graph;
     public   ArrayList  sort;// topological queue
        // constructor
    public algorithm(Graph g){
        graph = g;
        sort= new  ArrayList();
        }
    
    public void compute_adj(){
        /*
        for (int i=0; i<graph.nVerts;i++) {
           for (int j=0; j<graph.nVerts;j++) {
               if (graph.adjMat[i][j]!=0){
                   graph.vertexList[i].children.add(j);
                   graph.vertexList[j].parent.add(i);
               }
           }
       }*/
       
       for (int i=0; i<graph.nVerts;i++) {
           //System.out.println("cac dinh cha cua dinh: "+i);
           //System.out.println(graph.vertexList[i].parent);
           System.out.println("children of vertex: "+i); 
           System.out.println(graph.vertexList[i].children);
           
       }
        
        
    }
    // topological sort
    public void dfs(){
      
       Vertex vertex1= new Vertex(0,0,0);
       
       
      // DFS 
      for (int i=0; i< graph.nVerts; i++){
          if (!graph.vertexList[i].wasVisited) {
              DFS_visit(i);
              
          }
       }
     
      
        System.out.println(sort);
        
        
        
    }
    
    // a function of topological sort
    public void DFS_visit(int u){
      for (int j=0; j<graph.vertexList[u].children.size(); j++){
          int v= (int)graph.vertexList[u].children.get(j);
          if (!graph.vertexList[v].wasVisited){
              graph.vertexList[v].wasVisited=true;
              DFS_visit(v);
          }
      }  
      graph.vertexList[u].isCP=true;
      sort.add(u); // (0,u): topological; (u): inverser topological
    }
    
    
    
    
    
    // compute bottom level
    public void compute_b_L(){
        dfs();
        Object[] b;
        int[] a;
        
        a=new int[sort.size()];         
        b= sort.toArray();
        for (int i=0;i<a.length;i++){
            a[i]=(int)b[i];
            //System.out.println((int)b[i]+(int)b[i+1]);
        }
        
//        System.out.println(graph.vertexList[11].children.size());
        
        
        for (int ni=0;ni<a.length;ni++){
            double max=0;
            graph.vertexList[a[ni]].b_level=0;
         if (graph.vertexList[a[ni]].children.size()==0){
             graph.vertexList[a[ni]].b_level=graph.vertexList[a[ni]].comp_time;
         } else {  
            
            for (int nj=0;nj<graph.vertexList[a[ni]].children.size();nj++){
               int tg= (int)graph.vertexList[a[ni]].children.get(nj);// tg=nj
               if (graph.vertexList[tg].comp_time>max){
                   max=graph.vertexList[tg].b_level;
                   graph.vertexList[a[ni]].blSucc=tg;
                   
               }
               graph.vertexList[a[ni]].b_level=graph.vertexList[a[ni]].comp_time+max;
               
           }
            
        }
        }
       for (int i=0;i<graph.nVerts;i++){ 
                      System.out.println(graph.vertexList[i].b_level);
       }
       for (int i=0;i<graph.nVerts;i++){ 
            System.out.println("blSucc cua "+i+"   la:");
           System.out.println(graph.vertexList[i].blSucc);
       }
                    
    }
    
    
    //compute top level 2
    public void compute_t_L_2(){
        dfs();
        Object[] b;
        int[] a;
        
        a=new int[sort.size()];         
        b= sort.toArray();
        for (int i=0;i<a.length;i++){
            a[i]=(int)b[a.length-i-1];
            //System.out.println((int)b[i]+(int)b[i+1]);
        }
        
       // System.out.println(graph.vertexList[11].parent.size());
        
        
        for (int ni=0;ni<a.length;ni++){
            double max=0;
            graph.vertexList[a[ni]].t_level_2=0;
         if (graph.vertexList[a[ni]].parent.size()==0){
             graph.vertexList[a[ni]].t_level_2=graph.vertexList[a[ni]].weight;
         } else {  
            
            for (int nj=0;nj<graph.vertexList[a[ni]].parent.size();nj++){
               int tg= (int)graph.vertexList[a[ni]].parent.get(nj);// tg=nj
               if (graph.vertexList[tg].t_level_2+graph.vertexList[tg].comp_time>max){
                   max=graph.vertexList[tg].t_level_2+graph.vertexList[tg].comp_time;
                   graph.vertexList[a[ni]].tlPred=tg;
                   
               }
               graph.vertexList[a[ni]].t_level_2=max;
               
           }
            
        }
        }
       for (int i=0;i<graph.nVerts;i++){ 
           System.out.println(graph.vertexList[i].t_level_2);
       }
        
                    
    }
    
    //compute top level 
    public void compute_t_L(){
        dfs();
        Object[] b;
        int[] a;
        
        a=new int[sort.size()];         
        b= sort.toArray();
        for (int i=0;i<a.length;i++){
            a[i]=(int)b[a.length-i-1];
            //System.out.println((int)b[i]+(int)b[i+1]);
        }
        
       // System.out.println(graph.vertexList[11].parent.size());
        
        
        for (int ni=0;ni<a.length;ni++){
            double max=0;
            graph.vertexList[a[ni]].t_level=0;
         if (graph.vertexList[a[ni]].parent.size()==0){
             graph.vertexList[a[ni]].t_level=0;
         } else {  
            
            for (int nj=0;nj<graph.vertexList[a[ni]].parent.size();nj++){
               int tg= (int)graph.vertexList[a[ni]].parent.get(nj);// tg=nj
               if (graph.vertexList[tg].t_level+graph.vertexList[tg].comp_time>max){
                   max=graph.vertexList[tg].t_level+graph.vertexList[tg].comp_time;
                   graph.vertexList[a[ni]].tlPred=tg;
                   
               }
               graph.vertexList[a[ni]].t_level=max;
               
           }
            
        }
        }
       
   // xoa nhap
      /*  
        for (int i=0;i<graph.nVerts;i++){ 
           System.out.println(graph.vertexList[i].t_level);
       }
      */  
                    
    }
    
    
    
    public void compute_path(){
        compute_t_L_2();
        compute_b_L();
        for (int i=0;i<graph.nVerts;i++){ 
          graph.vertexList[i].path=graph.vertexList[i].b_level+graph.vertexList[i].t_level_2 ;
       }
  
   // xoa nhap     
    /*    System.out.println("path of each task");
        for (int i=0;i<graph.nVerts;i++){ 
           System.out.println(graph.vertexList[i].path);
       } 
     */   
    }
    
    
    public void findCriticalPath(){
        compute_b_L();
        int first=0;
        double max=0;
        //reset isCP 
        for (int i=0;i<graph.nVerts;i++){
            graph.vertexList[i].isCP=false;
        }
        
        //find the 1st node of CP
        for (int i=0;i<graph.nVerts;i++){
          if (graph.vertexList[i].b_level>max){
              max=graph.vertexList[i].b_level;
              first=i;
          }  
        }
        
        mark(first);
        //output the blSucc
        for (int i=0;i<graph.nVerts;i++){
           {
                System.out.println(graph.vertexList[i].blSucc);
            }
        }
        
        //output the Critical Path
        System.out.println("critical path");
        for (int i=1;i<graph.nVerts;i++){
           if (graph.vertexList[i].isCP){
                System.out.print(i);
            }
        }
        
        
    }
    //mark the Critical Path
    public void mark(int n){
        graph.vertexList[n].isCP=true;
        if (graph.vertexList[n].b_level>0){
        mark(graph.vertexList[n].blSucc);
        }
    }
    /*
    //creat node list =algorithm 12, p133
    public void createNodeList(){
        int j=0;
        int n=0; 
        ArrayList nodeList=new ArrayList();//node Lise =nodeList
        ArrayList freeNode=new ArrayList();// freenode is Q
        //line 1
        compute_b_L(); //priority = bottom level
        //line2
        for (int i=1;i<graph.nVerts;i++){ 
            if (graph.vertexList[i].parent.size()==0){
                freeNode.add(i);
             }
        }
        //line 3
        while (freeNode.size()!=0){
            //line 4 find node with highest priority
            double max=0;
            for (int i=0; i< freeNode.size();i++){
                int tg=(int)freeNode.get(i);
                if (graph.vertexList[tg].b_level>max){
                    max=graph.vertexList[tg].b_level;
                    n=tg;
                }
                
                
            }
            //line 5
            nodeList.add(n);
            Boolean check=freeNode.remove((Object)n);
            //line6
            for (j=0;j<graph.vertexList[n].children.size();j++){
                int tg=(int)graph.vertexList[n].children.get(j);//tg=j
                check=nodeList.containsAll(graph.vertexList[tg].parent);
                if (check){
                    freeNode.add(tg);
                    }   
                }
                
        }
        
       System.out.println(nodeList);   
        
        
        
        
    }
    */
    
    
    
    
    
}

