/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling_algorithm;

import java.util.ArrayList;

/**
 *
 * @author Dung
 */

// class for Vertex== node= task
public class Vertex {
    public int index;
    public int idOnProc;
    public boolean wasVisited;
    public double comp_time; // execution time
    public ArrayList parent;
    public ArrayList children;
    public boolean isEntry; // first task
    public boolean isExit; // last task
    public double b_level; // bottom level
    public double t_level;  // top level
    public double t_level_2;
    public int processor; // start from this processor
    
    public int voltage;
    public double aft;  // execution finish time
    public double ast;  // execution start time
    public boolean isCP;  // task is in critical path
    public double est_sl; // no need
    public double eft_sl;  // no need
    public double sl; // no need
    public int blSucc; 
    public int tlPred;
    public double priority;
   // public int[] CP;
    public double path;   // bt_level + top_level
    public int weight;   // # of columns
    public double rft;    // reconfiguration finish time
    public double rst;   /// reconfiguration start time
    public double lk;
    // constructor
    public Vertex(int lab, double time, int numColum){
        index = lab;
        ast = 0;
        
        comp_time = time;
        parent = new ArrayList();
        children=new ArrayList();
        //System.out.println(comp_time_avg);
        wasVisited = false;
        if (lab==0){
            isEntry = true;
        }
        else 
            isEntry = false;
        isExit = false;
        b_level = 0;
        t_level=0;
        processor = 0;
        voltage=0;
        aft = 0;
        isCP= false;
        est_sl=0;
        eft_sl=0;
        sl=0;
        idOnProc=0;
        blSucc=0;
        tlPred=0;
        priority=0;
       // CP=new int[Architecture.N_pro];
        weight=numColum;
        rst=0;
        rft=0;
        path=0;
        lk=0;
        t_level_2=0;
    }
}
