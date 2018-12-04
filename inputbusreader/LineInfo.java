/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputbusreader;

import java.util.List;

/**
 *
 * @author APU
 */
public class LineInfo {

	public int fromBus;
	public int toBus;
	public double reactance;
        public double resistance;
	
	public LineInfo(int f,int t,double ra,double rs) {
		this.fromBus=f;
		this.toBus=t;
		this.reactance=ra;
                this.resistance =rs;
                
	}

}
    

