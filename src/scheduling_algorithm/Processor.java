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


// this class describes each column
public class Processor {
    public ArrayList taskList;
    public int index;
    public int currentNumTask;
    public double availableTime;
    public Processor(int ID){
        index=ID;
        taskList= new ArrayList();
        availableTime=0;
        currentNumTask=0;
    }
    public void addTask(Vertex task){
        currentNumTask++;
        taskList.add(task.index);
        task.idOnProc=currentNumTask;
        availableTime=task.aft;
    }
    
}
