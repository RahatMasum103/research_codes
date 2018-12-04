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
public class FlowInfo {
    public int line_no;
    public double line_flow;
//    public double max_cap;
    
    
    FlowInfo(int l, double lf)
    {
        this.line_no=l;
        this.line_flow=lf;
//        this.max_cap=mc;
    }
}
