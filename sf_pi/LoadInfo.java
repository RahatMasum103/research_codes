/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sf_pi;

/**
 *
 * @author APU
 */
public class LoadInfo {
    public int bus_no;
    public double load_flow;
    public double max_flow;
    
      LoadInfo(int b, double f, double mf)
    {
        this.bus_no=b;
        this.load_flow=f;
        this.max_flow=mf;
    }
      
       public static int getRandomInteger(int maximum, int minimum) {
        int number = ((int) (Math.random() * (maximum - minimum))) + minimum;
        return number;
    }
    
}
