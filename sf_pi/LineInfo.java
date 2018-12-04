/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sf_pi;

import java.util.List;

/**
 *
 * @author APU
 */
public class LineInfo {

	public int fromBus;
	public int toBus;
	public double reactance;
	
	public LineInfo(int f,int t,double r) {
		this.fromBus=f;
		this.toBus=t;
		this.reactance=r;
	}

}
    

