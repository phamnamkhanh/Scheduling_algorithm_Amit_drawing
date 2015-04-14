/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling_package;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author namkhanh
 */
public class schedule {
     private Graph graph;
     //public   ArrayList  sort;// topological queue
     public static FPGA fpga;
     public static double alpha;
     public static double beta;
     public static double gamma;
     public static double delta;
     public static double eta;
     public static double sigma;
     public static double mu;
        
        // constructor
    public schedule(Graph g, int num_column, double a, double b, double c, double d, double e, double s, double m){
        graph = g;
               
        fpga=new FPGA(num_column);
        alpha = a;
        beta = b;
        gamma=c;
        delta=d;
        eta=e;
        sigma=s;
        mu=m;
        
        
        
        }
    
    
    //algorithm 1 trong vo-p47
    public void dynamic_schedule(){
       
        fpga.reset();
        
        //line 1- create set of ready task- ready node and set of shceduled node- shceduledNode 
        ArrayList readyNode=new ArrayList();
        ArrayList scheduledNode=new ArrayList();
        //line 3- add source node
        for (int i=0;i<graph.nVerts;i++){                 //chu y
            if (graph.vertexList[i].parent.size()==0){
                readyNode.add(i);
             }
        }
   //line 2- compute path for each task
        algorithm al=new algorithm(graph);
        al.compute_path();
        al.compute_t_L();
        
   // from line 4-
        while (readyNode.size()!=0){
            //line 4 find node with highest priority
            int temp_n=0;
            int temp_p=0;
            double max=-1000;
            // line 5- voi moi task(node) thuoc readyNode
            // chon task co priority cao nhat
            for (int i=0; i< readyNode.size();i++){
                
                int tg=(int)readyNode.get(i);// tg =readyNode.[i]
                compute_est(tg); // line 6- tinh est
                compute_eft(tg); // line 7- tinh eft
                //compute_lk(tg);
                compute_f(tg); // line 8- tinh function F- gan tg.priority=tg.f
                               
                if (graph.vertexList[tg].priority>max){
                    max=graph.vertexList[tg].priority;
                    temp_n=tg;
                }
                
                
            }
            
            
    //line 13- placement algorithm-BF,FF,C-stuff choose a start column Pj cho task temp_n
         // thu tat ca cac column tu thap len cao 
         double lk=0;
            double min_score=10000000;
            double score=0;
          //  for (int j=fpga.N_column-graph.vertexList[temp_n].weight-1;j>=0 ; j-- ){
            for (int j=0;j<fpga.N_column-graph.vertexList[temp_n].weight ; j++ ){
                // leakage-aware placement
       //         if (r_start_time(temp_n,j)==0){
       //             lk=0;
       //         } else{
                graph.vertexList[temp_n].rft=r_start_time(temp_n,j)+graph.vertexList[temp_n].weight; 
                graph.vertexList[temp_n].ast=e_start_time(temp_n,j);
                lk=e_start_time(temp_n,j)-graph.vertexList[temp_n].rft;
       //         }
                
                score=0*lk+1*e_start_time(temp_n,j);
                if (score<min_score){   // change the coefficient to get a trade off betwwen lk and time
                    min_score= score;
                    temp_p=j;
                }
            } 
    //line 14- schedule task temp_n on column temp_p        
            
            schedule(temp_n,temp_p);
           //schedule(temp_n,graph.vertexList[temp_n].processor);
    //line 15- add temp_n vao scheduledNode va xoa khoi ready node       
            scheduledNode.add(temp_n);
            Boolean check=readyNode.remove((Object)temp_n);
    //line 16- add nhung ready task con cua task temp_n
            for (int j=0;j<graph.vertexList[temp_n].children.size();j++){
                int tg=(int)graph.vertexList[temp_n].children.get(j);//tg=j
                check=scheduledNode.containsAll(graph.vertexList[tg].parent);
                if (check){
                    readyNode.add(tg);
                    }   
                }
                
        }
        
  // compute total leakage power and makespan
        graph.energy=compute_total_lk();
        for (int i=0; i<graph.nVerts;i++ ){
            if (graph.makespan<graph.vertexList[i].aft){
                graph.makespan=graph.vertexList[i].aft;
            }
            
        } 
        
          
        
       
        
     // write the output   
     for (int i=0;i<graph.nVerts;i++){
         System.out.println("task "+i+" tren processor: "+graph.vertexList[i].processor+" : start recon time: "+graph.vertexList[i].rst+" : finish recon time: "+graph.vertexList[i].rft+
                 " : start recon time: "+graph.vertexList[i].ast+" : finish time: "+graph.vertexList[i].aft);
   
     }   
   
     
  
// draw schedule
     
//        Drawing d = new Drawing(graph);
//        d.start();
//        
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(schedule.class.getName()).log(Level.SEVERE, null, ex);
//        } 
     
 
        
        System.out.println("leakage is:"+graph.energy);   
        System.out.println("time is:"+graph.makespan); 
    }
    
    
    public double compute_lk_power(int n){
        double lk=0;
        lk= graph.vertexList[n].weight*(graph.vertexList[n].ast-graph.vertexList[n].rft);
        return lk;
                
    }
    
    public double compute_total_lk(){
        double total=0;
        for (int i=0; i<graph.nVerts;i++ ){
            total=total+compute_lk_power(i);
        }
        return total;
        
    }
   
    // schedule task n from column p
    public void schedule(int n, int p){
        graph.vertexList[n].processor=p;
        graph.vertexList[n].rst=r_start_time(n,p);
        if (graph.vertexList[n].rst==0){
           graph.vertexList[n].rft=0; 
        }
        else{
        graph.vertexList[n].rft=graph.vertexList[n].rst+graph.vertexList[n].weight;
        }
        graph.vertexList[n].ast=e_start_time(n,p);
        graph.vertexList[n].aft=graph.vertexList[n].ast+graph.vertexList[n].comp_time;
        if ((graph.vertexList[n].rst==0)&&(graph.vertexList[n].ast!=0)){
            graph.vertexList[n].rft=graph.vertexList[n].ast;
            graph.vertexList[n].rst=graph.vertexList[n].rft-graph.vertexList[n].weight;
        }
        //update available time tren column p..(p+weight-1)
        for (int i=0; i<graph.vertexList[n].weight;i++ ){
            fpga.ArrayColumn[p+i].addTask(graph.vertexList[n]);
        }
        //update available time on controller - reconfigurator
        fpga.controller.availableTime= graph.vertexList[n].rft;
        fpga.controller.currentNumTask++;
        fpga.controller.taskList.add(n);
            
        
    }
    
    public double r_start_time(int n,int p){
        double erst=0;
        erst=Math.max(max_available(p,graph.vertexList[n].weight),fpga.controller.availableTime);
        return erst;
    }
    
    
    
    public double e_start_time(int n,int p){
        double est=0;
        
        double parent_dep=0;
        // find max of parent dependencies
        for (int i=0; i<graph.vertexList[n].parent.size();i++){
             int tg=(int)graph.vertexList[n].parent.get(i);
             if (graph.vertexList[tg].aft>parent_dep){
                 parent_dep=graph.vertexList[tg].aft;
             }
        }
        graph.vertexList[n].rst=r_start_time(n, p);
        if (graph.vertexList[n].rst==0){
            if (parent_dep==0){
                est=0;
                graph.vertexList[n].rft=0;
                              
            } else {
                est=Math.max(parent_dep, graph.vertexList[n].rst+graph.vertexList[n].weight);
                graph.vertexList[n].rst=est-graph.vertexList[n].weight;
            }
        } else {est=Math.max(parent_dep, graph.vertexList[n].rst+graph.vertexList[n].weight);
            
        }
        
        return est;
    }
    
    // find available time of w-columns from column p
    public double max_available(int p, int w){
        double max=0;
        for (int i=p; i<p+w; i++){
            if (fpga.ArrayColumn[i].availableTime>max){
                max=fpga.ArrayColumn[i].availableTime;
            }
        }
      
        return max;
    }
    
    public void compute_est(int n){
        double est;
        int temp_p=0;
        double parent_dep=0;
        // find max of parent dependencies
        for (int i=0; i<graph.vertexList[n].parent.size();i++){
             int tg=(int)graph.vertexList[n].parent.get(i);
             if (graph.vertexList[tg].aft>parent_dep){
                 parent_dep=graph.vertexList[tg].aft;
             }
        }
        
        
        
        // find EST of reconfiguration=ERST
        double erst=1000;
        for (int i=0; i< fpga.N_column-graph.vertexList[n].weight; i++ ){
           if (max_available(i,graph.vertexList[n].weight)<erst)
           { erst=max_available(i,graph.vertexList[n].weight);
           temp_p=i;               
           }
        }
        erst=Math.max(erst, fpga.controller.availableTime);
        
        // EST max of parent dependecies and erst+reconfiguration time
        est=Math.max(parent_dep, erst+graph.vertexList[n].weight);
        graph.vertexList[n].est_sl=est;
        graph.vertexList[n].processor=temp_p;
        
    }
    
    
    
        public void compute_lk(int n){
        // consider leakage power
         double lk=0;
            double score=1000;
            for (int j=0;j< fpga.N_column-graph.vertexList[n].weight; j++ ){
                // leakage-aware placement
                if (r_start_time(n,j)==0){
                    lk=0;
                } else{
                graph.vertexList[n].rft=r_start_time(n,j)+graph.vertexList[n].weight;    
                lk=e_start_time(n,j)-r_start_time(n,j);
                }
                if ((lk)<score){
                    score= lk;
                    
                }
            }
            graph.vertexList[n].lk=score;
        
    }
    
    
    
    public void compute_eft(int n){
        graph.vertexList[n].eft_sl=graph.vertexList[n].est_sl+graph.vertexList[n].comp_time;
    }
    public void compute_f(int n){
//        int alpha, beta, gamma,delta,eta, sigma, mu;
        // f= alpha*BT(b_level) + beta*TL(t_level) + gamma*C (weight) +(-) delta*RT(comp_time) - eta*LK(lk) + mu*EET(est_sl) + sigma*ERT(rst)
        // first 4 fators: BT, TL, weight, comp_time => is static metrics for EACH TASK => highly dependent on the TG itself
        // last 3 factors: LK, EET, ERT => dynamic metrics during scheduling
//        alpha=4;
//        beta=-10;
//        gamma=10;
//        delta=-1;
//        eta=0;
//         sigma=0;
//        mu=-2;
//       
        graph.vertexList[n].priority=alpha*graph.vertexList[n].b_level +beta*graph.vertexList[n].t_level+ gamma*graph.vertexList[n].weight +delta*graph.vertexList[n].comp_time
                + eta*graph.vertexList[n].lk + mu*graph.vertexList[n].est_sl + sigma*graph.vertexList[n].rst;
    }
    
     
    
}
