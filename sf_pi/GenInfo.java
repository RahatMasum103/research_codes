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
public class GenInfo {
    public int bus_no;
    public double gen_flow;
    public double max_flow;
    
    
    GenInfo(int b, double f, double mf)
    {
        this.bus_no=b;
        this.gen_flow=f;
        this.max_flow=mf;
    }
    
}
