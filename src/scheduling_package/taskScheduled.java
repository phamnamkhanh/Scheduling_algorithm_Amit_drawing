/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling_package;

/**
 *
 * @author namkhanh
 */
public class taskScheduled {
    public int index;
    public double compTime;
    public double startTime;
    public taskScheduled(int id, double start,double comp){
        index=id;
        compTime=comp;
        startTime=start;
    }
    
}
