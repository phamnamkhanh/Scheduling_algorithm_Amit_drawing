/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scheduling_package;

/**
 *
 * @author AdminNUS
 */
public class Scheduling_wrapper {
     public static void main(String[] args) {
            Scheduling_algorithm schedule= new Scheduling_algorithm(4, -10, 10, -1, 0, 0, -2, "TG2");
            schedule.obj_func();
            System.out.println("time: " + schedule.schedule_length );
            System.out.println("energy: " + schedule.leakage );
     }
         
}
