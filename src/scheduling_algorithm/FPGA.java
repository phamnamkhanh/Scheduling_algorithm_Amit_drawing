/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling_algorithm;

/**
 *
 * @author namkhanh
 */
public class FPGA {
     public int N_column ;
     public Processor[] ArrayColumn;
     public Processor controller;
     
     public FPGA(int number){
         N_column=number;
         ArrayColumn=new Processor[N_column];
         for (int i=0;i<N_column;i++){
             ArrayColumn[i]=new Processor(i);
         }
         controller= new Processor(N_column);
     }
     
     public void reset(){
         for (int i=0;i<N_column;i++){
             ArrayColumn[i].availableTime=0;
         }
         
         controller.availableTime=0;
     }
    
}
